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
 * Enumeration of connection states.
 * @version $Id: SessionState.java 452 2009-01-15 16:56:36Z orank $
 */
public enum SessionState {
    UNBOUND(0),
    BINDING(1),
    UNBINDING(2),
    BOUND(3);
    
    private int state;
    
    private SessionState(int state) {
        this.state = state;
    }
    
    public int intValue() {
        return state;
    }
    
    public static final SessionState valueOf(int value) {
        SessionState[] states = SessionState.values();
        if (value >=0 && value < states.length) {
            return states[value];
        } else {
            return null;
        }
    }
}
