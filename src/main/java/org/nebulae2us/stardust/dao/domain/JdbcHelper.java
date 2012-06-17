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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.util.Immutables;

/**
 * @author Trung Phan
 *
 */
public class JdbcHelper {

	private final JdbcExecutor jdbcExecutor;
	
	public JdbcHelper(JdbcExecutor jdbcExecutor) {
		this.jdbcExecutor = jdbcExecutor;
	}
	
	public <T> List<T> queryForListOf(Class<T> valueType, String sql, Map<String, ?> paramValues, List<?> wildcardValues) {
		return query(sql, paramValues, wildcardValues, new FirstColumnRecordMapper<T>(valueType));
	}
	
	public List<Long> queryForListOfLong(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Long.class, sql, paramValues, wildcardValues);
	}
	
	public List<Integer> queryForListOfInteger(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Integer.class, sql, paramValues, wildcardValues);
	}
	
	public List<Short> queryForListOfShort(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Short.class, sql, paramValues, wildcardValues);
	}
	
	public List<Byte> queryForListOfByte(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Byte.class, sql, paramValues, wildcardValues);
	}
	
	public List<Double> queryForListOfDouble(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Double.class, sql, paramValues, wildcardValues);
	}
	
	public List<Float> queryForListOfFloat(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Float.class, sql, paramValues, wildcardValues);
	}
	
	public List<BigDecimal> queryForListOfBigDecimal(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(BigDecimal.class, sql, paramValues, wildcardValues);
	}
	
	public List<BigInteger> queryForListOfBigInteger(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(BigInteger.class, sql, paramValues, wildcardValues);
	}
	
	public List<String> queryForListOfString(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(String.class, sql, paramValues, wildcardValues);
	}
	
	public List<byte[]> queryForListOfBytes(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(byte[].class, sql, paramValues, wildcardValues);
	}
	
	public List<Clob> queryForListOfClob(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Clob.class, sql, paramValues, wildcardValues);
	}
	
	public List<Blob> queryForListOfBlob(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Blob.class, sql, paramValues, wildcardValues);
	}
	
	public List<Date> queryForListOfDate(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Date.class, sql, paramValues, wildcardValues);
	}
	
	public List<Time> queryForListOfTime(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Time.class, sql, paramValues, wildcardValues);
	}
	
	public List<Timestamp> queryForListOfTimestamp(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Timestamp.class, sql, paramValues, wildcardValues);
	}
	
	public List<Boolean> queryForListOfBoolean(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryForListOf(Boolean.class, sql, paramValues, wildcardValues);
	}

	public <T> List<T> query(String sql, RecordMapper<T> recordMapper) {
		return query(sql, Immutables.emptyStringMap(), Immutables.emptyList(), recordMapper);
	}
	
	public <T> List<T> query(String sql, List<?> wildcardValues, RecordMapper<T> recordMapper) {
		return query(sql, Immutables.emptyStringMap(), wildcardValues, recordMapper);
	}
	
	public <T> List<T> query(String sql, Map<String, ?> paramValues, RecordMapper<T> recordMapper) {
		return query(sql, paramValues, Immutables.emptyList(), recordMapper);
	}
	
	public <T> List<T> query(String sql, Map<String, ?> paramValues, List<?> wildcardValues, RecordMapper<T> recordMapper) {
		
		ResultSet resultSet = jdbcExecutor.query(sql, paramValues, wildcardValues);
		GenericDataReader dataReader = new GenericDataReader(resultSet);

		try {
			List<T> result = new ArrayList<T>();
			recordMapper.before(dataReader);

			int totalRows = 0;
			while (dataReader.next()) {
				totalRows = dataReader.getRowNumber() + 1;
				T value = recordMapper.visitRow(dataReader, dataReader.getRowNumber());
				result.add(value);
			}
			
			recordMapper.after(dataReader, totalRows);
			dataReader.close();
			
			return result;
		}
		finally {
			dataReader.close();
		}
	}
	
	public <T> T queryFor(Class<T> valueType, String sql) {
		return queryFor(valueType, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public <T> T queryFor(Class<T> valueType, String sql, Map<String, ?> paramValues, List<?> wildcardValues) {
		ResultSet resultSet = jdbcExecutor.query(sql, paramValues, wildcardValues);
		GenericDataReader dataReader = new GenericDataReader(resultSet);
		
		try {
			if (!dataReader.next()) {
				throw new RuntimeException();
			}
			
			T result = dataReader.readObject(valueType, 1);
			
			if (dataReader.next()) {
				throw new RuntimeException();
			}
			
			return result;
		}
		finally {
			dataReader.close();
		}
	}
	
	
	public String queryForString(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(String.class, sql, paramValues, wildcardValues);
	}
	
	public Long queryForLong(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Long.class, sql, paramValues, wildcardValues);
	}
	
	public Integer queryForInt(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Integer.class, sql, paramValues, wildcardValues);
	}
	
	public Double queryForDouble(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Double.class, sql, paramValues, wildcardValues);
	}
	
	public Float queryForFloat(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Float.class, sql, paramValues, wildcardValues);
	}
	
	public Short queryForShort(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Short.class, sql, paramValues, wildcardValues);
	}
	
	public Byte queryForByte(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Byte.class, sql, paramValues, wildcardValues);
	}
	
	public byte[] queryForBytes(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(byte[].class, sql, paramValues, wildcardValues);
	}
	
	public Blob queryForBlob(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Blob.class, sql, paramValues, wildcardValues);
	}
	
	public Clob queryForClob(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Clob.class, sql, paramValues, wildcardValues);
	}
	
	public Date queryForDate(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(java.sql.Date.class, sql, paramValues, wildcardValues);
	}
	
	public Time queryForTime(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Time.class, sql, paramValues, wildcardValues);
	}
	
	public Date queryForTimestamp(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Timestamp.class, sql, paramValues, wildcardValues);
	}
	
	public BigDecimal queryForBigDecimal(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(BigDecimal.class, sql, paramValues, wildcardValues);
	}
	
	public BigInteger queryForBigInteger(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(BigInteger.class, sql, paramValues, wildcardValues);
	}
	
	public Boolean queryForBoolean(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
		return queryFor(Boolean.class, sql, paramValues, wildcardValues);
	}
	
	
	public String queryForString(String sql, List<?> wildcardValues) {
		return queryFor(String.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Long queryForLong(String sql, List<?> wildcardValues) {
		return queryFor(Long.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Integer queryForInt(String sql, List<?> wildcardValues) {
		return queryFor(Integer.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Double queryForDouble(String sql, List<?> wildcardValues) {
		return queryFor(Double.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Float queryForFloat(String sql, List<?> wildcardValues) {
		return queryFor(Float.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Short queryForShort(String sql, List<?> wildcardValues) {
		return queryFor(Short.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Byte queryForByte(String sql, List<?> wildcardValues) {
		return queryFor(Byte.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public byte[] queryForBytes(String sql, List<?> wildcardValues) {
		return queryFor(byte[].class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Blob queryForBlob(String sql, List<?> wildcardValues) {
		return queryFor(Blob.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Clob queryForClob(String sql, List<?> wildcardValues) {
		return queryFor(Clob.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Date queryForDate(String sql, List<?> wildcardValues) {
		return queryFor(java.sql.Date.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Time queryForTime(String sql, List<?> wildcardValues) {
		return queryFor(Time.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Date queryForTimestamp(String sql, List<?> wildcardValues) {
		return queryFor(Timestamp.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public BigDecimal queryForBigDecimal(String sql, List<?> wildcardValues) {
		return queryFor(BigDecimal.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public BigInteger queryForBigInteger(String sql, List<?> wildcardValues) {
		return queryFor(BigInteger.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public Boolean queryForBoolean(String sql, List<?> wildcardValues) {
		return queryFor(Boolean.class, sql, Immutables.emptyStringMap(), wildcardValues);
	}
	
	public String queryForString(String sql, Map<String,?> paramValues) {
		return queryFor(String.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Long queryForLong(String sql, Map<String,?> paramValues) {
		return queryFor(Long.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Integer queryForInt(String sql, Map<String,?> paramValues) {
		return queryFor(Integer.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Double queryForDouble(String sql, Map<String,?> paramValues) {
		return queryFor(Double.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Float queryForFloat(String sql, Map<String,?> paramValues) {
		return queryFor(Float.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Short queryForShort(String sql, Map<String,?> paramValues) {
		return queryFor(Short.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Byte queryForByte(String sql, Map<String,?> paramValues) {
		return queryFor(Byte.class, sql, paramValues, Immutables.emptyList());
	}
	
	public byte[] queryForBytes(String sql, Map<String,?> paramValues) {
		return queryFor(byte[].class, sql, paramValues, Immutables.emptyList());
	}
	
	public Blob queryForBlob(String sql, Map<String,?> paramValues) {
		return queryFor(Blob.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Clob queryForClob(String sql, Map<String,?> paramValues) {
		return queryFor(Clob.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Date queryForDate(String sql, Map<String,?> paramValues) {
		return queryFor(java.sql.Date.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Time queryForTime(String sql, Map<String,?> paramValues) {
		return queryFor(Time.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Date queryForTimestamp(String sql, Map<String,?> paramValues) {
		return queryFor(Timestamp.class, sql, paramValues, Immutables.emptyList());
	}
	
	public BigDecimal queryForBigDecimal(String sql, Map<String,?> paramValues) {
		return queryFor(BigDecimal.class, sql, paramValues, Immutables.emptyList());
	}
	
	public BigInteger queryForBigInteger(String sql, Map<String,?> paramValues) {
		return queryFor(BigInteger.class, sql, paramValues, Immutables.emptyList());
	}
	
	public Boolean queryForBoolean(String sql, Map<String,?> paramValues) {
		return queryFor(Boolean.class, sql, paramValues, Immutables.emptyList());
	}
	
	public String queryForString(String sql) {
		return queryFor(String.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Long queryForLong(String sql) {
		return queryFor(Long.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Integer queryForInt(String sql) {
		return queryFor(Integer.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Double queryForDouble(String sql) {
		return queryFor(Double.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Float queryForFloat(String sql) {
		return queryFor(Float.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Short queryForShort(String sql) {
		return queryFor(Short.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Byte queryForByte(String sql) {
		return queryFor(Byte.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public byte[] queryForBytes(String sql) {
		return queryFor(byte[].class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Blob queryForBlob(String sql) {
		return queryFor(Blob.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Clob queryForClob(String sql) {
		return queryFor(Clob.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Date queryForDate(String sql) {
		return queryFor(java.sql.Date.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Time queryForTime(String sql) {
		return queryFor(Time.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Date queryForTimestamp(String sql) {
		return queryFor(Timestamp.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public BigDecimal queryForBigDecimal(String sql) {
		return queryFor(BigDecimal.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public BigInteger queryForBigInteger(String sql) {
		return queryFor(BigInteger.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public Boolean queryForBoolean(String sql) {
		return queryFor(Boolean.class, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public int update(String sql, List<?> wildcardValues) {
		return jdbcExecutor.update(sql, wildcardValues);
	}

	public int update(String sql, Map<String, ?> paramValues, List<?> wildcardValues) {
		return jdbcExecutor.update(sql, paramValues, wildcardValues);
	}
	
	public int batchUpdate(String sql, int size, Map<String, List<?>> paramValues) {
		return jdbcExecutor.batchUpdate(sql, size, paramValues);
	}
	
	public int batchUpdate(String sql, int size, List<?> ... valuesList) {
		return jdbcExecutor.batchUpdate(sql, size, valuesList);
	}
	
}
