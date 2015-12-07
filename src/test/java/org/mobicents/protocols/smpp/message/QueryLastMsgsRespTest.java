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

import org.mobicents.protocols.smpp.message.QueryLastMsgsResp;
import org.testng.annotations.Test;

@Test
public class QueryLastMsgsRespTest extends PacketTests<QueryLastMsgsResp> {

    protected Class<QueryLastMsgsResp> getPacketType() {
        return QueryLastMsgsResp.class;
    }
    
    @Override
    protected QueryLastMsgsResp getInitialisedPacket() {
        QueryLastMsgsResp packet = new QueryLastMsgsResp();
        packet.addMessageId("messageId1");
        packet.addMessageId("messageId2");
        packet.addMessageId("messageId3");
        packet.addMessageId("messageId4");
        packet.addMessageId("messageId5");
        return packet;
    }
}
