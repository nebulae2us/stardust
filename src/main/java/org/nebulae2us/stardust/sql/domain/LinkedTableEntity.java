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
import org.nebulae2us.stardust.db.domain.Table;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class LinkedTableEntity {

	private final LinkedTableEntity parent;
	
	private final Table table;
	
	private final Entity entity;
	
	private final String alias;
	
	private final String tableAlias; // in most case, alias == tableAlias, only when there is joined inheritance or secondary table, then they are different
	
	private final List<Attribute> owningSideAttributes;
	
	private final List<Column> parentColumns;
	
	private final List<Column> columns;
	
	private final JoinType joinType;
	
	public LinkedTableEntity(Mirror mirror) {
		mirror.bind(this);
		
		this.parent = mirror.to(LinkedTableEntity.class, "parent");
		this.table = mirror.to(Table.class, "table");
		this.entity = mirror.to(Entity.class, "entity");
		this.tableAlias = mirror.toString("tableAlias");
		this.alias = mirror.toString("alias");
		this.owningSideAttributes = mirror.toListOf(Attribute.class, "owningSideAttributes");
		
		this.parentColumns = mirror.toListOf(Column.class, "parentColumns");
		this.columns = mirror.toListOf(Column.class, "columns");
		this.joinType = mirror.to(JoinType.class, "joinType");
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notNull(this.table, "table cannot be null");
		Assert.notNull(this.entity, "entity cannot be null");
		Assert.notNull(this.alias, "alias cannot be null");
		Assert.notEmpty(this.tableAlias, "tableAlias cannot be null");
		
		if (this.parent == null) {
			Assert.empty(this.parentColumns, "parentColumns cannot be set for root node");
			Assert.empty(this.columns, "columns cannot be set for root node");
			Assert.isNull(joinType, "joinType cannot be set for root node");
		}
		else {
			Assert.notEmpty(this.tableAlias, "tableAlias cannot be empty");
			Assert.notEmpty(this.parentColumns, "parentColumns cannot be empty");
			Assert.notEmpty(this.columns, "columns cannot be empty");
			Assert.notNull(this.joinType, "joinType cannot be null");
			
			Assert.isTrue(this.parentColumns.size() == this.columns.size(), "columns size mismatch");

			for (Column column: this.columns) {
				Assert.isTrue(this.table.equals(column.getTable()), "Invaid columns");
			}
			
			for (Column parentColumn: this.parentColumns) {
				Assert.isTrue(this.parent.table.equals(parentColumn.getTable()), "Invalid parent columns");
			}

		}
		
		for (Attribute owningSideAttribute : this.owningSideAttributes) {
			if (owningSideAttribute instanceof ScalarAttribute) {
				Assert.isTrue(this.table.equals(((ScalarAttribute)owningSideAttribute).getColumn().getTable()), "Invalid scalarAttributes.");
			}
			else if (owningSideAttribute instanceof EntityAttribute) {
				EntityAttribute entityAttribute = (EntityAttribute)owningSideAttribute;
				Assert.isTrue(entityAttribute.isOwningSide(), "invalide entity Attribute");
			}
		}
		
	}

	public LinkedTableEntity getParent() {
		return parent;
	}

	public Table getTable() {
		return table;
	}

	public Entity getEntity() {
		return entity;
	}

	public String getAlias() {
		return alias;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public List<Attribute> getOwningSideAttributes() {
		return owningSideAttributes;
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
