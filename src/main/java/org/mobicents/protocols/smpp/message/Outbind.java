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
 * $Id: Outbind.java 457 2009-01-15 17:37:42Z orank $
 */
public class Outbind extends SMPPPacket {
    private static final long serialVersionUID = 2L;

    private String systemId;
    private String password;

    public Outbind() {
        super(CommandId.OUTBIND);
    }
    
    public String getSystemId() {
        return systemId;
    }
    
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            Outbind other = (Outbind) obj;
            equals |= safeCompare(systemId, other.systemId);
            equals |= safeCompare(password, other.password);
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (systemId != null) ? systemId.hashCode() : 0;
        hc += (password != null) ? password.hashCode() : 0;
        return hc;
    }

    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("systemId=").append(systemId)
        .append(",password=").append(password);
    }

    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateSystemId(systemId);
        smppVersion.validatePassword(password);
    }

    @Override
    protected void readMandatory(PacketDecoder decoder) {
        systemId = decoder.readCString();
        password = decoder.readCString();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(systemId);
        encoder.writeCString(password);
    }
    
    @Override
    protected int getMandatorySize() {
        int length = 2;
        length += sizeOf(systemId);
        length += sizeOf(password);
        return length;
    }
}
