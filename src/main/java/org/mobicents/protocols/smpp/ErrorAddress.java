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

import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;

/**
 * An address that message submission was unsuccessfully submitted to. This
 * class is used in the SubmitMultiResp packet type to return a list of SME
 * addresses that message submission failed for along with an error code for
 * each address indicating the reason for the failure.
 * 
 */
public class ErrorAddress extends Address {
    static final long serialVersionUID = 2L;
    
    /**
     * The error code showing why this address failed.
     */
    private long error;

    /**
     * Create a new ErrorAddress object.
     */
    public ErrorAddress() {
    }

    /**
     * Create a new ErrorAddress object.
     * 
     * @param ton
     *            The Type Of Number.
     * @param npi
     *            The Numbering Plan Indicator.
     * @param addr
     *            The address.
     */
    public ErrorAddress(int ton, int npi, String addr) {
        super(ton, npi, addr);
    }

    /**
     * Create a new ErrorAddress object.
     * 
     * @param ton
     *            The Type Of Number.
     * @param npi
     *            The Numbering Plan Indicator.
     * @param addr
     *            The address.
     * @param error
     *            The error code indicating why message submission failed.
     */
    public ErrorAddress(int ton, int npi, String addr, long error) {
        super(ton, npi, addr);
        this.error = error;
    }

    /**
     * Get the error code associated with this ErrorAddress.
     */
    public long getError() {
        return error;
    }

    /**
     * Set the error code associated with this ErrorAddress.
     */
    public void setError(long error) {
        this.error = error;
    }

    public int getLength() {
        return super.getLength() + 4;
    }

    public void writeTo(PacketEncoder encoder) throws java.io.IOException {
        super.writeTo(encoder);
        encoder.writeUInt4(error);
    }

    public void readFrom(PacketDecoder decoder) {
        super.readFrom(decoder);
        error = decoder.readUInt4();
    }
    
    @Override
    public String toString() {
        return new StringBuffer(super.toString())
        .append("/Error=").append(error).toString();
    }
}
