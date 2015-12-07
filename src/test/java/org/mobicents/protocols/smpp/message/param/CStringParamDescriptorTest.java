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

import org.testng.annotations.Test;

import org.mobicents.protocols.smpp.message.param.CStringParamDescriptor;
import org.mobicents.protocols.smpp.util.PacketDecoderImpl;
import org.mobicents.protocols.smpp.util.PacketEncoderImpl;

@Test
public class CStringParamDescriptorTest {

    private final String testString = new String("A test string");
    private CStringParamDescriptor descriptor = new CStringParamDescriptor();
    
    public void testSizeOf() {
        assertEquals(descriptor.sizeOf(null), 1);
        assertEquals(descriptor.sizeOf(""), 1);
        assertEquals(descriptor.sizeOf(testString), testString.length() + 1);
    }
    
    public void testWriteNullString() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PacketEncoderImpl encoder = new PacketEncoderImpl(out);
        descriptor.writeObject(null, encoder);
        byte[] array = out.toByteArray();
        assertEquals(array.length, 1);
        assertEquals(array[0], (byte) 0);
    }
    
    public void testWriteEmptyString() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PacketEncoderImpl encoder = new PacketEncoderImpl(out);
        descriptor.writeObject("", encoder);
        byte[] array = out.toByteArray();
        assertEquals(array.length, 1);
        assertEquals(array[0], (byte) 0);
    }

    public void testWriteString() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PacketEncoderImpl encoder = new PacketEncoderImpl(out);
        descriptor.writeObject(testString, encoder);
        byte[] array = out.toByteArray();
        assertEquals(array.length, testString.length() + 1);
        assertEquals(array[array.length - 1], (byte) 0);
        assertEquals(new String(array, 0, array.length - 1, "US-ASCII"), testString);
    }

    public void testReadEmptyString() throws Exception {
        PacketDecoderImpl decoder = new PacketDecoderImpl(new byte[] {0});
        String string = (String) descriptor.readObject(decoder, 0);
        assertEquals(string, "");
    }

    public void testReadString() throws Exception {
        byte[] array = new byte[testString.length() + 1];
        System.arraycopy(testString.getBytes("US-ASCII"), 0, array, 0, testString.length());
        array[array.length - 1] = (byte) 0;
        PacketDecoderImpl decoder = new PacketDecoderImpl(array);
        String string = (String) descriptor.readObject(decoder, 0);
        assertEquals(string, testString);
        assertEquals(decoder.getParsePosition(), testString.length() + 1);
    }
}
