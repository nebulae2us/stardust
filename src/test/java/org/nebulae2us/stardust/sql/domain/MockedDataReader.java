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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.nebulae2us.electron.util.Immutables.*;

/**
 * @author Trung Phan
 *
 */
public class MockedDataReader extends DataReader {

	private final List<String> columnNames;
	
	private final List<Object[]> data;
	
	private int currentPos = -1;
	
	private final Map<String, Integer> columnNameIndexes;
	
	public MockedDataReader(List<String> columnNames, List<Object[]> data) {
		this.columnNames = $(columnNames).elementToUpperCase();
		this.data = data;
		
		columnNameIndexes = new HashMap<String, Integer>();
		for (int i = columnNames.size() - 1; i >= 0; i--) {
			columnNameIndexes.put(columnNames.get(i).toLowerCase(), i);
		}
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readObject(Class<T> expectedClass, int columnIndex) {
		return (T)this.data.get(currentPos)[columnIndex - 1];
	}

	@Override
	public boolean next() {
		return ++this.currentPos < this.data.size();
	}

	@Override
	public <T> T readObject(Class<T> expectedClass, String columnName) {
		int index = columnNameIndexes.get(columnName);
		return readObject(expectedClass, index + 1);
	}

	@Override
	public void close() {
	}

	@Override
	public int getRowNumber() {
		return this.currentPos;
	}

}
