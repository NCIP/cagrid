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
package org.globus.util;

import java.io.InputStream;

/**
 * Various classloader utils. Extends the standard ways of loading classes
 * or resources with a fallback mechanism to the thread context classloader.
 */
public class ClassLoaderUtils {
    
    private static DummySecurityManager MANAGER = new DummySecurityManager();

    private static class DummySecurityManager extends SecurityManager {
        public Class[] getClassContext() {
            return super.getClassContext();
        }
    }

    /**
     * Returns the current execution stack as an array of classes. 
     * <p>
     * The length of the array is the number of methods on the execution 
     * stack. The element at index <code>0</code> is the class of the 
     * currently executing method, the element at index <code>1</code> is 
     * the class of that method's caller, and so on. 
     *
     * @return  the execution stack.
     */
    public static Class[] getClassContext() {
        return MANAGER.getClassContext();
    }

    /**
     * Returns a class at specified depth of the current execution stack.
     *
     * @return the class at the specified depth of the current execution 
     *         stack. Migth return null if depth is out of range.
     */
    public static Class getClassContextAt(int i) {
        Class[] classes = MANAGER.getClassContext();
        if (classes != null && classes.length > i) {
            return classes[i];
        }
        return null;
    }
    
    /**
     * Returns a classloader at specified depth of the current execution stack.
     *
     * @return the classloader at the specified depth of the current execution 
     *         stack. Migth return null if depth is out of range.
     */
    public static ClassLoader getClassLoaderContextAt(int i) {
        Class[] classes = MANAGER.getClassContext();
        if (classes != null && classes.length > i) {
            return classes[i].getClassLoader();
        }
        return null;
    }

    /**
     * Gets an InputStream to a resource of a specified name.
     * First, the caller's classloader is used to load the resource
     * and if it fails the thread's context classloader is used 
     * to load the resource.
     */
    public static InputStream getResourceAsStream(String name) {
        // try with caller classloader
        ClassLoader loader = getClassLoaderContextAt(3);
        InputStream in = (loader == null) 
            ? null : loader.getResourceAsStream(name);
        if (in == null) {
            // try with context classloader if set & different
            ClassLoader contextLoader = 
                Thread.currentThread().getContextClassLoader();
            if (contextLoader != null && contextLoader != loader) {
                in = contextLoader.getResourceAsStream(name);
            }
        }
        return in;
    }
    
    /**
     * Loads a specified class.
     * First, the caller's classloader is used to load the class
     * and if it fails the thread's context classloader is used 
     * to load the specified class.
     */
    public static Class forName(String name) 
        throws ClassNotFoundException {
        // try with caller classloader
        ClassLoader loader = getClassLoaderContextAt(3);
        try {
            return Class.forName(name, true, loader);
        } catch (ClassNotFoundException e) {
            // try with context classloader if set & different
            ClassLoader contextLoader = 
                Thread.currentThread().getContextClassLoader();
            if (contextLoader == null || contextLoader == loader) {
                throw e;
            } else {
                return Class.forName(name, true, contextLoader);
            }
        }
    }
    
}
