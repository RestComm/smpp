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

package org.mobicents.protocols.smpp.message.tlv;

import org.mobicents.protocols.smpp.message.param.BitmaskParamDescriptor;
import org.mobicents.protocols.smpp.message.param.BytesParamDescriptor;
import org.mobicents.protocols.smpp.message.param.CStringParamDescriptor;
import org.mobicents.protocols.smpp.message.param.DateParamDescriptor;
import org.mobicents.protocols.smpp.message.param.IntegerParamDescriptor;
import org.mobicents.protocols.smpp.message.param.NullParamDescriptor;
import org.mobicents.protocols.smpp.message.param.ParamDescriptor;

/**
 * A static class holding some basic parameter descriptors, used
 * primarily by the {@link Tag} class.
 * @version $Id: BasicDescriptors.java 452 2009-01-15 16:56:36Z orank $
 * @since 0.4.0
 */
public class BasicDescriptors {
    public static final ParamDescriptor INTEGER1 = new IntegerParamDescriptor(1);
    public static final ParamDescriptor INTEGER2 = new IntegerParamDescriptor(2);
    public static final ParamDescriptor INTEGER4 = new IntegerParamDescriptor(4);
    public static final ParamDescriptor INTEGER8 = new IntegerParamDescriptor(8);
    public static final ParamDescriptor BYTES = new BytesParamDescriptor();
    public static final ParamDescriptor CSTRING = new CStringParamDescriptor();
    public static final ParamDescriptor BITMASK = new BitmaskParamDescriptor();
    public static final ParamDescriptor DATE = new DateParamDescriptor();
    public static final ParamDescriptor NULL = new NullParamDescriptor();
}
