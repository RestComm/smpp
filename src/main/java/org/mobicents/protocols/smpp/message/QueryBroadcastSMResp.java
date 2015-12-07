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

import org.mobicents.protocols.smpp.message.tlv.TLVTable;
import org.mobicents.protocols.smpp.message.tlv.Tag;
import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;
import org.mobicents.protocols.smpp.version.SMPPVersion;

/**
 * QueryBroadcastSM response packet.
 * @version $Id: QueryBroadcastSMResp.java 457 2009-01-15 17:37:42Z orank $
 * @since 0.4.0
 */
public class QueryBroadcastSMResp extends SMPPPacket {
    private static final long serialVersionUID = 2L;
    
    private String messageId;
    
    public QueryBroadcastSMResp() {
        super (CommandId.QUERY_BROADCAST_SM_RESP);
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            QueryBroadcastSMResp other = (QueryBroadcastSMResp) obj;
            equals |= safeCompare(messageId, other.messageId);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (messageId != null) ? messageId.hashCode() : 0;
        return hc;
    }

    @Override
    protected void readMandatory(PacketDecoder decoder) {
        messageId = decoder.readCString();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(messageId);
    }
    
    @Override
    protected boolean validateTLVTable(SMPPVersion smppVersion) {
        boolean valid = true;
        TLVTable tlvTable = getTLVTable();
        valid &= tlvTable.containsKey(Tag.MESSAGE_STATE);
        valid &= tlvTable.containsKey(Tag.BROADCAST_AREA_IDENTIFIER);
        valid &= tlvTable.containsKey(Tag.BROADCAST_AREA_SUCCESS);
        return valid;
    }
    
    @Override
    protected int getMandatorySize() {
        return 1 + sizeOf(messageId);
    }
}
