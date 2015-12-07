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

package org.mobicents.protocols.smpp;

import java.io.IOException;
import java.net.UnknownHostException;

import org.mobicents.protocols.smpp.Address;
import org.mobicents.protocols.smpp.Session;
import org.mobicents.protocols.smpp.encoding.AlphabetEncoding;
import org.mobicents.protocols.smpp.encoding.DefaultAlphabetEncoding;
import org.mobicents.protocols.smpp.event.SMPPEvent;
import org.mobicents.protocols.smpp.event.SessionObserver;
import org.mobicents.protocols.smpp.message.Bind;
import org.mobicents.protocols.smpp.message.BindTransmitter;
import org.mobicents.protocols.smpp.message.DataSM;
import org.mobicents.protocols.smpp.message.SMPPPacket;
import org.mobicents.protocols.smpp.message.SubmitSM;
import org.mobicents.protocols.smpp.message.tlv.Tag;

public class MyExample implements SessionObserver {

	// private static final Logger STATIC_LOG =
	// LoggerFactory.getLogger(MyExample.class);

	// java.util.logging.Logger logger;
	private String hostname = "192.168.0.105";
	private int port = 2775;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MyExample example = new MyExample();
		example.test();

		try {
			Thread.sleep(1000 * 60 * 5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void test() {
		try {

			// java.util.logging.LogManager logManager =
			// java.util.logging.LogManager.getLogManager();
			// java.util.logging.Logger logger = logManager.getLogger("");
			// logger.setLevel(java.util.logging.Level.ALL);
			//			
			// logger.fine("Starting");

			Session session = createSession();
			session.addObserver(this);

			bind(session);

			Thread.sleep(1000);

			Address destination = new Address();
			destination.setAddress("919960639901");
			destination.setTON(1);
			destination.setNPI(0);

			sendSubmitSMMessage(session, destination, "Hello World");

			Thread.sleep(1000);

			sendDataSMMessage(session, destination);
			
			Thread.sleep(1000 * 60);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Session createSession() throws UnknownHostException {
		return new Session(hostname, port);
	}

	private void bind(Session session) throws IOException {
		BindTransmitter bt = new BindTransmitter();
		initBindReq(bt);
		session.bind(bt);
	}

	protected Bind initBindReq(Bind bindRequest) {
		bindRequest.setAddressTon(1);
		bindRequest.setAddressNpi(0);
		bindRequest.setAddressRange("50");
		bindRequest.setSystemType("ESME");
		bindRequest.setSystemId("smppclient1");
		bindRequest.setPassword("password");
		return bindRequest;
	}

	private void sendSubmitSMMessage(Session session, Address destination,
			String message) throws IOException {
		sendSubmitSMMessage(session, destination, message,
				new DefaultAlphabetEncoding());
	}

	private void sendSubmitSMMessage(Session session, Address destination,
			String message, AlphabetEncoding encoding) throws IOException {
		SubmitSM submitSm = new SubmitSM();
		submitSm.setDestination(destination);
		submitSm.setDataCoding(encoding.getDataCoding());
		submitSm.setMessage(encoding.encode(message));
		session.sendPacket(submitSm);
	}

	private void sendDataSMMessage(Session session, Address destination)
			throws IOException {
		sendDataSMMessage(session, destination, new DefaultAlphabetEncoding());
	}

	private void sendDataSMMessage(Session session, Address destination,
			AlphabetEncoding encoding) throws IOException {
		DataSM dataSm = new DataSM();
		dataSm.setDestination(destination);
		dataSm.setDataCoding(encoding.getDataCoding());
		dataSm.setTLV(Tag.MESSAGE_PAYLOAD,
				"Hello world?".getBytes());

		session.sendPacket(dataSm);
	}

	public void packetReceived(Session arg0, SMPPPacket arg1) {
		System.out.println("Packet Received " + arg1);

	}

	public void update(Session arg0, SMPPEvent arg1) {
		System.out.println("Update called " + arg1.toString());
	}

}
