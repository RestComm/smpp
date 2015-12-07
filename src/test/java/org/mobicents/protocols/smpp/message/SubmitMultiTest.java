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
import org.mobicents.protocols.smpp.message.SubmitMulti;
import org.mobicents.protocols.smpp.util.SMPPDate;

@Test
public class SubmitMultiTest extends PacketTests<SubmitMulti> {

    protected Class<SubmitMulti> getPacketType() {
        return SubmitMulti.class;
    }
    
    @Override
    protected SubmitMulti getInitialisedPacket() {
        Calendar calendar = Calendar.getInstance();
        SubmitMulti packet = new SubmitMulti();
        packet.setDataCoding(1);
        packet.setDefaultMsg(2);
        packet.setDeliveryTime(SMPPDate.getAbsoluteInstance(calendar));
        packet.setEsmClass(2);
        packet.setExpiryTime(SMPPDate.getAbsoluteInstance(calendar));
        packet.setMessage(new byte[] {9, 8, 7, 6, 5, 4, 3, 2, 1});
        packet.setPriority(1);
        packet.setProtocolID(2);
        packet.setRegistered(1);
        packet.setReplaceIfPresent(1);
        packet.setServiceType("serviceType");
        packet.setSource(new Address(1, 2, "345678"));
        packet.addDestination(new Address(1, 1, "11111"));
        packet.addDestination(new Address(2, 2, "22222"));
        packet.addDestination("distList1");
        packet.addDestination("distList2");
        return packet;
    }
}
