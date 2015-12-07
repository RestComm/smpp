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

/**
 * Exception thrown when an attempt to parse a String as an SMPPDate fails due
 * to a bad format.
 * 
 * @since 1.0
 */
public class InvalidDateFormatException extends org.mobicents.protocols.smpp.SMPPException {
    static final long serialVersionUID = 2L;
    
    private String dateString = "";

    public InvalidDateFormatException() {
    }

    public InvalidDateFormatException(String msg, String dateString) {
        super(msg);
        this.dateString = dateString;
    }

    /**
     * Get the date string that caused this exception.
     */
    public String getDateString() {
        return dateString;
    }
}

