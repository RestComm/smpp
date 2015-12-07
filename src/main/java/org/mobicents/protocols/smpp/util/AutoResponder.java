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

package org.mobicents.protocols.smpp.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.mobicents.protocols.smpp.Session;
import org.mobicents.protocols.smpp.event.SMPPEvent;
import org.mobicents.protocols.smpp.event.SessionObserver;
import org.mobicents.protocols.smpp.message.CommandId;
import org.mobicents.protocols.smpp.message.DataSM;
import org.mobicents.protocols.smpp.message.DataSMResp;
import org.mobicents.protocols.smpp.message.DeliverSM;
import org.mobicents.protocols.smpp.message.DeliverSMResp;
import org.mobicents.protocols.smpp.message.EnquireLink;
import org.mobicents.protocols.smpp.message.EnquireLinkResp;
import org.mobicents.protocols.smpp.message.SMPPPacket;
import org.mobicents.protocols.smpp.message.Unbind;
import org.mobicents.protocols.smpp.message.UnbindResp;

/**
 * A connection observer that can automatically respond to some of the basic
 * packet types. An instance of this class can be added as an observer to
 * a {@link org.mobicents.protocols.smpp.Session} and configured to send response packets
 * to any of unbind, deliver_sm, data_sm or enquire_link requests.
 * <p>
 * After construction, the default configuration for an <code>AutoResponder
 * </code> is not to respond to anything. Each setting must be explicitly
 * enabled by the caller. As an example, in order to respond to enquire_link
 * and deliver_sm packets but to ignore bind and data_sm packets, code like
 * the following could be used:
 * <pre>
 * Connection connection = new Connection(...);
 * AutoResponder responder = new AutoResponder();
 * responder.setAckDeliverSm(true);
 * responder.setAckEnqureLink(true);
 * connection.addObserver(responder);
 * </pre>
 * </p>
 * @version $Id: AutoResponder.java 457 2009-01-15 17:37:42Z orank $
 */
public class AutoResponder implements SessionObserver {
    private static final Logger LOG = LoggerFactory.getLogger(AutoResponder.class);
    
    private boolean ackUnbind;
    private boolean ackDeliverSm;
    private boolean ackDataSm;
    private boolean ackEnquireLink;

    /**
     * Constructor that will initialise with all 'ack' properties initially set
     * to false.
     */
    public AutoResponder() {
    }
    
    /**
     * Constructor that will initialise with all 'ack' properties initially set
     * to <code>respond</code>.
     * @param respond The value to assign to all of this class' ack properties.
     */
    public AutoResponder(boolean respond) {
        ackUnbind = ackDeliverSm = ackDataSm = ackEnquireLink = respond;
    }
    
    public boolean isAckDataSm() {
        return ackDataSm;
    }

    public void setAckDataSm(boolean ackDataSm) {
        this.ackDataSm = ackDataSm;
    }

    public boolean isAckDeliverSm() {
        return ackDeliverSm;
    }

    public void setAckDeliverSm(boolean ackDeliverSm) {
        this.ackDeliverSm = ackDeliverSm;
    }

    public boolean isAckEnquireLink() {
        return ackEnquireLink;
    }

    public void setAckEnquireLink(boolean ackEnquireLink) {
        this.ackEnquireLink = ackEnquireLink;
    }

    public boolean isAckUnbind() {
        return ackUnbind;
    }

    public void setAckUnbind(boolean ackUnbind) {
        this.ackUnbind = ackUnbind;
    }

    public void packetReceived(Session source, SMPPPacket packet) {
        switch (packet.getCommandId()) {
        case CommandId.DELIVER_SM:
            if (ackDeliverSm) {
                respond(source, new DeliverSMResp((DeliverSM) packet));
            }
            break;
        case CommandId.DATA_SM:
            if (ackDataSm) {
                respond(source, new DataSMResp((DataSM) packet));
            }
            break;
        case CommandId.ENQUIRE_LINK:
            if (ackEnquireLink) {
                respond(source, new EnquireLinkResp((EnquireLink) packet));
            }
            break;
        case CommandId.UNBIND:
            if (ackUnbind) {
                respond(source, new UnbindResp((Unbind) packet));
            }
            break;
        }
    }

    public void update(Session source, SMPPEvent event) {
    }
    
    private void respond(Session connection, SMPPPacket response) {
        try {
            connection.sendPacket(response);
        } catch (IOException x) {
            LOG.error("IOException while trying to send packet {}: {}",
                    response, x.getMessage());
            LOG.debug("Stack trace", x);
        }
    }
}
