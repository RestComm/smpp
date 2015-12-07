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
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.util.PacketDecoder;
import org.mobicents.protocols.smpp.util.PacketEncoder;
import org.mobicents.protocols.smpp.util.SMPPDate;

/**
 * Submit a message to the SMSC for delivery to a single destination.
 * 
 * @version $Id: SubmitSM.java 452 2009-01-15 16:56:36Z orank $
 * @author amit bhayani
 * @author orank
 */
public class SubmitSM extends SMPPPacket {
	private static final long serialVersionUID = 2L;

	private String serviceType;
	private Address source;
	private Address destination;
	private int esmClass;
	private int protocolID;
	private int priority;
	private SMPPDate deliveryTime;
	private SMPPDate expiryTime;
	private int registered;
	private int replaceIfPresent;
	private int dataCoding;
	private int defaultMsg;
	private byte[] message;

	public SubmitSM() {
		super(CommandId.SUBMIT_SM);
	}

	SubmitSM(int commandId) {
		// Convenience constructor provided for deliver_sm.
		super(commandId);
	}

	public int getDataCoding() {
		return dataCoding;
	}

	public void setDataCoding(int dataCoding) {
		this.dataCoding = dataCoding;
	}

	public int getDefaultMsg() {
		return defaultMsg;
	}

	public void setDefaultMsg(int defaultMsg) {
		this.defaultMsg = defaultMsg;
	}

	public SMPPDate getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(SMPPDate deliveryTime) {
		this.deliveryTime = deliveryTime;
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

	public SMPPDate getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(SMPPDate expiryTime) {
		this.expiryTime = expiryTime;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getProtocolID() {
		return protocolID;
	}

	public void setProtocolID(int protocolID) {
		this.protocolID = protocolID;
	}

	public int getRegistered() {
		return registered;
	}

	public void setRegistered(int registered) {
		this.registered = registered;
	}

	public int getReplaceIfPresent() {
		return replaceIfPresent;
	}

	public void setReplaceIfPresent(int replaceIfPresent) {
		this.replaceIfPresent = replaceIfPresent;
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

	@Override
	public boolean equals(Object obj) {
		boolean equals = super.equals(obj);
		if (equals) {
			SubmitSM other = (SubmitSM) obj;
			equals |= safeCompare(serviceType, other.serviceType);
			equals |= safeCompare(source, other.source);
			equals |= safeCompare(destination, other.destination);
			equals |= esmClass == other.esmClass;
			equals |= protocolID == other.protocolID;
			equals |= priority == other.priority;
			equals |= safeCompare(deliveryTime, other.deliveryTime);
			equals |= safeCompare(expiryTime, other.expiryTime);
			equals |= registered == other.registered;
			equals |= replaceIfPresent == other.replaceIfPresent;
			equals |= dataCoding == other.dataCoding;
			equals |= defaultMsg == other.defaultMsg;
			equals |= Arrays.equals(message, other.message);
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
		hc += Integer.valueOf(protocolID).hashCode();
		hc += Integer.valueOf(priority).hashCode();
		hc += (deliveryTime != null) ? deliveryTime.hashCode() : 0;
		hc += (expiryTime != null) ? expiryTime.hashCode() : 0;
		hc += Integer.valueOf(registered).hashCode();
		hc += Integer.valueOf(replaceIfPresent).hashCode();
		hc += Integer.valueOf(dataCoding).hashCode();
		hc += Integer.valueOf(defaultMsg).hashCode();
		if (message != null) {
			try {
				hc += new String(message, "US-ASCII").hashCode();
			} catch (UnsupportedEncodingException x) {
				throw new RuntimeException(x);
			}
		}
		return hc;
	}

	/**
	 * Return the number of bytes this packet would be encoded as to an
	 * OutputStream.
	 * 
	 * @return the number of bytes this packet would encode as.
	 */
	public int getMandatorySize() {
		int len = ((serviceType != null) ? serviceType.length() : 0)
				+ ((source != null) ? source.getLength() : 3)
				+ ((destination != null) ? destination.getLength() : 3)
				+ ((deliveryTime != null) ? deliveryTime.getLength()
						: 1)
				+ ((expiryTime != null) ? expiryTime.getLength() : 1)
				+ ((message != null) ? (message.length + 1)  : 1);

		// 8 1-byte integers, 3 c-strings
		return len + 8;
	}

	protected void writeMandatory(PacketEncoder encoder) throws IOException {

		encoder.writeCString(serviceType);

		if (source != null) {
			source.writeTo(encoder);
		} else {
			// Write ton=0(null), npi=0(null), address=\0(nul)
			new Address().writeTo(encoder);
		}
		if (destination != null) {
			destination.writeTo(encoder);
		} else {
			// Write ton=0(null), npi=0(null), address=\0(nul)
			new Address().writeTo(encoder);
		}

		encoder.writeUInt1(esmClass);
		encoder.writeUInt1(protocolID);
		encoder.writeUInt1(priority);

		encoder.writeDate(deliveryTime);
		encoder.writeDate(expiryTime);

		encoder.writeUInt1(registered);
		encoder.writeUInt1(replaceIfPresent);
		encoder.writeUInt1(dataCoding);
		encoder.writeUInt1(defaultMsg);

		int smLength = 0;
		if (message != null) {
			smLength = message.length;
		}

		encoder.writeUInt1(smLength);

		if (message != null) {
			encoder.writeBytes(message);
		}
	}

	@Override
	protected void readMandatory(PacketDecoder decoder) {
		this.serviceType = decoder.readCString();

		this.source = new Address();
		this.source.readFrom(decoder);

		this.destination = new Address();
		this.destination.readFrom(decoder);

		this.esmClass = decoder.readUInt1();
		this.protocolID = decoder.readUInt1();
		this.priority = decoder.readUInt1();

		this.deliveryTime = decoder.readDate();
		this.expiryTime = decoder.readDate();

		this.registered = decoder.readUInt1();
		this.replaceIfPresent = decoder.readUInt1();
		this.dataCoding = decoder.readUInt1();
		this.defaultMsg = decoder.readUInt1();
		
		int smLength = decoder.readUInt1();
		
		if(smLength > 0){
			 this.message =decoder.readBytes(smLength);
		}
	}

}
