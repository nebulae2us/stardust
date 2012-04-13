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
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.ImmutableList;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class LinkedTableBundle {

	private final List<LinkedTable> linkedTables;
	
	public LinkedTableBundle(Mirror mirror) {
		mirror.bind(this);
		
		this.linkedTables = mirror.toListOf(LinkedTable.class, "linkedTables");
	
		assertInvariant();
	}
	
	private LinkedTableBundle(List<LinkedTable> linkedTables) {
		this.linkedTables = new ImmutableList<LinkedTable>(linkedTables);
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notEmpty(this.linkedTables, "linkedTables cannot be empty");
		
		IdentityHashMap<LinkedTable, Boolean> visitedLinkedTables = new IdentityHashMap<LinkedTable, Boolean>();

		LinkedTable root = this.linkedTables.get(0);
		Assert.isNull(root.getParent(), "parent must be null for root");
		
		visitedLinkedTables.put(root, Boolean.TRUE);
		
		for (int i = 1; i < this.linkedTables.size(); i++) {
			LinkedTable linkedTable = this.linkedTables.get(0);
			
			Assert.isTrue(visitedLinkedTables.containsKey(linkedTable), "There is an invalid linkedTable");
		}
	}
	
	public List<LinkedTable> getLinkedTables() {
		return linkedTables;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		LinkedTableBundle linkedTableBundle = (LinkedTableBundle)o;
		
		return this.linkedTables.equals(linkedTableBundle.linkedTables);
	}
	
	@Override
	public int hashCode() {
		return linkedTables.hashCode();
	}
	
	
	private int findSimilarJoin(LinkedTable linkedTable) {
		
		for (int i = 1; i < linkedTables.size(); i++) {			
			if (linkedTables.get(i).similarTo(linkedTable)) {
				return i;
			}
		}
		return -1;
	}
	
	public LinkedTableBundle join(LinkedTableBundle linkedTableBundle) {
		Assert.notNull(linkedTableBundle, "linkedTableBundle cannot be null");
		
		if (linkedTableBundle.linkedTables.size() <= 1) {
			return this;
		}

		List<LinkedTable> newLinkedTables = new ArrayList<LinkedTable>(this.linkedTables);
		
		for (LinkedTable linkedTable : linkedTableBundle.getNonRoots()) {
			
			int idx = this.findSimilarJoin(linkedTable);
			if (idx < 0) {
				newLinkedTables.add(linkedTable);
			}
			else {
				// TODO select the stronger join (i.e. INNER JOIN is stronger than LEFT JOIN)
			}
			
			
		}
		
		return new LinkedTableBundle(newLinkedTables);
	}
	
	public LinkedTable getRoot() {
		return this.linkedTables.get(0);
	}
	
	public List<LinkedTable> getNonRoots() {
		return this.linkedTables.subList(1, this.linkedTables.size());
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(this.linkedTables.get(0).getTable().getName());
		
		for (int j = 1; j < this.linkedTables.size(); j++) {
			LinkedTable linkedTable = this.linkedTables.get(j);

			result.append("\n    ").append(linkedTable.getJoinType() == JoinType.INNER_JOIN ? "inner join " : "left outer join ")
			.append(linkedTable.getTable().getExtName()).append("\n        on (");
			
			for (int i = 0; i < linkedTable.getParentColumns().size(); i++) {
				Column parentColumn = linkedTable.getParentColumns().get(i);
				Column column = linkedTable.getColumns().get(i);

				if (i > 0) {
					result.append("\n        and ");
				}
				
				result.append(linkedTable.getParent().getTable().getName())
					.append(".")
					.append(parentColumn.getName())
					.append(" = ")
					.append(linkedTable.getTable().getName())
					.append(".")
					.append(column.getName());
			}
			
			result.append(")");
		}
		
		return result.toString();
		
	}
	
}
