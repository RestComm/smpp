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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.mobicents.protocols.smpp.util.DefaultSequenceScheme;
import org.testng.annotations.Test;

@Test
public class DefaultSequenceSchemeTest {

    public void testNextNumber() {
        DefaultSequenceScheme seq = new DefaultSequenceScheme();
        assertEquals(seq.nextNumber(), 1L);
        assertEquals(seq.nextNumber(), 2L);
        assertEquals(seq.nextNumber(), 3L);
        assertEquals(seq.nextNumber(), 4L);
        assertEquals(seq.nextNumber(), 5L);
        assertEquals(seq.nextNumber(), 6L);
    }
    public void testPeek() {
        DefaultSequenceScheme seq = new DefaultSequenceScheme();
        assertEquals(seq.peek(), 1L);
        assertEquals(seq.nextNumber(), 1L);
        assertEquals(seq.peek(), 2L);
        assertEquals(seq.peek(), 2L);
        assertEquals(seq.peek(), 2L);
        assertEquals(seq.peek(), 2L);
        assertEquals(seq.nextNumber(), 2L);
        assertEquals(seq.peek(), 3L);
        assertEquals(seq.peek(10L), 13L);
    }
    public void testReset() {
        DefaultSequenceScheme seq = new DefaultSequenceScheme();
        while (seq.nextNumber() < 1450L);
        assertEquals(seq.peek(), 1451);
        seq.reset();
        assertEquals(seq.nextNumber(), 1);
    }

    public void testWrap() {
        DefaultSequenceScheme dss = new DefaultSequenceScheme(
                DefaultSequenceScheme.MAX_VALUE - 1);
        assertTrue(dss.nextNumber() == DefaultSequenceScheme.MAX_VALUE - 1L);
        assertTrue(dss.nextNumber() == DefaultSequenceScheme.MAX_VALUE);
        //Resets back to start
        assertTrue(dss.nextNumber() == DefaultSequenceScheme.MAX_VALUE - 1);
    }
}
