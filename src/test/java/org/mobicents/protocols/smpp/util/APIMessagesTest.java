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

import static org.testng.Assert.assertNotNull;

import java.util.MissingResourceException;

import org.mobicents.protocols.smpp.util.APIConfigFactory;
import org.mobicents.protocols.smpp.util.APIMessages;
import org.mobicents.protocols.smpp.util.PropertiesAPIConfig;
import org.testng.annotations.Test;

@Test
public class APIMessagesTest {

    public void testAPIMessagesWorksWithNoBundle() throws Exception {
        try {
            PropertiesAPIConfig cfg = new PropertiesAPIConfig();
            cfg.initialise();
            cfg.setProperty(APIMessages.BUNDLE_PROPERTY, "non_existent");
            APIConfigFactory.setCachedConfig(cfg);
            APIMessages messages = new APIMessages();
            assertNotNull(messages.getPacketStatus(8));
        } finally {
            // Ensure other tests are not affected by this one.
            APIConfigFactory.reset();
        }
    }
    
    public void testGetPacketStatusReturnsValidValue() throws Exception {
        APIMessages messages = new APIMessages();
        assertNotNull(messages.getPacketStatus(2));
    }
    
    @Test(expectedExceptions = MissingResourceException.class)
    public void testGetPacketStatusThrowsExceptionOnUnrecognizedStatus() throws Exception {
        APIMessages messages = new APIMessages();
        messages.getPacketStatus(Integer.MAX_VALUE);
    }
}
