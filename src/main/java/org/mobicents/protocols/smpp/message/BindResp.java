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
 * SMSC response to a Bind request.
 * 
 * @version $Id: BindResp.java 457 2009-01-15 17:37:42Z orank $
 */
public abstract class BindResp extends SMPPPacket {
    private static final long serialVersionUID = 2L;
    private String systemId;

    /**
     * Construct a new BindResp.
     */
    protected BindResp(int id) {
        super(id);
    }

    protected BindResp(SMPPPacket request) {
        super(request);
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            BindResp other = (BindResp) obj;
            equals |= safeCompare(systemId, other.systemId);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc1 = (systemId != null) ? systemId.hashCode() : 996631;
        return super.hashCode() + hc1;
    }
    
    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("systemId=").append(systemId);
    }
    
    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateSystemId(systemId);
    }

    @Override
    protected void readMandatory(PacketDecoder decoder) {
        systemId = decoder.readCString();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(systemId);
    }
    
    @Override
    protected int getMandatorySize() {
        return 1 + sizeOf(systemId);
    }
}
