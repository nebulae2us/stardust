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
package org.nebulae2us.stardust.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.nebulae2us.electron.util.ListBuilder;

/**
 * @author Trung Phan
 *
 */
public final class NullObject {
	
	public static final NullObject NULL_CHAR = new NullObject(Character.class);

	public static final NullObject NULL_STRING = new NullObject(String.class);
	
	public static final NullObject NULL_LONG = new NullObject(Long.class);

	public static final NullObject NULL_INTEGER = new NullObject(Integer.class);

	public static final NullObject NULL_SHORT = new NullObject(Short.class);

	public static final NullObject NULL_BYTE = new NullObject(Byte.class);

	public static final NullObject NULL_DOUBLE = new NullObject(Double.class);

	public static final NullObject NULL_FLOAT = new NullObject(Float.class);

	public static final NullObject NULL_BIG_DECIMAL = new NullObject(BigDecimal.class);

	public static final NullObject NULL_BIG_INTEGER = new NullObject(BigInteger.class);

	public static final NullObject NULL_DATE = new NullObject(Date.class);

	public static final NullObject NULL_TIME = new NullObject(Time.class);

	public static final NullObject NULL_TIMESTAMP = new NullObject(Timestamp.class);

	public static final NullObject NULL_JAVA_DATE = new NullObject(java.util.Date.class);

	public static final NullObject NULL_CALENDAR = new NullObject(Calendar.class);
	
	public static final NullObject NULL_GREGORIAN_CALENDAR = new NullObject(GregorianCalendar.class);

	public static final NullObject NULL_BYTE_ARRAY = new NullObject(byte[].class);
	
	public static final NullObject NULL_CHAR_ARRAY = new NullObject(char[].class);
	
	public static final NullObject NULL_BLOB = new NullObject(Blob.class);
	
	public static final NullObject NULL_CLOB = new NullObject(Clob.class);
	
	public static final NullObject NULL_BOOLEAN = new NullObject(Boolean.class);
	
	private static final List<NullObject> VALUES = new ListBuilder<NullObject>()
			.add(NULL_CHAR, NULL_STRING, NULL_LONG, NULL_INTEGER,NULL_SHORT, NULL_BYTE, NULL_DOUBLE, NULL_FLOAT, NULL_BIG_DECIMAL, NULL_BIG_INTEGER, 
					NULL_DATE, NULL_TIME, NULL_TIMESTAMP, NULL_JAVA_DATE, NULL_CALENDAR, NULL_GREGORIAN_CALENDAR,
					NULL_BYTE_ARRAY, NULL_CHAR_ARRAY, NULL_BLOB, NULL_CLOB, NULL_BOOLEAN)
			.toList();
	
	private final Class<?> javaType;
	
	private NullObject(Class<?> javaType) {
		this.javaType = javaType;
	}
	
	public final Class<?> getJavaType() {
		return javaType;
	}

	public static NullObject valueOf(Class<?> javaType) {
		if (javaType == null) {
			throw new NullPointerException();
		}
		for (NullObject no : VALUES) {
			if (no.getJavaType() == javaType) {
				return no;
			}
		}
		return new NullObject(javaType);
	}
	
}
