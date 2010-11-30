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
package org.globus.wsrf.utils.cache;

import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

public class LinkedNodeListTest extends TestCase {

    public void testAdd() {
        LinkedNodeList list = new LinkedNodeList();
        assertTrue( list.getFirst() == null );
        assertEquals(0, list.size());
        assertTrue( list.isEmpty() );

        LinkedNodeList.Node n1 = list.createNode();
        list.add(n1);
        assertTrue( list.getFirst() == n1 );
        assertTrue( list.getFirst().getNext() == null );
        assertTrue( list.getFirst().getPrevious() == null );
        assertTrue( list.getLast() == n1 );
        assertTrue( list.getLast().getNext() == null );
        assertTrue( list.getLast().getPrevious() == null );
        assertFalse( list.isEmpty() );

        assertEquals(1, list.size());

        LinkedNodeList.Node n2 = list.createNode();
        list.add(n2);
        assertTrue( list.getFirst() == n1 );
        assertTrue( list.getFirst().getNext() == n2 );
        assertTrue( list.getFirst().getNext().getNext() == null );
        assertTrue( list.getFirst().getNext().getPrevious() == n1 );
        assertTrue( list.getLast() == n2 );
        assertTrue( list.getLast().getNext() == null );
        assertTrue( list.getLast().getPrevious() == n1 );
        assertEquals(2, list.size());

        LinkedNodeList.Node n3 = list.createNode();
        list.add(n3);
        assertTrue( list.getFirst() == n1 );
        assertTrue( list.getFirst().getNext() == n2 );
        assertTrue( list.getFirst().getNext().getNext() == n3 );
        assertTrue( list.getFirst().getNext().getNext().getNext() == null );
        assertTrue( list.getFirst().getNext().getNext().getPrevious() == n2 );
        assertTrue( list.getLast() == n3 );
        assertTrue( list.getLast().getNext() == null );
        assertTrue( list.getLast().getPrevious() == n2 );
        assertEquals(3, list.size());
    }

    public void testRemove() {
        LinkedNodeList list = new LinkedNodeList();
        assertTrue( list.getFirst() == null );

        LinkedNodeList.Node n1 = list.createNode();
        list.add(n1);
        LinkedNodeList.Node n2 = list.createNode();
        list.add(n2);
        LinkedNodeList.Node n3 = list.createNode();
        list.add(n3);
        LinkedNodeList.Node n4 = list.createNode();
        list.add(n4);
        System.out.println("1: " + list);

        // delete middle
        list.remove(n2);
        System.out.println("2: " + list);

        assertTrue( list.getFirst() == n1 );
        assertTrue( list.getFirst().getNext() == n3 );
        assertTrue( list.getFirst().getNext().getNext() == n4 );
        assertTrue( list.getFirst().getNext().getPrevious() == n1 );
        assertTrue( list.getFirst().getNext().getNext().getNext() == null );

        assertTrue( list.getLast() == n4 );
        assertTrue( list.getLast().getNext() == null );
        assertTrue( list.getLast().getPrevious() == n3 );

        assertEquals(3, list.size());

        // delete tail
        list.remove(n4);
        System.out.println("3: " + list);

        assertTrue( list.getFirst() == n1 );
        assertTrue( list.getFirst().getNext() == n3 );
        assertTrue( list.getFirst().getNext().getNext() == null );

        assertTrue( list.getLast() == n3 );
        assertTrue( list.getLast().getNext() == null );
        assertTrue( list.getLast().getPrevious() == n1 );

        assertEquals(2, list.size());

        // delete head
        list.remove(n1);
        System.out.println("4: " + list);

        assertTrue( list.getFirst() == n3 );
        assertTrue( list.getFirst().getPrevious() == null );
        assertTrue( list.getFirst().getNext() == null );

        assertTrue( list.getLast() == n3 );
        assertTrue( list.getLast().getNext() == null );
        assertTrue( list.getLast().getPrevious() == null );
        
        assertEquals(1, list.size());

        // delete last
        list.remove(n3);
        System.out.println("5: " + list);

        assertTrue( list.getFirst() == null );
        assertTrue( list.getLast() == null );

        assertEquals(0, list.size());
    }

    public void testRemoveFirst() {
        LinkedNodeList list = new LinkedNodeList();
        assertTrue( list.getFirst() == null );
        assertTrue( list.getLast() == null );

        LinkedNodeList.Node n1 = list.createNode();
        list.add(n1);
        LinkedNodeList.Node n2 = list.createNode();
        list.add(n2);
        LinkedNodeList.Node n3 = list.createNode();
        list.add(n3);
        LinkedNodeList.Node n4 = list.createNode();
        list.add(n4);

        assertTrue ( list.removeFirst() == n1 );

        assertTrue( list.getFirst() == n2 );
        assertTrue( list.getFirst().getPrevious() == null );
        assertTrue( list.getFirst().getNext() == n3);
        assertEquals(3, list.size());
        assertEquals(3, list.computeSize());

        assertTrue ( list.removeFirst() == n2 );

        assertTrue( list.getFirst() == n3 );
        assertTrue( list.getFirst().getPrevious() == null );
        assertTrue( list.getFirst().getNext() == n4);
        assertEquals(2, list.size());
        assertEquals(2, list.computeSize());

        assertTrue ( list.removeFirst() == n3 );

        assertTrue( list.getFirst() == n4 );
        assertTrue( list.getFirst().getPrevious() == null );
        assertTrue( list.getFirst().getNext() == null);
        assertEquals(1, list.size());
        assertEquals(1, list.computeSize());

        assertTrue ( list.removeFirst() == n4 );

        assertTrue( list.getFirst() == null );
        assertEquals(0, list.size());
        assertEquals(0, list.computeSize());

        assertTrue ( list.removeFirst() == null );
        assertEquals(0, list.size());
        assertEquals(0, list.computeSize());

    }

    public void testMoveToEnd() {
        LinkedNodeList list = new LinkedNodeList();

        LinkedNodeList.Node n1 = list.createNode();
        list.add(n1);
        LinkedNodeList.Node n2 = list.createNode();
        list.add(n2);
        LinkedNodeList.Node n3 = list.createNode();
        list.add(n3);
        LinkedNodeList.Node n4 = list.createNode();
        list.add(n4);

        System.out.println("a: " + list);

        list.moveToEnd(n2);

        assertTrue( list.getLast() == n2 );

        System.out.println("b: " + list);
        
        list.moveToEnd(n2);

        assertTrue( list.getLast() == n2 );
        
        System.out.println("c: " + list);

        list.moveToEnd(n1);

        assertTrue( list.getLast() == n1 );
        
        System.out.println("d: " + list);
    }

    public void testForwardIterator() {
        LinkedNodeList list = new LinkedNodeList();
        
        LinkedNodeList.Node n1 = list.createNode();
        list.add(n1);
        LinkedNodeList.Node n2 = list.createNode();
        list.add(n2);
        LinkedNodeList.Node n3 = list.createNode();
        list.add(n3);
        LinkedNodeList.Node n4 = list.createNode();
        list.add(n4);

        Iterator iter = list.iterator();
        assertTrue( iter.hasNext() );
        assertTrue( iter.next() == n1 );

        assertTrue( iter.hasNext() );
        assertTrue( iter.next() == n2 );

        assertTrue( iter.hasNext() );
        assertTrue( iter.next() == n3 );

        assertTrue( iter.hasNext() );
        assertTrue( iter.next() == n4 );

        assertFalse( iter.hasNext() );

        try {
            iter.next();
            fail("Did not throw expected exception");
        } catch (NoSuchElementException e) {
        }
    }

    public void testReverseIterator() {
        LinkedNodeList list = new LinkedNodeList();
        
        LinkedNodeList.Node n1 = list.createNode();
        list.add(n1);
        LinkedNodeList.Node n2 = list.createNode();
        list.add(n2);
        LinkedNodeList.Node n3 = list.createNode();
        list.add(n3);
        LinkedNodeList.Node n4 = list.createNode();
        list.add(n4);

        Iterator iter = list.reverseIterator();
        assertTrue( iter.hasNext() );
        assertTrue( iter.next() == n4 );

        assertTrue( iter.hasNext() );
        assertTrue( iter.next() == n3 );

        assertTrue( iter.hasNext() );
        assertTrue( iter.next() == n2 );

        assertTrue( iter.hasNext() );
        assertTrue( iter.next() == n1 );

        assertFalse( iter.hasNext() );

        try {
            iter.next();
            fail("Did not throw expected exception");
        } catch (NoSuchElementException e) {
        }
    }

}
