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

import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.my.domain.InheritanceType;

/**
 * @author Trung Phan
 *
 */
public class EntityDefinition extends SemiEntityDefinition {

	private final Class<?> entityClass;
	
	private final String tableName;
	
	private final Map<String, List<String>> secondaryTables;
	
	private final InheritanceType inheritanceType;
	
	private final String discriminatorColumn;
	
	private final Object discriminatorValue;
	
	
	public EntityDefinition(Mirror mirror) {
		super(mirror);
		
		mirror.bind(this);
		
		this.entityClass = mirror.to(Class.class, "entityClass");
		this.tableName = mirror.toString("tableName");
		this.secondaryTables = mirror.toMultiValueMapOf(String.class, String.class, "secondaryTables");
		this.inheritanceType = mirror.to(InheritanceType.class, "inheritanceType");
		this.discriminatorColumn = mirror.toString("discriminatorColumn");
		this.discriminatorValue = mirror.toObject("discriminatorValue");
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public String getTableName() {
		return tableName;
	}

	public Map<String, List<String>> getSecondaryTables() {
		return secondaryTables;
	}

	public InheritanceType getInheritanceType() {
		return inheritanceType;
	}

	public String getDiscriminatorColumn() {
		return discriminatorColumn;
	}

	public Object getDiscriminatorValue() {
		return discriminatorValue;
	}
	
	
}
