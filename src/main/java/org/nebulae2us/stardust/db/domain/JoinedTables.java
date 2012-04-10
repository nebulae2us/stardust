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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.ImmutableList;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
@Deprecated
public class JoinedTables {

	private final Table table;
	
	private final List<TableJoin> tableJoins;
	
	public JoinedTables(Mirror mirror) {
		mirror.bind(this);
		
		this.table = mirror.to(Table.class, "table");
		this.tableJoins = mirror.toListOf(TableJoin.class, "tableJoins");
	
		assertInvariant();
	}
	
	private JoinedTables(Table table, List<TableJoin> tableJoins) {
		this.table = table;
		this.tableJoins = new ImmutableList<TableJoin>(tableJoins);
		
		assertInvariant();
	}

	private void assertInvariant() {
		Set<Table> tables = new HashSet<Table>();
		tables.add(table);
		
		for (TableJoin tableJoin : tableJoins) {
			Assert.isTrue(tables.contains(tableJoin.getLeftTable()), "Invalid table join");
			tables.add(tableJoin.getRightTable());
		}
		
	}
	
	public Table getTable() {
		return table;
	}

	public List<TableJoin> getTableJoins() {
		return tableJoins;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		JoinedTables joinedTables = (JoinedTables)o;
		
		return this.table.equals(joinedTables.table) && this.tableJoins.equals(joinedTables.tableJoins);
	}
	
	@Override
	public int hashCode() {
		return table.hashCode() ^ tableJoins.hashCode();
	}
	
	
	private int findSimilarJoin(TableJoin tableJoin) {
		
		for (int i = 0; i < tableJoins.size(); i++) {			
			if (tableJoins.get(i).similarTo(tableJoin)) {
				return i;
			}
		}
		return -1;
	}
	
	public JoinedTables join(TableJoin tableJoin) {
		return join(Collections.singletonList(tableJoin));
	}
	
	public JoinedTables join(List<TableJoin> tableJoins) {
		Assert.notNull(tableJoins, "tableJoins cannot be null");
		
		if (tableJoins.size() == 0) {
			return this;
		}

		List<TableJoin> newTableJoins = new ArrayList<TableJoin>(this.tableJoins);
		
		for (TableJoin tableJoin : tableJoins) {
			int idx = this.findSimilarJoin(tableJoin);
			if (idx < 0) {
				newTableJoins.add(tableJoin);
			}
			else {
				// TODO select the stronger join (i.e. INNER JOIN is stronger than LEFT JOIN)
			}
			
			
		}
		
		return new JoinedTables(this.table, newTableJoins);
	}
	
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(table.getExtName());
		
		for (TableJoin tableJoin : tableJoins) {
			result.append("\n    ").append(tableJoin.getJoinType() == JoinType.INNER_JOIN ? "inner join " : "left outer join ")
			.append(tableJoin.getRightTable().getExtName()).append("\n        on (");
			
			for (int i = 0; i < tableJoin.getLeftColumns().size(); i++) {
				Column leftColumn = tableJoin.getLeftColumns().get(i);
				Column rightColumn = tableJoin.getRightColumns().get(i);

				if (i > 0) {
					result.append("\n        and ");
				}
				
				result.append(tableJoin.getLeftTable().getName())
					.append(".")
					.append(leftColumn.getName())
					.append(" = ")
					.append(tableJoin.getRightTable().getName())
					.append(".")
					.append(rightColumn.getName());
			}
			
			result.append(")");
		}
		
		return result.toString();
		
	}
	
}
