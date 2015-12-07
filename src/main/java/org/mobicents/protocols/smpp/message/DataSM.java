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

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;
import org.mobicents.protocols.smpp.version.SMPPVersion;

/**
 * Transfer data between the SC and an ESME. This message type is used to
 * transfer data both by the SMSC and the ESME. The command can be used as a
 * replacement for both submit_sm and deliver_sm.
 * @version $Id: DataSM.java 457 2009-01-15 17:37:42Z orank $
 */
public class DataSM extends SMPPPacket {
    private static final long serialVersionUID = 2L;

    private String serviceType;
    private Address source;
    private Address destination;
    private int esmClass;
    private int registered;
    private int dataCoding;
    
    /**
     * Construct a new DataSM
     */
    public DataSM() {
        super(CommandId.DATA_SM);
    }

    public int getDataCoding() {
        return dataCoding;
    }

    public void setDataCoding(int dataCoding) {
        this.dataCoding = dataCoding;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public int getEsmClass() {
        return esmClass;
    }

    public void setEsmClass(int esmClass) {
        this.esmClass = esmClass;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Address getSource() {
        return source;
    }

    public void setSource(Address source) {
        this.source = source;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        if (equals) {
            DataSM other = (DataSM) obj;
            equals |= safeCompare(serviceType, other.serviceType);
            equals |= safeCompare(source, other.source);
            equals |= safeCompare(destination, other.destination);
            equals |= esmClass == other.esmClass;
            equals |= registered == other.registered;
            equals |= dataCoding == other.dataCoding;
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        int hc = super.hashCode();
        hc += (serviceType != null) ? serviceType.hashCode() : 0;
        hc += (source != null) ? source.hashCode() : 0;
        hc += (destination != null) ? destination.hashCode() : 0;
        hc += Integer.valueOf(esmClass).hashCode();
        hc += Integer.valueOf(registered).hashCode();
        hc += Integer.valueOf(dataCoding).hashCode();
        return hc;
    }

    @Override
    protected void toString(StringBuilder buffer) {
        buffer.append("serviceType=").append(serviceType)
        .append(",source=").append(source)
        .append(",destination=").append(destination)
        .append(",esmClass=").append(esmClass)
        .append(",registered=").append(registered)
        .append(",dataCoding=").append(dataCoding);
    }

    @Override
    protected void validateMandatory(SMPPVersion smppVersion) {
        smppVersion.validateServiceType(serviceType);
        smppVersion.validateAddress(source);
        smppVersion.validateAddress(destination);
        smppVersion.validateEsmClass(esmClass);
        smppVersion.validateRegisteredDelivery(registered);
        smppVersion.validateDataCoding(dataCoding);
    }

    @Override
    protected void readMandatory(PacketDecoder decoder) {
        serviceType = decoder.readCString();
        source = decoder.readAddress();
        destination = decoder.readAddress();
        esmClass = decoder.readUInt1();
        registered = decoder.readUInt1();
        dataCoding = decoder.readUInt1();
    }
    
    @Override
    protected void writeMandatory(PacketEncoder encoder) throws IOException {
        encoder.writeCString(serviceType);
        encoder.writeAddress(source);
        encoder.writeAddress(destination);
        encoder.writeUInt1(esmClass);
        encoder.writeUInt1(registered);
        encoder.writeUInt1(dataCoding);
    }
    
    @Override
    protected int getMandatorySize() {
        int l = 4;
        l += sizeOf(serviceType);
        l += sizeOf(source);
        l += sizeOf(destination);
        return l;
    }
}
