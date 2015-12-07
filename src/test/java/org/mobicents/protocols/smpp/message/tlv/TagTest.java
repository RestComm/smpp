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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import org.mobicents.protocols.smpp.message.tlv.BasicDescriptors;
import org.mobicents.protocols.smpp.message.tlv.Tag;
import org.testng.annotations.Test;

@Test
public class TagTest {
    
    public void testTag() {
        Tag testTag = Tag.SMS_SIGNAL;
        int testTagVal = 0x1203;

        assertEquals(testTag.intValue(), testTagVal);
        assertEquals(testTag.intValue(), testTagVal);

        assertSame(Tag.getTag(testTagVal), testTag);
        assertEquals(Tag.getTag(testTagVal), testTag);
        assertTrue(testTag.equals(testTagVal));

        assertEquals(testTag.hashCode(), new Integer(testTagVal).hashCode());

        //
        // Define a new Tag type
        //
        int newTagVal = 0x1456;
        Tag newTag = Tag.defineTag(0x1456, BasicDescriptors.INTEGER4, 4);

        assertTrue(newTag.equals(newTagVal));
        assertSame(Tag.getTag(newTagVal), newTag);
    }

    public void testDefineAndUndefine() throws Exception {
        final int TAG_VALUE = 9000;
        assertFalse(Tag.isTagDefined(TAG_VALUE));
        Tag.defineTag(TAG_VALUE, BasicDescriptors.CSTRING, 30);
        assertTrue(Tag.isTagDefined(TAG_VALUE));
        Tag tag = Tag.getTag(TAG_VALUE);
        assertEquals(tag.getParamDescriptor(), BasicDescriptors.CSTRING);
        assertEquals(tag.intValue(), TAG_VALUE);
        assertEquals(tag.getMaxLength(), 30);
        assertEquals(tag.getMinLength(), 30);
        assertEquals(tag.getLength(), 30);
        Tag.undefineTag(tag);
        assertFalse(Tag.isTagDefined(TAG_VALUE));
    }
}
