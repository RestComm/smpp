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

package org.mobicents.protocols.smpp.message;

/**
 * Message state enumeration.
 * @version $Id: MessageState.java 452 2009-01-15 16:56:36Z orank $
 * @since 0.4.0
 */
public class MessageState {
    public static final MessageState SCHEDULED = new MessageState(0, false);
    public static final MessageState EN_ROUTE = new MessageState(1, false);
    public static final MessageState DELIVERED = new MessageState(2, true);
    public static final MessageState EXPIRED = new MessageState(3, true);
    public static final MessageState DELETED = new MessageState(4, true);
    public static final MessageState UNDELIVERABLE = new MessageState(5, true);
    public static final MessageState ACCEPTED = new MessageState(6, true);
    public static final MessageState UNKNOWN = new MessageState(7, false);
    public static final MessageState REJECTED = new MessageState(8, true);
    public static final MessageState SKIPPED = new MessageState(9, true);
    
    private static final MessageState[] LOOKUP_TABLE = new MessageState[] {
        SCHEDULED,
        EN_ROUTE,
        DELIVERED,
        EXPIRED,
        DELETED,
        UNDELIVERABLE,
        ACCEPTED,
        UNKNOWN,
        REJECTED,
        SKIPPED,
    };
    
    private final int value;
    private final boolean isFinal;
    
    protected MessageState(int value, boolean isFinal) {
        this.value = value;
        this.isFinal = isFinal;
    }

    public int getValue() {
        return value;
    }
    
    public boolean isFinal() {
        return isFinal;
    }
    
    public static MessageState getMessageState(int value) {
        try {
            return LOOKUP_TABLE[value];
        } catch (ArrayIndexOutOfBoundsException x) {
            return null;
        }
    }
    
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
