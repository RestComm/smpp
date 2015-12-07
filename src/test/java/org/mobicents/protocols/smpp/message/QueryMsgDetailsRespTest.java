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

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.message.MessageState;
import org.mobicents.protocols.smpp.message.QueryMsgDetailsResp;
import org.mobicents.protocols.smpp.util.SMPPDate;

@Test
public class QueryMsgDetailsRespTest extends PacketTests<QueryMsgDetailsResp> {

    protected Class<QueryMsgDetailsResp> getPacketType() {
        return QueryMsgDetailsResp.class;
    }
    
    @Override
    protected QueryMsgDetailsResp getInitialisedPacket() {
        Calendar calendar = Calendar.getInstance();
        QueryMsgDetailsResp packet = new QueryMsgDetailsResp();
        packet.setDataCoding(2);
        packet.setDeliveryTime(SMPPDate.getAbsoluteInstance(calendar));
        packet.setErrorCode(4);
        packet.setExpiryTime(SMPPDate.getAbsoluteInstance(calendar));
        packet.setFinalDate(SMPPDate.getAbsoluteInstance(calendar));
        packet.setMessage(new byte[] {5, 4, 3, 2, 1});
        packet.setMessageId("messageId");
        packet.setMessageStatus(MessageState.EN_ROUTE);
        packet.setPriority(1);
        packet.setProtocolID(1);
        packet.setRegistered(1);
        packet.setServiceType("serviceType");
        packet.setSource(new Address(6, 6, "55555555"));
        packet.addDestination(new Address(1, 1, "11111111"));
        packet.addDestination(new Address(2, 2, "22222222"));
        packet.addDestination(new Address(3, 3, "33333333"));
        packet.addDestination("distList1");
        packet.addDestination("distList2");
        return packet;
    }
}
