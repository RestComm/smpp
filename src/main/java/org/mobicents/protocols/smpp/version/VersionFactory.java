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

package org.mobicents.protocols.smpp.version;

import org.mobicents.protocols.smpp.util.APIConfig;
import org.mobicents.protocols.smpp.util.APIConfigFactory;
import org.mobicents.protocols.smpp.util.PropertyNotFoundException;

/**
 * Factory class for SMPP versions.
 * @version $Id: VersionFactory.java 452 2009-01-15 16:56:36Z orank $
 */
public class VersionFactory {
    /**
     * Get the default SMPP version implemented by this API. This APIs
     * default is currently version 3.4.
     * @return The default SMPP version.
     */
    public static SMPPVersion getDefaultVersion() {
        APIConfig cfg = APIConfigFactory.getConfig();
        try {
            int versionNum = cfg.getInt(APIConfig.DEFAULT_VERSION);
            return VersionFactory.getVersion(versionNum);
        } catch (PropertyNotFoundException x) {
            return SMPPVersion.VERSION_5_0;
        }
    }

    /**
     * Get the SMPP version for a particular version ID.
     * @param id The version ID to get the SMPP version for,
     * @return The matching SMPP version.
     * @throws VersionException If the version ID is not known by this
     * factory.
     */
    public static SMPPVersion getVersion(int id) {
        if (id == SMPPVersion.VERSION_3_3.getVersionID()) {
            return SMPPVersion.VERSION_3_3;
        } else if (id == SMPPVersion.VERSION_3_4.getVersionID()) {
            return SMPPVersion.VERSION_3_4;
        } else if (id == SMPPVersion.VERSION_5_0.getVersionID()) {
            return SMPPVersion.VERSION_5_0;
        } else {
            APIConfig cfg = APIConfigFactory.getConfig();
            if (cfg.getBoolean(APIConfig.LAX_VERSIONS, false)) {
                if (id >= 0x00 && id <= 0x32) {
                    return SMPPVersion.VERSION_3_3;
                }
            }
        }
        throw new VersionException("Unknown version id: 0x"
                + Integer.toHexString(id));
    }
}
