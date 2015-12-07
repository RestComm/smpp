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

package org.mobicents.protocols.smpp;

/**
 * Interface definition for an object that can act as a receiver for
 * an SMPP session.
 * @version $Id: Receiver.java 452 2009-01-15 16:56:36Z orank $
 * @since 0.4.0
 */
public interface Receiver {
    /**
     * Get the name of this receiver.
     * @return The name of this receiver.
     */
    String getName();
    
    /**
     * Set the name of this receiver.
     * @param name The name of this receiver.
     */
    void setName(String name);
    
    /**
     * Get the session that this receiver is using.
     * @return The SMPP session this receiver is using.
     */
    Session getSession();
    
    /**
     * Se the session that this receiver is using.
     * @param session The SMPP session this receiver is using.
     */
    void setSession(Session session);
    
    /**
     * Test if this receiver is currently started.
     * @return <code>true</code> if this receiver is running, <code>false</code>
     * otherwise.
     */
    boolean isStarted();
    
    /**
     * Start this receiver.
     */
    void start();
    
    /**
     * Stop this receiver. A receiver may not have stopped by the time
     * this method returns. Callers should use the {@link #isStarted} method
     * to ensure the receiver has been fully stopped.
     */
    void stop();
}
