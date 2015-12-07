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

package org.mobicents.protocols.smpp.message;

/**
 * ESM class enumeration.
 * @version $Id: ESMClass.java 452 2009-01-15 16:56:36Z orank $
 * @since 0.4.0
 */
public class ESMClass {
    /** Mobile Terminated; Normal delivery, no address swapping */
    public static final int SMC_MT = 1;

    /** Mobile originated */
    public static final int SMC_MO = 2;

    /** Mobile Originated / Terminated */
    public static final int SMC_MOMT = 3;

    /** Delivery receipt, no address swapping */
    public static final int SMC_RECEIPT = 4;

    /** Predefined message */
    public static final int SMC_DEFMSG = 8;

    /** Normal delivery , address swapping on */
    public static final int SMC_LOOPBACK_RECEIPT = 16;

    /** Delivery receipt, address swapping on */
    public static final int SMC_RECEIPT_SWAP = 20;

    /** Store message, do not send to Kernel */
    public static final int SMC_STORE = 32;

    /** Store message and send to kernel */
    public static final int SMC_STORE_FORWARD = 36;

    /** Distribution submission */
    public static final int SMC_DLIST = 64;

    /** Multiple recipient submission */
    public static final int SMC_MULTI = 128;

    /** Distribution list and multiple recipient submission */
    public static final int SMC_CAS_DL = 256;

    /** Escalated message FFU */
    public static final int SMC_ESCALATED = 512;

    /** Submit with replace message */
    public static final int SMC_SUBMIT_REPLACE = 1024;

    /** Memory capacity error */
    public static final int SMC_MCE = 2048;
}
