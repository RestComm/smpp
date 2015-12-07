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
import org.mobicents.protocols.smpp.Npi;
import org.mobicents.protocols.smpp.Ton;
import org.mobicents.protocols.smpp.message.BroadcastSM;
import org.mobicents.protocols.smpp.util.SMPPDate;

@Test
public class BroadcastSMTest extends PacketTests<BroadcastSM> {

    protected Class<BroadcastSM> getPacketType() {
        return BroadcastSM.class;
    }

    @Override
    protected BroadcastSM getInitialisedPacket() {
        Calendar calendar = Calendar.getInstance();
        BroadcastSM packet = new BroadcastSM();
        packet.setDataCoding(3);
        packet.setDefaultMsg(5);
        packet.setDeliveryTime(SMPPDate.getAbsoluteInstance(calendar));
        packet.setExpiryTime(SMPPDate.getAbsoluteInstance(calendar));
        packet.setMessageId("messageId");
        packet.setPriority(1);
        packet.setReplaceIfPresent(1);
        packet.setServiceType("serviceType");
        packet.setSource(new Address(Ton.UNKNOWN, Npi.UNKNOWN, "54321"));
        return packet;
    }
}
