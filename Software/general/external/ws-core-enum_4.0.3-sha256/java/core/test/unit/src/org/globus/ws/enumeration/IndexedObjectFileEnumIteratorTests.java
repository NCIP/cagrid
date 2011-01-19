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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.axis.message.MessageElement;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.utils.io.IndexedObjectFileUtils;

public class IndexedObjectFileEnumIteratorTests extends TestCase {

    public void testEmpty()
        throws Exception {
        File file = null;

        Object [] array = null;
        
        file = IndexedObjectFileUtils.createIndexedObjectFile(array);
        testEmpty(new IndexedObjectFileEnumIterator(file, null));
        array = new Object[]{};
        file = IndexedObjectFileUtils.createIndexedObjectFile(array);
        testEmpty(new IndexedObjectFileEnumIterator(file, null));
        
        List list = null;
        file = IndexedObjectFileUtils.createIndexedObjectFile(list);
        testEmpty(new IndexedObjectFileEnumIterator(file, null));
        list = new ArrayList();
        file = IndexedObjectFileUtils.createIndexedObjectFile(list);
        testEmpty(new IndexedObjectFileEnumIterator(file, null));
    }

    private void testEmpty(EnumIterator iter) 
        throws Exception {
        try {
            iter.next(IterationConstraints.DEFAULT_CONSTRAINTS);
            fail("Did not throw expected exception");
        } catch (NoSuchElementException e) {
            // that's what we want
        }
    }

    public void testIterate()
        throws Exception {
        List list = EnumerationTestCase.getData(100);

        testIterate(list, 1);
        testIterate(list, 2);
        testIterate(list, 3);
        testIterate(list, 5);
        testIterate(list, 7);
    }

    private void testIterate(List list, int maxElements)
        throws Exception {
        int n = list.size();

        File file = IndexedObjectFileUtils.createIndexedObjectFile(list);

        IndexedObjectFileEnumIterator iter = 
            new IndexedObjectFileEnumIterator(file, 
                                              EnumerationTestCase.ITEM_NAME);

        IterationResult result = null;
        IterationConstraints con = 
            new IterationConstraints(maxElements, -1, null);
        int items = 0;
        int calls = 0;
        do {
            result = iter.next(con);

            calls++;

            assertTrue(result != null);
            assertTrue(result.getItems() != null);

            if (items + maxElements < n) {
                EnumerationTestCase.checkIterationResult(result,
                                                         items,
                                                         maxElements,
                                                         false);
            } else if (items + maxElements == n) {
                EnumerationTestCase.checkIterationResult(result,
                                                         items,
                                                         maxElements,
                                                         true);
            } else {
                int elementsLeft = n - items;
                EnumerationTestCase.checkIterationResult(result,
                                                         items,
                                                         elementsLeft,
                                                         true);
            }

            items += result.getItems().length;
        } while (!result.isEndOfSequence());

        if (n % maxElements == 0) {
            assertEquals(n / maxElements, calls);
        } else {
            assertEquals(1 + (n / maxElements), calls);
        }

        testEmpty(iter);

        assertFalse(file.getAbsolutePath(), file.exists());
    }

    public void testRelease()
        throws Exception {
        File file = null;
        List list = EnumerationTestCase.getData(10);

        IndexedObjectFileEnumIterator iter = null;
        IterationConstraints con = IterationConstraints.DEFAULT_CONSTRAINTS;

        // case 1: release it right away
        file = IndexedObjectFileUtils.createIndexedObjectFile(list);
        iter = 
            new IndexedObjectFileEnumIterator(file, 
                                              EnumerationTestCase.ITEM_NAME);
        iter.release();

        testEmpty(iter);

        assertFalse(file.exists());

        // case 2: release it after a few iterations
        file = IndexedObjectFileUtils.createIndexedObjectFile(list);
        iter = 
            new IndexedObjectFileEnumIterator(file, 
                                              EnumerationTestCase.ITEM_NAME);
        for (int i=0;i<list.size() / 2;i++) {
            EnumerationTestCase.checkIterationResult(iter.next(con),
                                                     i, 1, false);
        }
        iter.release();

        testEmpty(iter);

        assertFalse(file.exists());

        // case 3: once got all data
        file = IndexedObjectFileUtils.createIndexedObjectFile(list);
        iter = 
            new IndexedObjectFileEnumIterator(file, 
                                              EnumerationTestCase.ITEM_NAME);
        for (int i=0;i<list.size();i++) {
            EnumerationTestCase.checkIterationResult(iter.next(con),
                                                     i,
                                                     1,
                                                     (i+1 == list.size()));
        }
        
        testEmpty(iter);
        
        assertFalse(file.exists());
    }

    public void testDisabledAutoDelete()
        throws Exception {
        File file = null;
        List list = EnumerationTestCase.getData(10);

        IndexedObjectFileEnumIterator iter = null;
        IterationConstraints con = new IterationConstraints();
        
        // case 1: release it right away
        file = IndexedObjectFileUtils.createIndexedObjectFile(list);
        iter = 
            new IndexedObjectFileEnumIterator(file, 
                                              EnumerationTestCase.ITEM_NAME);
        iter.setEnableAutoDelete(false);
        iter.release();

        testEmpty(iter);

        assertTrue(file.exists());
        file.delete();

        // case 2: release it after a few iterations
        file = IndexedObjectFileUtils.createIndexedObjectFile(list);
        iter = 
            new IndexedObjectFileEnumIterator(file, 
                                              EnumerationTestCase.ITEM_NAME);
        iter.setEnableAutoDelete(false);
        for (int i=0;i<list.size() / 2;i++) {
            EnumerationTestCase.checkIterationResult(iter.next(con),
                                                     i, 1, false);
        }
        iter.release();

        testEmpty(iter);

        assertTrue(file.exists());
        file.delete();

        // case 3: check after got all data
        file = IndexedObjectFileUtils.createIndexedObjectFile(list);
        iter = 
            new IndexedObjectFileEnumIterator(file, 
                                              EnumerationTestCase.ITEM_NAME);
        iter.setEnableAutoDelete(false);
        for (int i=0;i<list.size();i++) {
            EnumerationTestCase.checkIterationResult(iter.next(con),
                                                     i,
                                                     1,
                                                     (i+1 == list.size()));
        }

        testEmpty(iter);
        
        assertTrue(file.exists());
        file.delete();
    }
    
    public void testSkips() 
        throws Exception {
        List list = new ArrayList();
        int j = 0;
        for (int i=0;i<3;i++) {
            list.add(EnumerationTestCase.ENUM_ELEMENT + j);
            j++;
        }

        QName itemName = 
            new QName("http://www.globus.org", "DiffItem");
        
        list.add(ObjectSerializer.toSOAPElement("foo", itemName));
        j++;

        IndexedObjectFileEnumIterator iter = null;
        IterationConstraints con = null;
        IterationResult result = null;
        File file = null;

        file = IndexedObjectFileUtils.createIndexedObjectFile(list);
        iter = 
            new IndexedObjectFileEnumIterator(file, 
                                              EnumerationTestCase.ITEM_NAME);
        con = new IterationConstraints();
        for (int i=0;i<3;i++) {
            EnumerationTestCase.checkIterationResult(iter.next(con),
                                                     i, 1, false);
        }

        result = iter.next(con);
        assertTrue(result.isEndOfSequence());
        assertTrue(result.getItems() == null);
        assertFalse(file.exists());

        // add a few more entires

        list.add(ObjectSerializer.toSOAPElement("foo2", itemName));
        j++;
        for (int i=0;i<3;i++) {
            list.add(EnumerationTestCase.ENUM_ELEMENT + j);
            j++;
        }

        file = IndexedObjectFileUtils.createIndexedObjectFile(list);
        iter = 
            new IndexedObjectFileEnumIterator(file, 
                                              EnumerationTestCase.ITEM_NAME);
        con = new IterationConstraints(2, -1, null);

        EnumerationTestCase.checkIterationResult(iter.next(con),
                                                 0, 2, false);
        
        result = iter.next(con);
        assertFalse(result.isEndOfSequence());
        assertTrue(result.getItems() != null);
        assertEquals(2, result.getItems().length);
        assertEquals(EnumerationTestCase.ENUM_ELEMENT + 2, 
                     EnumerationTestCase.toString(result.getItems()[0]));
        assertEquals(EnumerationTestCase.ENUM_ELEMENT + 5,
                     EnumerationTestCase.toString(result.getItems()[1]));

        result = iter.next(con);
        assertTrue(result.isEndOfSequence());
        assertTrue(result.getItems() != null);
        assertEquals(2, result.getItems().length);
        assertEquals(EnumerationTestCase.ENUM_ELEMENT + 6, 
                     EnumerationTestCase.toString(result.getItems()[0]));
        assertEquals(EnumerationTestCase.ENUM_ELEMENT + 7,
                     EnumerationTestCase.toString(result.getItems()[1]));
        
        assertFalse(file.exists());
    }
    
    public void testSOAPElementData()
        throws Exception {
        List [] lists = EnumerationTestCase.getSOAPElementData();
        
        IndexedObjectFileEnumIterator iter = null;
        IterationConstraints con = null;
        IterationResult result = null;

        File file = IndexedObjectFileUtils.createIndexedObjectFile(lists[2]);
        iter = new IndexedObjectFileEnumIterator(file, null);
        con = new IterationConstraints();
        
        for (int i=0;i<lists[0].size();i++) {
            result = iter.next(con);
            
            if (i < lists[0].size() - 1) {
                assertFalse(result.isEndOfSequence());
            } else {
                assertTrue(result.isEndOfSequence());
            }
            
            assertTrue(result.getItems() != null);
            
            QName actualQName = (QName)lists[1].get(i);
            QName valueQName = ((MessageElement)result.getItems()[0]).getQName();

            assertEquals(actualQName, valueQName);

            Object actual = lists[0].get(i);
            Object value = ObjectDeserializer.toObject(result.getItems()[0],
                                                       actual.getClass());

            assertEquals(actual, value);
        }
    }

}
