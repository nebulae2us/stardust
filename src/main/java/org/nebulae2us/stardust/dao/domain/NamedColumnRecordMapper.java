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
package org.nebulae2us.stardust.dao.domain;

import org.nebulae2us.stardust.sql.domain.DataReader;

/**
 * @author Trung Phan
 *
 */
public class NamedColumnRecordMapper<T> extends RecordSetHandler<T> {

	private final Class<T> valueType;
	
	private final String columnName;
	
	private transient int columnIndex;
	
	public NamedColumnRecordMapper(Class<T> valueType, String columnName) {
		super(RecordSetHandler.MODE_DATA_READER);
		this.valueType = valueType;
		this.columnName = columnName;
	}
	
	@Override
	public int handleRecordSet(DataReader dataReader) {
		
		this.columnIndex = dataReader.findColumn(columnName);
		if (this.columnIndex == -1) {
			throw new IllegalStateException("Column " + columnName + " not found.");
		}
		
		return super.handleRecordSet(dataReader);
	}

	@Override
	public T mapRecord(DataReader dataReader) {
		return dataReader.read(valueType, columnIndex);
	}

}
