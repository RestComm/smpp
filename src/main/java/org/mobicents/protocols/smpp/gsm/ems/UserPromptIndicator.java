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

import java.nio.ByteBuffer;

import org.mobicents.protocols.smpp.gsm.AbstractHeaderElement;

/**
 * Implementation of the user prompt indicator.
 * @version $Id: UserPromptIndicator.java 484 2010-02-08 16:08:50Z orank $
 */
public class UserPromptIndicator extends AbstractHeaderElement {

    private int numObjects;
    
    public UserPromptIndicator(int numObjects) {
        this.numObjects = numObjects;
    }
    
    @Override
    protected boolean doWrite(int segmentNum, ByteBuffer buffer) {
        buffer.put((byte) 0x13);
        buffer.put((byte) 1);
        buffer.put((byte) numObjects);
        return true;
    }
    
    public int getLength() {
        return 1;
    }
}
