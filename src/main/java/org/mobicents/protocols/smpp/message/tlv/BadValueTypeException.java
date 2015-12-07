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

package org.mobicents.protocols.smpp.message.tlv;

import org.mobicents.protocols.smpp.SMPPRuntimeException;

/**
 * Attempt to set a value on a tag that expects a Java type other than that
 * used. This exception gets thrown if an attempt is made, for instance, to set
 * a <code>java.lang.String</code> value on a Tag that is defined as an
 * integer.
 * 
 * @version $Id: BadValueTypeException.java 452 2009-01-15 16:56:36Z orank $
 */
public class BadValueTypeException extends SMPPRuntimeException {
    static final long serialVersionUID = 3L;
    
    /**
     * Create a new BadValueTypeException.
     */
    public BadValueTypeException() {
    }

    /**
     * Create a new BadValueTypeException.
     * 
     * @param msg
     *            Exception message.
     */
    public BadValueTypeException(String msg) {
        super(msg);
    }
}

