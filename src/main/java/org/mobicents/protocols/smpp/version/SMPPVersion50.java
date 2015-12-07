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

package org.mobicents.protocols.smpp.version;

import org.mobicents.protocols.smpp.message.CommandId;

public class SMPPVersion50 extends AbstractSMPPVersion {
    private static final long serialVersionUID = 2L;
    private static final int MAX_MSG_LENGTH = 255;
    
    public SMPPVersion50() {
        super(0x50, "SMPP version 5.0");
    }
    
    public int getMaxLength(MandatoryParameter mandatoryParameter) {
        switch (mandatoryParameter) {
        case SHORT_MESSAGE:
            return MAX_MSG_LENGTH;
            
        default:
            return Integer.MAX_VALUE;
        }
    }

    public boolean isSupportTLV() {
        return true;
    }

    public boolean isSupported(int commandId) {
        // Turn off the msb, which is used to signify a response packet..
        switch (commandId & 0x7fffffff) {
        case CommandId.ALERT_NOTIFICATION:
        case CommandId.BIND_RECEIVER:
        case CommandId.BIND_TRANSCEIVER:
        case CommandId.BIND_TRANSMITTER:
        case CommandId.BROADCAST_SM:
        case CommandId.CANCEL_BROADCAST_SM:
        case CommandId.CANCEL_SM:
        case CommandId.DATA_SM:
        case CommandId.DELIVER_SM:
        case CommandId.ENQUIRE_LINK:
        case CommandId.GENERIC_NACK:
        case CommandId.OUTBIND:
        case CommandId.QUERY_BROADCAST_SM:
        case CommandId.QUERY_SM:
        case CommandId.REPLACE_SM:
        case CommandId.SUBMIT_MULTI:
        case CommandId.SUBMIT_SM:
        case CommandId.UNBIND:
            return true;

        default:
            return false;
        }
    }

    public void validateMessage(byte[] message, int start, int length) {
        if (message != null && length > MAX_MSG_LENGTH) {
            throw new VersionException("Message is too long: " + length);
        }
    }

    public void validateMessageId(String messageId) {
        if (messageId != null && messageId.length() > 64) {
            throw new VersionException("Invalid message ID: " + messageId);
        }
    }

    public void validatePriorityFlag(int priority) {
        if (priority < 0 || priority > 4) {
            throw new VersionException(
                    "Invalid message priority: " + priority);
        }
    }

    public void validateRegisteredDelivery(int registeredDelivery) {
        // See comments in SMPPVersion34 for info on the following
        // check.
        if (registeredDelivery < 0 || registeredDelivery > 0x1f) {
            throw new VersionException(
                    "Invalid registered delivery: " + registeredDelivery);
        }
    }

    public void validateNumberOfDests(int num) {
        if (num < 0 || num > 255) {
            throw new VersionException(
                    "Invalid number of destinations: " + num);
        }
    }
}
