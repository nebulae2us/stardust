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

import java.util.List;

import org.nebulae2us.electron.Mirror;
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
	 * These columns belong to owning entity
	 */
	private final List<Column> leftColumns;
	
	/**
	 * These columns belong to attribute entity
	 */
	private final List<Column> rightColumns;

	/**
	 * These columns belong to junction table in case a junction table is required (such as many-to-many relationship)
	 */
	private final List<Column> junctionLeftColumns;

	/**
	 * These columns belong to junction table in case a junction table is required (such as many-to-many relationship)
	 */
	private final List<Column> junctionRightColumns;

	public EntityAttribute(Mirror mirror) {
		super(mirror);
		mirror.bind(this);
		
		this.entity = mirror.to(Entity.class, "entity");
		this.relationalType = mirror.to(RelationalType.class, "relationalType");
		this.joinType = mirror.to(JoinType.class, "joinType");
		this.leftColumns = mirror.toListOf(Column.class, "leftColumns");
		this.rightColumns = mirror.toListOf(Column.class, "rightColumns");
		this.junctionLeftColumns = mirror.toListOf(Column.class, "junctionLeftColumns");
		this.junctionRightColumns = mirror.toListOf(Column.class, "junctionRightColumns");
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

	public List<Column> getLeftColumns() {
		return leftColumns;
	}

	public List<Column> getRightColumns() {
		return rightColumns;
	}

	public List<Column> getJunctionLeftColumns() {
		return junctionLeftColumns;
	}

	public List<Column> getJunctionRightColumns() {
		return junctionRightColumns;
	}

	@Override
	public String toString() {
		return this.getName() + ": E " + entity.getDeclaringClass().getSimpleName();
	}
	
}
