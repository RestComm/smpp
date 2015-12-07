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

package org.mobicents.protocols.smpp.encoding;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;
import java.util.Locale;

import org.testng.annotations.Test;

import org.mobicents.protocols.smpp.SMPPRuntimeException;
import org.mobicents.protocols.smpp.encoding.ASCIIEncoding;
import org.mobicents.protocols.smpp.encoding.AlphabetEncoding;
import org.mobicents.protocols.smpp.encoding.BinaryEncoding;
import org.mobicents.protocols.smpp.encoding.DefaultAlphabetEncoding;
import org.mobicents.protocols.smpp.encoding.EncodingFactory;
import org.mobicents.protocols.smpp.encoding.HPRoman8Encoding;
import org.mobicents.protocols.smpp.encoding.Latin1Encoding;
import org.mobicents.protocols.smpp.encoding.MessageEncoding;
import org.mobicents.protocols.smpp.encoding.UCS2Encoding;
import org.mobicents.protocols.smpp.encoding.UTF16Encoding;
import org.mobicents.protocols.smpp.util.APIConfig;
import org.mobicents.protocols.smpp.util.APIConfigFactory;
import org.mobicents.protocols.smpp.util.PropertiesAPIConfig;

@Test
public class EncodingFactoryTest {

    public void testEncodingFactory() {
        EncodingFactory factory = EncodingFactory.getInstance();
        assertNotNull(factory.getDefaultAlphabet());
        assertEquals(factory.getEncoding(0).getClass(), DefaultAlphabetEncoding.class);
        assertEquals(factory.getEncoding(1).getClass(), ASCIIEncoding.class);
        assertEquals(factory.getEncoding(3).getClass(), Latin1Encoding.class);
        assertEquals(factory.getEncoding(4).getClass(), BinaryEncoding.class);
        assertEquals(factory.getEncoding(8).getClass(), UCS2Encoding.class);
        assertNull(factory.getEncoding(10));
        assertNull(factory.getEncoding(100));
        assertNull(factory.getEncoding(189));
        assertEquals(DefaultAlphabetEncoding.class,
                factory.getAlphabet("en").getClass());
        Locale chinese = Locale.CHINESE;
        Locale korean = Locale.KOREAN;
        assertEquals(UCS2Encoding.class,
                factory.getAlphabet(chinese.getLanguage()).getClass());
        assertEquals(UCS2Encoding.class,
                factory.getAlphabet(korean.getLanguage()).getClass());
    }
    
    public void testAddEncoding() {
        class TestEncoding1 extends AlphabetEncoding {
            TestEncoding1() {
                super(100);
            }
        }
        EncodingFactory factory = new EncodingFactory();
        factory.addEncoding(new TestEncoding1());
        assertNotNull(factory.getEncoding(100));
        assertEquals(factory.getEncoding(100).getClass(), TestEncoding1.class);
    }
    
    @Test(expectedExceptions = {SMPPRuntimeException.class})
    public void testAddNullEncodingThrowsException() {
        EncodingFactory factory = new EncodingFactory();
        factory.addEncoding((Class<AlphabetEncoding>) null);
    }

    @Test(expectedExceptions = {SMPPRuntimeException.class})
    public void testExceptionIsThrownWhenEncodingConstructorExceptions() {
        class TestEncoding2 extends AlphabetEncoding {
            TestEncoding2() {
                super(101);
                throw new RuntimeException("This exception is expected.");
            }
        }
        EncodingFactory factory = new EncodingFactory();
        factory.addEncoding(TestEncoding2.class);
    }
    
    public void testGetAllEncodings() {
        Iterator<MessageEncoding<?>> encodings =
            EncodingFactory.getInstance().getAllEncodings();
        assertNotNull(encodings);
        assertTrue(encodings.hasNext());
    }

    public void testDefaultAlphabetUsesGsmDefaultAlphabetIfNotOtherwiseSpecified() throws Exception {
        assertNull(System.getProperty(EncodingFactory.DEFAULT_ALPHABET_PROPNAME));
        APIConfig config = APIConfigFactory.getConfig();
        assertFalse(config.isSet(APIConfig.DEFAULT_ALPHABET));
        EncodingFactory factory = new EncodingFactory();
        assertEquals(factory.getDefaultAlphabet().getClass(), DefaultAlphabetEncoding.class);
    }
    
    public void testInstantiatingDefaultAlphabetFromSystemProperty() {
        try {
            System.setProperty(
                    EncodingFactory.DEFAULT_ALPHABET_PROPNAME,
                    "org.mobicents.protocols.smpp.encoding.UTF16Encoding");
            EncodingFactory factory = new EncodingFactory();
            assertNotNull(factory.getDefaultAlphabet());
            assertEquals(factory.getDefaultAlphabet().getClass(), UTF16Encoding.class);
        } finally {
            System.clearProperty(EncodingFactory.DEFAULT_ALPHABET_PROPNAME);
        }
    }
    
    public void testInstantiatingDefaultAlphabetFromAPIConfig() throws Exception {
        try {
            assertNull(System.getProperty(EncodingFactory.DEFAULT_ALPHABET_PROPNAME));
            PropertiesAPIConfig config = new PropertiesAPIConfig();
            config.initialise();
            config.setProperty(APIConfig.DEFAULT_ALPHABET, ASCIIEncoding.class.getName());
            APIConfigFactory.setCachedConfig(config);
            EncodingFactory factory = new EncodingFactory();
            assertNotNull(factory.getDefaultAlphabet());
            assertEquals(factory.getDefaultAlphabet().getClass(), ASCIIEncoding.class);
        } finally {
            APIConfigFactory.reset();
        }
    }
    
    public void testDefaultAlphabetSpecifiedInSystemPropertyOverridesAPIConfig() throws Exception {
        try {
            System.setProperty(
                    EncodingFactory.DEFAULT_ALPHABET_PROPNAME,
                    UCS2Encoding.class.getName());
            PropertiesAPIConfig config = new PropertiesAPIConfig();
            config.initialise();
            config.setProperty(APIConfig.DEFAULT_ALPHABET, HPRoman8Encoding.class.getName());
            APIConfigFactory.setCachedConfig(config);
            EncodingFactory factory = new EncodingFactory();
            assertNotNull(factory.getDefaultAlphabet());
            assertEquals(factory.getDefaultAlphabet().getClass(), UCS2Encoding.class);
        } finally {
            System.clearProperty(EncodingFactory.DEFAULT_ALPHABET_PROPNAME);
            APIConfigFactory.reset();
        }
    }
}
