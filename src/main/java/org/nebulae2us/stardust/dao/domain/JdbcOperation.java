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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.stardust.exception.IllegalSyntaxException;
import org.nebulae2us.stardust.exception.SqlException;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class JdbcOperation {

	private final ConnectionProvider connectionProvider;
	
	public JdbcOperation(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}
	
	
	protected Pair<String, List<?>> transformSql(String sql, Map<String, ?> paramValues, List<?> wildcardValues) {
		List<Object> values = new ArrayList<Object>();
		StringBuilder newSql = new StringBuilder();
		
		int length = sql.length();
		
		boolean inParam = false;
		StringBuilder paramNameBuilder = null;
		
		for (int i = 0, j = 0; i <= length; i++) {
			char c = i < length ? sql.charAt(i) : '\0';
			if (c == '?') {
				if (inParam) {
					throw new IllegalSyntaxException("Invalid parameter expression \":" + paramNameBuilder.toString() + "\"");
				}
				values.add(wildcardValues.get(j++));
				newSql.append(c);
			}
			else if (c == ':') {
				if (inParam) {
					throw new IllegalSyntaxException("Invalid parameter expression \":" + paramNameBuilder.toString() + "\"");
				}
				
				paramNameBuilder = new StringBuilder();
				inParam = true;
			}
			else if (inParam) {
				if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c < '9' || c == '_' || c == '$' || c == '#') {
					paramNameBuilder.append(c);
				}
				else {
					inParam = false;
					String paramName = paramNameBuilder.toString();
					paramNameBuilder = null;
					if (!paramValues.containsKey(paramName)) {
						throw new IllegalSyntaxException("Cannot find value for parameter \"" + paramName + "\"");
					}
					values.add(paramValues.get(paramName));
					newSql.append('?').append(c);
				}
			}
			else {
				newSql.append(c);
			}
		}

		newSql.deleteCharAt(newSql.length() - 1);
		
		return new Pair<String, List<?>>(newSql.toString(), values);
	}	
	
	@SuppressWarnings("unchecked")
	public PreparedStatement prepareStatement(String sql, List<?> wildcardValues) {
		return prepareStatement(sql, Collections.EMPTY_MAP, wildcardValues);
	}

	public PreparedStatement prepareStatement(String sql, Map<String, ?> paramValues) {
		return prepareStatement(sql, paramValues, Collections.EMPTY_LIST);
	}

	public PreparedStatement prepareStatement(String sql, Map<String, ?> paramValues, List<?> wildcardValues) {
		
		String sqlToExecute = sql;
		List<?> values = wildcardValues;
		if (paramValues.size() > 0) {
			Pair<String, List<?>> transformResult = transformSql(sql, paramValues, wildcardValues);
			sqlToExecute = transformResult.getItem1();
			values = transformResult.getItem2();
		}
		
		
		Connection connection = connectionProvider.getConnection();
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sqlToExecute);
		} catch (SQLException e) {
			throw new SqlException(e);
		}

		int length = values.size();
		for (int i = 0; i < length; i++) {
			Object value = values.get(i);
			setParamValueForPreparedStatement(preparedStatement, i+1, value);
		}
		
		return preparedStatement;
	}


	private void setParamValueForPreparedStatement(PreparedStatement preparedStatement, int i, Object value) {
		try {
			if (value == null) {
				preparedStatement.setNull(i, Types.NULL);
			}
			else if (value instanceof NullObject) {
				preparedStatement.setNull(i, ((NullObject)value).getSqlType());
			}
			else if (value instanceof String) {
				preparedStatement.setString(i, (String)value);
			}
			else if (value instanceof Character) {
				preparedStatement.setString(i, String.valueOf((Character)value));
			}
			else if (value instanceof Enum) {
				preparedStatement.setString(i, ((Enum<?>)value).name());
			}
			else if (value instanceof Number) {
				Number number = (Number)value;
				if (value instanceof Long) {
					preparedStatement.setLong(i, number.longValue());
				}
				else if (value instanceof Integer) {
					preparedStatement.setInt(i, number.intValue());
				}
				else if (value instanceof Short) {
					preparedStatement.setShort(i, number.shortValue());
				}
				else if (value instanceof Byte) {
					preparedStatement.setByte(i, number.byteValue());
				}
				else if (value instanceof Double) {
					preparedStatement.setDouble(i, number.doubleValue());
				}
				else if (value instanceof Float) {
					preparedStatement.setFloat(i, number.floatValue());
				}
				else if (value instanceof BigDecimal) {
					preparedStatement.setBigDecimal(i, (BigDecimal)value);
				}
				else if (value instanceof AtomicInteger) {
					preparedStatement.setInt(i, ((AtomicInteger)value).get());
				}
				else if (value instanceof AtomicLong) {
					preparedStatement.setLong(i, ((AtomicLong)value).get());
				}
				else if (value instanceof BigInteger) {
					BigDecimal d = new BigDecimal((BigInteger)value);
					preparedStatement.setBigDecimal(i, d);
				}
				else {
					preparedStatement.setObject(i, value);
				}
			}
			else if (value instanceof Date) {
				preparedStatement.setDate(i, (Date)value);
			}
			else if (value instanceof Time) {
				preparedStatement.setTime(i, (Time)value);
			}
			else if (value instanceof Timestamp) {
				preparedStatement.setTimestamp(i, (Timestamp)value);
			}
			else if (value instanceof java.util.Date) {
				preparedStatement.setObject(i, value);
			}
			else if (value instanceof Boolean) {
				preparedStatement.setBoolean(i, (Boolean)value);
			}
			else if (value instanceof AtomicBoolean) {
				preparedStatement.setBoolean(i, ((AtomicBoolean)value).get());
			}
			else if (value instanceof byte[]) {
				preparedStatement.setBytes(i, (byte[]) value);
			}
			else if (value instanceof Blob) {
				preparedStatement.setBlob(i, (Blob)value);
			}
			else if (value instanceof Clob) {
				preparedStatement.setClob(i, (Clob)value);
			}
			else if (value instanceof Reader) {
				preparedStatement.setCharacterStream(i, (Reader)value);
			}
			else if (value instanceof InputStream) {
				preparedStatement.setBinaryStream(i, (InputStream)value);
			}
			else if (value instanceof SQLXML) {
				preparedStatement.setSQLXML(i, (SQLXML)value);
			}
			else {
				preparedStatement.setObject(i, value);
			}
		}
		catch (SQLException e) {
			throw new SqlException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public ResultSet query(String sql) {
		return query(sql, Collections.EMPTY_MAP, Collections.EMPTY_LIST);
	}
	
	public ResultSet query(String sql, Map<String, ?> paramValues) {
		return query(sql, paramValues, Collections.EMPTY_LIST);
	}
	
	@SuppressWarnings("unchecked")
	public ResultSet query(String sql, List<?> wildcardValues) {
		return query(sql, Collections.EMPTY_MAP, wildcardValues);
	}
	
	public ResultSet query(String sql, Map<String, ?> paramValues, List<?> wildcardValues) {
		PreparedStatement prepStmt = prepareStatement(sql, paramValues, wildcardValues);
		try {
			return prepStmt.executeQuery();
		} catch (SQLException e) {
			throw new SqlException(e);
		}
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
		PreparedStatement prepStmt = prepareStatement(sql, paramValues, wildcardValues);
		
		try {
			return prepStmt.executeUpdate();
		} catch (SQLException e) {
			throw new SqlException(e);
		}
	}
	
	/**
	 * 
	 * Parse a sql such as "select :param1, :param2 from dual" and return a SQL with wildcards such as "select ?, ? from dual" and the list of parameters such as "[param1, param2]"
	 * 
	 * @param sql
	 * @param paramValues
	 * @return new parsed sql and list of parameter names
	 */
	protected Pair<String, List<String>> parseSql(String sql) {
		List<String> params = new ArrayList<String>();
		StringBuilder newSql = new StringBuilder();
		
		int length = sql.length();
		
		boolean inParam = false;
		StringBuilder paramNameBuilder = null;
		
		for (int i = 0; i <= length; i++) {
			char c = i < length ? sql.charAt(i) : '\0';
			if (c == '?') {
				throw new IllegalSyntaxException("Unexpected character ?");
			}
			else if (c == ':') {
				if (inParam) {
					throw new IllegalSyntaxException("Invalid parameter expression \":" + paramNameBuilder.toString() + "\"");
				}
				
				paramNameBuilder = new StringBuilder();
				inParam = true;
			}
			else if (inParam) {
				if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c < '9' || c == '_' || c == '$' || c == '#') {
					paramNameBuilder.append(c);
				}
				else {
					inParam = false;
					String paramName = paramNameBuilder.toString();
					paramNameBuilder = null;
					params.add(paramName);
					newSql.append('?').append(c);
				}
			}
			else {
				newSql.append(c);
			}
		}

		newSql.deleteCharAt(newSql.length() - 1);
		
		return new Pair<String, List<String>>(newSql.toString(), params);
	}		
	
	@SuppressWarnings("unchecked")
	public int batchUpdate(String sql, int size, Map<String, List<?>> paramValues) {
		
		Assert.isTrue(size > 0, "size must be greater than 0");
		
		String sqlToExecute = sql;
		List<String> params = Collections.EMPTY_LIST;
		if (paramValues.size() > 0) {
			Pair<String, List<String>> parseResult = parseSql(sqlToExecute);
			sqlToExecute = parseResult.getItem1();
			params = parseResult.getItem2();
		}
		
		
		Connection connection = connectionProvider.getConnection();
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sqlToExecute);
		} catch (SQLException e) {
			throw new SqlException(e);
		}

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
			
			try {
				preparedStatement.addBatch();
			} catch (SQLException e) {
				throw new SqlException(e);
			}
		}
		
		try {
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SqlException(e);
		}
	}

	public int batchUpdate(String sql, int size, List<?> ... valuesList) {
		Assert.isTrue(size > 0, "size must be greater than 0");
		
		Connection connection = connectionProvider.getConnection();
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SqlException(e);
		}

		int valuesListSize = valuesList.length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < valuesListSize; i++ ) {
				List<?> values = valuesList[j];
				Object value = values.size() == 1 ? values.get(0) : values.get(i);
				setParamValueForPreparedStatement(preparedStatement, j+1, value);
			}

			try {
				preparedStatement.addBatch();
			} catch (SQLException e) {
				throw new SqlException(e);
			}
		}
		
		try {
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SqlException(e);
		}
	}


	/**
	 * @param ddlSql
	 */
	public boolean execute(String sql) {
		
		Connection conn = connectionProvider.getConnection();
		try {
			Statement stmt = conn.createStatement();
			return stmt.execute(sql);
		}
		catch (SQLException e) {
			throw new SqlException(e);
		}
	}
	
}
