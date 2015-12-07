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

import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;

/**
 * Mandatory parameter descriptor for an integer.
 * @version $Id: IntegerParamDescriptor.java 457 2009-01-15 17:37:42Z orank $
 */
public class IntegerParamDescriptor extends AbstractDescriptor {
    private static final long serialVersionUID = 2L;

    /**
     * Number of bytes to read for the integer. Default is 1.
     */
    private int length;
    
    public IntegerParamDescriptor(int length) {
        this.length = length;
    }
    
    public int getLengthSpecifier() {
        return -1;
    }
    
    public int sizeOf(Object obj) {
        return length;
    }

    public void writeObject(Object obj, PacketEncoder encoder) throws IOException {
        if (!(obj instanceof Number)) {
            throw new IllegalArgumentException("Invalid object type.");
        }
        long value = ((Number) obj).longValue();
        switch (length) {
        case 8:
            encoder.writeInt8(value);
            break;
        case 4:
            encoder.writeUInt4(value);
            break;
        case 2:
            encoder.writeUInt2((int) value);
            break;
        default:
            encoder.writeUInt1((int) value);
            break;
        }
    }

    public Object readObject(PacketDecoder decoder, int length) {
        Number value;
        switch (this.length) {
        case 8:
            value = new Long(decoder.readInt8());
            break;
        case 4:
            value = new Long(decoder.readUInt4());
            break;
        case 2:
            value = new Integer(decoder.readUInt2());
            break;
        default:
            value = new Integer(decoder.readUInt1());
            break;
        }
        return value;
    }
}
