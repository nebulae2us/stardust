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
package com.nebulae2us.stardust.dao.domain;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.nebulae2us.stardust.dao.domain.GenericDataReader;
import org.nebulae2us.stardust.sql.domain.DataReader;

import com.nebulae2us.stardust.BaseIntegrationTest;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
@RunWith(Parameterized.class)
public class JdbcOperation_prepareStatement_IT extends BaseIntegrationTest {
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{"derby-in-memory"}, {"h2-in-memory"}, {"hsqldb-in-memory"}
		});
	}
	
	
	public JdbcOperation_prepareStatement_IT(String configuration) {
		super(configuration);
	}
	
	@Before
	public void setup() throws Exception {
		String sqlCreateTestTable = "create table jdbc_test (a bigint, b varchar(100))";
		
		Statement stmt = connection.createStatement();
		stmt.execute(sqlCreateTestTable);
		
		String sqlInsertTestData = "insert into jdbc_test(a, b) values (?, ?)";
		PreparedStatement prepStmt = connection.prepareStatement(sqlInsertTestData);
		prepStmt.setLong(1, 100);
		prepStmt.setString(2, "Test String");
		prepStmt.execute();
		connection.commit();
		
	}
	
	@After
	public void tearDown() throws Exception {
		String sqlDropTestTable = "drop table jdbc_test";

		if (connection != null && !connection.isClosed()) {
			Statement stmt = connection.createStatement();
			stmt.execute(sqlDropTestTable);
			connection.close();
		}
	}
	
	private <T> void testQuery(Class<T> expectedClass, T expectedValue, String sqlTest, Object ... values) throws SQLException {
		PreparedStatement prepStmt = jdbcOperation.prepareStatement(sqlTest, Arrays.asList(values));
		
		ResultSet rs = prepStmt.executeQuery();
		DataReader dataReader = new GenericDataReader(rs);
		boolean hasData = rs.next();
		assertTrue(hasData);
		assertEquals(expectedValue, dataReader.readObject(expectedClass, 1));

		hasData = rs.next();
		assertFalse(hasData);
		
		rs.close();
	}
	
	@Test
	public void test_1() throws Exception {
		testQuery(Long.class, 100L, "select a from jdbc_test where a = ?", 100);
		testQuery(Long.class, 100L, "select a from jdbc_test where a = ?", 100L);
		testQuery(Long.class, 100L, "select a from jdbc_test where a = ?", (short)100);
		testQuery(Long.class, 100L, "select a from jdbc_test where a = ?", (byte)100);
		testQuery(Long.class, 100L, "select a from jdbc_test where a = ?", (double)100);
		testQuery(Long.class, 100L, "select a from jdbc_test where a = ?", (float)100);
		testQuery(Long.class, 100L, "select a from jdbc_test where a = ?", "100");

	
		testQuery(Integer.class, 100, "select a from jdbc_test where a = ?", 100);
		testQuery(Short.class, (short)100, "select a from jdbc_test where a = ?", 100);
		testQuery(Byte.class, (byte)100, "select a from jdbc_test where a = ?", 100);
		testQuery(Double.class, 100d, "select a from jdbc_test where a = ?", 100);
		testQuery(Float.class, 100f, "select a from jdbc_test where a = ?", 100);
		testQuery(String.class, "100", "select a from jdbc_test where a = ?", 100);
		testQuery(int.class, 100, "select a from jdbc_test where a = ?", 100);
		testQuery(long.class, 100L, "select a from jdbc_test where a = ?", 100);
		testQuery(short.class, (short)100, "select a from jdbc_test where a = ?", 100);
		testQuery(byte.class, (byte)100, "select a from jdbc_test where a = ?", 100);
		testQuery(double.class, 100d, "select a from jdbc_test where a = ?", 100);
		testQuery(float.class, 100f, "select a from jdbc_test where a = ?", 100);
	
	
	}
	
	private <T> void _test_null(Class<T> expectedClass, String sqlTest, Object ... values) throws SQLException {
		PreparedStatement prepStmt = jdbcOperation.prepareStatement(sqlTest, Arrays.asList(values));
		
		ResultSet rs = prepStmt.executeQuery();
		DataReader dataReader = new GenericDataReader(rs);
		boolean hasData = rs.next();
		assertTrue(hasData);
		assertNull(dataReader.readObject(expectedClass, 1));

		hasData = rs.next();
		assertFalse(hasData);
	}
	
	@Test
	public void retrieve_null_value() throws Exception {
		
		// derby requires null to be cast into a type;
		if (configuration.startsWith("derby")) {
			return;
		}
		
		
		_test_null(Long.class, "select ? from jdbc_test", (Object)null);
		_test_null(Integer.class, "select ? from jdbc_test", (Object)null);
		_test_null(Short.class, "select ? from jdbc_test", (Object)null);
		_test_null(Byte.class, "select ? from jdbc_test", (Object)null);
		_test_null(Double.class, "select ? from jdbc_test", (Object)null);
		_test_null(Float.class, "select ? from jdbc_test", (Object)null);
		_test_null(Boolean.class, "select ? from jdbc_test", (Object)null);
		_test_null(String.class, "select ? from jdbc_test", (Object)null);
		_test_null(Character.class, "select ? from jdbc_test", (Object)null);
		_test_null(Date.class, "select ? from jdbc_test", (Object)null);
		_test_null(Time.class, "select ? from jdbc_test", (Object)null);
		_test_null(Timestamp.class, "select ? from jdbc_test", (Object)null);
		_test_null(java.util.Date.class, "select ? from jdbc_test", (Object)null);
		
		_test_null(byte[].class, "select ? from jdbc_test", (Object)null);
		_test_null(Clob.class, "select ? from jdbc_test", (Object)null);
		_test_null(Blob.class, "select ? from jdbc_test", (Object)null);
	
	}
	
	@Test
	public void retrieve_literal_null_value() throws Exception {
		
		// derby requires null to be cast into a type;
		if (configuration.startsWith("derby")) return;
		
		
		_test_null(Long.class, "select null from jdbc_test");
		_test_null(Integer.class, "select null from jdbc_test");
		_test_null(Short.class, "select null from jdbc_test");
		_test_null(Byte.class, "select null from jdbc_test");
		_test_null(Double.class, "select null from jdbc_test");
		_test_null(Float.class, "select null from jdbc_test");
		_test_null(Boolean.class, "select null from jdbc_test");
		_test_null(String.class, "select null from jdbc_test");
		_test_null(Character.class, "select null from jdbc_test");
		_test_null(Date.class, "select null from jdbc_test");
		_test_null(Time.class, "select null from jdbc_test");
		_test_null(Timestamp.class, "select null from jdbc_test");
		_test_null(java.util.Date.class, "select null from jdbc_test");
		
		_test_null(byte[].class, "select null from jdbc_test");
		_test_null(Clob.class, "select null from jdbc_test");
		_test_null(Blob.class, "select null from jdbc_test");
	
	}
	
	@Test
	public void retrieve_null_int() throws Exception {
		_test_null(Long.class, "select cast(null as int) from jdbc_test");
		_test_null(Integer.class, "select cast(null as int) from jdbc_test");
		_test_null(Short.class, "select cast(null as int) from jdbc_test");
		_test_null(Byte.class, "select cast(null as int) from jdbc_test");
		_test_null(Double.class, "select cast(null as int) from jdbc_test");
		_test_null(Float.class, "select cast(null as int) from jdbc_test");
		_test_null(Boolean.class, "select cast(null as int) from jdbc_test");
		_test_null(String.class, "select cast(null as int) from jdbc_test");
		_test_null(Character.class, "select cast(null as int) from jdbc_test");
		_test_null(Date.class, "select cast(null as int) from jdbc_test");
		_test_null(Time.class, "select cast(null as int) from jdbc_test");
		_test_null(Timestamp.class, "select cast(null as int) from jdbc_test");
		_test_null(java.util.Date.class, "select cast(null as int) from jdbc_test");
		
		_test_null(byte[].class, "select cast(null as int) from jdbc_test");
		
		if (configuration.startsWith("derby")) return;
		
		_test_null(Clob.class, "select cast(null as int) from jdbc_test");
		_test_null(Blob.class, "select cast(null as int) from jdbc_test");
		
	}
	
}
