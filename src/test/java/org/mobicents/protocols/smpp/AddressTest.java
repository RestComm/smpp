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

package org.mobicents.protocols.smpp;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.testng.annotations.Test;

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.Npi;
import org.mobicents.protocols.smpp.Ton;
import org.mobicents.protocols.smpp.util.PacketDecoderImpl;
import org.mobicents.protocols.smpp.util.PacketEncoderImpl;

@Test
public class AddressTest {
    private void testSize(Address addr) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PacketEncoderImpl encoder = new PacketEncoderImpl(out);
        try {
            addr.writeTo(encoder);
        } catch (IOException x) {
            fail("Serializing address caused I/O Exception:\n" + x.toString());
            return;
        }
        byte[] array = out.toByteArray();
        PacketDecoderImpl decoder = new PacketDecoderImpl(array);
        Address deserialized = decoder.readAddress();
        assertEquals(decoder.getParsePosition(), array.length);
        assertEquals(addr.getLength(), array.length, "serialized. ");
        assertEquals(array.length, deserialized.getLength(), "deserialized.");
    }

    public void testEmptyFieldSize() {
        testSize(new Address());
    }

    public void testFilledFieldSize() {
        Address addr = new Address();
        addr.setTON(Ton.INTERNATIONAL);
        addr.setNPI(Npi.ISDN);
        addr.setAddress("353851234567");
        testSize(addr);
    }

    public void testEquals() {
        Address a1 = new Address(Ton.NETWORK,
                Npi.NATIONAL, "353851234567");
        Address a2 = new Address(Ton.NETWORK,
                Npi.NATIONAL, "353851234567");
        Address a3 = new Address(Ton.NATIONAL,
                Npi.NATIONAL, "441237654321");

        assertEquals(a2, a1);
        assertTrue(!(a1.equals(a3)));
    }
}

