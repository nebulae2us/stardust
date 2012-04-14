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
	
	public Attribute(Mirror mirror) {
		mirror.bind(this);
		
		this.field = mirror.to(Field.class, "field");
		this.owningEntity = mirror.to(Entity.class, "owningEntity");
		this.fullName = mirror.toString("fullName");
		
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
	
	@Override
	public String toString() {
		return fullName;
	}
}
