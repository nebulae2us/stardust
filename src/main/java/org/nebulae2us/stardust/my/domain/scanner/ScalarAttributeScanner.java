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
package org.nebulae2us.stardust.my.domain.scanner;

import static org.nebulae2us.stardust.Builders.*;

import java.lang.reflect.Field;

import javax.persistence.Basic;

import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.TableBuilder;
import org.nebulae2us.stardust.def.domain.AttributeHolderDefinition;
import org.nebulae2us.stardust.internal.util.NameUtils;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.FetchType;
import org.nebulae2us.stardust.my.domain.ScalarAttributeBuilder;

/**
 * @author Trung Phan
 *
 */
public class ScalarAttributeScanner {

	private final EntityBuilder<?> owningEntity;
	
	private final AttributeHolderDefinition attributeHolderDefinition;
	
	private final Field field;
	
	private final String parentPath;
	
	/**
	 * DefaultTable is used to determine the table if it's not defined in the annotation of a field.
	 * @see ScannerUtils#getDefaultTable(Class, String)
	 */
	private final TableBuilder<?> defaultTable;
	
	public ScalarAttributeScanner(EntityBuilder<?> owningEntity, Field field, String parentPath, AttributeHolderDefinition attributeHolderDefinition) {
		this.owningEntity = owningEntity;
		this.attributeHolderDefinition = attributeHolderDefinition;
		this.field = field;
		this.parentPath = parentPath;
		
		String firstAttributeName = ObjectUtils.isEmpty(parentPath) ? field.getName() : parentPath.split("\\.")[0];
		
		this.defaultTable = ScannerUtils.getDefaultTable(owningEntity.getDeclaringClass(), firstAttributeName);
	}
	
	public ScalarAttributeBuilder<?> produce() {
		
		String columnName = attributeHolderDefinition.getScalarAttributeColumnNames().get(field.getName());
		if (columnName == null) {
			columnName = NameUtils.camelCaseToUpperCase(field.getName());
		}
		Column column = Column.parse(columnName, defaultTable.toTable());
		
		FetchType fetchType = FetchType.EAGER;
		if (field.getAnnotation(Basic.class) != null) {
			fetchType = field.getAnnotation(Basic.class).fetch() == javax.persistence.FetchType.EAGER ? FetchType.EAGER : FetchType.LAZY;
		}
		
		ScalarAttributeBuilder<?> attributeBuilder = scalarAttribute()
				.fullName(parentPath.length() > 0 ? parentPath + "." + field.getName() : field.getName())
				.field(field)
				.scalarType(field.getType())
				.owningEntity(this.owningEntity)
				.column$wrap(column)
				.insertable( !this.attributeHolderDefinition.getNotInsertableAttributes().contains(field.getName()) )
				.updatable( !this.attributeHolderDefinition.getNotUpdatableAttributes().contains(field.getName()))
				.nullable( !this.attributeHolderDefinition.getNotNullableAttributes().contains(field.getName()))
				.fetchType(fetchType)
				;
		
		return attributeBuilder;
	}
	
}
