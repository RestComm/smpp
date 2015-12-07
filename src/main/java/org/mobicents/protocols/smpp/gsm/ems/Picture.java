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

import org.mobicents.protocols.smpp.gsm.AbstractHeaderElement;
import org.mobicents.protocols.smpp.util.ImageUtils;


/**
 * Base class for picture implementations.
 * @version $Id: Picture.java 484 2010-02-08 16:08:50Z orank $
 */
public abstract class Picture extends AbstractHeaderElement {

    private int iei;
    private int position;
    private byte[] data;
    
    public Picture(int iei, BufferedImage image) {
        checkImage(image);
        this.iei = iei;
        this.data = createData(image);
    }
    
    public Picture(int iei, BufferedImage image, int position) {
        this(iei, image);
        this.position = position;
    }

    public int getLength() {
        return data.length;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public byte[] getData() {
        return data;
    }
    
    @Override
    protected boolean doWrite(int segmentNum, ByteBuffer buffer) {
        buffer.put((byte) iei);
        buffer.put((byte) data.length);
        buffer.put(data);
        return true;
    }

    /**
     * Check if an image is ok to encode as this <tt>Picture</tt> type.
     * @param image The image to check.
     */
    protected abstract void checkImage(BufferedImage image);
    
    /**
     * Get the number of bytes, beyond the image position, that this
     * picture implementation needs to write to the header.
     * @return This default implementation returns zero.
     */
    protected int getExtraHeaderSize() {
        return 0;
    }

    /**
     * Write any extra header information this picture implementation
     * needs after the 'position' element.
     * <p>This default method does nothing, leaving the buffer unaffected.</p>
     * @param image The image this picture represents.
     * @param buffer The buffer to write header information to.
     */
    protected void writeHeader(BufferedImage image, ByteBuffer buffer) {
    }
    
    private byte[] createData(BufferedImage image) {
        // The one byte is for the image position in the text
        int headerSize = 1 + getExtraHeaderSize();
        int width = image.getWidth();
        int height = image.getHeight();
        int bytesWidth = (width / 8) + 1;
        if (width % 8 == 0) {
            bytesWidth--;
        }
        int size = headerSize + (bytesWidth * height);
        if (size > (137 - getExtraHeaderSize())) {
            throw new IllegalStateException("Picture is too big.");
        }
        ByteBuffer buffer = ByteBuffer.allocate(size);
        buffer.put((byte) position);
        writeHeader(image, buffer);
        ImageUtils.imageToBwBitmap(image, buffer);
        return buffer.array();
    }
}
