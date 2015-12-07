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
import org.mobicents.protocols.smpp.message.DataSM;

@Test
public class DataSMTest extends PacketTests<DataSM> {

    protected Class<DataSM> getPacketType() {
        return DataSM.class;
    }
    
    @Override
    protected DataSM getInitialisedPacket() {
        DataSM packet = new DataSM();
        packet.setDataCoding(4);
        packet.setDestination(new Address(2, 2, "22222222"));
        packet.setEsmClass(32);
        packet.setServiceType("serviceType");
        packet.setSource(new Address(1, 1, "111111111111"));
        packet.setRegistered(0);
        return packet;
    }
}
