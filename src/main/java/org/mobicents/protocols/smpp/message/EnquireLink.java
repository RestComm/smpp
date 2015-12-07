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

/**
 * Check the link status. This message can originate from either an ESME or the
 * SMSC. It is used to check that the entity at the other end of the link is
 * still alive and responding to messages. Usually used by the SMSC after a
 * period of inactivity to decide whether to close the link.
 * 
 * @version $Id: EnquireLink.java 452 2009-01-15 16:56:36Z orank $
 */
public class EnquireLink extends SMPPPacket {
    private static final long serialVersionUID = 2L;

    /**
     * Construct a new EnquireLink.
     */
    public EnquireLink() {
        super(CommandId.ENQUIRE_LINK);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() * 47;
    }
}

