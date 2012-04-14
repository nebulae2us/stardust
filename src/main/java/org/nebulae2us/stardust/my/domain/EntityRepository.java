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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.nebulae2us.electron.Converter;
import org.nebulae2us.stardust.Builders;
import org.nebulae2us.stardust.my.domain.scanner.EntityScanner;


/**
 * @author Trung Phan
 *
 */
public class EntityRepository {

	public final ConcurrentMap<Class<?>, Entity> entities = new ConcurrentHashMap<Class<?>, Entity>();
	
	public EntityRepository() {
		
	}
	
	public List<Entity> getEntitiesAndSub(Class<?> entityClass) {
		
		List<Entity> result = new ArrayList<Entity>();
		
		Entity entity = getEntity(entityClass);
		result.add(entity);
		
		for (Entity e : entities.values()) {
			if (entity != e && entity.isSuperOf(e)) {
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
					new Converter(Builders.DESTINATION_CLASS_RESOLVER, true).convert(entityBuilders.values()).toListOf(Entity.class);
			
			for (Entity e : entities) {
				if (!this.entities.containsKey(e.getDeclaringClass())) {
					this.entities.put(e.getDeclaringClass(), e);
				}
			}
			
			entity = this.entities.get(entityClass);
			if (entity == null) {
				throw new IllegalStateException();
			}
			
			return entity;

		}



	}
	
	
	
	
	
}
