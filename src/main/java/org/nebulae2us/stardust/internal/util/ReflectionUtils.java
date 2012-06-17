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

import java.lang.reflect.Field;

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
	
	
}
