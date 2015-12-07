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
 * Alert notification. This packet type is sent from the SMSC to an ESME to
 * signal that a particular mobile subscriber has become available and a
 * delivery pending flag had previously been set for that subscriber by a
 * <code>data_sm</code> packet.
 * <p>
 * Note that there is no response packet to an <code>alert_notification</code>.
 * 
 * @version $Id: AlertNotification.java 457 2009-01-15 17:37:42Z orank $
 */
public class AlertNotification extends SMPPPacket implements Cloneable {
    private static final long serialVersionUID = 2L;

    private Address source;
    private Address destination;

    /**
     * Create a new alert_notification object.
     */
    public AlertNotification() {
        super(CommandId.ALERT_NOTIFICATION);
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public Address getSource() {
        return source;
    }

    public void setSource(Address source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || !(obj instanceof AlertNotification)) {
            return false;
        }
        boolean equals = super.equals(obj);
        if (equals) {
            AlertNotification other = (AlertNotification) obj;
            equals |= safeCompare(source, other.source);
            equals |= safeCompare(destination, other.destination);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc1 = (source != null) ? super.hashCode() : 13;
        int hc2 = (destination != null) ? super.hashCode() : 23;
        return super.hashCode() + hc1 + hc2;
    }
    
    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("source=").append(source)
        .append(",destination=").append(destination);
    }
    
    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateAddress(source);
        smppVersion.validateAddress(destination);
    }

    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeAddress(source);
        encoder.writeAddress(destination);
    }

    @Override
    protected void readMandatory(PacketDecoder decoder) {
        source = decoder.readAddress();
        destination = decoder.readAddress();
    }
    
    @Override
    protected int getMandatorySize() {
        int length = 0;
        length += sizeOf(source);
        length += sizeOf(destination);
        return length;
    }
}

