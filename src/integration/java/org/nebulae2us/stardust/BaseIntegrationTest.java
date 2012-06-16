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
package org.nebulae2us.stardust;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.After;
import org.nebulae2us.stardust.dao.domain.ConnectionProvider;
import org.nebulae2us.stardust.dao.domain.JdbcExecutor;
import org.nebulae2us.stardust.exception.SqlException;

/**
 * @author Trung Phan
 *
 */
public class BaseIntegrationTest {

	protected final String configuration;
	protected final String driverClass;
	protected final String url;
	protected final String username;
	protected final String password;
	
	protected final Connection connection;
	
	protected final JdbcExecutor jdbcExecutor;

	@After
	public void closeConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
	
	public BaseIntegrationTest(String configuration) {
		Properties prop = new Properties();
		
		try {
			prop.load(this.getClass().getResourceAsStream("/db/" + configuration + ".properties"));
		} catch (IOException e) {
			throw new IllegalStateException("Failed to load configuration file", e);
		}
		
		this.configuration = configuration;
		this.driverClass = prop.getProperty("driverClass");
		this.url = prop.getProperty("url");
		this.username = prop.getProperty("username");
		this.password = prop.getProperty("password");
		
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Failed to load driver class: " + driverClass, e);
		}
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			throw new SqlException(e);
		}
		
		this.jdbcExecutor = new JdbcExecutor(new ConnectionProvider() {
			public Connection getConnection() {
				return connection;
			}
		});
		
	}

}
