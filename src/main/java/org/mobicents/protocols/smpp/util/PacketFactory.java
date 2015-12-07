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

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.mobicents.protocols.smpp.BadCommandIDException;
import org.mobicents.protocols.smpp.SMPPRuntimeException;
import org.mobicents.protocols.smpp.message.AlertNotification;
import org.mobicents.protocols.smpp.message.BindReceiver;
import org.mobicents.protocols.smpp.message.BindReceiverResp;
import org.mobicents.protocols.smpp.message.BindTransceiver;
import org.mobicents.protocols.smpp.message.BindTransceiverResp;
import org.mobicents.protocols.smpp.message.BindTransmitter;
import org.mobicents.protocols.smpp.message.BindTransmitterResp;
import org.mobicents.protocols.smpp.message.BroadcastSM;
import org.mobicents.protocols.smpp.message.BroadcastSMResp;
import org.mobicents.protocols.smpp.message.CancelBroadcastSM;
import org.mobicents.protocols.smpp.message.CancelBroadcastSMResp;
import org.mobicents.protocols.smpp.message.CancelSM;
import org.mobicents.protocols.smpp.message.CancelSMResp;
import org.mobicents.protocols.smpp.message.DataSM;
import org.mobicents.protocols.smpp.message.DataSMResp;
import org.mobicents.protocols.smpp.message.DeliverSM;
import org.mobicents.protocols.smpp.message.DeliverSMResp;
import org.mobicents.protocols.smpp.message.EnquireLink;
import org.mobicents.protocols.smpp.message.EnquireLinkResp;
import org.mobicents.protocols.smpp.message.GenericNack;
import org.mobicents.protocols.smpp.message.Outbind;
import org.mobicents.protocols.smpp.message.ParamRetrieve;
import org.mobicents.protocols.smpp.message.ParamRetrieveResp;
import org.mobicents.protocols.smpp.message.QueryBroadcastSM;
import org.mobicents.protocols.smpp.message.QueryBroadcastSMResp;
import org.mobicents.protocols.smpp.message.QueryLastMsgs;
import org.mobicents.protocols.smpp.message.QueryLastMsgsResp;
import org.mobicents.protocols.smpp.message.QueryMsgDetails;
import org.mobicents.protocols.smpp.message.QueryMsgDetailsResp;
import org.mobicents.protocols.smpp.message.QuerySM;
import org.mobicents.protocols.smpp.message.QuerySMResp;
import org.mobicents.protocols.smpp.message.ReplaceSM;
import org.mobicents.protocols.smpp.message.ReplaceSMResp;
import org.mobicents.protocols.smpp.message.SMPPPacket;
import org.mobicents.protocols.smpp.message.SubmitMulti;
import org.mobicents.protocols.smpp.message.SubmitMultiResp;
import org.mobicents.protocols.smpp.message.SubmitSM;
import org.mobicents.protocols.smpp.message.SubmitSMResp;
import org.mobicents.protocols.smpp.message.Unbind;
import org.mobicents.protocols.smpp.message.UnbindResp;

/**
 * Factory class for SMPP packets.
 * @version $Id: PacketFactory.java 457 2009-01-15 17:37:42Z orank $
 */
public final class PacketFactory {
    private static final Logger LOG = LoggerFactory.getLogger(PacketFactory.class);
    
    private final Map<Integer, Class<? extends SMPPPacket>> commands =
        new HashMap<Integer, Class<? extends SMPPPacket>>();
    private final Map<Integer, Class<? extends SMPPPacket>> userCommands =
        new HashMap<Integer, Class<? extends SMPPPacket>>();
    
    public PacketFactory() {
        add(new AlertNotification());
        add(new BindReceiver(), new BindReceiverResp());
        add(new BindTransceiver(), new BindTransceiverResp());
        add(new BindTransmitter(), new BindTransmitterResp());
        add(new BroadcastSM(), new BroadcastSMResp());
        add(new CancelBroadcastSM(), new CancelBroadcastSMResp());
        add(new CancelSM(), new CancelSMResp());
        add(new DataSM(), new DataSMResp());
        add(new DeliverSM(), new DeliverSMResp());
        add(new EnquireLink(), new EnquireLinkResp());
        add(new GenericNack());
        add(new Outbind());
        add(new ParamRetrieve(), new ParamRetrieveResp());
        add(new QueryBroadcastSM(), new QueryBroadcastSMResp());
        add(new QueryLastMsgs(), new QueryLastMsgsResp());
        add(new QueryMsgDetails(), new QueryMsgDetailsResp());
        add(new QuerySM(), new QuerySMResp());
        add(new ReplaceSM(), new ReplaceSMResp());
        add(new SubmitMulti(), new SubmitMultiResp());
        add(new SubmitSM(), new SubmitSMResp());
        add(new Unbind(), new UnbindResp());
    }

    /**
     * Create a new instance of the appropriate sub class of SMPPPacket. Packet
     * fields are all left at their default initial state.
     * 
     * @param id
     *            The SMPP command ID of the packet type to return.
     * @return A sub-class instance of {@link org.mobicents.protocols.smpp.message.SMPPPacket}
     *         representing SMPP command <code>id</code>.
     * @throws org.mobicents.protocols.smpp.BadCommandIDException
     *             if the command ID is not recognized.
     */
    public SMPPPacket newInstance(int id) {
        return newInstance(id, null);
    }
    
    /**
     * Get a response packet for the specified request. The returned response
     * packet will have its sequence number initialised to the same value
     * as <code>packet</code>.
     * @param packet The request packet to get a response for.
     * @return An SMPP response packet.
     * @throws BadCommandIDException If there is no response packet for the
     * specified request (for example, an <code>AlertNotification</code>).
     * @throws SMPPRuntimeException If an attempt is made to create a
     * response to a response packet.
     */
    public SMPPPacket newResponse(SMPPPacket packet) {
        if (packet.isResponse()) {
            throw new SMPPRuntimeException(
                    "Cannot create a response to a response!");
        }
        int id = packet.getCommandId();
        SMPPPacket response = newInstance(id | 0x80000000, packet);
        response.setSequenceNum(packet.getSequenceNum());
        return response;
    }

    /**
     * Register a vendor packet with the factory. The SMPP allows for
     * vendor-specific packets to be defined. In order for these to be
     * usable with the API, primarily so that they can be identified and
     * decoded when received from an SMSC, they must be registered with
     * the packet factory.
     * <p>
     * This implementation assumes that the ID of the response packet will
     * be the ID of the request packet ORed with <code>0x80000000</code>.
     * This implementation also accepts <code>null</code> for the
     * <code>responseType</code> since there is at least one incidence in
     * the specification of such a case (<code>AlertNotification</code> has
     * no response packet).
     * </p>
     * @param id The command ID of the request packet.
     * @param requestType The class which implements the vendor request packet.
     * @param responseType The class which implements the vendor response
     * packet.
     */
    public void registerVendorPacket(int id,
            Class<? extends SMPPPacket> requestType,
            Class<? extends SMPPPacket> responseType) {
        userCommands.put(Integer.valueOf(id), requestType);
        if (responseType != null) {
            userCommands.put(
                    Integer.valueOf(id | 0x80000000), responseType);
        }
    }

    /**
     * Remove a vendor packet definition from this factory.
     * @param id The ID of the vendor packet to remove. This will also
     * unregister the response packet if it exists.
     */
    public void unregisterVendorPacket(int id) {
        userCommands.remove(Integer.valueOf(id));
        userCommands.remove(Integer.valueOf(id | 0x80000000));
    }

    /**
     * Add an internal API-defined packet type.
     * @param command The request packet to add.
     */
    private void add(SMPPPacket command) {
        int commandId = command.getCommandId();
        Class<? extends SMPPPacket> commandClass = command.getClass();
        commands.put(Integer.valueOf(commandId), commandClass);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Mapping id 0x{} to class {}",
                    Integer.toHexString(commandId), commandClass.getName());
        }
    }
    
    /**
     * Add an internal API-defined packet type.
     * @param requestClass The request packet to add.
     * @param responseClass The response packet to add.
     */
    private void add(SMPPPacket request, SMPPPacket response) {
        add(request);
        if (response != null) {
            add(response);
        }
    }

    /**
     * Get a new instance of an SMPP packet for the specified ID.
     * @param id The command ID to get the packet object for.
     * @param request If a response packet is being created, this parameter
     * may be optionally supplied and an attempt will be made to call a
     * constructor which accepts an SMPPPacket as its argument. All of the
     * response packets that are supplied as part of the API have such
     * a constructor.
     * @return A new instance of the relevant SMPPPacket implementation.
     * @throws BadCommandIDException If no matching class can be found for
     * <code>id</code>.
     * @throws SMPPRuntimeException If a packet&apos;s constructor throws
     * an exception.
     */
    private SMPPPacket newInstance(int id, SMPPPacket request) {
        SMPPPacket response = null;
        Class<? extends SMPPPacket> clazz = getClassForId(id);
        if (clazz == null) {
            throw new BadCommandIDException(
                    "Unrecognized command id " + Integer.toHexString(id), id);
        }
        try {
            if (request != null) {
                response = constructWithPacketArg(clazz, request);
            }
            if (response == null) {
                response = clazz.newInstance();
            }
        } catch (Exception x) {
            throw new SMPPRuntimeException(
                    "Packet constructor threw an exception.", x);
        }
        return response;
    }
    
    /**
     * Construct an SMPPPacket implementation class using a single-argument
     * constructor which takes an SMPPPacket object as its argument.
     * @param clazz The class to instantiate.
     * @param request The object to pass to the constructor.
     * @return The instantiated class, or <code>null</code> if the class does
     * not implement a single-argument constructor which accepts an SMPPPacket.
     * @throws Exception Any exception that is thrown by
     * {@link Constructor#newInstance(java.lang.Object[])} can be thrown
     * by this method.
     */
    private SMPPPacket constructWithPacketArg(
            Class<? extends SMPPPacket> clazz,
            SMPPPacket request) throws Exception {
        SMPPPacket packet = null;
        try {
            Constructor<? extends SMPPPacket> cons = clazz.getConstructor(
                    new Class[] {SMPPPacket.class});
            packet = cons.newInstance(
                    new Object[] {request});
        } catch (NoSuchMethodException x) {
            LOG.debug("No SMPPPacket constructor; will fall back to default.");
        }
        return packet;
    }
    
    /**
     * Get the implementation class for SMPP <code>commandId</code>.
     * The internally supplied SMPPPacket implementations will be queried
     * first, followed by all registered vendor packets.
     * @param commandId The command ID of the packet to get.
     * @return The implementing class, or <code>null</code> if there is
     * no class for the specified command ID.
     */
    private Class<? extends SMPPPacket> getClassForId(int commandId) {
        Integer id = Integer.valueOf(commandId);
        Class<? extends SMPPPacket> clazz = commands.get(id);
        if (clazz == null) {
            clazz = userCommands.get(id);
        }
        return clazz;
    }
}
