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
import java.util.ArrayList;
import java.util.List;

import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;

/**
 * SMSC response to a QueryLastMsgs request.
 * @version $Id: QueryLastMsgsResp.java 457 2009-01-15 17:37:42Z orank $
 */
public class QueryLastMsgsResp extends SMPPPacket {
    private static final long serialVersionUID = 2L;
    private static final int MAX_SIZE = 255;
    
    /** The table of messages returned */
    private List<String> messageTable = new ArrayList<String>();

    /**
     * Construct a new QueryLastMsgsResp.
     */
    public QueryLastMsgsResp() {
        super(CommandId.QUERY_LAST_MSGS_RESP);
    }

    /**
     * Create a new QueryLastMsgsResp packet in response to a BindReceiver. This
     * constructor will set the sequence number to it's expected value.
     * 
     * @param request
     *            The Request packet the response is to
     */
    public QueryLastMsgsResp(SMPPPacket request) {
        super(request);
    }

    /**
     * Add a message Id to the response packet. A maximum of 255 message IDs
     * can be added, since the size specifier for the encoded packet is only
     * one byte. If an attempt is made to add more than 255 message IDs, this
     * method will fail silently.
     * @param id
     *            The message Id to add to the packet.
     * @return The current number of message Ids (including the new one).
     */
    public int addMessageId(String id) {
        if (messageTable.size() < MAX_SIZE) {
            messageTable.add(id);
        }
        return messageTable.size();
    }

    /** Get the number of message Ids. */
    public int getMsgCount() {
        return messageTable.size();
    }

    /**
     * Get a String array of the message Ids.
     * @return A String array of all the message Ids. Will never return
     * <code>null</code>, if the table is empty a zero-length array will be
     * returned.
     */
    public String[] getMessageIds() {
        return (String[]) messageTable.toArray(new String[0]);
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            QueryLastMsgsResp other = (QueryLastMsgsResp) obj;
            equals |= safeCompare(messageTable, other.messageTable);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (messageTable != null) ? messageTable.hashCode() : 71;
        return hc;
    }

    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("msgCount=").append(messageTable.size())
        .append(",messageIds=").append(messageTable);
    }

    @Override
    protected void readMandatory(PacketDecoder decoder) {
        messageTable = new ArrayList<String>();
        int count = decoder.readUInt1();
        for (int i = 0; i < count; i++) {
            messageTable.add(decoder.readCString());
        }
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        int count = messageTable.size();
        encoder.writeUInt1(count);
        for (String s : messageTable) {
            encoder.writeCString(s);
        }
    }
    
    @Override
    protected int getMandatorySize() {
        int length = 1;
        for (String s : messageTable) {
            length += (1 + sizeOf(s));
        }
        return length;
    }
}
