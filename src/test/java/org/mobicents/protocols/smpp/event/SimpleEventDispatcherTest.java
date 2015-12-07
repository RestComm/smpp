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

import static org.testng.Assert.assertTrue;

import org.easymock.EasyMock;
import org.testng.annotations.Test;

import org.mobicents.protocols.smpp.Session;
import org.mobicents.protocols.smpp.event.ReceiverExitEvent;
import org.mobicents.protocols.smpp.event.SessionObserver;
import org.mobicents.protocols.smpp.event.SimpleEventDispatcher;
import org.mobicents.protocols.smpp.message.SMPPPacket;
import org.mobicents.protocols.smpp.message.SubmitSM;

/**
 * @version $Id:$
 */
@Test
public class SimpleEventDispatcherTest {
    
    public void testConstructWithObserver() {
        SessionObserver observer1 = EasyMock.createMock(SessionObserver.class);
        EasyMock.replay(observer1);
        SimpleEventDispatcher dispatcher = new SimpleEventDispatcher(observer1);
        assertTrue(dispatcher.contains(observer1));
        EasyMock.verify(observer1);
    }
    
    public void testNotifyOfEvent() {
        SessionObserver observer1 = EasyMock.createMock(SessionObserver.class);
        observer1.update(
                (Session) EasyMock.isNull(),
                EasyMock.isA(ReceiverExitEvent.class));
        EasyMock.replay(observer1);
        SimpleEventDispatcher dispatcher = new SimpleEventDispatcher(observer1);
        dispatcher.notifyObservers(null, new ReceiverExitEvent(null));
        EasyMock.verify(observer1);
    }
    
    public void testNotifyOfPacket() {
        SessionObserver observer1 = EasyMock.createMock(SessionObserver.class);
        observer1.packetReceived(
                (Session) EasyMock.isNull(),
                EasyMock.isA(SMPPPacket.class));
        EasyMock.replay(observer1);
        SimpleEventDispatcher dispatcher = new SimpleEventDispatcher(observer1);
        assertTrue(dispatcher.contains(observer1));
        dispatcher.notifyObservers(null, new SubmitSM());
        EasyMock.verify(observer1);
    }
}
