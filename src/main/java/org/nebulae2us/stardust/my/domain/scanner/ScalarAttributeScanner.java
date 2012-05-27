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

import javax.persistence.JoinColumn;

import org.nebulae2us.stardust.db.domain.TableBuilder;
import org.nebulae2us.stardust.internal.util.NameUtils;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.ScalarAttributeBuilder;

/**
 * @author Trung Phan
 *
 */
public class ScalarAttributeScanner {

	private final EntityBuilder<?> owningEntity;
	
	private final Field field;
	
	private final String parentPath;
	
	/**
	 * DefaultTable is used to determine the table if it's not defined in the annotation of a field.
	 * @see ScannerUtils#getDefaultTable(Class, String)
	 */
	private final TableBuilder<?> defaultTable;
	
	public ScalarAttributeScanner(EntityBuilder<?> owningEntity, Field field, String parentPath) {
		this.owningEntity = owningEntity;
		this.field = field;
		this.parentPath = parentPath;
		
		String firstAttributeName = ObjectUtils.isEmpty(parentPath) ? field.getName() : parentPath.split("\\.")[0];
		
		this.defaultTable = ScannerUtils.getDefaultTable(owningEntity.getDeclaringClass(), firstAttributeName);
	}
	
	public ScalarAttributeBuilder<?> produce() {
		
		ColumnInfo columnInfo = extractColumnInfo2(field);
		
		TableBuilder<?> table = ObjectUtils.isEmpty(columnInfo.tableName) ? defaultTable :
			table().name(columnInfo.tableName);
		
		ScalarAttributeBuilder<?> attributeBuilder = scalarAttribute()
				.fullName(parentPath.length() > 0 ? parentPath + "." + field.getName() : field.getName())
				.field(field)
				.scalarType(field.getType())
				.owningEntity(this.owningEntity)
				.column$begin()
					.name(columnInfo.columnName)
					.table(table)
				.end()
				.insertable(columnInfo.insertable)
				.updatable(columnInfo.updatable)
				.nullable(columnInfo.nullable)
				;
		
		return attributeBuilder;
	}
	
	private static class ColumnInfo {
		String columnName;
		String tableName = "";
		boolean insertable = true;
		boolean updatable = true;
		boolean nullable = true;
	}
	
	private static ColumnInfo extractColumnInfo2(Field field) {
		
		ColumnInfo result = new ColumnInfo();
		
		if (field.getAnnotation(javax.persistence.Column.class) != null) {
			javax.persistence.Column column = field.getAnnotation(javax.persistence.Column.class);
			result.columnName = column.name().trim().toUpperCase();
			result.tableName = column.table().trim().toUpperCase();
			result.insertable = column.insertable();
			result.updatable = column.updatable();
			result.nullable = column.nullable();
		}
		else if (field.getAnnotation(JoinColumn.class) != null) {
			JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
			result.columnName = joinColumn.name().trim().toUpperCase();
			result.tableName = joinColumn.table().trim().toUpperCase();
			result.insertable = joinColumn.insertable();
			result.updatable = joinColumn.updatable();
			result.nullable = joinColumn.nullable();
		}
		
		if (ObjectUtils.isEmpty(result.columnName)) {
			result.columnName = NameUtils.camelCaseToUpperCase(field.getName());
		}
		
		return result;
	}
	
}
