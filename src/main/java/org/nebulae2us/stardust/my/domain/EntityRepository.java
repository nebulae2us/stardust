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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.nebulae2us.electron.Converter;
import org.nebulae2us.stardust.internal.util.Builders;
import org.nebulae2us.stardust.internal.util.ReflectionUtils;
import org.nebulae2us.stardust.my.domain.scanner.EntityScanner;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class EntityRepository {

	private final ConcurrentMap<Class<?>, Entity> entities = new ConcurrentHashMap<Class<?>, Entity>();
	
	
	
	public EntityRepository() {
		
	}
	
	/**
	 * Trying to guess the entity of this instance without instantiating a new Entity definition
	 * @param entityInstance
	 * @return
	 */
	public Entity guessEntity(Object entityInstance) {
		
		Entity result = null;
		
		for (Entity e : entities.values()) {
			if (e.getDeclaringClass().isInstance(entityInstance)) {
				if (result == null || e.isSupOf(result)) {
					result = e;
				}
			}
		}
		
		return result;
	}
	
	public void scanEntities(Class<?> ... entityClasses) {
		for (Class<?> entityClass : entityClasses) {
			getEntity(entityClass);
		}
	}
	
	public void scanPackage(String packageName) {
		Assert.notNull(packageName, "packageName cannot be null");
		
		List<Class<?>> candidateClasses = ReflectionUtils.scanPackage(packageName);
		
		List<Class<?>> entityClasses = new ArrayList<Class<?>>();
		
		for (Class<?> candidateClass : candidateClasses) {
			if (candidateClass.getAnnotation(javax.persistence.Entity.class) != null) {
				entityClasses.add(candidateClass);
			}
		}
		
		for (Class<?> entityClass : entityClasses) {
			getEntity(entityClass);
		}
		
	}
	
	public List<Entity> getAllEntities() {
		List<Entity> result = new ArrayList<Entity>();
		result.addAll(entities.values());
		return result;
	}
	
	public List<Entity> getSubEntities(Entity parentEntity) {
		List<Entity> result = new ArrayList<Entity>();
		for (Entity e : entities.values()) {
			if (e != parentEntity && parentEntity.isSuperOf(e)) {
				result.add(e);
			}
		}
		return result;
	}
	
	public Entity getEntity(Class<?> entityClass) {
		if (entityClass == null) {
			throw new NullPointerException();
		}
		
		Entity entity = this.entities.get(entityClass);
		if (entity != null) {
			return entity;
		}

		synchronized (this) {
			entity = this.entities.get(entityClass);
			if (entity != null) {
				return entity;
			}
			
			EntityScanner scanner = new EntityScanner(entityClass, this.entities);
			scanner.scan();
			
			Map<Class<?>, EntityBuilder<?>> entityBuilders = scanner.getScannedEntityBuilders();
			List<Entity> entities = 
					new Converter(Builders.DESTINATION_CLASS_RESOLVER, true, Builders.IGNORED_TYPES).convert(entityBuilders.values()).toListOf(Entity.class);
			
			for (Entity e : entities) {
				if (!this.entities.containsKey(e.getDeclaringClass())) {
					this.entities.put(e.getDeclaringClass(), e);
				}
			}
			
			entity = this.entities.get(entityClass);
			AssertState.notNull(entity, "Failed to retrieve Entity for class %s.", entityClass);

			Set<Object> discriminatorValues = new HashSet<Object>();
			if (entity.getEntityDiscriminator() != null) {
				discriminatorValues.add(entity.getRootEntity().getEntityDiscriminator().getValue());
				for (Entity subEntity : getSubEntities(entity.getRootEntity())) {
					AssertState.isTrue(!discriminatorValues.contains(subEntity.getEntityDiscriminator().getValue()), "Duplicate discriminator value detected for %s.", subEntity.getDeclaringClass().getSimpleName());
				}
			}
			
			return entity;

		}



	}
	
	
	public Map<Object, Entity> getDiscriminatorValues(Entity entity) {
		Assert.notNull(entity, "entity cannot be null");
		Map<Object, Entity> result = new HashMap<Object, Entity>();
		
		Entity rootEntity = entity.getRootEntity();
		
		AssertState.notNull(rootEntity.getEntityDiscriminator(), "This entity does not have inheritance defined: %s", entity.getDeclaringClass().getSimpleName());
		
		result.put(rootEntity.getEntityDiscriminator().getValue(), rootEntity);

		List<Entity> subEntities = getSubEntities(rootEntity);
		for (Entity subEntity : subEntities) {
			result.put(subEntity.getEntityDiscriminator().getValue(), subEntity);
		}
		
		return result;
	}
	
}
