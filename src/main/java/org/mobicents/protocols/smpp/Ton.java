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
 * Type of number constants.
 * @version $Id: Ton.java 452 2009-01-15 16:56:36Z orank $
 * @since 0.4.0
 */
public class Ton {
    public static final int UNKNOWN = 0;
    public static final int INTERNATIONAL = 1;
    public static final int NATIONAL = 2;
    public static final int NETWORK = 3;
    public static final int SUBSCRIBER = 4;
    public static final int ALPHANUMERIC = 5;
    public static final int ABBREVIATED = 6;
    public static final int RESERVED_EXTN = 7;
}
