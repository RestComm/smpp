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

import java.util.ArrayList;
import java.util.List;

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.message.DataSM;
import org.mobicents.protocols.smpp.message.DeliverSM;
import org.mobicents.protocols.smpp.message.SubmitSM;
import org.mobicents.protocols.smpp.message.tlv.Tag;

/**
 * User data utility methods.
 * @version $Id: UserDataUtil.java 484 2010-02-08 16:08:50Z orank $
 */
public final class UserDataUtil {

    private UserDataUtil() {
    }
    
    /**
     * Create {@link SubmitSM} packets from segments created by a
     * {@link UserData} implementation. This method sets the ESM class
     * on every packet to indicate the user data header is present.
     * @param segments The segments to create packets from.
     * @param from The source address to set on every packet, may be
     * <tt>null</tt>.
     * @param to The destination address to set on every packet.
     * @return An array of created <tt>SubmitSM</tt> packets.
     */
    public static SubmitSM[] createSubmits(
            byte[][] segments,
            Address from,
            Address to) {
        List<SubmitSM> packets = new ArrayList<SubmitSM>();
        for (byte[] segment : segments) {
            SubmitSM p = new SubmitSM();
            p.setSource(from);
            p.setDestination(to);
            p.setEsmClass(0x40);
            p.setMessage(segment);
        }
        return packets.toArray(new SubmitSM[packets.size()]);
    }
    
    /**
     * Create {@link DeliverSM} packets from segments created by a
     * {@link UserData} implementation. This method sets the ESM class
     * on every packet to indicate the user data header is present.
     * @param segments The segments to create packets from.
     * @param from The source address to set on every packet, may be
     * <tt>null</tt>.
     * @param to The destination address to set on every packet.
     * @return An array of created <tt>DeliverSM</tt> packets.
     */
    public static DeliverSM[] createDelivers(
            byte[][] segments,
            Address from,
            Address to) {
        List<DeliverSM> packets = new ArrayList<DeliverSM>();
        for (byte[] segment : segments) {
            DeliverSM p = new DeliverSM();
            p.setSource(from);
            p.setDestination(to);
            p.setEsmClass(0x40);
            p.setMessage(segment);
        }
        return packets.toArray(new DeliverSM[packets.size()]);
    }
    
    /**
     * Create {@link DataSM} packets from segments created by a
     * {@link UserData} implementation. This method sets the ESM class
     * on every packet to indicate the user data header is present.
     * @param segments The segments to create packets from.
     * @param from The source address to set on every packet, may be
     * <tt>null</tt>.
     * @param to The destination address to set on every packet.
     * @return An array of created <tt>DataSM</tt> packets.
     */
    public static DataSM[] createDataSM(
            byte[][] segments,
            Address from,
            Address to) {
        List<DataSM> packets = new ArrayList<DataSM>();
        for (byte[] segment : segments) {
            DataSM p = new DataSM();
            p.setSource(from);
            p.setDestination(to);
            p.setEsmClass(0x40);
            p.setTLV(Tag.MESSAGE_PAYLOAD, segment);
        }
        return packets.toArray(new DataSM[packets.size()]);
    }
}
