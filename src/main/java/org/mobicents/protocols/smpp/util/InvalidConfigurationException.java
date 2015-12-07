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

import org.mobicents.protocols.smpp.SMPPRuntimeException;

/**
 * @version $Id: InvalidConfigurationException.java 452 2009-01-15 16:56:36Z orank $
 */
public class InvalidConfigurationException extends SMPPRuntimeException {
    static final long serialVersionUID = 3L;
    
    private String invalidValue;


    public InvalidConfigurationException() {
        super();
    }

    public InvalidConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidConfigurationException(String msg) {
        super(msg);
    }

    public InvalidConfigurationException(Throwable cause) {
        super(cause);
    }

    public InvalidConfigurationException(String msg,
            String invalidValue) {
        super(msg);
        this.invalidValue = invalidValue;
    }

    public InvalidConfigurationException(String msg,
            Throwable cause,
            String invalidValue) {
        super(msg, cause);
        this.invalidValue = invalidValue;
    }
    
    /**
     * Get the invalid value which caused this exception.
     * @return The offending value.
     */
    public String getInvalidValue() {
        return invalidValue;
    }
}
