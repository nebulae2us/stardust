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

import java.util.List;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.common.domain.LinkedNode;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * 
 * Linked Table define table and how it's linked to other LinkedTable.
 * 
 * @author Trung Phan
 *
 */
public class LinkedTable implements LinkedNode {

	private final LinkedTable parent;
	
	private final Table table;
	
	private final List<Column> parentColumns;
	
	private final List<Column> columns;
	
	private final JoinType joinType;
	
	public LinkedTable(Mirror mirror) {
		mirror.bind(this);
		
		this.parent = mirror.to(LinkedTable.class, "prevLinkedTable");
		this.table = mirror.to(Table.class, "table");
		this.parentColumns = mirror.toListOf(Column.class, "prevColumns");
		this.columns = mirror.toListOf(Column.class, "columns");
		this.joinType = mirror.to(JoinType.class, "joinType");
		
		verifyInvariant();
	}

	private void verifyInvariant() {
		Assert.notNull(table, "table cannot be null");
		
		if (this.parent == null) {
			Assert.isNull(joinType, "joinType cannot be defined for first node");
			Assert.empty(parentColumns, "prevColumns cannot be defined for the first node");
			Assert.empty(columns, "columns cannot be defined for the first node");
		}
		else {
			Assert.notEmpty(parentColumns, "prevColumns cannot be empty");
			Assert.notEmpty(columns, "columns cannot be empty");
			Assert.isTrue(columns.size() == parentColumns.size(), "columns' size mismatch");
			Assert.notNull(joinType, "joinType cannot be null");
			
			for (Column column : columns) {
				Assert.isTrue(table.equals(column.getTable()), "columns and table mismatch");
			}
			for (Column prevColumn : parentColumns) {
				Assert.isTrue(parent.table.equals(prevColumn.getTable()), "columns and table mismatch");
			}
		}
		
		
	}
	
	public LinkedTable getParent() {
		return parent;
	}

	public Table getTable() {
		return table;
	}

	public List<Column> getParentColumns() {
		return parentColumns;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public JoinType getJoinType() {
		return joinType;
	}
	
	
}
