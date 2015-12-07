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

import java.io.UnsupportedEncodingException;

import org.mobicents.protocols.smpp.encoding.Latin1Encoding;
import org.testng.annotations.Test;

/**
 * @version $Id:$
 */
@Test
public class Latin1EncodingTest extends BaseAlphabetEncodingTest<Latin1Encoding> {
    // "Test message" followed by:
    // Yen symbol
    // Pound sign (European interpretation, not what I would call a "hash").
    // Superscript 3
    // Latin capital letter AE
    private final static int[] EXPECTED_BYTES = {
        0x54, 0x65, 0x73, 0x74, 0x20, 0x6d, 0x65, 0x73,
        0x73, 0x61, 0x67, 0x65, 0xa5, 0xa3, 0xb3, 0xc6,
    };
    private static final String STRING = "Test message\u00a5\u00a3\u00b3\u00c6";

    @Override
    protected TestData getArrayToDecode() {
        return new TestData(STRING, EXPECTED_BYTES);
    }

    @Override
    protected Latin1Encoding getEncodingToTest() throws UnsupportedEncodingException {
        return new Latin1Encoding();
    }

    @Override
    protected TestData getFullySupportedStringToEncode() {
        return new TestData(EXPECTED_BYTES, STRING);
    }

    @Override
    protected TestData getPartiallySupportedStringToEncode() {
        String string = "Unsupported character: \u20ac";
        int[] expectedBytes = new int[] {
                85, 110, 115, 117, 112, 112, 111, 114,
                116, 101, 100, 32, 99, 104, 97, 114,
                97, 99, 116, 101, 114, 58, 32, 63,
        };
        return new TestData(expectedBytes, string);
    }
}
