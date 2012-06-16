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


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.ImmutableList;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.EntityRepository;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;
import static org.nebulae2us.stardust.internal.util.Builders.*;

/**
 * @author Trung Phan
 *
 */
public class LinkedEntityBundle {

	private final List<LinkedEntity> linkedEntities;

	public LinkedEntityBundle(Mirror mirror) {
		mirror.bind(this);
		
		this.linkedEntities = mirror.toListOf(LinkedEntity.class, "linkedEntities");
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notEmpty(this.linkedEntities, "linkedEntities cannot be empty");
		Assert.isNull(getRoot().getParent(), "root node cannot have parent");
		
		for (LinkedEntity linkedEntity : getNonRoots()) {
			Assert.notEmpty(linkedEntity.getAlias(), "non root node's alias cannot be empty.");
		}
		
		Assert.isTrue(getAliases().isUnique(), "Duplicated alias is found");
	}

	public List<LinkedEntity> getLinkedEntities() {
		return linkedEntities;
	}
	
	public LinkedEntity getRoot() {
		return this.linkedEntities.get(0);
	}
	
	public List<LinkedEntity> getNonRoots() {
		return this.linkedEntities.subList(1, this.linkedEntities.size());
	}
	
	/**
	 * 
	 * AttributeExpression is the attribute locator. Examples are: houseId.houseLetter, or color.
	 * 
	 * @param attributeExpression
	 * @return
	 */
	public Attribute findAttribute(final String alias, final String attributeExpression) {
		final LinkedEntity linkedEntity = getLinkedEntity(alias);
		return linkedEntity == null ? null : linkedEntity.getEntity().findAttribute(attributeExpression);
	}
	
	public LinkedEntity getLinkedEntity(String alias) {
		for (LinkedEntity linkedEntity : linkedEntities) {
			if (linkedEntity.getAlias().equals(alias)) {
				return linkedEntity;
			}
		}
		return null;
	}
	
	public ImmutableList<String> getAliases() {
		ListBuilder<String> result = new ListBuilder<String>();
		
		for (LinkedEntity linkedEntity : this.linkedEntities) {
			result.add(linkedEntity.getAlias());
		}
		
		return result.toList();
	}
	
	public static LinkedEntityBundle newInstance(final Entity entity, final String initialAlias, final List<AliasJoin> aliasJoins) {
		
		Assert.notNull(entity, "entity cannot be null");
		Assert.notNull(initialAlias, "initialAlias cannot be null");
		Assert.notNull(aliasJoins, "aliasJoins cannot be null");
		
		LinkedEntityBundleBuilder<?> resultBuilder = linkedEntityBundle()
			.linkedEntities$addLinkedEntity()
				.entity$wrap(entity)
				.alias(initialAlias)
			.end()
			;
		
		Map<String, LinkedEntityBuilder<?>> linkedEntities = new HashMap<String, LinkedEntityBuilder<?>>();
		
		for (AliasJoin aliasJoin : aliasJoins) {
			String firstSegment = aliasJoin.getFirstSegment();
			String secondSegment = aliasJoin.getSecondSegment();
			
			LinkedEntityBuilder<?> leftLinkedEntity = null;
			
			if (firstSegment.equals(initialAlias)) {
				leftLinkedEntity = resultBuilder.getRoot();
			}
			else {
				leftLinkedEntity = linkedEntities.get(firstSegment);
				
				AssertSyntax.notNull(leftLinkedEntity,
						"Alias \"%s\" for \"%s\" is invalid.", aliasJoin.getAlias(), aliasJoin.getName());
			}
			
			Entity leftEntity = leftLinkedEntity.getEntity().toEntity();
			Attribute _attribute = leftEntity.getAttribute(secondSegment);
			
			AssertSyntax.isTrue(_attribute instanceof EntityAttribute,
					"Alias \"%s\" for \"%s\" is invalid. \"%s\" is not a valid field or property for %s.", aliasJoin.getAlias(), aliasJoin.getName(), secondSegment, leftEntity.getDeclaringClass().getSimpleName());
			
			EntityAttribute attribute = (EntityAttribute)_attribute;
			
			JoinType joinType = aliasJoin.getJoinType();
			if (joinType == JoinType.DEFAULT_JOIN) {
				//TODO needs to parse nullable attribute to determine if join should be left or inner join.
				throw new UnsupportedOperationException();
			}

			LinkedEntityBuilder<?> linkedEntity = linkedEntity()
					.parent(leftLinkedEntity)
					.entity$wrap(attribute.getEntity())
					.alias(aliasJoin.getAlias())
					.attribute$wrap(attribute)
					.joinType(joinType)
					;
			
			resultBuilder.linkedEntities(linkedEntity);
			
			linkedEntities.put(aliasJoin.getAlias(), linkedEntity);
		}
		
		LinkedEntityBundle result = resultBuilder.toLinkedEntityBundle();
		
		return result;
		
	}

	public List<?> readData(EntityRepository entityRepository, DataReader dataReader) {
		EntityMappingRepository repository = new EntityMappingRepository(entityRepository);
		return repository.readData(this, dataReader);
	}

	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		
		return result.toString();
	}
	
}
