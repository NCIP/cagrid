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
package org.globus.wsrf.impl.notification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.Calendar;

import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.PersistentResource;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.Topic;
import org.globus.wsrf.TopicList;
import org.globus.wsrf.TopicListAccessor;
import org.globus.wsrf.impl.ReflectionResourceProperty;
import org.globus.wsrf.impl.ResourcePropertyTopic;
import org.globus.wsrf.impl.SimpleResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.globus.wsrf.impl.SimpleTopic;
import org.globus.wsrf.impl.SimpleTopicList;
import org.globus.wsrf.impl.SimpleTopicListMetaData;
import org.globus.wsrf.tests.notification.JobStatusType;
import org.globus.wsrf.utils.FilePersistenceHelper;
import org.globus.wsrf.utils.SubscriptionPersistenceUtils;
import org.oasis.wsrf.faults.BaseFaultType;

public class NotificationTestResource implements ResourceProperties,
                                                 TopicListAccessor,
                                                 PersistentResource
{
    private TopicList topicList;
    private ResourcePropertySet propSet;
    private JobStatusType jobStatus;
    private Topic jobStatusTopic = null;
    private FilePersistenceHelper persistenceHelper;
    private Integer id = null;
    private static final String FILE_SUFFIX = ".obj";

    protected Topic getJobStatusTopic()
    {
        return this.jobStatusTopic;
    }

    public NotificationTestResource() throws RemoteException
    {
        this.propSet = new SimpleResourcePropertySet(
            NotificationTestService.RP_SET);
        jobStatus = new JobStatusType();
        jobStatus.setJobState(NotificationTestService.JOB_STATUS_ACTIVE);
        BaseFaultType fault = new BaseFaultType();
        fault.setTimestamp(Calendar.getInstance());
        jobStatus.setFault(fault);
        try
        {
            this.jobStatusTopic = new ResourcePropertyTopic(new ReflectionResourceProperty(
                NotificationTestService.JOB_STATUS_TOPIC_RP,
                "JobStatus",
                this));
            this.propSet.add(new ResourcePropertyTopic(new SimpleResourceProperty(
                NotificationTestService.AUTO_NOTIFY_TOPIC_RP)));
            this.propSet.add(new ResourcePropertyTopic(new ReflectionResourceProperty(
                NotificationTestService.SEND_OLD_TOPIC_RP,
                "SendOldValue",
                this.propSet.get(NotificationTestService.AUTO_NOTIFY_TOPIC_RP))));
        }
        catch(Exception e)
        {
            throw new RemoteException("", e);
        }
        ((ResourcePropertyTopic) this.jobStatusTopic).setAutoNotify(false);
        this.propSet.add((ResourceProperty) this.jobStatusTopic);
        
        SimpleTopicListMetaData metaData = 
            new SimpleTopicListMetaData(true);
        this.topicList = new SimpleTopicList(this, metaData);

        this.topicList.addTopic(new SimpleTopic(
            NotificationTestService.TEST_TOPIC));
        this.topicList.addTopic(new SimpleTopic(
            NotificationTestService.EMPTY_TOPIC));
        this.topicList.addTopic(new SimpleTopic(
            NotificationTestService.THIRD_TOPIC));
        this.topicList.addTopic(new SimpleTopic(
            NotificationTestService.FOURTH_TOPIC));
        this.topicList.addTopic(this.jobStatusTopic);
        this.topicList.addTopic((Topic) this.propSet.get(
            NotificationTestService.AUTO_NOTIFY_TOPIC_RP));
        this.topicList.addTopic((Topic) this.propSet.get(
            NotificationTestService.SEND_OLD_TOPIC_RP));
    }

    public ResourcePropertySet getResourcePropertySet()
    {
        return this.propSet;
    }

    public TopicList getTopicList()
    {
        return this.topicList;
    }

    public JobStatusType getJobStatus()
    {
        return jobStatus;
    }

    public void setJobStatus(JobStatusType jobStatus)
    {
        this.jobStatus = jobStatus;
        try
        {
            this.store();
        }
        catch(ResourceException e)
        {
            throw new RuntimeException(e.toString());
        }
    }

    public void load(ResourceKey key) throws ResourceException,
                                             NoSuchResourceException,
                                             InvalidResourceKeyException
    {
        this.id = (Integer) key.getValue();
        File file = getPersistenceHelper().getKeyAsFile(this.id);
        if(!file.exists())
        {
            throw new NoSuchResourceException();
        }
        FileInputStream fis = null;

        try
        {
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.jobStatus = (JobStatusType) ois.readObject();
            SubscriptionPersistenceUtils.loadSubscriptionListeners(
                this.getTopicList(), ois);
        }
        catch(Exception e)
        {
            throw new ResourceException("Failed to load resource", e);
        }
        finally
        {
            if(fis != null)
            {
                try
                {
                    fis.close();
                }
                catch(Exception ee)
                {
                }
            }
        }
    }

    public synchronized void store() throws ResourceException
    {
        FileOutputStream fos = null;
        File tmpFile = null;
        try
        {
            tmpFile = File.createTempFile(
                "notificationTestResource", ".tmp",
                getPersistenceHelper().getStorageDirectory());
            fos = new FileOutputStream(tmpFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.jobStatus);
            SubscriptionPersistenceUtils.storeSubscriptionListeners(
                this.getTopicList(), oos);
        }
        catch(Exception e)
        {
            tmpFile.delete();
            throw new ResourceException("Failed to store resource", e);
        }
        finally
        {
            if(fos != null)
            {
                try
                {
                    fos.close();
                }
                catch(Exception ee)
                {
                }
            }
        }

        File file = getPersistenceHelper().getKeyAsFile(this.id);
        if (file.exists()) {
            file.delete();
        }
        if (!tmpFile.renameTo(file)) {
            tmpFile.delete();
            throw new ResourceException("Failed to store resource");
        }
    }

    public Object getID()
    {
        return this.id;
    }

    protected void setID(Integer id)
    {
        this.id = id;
    }

    public void remove() throws ResourceException
    {
        getPersistenceHelper().remove(this.id);
    }

    protected synchronized FilePersistenceHelper getPersistenceHelper()
    {
        if(this.persistenceHelper == null)
        {
            try
            {
                this.persistenceHelper = new FilePersistenceHelper(getClass(),
                                                                   FILE_SUFFIX);
            }
            catch(Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }
        return this.persistenceHelper;
    }
}
