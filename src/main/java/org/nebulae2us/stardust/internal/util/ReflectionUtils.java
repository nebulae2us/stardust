/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nebulae2us.stardust.internal.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Trung Phan
 *
 */
public class ReflectionUtils {

	public static Field findField(Class<?> objectClass, String fieldName) {
		try {
			Field field = objectClass.getDeclaredField(fieldName);
			return field;
		} catch (Exception e) {
			if (objectClass.getSuperclass() != null) {
				return findField(objectClass.getSuperclass(), fieldName);
			}
			return null;
		}
	}
	
	public static Constructor<?> findPublicConstructor(Class<?> objectClass, Class<?> ... parameterTypes) {
		try {
			return objectClass.getConstructor(parameterTypes);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	public static Constructor<?> findConstructor(Class<?> objectClass, Class<?> ... parameterTypes) {
		try {
			return objectClass.getDeclaredConstructor(parameterTypes);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	public static Object newObject(Constructor<?> constructor, Object ... initargs) {
		try {
			return constructor.newInstance(initargs);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (InstantiationException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	public static void setValue(Field field, Object object, Object value) {
		try {
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	public static Object getValue(Field field, Object object) {
		try {
			return field.get(object);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static List<Class<?>> scanPackage(final String packageName) {
		
		try {
		    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		    List<Class<?>> result = new ArrayList<Class<?>>();

		    String packagePath = packageName.replace('.', '/');
		    
		    URL packageURL = classLoader.getResource(packagePath);

		    if (packageURL != null) {
			    if(packageURL.getProtocol().equals("jar")){
			        String jarFileName = URLDecoder.decode(packageURL.getPath(), "UTF-8");
			        jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));

			        JarFile jf = new JarFile(jarFileName);
			        Enumeration<JarEntry> jarEntries = jf.entries();
			        while(jarEntries.hasMoreElements()){
			            String entryName = jarEntries.nextElement().getName();
			            if(entryName.endsWith(".class") && entryName.startsWith(packagePath) && entryName.length()>packagePath.length()+5){
			                entryName = entryName.substring(packagePath.length(), entryName.lastIndexOf('.'));
			                if (entryName.startsWith("/")) {
			                	entryName = entryName.substring(1);
			                }
			                if (entryName.indexOf('/') == -1 && !entryName.equals("package-info")) {
				                result.add(nameToClass(packageName, entryName, classLoader));
			                }
			            }
			        }

			    }else{
			    	File folder = new File(URLDecoder.decode(packageURL.getPath(), "UTF-8"));
			        File[] files = folder.listFiles();
			        if (files != null) {
				        for(File file: files){
				            String fileName = file.getName();
				            if (fileName.endsWith(".class")) {
					            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
				                result.add(nameToClass(packageName, fileName, classLoader));
				            }
				        }
			        }
			    }
		    }
		    return result;
		}
		catch (IOException e) {
			throw new IllegalStateException("Failed to scan package " + packageName, e);
		}
		
	}
	
	private static Class<?> nameToClass(String packageName, String entryName, ClassLoader classLoader) {
        try {
			String className = packageName.length() > 0 ? packageName + '.' + entryName : entryName;
			return Class.forName(className, false, classLoader);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
