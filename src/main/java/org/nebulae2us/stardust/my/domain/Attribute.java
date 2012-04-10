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

/**
 * @author Trung Phan
 *
 */
public abstract class Attribute {

//	private final boolean collection;
//	
//	private final Class<?> collectionType;
	
	private final Field field;
	
	private final Entity owningEntity;
	
	public Attribute(Mirror mirror) {
		mirror.bind(this);
		
//		this.collection = mirror.toBooleanValue("collection");
//		this.collectionType = mirror.to(Class.class, "collectionType");
		this.field = mirror.to(Field.class, "field");
		this.owningEntity = mirror.to(Entity.class, "owningEntity");
	}

//	public boolean isCollection() {
//		return collection;
//	}
//
//	public Class<?> getCollectionType() {
//		return collectionType;
//	}

	public String getName() {
		return field.getName();
	}
	
	public Field getField() {
		return field;
	}

	public Entity getOwningEntity() {
		return owningEntity;
	}
	
}