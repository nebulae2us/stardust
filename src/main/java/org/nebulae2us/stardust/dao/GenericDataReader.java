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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.nebulae2us.stardust.exception.SqlException;
import org.nebulae2us.stardust.sql.domain.DataReader;

/**
 * 
 * Not thread safe.
 * 
 * @author Trung Phan
 *
 */
public class GenericDataReader extends DataReader {

	private final ResultSet resultSet;
	
	private int rowNumber = -1;
	
	public GenericDataReader(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
	@Override
	public int findColumn(String columnName) {
		try {
			return resultSet.findColumn(columnName);
		} catch (SQLException e) {
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T read(Class<T> valueType, int columnIndex) {
		
		try {
			if (valueType == String.class) {
				return (T)resultSet.getString(columnIndex);
			}
			else if (valueType.isPrimitive()) {
				if (valueType == long.class) {
					return (T)Long.valueOf(resultSet.getLong(columnIndex));
				}
				else if (valueType == int.class) {
					return (T)Integer.valueOf(resultSet.getInt(columnIndex));
				}
				else if (valueType == short.class) {
					return (T)Short.valueOf(resultSet.getShort(columnIndex));
				}
				else if (valueType == byte.class) {
					return (T)Byte.valueOf(resultSet.getByte(columnIndex));
				}
				else if (valueType == double.class) {
					return (T)Double.valueOf(resultSet.getDouble(columnIndex));
				}
				else if (valueType == float.class) {
					return (T)Float.valueOf(resultSet.getFloat(columnIndex));
				}
				else if (valueType == boolean.class) {
					return (T)Boolean.valueOf(resultSet.getBoolean(columnIndex));
				}
				else if (valueType == char.class) {
					String s = resultSet.getString(columnIndex);
					return (T)Character.valueOf(s == null || s.length() == 0 ? '\0' : s.charAt(0));
				}
			}
			else if (Number.class.isAssignableFrom(valueType)) {
				T result = null;
				if (valueType == Long.class) {
					result = (T)Long.valueOf(resultSet.getLong(columnIndex));
				}
				else if (valueType == Integer.class) {
					result = (T)Integer.valueOf(resultSet.getInt(columnIndex));
				}
				else if (valueType == Short.class) {
					result = (T)Short.valueOf(resultSet.getShort(columnIndex));
				}
				else if (valueType == Byte.class) {
					result = (T)Byte.valueOf(resultSet.getByte(columnIndex));
				}
				else if (valueType == Double.class) {
					result = (T)Double.valueOf(resultSet.getDouble(columnIndex));
				}
				else if (valueType == Float.class) {
					result = (T)Float.valueOf(resultSet.getFloat(columnIndex));
				}
				else if (valueType == BigDecimal.class) {
					result = (T)resultSet.getBigDecimal(columnIndex);
				}
				else if (valueType == BigInteger.class) {
					BigDecimal d = resultSet.getBigDecimal(columnIndex);
					if (d != null) {
						result = (T)d.toBigInteger();
					}
				}
				else if (valueType == AtomicInteger.class) {
					result = (T)new AtomicInteger(resultSet.getInt(columnIndex));
				}
				else if (valueType == AtomicLong.class) {
					result = (T)new AtomicLong(resultSet.getLong(columnIndex));
				}
				else {
					throw new IllegalStateException("Unknown return type: " + valueType);
				}
				
				if (resultSet.wasNull()) {
					result = null;
				}
				return result;
			}
			else if (Date.class.isAssignableFrom(valueType)) {
				if (java.sql.Date.class.isAssignableFrom(valueType)) {
					return (T)resultSet.getDate(columnIndex);
				}
				else if (Time.class.isAssignableFrom(valueType)) {
					return (T)resultSet.getTime(columnIndex);
				} else {
					Timestamp ts = resultSet.getTimestamp(columnIndex);
					return Timestamp.class.isAssignableFrom(valueType) ? (T)ts : ts == null ? null : (T)new Date(ts.getTime());
				}
			}
			else if (valueType == Boolean.class) {
				T result = (T)Boolean.valueOf(resultSet.getBoolean(columnIndex));
				if (resultSet.wasNull()) {
					result = null;
				}
				return result;
			}
			else if (valueType == Character.class) {
				String s = resultSet.getString(columnIndex);
				if (s == null || s.length() == 0 || resultSet.wasNull()) {
					return null;
				}
				return (T)Character.valueOf(s.charAt(0));
			}
			else if (valueType == byte[].class) {
				return (T)resultSet.getBytes(columnIndex);
			}
			else if (valueType == Clob.class) {
				return (T)resultSet.getClob(columnIndex);
			}
			else if (valueType == Blob.class) {
				return (T)resultSet.getBlob(columnIndex);
			}
			
		}
		catch (SQLException e) {
			throw new IllegalStateException("Failed to retrieve data at column: " + columnIndex, e);
		}
		
		return null;
	}

	@Override
	public <T> T read(Class<T> expectedClass, String columnName) {
		int i = findColumn(columnName);
		return read(expectedClass, i);
	}
	
	@Override
	public boolean next() {
		try {
			boolean result = resultSet.next();
			rowNumber++;
			return result;
		} catch (SQLException e) {
			throw new SqlException(e);
		}
	}

	@Override
	public void close() {
		try {
			resultSet.close();
		} catch (SQLException e) {
		}
	}

	@Override
	public int getRowNumber() {
		return rowNumber;
	}

}
