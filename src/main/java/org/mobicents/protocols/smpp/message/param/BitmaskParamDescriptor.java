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

import java.io.IOException;
import java.util.BitSet;

import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;

/**
 * Encode and decode bit masks. While this descriptor type supports bit masks
 * that are longer than a single octet, the SMPP specification does not define
 * how a multi-byte bit mask should be encoded on the wire. Since the rest
 * of SMPP is big-endian, this implementation assumes big-endian for bit masks
 * too.
 * @version $Id: BitmaskParamDescriptor.java 457 2009-01-15 17:37:42Z orank $
 */
public class BitmaskParamDescriptor extends AbstractDescriptor {
    private static final long serialVersionUID = 2L;
    
    public BitmaskParamDescriptor() {
    }
    
    public int getLengthSpecifier() {
        return -1;
    }

    public int sizeOf(Object obj) {
        return 1;
    }

    public void writeObject(Object obj, PacketEncoder encoder) throws IOException {
        encoder.writeUInt1(bitsetToInt((BitSet) obj));
    }

    public Object readObject(PacketDecoder decoder, int length) {
        int bits = decoder.readUInt1();
        BitSet bitset = new BitSet();
        for (int i = 0; i < 8; i++) {
            if ((bits & (1 << i)) != 0) {
                bitset.set(i);
            }
        }
        return bitset;
    }
    
    private int bitsetToInt(BitSet bitSet) {
        int value = 0;
        for (int i = bitSet.nextSetBit(0); i >= 0 && i < 8; i = bitSet.nextSetBit(i + 1)) {
            int bit = i % 8;
            value |= 1 << bit;
        }
        return value;
    }
}
