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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.mobicents.protocols.smpp.SMPPRuntimeException;

/**
 * Base implementation of the {@link APIConfig} which reads its properties
 * from a Java properties file, loaded from a predefined location.
 * 
 * <p>
 * When initialised, this implementation searches for a file named
 * "smppapi.properties". This file needs to be on the classpath in
 * one of the following locations:
 * </p>
 * <ol>
 * <li>smppapi.properties</li>
 * <li>com/smppapi.properties</li>
 * <li>com/adenki/smppapi.properties</li>
 * <li>com/adenki/smpp/smppapi.properties</li>
 * <li>com/adenki/smpp/util/smppapi.properties</li>
 * </ol>
 * @version $Id: PropertiesAPIConfig.java 475 2009-07-12 17:30:47Z orank $
 */
public class PropertiesAPIConfig extends AbstractAPIConfig implements APIConfig, Serializable {
    private static final long serialVersionUID = 2L;
    private static final Logger LOG =
        LoggerFactory.getLogger(PropertiesAPIConfig.class);

    /**
     * Paths to search for the API properties file. These should always end in
     * the '/' character.
     */
    private static final String[] SEARCH_PATH = {
        "",
        "com/",
        "com/adenki/",
        "com/adenki/smpp/",
        "com/adenki/smpp/util/",
    };

    /**
     * Name of the resource to load properties from.
     */
    private static final String PROPS_RESOURCE = "smppapi.properties";

    /**
     * The URL that API properties are loaded from (including path info).
     */
    private URL propsURL;
    private Properties properties = new Properties();
    
    /**
     * Construct a new APIConfig object which reads properties from the
     * default properties resource.
     */
    public PropertiesAPIConfig() {
        this.propsURL = getDefaultPropertiesResource();
    }
    
    /**
     * Construct a new APIConfig object which reads properties from the
     * specified URL.
     * @param propertiesURL The URL to read properties from.
     */
    public PropertiesAPIConfig(URL propertiesURL) {
        this.propsURL = propertiesURL;
    }

    @Override
    public boolean isSet(String property) {
        return properties.containsKey(property);
    }
    
    public void initialise() {
        LOG.debug("Initialising API properties.");
        try {
            loadAPIProperties();
        } catch (IOException x) {
            throw new SMPPRuntimeException("Could not load API config", x);
        }
    }
    
    public boolean reloadAPIConfig() {
        LOG.debug("Reloading API config properties.");
        try {
            properties.clear();
            loadAPIProperties();
        } catch (IOException x) {
            LOG.warn("Could not reload API properties.", x);
            return false;
        }
        return true;
    }

    /**
     * Reconfigure this <tt>PropertiesAPIConfig</tt> to load its properties
     * from a new URL, and reload the configuration.
     * @param newURL The new URL to load properties from.
     * @throws IOException If the properties cannot be loaded from the
     * <tt>newURL</tt>. If this exception is thrown, the API configuration
     * will be left in an indeterminate state.
     */
    public void reconfigure(URL newURL) throws IOException {
        propsURL = newURL;
        properties.clear();
        loadAPIProperties();
    }
    
    public String getProperty(String property) throws PropertyNotFoundException {
        String val = properties.getProperty(property);
        if (val == null) {
            throw new PropertyNotFoundException(property);
        } else {
            return val;
        }
    }

    public void setProperty(String property, String value) {
        properties.setProperty(property, value);
    }
    
    public Object remove(String property) {
        return properties.remove(property);
    }
    
    /**
     * Try to locate the default smppapi properties resource.
     * @return A URL pointing to the default properties resource, or
     * <code>null</code> if it cannot be found.
     */
    private URL getDefaultPropertiesResource() {
        URL url = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = getClass().getClassLoader();
        }
        for (int i = 0; i < SEARCH_PATH.length && url == null; i++) {
            url = loader.getResource(SEARCH_PATH[i] + PROPS_RESOURCE);
        }
        return url;
    }

    /**
     * Load the properties.
     */
    private void loadAPIProperties() throws IOException {
        if (propsURL != null) {
            properties.load(propsURL.openStream());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Loaded API properties from {}", propsURL);
                StringWriter w = new StringWriter();
                properties.list(new PrintWriter(w));
                LOG.debug("\n" + w.toString());
            }
        }
    }
}
