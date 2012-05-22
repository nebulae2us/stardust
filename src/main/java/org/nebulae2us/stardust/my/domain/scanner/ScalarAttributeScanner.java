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
import static org.nebulae2us.stardust.internal.util.BaseAssert.Assert;

import java.lang.reflect.Field;

import javax.persistence.JoinColumn;

import org.nebulae2us.stardust.db.domain.ColumnBuilder;
import org.nebulae2us.stardust.db.domain.LinkedTableBundleBuilder;
import org.nebulae2us.stardust.db.domain.Table;
import org.nebulae2us.stardust.db.domain.TableBuilder;
import org.nebulae2us.stardust.internal.util.NameUtils;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.InheritanceType;
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
	 * @see ScannerUtils#getDefaultTable(Class, Class, InheritanceType, Class)
	 */
	private final TableBuilder<?> defaultTable;
	
	public ScalarAttributeScanner(EntityBuilder<?> owningEntity, TableBuilder<?> defaultTable, Field field, String parentPath) {
		this.owningEntity = owningEntity;
		this.field = field;
		this.parentPath = parentPath;
		this.defaultTable = defaultTable;
	}
	
	public ScalarAttributeBuilder<?> produce() {
		
		ScalarAttributeBuilder<?> attributeBuilder = scalarAttribute()
				.fullName(parentPath.length() > 0 ? parentPath + "." + field.getName() : field.getName())
				.field(field)
				.scalarType(field.getType())
				.owningEntity(this.owningEntity)
				.column(extractColumnInfo(this.field, defaultTable))
				;
		
		return attributeBuilder;
	}
	
	/**
	 * Extract column info from annotation. If table is not defined in the annotation, use defaultTable.
	 * 
	 * DefaultTable depends on what class the field belongs to.
	 * 
	 * @param field
	 * @param defaultTable
	 * @return
	 */
	private static ColumnBuilder<?> extractColumnInfo(Field field, TableBuilder<?> defaultTable) {
		
		ColumnBuilder<?> result = column();
		
		if (field.getAnnotation(javax.persistence.Column.class) != null) {
			javax.persistence.Column column = field.getAnnotation(javax.persistence.Column.class);
			result.name(column.name().trim().toUpperCase())
				.table$begin()
					.name(column.table().trim().toUpperCase())
				.end();
			
		}
		else if (field.getAnnotation(JoinColumn.class) != null) {
			JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
			result.name(joinColumn.name().toUpperCase().trim())
				.table$begin()
					.name(joinColumn.table().trim().toUpperCase())
				.end();
		}
		else {
			result.name(NameUtils.camelCaseToUpperCase(field.getName()));
		}
		
		if (result.getTable() == null || ObjectUtils.isEmpty(result.getTable().getName())) {
//			Assert.notNull(linkedTableBundle.getRoot().getTable(), "table cannot be null.");
//			result.table(linkedTableBundle.getRoot().getTable());
			result.table(defaultTable);
		}
		
		return result;
	}
	
}
