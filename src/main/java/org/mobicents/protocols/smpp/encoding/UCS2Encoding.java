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

public class UCS2Encoding extends AlphabetEncoding {
    private static final String ENCODING = "ISO-10646-UCS-2";
    private static final int DCS = 8;

    /**
     * Construct a new UCS2 encoding.
     * @throws java.io.UnsupportedEncodingException if the ISO-10646-UCS-2
     * charset is not supported by the JVM.
     */
    public UCS2Encoding() throws UnsupportedEncodingException {
        super(DCS);
        setCharset(ENCODING);
    }
}
