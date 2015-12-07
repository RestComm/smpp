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

package org.mobicents.protocols.smpp.gsm;

/**
 * GSM error codes.
 * @version $Id: GSMError.java 452 2009-01-15 16:56:36Z orank $
 * @since 0.4.0
 */
public enum GSMError {
    ERR_NONE(0),
    ERR_P_UNKNOWN(1),
    ERR_P_PROVISION(11),
    ERR_T_BARRED(13),
    ERR_P_CUG(15),
    ERR_T_MSSUPPORT(19),
    ERR_T_MSERROR(20),
    ERR_T_SUPPORT(21),
    ERR_T_MEMCAP(22),
    ERR_T_ABSENT(29),
    ERR_T_ABSENT_DETACHED(30),
    ERR_T_ABSENT_PAGEFAIL(31),
    ERR_T_SUPPORT_ROAMING(32),
    ERR_T_SYSTEM(36);
    
    private int code;
    
    private GSMError(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
