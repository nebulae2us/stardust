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

import java.util.Collections;
import java.util.List;
import static org.nebulae2us.electron.util.Immutables.*;

/**
 * @author Trung Phan
 *
 */
public class MockedDataReader extends DataReader {

	private final List<String> columnNames;
	
	private final List<Object[]> data;
	
	private int currentPos = -1;
	
	public MockedDataReader(List<String> columnNames, List<Object[]> data) {
		this.columnNames = $(columnNames).elementToUpperCase();
		this.data = data;
	}
	
	@SuppressWarnings("unchecked")
	public MockedDataReader(List<String> columnNames) {
		this(columnNames, Collections.EMPTY_LIST);
	}
	
	@Override
	public int findColumn(String columnName) {
		int idx = this.columnNames.indexOf(columnName.toUpperCase());
		return idx < 0 ? -1 : idx + 1;
	}

	@Override
	public <T> T readObject(Class<T> expectedClass, int columnIndex) {
		return (T)this.data.get(currentPos)[columnIndex - 1];
	}

	@Override
	public int readInt(int columnIndex) {
		Number number = readObject(Number.class, columnIndex);
		return number != null ? number.intValue() : 0;
	}

	@Override
	public long readLong(int columnIndex) {
		Number number = readObject(Number.class, columnIndex);
		return number != null ? number.longValue() : 0L;
	}

	@Override
	public short readShort(int columnIndex) {
		Number number = readObject(Number.class, columnIndex);
		return number != null ? number.shortValue() : 0;
	}

	@Override
	public byte readByte(int columnIndex) {
		Number number = readObject(Number.class, columnIndex);
		return number != null ? number.byteValue() : 0;
	}

	@Override
	public char readChar(int columnIndex) {
		Character character = readObject(Character.class, columnIndex);
		return character != null ? character.charValue() : 0;
	}

	@Override
	public double readDouble(int columnIndex) {
		Number number = readObject(Number.class, columnIndex);
		return number != null ? number.doubleValue() : 0.0;
	}

	@Override
	public float readFloat(int columnIndex) {
		Number number = readObject(Number.class, columnIndex);
		return number != null ? number.floatValue() : 0.0F;
	}

	@Override
	public boolean readBoolean(int columnIndex) {
		Boolean bool = readObject(Boolean.class, columnIndex);
		return bool != null ? bool.booleanValue() : false;
	}

	@Override
	public boolean next() {
		return ++this.currentPos < this.data.size();
	}

}
