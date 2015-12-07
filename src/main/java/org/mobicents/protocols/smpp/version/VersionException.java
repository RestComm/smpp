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

package org.mobicents.protocols.smpp.version;

import org.mobicents.protocols.smpp.SMPPRuntimeException;

/**
 * Exception thrown when there is a versioning problem. This can be thrown
 * when an SMPP version is not recognized, or when a packet is
 * invalidate in the context of a particular version.
 * @version $Id: VersionException.java 452 2009-01-15 16:56:36Z orank $
 */
public class VersionException extends SMPPRuntimeException {
    static final long serialVersionUID = 2L;

    public VersionException() {
        super();
    }

    public VersionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public VersionException(String msg) {
        super(msg);
    }

    public VersionException(Throwable cause) {
        super(cause);
    }
}
