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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.easymock.EasyMock;
import org.testng.annotations.Test;

import org.mobicents.protocols.smpp.Session;
import org.mobicents.protocols.smpp.event.ReceiverExitEvent;
import org.mobicents.protocols.smpp.event.SMPPEvent;
import org.mobicents.protocols.smpp.event.SessionObserver;
import org.mobicents.protocols.smpp.event.TaskExecutorEventDispatcher;
import org.mobicents.protocols.smpp.message.EnquireLink;
import org.mobicents.protocols.smpp.message.SMPPPacket;

@Test
public class TaskExecutorEventDispatcherTest {

    public void testNotifyEventWithDefaultExecutor() throws Exception {
        TaskExecutorEventDispatcher dispatcher = new TaskExecutorEventDispatcher();
        dispatcher.setThreadCount(1);
        dispatcher.init();
        SessionObserver observer1 = EasyMock.createMock(SessionObserver.class);
        observer1.update(
                (Session) EasyMock.isNull(),
                EasyMock.isA(SMPPEvent.class));
        EasyMock.replay(observer1);
        dispatcher.addObserver(observer1);
        dispatcher.notifyObservers(null, new ReceiverExitEvent(null));
        ExecutorService executorService =
            (ExecutorService) dispatcher.getExecutor();
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        EasyMock.verify(observer1);
    }
    
    public void testNotifyPacketWithDefaultExecutor() throws Exception {
        TaskExecutorEventDispatcher dispatcher = new TaskExecutorEventDispatcher();
        dispatcher.setThreadCount(1);
        dispatcher.init();
        SessionObserver observer1 = EasyMock.createMock(SessionObserver.class);
        observer1.packetReceived(
                (Session) EasyMock.isNull(),
                EasyMock.isA(SMPPPacket.class));
        EasyMock.replay(observer1);
        dispatcher.addObserver(observer1);
        dispatcher.notifyObservers(null, new EnquireLink());
        ExecutorService executorService =
            (ExecutorService) dispatcher.getExecutor();
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        EasyMock.verify(observer1);
    }
}
