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

package org.mobicents.protocols.smpp.util;

import java.io.IOException;
import java.io.OutputStream;

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.ErrorAddress;

/**
 * Implementation of the {@link PacketEncoder} interface.
 * @version $Id: PacketEncoderImpl.java 457 2009-01-15 17:37:42Z orank $
 *
 */
public class PacketEncoderImpl implements PacketEncoder {

    private static final SMPPDateFormat DATE_FORMAT = new SMPPDateFormat();
    private OutputStream out;
    
    public PacketEncoderImpl() {
    }
    
    public PacketEncoderImpl(OutputStream stream) {
        this.out = stream;
    }
    
    public PacketEncoder setStream(OutputStream out) {
        this.out = out;
        return this;
    }
    
    public OutputStream getStream() {
        return out;
    }
    
    public PacketEncoder writeCString(String value) throws IOException {
        if (value != null) {
            out.write(value.getBytes("US-ASCII"));
        }
        out.write(0);
        return this;
    }

    public PacketEncoder writeString(String value, int length) throws IOException {
        if (length > value.length()) {
            throw new IndexOutOfBoundsException(String.valueOf(length));
        }
        byte[] array = value.getBytes("US-ASCII");
        out.write(array, 0, length);
        return this;
    }

    public PacketEncoder writeUInt1(int value) throws IOException {
        out.write(value);
        return this;
    }

    public PacketEncoder writeUInt2(int value) throws IOException {
        out.write(value >>> 8);
        out.write(value);
        return this;
    }

    public PacketEncoder writeUInt4(long value) throws IOException {
        out.write((int) (value >>> 24));
        out.write((int) (value >>> 16));
        out.write((int) (value >>> 8));
        out.write((int) value);
        return this;
    }

    public PacketEncoder writeInt4(int value) throws IOException {
        out.write(value >>> 24);
        out.write(value >>> 16);
        out.write(value >>> 8);
        out.write(value);
        return this;
    }

    public PacketEncoder writeInt8(long value) throws IOException {
        out.write((int) (value >>> 56));
        out.write((int) (value >>> 48));
        out.write((int) (value >>> 40));
        out.write((int) (value >>> 32));
        out.write((int) (value >>> 24));
        out.write((int) (value >>> 16));
        out.write((int) (value >>> 8));
        out.write((int) value);
        return this;
    }

    public PacketEncoder writeAddress(Address address) throws IOException {
        if (address != null) {
            address.writeTo(this);
        } else {
            new Address().writeTo(this);
        }
        return this;
    }

    public PacketEncoder writeErrorAddress(ErrorAddress errorAddress) throws IOException {
        if (errorAddress != null) {
            errorAddress.writeTo(this);
        } else {
            new ErrorAddress().writeTo(this);
        }
        return this;
    }
    
    public PacketEncoder writeDate(SMPPDate date) throws IOException {
        String str = DATE_FORMAT.format(date);
        writeCString(str);
        return this;
    }
    
    public PacketEncoder writeBytes(byte[] bytes) throws IOException {
        return writeBytes(bytes, 0, bytes.length);
    }
    
    public PacketEncoder writeBytes(byte[] bytes, int offset, int length) throws IOException {
        if (bytes != null) {
            out.write(bytes, offset, length);
        } else {
            if (length != 0) {
                throw new IndexOutOfBoundsException(Integer.toString(offset));
            }
        }
        return this;
    }
}
