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

import com.enumeration.StartEnumerationResponse;
import com.enumeration.StartEnumeration;

import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Enumerate;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.utils.io.IndexedObjectFileUtils;

import java.io.File;
import java.util.List;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

public class TestEnumService {

    private static final QName ITEM_NAME = 
        new QName("http://www.globus.org/core/enumeration", "ItemName");

    public StartEnumerationResponse startEnumeration(StartEnumeration request)
        throws RemoteException {
        EnumResourceHome enumHome = null;
        
        try {
            enumHome = EnumResourceHome.getEnumResourceHome();
        } catch (Exception e) {
            throw new RemoteException("", e);
        }

        List list = EnumerationTestCase.getData(100);

        EnumIterator iter = null;
        switch (request.getIteratorType()) {

        case 1:
            // data in memory
            iter = new SimpleEnumIterator(list, ITEM_NAME);
            break;

        case 2:
            // data in file
            try {
                File dataFile = 
                    IndexedObjectFileUtils.createIndexedObjectFile(list);
                iter = new IndexedObjectFileEnumIterator(dataFile, ITEM_NAME);
            } catch (Exception e) {
                throw new RemoteException("failed to create iterator", e);
            }
            break;
            
        case 3:
            // for testing timeout 
            iter = new TestEnumIterator(list, ITEM_NAME);
            break;

        default:
            throw new RemoteException("test case not supported");
        }

        StartEnumerationResponse response = new StartEnumerationResponse();

        try {
            EnumResource resource = 
                enumHome.createEnumeration(iter, request.isPersistent());
            ResourceKey key = enumHome.getKey(resource);
        
            response.setEnumerationContext(
                            EnumProvider.createEnumerationContextType(key));
        } catch (Exception e) {
            throw new RemoteException("failed to create enum resource", e);
        }

        return response;
    }

    public EnumerateResponse enumerateOp(Enumerate body) 
        throws RemoteException {
        EnumResourceHome enumHome = null;
        
        try {
            enumHome = EnumResourceHome.getEnumResourceHome();
        } catch (Exception e) {
            throw new RemoteException("", e);
        }

        List list = EnumerationTestCase.getData(100);

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
    
    private static class TestEnumIterator extends SimpleEnumIterator {
        
        public TestEnumIterator(List items, QName itemName) {
            super(items, itemName);
        }
        
        public IterationResult next(IterationConstraints constraints) {
            if (constraints.getMaxTime() != null &&
                constraints.getMaxTime().getYears() == 5) {
                throw new TimeoutException();
            } else {
                return super.next(constraints);
            }
        }
    }
    
}
