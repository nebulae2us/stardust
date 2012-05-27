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

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.junit.Test;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.dao.domain.JdbcTemplate;

import com.nebulae2us.stardust.BaseIntegrationTest;
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

	private JdbcTemplate jdbcTemplate;
	
	public H2_SQL_IT() {
		super("h2-in-memory");
		
		jdbcTemplate = new JdbcTemplate(jdbcOperation);
	}

	@Test
	public void get_last_identity() throws SQLException {
		Statement stmt = connection.createStatement();
		
		stmt.execute("create table test_identity(my_id int identity, my_name varchar(100))");
		
		jdbcTemplate.update("insert into test_identity(my_name) values (?)", Arrays.asList("some vale"));
		
		Long id = jdbcTemplate.queryForLong("select scope_identity() from dual", Immutables.emptyStringMap(), Immutables.emptyList());
		
		assertEquals(Long.valueOf(1), id);
		
	}
	
	@Test
	public void get_sequence_value() throws SQLException {
		
		Statement stmt = connection.createStatement();
		stmt.execute("create sequence my_sequence");
		
		Long id = jdbcTemplate.queryForLong("select next value for my_sequence from dual", Immutables.emptyStringMap(), Immutables.emptyList());
		
		assertEquals(Long.valueOf(1), id);
	}
}
