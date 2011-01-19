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
package org.globus.wsrf.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.globus.util.I18n;

/*
 * This class is used to create an adapter-like dynamic proxy that exposes
 * a given object as a set of specified interfaces. The calls on the returned
 * proxy are forwarded to the target object.
 */
public class AdapterProxy implements InvocationHandler {

    private static I18n i18n = 
        I18n.getI18n(Resources.class.getName());

    private Map methodMap;
    private Object target;
    
    private AdapterProxy(Object obj, Map methodMap) {
        this.target = obj;
        this.methodMap = methodMap;
    }

    public Object invoke(Object proxy,
                         Method method,
                         Object[] args)
        throws Throwable {
        Method m = (Method)this.methodMap.get(method);
        if (m == null) {
            // this should never happen
            throw new NoSuchMethodException(
                           "No such method on target object");
        } else {
            try {
                return m.invoke(target, args);
            } catch (InvocationTargetException e) {
                if (e.getTargetException() != null) {
                    throw e.getTargetException();
                } else {
                    throw e;
                }
            }
        }
    }

    /**
     * Creates an adapter-like dynamic proxy that exposes the target object
     * as a set of specified interfaces. The calls on the returned proxy
     * will be forwarded to the target object. The target object must have  
     * the exact same methods as defined by the specified interfaces classes.
     *
     * @param target the target object to which the calls on the proxy
     *        will be forwarded to.
     * @param interfaces the list of interfaces that the adapter proxy should
     *        implement. The target object must define the same exact methods
     *        as defined by these classes.
     * @return the proxy object
     * @throws IllegalArgumentException if there is a mismatch between methods
     *         of the target object and specified interfaces or some methods
     *         are missing.
     */
    public static Object newInstance(Object target, Class[] interfaces) 
        throws IllegalArgumentException {
        
        if (target == null) {
            throw new IllegalArgumentException(
                         i18n.getMessage("nullArgument", "target"));
        }

        HashMap methodMap = new HashMap();
        Class targetClass = target.getClass();
        
        for (int i=0;i<interfaces.length;i++) {
            Method[] methods = interfaces[i].getMethods();
            for (int j=0;j<methods.length;j++) {
                Method targetMethod = null;
                
                try {
                    targetMethod = 
                        targetClass.getMethod(methods[j].getName(),
                                              methods[j].getParameterTypes());
                } catch (NoSuchMethodException e) {
                    throw new IllegalArgumentException(
                                 i18n.getMessage("apMissingMethod", 
                                                 methods[j].getName()));
                }
                
                if (!methods[j].getReturnType().equals(
                                   targetMethod.getReturnType())) {
                    throw new IllegalArgumentException(
                                 i18n.getMessage("apReturnMismatch",
                                                 methods[j].getName()));
                }
                
                if (!Arrays.equals(methods[j].getExceptionTypes(),
                                   targetMethod.getExceptionTypes())){
                    throw new IllegalArgumentException(
                                 i18n.getMessage("apExceptionMismatch", 
                                                 methods[j].getName()));
                }
                
                methodMap.put(methods[j], targetMethod);
            }
        }
        
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                                      interfaces,
                                      new AdapterProxy(target, methodMap));
    }
    
}
