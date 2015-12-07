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

package org.mobicents.protocols.smpp.event;

import org.mobicents.protocols.smpp.Session;

/**
 * Abstract super class of SMPP control events.
 * 
 * @version $Id: SMPPEvent.java 452 2009-01-15 16:56:36Z orank $
 */
public abstract class SMPPEvent {
    /** ReceiverStartEvent enumeration type. */
    public static final int RECEIVER_START = 2;

    /** ReceiverExitEvent enumeration type. */
    public static final int RECEIVER_EXIT = 3;

    /** ReceiverExceptionEvent enumeration type. */
    public static final int RECEIVER_EXCEPTION = 4;

    /** The source Connection of this event. */
    private Session source;

    /** The type of this event. */
    private int type;

    /**
     * Construct a new event. The <code>type</code> parameter should match one
     * of the enumeration constants defined in this class.
     */
    protected SMPPEvent(int type, Session source) {
        this.source = source;
        this.type = type;
    }

    /**
     * Get the source connection of this event.
     */
    public Session getSource() {
        return source;
    }

    /**
     * Get the enumeration type of this event.
     * 
     * @see #RECEIVER_EXIT
     * @see #RECEIVER_EXCEPTION
     */
    public int getType() {
        return type;
    }
}

