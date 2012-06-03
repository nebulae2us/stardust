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
package org.nebulae2us.stardust.def.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class DefinitionRepository {

	private final Map<Class<?>, EntityDefinition> entityDefinitions = new HashMap<Class<?>, EntityDefinition>();
	
	private final Map<ExtensionKey, ExtensionDefinition> extensionDefinitions = new HashMap<ExtensionKey, ExtensionDefinition>();
	
	private final Map<ValueObjectKey, ValueObjectDefinition> valueObjectDefinitions = new HashMap<DefinitionRepository.ValueObjectKey, ValueObjectDefinition>();
	
	public synchronized EntityDefinition getEntityDefinition(Class<?> entityClass) {
		EntityDefinition result = entityDefinitions.get(entityClass);
		if (result == null) {
			result = new AnnotationEntityDefinitionBuilder(entityClass).toEntityDefinition();
			this.entityDefinitions.put(entityClass, result);
		}
		return result;
	}
	
	public synchronized ExtensionDefinition getExtensionDefinition(Class<?> owningEntityClass, Class<?> extClass) {
		Assert.isTrue(extClass.getAnnotation(MappedSuperclass.class) != null, "Invalid extension class: %s", extClass.getName()); 
		
		ExtensionKey key = new ExtensionKey(owningEntityClass, extClass);
		ExtensionDefinition result = extensionDefinitions.get(key);
		if (result == null) {
			result = new AnnotationExtensionDefinitionBuilder(owningEntityClass, extClass).toExtensionDefinition();
			this.extensionDefinitions.put(key, result);
		}
		return result;
	}
	
	public SemiEntityDefinition getBaseEntityDefinition(Class<?> entityClass, Class<?> currentClass) {
		if (entityClass == currentClass) {
			return getEntityDefinition(entityClass);
		}
		Assert.isTrue(currentClass.getAnnotation(MappedSuperclass.class) != null || currentClass.getAnnotation(Entity.class) != null, "Invalid currentClass");
		
		if (currentClass.getAnnotation(Entity.class) != null) {
			return getEntityDefinition(currentClass);
		}
		else {
			return getExtensionDefinition(entityClass, currentClass);
		}
	}
	
	public synchronized ValueObjectDefinition getValueObjectDefinition(Class<?> owningEntityClass, String owningFieldFullName, Class<?> voClass) {
		
		ValueObjectKey key = new ValueObjectKey(owningEntityClass, owningFieldFullName, voClass);
		
		ValueObjectDefinition definition = this.valueObjectDefinitions.get(key);
		if (definition == null) {
			definition = new AnnotationValueObjectDefinitionBuilder(owningEntityClass, owningFieldFullName, voClass).toValueObjectDefinition();
			this.valueObjectDefinitions.put(key, definition);
		}
		
		return definition;
	}
	
	private static DefinitionRepository instance = new DefinitionRepository();
	
	public static DefinitionRepository getInstance() {
		// TODO remove this static dependency.
		return instance;
	}


	private final class ExtensionKey {
		private final Class<?> owningEntityClass;
		private final Class<?> extClass;
		public ExtensionKey(Class<?> owningEntityClass, Class<?> extClass) {
			Assert.isTrue(extClass.isAssignableFrom(owningEntityClass), "%s must extends %s", owningEntityClass.getSimpleName(), extClass.getSimpleName());
			Assert.isTrue(extClass != owningEntityClass, "ownningEntityClass must be different from extClass");
			this.owningEntityClass = owningEntityClass;
			this.extClass = extClass;
		}
		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (o == null || o.getClass() != this.getClass()) {
				return false;
			}
			ExtensionKey ek = (ExtensionKey)o;
			return this.owningEntityClass.equals(ek.owningEntityClass) && this.extClass.equals(ek.extClass);
		}
		@Override
		public int hashCode() {
			return this.owningEntityClass.hashCode() ^ this.extClass.hashCode();
		}
	}
	
	private final class ValueObjectKey {
		private final Class<?> owningEntityClass;
		private final Class<?> voClass;
		private final String owningFieldFullName;
		public ValueObjectKey(Class<?> owningEntityClass, String owningFieldFullName, Class<?> voClass) {
			this.owningEntityClass = owningEntityClass;
			this.owningFieldFullName = owningFieldFullName;
			this.voClass = voClass;
		}
		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (o == null || o.getClass() != this.getClass()) {
				return false;
			}
			ValueObjectKey vok = (ValueObjectKey)o;
			return this.owningEntityClass.equals(vok.owningEntityClass) && this.voClass.equals(vok.voClass) && this.owningFieldFullName.equals(vok.owningFieldFullName);
		}
		@Override
		public int hashCode() {
			return this.owningEntityClass.hashCode() ^ this.voClass.hashCode() ^ this.owningFieldFullName.hashCode();
		}

	}
}
