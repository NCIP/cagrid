/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */
package org.globus.wsrf.samples.counter;

import java.util.Calendar;

import javax.xml.namespace.QName;

import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceIdentifier;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.Topic;
import org.globus.wsrf.TopicList;
import org.globus.wsrf.TopicListAccessor;
import org.globus.wsrf.ResourceLifetime;
import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.impl.ReflectionResourceProperty;
import org.globus.wsrf.impl.ResourcePropertyTopic;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.globus.wsrf.impl.SimpleTopicList;
import org.globus.wsrf.impl.SimpleTopic;
import org.globus.wsrf.impl.SimpleResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertyMetaData;

/**
 * Resource Implementation
 */
public class Counter
    implements Resource,
               ResourceLifetime,
               ResourceIdentifier,
               ResourceProperties,
               TopicListAccessor {

    public static final QName KEY =
        new QName("http://counter.com", "CounterKey");

    public static final QName RP_SET =
        new QName("http://counter.com", "Counter");

    public static final QName VALUE =
        new QName("http://counter.com", "Value");

    private ResourcePropertySet propSet;
    private TopicList topicList;

    protected Calendar terminationTime = null;
    protected Object key;
    protected ResourceProperty value;

    protected void initialize(Object key) {
        this.key = key;
        this.propSet = new SimpleResourcePropertySet(RP_SET);
        this.topicList = new SimpleTopicList(this);
        ResourceProperty prop = null;

        try {
            this.value = new ResourcePropertyTopic(
                new SimpleResourceProperty(VALUE));
            this.propSet.add(this.value);
            this.topicList.addTopic((Topic) this.value);
            this.value.add(new Integer(0));

            prop = new ReflectionResourceProperty(SimpleResourcePropertyMetaData.TERMINATION_TIME, this);
            this.propSet.add(prop);
            this.topicList.addTopic(new SimpleTopic(
                WSRFConstants.TERMINATION_TOPIC));

            prop = new ReflectionResourceProperty(SimpleResourcePropertyMetaData.CURRENT_TIME, this);
            this.propSet.add(prop);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResourcePropertySet getResourcePropertySet() {
        return this.propSet;
    }

    public TopicList getTopicList() {
        return this.topicList;
    }

    public int getValue() {
        return ((Integer) this.value.get(0)).intValue();
    }

    public void setValue(int value) {
        this.value.set(0, new Integer(value));
    }

    /**
     * Called when a new Counter resource is created.
     *
     * @return the resource key
     */
    public Object create() throws Exception {
        // just an example, might be a file already...
        this.key = new Integer(hashCode());
        initialize(key);
        return key;
    }

    public Object getID() {
        return this.key;
    }

    public void setTerminationTime(Calendar time) {
        this.terminationTime = time;
    }

    public Calendar getTerminationTime() {
        return this.terminationTime;
    }

    public Calendar getCurrentTime() {
        return Calendar.getInstance();
    }

}
