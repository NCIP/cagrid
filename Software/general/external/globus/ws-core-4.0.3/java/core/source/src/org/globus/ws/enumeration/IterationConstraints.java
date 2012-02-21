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

import org.globus.axis.utils.DurationUtils;
import org.globus.util.I18n;

import org.apache.axis.types.Duration;

/**
 * Represents the iteration constraints that can be specified on the pull
 * operation of WS-Enumeration.
 */
public class IterationConstraints {
    
    private static I18n i18n =
        I18n.getI18n("org.globus.ws.enumeration.error");

    /**
     * Default constraints (<tt>maxElements</tt> set to 1, no <tt>maxTime</tt>
     * limit and no <tt>maxCharacters</tt> limit).
     */
    public static final IterationConstraints DEFAULT_CONSTRAINTS = 
        new IterationConstraints(1, -1, null);
    
    private int maxElements;
    private int maxCharacters;
    private Duration maxTime;
    
    /**
     * Creates <tt>IterationConstraints</tt> with the default settings 
     * (<tt>maxElements</tt> set to 1, no <tt>maxTime</tt> limit and
     * no <tt>maxCharacters</tt> limit).
     */
    public IterationConstraints() {
        this(1, -1, null);
    }

    /**
     * Creates <tt>IterationConstraints</tt> with the specified settings.
     *
     * @param maxElements the maxiumum number of elements that the consumer
     *        can accept.
     * @param maxCharacters the maximum number of characters (in Unicode) that
     *        the consumer can accept.
     * @param maxTime the maximum amount of time that the consumer is willing
     *        to wait for a response.
     * @throws IllegalArgumentException if any of the parameters values is
     *        invalid
     */
    public IterationConstraints(int maxElements, 
                                int maxCharacters,
                                Duration maxTime) {
        if (maxElements == 0) {
            throw new IllegalArgumentException(
                    i18n.getMessage("zeroArgument", "maxElements"));
        }
        if (maxCharacters == 0) {
            throw new IllegalArgumentException(
                    i18n.getMessage("zeroArgument", "maxCharacters"));
        }
        if (DurationUtils.isZero(maxTime)) {
            throw new IllegalArgumentException(
                    i18n.getMessage("zeroArgument", "maxTime"));
        }
        this.maxElements = maxElements;
        this.maxCharacters = maxCharacters;
        this.maxTime = maxTime;
    }
    
    /**
     * Returns the maximum number of elements that the consumer can accept.
     *
     * @return the maximum number of elements that the consumer can accept.
     */
    public int getMaxElements() {
        return this.maxElements;
    }
    
    /**
     * Returns the maximum number of characters (in Unicode) that the consumer
     * can accept.
     *
     * @return the maximum number of characters (in Unicode) that the consumer
     *         can accept. Returns less then 0 if not set (no limit on size).
     */
    public int getMaxCharacters() {
        return this.maxCharacters;
    }
    
    /**
     * Returns the maximum amount of time the consumer is willing to wait
     * for a response.
     *
     * @return the maximum amount of time the consumer is willing to wait
     *         for a response. Returns less then 0 if not set (no time limit).
     */
    public Duration getMaxTime() {
        return this.maxTime;
    }
    
}
