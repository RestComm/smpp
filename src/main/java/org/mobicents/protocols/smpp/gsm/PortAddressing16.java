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

/**
 * 16-bit Port addressing. 3GPP TS 23.040 9.2.3.24.4.
 * @version $Id: PortAddressing16.java 484 2010-02-08 16:08:50Z orank $
 */
public class PortAddressing16 extends RecurringHeaderElement {

    private int sourcePort;
    private int destPort;

    public PortAddressing16() {
        super(true);
    }

    public PortAddressing16(int sourcePort, int destPort) {
        super(true);
        setSourcePort(sourcePort);
        setDestPort(destPort);
    }
    
    public int getLength() {
        return 4;
    }

    public boolean doWrite(int segmentNum, ByteBuffer buffer) {
        buffer.put((byte) 5);
        buffer.put((byte) 4);
        buffer.putShort((short) destPort);
        buffer.putShort((short) sourcePort);
        return true;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        if (sourcePort < 0 || sourcePort > 0xffff) {
            throw new IllegalArgumentException(
                    "Source port must be 0 <= port <= 0xffff");
        }
        this.sourcePort = sourcePort;
    }

    public int getDestPort() {
        return destPort;
    }

    public void setDestPort(int destPort) {
        if (destPort < 0 || destPort > 0xffff) {
            throw new IllegalArgumentException(
                    "Destination port must be 0 <= port <= 0xffff");
        }
        this.destPort = destPort;
    }
}
