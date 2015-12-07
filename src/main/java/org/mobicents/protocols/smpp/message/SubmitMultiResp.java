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
import java.util.ListIterator;

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.ErrorAddress;
import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;
import org.mobicents.protocols.smpp.version.SMPPVersion;

/**
 * Submit to multiple destinations response.
 * 
 * @version $Id: SubmitMultiResp.java 457 2009-01-15 17:37:42Z orank $
 */
public class SubmitMultiResp extends SMPPPacket {
    private static final long serialVersionUID = 2L;
    
    private String messageId;
    
    /** Table of unsuccessful destinations */
    private List<ErrorAddress> unsuccessfulTable = new ArrayList<ErrorAddress>();

    /**
     * Construct a new Unbind.
     */
    public SubmitMultiResp() {
        super(CommandId.SUBMIT_MULTI_RESP);
    }

    /**
     * Create a new SubmitMultiResp packet in response to a BindReceiver. This
     * constructor will set the sequence number to it's expected value.
     * 
     * @param request
     *            The Request packet the response is to
     */
    public SubmitMultiResp(SMPPPacket request) {
        super(request);
    }

    
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /** Get the number of destinations the message was not delivered to. */
    public int getUnsuccessfulCount() {
        return unsuccessfulTable.size();
    }

    /**
     * Add a destination address to the table of unsuccessful destinations.
     * 
     * @param ea
     *            ErrorAddress object representing the failed destination
     * @return The current count of unsuccessful destinations (including the new
     *         one)
     */
    public int add(ErrorAddress ea) {
        unsuccessfulTable.add(ea);
        return unsuccessfulTable.size();
    }

    /**
     * Remove an address from the table of unsuccessful destinations.
     * 
     * @param a
     *            the address to remove.
     * @return the size of the table after removal.
     */
    public int remove(Address a) {
        synchronized (unsuccessfulTable) {
            int i = unsuccessfulTable.indexOf(a);
            if (i > -1) {
                unsuccessfulTable.remove(i);
            }

            return unsuccessfulTable.size();
        }
    }

    /**
     * Get an iterator to iterate over the set of addresses in the unsuccessful
     * destination table.
     */
    public ListIterator<ErrorAddress> tableIterator() {
        return unsuccessfulTable.listIterator();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            SubmitMultiResp other = (SubmitMultiResp) obj;
            equals |= safeCompare(messageId, other.messageId);
            equals |= safeCompare(unsuccessfulTable, other.unsuccessfulTable);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (messageId != null) ? messageId.hashCode() : 0;
        hc += (unsuccessfulTable != null) ? unsuccessfulTable.hashCode() : 0;
        return hc;
    }

    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("messageId=").append(messageId)
        .append(",unsuccessfulCount=").append(unsuccessfulTable.size())
        .append(",unsuccessful=").append(unsuccessfulTable);
    }

    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateMessageId(messageId);
        smppVersion.validateNumUnsuccessful(unsuccessfulTable.size());
    }

    @Override
    protected void readMandatory(PacketDecoder decoder) {
        messageId = decoder.readCString();
        int count = decoder.readUInt1();
        unsuccessfulTable = new ArrayList<ErrorAddress>();
        for (int i = 0; i < count; i++) {
            unsuccessfulTable.add(decoder.readErrorAddress());
        }
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(messageId);
        encoder.writeUInt1(unsuccessfulTable.size());
        for (ErrorAddress errorAddress : unsuccessfulTable) {
            encoder.writeErrorAddress(errorAddress);
        }
    }
    
    @Override
    protected int getMandatorySize() {
        int length = 2;
        length += sizeOf(messageId);
        for (ErrorAddress ea : unsuccessfulTable) {
            length += ea.getLength();
        }
        return length;
    }
}
