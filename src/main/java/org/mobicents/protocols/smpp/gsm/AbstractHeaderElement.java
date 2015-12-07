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
import java.util.List;


/**
 * Abstract base class for {@link HeaderElement} implementations.
 * @version $Id: AbstractHeaderElement.java 484 2010-02-08 16:08:50Z orank $
 */
public abstract class AbstractHeaderElement implements HeaderElement {
    /**
     * Complete will be true after the element has been successfully written,
     * and false if not yet written to a segment. This only makes sense
     * for non-recurring elements.
     */
    private boolean complete;
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return getClass().equals(obj.getClass());
    }

    public void reset() {
    }
    
    public boolean isComplete() {
        return complete;
    }

    public boolean isRecurring() {
        return false;
    }
    
    public boolean write(int segmentNum, ByteBuffer buffer) {
        if (buffer.remaining() < getLength() + 2) {
            return false;
        } else {
            complete = doWrite(segmentNum, buffer);
            return complete;
        }
    }

    public void postProcess(List<ByteBuffer> segments) {
    }
    
    protected boolean doWrite(int segmentNum, ByteBuffer buffer) {
        return true;
    }
}
