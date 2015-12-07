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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parent class for event dispatchers that maintain their observers in a
 * <code>java.util.List</code>.
 * @version $Id: AbstractEventDispatcher.java 452 2009-01-15 16:56:36Z orank $
 */
public abstract class AbstractEventDispatcher implements EventDispatcher {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractEventDispatcher.class);
    
    private List<SessionObserver> observers =
        new ArrayList<SessionObserver>();

    public void addObserver(SessionObserver observer) {
        synchronized (observers) {
            if (!observers.contains(observer)) {
                observers.add(observer);
            } else {
                LOG.info("Not adding observer because it's already registered");
            }
        }
    }

    public void removeObserver(SessionObserver observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public Collection<SessionObserver> getObservers() {
        return Collections.unmodifiableCollection(observers);
    }
    
    public Iterator<SessionObserver> observerIterator() {
        return Collections.unmodifiableList(observers).iterator();
    }

    public boolean contains(SessionObserver observer) {
        return observers.contains(observer);
    }

    public int size() {
        return observers.size();
    }

    /**
     * Get the list of observers as an array.
     * @return An array of all registered observers.
     */
    protected SessionObserver[] getObserverList() {
        SessionObserver[] observerList =
            observers.toArray(new SessionObserver[0]);
        return observerList;
    }
}
