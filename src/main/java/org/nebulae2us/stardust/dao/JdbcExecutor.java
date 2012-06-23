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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.dialect.DefaultDialect;
import org.nebulae2us.stardust.dialect.Dialect;
import org.nebulae2us.stardust.exception.SqlException;
import org.nebulae2us.stardust.internal.util.SQLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class JdbcExecutor {
	
	private static final Logger logger = LoggerFactory.getLogger(JdbcExecutor.class);

	private final DataSource dataSource;
	
	protected final Dialect dialect;
	
	private static class ConnectionHolder {
		private int lock;
		private Connection connection;
	}
	
	private static final ThreadLocal<ConnectionHolder> connectionHolders = new ThreadLocal<ConnectionHolder>();
	
	public JdbcExecutor(DataSource dataSource) {
		this(dataSource, new DefaultDialect());
	}
	
	public JdbcExecutor(DataSource dataSource, Dialect dialect) {
		this.dataSource = dataSource;
		this.dialect = dialect;
	}
	
	public final Dialect getDialect() {
		return dialect;
	}

    public void beginUnitOfWork() {
    	ConnectionHolder connectionHolder = connectionHolders.get();
    	if (connectionHolder == null) {
    		connectionHolder = new ConnectionHolder();
    		connectionHolders.set(connectionHolder);
    	}
    	connectionHolder.lock++;
    }

    public void endUnitOfWork() {
    	ConnectionHolder connectionHolder = connectionHolders.get();
    	AssertState.notNull(connectionHolder, "Unit of work has not been started");
    	if (--connectionHolder.lock == 0) {
    		if (connectionHolder.connection != null) {
    			closeConnection(connectionHolder.connection);
    		}
    		connectionHolders.set(null);
    	}
    }
    
	private PreparedStatement prepareStatement(Connection connection, String sql, Map<String, ?> paramValues, List<?> wildcardValues) throws SQLException {
		
		String sqlToExecute = sql;
		List<?> values = wildcardValues;
		if (paramValues.size() > 0) {
			Pair<String, List<?>> deparameterizeResult = SQLUtils.deparameterizeSql(sql, paramValues, wildcardValues);
			sqlToExecute = deparameterizeResult.getItem1();
			values = deparameterizeResult.getItem2();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(sqlToExecute);
		}
		
		PreparedStatement preparedStatement = connection.prepareStatement(sqlToExecute);

		int length = values.size();
		for (int i = 0; i < length; i++) {
			Object value = values.get(i);
			setParamValueForPreparedStatement(preparedStatement, i+1, value);
		}
		
		return preparedStatement;
	}

	
	private void setParamValueForPreparedStatement(PreparedStatement preparedStatement, int i, Object _value) {
		
		Class<?> targetType = _value == null ? null : _value instanceof NullObject ? ((NullObject)_value).getJavaType() : dialect.getJavaTypeForPersistence(_value.getClass());
		Object value = convertValue(targetType, _value);
		try {
			if (_value instanceof NullObject) {
				preparedStatement.setNull(i, dialect.getSqlTypeFrom(targetType));
			}
			else if (value == null) {
				preparedStatement.setNull(i, Types.NULL);
			}
			else {
				preparedStatement.setObject(i, value, dialect.getSqlTypeFrom(targetType));
			}
		}
		catch (SQLException e) {
			throw new SqlException(e);
		}
	}
	

	private Object convertValue(Class<?> targetType, Object value) {
		if (value == null || targetType == null || value instanceof NullObject) {
			return null;
		}
		
		if (targetType.isInstance(value)) {
			return value;
		}
		
		if (String.class.isAssignableFrom(targetType)) {
			return value.toString();
		}
		else if (Number.class.isAssignableFrom(targetType)) {
			Number number = null;
			if (value instanceof Number) {
				number = (Number)value;
			}
			else if (value instanceof Character) {
				number = (int)((Character)value).charValue();
			}
			else {
				throw new IllegalStateException("Cannot convert value of type " + value.getClass().getSimpleName() + " to type " + targetType.getSimpleName());
			}
			
			if (targetType == Long.class) {
				return Long.valueOf(number.longValue());
			}
			else if (targetType == Integer.class) {
				return Integer.valueOf(number.intValue());
			}
			else if (targetType == Short.class) {
				return Short.valueOf(number.shortValue());
			}
			else if (targetType == Byte.class) {
				return Byte.valueOf(number.byteValue());
			}
			else if (targetType == Double.class) {
				return Double.valueOf(number.doubleValue());
			}
			else if (targetType == Float.class) {
				return Float.valueOf(number.floatValue());
			}
			else if (BigDecimal.class.isAssignableFrom(targetType)) {
				if (value instanceof BigInteger) {
					return new BigDecimal((BigInteger)value);
				}
				else {
					return BigDecimal.valueOf(number.doubleValue());
				}
			}
			else if (BigInteger.class.isAssignableFrom(targetType)) {
				if (value instanceof BigDecimal) {
					return ((BigDecimal)value).toBigInteger();
				}
				else {
					return BigInteger.valueOf(number.longValue());
				}
			}
		}
		else if (java.util.Date.class.isAssignableFrom(targetType)) {
			java.util.Date date = null;
			if (value instanceof java.util.Date) {
				date = (java.util.Date)value;
			}
			else if (value instanceof Calendar) {
				date = ((Calendar)value).getTime();
			}
			
			if (Timestamp.class.isAssignableFrom(targetType)) {
				return new Timestamp(date.getTime());
			}
			else if (Time.class.isAssignableFrom(targetType)) {
				return new Time(date.getTime());
			}
			else if (Date.class.isAssignableFrom(targetType)) {
				return new Date(date.getTime());
			}

			throw new IllegalStateException("Cannot convert value of type " + value.getClass().getSimpleName() + " to type " + targetType.getSimpleName());
		}
		
		throw new IllegalStateException("Cannot convert value of type " + value.getClass().getSimpleName() + " to type " + targetType.getSimpleName());
		
	}
	
	@SuppressWarnings("unchecked")
	public int update(String sql) {
		return update(sql, Collections.EMPTY_MAP, Collections.EMPTY_LIST);
	}
	
	public int update(String sql, Map<String, ?> paramValues) {
		return update(sql, paramValues, Collections.EMPTY_LIST);
	}
	
	@SuppressWarnings("unchecked")
	public int update(String sql, List<?> wildcardValues) {
		return update(sql, Collections.EMPTY_MAP, wildcardValues);
	}
	
	public int update(String sql, Map<String, ?> paramValues, List<?> wildcardValues) {
		Connection connection = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = prepareStatement(connection, sql, paramValues, wildcardValues);
			return prepStmt.executeUpdate();
		} catch (SQLException e) {
			throw new SqlException(e);
		}
		finally {
			closeStatement(prepStmt);
			releaseConnection(connection);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public int batchUpdate(String sql, int size, Map<String, List<?>> paramValues) {
		
		Assert.isTrue(size > 0, "size must be greater than 0");
		
		String sqlToExecute = sql;
		List<String> params = Collections.EMPTY_LIST;
		if (paramValues.size() > 0) {
			Pair<String, List<String>> parseResult = SQLUtils.parseSql(sqlToExecute);
			sqlToExecute = parseResult.getItem1();
			params = parseResult.getItem2();
		}
		
		
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlToExecute);

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < params.size(); i++) {
					String param = params.get(i);
					if (i == 0 && !paramValues.containsKey(param)) {
						throw new IllegalStateException("Cannot find values for parameter \"" + param + "\"");
					}
					List<?> values = paramValues.get(param);
					Object value = values.size() == 1 ? values.get(0) : values.get(i);
					setParamValueForPreparedStatement(preparedStatement, j+1, value);
				}
				
				preparedStatement.addBatch();
			}
		
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SqlException(e);
		}
		finally {
			closeStatement(preparedStatement);
			releaseConnection(connection);
		}
	}

	public int batchUpdate(String sql, int size, List<?> ... valuesList) {
		Assert.isTrue(size > 0, "size must be greater than 0");
		
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);

			int valuesListSize = valuesList.length;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < valuesListSize; j++ ) {
					List<?> values = valuesList[j];
					Object value = values.size() == 1 ? values.get(0) : values.get(i);
					setParamValueForPreparedStatement(preparedStatement, j+1, value);
				}
	
				preparedStatement.addBatch();
			}
		
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SqlException(e);
		}
		finally {
			closeStatement(preparedStatement);
			releaseConnection(connection);
		}
	}


	/**
	 * @param ddlSql
	 */
	public boolean execute(String sql) {
		
		Connection conn = getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			return stmt.execute(sql);
		}
		catch (SQLException e) {
			throw new SqlException(e);
		}
		finally {
			closeStatement(stmt);
			releaseConnection(conn);
		}
		
	}
	
	protected Connection getPhysicalConnection() {
		try {
			Connection conn = dataSource.getConnection();
			return conn;
		} catch (SQLException e1) {
			throw new SqlException(e1);
		}
	}
	
	protected Connection getConnection() {
		ConnectionHolder connectionHolder = connectionHolders.get();
		if (connectionHolder != null) {
			if (connectionHolder.connection == null) {
				connectionHolder.connection = getPhysicalConnection();
			}
			return connectionHolder.connection;
		}
		else {
			return getPhysicalConnection();
		}
	}
	
	protected void closePhysicalConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {}
	}
	
	protected void closeConnection(Connection connection) {
		if (dataSource instanceof ReleaseConnectionHandler) {
			((ReleaseConnectionHandler)dataSource).releaseConnection(connection);
		}
		else {
			closePhysicalConnection(connection);
		}
	}
	
	protected void releaseConnection(Connection connection) {
		if (connectionHolders.get() == null) {
			closeConnection(connection);
		}
	}
	
	protected void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {}
	}
	
	protected void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {}
	}

	
	
	
	
	
	
	
	/***************************************************
	 * 
	 * Convenient helper methods
	 *
	 ***************************************************/

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

	public <T> List<T> query(String sql, RecordSetHandler<T> recordMapper) {
		return query(sql, Immutables.emptyStringMap(), Immutables.emptyList(), recordMapper);
	}
	
	public <T> List<T> query(String sql, List<?> wildcardValues, RecordSetHandler<T> recordMapper) {
		return query(sql, Immutables.emptyStringMap(), wildcardValues, recordMapper);
	}
	
	public <T> List<T> query(String sql, Map<String, ?> paramValues, RecordSetHandler<T> recordMapper) {
		return query(sql, paramValues, Immutables.emptyList(), recordMapper);
	}
	
	public <T> List<T> query(String sql, Map<String, ?> paramValues, List<?> wildcardValues, RecordSetHandler<T> recordMapper) {
		Connection connection = getConnection();
		
		PreparedStatement predStmt = null;
		ResultSet resultSet = null;
		try {
			predStmt = prepareStatement(connection, sql, paramValues, wildcardValues);
			resultSet = predStmt.executeQuery();

			List<T> result = new ArrayList<T>();

			if (recordMapper.getMode() == RecordSetHandler.MODE_DATA_READER) {
				GenericDataReader dataReader = new GenericDataReader(resultSet);
				int nextAction = recordMapper.handleRecordSet(dataReader);
				if (nextAction == RecordSetHandler.ACTION_MAP_RECORD) {
					while (dataReader.next()) {
						T value = recordMapper.mapRecord(dataReader);
						result.add(value);
					}
				}
			}
			else {
				int nextAction = recordMapper.handleRecordSet(resultSet);
				int lineNum = 0;
				if (nextAction == RecordSetHandler.ACTION_MAP_RECORD) {
					while (resultSet.next()) {
						T value = recordMapper.mapRecord(resultSet, lineNum++);
						result.add(value);
					}
				}
			}
			return result;
		
		} catch (SQLException e) {
			throw new SqlException(e);
		}
		finally {
			closeResultSet(resultSet);
			closeStatement(predStmt);
			releaseConnection(connection);
		}
	}
	
	public <T> T queryFor(Class<T> valueType, String sql) {
		return queryFor(valueType, sql, Immutables.emptyStringMap(), Immutables.emptyList());
	}
	
	public <T> T queryFor(Class<T> valueType, String sql, List<?> wildcardValues) {
		return queryFor(valueType, sql, Immutables.emptyStringMap(), wildcardValues);
	}

	public <T> T queryFor(Class<T> valueType, String sql, Map<String, ?> paramValues) {
		return queryFor(valueType, sql, paramValues, Immutables.emptyList());
	}
	
	public <T> T queryFor(Class<T> valueType, String sql, Map<String, ?> paramValues, List<?> wildcardValues) {
		Connection connection = getConnection();
		
		PreparedStatement predStmt = null;
		try {
			predStmt = prepareStatement(connection, sql, paramValues, wildcardValues);
			ResultSet resultSet = predStmt.executeQuery();
			GenericDataReader dataReader = new GenericDataReader(resultSet);

			if (!dataReader.next()) {
				throw new IllegalStateException("No data found.");
			}
			
			T result = dataReader.read(valueType, 1);
			
			if (dataReader.next()) {
				throw new IllegalStateException("More than one record returned.");
			}
			
			return result;
		} 
		catch (SQLException e) {
			throw new SqlException(e);
		}
		finally {
			closeStatement(predStmt);
			releaseConnection(connection);
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
	
	public Timestamp queryForTimestamp(String sql, Map<String,?> paramValues, List<?> wildcardValues) {
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
	
	public Timestamp queryForTimestamp(String sql, List<?> wildcardValues) {
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
	
	public Timestamp queryForTimestamp(String sql, Map<String,?> paramValues) {
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
	
	public Timestamp queryForTimestamp(String sql) {
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
	
}
