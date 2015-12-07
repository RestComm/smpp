/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.protocols.smpp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.mobicents.protocols.smpp.event.EventDispatcher;
import org.mobicents.protocols.smpp.event.SessionObserver;
import org.mobicents.protocols.smpp.event.SimpleEventDispatcher;
import org.mobicents.protocols.smpp.message.Bind;
import org.mobicents.protocols.smpp.message.BindReceiver;
import org.mobicents.protocols.smpp.message.BindResp;
import org.mobicents.protocols.smpp.message.BindTransceiver;
import org.mobicents.protocols.smpp.message.BindTransmitter;
import org.mobicents.protocols.smpp.message.CommandId;
import org.mobicents.protocols.smpp.message.SMPPPacket;
import org.mobicents.protocols.smpp.message.Unbind;
import org.mobicents.protocols.smpp.message.UnbindResp;
import org.mobicents.protocols.smpp.message.tlv.Tag;
import org.mobicents.protocols.smpp.net.SmscLink;
import org.mobicents.protocols.smpp.net.TcpLink;
import org.mobicents.protocols.smpp.util.APIConfig;
import org.mobicents.protocols.smpp.util.APIConfigFactory;
import org.mobicents.protocols.smpp.util.DefaultSequenceScheme;
import org.mobicents.protocols.smpp.util.PropertyNotFoundException;
import org.mobicents.protocols.smpp.util.SequenceNumberScheme;
import org.mobicents.protocols.smpp.version.SMPPVersion;
import org.mobicents.protocols.smpp.version.VersionException;
import org.mobicents.protocols.smpp.version.VersionFactory;

// TODO documentation!
/**
 * @author amit bhayani
 * @author orank
 */
public class Session {
    private static final AtomicInteger SESSION_ID = new AtomicInteger(1);
    
    private final Logger log;
    private String sessionId;
    private SMPPVersion version = VersionFactory.getDefaultVersion();
    private SessionType type;
    private AtomicReference<SessionState> state =
        new AtomicReference<SessionState>(SessionState.UNBOUND);
    private SmscLink smscLink;
    private SequenceNumberScheme numberScheme = new DefaultSequenceScheme();
    private EventDispatcher eventDispatcher;
    private Receiver receiver;
    private boolean useOptionalParams = version.isSupportTLV();
    private boolean validating = true;
    
    public Session(SmscLink link) {
        sessionId = "Session-" + SESSION_ID.getAndIncrement();
        log = LoggerFactory.getLogger(Session.class + "." + sessionId);
        this.smscLink = link;
        initFromConfig();
    }
    
    public Session(String host, int port) throws UnknownHostException {
        this(new TcpLink(host, port));
    }

    public String getSessionId() {
        return sessionId;
    }
    
    public void addObserver(SessionObserver observer) {
        eventDispatcher.addObserver(observer);
    }
    
    public void removeObserver(SessionObserver observer) {
        eventDispatcher.removeObserver(observer);
    }
    
    public SmscLink getSmscLink() {
        return smscLink;
    }
    
    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }
    
    public void setEventDispatcher(EventDispatcher eventDispatcher) {
        EventDispatcher oldDispatcher = this.eventDispatcher;
        initNewDispatcher(oldDispatcher, eventDispatcher);
        this.eventDispatcher = eventDispatcher;
        if(oldDispatcher!=null){
        	oldDispatcher.destroy();
        }
    }
    
    public SMPPVersion getVersion() {
        return version;
    }

    public void setVersion(SMPPVersion version) {
        this.version = version;
        this.useOptionalParams = version.isSupportTLV();
    }

    public SequenceNumberScheme getSequenceNumberScheme() {
        return numberScheme;
    }
    
    public void setSequenceNumberScheme(SequenceNumberScheme numberScheme) {
        this.numberScheme = numberScheme;
    }
    
    public boolean isValidating() {
        return validating;
    }

    public void setValidating(boolean validating) {
        this.validating = validating;
    }

    public void bind(SessionType type,
            String systemID,
            String password,
            String systemType) throws IOException {
        bind(type, systemID, password, systemType, 0, 0, null);
    }
    
    public void bind(SessionType type,
            String systemID,
            String password,
            String systemType,
            int typeOfNumber,
            int numberPlanIndicator,
            String addressRange) throws IOException {
        Bind bindRequest;
        if (type == SessionType.TRANSMITTER) {
            bindRequest = new BindTransmitter();
        } else if (type == SessionType.RECEIVER) {
            bindRequest = new BindReceiver();
        } else {
            bindRequest = new BindTransceiver();
        }
        bindRequest.setVersion(version);
        bindRequest.setSystemId(systemID);
        bindRequest.setPassword(password);
        bindRequest.setSystemType(systemType);
        bindRequest.setAddressTon(typeOfNumber);
        bindRequest.setAddressNpi(numberPlanIndicator);
        bindRequest.setAddressRange(addressRange);
        bind(bindRequest);
    }
    
    public void bind(Bind bindRequest) throws IOException {
        if (receiver == null) {
            initReceiver();
        }
        if (getState() != SessionState.UNBOUND) {
            throw new IllegalStateException("Already binding or bound.");
        }
        if (bindRequest.getCommandId() == CommandId.BIND_TRANSMITTER) {
            type = SessionType.TRANSMITTER;
        } else if (bindRequest.getCommandId() == CommandId.BIND_RECEIVER) {
            type = SessionType.RECEIVER;
        } else {
            type = SessionType.TRANSCEIVER;
        }
        if (!smscLink.isConnected()) {
            smscLink.connect();
        }
        setLinkTimeout(APIConfig.BIND_TIMEOUT);
        log.debug("Sending bind packet to the SMSC..");
        sendPacketInternal(bindRequest);
        receiver.start();
    }

    public void unbind() throws IOException {
        sendPacketInternal(new Unbind());
    }
    
    public void sendPacket(SMPPPacket packet) throws IOException {
        int commandId = packet.getCommandId();
        switch (commandId) {
        case CommandId.BIND_TRANSMITTER:
        case CommandId.BIND_TRANSCEIVER:
        case CommandId.BIND_RECEIVER:
            bind((Bind) packet);
            return;
        }
        if (type == SessionType.RECEIVER) {
            // We allow the receiver to send any response type but a very
            // limited set of requests.
            if (packet.isRequest()
                    && !(commandId == CommandId.UNBIND
                            || commandId == CommandId.ENQUIRE_LINK)) {
                throw new UnsupportedOperationException(
                        "Receiver connection cannot send command " + commandId);
            }
        }
        sendPacketInternal(packet);
    }

    public void closeLink() throws IOException {
        if (getState() == SessionState.UNBOUND || getState() == SessionState.UNBINDING) {
            smscLink.disconnect();
        } else {
            throw new IllegalStateException("Cannot close link while connection is bound.");
        }
    }
    
    public SessionState getState() {
        return state.get();
    }

    public Receiver getReceiver(Receiver receiver) {
        return receiver;
    }
    
    public void setReceiver(Receiver receiver) {
        if (this.receiver != null && this.receiver.isStarted()) {
            throw new IllegalStateException(
                    "Cannot change the receiver while it's running");
        }
        this.receiver = receiver;
    }
    
    public void processReceivedPacket(SMPPPacket packet) {
        switch (packet.getCommandId()) {
        case CommandId.BIND_TRANSMITTER_RESP:
        case CommandId.BIND_RECEIVER_RESP:
        case CommandId.BIND_TRANSCEIVER_RESP:
            processReceivedBindResponse((BindResp) packet);
            break;
        case CommandId.UNBIND:
            processReceivedUnbind((Unbind) packet);
            break;
        case CommandId.UNBIND_RESP:
            processReceivedUnbindResponse((UnbindResp) packet);
            break;
        default:
            // Do nothing.
        }
    }

    private void setState(SessionState fromState, SessionState toState) {
        if (!state.compareAndSet(fromState, toState)) {
            log.error("Race condition in setting state - expected {} but is {}. New value is "+ toState,
                    fromState, getState());
        }
    }

    private void initFromConfig() {
        APIConfig config = APIConfigFactory.getConfig();
        EventDispatcher dispatcher;
        try {
            dispatcher = config.getClassInstance(
                    APIConfig.EVENT_DISPATCHER_CLASS, EventDispatcher.class);
        } catch (PropertyNotFoundException x) {
            log.debug("Config does not specify an event dispatcher. Using {}",
                    SimpleEventDispatcher.class);
            dispatcher = new SimpleEventDispatcher();
        }
        setEventDispatcher(dispatcher);
    }

    private void initReceiver() {
        receiver = new ReceiverThread(this);
        receiver.setName(sessionId + "-Receiver");
    }

    private void initNewDispatcher(EventDispatcher oldDispatcher, EventDispatcher newDispatcher) {
        newDispatcher.init();
        if (oldDispatcher != null) {
            Collection<SessionObserver> observers =
                oldDispatcher.getObservers();
            for (SessionObserver observer : observers) {
                newDispatcher.addObserver(observer);
            }
        }
    }
    
    private void sendPacketInternal(SMPPPacket packet) throws IOException {
        if (packet.getSequenceNum() < 0L && numberScheme != null) {
            packet.setSequenceNum(numberScheme.nextNumber());
        }
        if (validating) {
            packet.validate(version);
        }
        smscLink.write(packet, useOptionalParams);
        processSentPacket(packet);
    }

    private void processSentPacket(SMPPPacket packet) {
        switch (packet.getCommandId()) {
        case CommandId.BIND_TRANSMITTER:
        case CommandId.BIND_RECEIVER:
        case CommandId.BIND_TRANSCEIVER:
            processSentBind((Bind) packet);
            break;
        case CommandId.UNBIND:
            processSentUnbind((Unbind) packet);
            break;
        case CommandId.UNBIND_RESP:
            processSentUnbindResponse((UnbindResp) packet);
            break;
        default:
        }
    }
    
    private void processSentBind(Bind bindRequest) {
        setState(SessionState.UNBOUND, SessionState.BINDING);
    }
    
    private void processSentUnbind(Unbind unbindRequest) {
        setState(SessionState.BOUND, SessionState.UNBINDING);
    }
    
    private void processSentUnbindResponse(UnbindResp unbindResponse) {
        int status = unbindResponse.getCommandStatus();
        if (status == 0) {
            setState(SessionState.UNBINDING, SessionState.UNBOUND);
        }
    }
    
    private void processReceivedBindResponse(BindResp bindResponse) {
        int status = bindResponse.getCommandStatus();
        if (status == 0) {
            setState(SessionState.BINDING, SessionState.BOUND);
            negotiateVersion(bindResponse);
            setLinkTimeout(APIConfig.LINK_TIMEOUT);
        } else {
            log.warn("Received a bind response with status {}", status);
            setState(SessionState.BINDING, SessionState.UNBOUND);
        }
    }
    
    private void negotiateVersion(BindResp bindResponse) {
        if (!bindResponse.isSet(Tag.SC_INTERFACE_VERSION)) {
            log.info("SMSC did not supply SC_INTERFACE_VERSION."
                    + " Disabling optional parameter support.");
            useOptionalParams = false;
            return;
        }
        int versionId = 0;
        try {
            versionId =
                bindResponse.getTLVTable().getInt(Tag.SC_INTERFACE_VERSION);
            SMPPVersion smscVersion =
                VersionFactory.getVersion(versionId);
            log.info("SMSC states its version as {}", smscVersion);
            if (smscVersion.isOlderThan(version)) {
                version = smscVersion;
                useOptionalParams = version.isSupportTLV();
            }
        } catch (VersionException x) {
            log.debug("SMSC implements a version I don't know: {}", versionId);
        }
    }
    
    private void processReceivedUnbind(Unbind unbindRequest) {
        setState(SessionState.BOUND, SessionState.UNBINDING);
    }
    
    private void processReceivedUnbindResponse(UnbindResp unbindResponse) {
        int status = unbindResponse.getCommandStatus();
        if (status == 0) {
            setState(SessionState.UNBINDING, SessionState.UNBOUND);
        } else {
            log.warn("Received an unbind response with status {}", status);
        }
    }
    
    private void setLinkTimeout(String propName) {
        try {
            if (smscLink.isTimeoutSupported()) {
                APIConfig config = APIConfigFactory.getConfig();
                int linkTimeout = config.getInt(propName);
                smscLink.setTimeout(linkTimeout);
                if (log.isDebugEnabled()) {
                    log.debug("Set the link timeout to {}", linkTimeout);
                }
            } else {
                log.info("SMSC link implementation does not support timeouts.");
            }
        } catch (PropertyNotFoundException x) {
            log.debug("Not setting link timeout as it is not configured.");
        }
    }
}
