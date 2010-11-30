/*
 * Copyright 1999-2006 University of Chicago
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.globus.ws.enumeration;

import java.util.Calendar;

import org.globus.util.I18n;

import org.apache.axis.types.Duration;

/**
 * Represents the expiration time of an enumeration context. The expiration
 * time of an enumeration context can be either expressed as a specific 
 * time/date or as a duration.
 */
public class EnumExpiration {

    private static I18n i18n =
        I18n.getI18n("org.globus.ws.enumeration.error");

    private Calendar calendar;
    private Duration duration;
    
    /**
     * Creates <tt>EnumExpiration</tt> with a given time/date.
     *
     * @param calendar the specific time/date on which the context will expire.
     *        Cannot be null.
     */
    public EnumExpiration(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "calendar"));
        }
        this.calendar = calendar;
    }

    /**
     * Creates <tt>EnumExpiration</tt> with a given duration.
     *
     * @param duration the duration after which the context will expire. 
     *        Cannot be null.
     */
    public EnumExpiration(Duration duration) {
        if (duration == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "duration"));
        }
        this.duration = duration;
    }

    /**
     * Sets expiration with a given a time/date.
     *
     * @param calendar the specific time/date on which the context will expire.
     *        Cannot be null.
     */
    public void setCalendar(Calendar calendar) {
        if (calendar != null && this.duration != null) {
            throw new IllegalArgumentException(
                                  i18n.getMessage("durationAlreadySet"));
        }
        this.calendar = calendar;
    }

    /**
     * Gets the time/date of the expiration.
     *
     * @return the time/date of the expiration
     */
    public Calendar getCalendar() {
        return this.calendar;
    }

    /**
     * Sets expiration with a given duration.
     *
     * @param duration the duration after which the context will expire. 
     *        Cannot be null.
     */
    public void setDuration(Duration duration) {
        if (duration != null && this.calendar != null) {
            throw new IllegalArgumentException(
                                  i18n.getMessage("timeAlreadySet"));
        }
        this.duration = duration;
    }

    /**
     * Gets the duration of the expiration.
     *
     * @return the duration of the expiration
     */
    public Duration getDuration() {
        return this.duration;
    }

    public String toString() {
        if (this.calendar != null) {
            return this.calendar.getTime().toString();
        } else if (this.duration != null) {
            return this.duration.toString();
        } else {
            return "";
        }
    }

}
