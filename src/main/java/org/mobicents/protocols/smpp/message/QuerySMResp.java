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

import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;
import org.mobicents.protocols.smpp.util.SMPPDate;
import org.mobicents.protocols.smpp.version.SMPPVersion;

/**
 * SMSC response to a QuerySM request. Contains the current state of a short
 * message at the SMSC.
 * 
 * @version $Id: QuerySMResp.java 457 2009-01-15 17:37:42Z orank $
 */
public class QuerySMResp extends SMPPPacket {
    private static final long serialVersionUID = 2L;
    
    private String messageId;
    private SMPPDate finalDate;
    private MessageState messageState = MessageState.UNKNOWN;
    private int errorCode;
    
    /**
     * Construct a new QuerySMResp.
     */
    public QuerySMResp() {
        super(CommandId.QUERY_SM_RESP);
    }

    /**
     * Create a new QuerySMResp packet in response to a BindReceiver. This
     * constructor will set the sequence number to it's expected value.
     * 
     * @param request
     *            The Request packet the response is to
     */
    public QuerySMResp(SMPPPacket request) {
        super(request);
    }
    
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public SMPPDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(SMPPDate finalDate) {
        this.finalDate = finalDate;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public MessageState getMessageState() {
        return messageState;
    }

    public void setMessageState(MessageState messageState) {
        this.messageState = messageState;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            QuerySMResp other = (QuerySMResp) obj;
            equals |= safeCompare(messageId, other.messageId);
            equals |= safeCompare(finalDate, other.finalDate);
            equals |= messageState == other.messageState;
            equals |= errorCode == other.errorCode;
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (messageId != null) ? messageId.hashCode() : 0;
        hc += (finalDate != null) ? finalDate.hashCode() : 0;
        hc += Integer.valueOf(messageState.getValue()).hashCode();
        hc += Integer.valueOf(errorCode).hashCode();
        return hc;
    }

    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("messageId=").append(messageId)
        .append(",finalDate=").append(finalDate)
        .append(",messageState=").append(messageState)
        .append(",errorCode=").append(errorCode);
    }

    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateMessageId(messageId);
        smppVersion.validateErrorCode(errorCode);
    }
    
    @Override
    protected void readMandatory(PacketDecoder decoder) {
        messageId = decoder.readCString();
        finalDate = decoder.readDate();
        messageState = MessageState.getMessageState(decoder.readUInt1());
        errorCode = decoder.readUInt1();
    }

    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(messageId);
        encoder.writeDate(finalDate);
        encoder.writeUInt1(messageState.getValue());
        encoder.writeUInt1(errorCode);
    }
    
    @Override
    protected int getMandatorySize() {
        int length = 3;
        length += sizeOf(messageId);
        length += sizeOf(finalDate);
        return length;
    }
}
