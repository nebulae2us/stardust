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
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.After;
import org.nebulae2us.stardust.dao.domain.JdbcExecutor;
import org.nebulae2us.stardust.datasource.DriverManagerDataSource;
import org.nebulae2us.stardust.dialect.Dialect;
import org.nebulae2us.stardust.dialect.H2Dialect;
import org.nebulae2us.stardust.dialect.MckoiDialect;

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
	
	protected final DataSource dataSource;
	
	protected final JdbcExecutor jdbcExecutor;
	
	protected final Dialect dialect;
	
	/**
	 * Dummy connection is needed for in-memory database such as H2 to keep the database open.
	 */
	private Connection dummyConnection;

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
		
		String _dialect = prop.getProperty("dialect");
		try {
			Class<?> _dialectClass = Class.forName(Dialect.class.getName().replace("Dialect", _dialect + "Dialect"));
			this.dialect = (Dialect)_dialectClass.newInstance();
		} catch (Exception e1) {
			throw new RuntimeException("Failed to find dialect " + _dialect);
		}
		
		this.dataSource = new DriverManagerDataSource(this.driverClass, this.url, this.username, this.password, null);

		if (dialect instanceof H2Dialect) {
			try {
				this.dummyConnection = this.dataSource.getConnection();
			} catch (SQLException e) {}
		}
		
		this.jdbcExecutor = new JdbcExecutor(dialect, dataSource);
		
		if (dialect instanceof MckoiDialect) {
			this.jdbcExecutor.beginUnitOfWork();
		}
		
	}
	
	@After
	public void releaseResource() {
		if (dialect instanceof MckoiDialect) {
			this.jdbcExecutor.endUnitOfWork();
		}
		try {
			if (this.dummyConnection != null && !this.dummyConnection.isClosed()) {
				this.dummyConnection.close();
			}
		}
		catch (SQLException e) {}
	}

}
