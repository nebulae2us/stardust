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
package org.nebulae2us.stardust.db.domain;

import org.nebulae2us.electron.Mirror;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * 
 * @author Trung Phan
 *
 */
public class Column {

	private final String name;
	
	private final Table table;
	
	public Column(Mirror mirror) {
		mirror.bind(this);
		
		this.name = mirror.toString("name");
		this.table = mirror.to(Table.class, "table");
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notEmpty(name, "name cannot be empty");
		Assert.notNull(table, "table cannot be null for column %s", name);
	}
	
	private Column(String name, Table table) {
		this.name = name;
		this.table = table;
		
		assertInvariant();
	}
	
	/**
	 * 
	 * If table expression is missing, then default table is used.
	 * 
	 * @param expression example MY_SCHEMA.MY_TABLE.MY_COLUMN, MY_TABLE.MY_COLUMN, or MY_COLUMN
	 * @param defaultTable
	 * @return
	 */
	public static Column parse(String expression, Table defaultTable) {
		
		String[] segments = expression.split("\\.");
		if (segments.length == 1) {
			return new Column(expression, defaultTable);
		}
		else if (segments.length == 2) {
			Table table = new Table(segments[0], "", "");
			return new Column(segments[1], table);
		}
		else if (segments.length == 3) {
			Table table = new Table(segments[1], segments[0], "");
			return new Column(segments[2], table);
		}
		
		throw new IllegalStateException("Unknown column expression " + expression);
	}

	public String getName() {
		return name;
	}

	public Table getTable() {
		return table;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		
		Column c = (Column)o;
		
		return name.equals(c.name) && table.equals(c.table);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() ^ table.hashCode();
	}
	
	@Override
	public String toString() {
		return table.getName() + "." + name;
	}
}
