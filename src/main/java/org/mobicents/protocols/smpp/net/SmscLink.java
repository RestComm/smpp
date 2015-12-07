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

package org.mobicents.protocols.smpp.net;

import java.io.IOException;

import org.mobicents.protocols.smpp.message.SMPPPacket;

/**
 * Interface for the network link to an SMSC. 
 * @version $Id: SmscLink.java 457 2009-01-15 17:37:42Z orank $
 */
public interface SmscLink {
    /**
     * Initiate the connection to the SMSC. If this link is already connected,
     * this method should do nothing.
     * @throws IOException
     */
    void connect() throws IOException;
    
    /**
     * Disconnect from the SMSC. If this link is already closed,
     * this method should do nothing.
     * @throws IOException
     */
    void disconnect() throws IOException;
    
    /**
     * Determine if the underlying link is connected to the SMSC.
     * @return <code>true</code> if connected, <code>false</code> otherwise.
     */
    boolean isConnected();
    
    /**
     * Send an SMPP packet to the SMSC.
     * @param packet The packet to send.
     * @param withOptionalParams <code>true</code> to send the packet&apos;s
     * optional parameters during the write, <code>false</code> to omit the
     * optional parameters.
     * @throws IOException
     */
    void write(SMPPPacket packet, boolean withOptionalParams) throws IOException;
    
    /**
     * If the underlying link implements some form of output buffering, then
     * this method should flush the buffer. If the link does not do any form
     * of buffering, this method should do nothing.
     * @throws IOException
     */
    void flush() throws IOException;
    
    /**
     * Read the next SMPP packet from the underlying link. This method should
     * block until it has fully read all of the bytes for the next packet,
     * barring any time out. The byte array supplied to the method call will
     * be used to store the packet&apos;s bytes, and that same array will be
     * returned. However, if the buffer is not large enough to hold the
     * packet, a <b>new</b> byte array will be created, the packet stored in it
     * and this new array will be returned.
     * @param buffer A byte array to use to store the packet data.
     * @return <code>buffer</code> will be returned if it is large enough to
     * hold all of the packet&apos;s data, otherwise a new array is created
     * and returned with the packet data.
     * @throws IOException
     * @throws ReadTimeoutException
     */
    SMPPPacket read() throws IOException;
    
    /**
     * Get the current timeout for the underlying link. If read timeouts
     * are not supported, calls to this method should throw a
     * {@link org.mobicents.protocols.smpp.UnsupportedOperationException}.
     * @return The current timeout, specified in milliseconds.
     * @throws org.mobicents.protocols.smpp.UnsupportedOperationException If read timeouts
     * are not supported.
     * @see #setTimeout(int)
     */
    int getTimeout();

    /**
     * Set the read timeout for the underlying link. If a blocked read takes
     * longer than the specified <code>timeout</code>, then a
     * {@link ReadTimeoutException} should be thrown. Supporting read timeouts
     * is optional for SmscLink implementations. If it is not supported,
     * calls to this method must throw an
     * {@link org.mobicents.protocols.smpp.UnsupportedOperationException}. A timeout value
     * of <code>0</code> deactivates timeouts - reads will block forever.
     * @param timeout The new timeout value, specified in milliseconds.
     * @throws org.mobicents.protocols.smpp.UnsupportedOperationException If read timeouts
     * are not supported.
     */
    void setTimeout(int timeout);
    
    /**
     * Determine if this SMSC link supports read timeouts.
     * @return <code>true</code> if the implementation supports read timeouts,
     * <code>false</code> if not.
     */
    boolean isTimeoutSupported();
}
