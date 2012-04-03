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

import static org.nebulae2us.stardust.Builders.entityJoin;
import static org.nebulae2us.stardust.Builders.relationalEntities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class RelationalEntities {

	private final Entity entity;
	
	private final List<EntityJoin> entityJoins;

	public RelationalEntities(Mirror mirror) {
		mirror.bind(this);
		
		this.entity = mirror.to(Entity.class, "entity");
		this.entityJoins = mirror.toListOf(EntityJoin.class, "entityJoins");
	}

	public Entity getEntity() {
		return entity;
	}

	public List<EntityJoin> getEntityJoins() {
		return entityJoins;
	}
	
	public EntityJoin getEntityJoin(String alias) {
		for (EntityJoin entityJoin : entityJoins) {
			if (entityJoin.getAlias().equals(alias)) {
				return entityJoin;
			}
		}
		return null;
	}

	public static RelationalEntities newInstance(final Entity entity, final String initialAlias, final List<AliasJoin> aliasJoins) {
		
		Assert.notNull(entity, "entity cannot be null");
		Assert.notNull(initialAlias, "initialAlias cannot be null");
		Assert.notNull(aliasJoins, "aliasJoins cannot be null");
		
		
		RelationalEntitiesBuilder<?> result = relationalEntities()
			.entity$wrap(entity)
			;
		
		Map<String, EntityJoinBuilder<?>> entityJoins = new HashMap<String, EntityJoinBuilder<?>>();
		
		for (AliasJoin aliasJoin : aliasJoins) {
			String firstSegment = aliasJoin.getFirstSegment();
			String secondSegment = aliasJoin.getSecondSegment();
			
			Entity leftEntity = null;
			
			if (firstSegment.equals(initialAlias)) {
				leftEntity = entity;
			}
			else {
				EntityJoinBuilder<?> entityJoin = entityJoins.get(firstSegment);
				if (entityJoin == null) {
					throw new IllegalStateException(firstSegment);
				}
				
				EntityAttribute joiningAttribute = entityJoin.getAttribute().toAttribute();
				
				leftEntity = joiningAttribute.getEntity();
			}
			
			Attribute attribute = leftEntity.getAttribute(secondSegment);
			if (attribute instanceof EntityAttribute) {
				EntityJoinBuilder<?> entityJoin = entityJoin()
					.alias(aliasJoin.getAlias())
					.attribute$wrap((EntityAttribute)attribute)
					.joinType(aliasJoin.getJoinType())
					;
				
				result.entityJoins(entityJoin);
				
				entityJoins.put(aliasJoin.getAlias(), entityJoin);
				
			}
			else {
				throw new IllegalStateException();
			}
		}
		
		return result.toRelationalEntities();
	}
	
}
