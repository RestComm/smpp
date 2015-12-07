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

import java.util.Calendar;

import org.testng.annotations.Test;

import org.mobicents.protocols.smpp.message.MessageState;
import org.mobicents.protocols.smpp.message.QuerySMResp;
import org.mobicents.protocols.smpp.util.SMPPDate;

@Test
public class QuerySMRespTest extends PacketTests<QuerySMResp> {

    protected Class<QuerySMResp> getPacketType() {
        return QuerySMResp.class;
    }
    
    @Override
    protected QuerySMResp getInitialisedPacket() {
        Calendar calendar = Calendar.getInstance();
        QuerySMResp packet = new QuerySMResp();
        packet.setErrorCode(2);
        packet.setFinalDate(SMPPDate.getAbsoluteInstance(calendar));
        packet.setMessageId("messageId");
        packet.setMessageState(MessageState.DELIVERED);
        return packet;
    }
}
