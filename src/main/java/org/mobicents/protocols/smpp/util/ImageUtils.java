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

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.mobicents.protocols.smpp.SMPPRuntimeException;

/**
 * Utility methods for working with images.
 * @version $Id: ImageUtils.java 484 2010-02-08 16:08:50Z orank $
 */
public final class ImageUtils {

    private ImageUtils() {
    }
    
    /**
     * Use a {@link BufferedImage} to create black and white bitmap data.
     * Bytes are output to the supplied <tt>buffer</tt> where each bit
     * represents a single pixel. 0 represents a white pixel, 1 represents
     * a black pixel. The leftmost pixel is in the most significant bit
     * of the byte..so bit 8 of byte 0 represents the pixel at (0, 0).
     * Bit 7 of byte 1 represents the pixel at (9, 0).
     * @param image The image to create bitmap data for.
     * @param buffer The buffer to output bitmap data to.
     * @throws SMPPRuntimeException If there is insufficient space in
     * the buffer to contain the bitmap data. 
     */
    public static void imageToBwBitmap(BufferedImage image, ByteBuffer buffer) {
        int width = image.getWidth();
        int height = image.getHeight();
        int bytesWidth = (width / 8) + 1;
        if (width % 8 == 0) {
            bytesWidth--;
        }
        if (buffer.remaining() < (bytesWidth * height)) {
            throw new SMPPRuntimeException(
                    "Insufficient space to for bitmap data");
        }
        int bit = 7;
        int currentByte = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int colour = image.getRGB(x, y);
                if ((colour & 0xffffff) != 0xffffff) {
                    currentByte |= 1 << bit;
                }
                bit--;
                if (bit < 0) {
                    buffer.put((byte) currentByte);
                    bit = 7;
                    currentByte = 0;
                }
            }
        }
    }
}
