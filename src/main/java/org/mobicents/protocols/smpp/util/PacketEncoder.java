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
 * Interface specification for a packet encoder.
 * @version $Id: PacketEncoder.java 457 2009-01-15 17:37:42Z orank $
 */
public interface PacketEncoder {
    
    /**
     * Set the output stream this encoder is writing to.
     * @param out The output stream to write to.
     * @return This packet encoder.
     */
    PacketEncoder setStream(OutputStream out);
    
    /**
     * Get the stream this encoder is writing to.
     * @return The stream this encoder is writing to.
     */
    OutputStream getStream();
    
    /**
     * Write a C-String (one that is terminated with a nul byte) to the
     * output stream. The characters will be encoded using US-ASCII.
     * @param value The string value to write. If <code>value</code> is
     * <code>null</code> a single nul-byte will still be written.
     * @return This packet encoder.
     * @throws IOException If a problem occurs writing to the stream.
     */
    PacketEncoder writeCString(String value) throws IOException;

    /**
     * Write a string to the output stream. The characters will be encoded
     * using US-ASCII.
     * @param value The string value to write.
     * @param length The number of characters to write.
     * @return This packet encoder.
     * @throws IOException If a problem occurs writing to the stream.
     * @throws IndexOutOfBoundsException If <code>length</code> is
     * longer than the number of characters in <code>value</code>.
     */
    PacketEncoder writeString(String value, int length) throws IOException;
    
    /**
     * Write a 1-byte unsigned integer to the output stream.
     * @param value The integer value to send.
     * @return This packet encoder.
     * @throws IOException If a problem occurs writing to the stream.
     */
    PacketEncoder writeUInt1(int value) throws IOException;

    /**
     * Write a 2-byte unsigned integer to the output stream in big-endian
     * order.
     * @param value The integer value to send.
     * @return This packet encoder.
     * @throws IOException If a problem occurs writing to the stream.
     */
    PacketEncoder writeUInt2(int value) throws IOException;

    /**
     * Write a 4-byte unsigned integer to the output stream in big-endian
     * order.
     * @param value The integer value to send.
     * @return This packet encoder.
     * @throws IOException If a problem occurs writing to the stream.
     */
    PacketEncoder writeUInt4(long value) throws IOException;

    /**
     * Write a 4-byte integer to the output stream in big-endian
     * order.
     * @param value The integer value to send.
     * @return This packet encoder.
     * @throws IOException If a problem occurs writing to the stream.
     */
    PacketEncoder writeInt4(int value) throws IOException;

    /**
     * Write an 8-byte integer to the output stream in big-endian
     * order.
     * @param value The integer value to send.
     * @return This packet encoder.
     * @throws IOException If a problem occurs writing to the stream.
     */
    PacketEncoder writeInt8(long value) throws IOException;

    /**
     * Write an SMPP address to the output stream.
     * @param address The address to write to the stream.
     * @return This packet encoder.
     * @throws IOException If a problem occurs writing to the stream.
     */
    PacketEncoder writeAddress(Address address) throws IOException;

    /**
     * Write an SMPP error address to the output stream.
     * @param address The error address to write.
     * @return This packet encoder.
     * @throws IOException If a problem occurs while writing.
     */
    PacketEncoder writeErrorAddress(ErrorAddress errorAddress) throws IOException;
    
    /**
     * Write an SMPP date to the output stream.
     * @param date The SMPP date to write to the stream.
     * @return This packet encoder.
     * @throws IOException If a problem occurs writing to the stream.
     */
    PacketEncoder writeDate(SMPPDate date) throws IOException;
    
    PacketEncoder writeBytes(byte[] array) throws IOException;
    
    /**
     * Write a byte array to the output stream.
     * @param array The byte array to write bytes from.
     * @param offset The offset to begin copying bytes from.
     * @param length The number of bytes to write.
     * @return This packet encoder.
     * @throws IOException If a problem occurs writing to the stream.
     * @throws IndexOutOfBoundsException if there are insufficient bytes
     * in the array to satisfy the <code>length</code> parameter.
     */
    PacketEncoder writeBytes(byte[] array, int offset, int length) throws IOException;
}
