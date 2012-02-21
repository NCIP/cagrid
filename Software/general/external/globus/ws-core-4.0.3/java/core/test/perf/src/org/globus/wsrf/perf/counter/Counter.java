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
package org.globus.wsrf.perf.counter;

import javax.xml.namespace.QName;

import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceIdentifier;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.Topic;
import org.globus.wsrf.TopicList;
import org.globus.wsrf.TopicListAccessor;
import org.globus.wsrf.impl.ResourcePropertyTopic;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.globus.wsrf.impl.SimpleTopicList;
import org.globus.wsrf.impl.SimpleResourceProperty;

/**
 * Resource Implementation
 */
public class Counter
    implements Resource,
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

    protected Object key;
    protected ResourceProperty value;

    public Counter() {
        // XXX: this might not be unqiue enough - might need to swith to UUID
        this.key = String.valueOf(hashCode());
        this.propSet = new SimpleResourcePropertySet(RP_SET);
        this.topicList = new SimpleTopicList(this);
        ResourceProperty prop = null;

        try {
            this.value = new ResourcePropertyTopic(
                         new SimpleResourceProperty(VALUE));
            this.propSet.add(this.value);
            this.topicList.addTopic((Topic) this.value);
            this.value.add(new Integer(0));
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

    public Object getID() {
        return this.key;
    }

}
