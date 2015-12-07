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

import org.mobicents.protocols.smpp.util.APIConfig;
import org.mobicents.protocols.smpp.util.AbstractAPIConfig;
import org.mobicents.protocols.smpp.util.PropertyNotFoundException;

/**
 * An object that implements {@link APIConfig} but does nothing.
 * @version $Id: NullAPIConfig.java 475 2009-07-12 17:30:47Z orank $
 */
public class NullAPIConfig extends AbstractAPIConfig implements APIConfig {

    public String getProperty(String property) throws PropertyNotFoundException {
        throw new PropertyNotFoundException();
    }

    public void initialise() {
    }

    public boolean reloadAPIConfig() {
        return false;
    }
}
