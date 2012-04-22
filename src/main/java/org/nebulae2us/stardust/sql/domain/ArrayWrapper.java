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
package org.nebulae2us.stardust.sql.domain;

import java.util.Arrays;

/**
 * @author Trung Phan
 *
 */
public class ArrayWrapper {

	private final Object[] object;
	private final int hashCode;
	
	public ArrayWrapper(Object[] object) {
		this.object = object;
		this.hashCode = Arrays.hashCode(object);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		ArrayWrapper aw = (ArrayWrapper)o;
		return Arrays.equals(this.object, aw.object);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(this.object);
	}
	
}
