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
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.mobicents.protocols.smpp.util.SMPPDate;
import org.testng.annotations.Test;

@Test
public class RelativeSMPPDateTest {

    public void testRelativeDate() {
        SMPPDate d = SMPPDate.getRelativeInstance(2, 5, 12, 9, 55, 3);
        assertTrue(d.isRelative());
        assertFalse(d.isAbsolute());
        assertEquals(d.getSign(), 'R');
        assertEquals(d.getYear(), 2);
        assertEquals(d.getMonth(), 5);
        assertEquals(d.getDay(), 12);
        assertEquals(d.getHour(), 9);
        assertEquals(d.getMinute(), 55);
        assertEquals(d.getSecond(), 3);
        assertEquals(d.getTenth(), 0);
        assertEquals(d.getUtcOffset(), 0);
        assertNull(d.getTimeZone());
    }

    public void testEqualsAndHashCode() {
        SMPPDate date1 = SMPPDate.getRelativeInstance(2, 2, 2, 2, 2, 2);
        SMPPDate date2 = SMPPDate.getRelativeInstance(2, 2, 2, 2, 2, 2);
        SMPPDate date3 = SMPPDate.getRelativeInstance(3, 3, 3, 3, 3, 3);

        assertEquals(date1, date1);
        assertEquals(date2, date2);
        assertEquals(date3, date3);
        assertEquals(date2, date1);
        assertEquals(date1, date2);
        assertFalse(date1.equals(date3));
        assertFalse(date3.equals(date1));
        
        assertEquals(date2.hashCode(), date1.hashCode());
        assertFalse(date1.hashCode() == date3.hashCode());
    }
}
