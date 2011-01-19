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
package org.globus.axis.utils;

import java.util.Calendar;

import org.apache.axis.types.Duration;

/**
 * Utility functions for converting Axis Duration type into milliseconds, milliseconds
 * into Axis Duration, and other related methods.
 * <BR><BR><B>Note:</B> <I>
 * Most of the conversions are inaccurate because assumptions such as how 
 * many days a month has based on the current clock. </I>
 */
public class DurationUtils {
    
    private static int [] FIELDS = {Calendar.MILLISECOND, 
                                    Calendar.SECOND, 
                                    Calendar.MINUTE,
                                    Calendar.HOUR,
                                    Calendar.DATE,
                                    Calendar.MONTH,
                                    Calendar.YEAR};

    /**
     * Updates calendar with a given duration.
     *
     * @param calendar the calendar to update
     * @param duration the duration to update the calendar with
     */
    public static void updateCalendar(Calendar calendar, Duration duration) {
        if (calendar == null || duration == null) {
            throw new IllegalArgumentException();
        }
        int mul = (duration.isNegative()) ? -1 : 1;

        if (duration.getYears() != 0) {
            calendar.add(Calendar.YEAR, mul*duration.getYears());
        }
        if (duration.getMonths() != 0) {
            calendar.add(Calendar.MONTH, mul*duration.getMonths());
        }
        if (duration.getDays() != 0) {
            calendar.add(Calendar.DATE, mul*duration.getDays());
        }
        if (duration.getHours() != 0) {
            calendar.add(Calendar.HOUR, mul*duration.getHours());
        }
        if (duration.getMinutes() != 0) {
            calendar.add(Calendar.MINUTE, mul*duration.getMinutes());
        }
        if (duration.getSeconds() != 0) {
            double seconds = duration.getSeconds();
            calendar.add(Calendar.SECOND, 
                         mul*(int)seconds);
            calendar.add(Calendar.MILLISECOND,
                         (int)(Math.round(seconds*1000) - (int)seconds*1000));
        }
    }
    
    /**
     * Computes duration between two dates. 
     * 
     * @param startTime the start date
     * @param endTime the end date
     * @return the computed duration of the dates as Axis Duration type
     */
    public static Duration computeDuration(Calendar startTime,
                                           Calendar endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException();
        }
        if (!startTime.getTimeZone().equals(endTime.getTimeZone())) {
            throw new IllegalArgumentException();
        }

        Calendar start = (Calendar)startTime.clone();
        Calendar end = endTime;

        boolean negative = false;
        int step = 1;
        
        if (end.before(start)) {
            negative = true;
            step = -1;
        }

        int [] output = new int[FIELDS.length];
        for (int i=0;i<FIELDS.length;i++) {
            while (start.get(FIELDS[i]) != end.get(FIELDS[i])) {
                start.add(FIELDS[i], step);
                output[i]++;
            }
        }
        
        double seconds = (double)output[0]/1000 + output[1];

        Duration dur = new Duration();
        dur.setNegative(negative);
        dur.setSeconds(seconds);
        dur.setMinutes(output[2]);
        dur.setHours(output[3]);
        dur.setDays(output[4]);
        dur.setMonths(output[5]);
        dur.setYears(output[6]);

        return dur;
    }

    /**
     * Converts Axis Duration type into number of milliseconds.
     * <BR><BR><B>Note:</B> <I>This is inexact conversion</I>.
     *
     * @param duration Axis Duration type
     * @return the duration time in milliseconds
     */
    public static long toMilliseconds(Duration duration) {
        if (duration == null) {
            throw new IllegalArgumentException();
        }
        
        Calendar now = Calendar.getInstance();

        long start = now.getTime().getTime();

        updateCalendar(now, duration);

        long end = now.getTime().getTime();

        return end - start;
    }

    /**
     * Converts milliseconds into Axis Duration type.
     * <BR><BR><B>Note:</B> <I>This is inexact conversion</I>.
     *
     * @param milliseconds number of milliseconds
     * @return the Duration type for the given time
     */
    public static Duration toDuration(long milliseconds) {
        Calendar now = Calendar.getInstance();
        Calendar later = (Calendar)now.clone();
      
        int seconds = (int)(milliseconds / 1000);
        later.add(Calendar.SECOND, seconds);
        int mseconds = (int)(milliseconds % 1000);
        later.add(Calendar.MILLISECOND, mseconds);
        
        return computeDuration(now, later);
    }
    
    /**
     * Returns true if the specified duration is a zero duration
     * (all components of the duration are zero).
     *
     * @return true if the specified duration is a zero duration. False,
     *         otherwise.
     */
    public static boolean isZero(Duration duration) {
        return (duration != null &&
                duration.getYears() == 0 &&
                duration.getMonths() == 0 &&
                duration.getDays() == 0 &&
                duration.getHours() == 0 &&
                duration.getMinutes() == 0 &&
                (int)duration.getSeconds() == 0);
    }
    
}
