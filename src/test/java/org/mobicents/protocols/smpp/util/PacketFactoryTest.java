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

package org.mobicents.protocols.smpp.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import org.mobicents.protocols.smpp.BadCommandIDException;
import org.mobicents.protocols.smpp.SMPPRuntimeException;
import org.mobicents.protocols.smpp.message.CommandId;
import org.mobicents.protocols.smpp.message.SMPPPacket;
import org.mobicents.protocols.smpp.util.PacketFactory;

@Test
public class PacketFactoryTest {

    public static final int VENDOR_ID = 0x10201;
    private final int[] allIds = new int[] {
            CommandId.ALERT_NOTIFICATION,
            CommandId.BIND_RECEIVER,
            CommandId.BIND_RECEIVER_RESP,
            CommandId.BIND_TRANSCEIVER,
            CommandId.BIND_TRANSCEIVER_RESP,
            CommandId.BIND_TRANSMITTER,
            CommandId.BIND_TRANSMITTER_RESP,
            CommandId.BROADCAST_SM,
            CommandId.BROADCAST_SM_RESP,
            CommandId.CANCEL_BROADCAST_SM,
            CommandId.CANCEL_BROADCAST_SM_RESP,
            CommandId.CANCEL_SM,
            CommandId.CANCEL_SM_RESP,
            CommandId.DATA_SM,
            CommandId.DATA_SM_RESP,
            CommandId.DELIVER_SM,
            CommandId.DELIVER_SM_RESP,
            CommandId.ENQUIRE_LINK,
            CommandId.ENQUIRE_LINK_RESP,
            CommandId.GENERIC_NACK,
            CommandId.OUTBIND,
            CommandId.PARAM_RETRIEVE,
            CommandId.PARAM_RETRIEVE_RESP,
            CommandId.QUERY_BROADCAST_SM,
            CommandId.QUERY_BROADCAST_SM_RESP,
            CommandId.QUERY_LAST_MSGS,
            CommandId.QUERY_LAST_MSGS_RESP,
            CommandId.QUERY_MSG_DETAILS,
            CommandId.QUERY_MSG_DETAILS_RESP,
            CommandId.QUERY_SM,
            CommandId.QUERY_SM_RESP,
            CommandId.REPLACE_SM,
            CommandId.REPLACE_SM_RESP,
            CommandId.SUBMIT_MULTI,
            CommandId.SUBMIT_MULTI_RESP,
            CommandId.SUBMIT_SM,
            CommandId.SUBMIT_SM_RESP,
            CommandId.UNBIND,
            CommandId.UNBIND_RESP,
    };
    private PacketFactory packetFactory = new PacketFactory();
    
    public void testCreatePackets() throws Exception {
        for (int id : allIds) {
            packetFactory.newInstance(id);
        }
    }
    
    public void testCreateResponses() throws Exception {
        for (int id : allIds) {
            SMPPPacket p = packetFactory.newInstance(id);
            if (p.isResponse()) {
                continue;
            }
            // Commands that have no responses
            if (id == CommandId.ALERT_NOTIFICATION || id == CommandId.OUTBIND) {
                continue;
            }
            p.setSequenceNum(89);
            SMPPPacket o = packetFactory.newResponse(p);
            assertEquals(o.getCommandId(), id | 0x80000000);
            assertEquals(o.getSequenceNum(), p.getSequenceNum());
        }
    }
    
    public void testCreateResponseFailsWithResponse() throws Exception {
        for (int id : allIds) {
            if ((id & 0x80000000) == 0) {
                continue;
            }
            SMPPPacket p = packetFactory.newInstance(id);
            try {
                packetFactory.newResponse(p);
                fail("Should not create a response to a response.");
            } catch (SMPPRuntimeException x) {
                // Pass
            }
        }
    }
    
    public void testCustomCommand() throws Exception {
        try {
            packetFactory.newInstance(VENDOR_ID);
            fail("Vendor ID should not be recognized yet.");
        } catch (BadCommandIDException x) {
            // Pass
        }
        packetFactory.registerVendorPacket(
                VENDOR_ID, VendorRequest.class, VendorResponse.class);
        SMPPPacket packet = packetFactory.newInstance(VENDOR_ID);
        assertTrue(packet instanceof VendorRequest);
        assertEquals(packet.getCommandId(), VENDOR_ID);
        
        packet = packetFactory.newInstance(VENDOR_ID | 0x80000000);
        assertTrue(packet instanceof VendorResponse);
        assertEquals(packet.getCommandId(), VENDOR_ID | 0x80000000);
        
        packet = packetFactory.newInstance(VENDOR_ID);
        packet.setSequenceNum(101);
        SMPPPacket response = packetFactory.newResponse(packet);
        assertTrue(response instanceof VendorResponse);
        assertEquals(response.getSequenceNum(), 101);
    }
}

class VendorRequest extends SMPPPacket {
    private static final long serialVersionUID = 1L;
    VendorRequest() {
        super(PacketFactoryTest.VENDOR_ID);
    }
}
class VendorResponse extends SMPPPacket {
    private static final long serialVersionUID = 1L;
    public VendorResponse() {
        super(PacketFactoryTest.VENDOR_ID | 0x80000000);
    }
    public VendorResponse(SMPPPacket request) {
        super(request);
    }
}
