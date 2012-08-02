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
package org.nebulae2us.stardust.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Trung Phan
 *
 */
public class JndiDataSource implements DataSource {

	private final DataSource dataSource;
	
	public JndiDataSource(String jndiName) {
		this(null, jndiName);
	}
	
	public JndiDataSource(Properties properties, String jndiName) {

		Hashtable<String, Object> env = null;
		if (properties != null) {
			env = new Hashtable<String, Object>();
			Enumeration<?> names = properties.propertyNames();
			while (names.hasMoreElements()) {
				String name = (String)names.nextElement();
				Object value = properties.getProperty(name);
				env.put(name, value);
			}
		}
		
		try {
			Context context = new InitialContext(env);
			this.dataSource = (DataSource)context.lookup(jndiName);
		} catch (NamingException e) {
			throw new IllegalStateException("Failed to lookup for name: " + jndiName, e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.dataSource.setLogWriter(out);
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	public void setLoginTimeout(int seconds) throws SQLException {
		this.dataSource.setLoginTimeout(seconds);
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	public int getLoginTimeout() throws SQLException {
		return this.dataSource.getLoginTimeout();
	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this.dataSource)) {
			return (T)this.dataSource;
		}
		return this.dataSource.unwrap(iface);
	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		if (iface.isInstance(this.dataSource)) {
			return true;
		}
		return this.dataSource.isWrapperFor(iface);
	}

	/* (non-Javadoc)
	 * @see javax.sql.DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	/* (non-Javadoc)
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
	 */
	public Connection getConnection(String username, String password)
			throws SQLException {
		return this.dataSource.getConnection(username, password);
	}
	
}
