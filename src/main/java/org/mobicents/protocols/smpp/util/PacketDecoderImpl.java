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

import java.text.ParseException;

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.ErrorAddress;
import org.mobicents.protocols.smpp.message.SMPPProtocolException;

/**
 * Implementation of the packet decoder.
 * @version $Id: PacketDecoderImpl.java 457 2009-01-15 17:37:42Z orank $
 */
public class PacketDecoderImpl implements PacketDecoder {

    private static final SMPPDateFormat DATE_FORMAT = new SMPPDateFormat();
    private byte[] bytes;
    private int pos;
    
    public PacketDecoderImpl() {
    }

    public PacketDecoderImpl(byte[] bytes) {
        this.bytes = bytes;
    }

    public PacketDecoderImpl(byte[] bytes, int parsePosition) {
        this.bytes = bytes;
        this.pos = parsePosition;
    }
    
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getParsePosition() {
        return pos;
    }

    public void setParsePosition(int pos) {
        this.pos = pos;
    }

    public int getAvailableBytes() {
        return bytes.length - pos;
    }
    
    public byte readByte() {
        int index = pos;
        pos++;
        return bytes[index];
    }

    public String readCString() {
        String str = SMPPIO.readCString(bytes, pos);
        pos += str.length() + 1;
        return str;
    }

    public String readString(int length) {
        String str = SMPPIO.readString(bytes, pos, length);
        pos += str.length();
        return str;
    }

    public int readUInt1() {
        int value = SMPPIO.readUInt1(bytes, pos);
        pos++;
        return value;
    }

    public int readUInt2() {
        int value = SMPPIO.readUInt2(bytes, pos);
        pos += 2;
        return value;
    }

    public long readUInt4() {
        long value = SMPPIO.readUInt4(bytes, pos);
        pos += 4;
        return value;
    }

    public long readInt8() {
        long value = SMPPIO.readInt8(bytes, pos);
        pos += 8;
        return value;
    }
    
    public Address readAddress() {
        Address address = new Address();
        address.readFrom(this);
        return address;
    }
    
    public ErrorAddress readErrorAddress() {
        ErrorAddress errorAddress = new ErrorAddress();
        errorAddress.readFrom(this);
        return errorAddress;
    }
    
    public SMPPDate readDate() {
        SMPPDate date = null;
        String str = null;
        try {
            str = readCString();
            if (str.length() > 0) {
                date = (SMPPDate) DATE_FORMAT.parseObject(str);
            }
        } catch (ParseException x) {
            throw new SMPPProtocolException("Cannot parse date value: " + str, x);
        }
        return date;
    }
    
    public byte[] readBytes(int length) {
        int startIndex = pos;
        if (startIndex + length > bytes.length) {
            throw new ArrayIndexOutOfBoundsException(startIndex + length);
        }
        byte[] copy = new byte[length];
        System.arraycopy(bytes, startIndex, copy, 0, length);
        pos += length;
        return copy;
    }
}
