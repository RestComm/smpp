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


/**
 * Interface for objects that can represent TP-User-Data as defined
 * by 3GPP TS 23.040.
 * 
 * <p>
 * <strong>Experimental API</strong>: <tt>UserData</tt> and its supporting
 * classes and implementations are an experimental API and can change or
 * be removed at any time.
 * </p>
 * @version $Id: UserData.java 484 2010-02-08 16:08:50Z orank $
 */
public interface UserData {
    /**
     * Add a {@link HeaderElement} to this user data implementation.
     * @param element The header element to add.
     */
    void addHeaderElement(HeaderElement element);
    
    /**
     * Calculate if this user data requires multiple SMS segments.
     * @return <tt>true</tt> if the user data requires multiple SMS
     * segments, <tt>false</tt> if the data requires only a single SMS.
     */
    boolean isMultiMessage();
    
    /**
     * Get the user data as a single message. This method only returns
     * normally if {@link #isMultiMessage()} returns <tt>false</tt>.
     * @return A byte array containing the encoded user data.
     * @throws IllegalStateException If this user data requires more
     * than one SMS segment.
     */
    byte[] toSingleSms();
    
    /**
     * Get the user data SMS segments.
     * @return An array of byte arrays, each element contains a single
     * SMS segment.
     */
    byte[][] toSegments();
    
    /**
     * Get the payload of this user data. This is the data that is
     * included in the message after the user data header has been encoded.
     * @return The payload of this user data.
     */
    byte[] getData();
    
    /**
     * Set the payload of this user data.
     * @param data The payload of this user data.
     * @see #getData()
     */
    void setData(byte[] data);
}
