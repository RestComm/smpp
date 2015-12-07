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

package org.mobicents.protocols.smpp.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.mobicents.protocols.smpp.Session;
import org.mobicents.protocols.smpp.message.SMPPPacket;

/**
 * A simple implementation of the event dispatcher interface. This
 * implementation simply iterates over the set of registered observers and
 * notifies each of the event in turn. This means that whatever thread the
 * <code>Connection</code> object uses to call into this object will be
 * blocked, from the <code>Connection</code>'s point of view, until every
 * observer has successfully processed the event.
 * 
 * <p>
 * Adding and removing observers from an instance of this class is protected
 * against multi-threaded access. However, event and packet notification
 * is not. If an event notification is currently in progress and another
 * thread modifies the set of registered observers, then it is possible for
 * the new observer to receive events before the call to <code>addObserver
 * </code> is complete.
 * </p>
 * 
 * @version $Id: SimpleEventDispatcher.java 457 2009-01-15 17:37:42Z orank $
 * @see com.adenki.smpp.event.EventDispatcher
 */
public class SimpleEventDispatcher extends AbstractEventDispatcher {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleEventDispatcher.class);
    
    /**
     * Create a new SimpleEventDispatcher.
     */
    public SimpleEventDispatcher() {
    }

    /**
     * Create a new SimpleEventDispatcher and register one observer on it.
     */
    public SimpleEventDispatcher(SessionObserver observer) {
        addObserver(observer);
    }

    public void init() {
        // nothing to do.
    }

    public void destroy() {
        // nothing to do.
    }

    /**
     * Notify registered observers of an SMPP event.
     * @param conn the Connection with which the event is associated.
     * @param event the SMPP event to notify observers of.
     */
    public void notifyObservers(Session conn, SMPPEvent event) {
        SessionObserver[] observerList = getObserverList();
        for (SessionObserver observer : observerList) {
            try {
                observer.update(conn, event);
            } catch (Exception x) {
                LOG.error("An observer threw an exception during event processing", x);
            }
        }
    }

    /**
     * Notify registered observers of an incoming SMPP packet.
     * @param conn  the Connection which the packet was received on.
     * @param packet the received packet to notify observers of.
     */
    public void notifyObservers(Session conn, SMPPPacket packet) {
        SessionObserver[] observerList = getObserverList();
        for (SessionObserver observer : observerList) {
            try {
                observer.packetReceived(conn, packet);
            } catch (Exception x) {
                LOG.error("An observer threw an exception during packet processing", x);
            }
        }
    }
}
