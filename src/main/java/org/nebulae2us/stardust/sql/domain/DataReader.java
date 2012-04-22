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
 * @author Trung Phan
 *
 */
public abstract class DataReader {
	
	public abstract int findColumn(String columnName);
	
	public abstract <T> T readObject(Class<T> expectedClass, int columnIndex);
	
	public abstract int readInt(int columnIndex);
	
	public abstract long readLong(int columnIndex);
	
	public abstract short readShort(int columnIndex);
	
	public abstract byte readByte(int columnIndex);
	
	public abstract char readChar(int columnIndex);
	
	public abstract double readDouble(int columnIndex);
	
	public abstract float readFloat(int columnIndex);
	
	public abstract boolean readBoolean(int columnIndex);
	
	public abstract boolean next();

}
