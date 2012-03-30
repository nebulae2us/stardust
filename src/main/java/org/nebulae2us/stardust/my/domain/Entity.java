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

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.db.domain.JoinedTables;

/**
 * @author Trung Phan
 *
 */
public class Entity extends AttributeHolder {

	
	private final JoinedTables joinedTables;

	private final EntityIdentifier entityIdentifier;

	private final Entity rootEntity;
	
	private final InheritanceType inheritanceType;
	
	private final ScalarAttribute discriminator;
	
	private final String discriminatorValue;
	
	public Entity(Mirror mirror) {
		super(mirror);
		mirror.bind(this);

		this.joinedTables = mirror.to(JoinedTables.class, "joinedTables");
		this.entityIdentifier = mirror.to(EntityIdentifier.class, "entityIdentifier");
		this.rootEntity = mirror.to(Entity.class, "rootEntity");
		this.inheritanceType = mirror.to(InheritanceType.class, "inheritanceType");
		this.discriminator = mirror.to(ScalarAttribute.class, "discriminator");
		this.discriminatorValue = mirror.toString("discriminatorValue");
		
	}

	public EntityIdentifier getEntityIdentifier() {
		return entityIdentifier;
	}

	public Entity getRootEntity() {
		return rootEntity;
	}

	public ScalarAttribute getDiscriminator() {
		return discriminator;
	}

	public String getDiscriminatorValue() {
		return discriminatorValue;
	}

	public JoinedTables getJoinedTables() {
		return joinedTables;
	}

	public InheritanceType getInheritanceType() {
		return inheritanceType;
	}
	
	@Override
    public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Entity: ")
			.append(declaringClass.getSimpleName());
		
		if (rootEntity != null) {
			result.append(" root ").append(rootEntity.getDeclaringClass().getSimpleName());
		}
		
		return result.toString();
    }
	
	public boolean isSuperOf(Entity entity) {
		return declaringClass.isAssignableFrom(entity.getDeclaringClass());
	}
	
	public boolean isSupOf(Entity entity) {
		return entity.getDeclaringClass().isAssignableFrom(declaringClass);
	}

	
}
