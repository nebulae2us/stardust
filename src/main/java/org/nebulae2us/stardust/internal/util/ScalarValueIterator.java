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

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class ScalarValueIterator<T> implements Iterable<T> {

	private final Object[] values;
	
	public ScalarValueIterator(Object ... values) {
		Assert.notNull(values, "values cannot be null");
		
		this.values = values;
	}
	
	public Iterator<T> iterator() {
		return new ValueIterator();
	}

	public class ValueIterator implements Iterator<T> {

		private int pos;
		private Iterator<T> internalIterator;
		
		private boolean existValuesFromIndex(int index) {
			if (index >= values.length) {
				return false;
			}
			if (values[pos] instanceof Collection) {
				if (((Collection<?>)values[pos]).size() > 0) {
					return true;
				}
				else {
					return existValuesFromIndex(index + 1);
				}
			}
			else {
				return true;
			}
		}
		
		public boolean hasNext() {
			if (internalIterator != null) {
				if (internalIterator.hasNext()) {
					return true;
				}
				else {
					return existValuesFromIndex(pos + 1);
				}
			}
			return existValuesFromIndex(pos);
		}

		public T next() {
			if (internalIterator != null) {
				if (internalIterator.hasNext()) {
					return internalIterator.next();
				}
				else {
					internalIterator = null;
					pos++;
				}
			}
			
			while (true) {

				if (pos >= values.length) {
					throw new NoSuchElementException();
				}
				
				if (values[pos] instanceof Collection) {
					Collection<T> list = (Collection<T>)values[pos];
					if (list.size() > 0) {
						internalIterator = list.iterator();
						return internalIterator.next();
					}
					else {
						pos++;
					}
				}
				else {
					return (T)values[pos++];
				}
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}
