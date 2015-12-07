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

package org.mobicents.protocols.smpp.gsm;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of {@link UserData}.
 * 
 * <p>This implementation automatically handles the inclusion of the
 * concatenated SMS {@link HeaderElement}. Calling code should <strong>not
 * </strong> attempt to add a {@link ConcatenatedSms} header element
 * via {@link #addHeaderElement(HeaderElement)}.</p>
 * @version $Id: UserDataImpl.java 486 2010-02-15 10:48:15Z orank $
 */
public class UserDataImpl implements UserData {

    private LinkedList<HeaderElement> headerElements = new LinkedList<HeaderElement>();
    private byte[] data;
    private boolean useConcat16;
    
    /**
     * Create a new <tt>UserDataImpl</tt> that uses 8-bit reference numbers,
     * if concatenated SMS is required.
     */
    public UserDataImpl() {
    }
    
    /**
     * Create a new <tt>UserDataImpl</tt>.
     * @param useConcat16 If concatenated SMS is required, pass <tt>true</tt>
     * for this value to use 16-bit segment reference numbers or <tt>false</tt>
     * to use 8-bit segment reference numbers.
     */
    public UserDataImpl(boolean useConcat16) {
        this.useConcat16 = useConcat16;
    }
    
    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException If <tt>element</tt> is an instance of
     * {@link ConcatenatedSms}.
     */
    public void addHeaderElement(HeaderElement element) {
        if (element instanceof ConcatenatedSms) {
            throw new IllegalArgumentException(
                    "Concatenated SMS is handled automatically.");
        }
        headerElements.add(element);
        Collections.sort(headerElements, new HeaderElementComparator());
    }

    public byte[] toSingleSms() {
        byte[][] segments = toSegments();
        if (segments.length > 1) {
            throw new IllegalStateException(
                    "There is more than one message segment");
        }
        return segments[0];
    }

    public byte[][] toSegments() {
        List<ByteBuffer> segments = new ArrayList<ByteBuffer>();
        List<HeaderElement> elements;
        if (calcSize(headerElements, data) > 140) {
            // Concatenation is required.
            elements = dupElements();
            HeaderElement concat = new ConcatenatedSms(useConcat16);
            elements.add(0, concat);
        } else {
            elements = headerElements;
        }
        ByteBuffer dataBuffer;
        if (data != null) {
            dataBuffer = ByteBuffer.wrap(data);
        } else {
            dataBuffer = ByteBuffer.wrap(new byte[0]);
        }
        boolean needMoreSegments = true;
        boolean needUdhl = elements.size() > 0;
        int segmentNum = 0;
        while (needMoreSegments) {
            segmentNum++;
            ByteBuffer segment = ByteBuffer.allocate(140);
            if (needUdhl) {
                segment.put((byte) 0);
            }
            boolean allComplete = true;
            for (HeaderElement element : elements) {
                if (element.isRecurring() || !element.isComplete()) {
                    element.write(segmentNum, segment);
                }
                allComplete &= element.isComplete();
                if (segment.remaining() < 2) {
                    break;
                }
            }
            int headerSize = (segment.capacity() - segment.remaining()) - 1;
            segment.put(0, (byte) headerSize);
            if (segment.remaining() > 0) {
                int numBytes =
                    Math.min(segment.remaining(), dataBuffer.remaining());
                dataBuffer.get(segment.array(), segment.position(), numBytes);
                segment.position(segment.position() + numBytes);
            }
            segments.add(segment);
            if (allComplete && dataBuffer.remaining() == 0) {
                needMoreSegments = false;
            }
        }
        for (HeaderElement element : elements) {
            element.postProcess(segments);
        }
        byte[][] result = new byte[segments.size()][];
        for (int i = 0; i < segments.size(); i++) {
            ByteBuffer segment = segments.get(i);
            // If the segment was completely filled, just use the ByteBuffer's
            // backing array. If it wasn't filled, copy out the exact
            // number of bytes that were filled into the array.
            if (segment.remaining() == 0) {
                result[i] = segment.array();
            } else {
                segment.flip();
                result[i] = new byte[segment.remaining()];
                segment.get(result[i], 0, result[i].length);
            }
        }
        return result;
    }

    public boolean isMultiMessage() {
        return calcSize(headerElements, data) > 140;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    
    private int calcSize(List<HeaderElement> elements, byte[] data) {
        int size = 0;
        if (elements.size() > 0) {
            // 1 octet for specifying the length of the UDH.
            size += 1;
        }
        if (data != null) {
            size += data.length;
        }
        for (HeaderElement element : elements) {
            size += element.getLength() + 2;
        }
        return size;
    }
    
    private List<HeaderElement> dupElements() {
        return new LinkedList<HeaderElement>(headerElements);
    }
}
