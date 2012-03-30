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
}
