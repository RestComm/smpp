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

package org.mobicents.protocols.smpp.message.param;

import static org.testng.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.BitSet;

import org.testng.annotations.Test;

import org.mobicents.protocols.smpp.message.param.BitmaskParamDescriptor;
import org.mobicents.protocols.smpp.message.param.ParamDescriptor;
import org.mobicents.protocols.smpp.util.PacketDecoderImpl;
import org.mobicents.protocols.smpp.util.PacketEncoderImpl;

@Test
public class BitmaskParamDescriptorTest {
    
    private ParamDescriptor descriptor = new BitmaskParamDescriptor();

    public void testWriteObjectWithNoBitsSet() throws Exception {
        BitSet bitset = new BitSet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PacketEncoderImpl encoder = new PacketEncoderImpl(out);
        descriptor.writeObject(bitset, encoder);
        byte[] actual = out.toByteArray();
        assertEquals(actual.length, 1);
        assertEquals(actual, new byte[] { 0 });
    }
    
    public void testWriteObject() throws Exception {
        BitSet bitset = new BitSet();
        bitset.set(3);
        bitset.set(4);
        bitset.set(6);
        bitset.set(7);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PacketEncoderImpl encoder = new PacketEncoderImpl(out);
        descriptor.writeObject(bitset, encoder);
        byte[] actual = out.toByteArray();
        assertEquals(actual.length, 1);
        assertEquals(actual, new byte[] { (byte) 0xd8 });
    }
    
    public void testReadObjectWithNoBitsSet() throws Exception {
        PacketDecoderImpl decoder = new PacketDecoderImpl(new byte[] {0});
        BitSet bitset = (BitSet) descriptor.readObject(decoder, 1);
        assertEquals(bitset.length(), 0);
        assertEquals(bitset.nextSetBit(0), -1);
        assertEquals(decoder.getParsePosition(), 1);
    }
    
    public void testReadObjectWithSize1() throws Exception {
        // We're reading from an offset here, the first byte should be ignored.
        PacketDecoderImpl decoder =
            new PacketDecoderImpl(new byte[] {0, 0x7f, 0x7f, 0x77}, 3);
        BitSet bitset = (BitSet) descriptor.readObject(decoder, 1);
        assertEquals(bitset.nextSetBit(0), 0);
        assertEquals(bitset.nextSetBit(1), 1);
        assertEquals(bitset.nextSetBit(2), 2);
        assertEquals(bitset.nextSetBit(3), 4);
        assertEquals(bitset.nextSetBit(5), 5);
        assertEquals(bitset.nextSetBit(6), 6);
        assertEquals(bitset.nextSetBit(7), -1);
        assertEquals(decoder.getParsePosition(), 4);
    }
}
