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
 * Query the last number of messages sent from a certain ESME. Relevant
 * inherited fields from SMPPPacket: <br>
 * <ul>
 * source <br>
 * </ul>
 * 
 * @version $Id: QueryLastMsgs.java 457 2009-01-15 17:37:42Z orank $
 */
public class QueryLastMsgs extends SMPPPacket {
    private static final long serialVersionUID = 2L;
    
    /**
     * The source address for which to query messages. The last <code>
     * msgCount</code> messages originating from this source address will
     * be retrieved.
     */
    private Address source;

    /**
     * Number of messages to look up.
     */
    private int msgCount;

    /**
     * Construct a new QueryLastMsgs.
     */
    public QueryLastMsgs() {
        super(CommandId.QUERY_LAST_MSGS);
    }

    public Address getSource() {
        return source;
    }

    public void setSource(Address source) {
        this.source = source;
    }

    public int getMsgCount() {
        return msgCount;
    }

    /**
     * Set the number of messages to query. <code>msgCount</code> must be
     * between 1 and 100 inclusive. Attempts to set a value less than 1 will
     * force the value to 1. Attempts to set a value greater than 100 will
     * force the value to 100.
     * @param msgCount The number of messages to query from the SMSC.
     */
    public void setMsgCount(int msgCount) {
        if (msgCount < 1) {
            this.msgCount = 1;
        } else if (msgCount > 100) {
            this.msgCount = 100;
        } else {
            this.msgCount = msgCount;
        }
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            QueryLastMsgs other = (QueryLastMsgs) obj;
            equals |= safeCompare(source, other.source);
            equals |= msgCount == other.msgCount;
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (source != null) ? source.hashCode() : 0;
        hc += Integer.valueOf(msgCount).hashCode();
        return hc;
    }

    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("source=").append(source)
        .append("msgCount=").append(msgCount);
    }

    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateAddress(source);
    }
    
    @Override
    protected void readMandatory(PacketDecoder decoder) {
        source = decoder.readAddress();
        msgCount = decoder.readUInt1();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeAddress(source);
        encoder.writeUInt1(msgCount);
    }
    
    @Override
    protected int getMandatorySize() {
        int length = 1;
        length += sizeOf(source);
        return length;
    }
}
