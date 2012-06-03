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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.internal.util.ObjectUtils;

/**
 * @author Trung Phan
 *
 */
public abstract class AbstractAnnotationDefinitionBuilder {

	
	protected boolean scanFieldForEmbedded(AttributeHolderDefinitionBuilder<?> builder, Field field) {
		if (field.getAnnotation(Embedded.class) != null || field.getType().getAnnotation(Embeddable.class) != null) {
			builder.embedAttributes(field.getName());
			return true;
		}
		return false;
	}
	
	protected void scanFieldForColumnInfo(AttributeHolderDefinitionBuilder<?> builder, Field field) {
		
		Column column = getColumnAnnot(field);
		
		if (column != null) {
			if (ObjectUtils.notEmpty(column.name())) {
				StringBuilder columnName = new StringBuilder();
				if (ObjectUtils.notEmpty(column.table())) {
					columnName.append(column.table()).append('.');
				}
				columnName.append(column.name());
				builder.mapAttribute(field.getName()).toColumn(columnName.toString());
			}
			if (!column.nullable()) {
				builder.notNullableAttributes(field.getName());
			}
			if (!column.insertable()) {
				builder.notInsertableAttributes(field.getName());
			}
			if (!column.updatable()) {
				builder.notUpdatableAttributes(field.getName());
			}
		}
		
	}
	
	protected void scanFieldForRelationship(AttributeRelationshipHolderDefinitionBuilder<?> builder, Field field) {
		if (field.getAnnotation(OneToOne.class) != null) {
			String mappedBy = field.getAnnotation(OneToOne.class).mappedBy();
			String junctionTableName = readJunctionTable(field);
			if (ObjectUtils.notEmpty(mappedBy)) {
				builder.oneToOneJoin(field.getName()).mappedBy(mappedBy);
			}
			else if (ObjectUtils.notEmpty(junctionTableName)) {
				builder.oneToOneJoin(field.getName()).usingJunctionTable(junctionTableName).on(readJoinColumns(field));
			}
			else {
				builder.oneToOneJoin(field.getName()).on(readJoinColumns(field));
			}
		}
		else if (field.getAnnotation(OneToMany.class) != null) {
			String mappedBy = field.getAnnotation(OneToMany.class).mappedBy();
			String junctionTableName = readJunctionTable(field);
			if (ObjectUtils.notEmpty(mappedBy)) {
				builder.oneToManyJoin(field.getName()).mappedBy(mappedBy);
			}
			else if (ObjectUtils.notEmpty(junctionTableName)) {
				builder.oneToManyJoin(field.getName()).usingJunctionTable(junctionTableName).on(readJoinColumns(field));
			}
			else {
				builder.oneToManyJoin(field.getName()).on(readJoinColumns(field));
			}
		}
		else if (field.getAnnotation(ManyToOne.class) != null) {
			String junctionTableName = readJunctionTable(field);
			if (ObjectUtils.notEmpty(junctionTableName)) {
				builder.manyToOneJoin(field.getName()).usingJunctionTable(junctionTableName).on(readJoinColumns(field));
			}
			else {
				builder.manyToOneJoin(field.getName()).on(readJoinColumns(field));
			}
		}
		else if (field.getAnnotation(ManyToMany.class) != null) {
			String mappedBy = field.getAnnotation(ManyToMany.class).mappedBy();
			String junctionTableName = readJunctionTable(field);
			if (ObjectUtils.notEmpty(mappedBy)) {
				builder.manyToManyJoin(field.getName()).mappedBy(mappedBy);
			}
			else if (ObjectUtils.notEmpty(junctionTableName)) {
				builder.manyToManyJoin(field.getName()).usingJunctionTable(junctionTableName).on(readJoinColumns(field));
			}
			else {
				throw new IllegalStateException("Expected Junction Table for Many-To-Many relationship.");
			}
		}
	}
	
	
	private String readJunctionTable(Field field) {
		JoinTable joinTable = field.getAnnotation(JoinTable.class);
		if (joinTable != null) {
			return getTableName(joinTable.name(), joinTable.schema(), joinTable.catalog());
		}
		return "";
	}
	
	private String[] readJoinColumns(Field field) {
		List<String> joins = new ArrayList<String>();
		
		List<JoinColumn> joinColumns = new ListBuilder<JoinColumn>()
				.addNonNullElements(field.getAnnotation(JoinColumn.class))
				.addNonNullElements(field.getAnnotation(JoinColumns.class) == null ? null : field.getAnnotation(JoinColumns.class).value())
				.toList();
		
		if (joinColumns.size() > 0) {
			for (JoinColumn joinColumn : joinColumns) {
				StringBuilder join = new StringBuilder();
				if (ObjectUtils.isEmpty(joinColumn.referencedColumnName())) {
					join.append("this.[default column]");
				}
				else {
					join.append("this.").append(joinColumn.referencedColumnName().toUpperCase().trim());
				}
				join.append(" = ");
				if (ObjectUtils.isEmpty(joinColumn.name())) {
					join.append("that.[default column]");
				}
				else {
					join.append("that.").append(joinColumn.name());
				}
				joins.add(join.toString());
			}
		}
		else if (field.getAnnotation(JoinTable.class) != null) {
			
			JoinTable joinTable = field.getAnnotation(JoinTable.class);
			String junctionTable = getTableName(joinTable.name(), joinTable.schema(), joinTable.catalog());
			
			joinColumns = new ListBuilder<JoinColumn>()
					.addNonNullElements(joinTable.joinColumns())
					.toList();

			for (JoinColumn joinColumn : joinColumns) {
				StringBuilder join = new StringBuilder();
				join.append("this.")
					.append(ObjectUtils.evl(joinColumn.referencedColumnName(), "[default column]"))
					.append(" = ")
					.append(junctionTable).append('.')
					.append(ObjectUtils.evl(joinColumn.name(), "[default column]"));
					;
					
				joins.add(join.toString());
			}
			
			List<JoinColumn> inverseJoinColumns = new ListBuilder<JoinColumn>()
					.addNonNullElements(joinTable.inverseJoinColumns())
					.toList();
			
			for (JoinColumn joinColumn : inverseJoinColumns) {
				StringBuilder join = new StringBuilder();
				join.append(junctionTable).append('.')
					.append(ObjectUtils.evl(joinColumn.referencedColumnName(), "[default column]"))
					.append(" = ")
					.append("that.")
					.append(ObjectUtils.evl(joinColumn.name(), "[default column]"));
					;
					
				joins.add(join.toString());
			}
			
		}
		
		return joins.toArray(new String[0]);
	}

	protected String getTableName(String tableName, String schemaName, String catalogName) {
		StringBuilder result = new StringBuilder();
		if (ObjectUtils.notEmpty(schemaName)) {
			result.append(schemaName).append('.');
		}
		else if (ObjectUtils.notEmpty(catalogName)) {
			result.append(catalogName).append('.');
		}
		if (ObjectUtils.isEmpty(tableName)) {
			result.append("[default table]");
		}
		else {
			result.append(tableName);
		}
		return result.toString();
	}
	
	protected abstract Column getColumnAnnot(Field field);	
	
}
