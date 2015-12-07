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

package org.mobicents.protocols.smpp.util;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.mobicents.protocols.smpp.gsm.GSMError;
import org.mobicents.protocols.smpp.message.MessageState;

/**
 * Look up descriptions of various SMPP codes.
 * @version $Id: APIMessages.java 457 2009-01-15 17:37:42Z orank $
 * @since 0.4.0
 */
public class APIMessages {
    public static final String BUNDLE_PROPERTY = "smppapi.bundle";
    public static final String DEFAULT_BUNDLE_NAME = "smpp_messages";
    
    private static final Logger LOG = LoggerFactory.getLogger(APIMessages.class);
    private static final String PACKET_STATUS_PREFIX = "packet.status.";
    private static final String MESSAGE_STATE_PREFIX = "message.state.";
    private static final String GSM_ERROR_PREFIX = "gsm.errors.";
    
    private ResourceBundle bundle;
    
    public APIMessages() {
        loadBundle();
    }
    
    public String getPacketStatus(int statusCode) {
        StringBuilder resource = new StringBuilder(PACKET_STATUS_PREFIX);
        resource.append("0x");
        resource.append(Integer.toHexString(statusCode).toLowerCase());
        return bundle.getString(resource.toString());
    }

    public String getMessageState(MessageState state) {
        StringBuilder resource = new StringBuilder(MESSAGE_STATE_PREFIX);
        resource.append("0x");
        resource.append(Integer.toHexString(state.getValue()).toLowerCase());
        return bundle.getString(resource.toString());
    }

    public String getGSMError(GSMError error) {
        StringBuilder resource = new StringBuilder(GSM_ERROR_PREFIX);
        resource.append(error.name().toLowerCase());
        return bundle.getString(resource.toString());
    }

    private void loadBundle() {
        try {
            bundle = ResourceBundle.getBundle(getBundleName());
        } catch (MissingResourceException x) {
            LOG.warn("Cannot load API messages.");
            bundle = getDummyBundle();
        }
    }

    private String getBundleName() {
        APIConfig cfg = APIConfigFactory.getConfig();
        return cfg.getProperty(BUNDLE_PROPERTY, DEFAULT_BUNDLE_NAME);
    }
    
    private ResourceBundle getDummyBundle() {
        return new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                return key;
            }
            
            @Override
            public Enumeration<String> getKeys() {
                return new Vector<String>().elements();
            }
        };
    }
}
