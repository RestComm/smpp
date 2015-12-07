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

/**
 * Implementation of {@link SMPPDate} representing a relative time
 * specification.
 * 
 * @version $Id: RelativeSMPPDate.java 452 2009-01-15 16:56:36Z orank $
 */
class RelativeSMPPDate extends SMPPDate {
    private static final long serialVersionUID = 2L;
    private int years;
    private int months;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;

    public RelativeSMPPDate(int years,
            int months,
            int days,
            int hours,
            int minutes,
            int seconds) {
        this.years = years;
        this.months = months;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getDay() {
        return days;
    }

    public int getHour() {
        return hours;
    }

    public int getMinute() {
        return minutes;
    }

    public int getMonth() {
        return months;
    }

    public int getSecond() {
        return seconds;
    }

    public int getYear() {
        return years;
    }
    
    public char getSign() {
        return 'R';
    }
    
    public boolean isRelative() {
        return true;
    }
    
    @Override
    public int getLength() {
        return 17;
    }
    
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RelativeSMPPDate)) {
            return false;
        }
        RelativeSMPPDate other = (RelativeSMPPDate) obj;
        return years == other.years
            && months == other.months
            && days == other.days
            && hours == other.hours
            && minutes == other.minutes
            && seconds == other.seconds;
    }

    public int hashCode() {
        long val = (long) years * 10000000000L;
        val += (long) months * 100000000L;
        val += (long) days * 1000000L;
        val += (long) hours * 10000L;
        val += (long) minutes * 100;
        val += seconds;
        return new Long(val).hashCode();
    }
}
