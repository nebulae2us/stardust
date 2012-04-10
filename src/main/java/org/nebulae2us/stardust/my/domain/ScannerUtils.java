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

import static org.nebulae2us.stardust.Builders.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;

import org.nebulae2us.stardust.db.domain.ColumnBuilder;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.db.domain.JoinedTablesBuilder;
import org.nebulae2us.stardust.db.domain.TableJoinBuilder;
import org.nebulae2us.stardust.internal.util.NameUtils;
import org.nebulae2us.stardust.internal.util.ObjectUtils;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class ScannerUtils {
	
	public static ColumnBuilder<?> extractColumnInfo(Field field, JoinedTablesBuilder<?> joinedTables) {
		
		ColumnBuilder<?> result = column();
		
		if (field.getAnnotation(javax.persistence.Column.class) != null) {
			javax.persistence.Column column = field.getAnnotation(javax.persistence.Column.class);
			result.name(column.name())
				.table$begin()
					.name(column.table())
				.end();
			
		}
		else if (field.getAnnotation(JoinColumn.class) != null) {
			JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
			result.name(joinColumn.name())
				.table$begin()
					.name(joinColumn.table())
				.end();
		}
		else {
			result.name(NameUtils.camelCaseToUpperCase(field.getName()));
		}
		
		if (result.getTable() == null || ObjectUtils.isEmpty(result.getTable().getName())) {
			Assert.notNull(joinedTables.getTable(), "table cannot be null.");
			result.table(joinedTables.getTable());
		}
		
		return result;
	}
	
	public static List<EntityAttributeBuilder<?>> extractEntityAttributes(List<AttributeBuilder<?>> attributes) {
		List<EntityAttributeBuilder<?>> result = new ArrayList<EntityAttributeBuilder<?>>();
		
		for (AttributeBuilder<?> attribute : attributes) {
			if (attribute instanceof EntityAttributeBuilder) {
				result.add((EntityAttributeBuilder<?>)attribute);
			}
		}
		
		return result;
	}

	public static List<ScalarAttributeBuilder<?>> extractScalarAttributes(List<AttributeBuilder<?>> attributes) {
		List<ScalarAttributeBuilder<?>> result = new ArrayList<ScalarAttributeBuilder<?>>();
		
		for (AttributeBuilder<?> attribute : attributes) {
			if (attribute instanceof ScalarAttributeBuilder) {
				result.add((ScalarAttributeBuilder<?>)attribute);
			}
			else if (attribute instanceof ValueObjectAttributeBuilder) {
				ValueObjectAttributeBuilder<?> valueObjectAttribute = (ValueObjectAttributeBuilder<?>)attribute;
				ValueObjectBuilder<?> valueObject = valueObjectAttribute.getValueObject();
				result.addAll(extractScalarAttributes(valueObject.getAttributes()));
			}
		}
		
		return result;
	}
	
	
	private static JoinedTablesBuilder<?> _extractTableInfo(Class<?> entityClass) {
		

		JoinedTablesBuilder<?> result = joinedTables();
		
		javax.persistence.Table table = entityClass.getAnnotation(javax.persistence.Table.class);
		
		if (table != null) {
			result.table$begin()
					.name(table.name())
					.schemaName(table.schema())
					.catalogName(table.catalog())
				.end()
				;
		}
		else {
			result.table$begin()
					.name(NameUtils.camelCaseToUpperCase(entityClass.getSimpleName()))
				.end()
				;
		}

		ArrayList<SecondaryTable> secondaryTables = new ArrayList<SecondaryTable>();
		if (entityClass.getAnnotation(SecondaryTable.class) != null) {
			secondaryTables.add(entityClass.getAnnotation(SecondaryTable.class));
		}
		else if (entityClass.getAnnotation(SecondaryTables.class) != null) {
			secondaryTables.addAll(
					Arrays.asList(entityClass.getAnnotation(SecondaryTables.class).value())
			);
		}
		
		for (SecondaryTable secondaryTable : secondaryTables) {
			TableJoinBuilder<?> tableJoin = result.tableJoins$addTableJoin()
				.joinType(JoinType.LEFT_JOIN)
				.leftTable(result.getTable())
				.rightTable$begin()
					.name(secondaryTable.name())
					.schemaName(secondaryTable.schema())
					.catalogName(secondaryTable.catalog())
				.end()
				;
			
			if (secondaryTable.pkJoinColumns() != null || secondaryTable.pkJoinColumns().length > 0) {
				for (PrimaryKeyJoinColumn pk : secondaryTable.pkJoinColumns()) {
					tableJoin
						.rightColumns$addColumn()
							.table(tableJoin.getRightTable())
							.name(pk.name())
						.end()
						;
					
					tableJoin
						.leftColumns$addColumn()
							.table(tableJoin.getLeftTable())
							.name(pk.referencedColumnName())
						.end()
						;
				}
			}
		}
		
		
		return result;
	}
	
	
	
	public static JoinedTablesBuilder<?> extractTableInfo(Class<?> entityClass, Class<?> rootEntityClass, InheritanceType inheritanceType) {
		
		JoinedTablesBuilder<?> result = _extractTableInfo(rootEntityClass);
		
		if (entityClass == rootEntityClass || inheritanceType == InheritanceType.SINGLE_TABLE) {
			return result;
		}
		
		List<Class<?>> relatedClasses = new ArrayList<Class<?>>();
		Class<?> c = entityClass;
		while (c != null && c != rootEntityClass) {
			if (c.getAnnotation(Entity.class) != null) {
				relatedClasses.add(c);
			}
			c = c.getSuperclass();
		}
		
		Collections.reverse(relatedClasses);
		
		for (Class<?> relatedClass : relatedClasses) {
			JoinedTablesBuilder<?> joinedTables = _extractTableInfo(relatedClass);
			
			TableJoinBuilder<?> tableJoin = tableJoin()
					.leftTable(result.getTable())
					.rightTable(joinedTables.getTable())
					;

			List<PrimaryKeyJoinColumn> pkColumns = new ArrayList<PrimaryKeyJoinColumn>();
			if (relatedClass.getAnnotation(PrimaryKeyJoinColumn.class) != null) {
				pkColumns.add(relatedClass.getAnnotation(PrimaryKeyJoinColumn.class));
			}
			else if (relatedClass.getAnnotation(PrimaryKeyJoinColumns.class) != null) {
				pkColumns.addAll(Arrays.asList(relatedClass.getAnnotation(PrimaryKeyJoinColumns.class).value()));
			}
			
			for (PrimaryKeyJoinColumn pkColumn : pkColumns) {
				tableJoin
					.rightColumns$addColumn()
						.name(pkColumn.name())
						.table(tableJoin.getRightTable())
					.end();
			}
			
			result.tableJoins(tableJoin);
			result.tableJoins(joinedTables.getTableJoins());
		}
		
		
		
		return result;

	}

}
