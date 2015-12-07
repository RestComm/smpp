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

package org.mobicents.protocols.smpp.message;

import java.io.IOException;

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;
import org.mobicents.protocols.smpp.version.SMPPVersion;

/**
 * Cancal message. This SMPP message is used to cancel a previously submitted
 * but yet undelivered short message at the SMSC. Relevant inherited fields from
 * SMPPPacket: <br>
 * <ul>
 * serviceType <br>
 * messageId <br>
 * source <br>
 * destination <br>
 * </ul>
 * 
 * @version $Id: CancelSM.java 457 2009-01-15 17:37:42Z orank $
 */
public class CancelSM extends SMPPPacket {
    private static final long serialVersionUID = 2L;

    private String serviceType;
    private String messageId;
    private Address source;
    private Address destination;
    
    /**
     * Construct a new CancelSM.
     */
    public CancelSM() {
        super(CommandId.CANCEL_SM);
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Address getSource() {
        return source;
    }

    public void setSource(Address source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            CancelSM other = (CancelSM) obj;
            equals |= safeCompare(serviceType, other.serviceType);
            equals |= safeCompare(messageId, other.messageId);
            equals |= safeCompare(source, other.source);
            equals |= safeCompare(destination, other.destination);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (serviceType != null) ? serviceType.hashCode() : 0;
        hc += (messageId != null) ? messageId.hashCode() : 0;
        hc += (source != null) ? source.hashCode() : 0;
        hc += (destination != null) ? destination.hashCode() : 0;
        return hc;
    }
    
    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("serviceType=").append(serviceType)
        .append(",messageId=").append(messageId)
        .append(",source=").append(source)
        .append(",destination=").append(destination);
    }
    
    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateServiceType(serviceType);
        smppVersion.validateMessageId(messageId);
        smppVersion.validateAddress(source);
        smppVersion.validateAddress(destination);
    }
    
    @Override
    protected void readMandatory(PacketDecoder decoder) {
        serviceType = decoder.readCString();
        messageId = decoder.readCString();
        source = decoder.readAddress();
        destination = decoder.readAddress();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(serviceType);
        encoder.writeCString(messageId);
        encoder.writeAddress(source);
        encoder.writeAddress(destination);
    }
    
    @Override
    protected int getMandatorySize() {
        int length = 2;
        length += sizeOf(serviceType);
        length += sizeOf(messageId);
        length += sizeOf(source);
        length += sizeOf(destination);
        return length;
    }
}
