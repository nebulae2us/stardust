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
import org.nebulae2us.electron.util.ImmutableList;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.Table;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

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
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notNull(this.declaringClass, "declaringClass cannot be null");
		Assert.notNull(this.attributes, "attributes cannot be null");
	}

	public Class<?> getDeclaringClass() {
		return declaringClass;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public Attribute getAttribute(String attributeName) {
		for (Attribute attribute : this.attributes) {
			if (attribute.getName().equals(attributeName)) {
				return attribute;
			}
		}
		return null;
	}
	
	public List<ScalarAttribute> getScalarAttributes() {
		List<ScalarAttribute> result = new ArrayList<ScalarAttribute>();
		
		for (Attribute attribute : this.attributes) {
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

	/**
	 * 
	 * return scalarAttributes or entityAttributes that are related to columns for a particular table.
	 * This makes one important assumption, that is all the columns for the entityAttribute must be in the same table.
	 * This is certainly a case for a well-designed database design because foreign keys must be in the same table.
	 * 
	 * @param table
	 * @return
	 */
	public ImmutableList<Attribute> getOwningSideAttributes(Table table) {
		List<Attribute> result = new ArrayList<Attribute>();
		
		for (Attribute attribute : this.attributes) {
			if (attribute instanceof ScalarAttribute) {
				ScalarAttribute scalarAttribute = (ScalarAttribute)attribute;
				if (scalarAttribute.getColumn().getTable().equals(table)) {
					result.add(scalarAttribute);
				}
			}
			else if (attribute instanceof ValueObjectAttribute) {
				ValueObjectAttribute valueObjectAttribute = (ValueObjectAttribute)attribute;
				ValueObject valueObject = valueObjectAttribute.getValueObject();
				List<ScalarAttribute> scalarAttributes = valueObject.getScalarAttributes();
				for (ScalarAttribute scalarAttribute : scalarAttributes) {
					if (scalarAttribute.getColumn().getTable().equals(table)) {
						result.add(scalarAttribute);
					}
				}
			}
			else if (attribute instanceof EntityAttribute) {
				EntityAttribute entityAttribute = (EntityAttribute)attribute;
				if (entityAttribute.isOwningSide()) {
					if (entityAttribute.getLeftColumns().get(0).getTable().equals(table)) {
						result.add(entityAttribute);
					}
				}
			}
		}
		
		return new ImmutableList<Attribute>(result);

	}
	
	/**
	 * Return ManyToOne or OneToOne EntityAttribute that is owned by this entity
	 * @param table
	 * @return
	 */
	public ImmutableList<EntityAttribute> getOwningSideEntityAttributes() {
		List<EntityAttribute> result = new ArrayList<EntityAttribute>();
		
		for (Attribute attribute : this.attributes) {
			if (attribute instanceof EntityAttribute) {
				EntityAttribute entityAttribute = (EntityAttribute)attribute;
				if (entityAttribute.isOwningSide()) {
					result.add(entityAttribute);
				}
			}
		}
		
		return new ImmutableList<EntityAttribute>(result);
	}

	/**
	 * Return OneToMany, ManyToMany or OneToOne EntityAttribute that is not owned by this entity
	 * @param table
	 * @return
	 */
	public ImmutableList<EntityAttribute> getNonOwningSideEntityAttributes() {
		List<EntityAttribute> result = new ArrayList<EntityAttribute>();
		
		for (Attribute attribute : this.attributes) {
			if (attribute instanceof EntityAttribute) {
				EntityAttribute entityAttribute = (EntityAttribute)attribute;
				if (!entityAttribute.isOwningSide()) {
					result.add(entityAttribute);
				}
			}
		}
		
		return new ImmutableList<EntityAttribute>(result);
	}
	
	/**
	 * @param table
	 * @return
	 */
	public ImmutableList<EntityAttribute> getEntityAttributes() {
		List<EntityAttribute> result = new ArrayList<EntityAttribute>();
		
		for (Attribute attribute : this.attributes) {
			if (attribute instanceof EntityAttribute) {
				EntityAttribute entityAttribute = (EntityAttribute)attribute;
				result.add(entityAttribute);
			}
		}
		
		return new ImmutableList<EntityAttribute>(result);

	}
	
	/**
	 * 
	 * Given an expression such as age or address.zipcode or color, it should return the attribute which can be either ScalarAttribute, ValueObjectAttribute or EntityAttribute.
	 * Return null if it cannot find a matching attribute.
	 * 
	 * The expression that contains dot (.) mainly is for value object attribute.
	 * 
	 * This method does not go beyond the entity. So this expression rooms.roomType on house should return null even though rooms is a valid entityAttribute; this is because
	 * rooms.roomType goes beyond the house object and goes into room object.
	 * 
	 * The only exception is the identity attribute of the foreign object. So owner.ssn is valid because ssn can be linked back to a column exists in house.
	 * 
	 * @param attributeExpression
	 * @return
	 * @throws NullPointerException if attributeExpression is null
	 */
	public Attribute findAttribute(String attributeExpression) {
		
		String firstSegment = attributeExpression;
		
		int index = attributeExpression.indexOf('.');
		if (index > -1) {
			firstSegment = attributeExpression.substring(0, index);
		}
		
		Attribute attribute = getAttribute(firstSegment);
		
		if (attribute == null) {
			return null;
		}
		
		if (index == -1) {
			return attribute;
		}
		
		String secondSegment = attributeExpression.substring(index + 1);

		if (attribute instanceof ValueObjectAttribute) {
			return ((ValueObjectAttribute)attribute).getValueObject().findAttribute(secondSegment);
		}
		else if (attribute instanceof EntityAttribute) {
			
			EntityAttribute entityAttribute = (EntityAttribute)attribute;
			if (entityAttribute.isOwningSide()) {
				return entityAttribute.getEntity().getEntityIdentifier().findAttribute(secondSegment);
			}
			
		}
		
		return null;
	}
	
	public boolean containsColumn(Column column) {
		for (Attribute attribute : attributes) {
			if (attribute instanceof ScalarAttribute) {
				ScalarAttribute scalarAttribute = (ScalarAttribute)attribute;
				if (scalarAttribute.getColumn().equals(column)) {
					return true;
				}
			}
			else if (attribute instanceof ValueObjectAttribute) {
				ValueObjectAttribute valueObjectValue = (ValueObjectAttribute)attribute;
				if (valueObjectValue.getValueObject().containsColumn(column)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
