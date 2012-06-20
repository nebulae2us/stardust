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

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Trung Phan
 *
 */
public class SpringManagedDataSource implements DataSource, ReleaseConnectionHandler {

	private final DataSource dataSource;
	
	private final Method getConnectionMethod;
	
	private final Method releaseConnectionMethod;
	
	public SpringManagedDataSource(DataSource dataSource) {
		if (dataSource == null) {
			throw new NullPointerException();
		}
		this.dataSource = dataSource;
		
		String className = "org.springframework.jdbc.datasource.DataSourceUtils";
		
		Class<?> dataSourceUtilsClass;
		try {
			dataSourceUtilsClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Class Not Found", e);
		}
		
		try {
			this.getConnectionMethod = dataSourceUtilsClass.getMethod("getConnection", new Class<?>[]{DataSource.class});
			this.releaseConnectionMethod = dataSourceUtilsClass.getMethod("releaseConnection", new Class<?>[]{Connection.class, DataSource.class});
		} catch (SecurityException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		this.dataSource.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		this.dataSource.setLoginTimeout(seconds);
	}

	public int getLoginTimeout() throws SQLException {
		return this.dataSource.getLoginTimeout();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.dataSource.unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.dataSource.isWrapperFor(iface);
	}

	public Connection getConnection() throws SQLException {
		try {
			return (Connection)getConnectionMethod.invoke(null, this.dataSource);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get Connection.", e);
		}
	}

	public Connection getConnection(String username, String password) throws SQLException {
		try {
			return (Connection)getConnectionMethod.invoke(null, this.dataSource);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get Connection.", e);
		}
	}

	public void releaseConnection(Connection connection) {
		try {
			releaseConnectionMethod.invoke(null, connection, this.dataSource);
		} catch (Exception e) {
			throw new RuntimeException("Failed to release Connection.", e);
		}
	}

}
