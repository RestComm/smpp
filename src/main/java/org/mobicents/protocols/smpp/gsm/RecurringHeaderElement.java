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
 * Class representing a header element that may recur in multiple
 * segments. Concatenation information must always occur across each
 * segment so that the segments can be reassembled. Most other header
 * elements do not recur, however the specification does allow some
 * elements to optionally recur, such as port addressing.
 * @version $Id: RecurringHeaderElement.java 484 2010-02-08 16:08:50Z orank $
 */
public abstract class RecurringHeaderElement extends AbstractHeaderElement {

    private boolean recurring;
    
    /**
     * Create a new recurring header element.
     * @param recur <tt>true</tt> if this header element recurs in multiple
     * segments, <tt>false</tt> if it should only occur once.
     */
    public RecurringHeaderElement(boolean recur) {
        recurring = recur;
    }
    
    @Override
    public boolean isRecurring() {
        return recurring;
    }
}
