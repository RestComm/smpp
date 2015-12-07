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
 * Parameter retrieve. Gets the current value of a configurable parameter at the
 * SMSC.
 * 
 * @version $Id: ParamRetrieve.java 457 2009-01-15 17:37:42Z orank $
 */
public class ParamRetrieve extends SMPPPacket {
    private static final long serialVersionUID = 2L;

    /** Name of the parameter to retrieve */
    private String paramName;

    /**
     * Construct a new ParamRetrieve.
     */
    public ParamRetrieve() {
        super(CommandId.PARAM_RETRIEVE);
    }

    /**
     * Set the name of the parameter to retrieve
     * @param paramName
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /** Get the parameter name */
    public String getParamName() {
        return paramName;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            ParamRetrieve other = (ParamRetrieve) obj;
            equals |= safeCompare(paramName, other.paramName);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (paramName != null) ? paramName.hashCode() : 0;
        return hc;
    }

    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("paramName=").append(paramName);
    }

    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateParamName(paramName);
    }

    @Override
    protected void readMandatory(PacketDecoder decoder) {
        paramName = decoder.readCString();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(paramName);
    }
    
    @Override
    protected int getMandatorySize() {
        return 1 + sizeOf(paramName);
    }
}
