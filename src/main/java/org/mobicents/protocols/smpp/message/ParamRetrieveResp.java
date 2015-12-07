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

import java.io.IOException;

import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;
import org.mobicents.protocols.smpp.version.SMPPVersion;

/**
 * SMSC response to a ParamRetrieve request. Returns the value of the requested
 * parameter.
 * 
 * @version $Id: ParamRetrieveResp.java 457 2009-01-15 17:37:42Z orank $
 */
public class ParamRetrieveResp extends SMPPPacket {
    private static final long serialVersionUID = 2L;

    /** String value of the requested parameter */
    private String paramValue;

    /**
     * Construct a new BindReceiverResp.
     */
    public ParamRetrieveResp() {
        super(CommandId.PARAM_RETRIEVE_RESP);
    }

    /**
     * Create a new ParamRetrieveResp packet in response to a BindReceiver. This
     * constructor will set the sequence number to it's expected value.
     * 
     * @param request
     *            The Request packet the response is to
     */
    public ParamRetrieveResp(SMPPPacket request) {
        super(request);
    }

    /**
     * Set the parameter value.
     * @param paramValue
     *            Value to be returned for the requested parameter.
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /** Get the value of the parameter */
    public String getParamValue() {
        return paramValue;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            ParamRetrieveResp other = (ParamRetrieveResp) obj;
            equals |= safeCompare(paramValue, other.paramValue);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (paramValue != null) ? paramValue.hashCode() : 0;
        return hc;
    }

    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("paramValue=").append(paramValue);
    }
    
    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateParamValue(paramValue);
    }
    
    @Override
    protected void readMandatory(PacketDecoder decoder) {
        paramValue = decoder.readCString();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(paramValue);
    }
    
    @Override
    protected int getMandatorySize() {
        return 1 + sizeOf(paramValue);
    }
}
