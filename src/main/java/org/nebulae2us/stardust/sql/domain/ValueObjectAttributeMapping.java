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
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.ValueObjectAttribute;

/**
 * @author Trung Phan
 *
 */
public class ValueObjectAttributeMapping extends AttributeMapping {

	private final List<AttributeMapping> attributeMappings;
	
	public ValueObjectAttributeMapping(Mirror mirror) {
		super(mirror);
		mirror.bind(this);
		
		this.attributeMappings = mirror.toListOf(AttributeMapping.class, "attributeMappings");
	}

	@Override
	public ValueObjectAttribute getAttribute() {
		return (ValueObjectAttribute)super.getAttribute();
	}
	
	public List<AttributeMapping> getAttributeMappings() {
		return attributeMappings;
	}

	public AttributeMapping getAttributeMapping(String attributeName) {
		Attribute attribute = getAttribute().getValueObject().getAttribute(attributeName);
		
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
	
	public ValueObjectAttributeMapping getCompositeAttributeMapping(String attributeName) {
		AttributeMapping attributeMapping = getAttributeMapping(attributeName);
		return attributeMapping instanceof ValueObjectAttributeMapping ? (ValueObjectAttributeMapping)attributeMapping : null;
		
	}
	
	
	
}
