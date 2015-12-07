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
 * An attempt was made to encode or decode a value with a length outside of the
 * bounds defined by its <code>Tag</code>. This can happen, for instance,
 * when an attempt is made to encode a string value that is longer than the
 * maximum length defined by the tag for that value.
 * 
 * @version $Id: InvalidSizeForValueException.java 452 2009-01-15 16:56:36Z orank $
 */
public class InvalidSizeForValueException extends SMPPRuntimeException {
    static final long serialVersionUID = 3L;
    
    /**
     * Create a new InvalidSizeForValueException.
     */
    public InvalidSizeForValueException() {
    }

    /**
     * Create a new InvalidSizeForValueException.
     * 
     * @param msg
     *            The exception message.
     */
    public InvalidSizeForValueException(String msg) {
        super(msg);
    }
}
