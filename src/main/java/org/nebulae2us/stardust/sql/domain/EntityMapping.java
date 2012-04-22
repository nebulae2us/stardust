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
import java.util.Map;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.Entity;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class EntityMapping {

	private final Entity entity;
	
	private final int discriminatorColumnIndex;
	
	private final List<ScalarAttributeMapping> identifierAttributeMappings;
	
	private final List<AttributeMapping> attributeMappings;
	
	public EntityMapping(Mirror mirror) {
		mirror.bind(this);
		
		this.entity = mirror.to(Entity.class, "entity");
		this.attributeMappings = mirror.toListOf(AttributeMapping.class, "attributeMappings");
		this.discriminatorColumnIndex = mirror.toIntValue("discriminatorColumnIndex");
		this.identifierAttributeMappings = mirror.toListOf(ScalarAttributeMapping.class, "identifierAttributeMappings");
		
		assertInvariant();
	}
	
	private void assertInvariant() {
		Assert.notNull(this.entity, "entity cannot be null");
	}

	public Entity getEntity() {
		return entity;
	}

	public List<AttributeMapping> getAttributeMappings() {
		return attributeMappings;
	}

	public int getDiscriminatorColumnIndex() {
		return discriminatorColumnIndex;
	}

	public List<ScalarAttributeMapping> getIdentifierAttributeMappings() {
		return identifierAttributeMappings;
	}
	
	public AttributeMapping getAttributeMapping(String attributeName) {
		Attribute attribute = entity.getAttribute(attributeName);
		if (attribute == null) {
			return null;
		}
		for (AttributeMapping attributeMapping : this.attributeMappings) {
			if (attributeMapping.getAttribute() == attribute) {
				return attributeMapping;
			}
		}
		return null;
	}
	
	public ScalarAttributeMapping getScalarAttributeMapping(String attributeName) {
		AttributeMapping attributeMapping = getAttributeMapping(attributeName);
		return attributeMapping instanceof ScalarAttributeMapping ? (ScalarAttributeMapping)attributeMapping : null;
		
	}
	
	public ValueObjectAttributeMapping getValueObjectAttributeMapping(String attributeName) {
		AttributeMapping attributeMapping = getAttributeMapping(attributeName);
		return attributeMapping instanceof ValueObjectAttributeMapping ? (ValueObjectAttributeMapping)attributeMapping : null;
		
	}

	public EntityAttributeMapping getEntityAttributeMapping(String attributeName) {
		AttributeMapping attributeMapping = getAttributeMapping(attributeName);
		return attributeMapping instanceof EntityAttributeMapping ? (EntityAttributeMapping)attributeMapping : null;
		
	}
	
	

	
	
}
