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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.mobicents.protocols.smpp.event.ReceiverExceptionEvent;
import org.mobicents.protocols.smpp.event.ReceiverExitEvent;
import org.mobicents.protocols.smpp.event.SMPPEvent;
import org.mobicents.protocols.smpp.message.SMPPPacket;
import org.mobicents.protocols.smpp.net.ReadTimeoutException;
import org.mobicents.protocols.smpp.util.APIConfig;
import org.mobicents.protocols.smpp.util.APIConfigFactory;
import org.mobicents.protocols.smpp.util.PacketFactory;

/**
 * Receiver thread for the connection.
 * @author amit bhayani
 * @version $Id: ReceiverThread.java 456 2009-01-15 17:15:38Z orank $
 */
public class ReceiverThread implements Receiver, Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ReceiverThread.class);

    private Thread thread;
    private boolean running;
    private Session session;
    private PacketFactory packetFactory = new PacketFactory();

    public ReceiverThread() {
        thread = new Thread(this);
        thread.setDaemon(true);
    }
    
    public ReceiverThread(Session session) {
        this();
        this.session = session;
    }
    
    public PacketFactory getPacketFactory() {
        return packetFactory;
    }

    public void setPacketFactory(PacketFactory packetFactory) {
        this.packetFactory = packetFactory;
    }

    public Session getSession() {
        return session;
    }
    
    public void setSession(Session session) {
        this.session = session;
    }
    
    public String getName() {
        return thread.getName();
    }
    
    public void setName(String name) {
        thread.setName(name);
    }
    
    public void run() {
        LOG.debug("Receiver thread starting.");
        SMPPEvent exitEvent = null;
        try {
            running = true;
            exitEvent = processPackets();
        } catch (Exception x) {
        	LOG.error("Error in receiver thread", x);
            exitEvent = new ReceiverExitEvent(session, x);
        }
        session.getEventDispatcher().notifyObservers(session, exitEvent);
        LOG.debug("Destroying event dispatcher.");
        session.getEventDispatcher().destroy();
        LOG.debug("Receiver thread exiting.");
    }

    public boolean isStarted() {
        return thread.isAlive();
    }

    public void start() {
        thread.start();
    }
    
    public void stop() {
        running = false;
    }
    
    private ReceiverExitEvent processPackets() throws Exception {
        ReceiverExitEvent exitEvent = null;
        ReceiverExceptionEvent excpEvent = null;
        int ioExceptions = 0;
        APIConfig config = APIConfigFactory.getConfig();
        final int ioExceptionLimit =
            config.getInt(APIConfig.TOO_MANY_IO_EXCEPTIONS, 5);
        
        SMPPPacket packet = null;
        while (running && session.getState() != SessionState.UNBOUND) {
            try {
                packet = readNextPacket();
                if (packet == null) {
                    continue;
                }
                session.processReceivedPacket(packet);
                session.getEventDispatcher().notifyObservers(session, packet);
                ioExceptions = 0;
            } catch (ReadTimeoutException x) {
                SessionState state = session.getState();
                
                if (state == SessionState.BINDING) {
                    LOG.debug("Bind timeout occurred.");
                    exitEvent = new ReceiverExitEvent(session, null, state);
                    exitEvent.setReason(ReceiverExitEvent.BIND_TIMEOUT);
                    break;
                }
                
                if (state == SessionState.BOUND) {
                	LOG.debug("Read timeout occurred.");
                	excpEvent = new ReceiverExceptionEvent(session, x);
                	session.getEventDispatcher().notifyObservers(session, excpEvent);
                }
            } catch (IOException x) {
                LOG.debug("Exception in receiver", x);
                ioExceptions++;
                if (ioExceptions >= ioExceptionLimit) {
                    SessionState state = session.getState();
                    exitEvent = new ReceiverExitEvent(session, x, state);
                    exitEvent.setReason(ReceiverExitEvent.EXCEPTION);
                    break;
                }
            }
        }
        if (exitEvent == null) {
            exitEvent = new ReceiverExitEvent(session);
        }
        return exitEvent;
    }
    
    private SMPPPacket readNextPacket() throws IOException {
        return session.getSmscLink().read();
    }
}
