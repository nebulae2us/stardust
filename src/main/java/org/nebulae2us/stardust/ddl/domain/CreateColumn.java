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
package org.nebulae2us.stardust.ddl.domain;

import org.nebulae2us.stardust.db.domain.Column;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class CreateColumn {

	private final Column column;
	private final ColumnType columnType;
	private final boolean isIdentity;
	
	public CreateColumn(Column column, ColumnType columnType, boolean isIdentity) {
		Assert.notNull(column, "column cannot be null");
		Assert.notNull(columnType, "columnType cannot be null.");
		this.column = column;
		this.columnType = columnType;
		this.isIdentity = isIdentity;
	}

	public Column getColumn() {
		return column;
	}
	
	public ColumnType getColumnType() {
		return columnType;
	}
	
	public boolean isIdentity() {
		return isIdentity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}
		
		CreateColumn cc = (CreateColumn)o;
		return this.column.equals(cc.column);
	}
	
	@Override
	public int hashCode() {
		return this.column.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(column.getName().toString()).append(' ').append(columnType.toString());
		return result.toString();
	}
}
