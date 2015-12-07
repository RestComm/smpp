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

import java.util.concurrent.atomic.AtomicLong;

/**
 * The default sequence numbering scheme. This implementation starts at sequence
 * number 1 and increments by 1 for each number requested, resulting in the
 * sequence numbers <code>1..2..3..4..5..6..7..8..n</code>. If the sequence
 * number reaches as far as <code>Integer.MAX_VALUE</code>, it will wrap back
 * around to 1.
 * <p>
 * This implementation uses an {@link java.util.concurrent.atomic.AtomicInteger}
 * internally to track the next sequence number.
 * </p>
 * @version $Id: DefaultSequenceScheme.java 452 2009-01-15 16:56:36Z orank $
 */
public class DefaultSequenceScheme implements SequenceNumberScheme {
    /**
     * Maximum this sequence can go to (a 32-bit unsigned integer).
     */
    public static final long MAX_VALUE = 4294967295L;
    
    private long start = 1L;
    private AtomicLong sequence = new AtomicLong(1);

    public DefaultSequenceScheme() {
    }

    /**
     * Construct a new DefaultSequenceScheme that starts the sequence from
     * <code>start</code>.
     */
    public DefaultSequenceScheme(long start) {
        this.start = start;
        sequence.set(start);
    }

    public long nextNumber() {
    	while (true) {
            long current = sequence.get();
            long next = (current < MAX_VALUE) ? (current + 1L) : start;
            if (sequence.compareAndSet(current, next))
                return current;
        }
    }

    public long peek() {
        return sequence.get();
    }

    public long peek(long nth) {
        return sequence.get() + nth;
    }

    public void reset() {
        sequence.set(start);
    }
}
