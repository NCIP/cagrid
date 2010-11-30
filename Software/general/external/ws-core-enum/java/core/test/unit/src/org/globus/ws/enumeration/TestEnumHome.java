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

import javax.xml.namespace.QName;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.ResourceHomeImpl;
import org.globus.wsrf.impl.SimpleResourceKey;
import org.globus.wsrf.impl.TestResource;

public class TestEnumHome extends ResourceHomeImpl {

    public static QName KEY_TYPE_NAME =
        new QName(TestResource.TEST_NS, "TestKey");

    public TestEnumHome() {
        this.keyTypeName = KEY_TYPE_NAME;
    }

    public void initialize() throws Exception {
        super.initialize();
        
        TestResource obj;
        
        obj = new TestResource();
        add(getKey(1), obj);
        
        obj = new TestResource();
        add(getKey(2), obj);
    }
    
    public static ResourceKey getKey(int o) {
        return new SimpleResourceKey(KEY_TYPE_NAME,
                                     String.valueOf(o));
    }
    
}
