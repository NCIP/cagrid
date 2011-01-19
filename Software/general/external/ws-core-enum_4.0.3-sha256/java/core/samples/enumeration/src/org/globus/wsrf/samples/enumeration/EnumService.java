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
package org.globus.wsrf.samples.enumeration;

import org.globus.ws.enumeration.EnumResourceHome;
import org.globus.ws.enumeration.EnumIterator;
import org.globus.ws.enumeration.EnumResource;
import org.globus.ws.enumeration.SimpleEnumIterator;
import org.globus.ws.enumeration.IndexedObjectFileEnumIterator;
import org.globus.ws.enumeration.EnumProvider;

import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Enumerate;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.utils.io.IndexedObjectFileWriter;
import org.globus.wsrf.utils.io.IndexedObjectFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

public class EnumService {

    private static final QName ITEM_NAME = 
        new QName("http://www.globus.org/core/enumeration", "ItemName");

    public EnumerateResponse enumerateOp(Enumerate body) 
        throws RemoteException {
        EnumResourceHome enumHome = null;
        
        try {
            enumHome = EnumResourceHome.getEnumResourceHome();
        } catch (Exception e) {
            throw new RemoteException("", e);
        }

        // generate dummy data
        ArrayList list = new ArrayList();
        for (int i=0;i<100;i++) {
            list.add("foo" + i);
        }

        try {
            File dataFile = 
                IndexedObjectFileUtils.createIndexedObjectFile(list);
            
            EnumIterator iter = 
                new IndexedObjectFileEnumIterator(dataFile, ITEM_NAME);
            
            EnumResource resource = enumHome.createEnumeration(iter, true);
            ResourceKey key = enumHome.getKey(resource);
            
            return EnumProvider.createEnumerateOpResponse(key, resource);
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
    }
    
}
