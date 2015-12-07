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
import org.mobicents.protocols.smpp.message.SubmitSM;
import org.mobicents.protocols.smpp.util.SMPPDate;

@Test
public class SubmitSMTest extends PacketTests<SubmitSM> {

    protected Class<SubmitSM> getPacketType() {
        return SubmitSM.class;
    }
    
    @Override
    protected SubmitSM getInitialisedPacket() {
        Calendar calendar = Calendar.getInstance();
        SubmitSM packet = new SubmitSM();
        packet.setDataCoding(1);
        packet.setDefaultMsg(2);
        packet.setDeliveryTime(SMPPDate.getAbsoluteInstance(calendar));
        packet.setDestination(new Address(0, 0, "8748746987"));
        packet.setEsmClass(2);
        packet.setExpiryTime(SMPPDate.getAbsoluteInstance(calendar));
        packet.setPriority(1);
        packet.setProtocolID(1);
        packet.setRegistered(1);
        packet.setReplaceIfPresent(1);
        packet.setServiceType("serviceType");
        packet.setSource(new Address(2, 2, "32873487"));
        packet.setMessage(new byte[] {1, 2, 3, 4, 5, 6});
        return packet;
    }
}
