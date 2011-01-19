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
import java.util.TimeZone;

import junit.framework.TestCase;

import org.apache.axis.types.Duration;

public class DurationUtilsTest extends TestCase {

    public void testConversionToMillis() throws Exception {
        testDurationConversion(1.45D, 0, 0, 0, 1450);
        testDurationConversion(1.55D, 0, 0, 0, 1550);
        testDurationConversion(1.95D, 0, 0, 0, 1950);
        testDurationConversion(5.99D, 0, 0, 0, 5990);
        testDurationConversion(700.99D, 0, 0, 0, 700 * 1000 + 990);

        testDurationConversion(1.45D, 5, 0, 0, 5L*60*1000 + 1450);
        testDurationConversion(1.55D, 0, 5, 0, 5L*3600*1000 + 1550);
        testDurationConversion(1.95D, 0, 0, 5, 3600L*24*5*1000 + 1950);

        testDurationConversion(1.95D, 2, 4, 8, 
                               2L*60*1000 +
                               4L*3600*1000 +
                               8L*3600*24*1000 +
                               1950);
    }

    private void testDurationConversion(double seconds, 
                                        int minutes,
                                        int hours,
                                        int days,
                                        long expected) 
        throws Exception {
        Duration dur = new Duration();
        dur.setSeconds(seconds);
        dur.setMinutes(minutes);
        dur.setHours(hours);
        dur.setDays(days);
        
        long actual = DurationUtils.toMilliseconds(dur);

        assertEquals(expected, actual);
    }

    public void testConversionToDuration() throws Exception {
        testMillisecondConversion(1450, 1.45D, 0, 0, 0);
        testMillisecondConversion(1550, 1.55D, 0, 0, 0);
        testMillisecondConversion(1950, 1.95D, 0, 0, 0);
        testMillisecondConversion(5990, 5.99D, 0, 0, 0);

        testMillisecondConversion(5L*60*1000 + 1450,
                                  1.45D, 5, 0, 0);
        testMillisecondConversion(5L*3600*1000 + 1550,
                                  1.55D, 0, 5, 0);
        testMillisecondConversion(3600L*24*5*1000 + 1950,
                                  1.95D, 0, 0, 5);

        testMillisecondConversion(2L*60*1000 +
                                  4L*3600*1000 +
                                  8L*3600*24*1000 +
                                  1950,
                                  1.95D, 2, 4, 8);

        testMillisecondConversion(700 * 1000 + 990,
                                  40.99D, 11, 0, 0);
    }

    private void testMillisecondConversion(long value,
                                           double expectedSeconds,
                                           int expectedMinutes,
                                           int expectedHours,
                                           int expectedDays)
        throws Exception {
        Duration dur = DurationUtils.toDuration(value);

        System.out.println(dur);

        assertEquals("days", expectedDays, dur.getDays());
        assertEquals("hours", expectedHours, dur.getHours());
        assertEquals("minutes", expectedMinutes, dur.getMinutes());
        assertEquals("seconds", 
                     (int)(expectedSeconds * 100), 
                     (int)(dur.getSeconds() * 100));
    }


    public void testZeroDuration() throws Exception {
        assertEquals(true, 
                     DurationUtils.isZero(new Duration()));
        assertEquals(false,
                     DurationUtils.isZero(null));
        assertEquals(true,
                     DurationUtils.isZero(new Duration(false, 0, 0, 0, 0, 0, 0)));
        assertEquals(true, 
                     DurationUtils.isZero(new Duration(true, 0, 0, 0, 0, 0, 0)));
        assertEquals(false, 
                     DurationUtils.isZero(new Duration(true, 1, 0, 0, 0, 0, 0)));
        assertEquals(false, 
                     DurationUtils.isZero(new Duration(true, 0, 1, 0, 0, 0, 0)));
        assertEquals(false, 
                     DurationUtils.isZero(new Duration(true, 0, 0, 1, 0, 0, 0)));
        assertEquals(false, 
                     DurationUtils.isZero(new Duration(true, 0, 0, 0, 1, 0, 0)));
        assertEquals(false, 
                     DurationUtils.isZero(new Duration(true, 0, 0, 0, 0, 1, 0)));
        assertEquals(false, 
                     DurationUtils.isZero(new Duration(true, 1, 0, 0, 0, 0, 1)));
    }

    public void testComputeDuration() throws Exception {
        Calendar cal1, cal2;
        Duration duration;

        cal1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal2 = (Calendar)cal1.clone();
        
        duration = DurationUtils.computeDuration(cal1, cal2);
        assertTrue(DurationUtils.isZero(duration));
        
        
        cal1 = Calendar.getInstance(TimeZone.getTimeZone("EST"));
        cal2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        try {
            DurationUtils.computeDuration(cal1, cal2);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            // should be ok
        }
    }


}
