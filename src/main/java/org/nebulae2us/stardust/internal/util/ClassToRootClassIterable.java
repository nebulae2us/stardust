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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Trung Phan
 *
 */
public class ClassToRootClassIterable implements Iterable<Class<?>> {

	private final Class<?> fromClass;
	
	public ClassToRootClassIterable(Class<?> fromClass) {
		if (fromClass == null) {
			throw new NullPointerException();
		}
		this.fromClass = fromClass;
	}
	
	public Iterator<Class<?>> iterator() {
		return new Iterator<Class<?>>() {
			private Class<?> currentClass = fromClass;

			public boolean hasNext() {
				return currentClass != null;
			}

			public Class<?> next() {
				if (currentClass == null) {
					throw new NoSuchElementException();
				}
				Class<?> result = currentClass;
				currentClass = currentClass.getSuperclass();
				return result;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}

}
