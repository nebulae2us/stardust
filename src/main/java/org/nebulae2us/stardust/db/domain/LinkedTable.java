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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nebulae2us.electron.*;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class LinkedTable {

	private final LinkedTable parent;
	
	private final List<Column> parentColumns;
	
	private final Table table;
	
	private final List<Column> columns;
	
	private final JoinType joinType;
	
	public LinkedTable(Mirror mirror) {
		mirror.bind(this);
		
		this.parent = mirror.to(LinkedTable.class, "parent");
		this.parentColumns = mirror.toListOf(Column.class, "parentColumns");
		this.table = mirror.to(Table.class, "table");
		this.columns = mirror.toListOf(Column.class, "columns");
		this.joinType = mirror.to(JoinType.class, "joinType");
		
		assertInvariant();
	}
	
	private void assertInvariant() {
		Assert.notNull(table, "table cannot be null");
		
		if (this.parent == null) {
			Assert.isNull(joinType, "joinType cannot be defined for first node");
			Assert.empty(parentColumns, "parentColumns cannot be defined for the first node");
			Assert.empty(columns, "columns cannot be defined for the first node");
		}
		else {
			Assert.notEmpty(parentColumns, "parentColumns cannot be empty");
			Assert.notEmpty(columns, "columns cannot be empty");
			Assert.isTrue(columns.size() == parentColumns.size(), "columns' size mismatch");
			Assert.notNull(joinType, "joinType cannot be null");
			
			for (Column column : columns) {
				Assert.isTrue(table.equals(column.getTable()), "columns and table mismatch for column " + column.toString() + ": " + table.toString() + " vs. " + column.getTable().toString());
			}
			for (Column prevColumn : parentColumns) {
				Assert.isTrue(parent.table.equals(prevColumn.getTable()), "columns and table mismatch");
			}
		}
		
//		System.out.println();
//		System.out.println("*********************");
//		System.out.println("Examine linked table " + toString());
//		System.out.println("parent columns: " + parentColumns.toString());
//		System.out.println("columns: " + columns.toString());
		
	}

	public LinkedTable getParent() {
		return parent;
	}

	public List<Column> getParentColumns() {
		return parentColumns;
	}

	public Table getTable() {
		return table;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public boolean similarTo(LinkedTable linkedTable) {
		
		if (parent == null && linkedTable.parent == null) {
			return this.table.equals(linkedTable.table);
		}
		
		if (parent == null || linkedTable.parent == null) {
			return false;
		}
		
		if (!parent.table.equals(linkedTable.parent.table)) {
			return false;
		}
		if (!table.equals(linkedTable.table)) {
			return false;
		}
		if (columns.size() != linkedTable.columns.size()) {
			return false;
		}
		
		if (parentColumns.size() == 1) {
			return parentColumns.get(0).equals(linkedTable.parentColumns.get(0)) &&
					columns.get(0).equals(linkedTable.columns.get(0));
		}
		else {
			Set<Pair<Column, Column>> thisColumnPairs = new HashSet<Pair<Column,Column>>();
			Set<Pair<Column, Column>> theOtherColumnPairs = new HashSet<Pair<Column,Column>>();

			for (int i = 0; i < parentColumns.size(); i++) {
				thisColumnPairs.add(new Pair<Column, Column>(parentColumns.get(i), columns.get(i)));
				theOtherColumnPairs.add(new Pair<Column, Column>(linkedTable.getParentColumns().get(i), linkedTable.getColumns().get(i)));
			}
			
			return (thisColumnPairs.containsAll(theOtherColumnPairs) && theOtherColumnPairs.containsAll(thisColumnPairs));
		}
		
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		LinkedTable linkedTable = (LinkedTable)o;
		
		return joinType == linkedTable.joinType && this.similarTo(linkedTable);
	}
	
	@Override
	public int hashCode() {
		return parent.hashCode() ^ table.hashCode() ^ parentColumns.hashCode() ^ columns.hashCode();
	}
	
	@Override
	public String toString() {
		if (parent == null) {
			return table.toString();
		}
		else {
			return parent.table.toString() + " " + (this.joinType == null ? "broken join" : this.joinType.toString()) + " " + table.toString();
		}
	}
}
