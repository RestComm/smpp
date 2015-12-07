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
import org.mobicents.protocols.smpp.util.SMPPDate;
import org.mobicents.protocols.smpp.util.SMPPDateFormat;

public class DateParamDescriptor extends AbstractDescriptor {
    private static final long serialVersionUID = 2L;
    private static final SMPPDateFormat DATE_FORMAT = new SMPPDateFormat();
    
    public int getLengthSpecifier() {
        return -1;
    }
    
    public int sizeOf(Object obj) {
        if (obj != null) {
            String str = DATE_FORMAT.format((SMPPDate) obj);
            return str.length() + 1;
        } else {
            return 1;
        }
    }

    public void writeObject(Object obj, PacketEncoder encoder) throws IOException {
        encoder.writeDate((SMPPDate) obj);
    }

    public Object readObject(PacketDecoder decoder, int length) {
        return decoder.readDate();
    }
}
