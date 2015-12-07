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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.easymock.EasyMock;
import org.mobicents.protocols.smpp.event.SessionObserver;
import org.mobicents.protocols.smpp.event.SimpleEventDispatcher;
import org.testng.annotations.Test;

@Test
public class AbstractEventDispatcherTest {

    public void testAddingAndRemovingObservers() throws Exception {
        SessionObserver observer1 = EasyMock.createMock(SessionObserver.class);
        EasyMock.replay(observer1);
        SessionObserver observer2 = EasyMock.createMock(SessionObserver.class);
        EasyMock.replay(observer2);
        SimpleEventDispatcher dispatcher = new SimpleEventDispatcher();
        assertEquals(dispatcher.size(), 0);
        assertFalse(dispatcher.contains(observer1));
        assertFalse(dispatcher.contains(observer2));
        dispatcher.addObserver(observer1);
        dispatcher.addObserver(observer2);
        assertEquals(dispatcher.size(), 2);
        assertTrue(dispatcher.contains(observer1));
        assertTrue(dispatcher.contains(observer2));
        dispatcher.addObserver(observer1);
        assertEquals(dispatcher.size(), 2);
        assertTrue(dispatcher.contains(observer1));
        assertTrue(dispatcher.contains(observer2));
        dispatcher.removeObserver(observer1);
        assertEquals(dispatcher.size(), 1);
        assertFalse(dispatcher.contains(observer1));
        assertTrue(dispatcher.contains(observer2));
        dispatcher.removeObserver(observer2);
        assertEquals(dispatcher.size(), 0);
        assertFalse(dispatcher.contains(observer1));
        assertFalse(dispatcher.contains(observer2));
        EasyMock.verify(observer1);
        EasyMock.verify(observer2);
    }
    
    public void testGetAllObserversReturnsCollectionOfObservers() {
        SessionObserver observer1 = EasyMock.createMock(SessionObserver.class);
        EasyMock.replay(observer1);
        SessionObserver observer2 = EasyMock.createMock(SessionObserver.class);
        EasyMock.replay(observer2);
        SimpleEventDispatcher dispatcher = new SimpleEventDispatcher();
        dispatcher.addObserver(observer1);
        dispatcher.addObserver(observer2);
        Collection<SessionObserver> observers = dispatcher.getObservers();
        assertNotNull(observers);
        assertEquals(observers.size(), 2);
        assertTrue(observers.contains(observer1));
        assertTrue(observers.contains(observer2));
        EasyMock.verify(observer1);
        EasyMock.verify(observer2);
    }

    @Test(expectedExceptions = {UnsupportedOperationException.class})
    public void testGetAllObserversReturnsUnmodifiableCollection() {
        SimpleEventDispatcher dispatcher = new SimpleEventDispatcher();
        Collection<SessionObserver> observers = dispatcher.getObservers();
        assertNotNull(observers);
        SessionObserver observer1 = EasyMock.createMock(SessionObserver.class);
        EasyMock.replay(observer1);
        observers.add(observer1);
        EasyMock.verify(observer1);
    }
    
    @Test(expectedExceptions = {UnsupportedOperationException.class})
    public void testObserverIteratorIsUnmodifiable() {
        SimpleEventDispatcher dispatcher = new SimpleEventDispatcher();
        SessionObserver observer1 = EasyMock.createMock(SessionObserver.class);
        EasyMock.replay(observer1);
        dispatcher.addObserver(observer1);
        Iterator<SessionObserver> iter = dispatcher.observerIterator();
        assertNotNull(iter);
        assertTrue(iter.hasNext());
        assertNotNull(iter.next());
        iter.remove();
    }
}
