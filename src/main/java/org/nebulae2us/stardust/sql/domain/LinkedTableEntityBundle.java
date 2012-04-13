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
package org.nebulae2us.stardust.sql.domain;

import java.util.List;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class LinkedTableEntityBundle {

	private final List<LinkedTableEntity> linkedTableEntities;
	
	public LinkedTableEntityBundle(Mirror mirror) {
		mirror.bind(this);
		
		this.linkedTableEntities = mirror.toListOf(LinkedTableEntity.class, "linkedTableEntities");
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notEmpty(this.linkedTableEntities, "linkedTableEntities cannot be empty.");
		Assert.isNull(getRoot().getParent(), "root node cannot have parent");
	}

	public List<LinkedTableEntity> getLinkedTableEntities() {
		return linkedTableEntities;
	}
	
	public LinkedTableEntity getRoot() {
		return this.linkedTableEntities.get(0);
	}
	
	public List<LinkedTableEntity> getNonRoots() {
		return this.linkedTableEntities.subList(1, this.linkedTableEntities.size());
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("select ");
		
		for (LinkedTableEntity linkedTableEntity : this.linkedTableEntities) {
			for (ScalarAttribute scalarAttribute : linkedTableEntity.getScalarAttributes()) {
				result
					.append(linkedTableEntity.getTableAlias())
					.append('.')
					.append(scalarAttribute.getColumn().getName())
					.append(' ')
					.append(linkedTableEntity.getAlias().length() == 0 ? "" : linkedTableEntity.getAlias() + '_')
					.append(scalarAttribute.getColumn().getName())
					.append(",\n       ");
			}
		}
		
		result.delete(result.length() - 9, result.length());
		
		result.append("\n  from ")
			.append(getRoot().getTable())
			.append(' ')
			.append(getRoot().getTableAlias());
		
		for (LinkedTableEntity linkedTableEntity : getNonRoots()) {
			result
				.append("\n           ")
				.append(linkedTableEntity.getJoinType() == JoinType.INNER_JOIN ? "inner join " : "left outer join ")
				.append(linkedTableEntity.getTable()).append(' ')
				.append(linkedTableEntity.getTableAlias()).append("\n               on (")
			;
			
			for (int i = 0; i < linkedTableEntity.getColumns().size(); i++) {
				Column parentColumn = linkedTableEntity.getParentColumns().get(i);
				Column column = linkedTableEntity.getColumns().get(i);
				
				result.append(linkedTableEntity.getParent().getTableAlias())
					.append('.').append(parentColumn.getName())
					.append(" = ")
					.append(linkedTableEntity.getTableAlias())
					.append('.')
					.append(column.getName())
					.append("\n               and ");
				
			}

			result.delete(result.length() - 20, result.length());
			result.append(")");
		}
		
		return result.toString();
	}
}
