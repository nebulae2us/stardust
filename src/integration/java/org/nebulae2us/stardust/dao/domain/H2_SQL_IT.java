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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.junit.Test;
import org.nebulae2us.electron.util.Immutables;

import org.nebulae2us.stardust.BaseIntegrationTest;
import static org.junit.Assert.*;

/**
 * 
 * This test is to verify some sqls are working in H2. These sqls are the base for translators. If these test failed, the translators may also fail because
 * the assumption is that these sqls work.
 * 
 * @author Trung Phan
 *
 */
public class H2_SQL_IT extends BaseIntegrationTest {

	public H2_SQL_IT() {
		super("h2-in-memory");
	}

	@Test
	public void get_last_identity() throws SQLException {
		Connection connection = dataSource.getConnection();
		Statement stmt = connection.createStatement();
		
		stmt.execute("create table test_identity(my_id int identity, my_name varchar(100))");
		
		jdbcExecutor.beginUnitOfWork();
		try {
			jdbcExecutor.update("insert into test_identity(my_name) values (?)", Arrays.asList("some vale"));
			Long id = jdbcExecutor.queryForLong("select scope_identity() from dual", Immutables.emptyStringMap(), Immutables.emptyList());
			assertEquals(Long.valueOf(1), id);
		}
		finally {
			jdbcExecutor.endUnitOfWork();
		}
		
		
	}
	
	@Test
	public void get_sequence_value() throws SQLException {
		Connection connection = dataSource.getConnection();
		
		Statement stmt = connection.createStatement();
		stmt.execute("create sequence my_sequence");
		
		Long id = jdbcExecutor.queryForLong("select next value for my_sequence from dual", Immutables.emptyStringMap(), Immutables.emptyList());
		
		assertEquals(Long.valueOf(1), id);
	}
}
