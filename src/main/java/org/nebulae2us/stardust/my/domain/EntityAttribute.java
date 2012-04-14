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
package org.nebulae2us.stardust.my.domain;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.ImmutableList;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.JoinType;

/**
 * 
 * 
 * 
 * 
 * 
 * @author Trung Phan
 *
 */
public class EntityAttribute extends Attribute {
	
	/**
	 * attribute entity
	 */
	private final Entity entity;
	
	private final RelationalType relationalType;
	
	private final JoinType joinType;
	
	/**
	 * true for ManyToOne relationship, may be true for OneToOne relationship
	 */
	private final boolean owningSide;
	
	/**
	 * These columns belong to owning entity
	 */
	private final ImmutableList<Column> leftColumns;
	
	/**
	 * These columns belong to attribute entity
	 */
	private final ImmutableList<Column> rightColumns;

	/**
	 * These columns belong to junction table in case a junction table is required (such as many-to-many relationship)
	 */
	private final ImmutableList<Column> junctionLeftColumns;

	/**
	 * These columns belong to junction table in case a junction table is required (such as many-to-many relationship)
	 */
	private final ImmutableList<Column> junctionRightColumns;

	public EntityAttribute(Mirror mirror) {
		super(mirror);
		mirror.bind(this);
		
		this.entity = mirror.to(Entity.class, "entity");
		this.relationalType = mirror.to(RelationalType.class, "relationalType");
		this.joinType = mirror.to(JoinType.class, "joinType");
		this.owningSide = mirror.toBooleanValue("owningSide");
		this.leftColumns = Immutables.$(mirror.toListOf(Column.class, "leftColumns"));
		this.rightColumns = Immutables.$(mirror.toListOf(Column.class, "rightColumns"));
		this.junctionLeftColumns = Immutables.$(mirror.toListOf(Column.class, "junctionLeftColumns"));
		this.junctionRightColumns = Immutables.$(mirror.toListOf(Column.class, "junctionRightColumns"));
	}

	public Entity getEntity() {
		return entity;
	}
	
	public RelationalType getRelationalType() {
		return relationalType;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public boolean isOwningSide() {
		return owningSide;
	}

	public ImmutableList<Column> getLeftColumns() {
		return leftColumns;
	}

	public ImmutableList<Column> getRightColumns() {
		return rightColumns;
	}

	public ImmutableList<Column> getJunctionLeftColumns() {
		return junctionLeftColumns;
	}

	public ImmutableList<Column> getJunctionRightColumns() {
		return junctionRightColumns;
	}

	@Override
	public String toString() {
		return this.getFullName() + ": E " + entity.getDeclaringClass().getSimpleName();
	}
}
