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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

/**
 * @author Trung Phan
 *
 */
public class DriverManagerDataSource implements DataSource {

	private final Properties connectionProperties;
	
	private final String url;
	
	public DriverManagerDataSource(String driverClass, String url, String username, String password, Properties connectionProperties) {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Driver not found", e);
		}
		this.url = url;
		this.connectionProperties = new Properties(connectionProperties);
		this.connectionProperties.setProperty("user", username);
		this.connectionProperties.setProperty("password", password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, connectionProperties);
	}

	public Connection getConnection(String username, String password) throws SQLException {
		Properties info = new Properties(connectionProperties);
		info.setProperty("user", username);
		info.setProperty("password", password);
		return DriverManager.getConnection(url, info);
	}
	
}
