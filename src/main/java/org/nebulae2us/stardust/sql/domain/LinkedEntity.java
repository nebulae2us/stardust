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

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class LinkedEntity {

	private final LinkedEntity parent;
	
	private final Entity entity;
	
	private final String alias;

	/**
	 * this is attribute of the parent entity that is used to be linked to this entity.
	 * Specifically attribute.owningEntity == parent.entity and attribute.entity == entity
	 * 
	 */
	private final EntityAttribute attribute;
	
	private final JoinType joinType;
	
	public LinkedEntity(Mirror mirror) {
		mirror.bind(this);

		this.parent = mirror.to(LinkedEntity.class, "parent");
		this.entity = mirror.to(Entity.class, "entity");
		this.attribute = mirror.to(EntityAttribute.class, "attribute");
		this.alias = mirror.toString("alias");
		this.joinType = mirror.to(JoinType.class, "joinType");
		
		assertInvariant();
	}
	
	private void assertInvariant() {
		Assert.notNull(this.entity, "entity cannot be null");
		Assert.notNull(this.alias, "alias cannot be null");

		if (parent == null) {
			Assert.isNull(this.joinType, "joinType cannot be set for the root node");
			Assert.isNull(this.attribute, "attribute cannot be set for root node");
		}
		else {
			Assert.notNull(this.joinType, "joinType cannot be null");
			Assert.notNull(this.attribute, "attribute cannot be null");
			
			Assert.isTrue(this.attribute.getEntity() == this.entity, "invalid");
			Assert.isTrue(this.attribute.getOwningEntity() == this.parent.entity, "invalid");
		}
		
	}
	
	public LinkedEntity getParent() {
		return parent;
	}

	public Entity getEntity() {
		return entity;
	}

	public String getAlias() {
		return alias;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public EntityAttribute getAttribute() {
		return attribute;
	}
	
	public Entity getLeftEntity() {
		return attribute.getOwningEntity();
	}
	
	public Entity getRightEntity() {
		return attribute.getEntity();
	}
	
	public boolean isRoot() {
		return this.parent == null;
	}
	
}
