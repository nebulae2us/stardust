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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Converter;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.my.domain.InheritanceType;
import org.nebulae2us.stardust.my.domain.RelationalType;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class EntityDefinitionBuilder extends AttributeRelationshipHolderDefinitionBuilder<EntityDefinitionBuilder> {
	
	private final Class<?> entityClass;
	
	private String tableName;
	
	private final List<String> identifiers = new ArrayList<String>();
	
	/**
	 * Key is secondary table name, List<String> is the list of joins
	 */
	private final Map<String, List<String>> secondaryTables = new HashMap<String, List<String>>();

	private InheritanceType inheritanceType;
	
	private String discriminatorColumn;
	
	private Object discriminatorValue;
	
	public EntityDefinitionBuilder(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	
	public EntityDefinitionBuilder table(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public EntityDefinitionBuilder identifier(Object ... attributeLocators ) {
		for (Object attributeLocator : attributeLocators) {
			String attributeName = attributeLocator.toString();
			if (!this.identifiers.contains(attributeName)) {
				this.identifiers.add(attributeName);
			}
		}
		return this;
	}
	
	public EntityDefinition toEntityDefinition() {
		return new Converter().convert(this).to(EntityDefinition.class);
	}

	public SecondaryTableBuilder joinSecondaryTable(String secondaryTableName) {
		Assert.isTrue(!secondaryTables.containsKey(secondaryTableName), "Duplicated definition of secondary table " + secondaryTableName);
		return new SecondaryTableBuilder(secondaryTableName);
	}

	public EntityDefinitionBuilder inheritanceType(InheritanceType inheritanceType) {
		this.inheritanceType = inheritanceType;
		return this;
	}
	
	public EntityDefinitionBuilder discriminatorColumn(String discriminatorColumn) {
		this.discriminatorColumn = discriminatorColumn;
		return this;
	}
	
	
	public EntityDefinitionBuilder discriminatorValue(Object discriminatorValue) {
		this.discriminatorValue = discriminatorValue;
		return this;
	}
	
	
	public class SecondaryTableBuilder {
		private final String secondaryTableName;
		public SecondaryTableBuilder(String secondaryTableName) {
			this.secondaryTableName = secondaryTableName;
		}
		
		public EntityDefinitionBuilder on(String ... joins) {
			Map<String, List<String>> map = EntityDefinitionBuilder.this.secondaryTables;
			map.put(this.secondaryTableName, Immutables.$(joins));
			return EntityDefinitionBuilder.this;
		}
	}
	
	
	
}
