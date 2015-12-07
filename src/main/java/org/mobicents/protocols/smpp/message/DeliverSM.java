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

import org.slf4j.LoggerFactory;

import org.mobicents.protocols.smpp.util.SMPPDate;

/**
 * Deliver message. This message is sent from the SMSC to a Receiver ESME to
 * deliver a short message. It is also used to notify an ESME that submitted a
 * message using registered delivery that a message has reached it's end point
 * successfully.
 * 
 * @version $Id: DeliverSM.java 457 2009-01-15 17:37:42Z orank $
 */
public class DeliverSM extends SubmitSM {
    private static final long serialVersionUID = 2L;
    private static final String SPEC_VIOLATION = "Setting the {} on a "
        + "deliver_sm is in violation of the SMPP specification";
    
    /**
     * Construct a new DeliverSM.
     */
    public DeliverSM() {
        super(CommandId.DELIVER_SM);
    }

    /**
     * Setting a delivery time on a deliver_sm is in violation of the SMPP
     * specification.
     */
    public void setDeliveryTime(SMPPDate d) {
        LoggerFactory.getLogger(DeliverSM.class).warn(
                SPEC_VIOLATION, "delivery time");
        super.setDeliveryTime(d);
    }

    /**
     * Setting an expiry time on a deliver_sm is in violation of the SMPP
     * specification.
     */
    public void setExpiryTime(SMPPDate d) {
        LoggerFactory.getLogger(DeliverSM.class).warn(
                SPEC_VIOLATION, "expiry time");
        super.setExpiryTime(d);
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 103;
    }
}
