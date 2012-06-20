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

/**
 * 
 * Not thread safe due to the nature of ResultSet, and the state for next();
 * 
 * @author Trung Phan
 *
 */
public abstract class DataReader {
	
	public abstract int findColumn(String columnName);
	
	public abstract <T> T read(Class<T> expectedType, int columnIndex);

	public abstract <T> T read(Class<T> expectedType, String columnName);
	
	public abstract boolean next();
	
	/**
	 * Get the current row number. If next() has not been called, rowNumber = -1.
	 * If next() = false, rowNumber is undefined.
	 * 
	 * @return
	 */
	public abstract int getRowNumber();

	public abstract void close();
}
