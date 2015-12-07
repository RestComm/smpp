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

import org.testng.annotations.Test;

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.message.DeliverSM;

@Test
public class DeliverSMTest extends PacketTests<DeliverSM> {

    protected Class<DeliverSM> getPacketType() {
        return DeliverSM.class;
    }
    
    @Override
    protected DeliverSM getInitialisedPacket() {
        DeliverSM packet = new DeliverSM();
        packet.setDataCoding(0);
        packet.setDefaultMsg(7);
        packet.setDestination(new Address(0, 0, "27934876984"));
        packet.setEsmClass(10);
        packet.setPriority(1);
        packet.setProtocolID(1);
        packet.setRegistered(1);
        packet.setReplaceIfPresent(1);
        packet.setServiceType("serviceType");
        packet.setSource(new Address(1, 1, "242357987876"));
        packet.setMessage(new byte[] {0x1, 0x2, 0x3, 0x4, 0x5, 0x6});
        return packet;
    }
}
