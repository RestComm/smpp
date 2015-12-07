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

package org.mobicents.protocols.smpp.gsm.ems;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.mobicents.protocols.smpp.util.ImageUtils;

/**
 * A black and white bitmap, as defined by
 * 3GPP TS 23.040 Annex E.
 * @version $Id: BlackAndWhiteBitmapEO.java 484 2010-02-08 16:08:50Z orank $
 */
public class BlackAndWhiteBitmapEO extends ExtendedObject {

    public BlackAndWhiteBitmapEO(int referenceNum, BufferedImage image) {
        super(2, referenceNum);
        setData(createData(image));
    }
    
    private byte[] createData(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int bytesWidth = (width / 8) + 1;
        if (width % 8 == 0) {
            bytesWidth--;
        }
        ByteBuffer buffer = ByteBuffer.allocate((height * bytesWidth) + 2);
        buffer.put((byte) width);
        buffer.put((byte) height);
        ImageUtils.imageToBwBitmap(image, buffer);
        assert buffer.remaining() == 0;
        return buffer.array();
    }
}
