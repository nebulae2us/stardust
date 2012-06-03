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

import java.util.List;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.LinkedTable;
import org.nebulae2us.stardust.db.domain.LinkedTableBundle;
import org.nebulae2us.stardust.sql.domain.ArrayWrapper;
import org.nebulae2us.stardust.sql.domain.LinkedEntity;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class Entity extends AttributeHolder {

	/**
	 * List of linkedTables that define this entity. In most case, it's one table. But in inheritance (JOIN type), the child entity
	 * contains list of joined tables.
	 * 
	 * Also if secondary tables are defined for this entity, linkedTableBundle includes more than one table for the entity.
	 */
	private final LinkedTableBundle linkedTableBundle;
	
	private final EntityIdentifier entityIdentifier;

	private final Entity rootEntity;
	
	private final InheritanceType inheritanceType;
	
	private final EntityDiscriminator entityDiscriminator;
	
	private final ScalarAttribute version;
	
	public Entity(Mirror mirror) {
		super(mirror);
		mirror.bind(this);

		this.linkedTableBundle = mirror.to(LinkedTableBundle.class, "linkedTableBundle");
		this.entityIdentifier = mirror.to(EntityIdentifier.class, "entityIdentifier");
		this.rootEntity = mirror.to(Entity.class, "rootEntity");
		this.inheritanceType = mirror.to(InheritanceType.class, "inheritanceType");
		this.entityDiscriminator = mirror.to(EntityDiscriminator.class, "entityDiscriminator");
		this.version = mirror.to(ScalarAttribute.class, "version");
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notNull(this.rootEntity, "rootEntity cannot be null");
		Assert.notNull(inheritanceType, "inheritanceType cannot be null");
		Assert.notNull(this.linkedTableBundle, "linkedTableBundle cannot be null");
		
		if (rootEntity.getEntityDiscriminator() != null) {
			AssertState.notNull(this.entityDiscriminator, "Sub entity %s must also have discriminator value defined.", this.declaringClass.getSimpleName());
			Assert.isTrue(this.entityDiscriminator.getColumn().equals(rootEntity.entityDiscriminator.getColumn()), "discriminator column mismatch between %s and %s", this.declaringClass.getSimpleName(), this.rootEntity.declaringClass.getSimpleName());
		}
		
	}

	public EntityIdentifier getEntityIdentifier() {
		return entityIdentifier;
	}

	public Entity getRootEntity() {
		return rootEntity;
	}

	public EntityDiscriminator getEntityDiscriminator() {
		return entityDiscriminator;
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

	public LinkedTableBundle getLinkedTableBundle() {
		return linkedTableBundle;
	}

	public ScalarAttribute getVersion() {
		return version;
	}
	
	public List<ScalarAttribute> getIdentifierScalarAttributes() {
		return this.entityIdentifier.getScalarAttributes();
	}

	@Override
	public boolean containsColumn(Column column) {
		if (super.containsColumn(column)) {
			return true;
		}
		if (entityDiscriminator != null && entityDiscriminator.getColumn().equals(column)) {
			return true;
		}
		for (LinkedTable linkedTable : linkedTableBundle.getLinkedTables()) {
			if (linkedTable.getColumns().contains(column)) {
				return true;
			}
		}
		return true;
	}
	
	public ArrayWrapper extractIdentifierValue(Object entityInstance) {
		List<ScalarAttribute> identifierScalarAttributes = entityIdentifier.getScalarAttributes();
		Object[] ids = new Object[identifierScalarAttributes.size()];
		
		for (int i = 0; i < identifierScalarAttributes.size(); i++) {
			ScalarAttribute identifierScalarAttribute = identifierScalarAttributes.get(i);
			ids[i] = identifierScalarAttribute.extractAttributeValue(entityInstance);
		}
		
		
		return new ArrayWrapper(ids);
	}
}
