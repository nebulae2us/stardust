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
@Deprecated
public class TableJoin {

	private final Table leftTable;
	
	private final List<Column> leftColumns;
	
	private final Table rightTable;
	
	private final List<Column> rightColumns;
	
	private final JoinType joinType;
	
	public TableJoin(Mirror mirror) {
		mirror.bind(this);
		
		this.leftTable = mirror.to(Table.class, "leftTable");
		this.leftColumns = mirror.toListOf(Column.class, "leftColumns");
		this.rightTable = mirror.to(Table.class, "rightTable");
		this.rightColumns = mirror.toListOf(Column.class, "rightColumns");
		this.joinType = mirror.to(JoinType.class, "joinType");
		
		assertInvariant();
	}
	
	private void assertInvariant() {
		Assert.notEmpty(this.leftColumns, "in relationship between %s and %s, leftColumns cannot be empty", this.leftTable.getExtName(), this.rightTable.getExtName());
		Assert.notEmpty(this.rightColumns, "rightColumns cannot be empty");
		Assert.isTrue(this.leftColumns.size() == this.rightColumns.size(), "joining columns do not have the same size.");
		
		Set<Pair<Column, Column>> columnPairs = new HashSet<Pair<Column,Column>>();
		for (int i = 0; i < this.leftColumns.size(); i++) {
			Pair<Column, Column> pair = new Pair<Column, Column>(this.leftColumns.get(i), this.rightColumns.get(i));
			Assert.isFalse(columnPairs.contains(pair), "joining columns are duplicate");
			columnPairs.add(pair);
		}
		
	}

	public Table getLeftTable() {
		return leftTable;
	}

	public List<Column> getLeftColumns() {
		return leftColumns;
	}

	public Table getRightTable() {
		return rightTable;
	}

	public List<Column> getRightColumns() {
		return rightColumns;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public boolean similarTo(TableJoin tableJoin) {
		if (!leftTable.equals(tableJoin.leftTable)) {
			return false;
		}
		if (!rightTable.equals(tableJoin.rightTable)) {
			return false;
		}
		if (rightColumns.size() != tableJoin.rightColumns.size()) {
			return false;
		}
		
		if (leftColumns.size() == 1) {
			return leftColumns.get(0).equals(tableJoin.leftColumns.get(0)) &&
					rightColumns.get(0).equals(tableJoin.rightColumns.get(0));
		}
		else {
			Set<Pair<Column, Column>> thisColumnPairs = new HashSet<Pair<Column,Column>>();
			Set<Pair<Column, Column>> theOtherColumnPairs = new HashSet<Pair<Column,Column>>();

			for (int i = 0; i < leftColumns.size(); i++) {
				thisColumnPairs.add(new Pair<Column, Column>(leftColumns.get(i), rightColumns.get(i)));
				theOtherColumnPairs.add(new Pair<Column, Column>(tableJoin.getLeftColumns().get(i), tableJoin.getRightColumns().get(i)));
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
		TableJoin tableJoin = (TableJoin)o;
		
		return joinType == tableJoin.joinType && this.similarTo(tableJoin);
	}
	
	@Override
	public int hashCode() {
		return leftTable.hashCode() ^ rightTable.hashCode() ^ leftColumns.hashCode() ^ rightColumns.hashCode();
	}
}
