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

import java.lang.reflect.Field;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.EqualityComparator;
import org.nebulae2us.stardust.internal.util.ReflectionUtils;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public abstract class Attribute {
	
	public static final EqualityComparator<Attribute> COMPARATOR_BY_NAME = new EqualityComparator<Attribute>() {
		public int hashCode(Attribute a) {
			return a.hashCode();
		}
		public boolean compare(Attribute a1, Attribute a2) {
			return a1.getFullName().equals(a2.getFullName());
		}
	};

	private final String fullName;
	
	private final Field field;
	
	private final Entity owningEntity;
	
	/**
	 * These are mainly used for ScalarAttribute and EntityAttribute. Not yet applicable for ValueObjectAttribute
	 */
	private final boolean insertable;
	
	private final boolean updatable;
	
	private final boolean nullable;
	
	public Attribute(Mirror mirror) {
		mirror.bind(this);
		
		this.field = mirror.to(Field.class, "field");
		this.owningEntity = mirror.to(Entity.class, "owningEntity");
		this.fullName = mirror.toString("fullName");
		this.insertable = mirror.toBooleanValue("insertable");
		this.updatable = mirror.toBooleanValue("updatable");
		this.nullable = mirror.toBooleanValue("nullable");
		
		this.field.setAccessible(true);
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notNull(this.field, "field cannot be null");
		Assert.notNull(this.owningEntity, "owningEntity cannot be null");
		Assert.notEmpty(this.fullName, "fullName cannot be empty");
	}

	public String getName() {
		return field.getName();
	}
	
	public Field getField() {
		return field;
	}

	public Entity getOwningEntity() {
		return owningEntity;
	}

	public String getFullName() {
		return fullName;
	}
	
	public boolean isInsertable() {
		return insertable;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public boolean isNullable() {
		return nullable;
	}

	/**
	 * Given an entity object, this object will retrieve the value of the attribute. For example, given person object, the attribute basicName.firstName will
	 * retrieve the first name of this person object.
	 * 
	 * @param object
	 * @return
	 */
	public Object extractAttributeValue(Object object) {
		
		Assert.notNull(object, "object cannot be null");
		
		Entity entity = this.owningEntity;
		Assert.isTrue(entity.getDeclaringClass().isInstance(object), "object \"%s\" is not an instanceof %s", object.toString(), entity.getDeclaringClass());
		
		
		String[] segments = this.fullName.split("\\.");
		
		Object result = object;
		AttributeHolder holder = entity;
		
		for (String segment : segments) {
			AssertState.notNull(holder, "null holder");
			
			Attribute attribute = holder.getAttribute(segment);
			Assert.notNull(segment, "segment %s cannot be found.", segment);
			
			if (attribute instanceof ValueObjectAttribute) {
				holder = ((ValueObjectAttribute)attribute).getValueObject();
			}
			else {
				holder = null;
			}
			
			result = ReflectionUtils.getValue(attribute.getField(), result);
			if (result == null) {
				return null;
			}
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		return fullName;
	}
}
