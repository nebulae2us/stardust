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
import org.nebulae2us.stardust.internal.util.ObjectUtils;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * 
 * @author Trung Phan
 *
 */
public class Table {

	private final String name;
	
	private final String schemaName;
	
	private final String catalogName;
	
	public Table(Mirror mirror) {
		mirror.bind(this);
		
		this.name = mirror.toString("name");
		this.schemaName = ObjectUtils.nvl(mirror.toString("schemaName"));
		this.catalogName = ObjectUtils.nvl(mirror.toString("catalogName"));
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notEmpty(this.name, "table name cannot be empty");

	}

	public String getName() {
		return name;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public String getCatalogName() {
		return catalogName;
	}
	
	public String getExtName() {
		return schemaName.length() > 0 ? schemaName + "." + name :
			   catalogName.length() > 0 ? catalogName + "." + name:
				   name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		
		Table t = (Table)o;
		
		return name.equals(t.name) && schemaName.equals(t.schemaName) && catalogName.equals(t.catalogName);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() ^ schemaName.hashCode() ^ catalogName.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}

	
}
