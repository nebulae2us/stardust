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

import static org.nebulae2us.stardust.Builders.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.ImmutableList;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

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
	
	public String getRecommendedDefaultAlias() {
		if (getRoot().getAlias().length() > 0) {
			return getRoot().getAlias();
		}
		
		List<String> aliases = getAliases();
		
		for (char c = 'a'; c  <= 'z'; c++) {
			if (!aliases.contains(c)) {
				return String.valueOf(c);
			}
		}
		
		for (char c = 'a'; c  <= 'z'; c++) {
			for (char c2 = 'a'; c2 <= 'z'; c2++) {
				String newAlias = "" + c + c2;
				if (!aliases.contains(newAlias)) {
					return newAlias;
				}
			}
		}		
		
		return "base";
	}

	public static LinkedEntityBundle newInstance(final Entity entity, final String initialAlias, final List<AliasJoin> aliasJoins) {
		
		Assert.notNull(entity, "entity cannot be null");
		Assert.notNull(initialAlias, "initialAlias cannot be null");
		Assert.notNull(aliasJoins, "aliasJoins cannot be null");
		
		
		LinkedEntityBundleBuilder<?> result = linkedEntityBundle()
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
				leftLinkedEntity = result.getRoot();
			}
			else {
				leftLinkedEntity = linkedEntities.get(firstSegment);
				if (leftLinkedEntity == null) {
					throw new IllegalStateException(firstSegment);
				}
			}
			
			Entity leftEntity = leftLinkedEntity.getEntity().toEntity();
			Attribute _attribute = leftEntity.getAttribute(secondSegment);
			
			if (_attribute == null || !(_attribute instanceof EntityAttribute)) {
				throw new IllegalStateException("Invalid expression " + aliasJoin.getAlias());
			}
			
			EntityAttribute attribute = (EntityAttribute)_attribute;

			if (attribute instanceof EntityAttribute) {
				LinkedEntityBuilder<?> linkedEntity = linkedEntity()
						.parent(leftLinkedEntity)
						.entity$wrap(attribute.getEntity())
						.alias(aliasJoin.getAlias())
						.attribute$wrap((EntityAttribute)attribute)
						.joinType(aliasJoin.getJoinType())
						;
				
				result.linkedEntities(linkedEntity);
				
				linkedEntities.put(aliasJoin.getAlias(), linkedEntity);
				
			}
			else {
				throw new IllegalStateException();
			}
		}
		
		return result.toLinkedEntityBundle();
	}
	
}
