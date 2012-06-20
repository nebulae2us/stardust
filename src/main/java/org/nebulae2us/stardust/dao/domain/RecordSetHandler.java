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

import java.sql.ResultSet;

import org.nebulae2us.stardust.sql.domain.DataReader;

/**
 * @author Trung Phan
 *
 */
public abstract class RecordSetHandler<T> {
	
	public static final int MODE_DATA_READER = 1;
	
	public static final int MODE_RESULT_SET = 2;
	
	public static final int ACTION_MAP_RECORD = 1;
	
	public static final int SKIP_MAPPING_RECORD = 2;

	private final int mode;
	
	public RecordSetHandler(int mode) {
		this.mode = mode;
	}

	public final int getMode() {
		return mode;
	}

	public int handleRecordSet(DataReader dataReader) {
		return ACTION_MAP_RECORD;
	}
	
	public T mapRecord(DataReader dataReader) {
		throw new IllegalStateException("This method must be overriden.");
	}
	
	public int handleRecordSet(ResultSet resultSet) {
		return ACTION_MAP_RECORD;
	}
	
	public T mapRecord(ResultSet resultSet, int lineNum) {
		throw new IllegalStateException("This method must be overriden.");
	}
	
}
