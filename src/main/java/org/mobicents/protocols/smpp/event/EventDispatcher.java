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

import java.util.Collection;
import java.util.Iterator;

import org.mobicents.protocols.smpp.Session;
import org.mobicents.protocols.smpp.message.SMPPPacket;

/**
 * This interface defines the observable side of the observer pattern for
 * asynchronous SMPP event notification. Each {@link com.adenki.smpp.Session}
 * object will have an implementation of the <code>EventDispatcher</code>
 * interface which it uses to deliver events to interested listeners. By
 * removing the actual dispatching of events from the internals of the
 * Connection, applications may provide their own event dispatch implementations
 * for their <code>Connection</code> objects which better suit how those
 * applications work.
 * 
 * @version $Id: EventDispatcher.java 457 2009-01-15 17:37:42Z orank $
 * @see com.adenki.smpp.event.SimpleEventDispatcher
 */
public interface EventDispatcher {

    /**
     * Initialise the event dispatcher. The <code>init</code> method will be
     * called by the <code>Connection</code> before it makes any attempt to
     * add any observers or deliver any events via the dispatcher.
     */
    void init();

    /**
     * Event dispatcher clean up. The <code>destroy</code> method will be
     * called by the <code>Connection</code> when it is finished delivering
     * events to it and the receiver daemon thread is exiting. Any initialising
     * done in the <code>init</code> method can be cleaned up here.
     * <p>
     * The <code>destroy</code> method <b>must not </b> interfere with the
     * delivery of any events notified to the event dispatcher before the call
     * to this method.
     * </p>
     */
    void destroy();

    /**
     * Add an observer to this event dispatcher.
     * 
     * @param observer
     *            the observer object to add.
     */
    void addObserver(SessionObserver observer);

    /**
     * Remove an observer from this event dispatcher.
     * 
     * @param observer
     *            the observer object to remove from the registered observers.
     */
    void removeObserver(SessionObserver observer);

    /**
     * Get a read-only collection view of all the observers registered with
     * this event dispatcher. This is useful for copying all of the observers
     * registered on one EventDispatcher into another.
     * @return A read-only collection of all the observers registered with
     * this dispatcher.
     */
    Collection<SessionObserver> getObservers();
    
    /**
     * Get an iterator over the currently registered observers.
     * 
     * @return an iterator object which iterates over all registered observers.
     */
    Iterator<SessionObserver> observerIterator();

    /**
     * Notify all registered observers of an SMPP event.
     * 
     * @param event
     *            the SMPP event to notify observers of.
     */
    void notifyObservers(Session conn, SMPPEvent event);

    /**
     * Notify all registered observers of a received SMPP packet.
     * 
     * @param packet
     *            the SMPP packet to notify observers of.
     */
    void notifyObservers(Session conn, SMPPPacket packet);
}

