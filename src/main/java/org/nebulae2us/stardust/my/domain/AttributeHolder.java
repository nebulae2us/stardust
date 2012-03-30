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

import java.util.ArrayList;
import java.util.List;

import org.nebulae2us.electron.Mirror;

/**
 * @author Trung Phan
 *
 */
public class AttributeHolder {

	protected final Class<?> declaringClass;
	
	protected final List<Attribute> attributes;
	
	public AttributeHolder(Mirror mirror) {
		mirror.bind(this);
		
		this.declaringClass = mirror.to(Class.class, "declaringClass");
		this.attributes = mirror.toListOf(Attribute.class, "attributes");
	}

	public Class<?> getDeclaringClass() {
		return declaringClass;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public Attribute getAttribute(String attributeName) {
		for (Attribute attribute : attributes) {
			if (attribute.getName().equals(attributeName)) {
				return attribute;
			}
		}
		return null;
	}

	public List<ScalarAttribute> getScalarAttributes() {
		List<ScalarAttribute> result = new ArrayList<ScalarAttribute>();
		
		for (Attribute attribute : attributes) {
			if (attribute instanceof ScalarAttribute) {
				result.add((ScalarAttribute)attribute);
			}
			else if (attribute instanceof ValueObjectAttribute) {
				ValueObjectAttribute valueObjectAttribute = (ValueObjectAttribute)attribute;
				ValueObject valueObject = valueObjectAttribute.getValueObject();
				result.addAll(valueObject.getScalarAttributes());
			}
		}
		
		return result;

	}
	
}
