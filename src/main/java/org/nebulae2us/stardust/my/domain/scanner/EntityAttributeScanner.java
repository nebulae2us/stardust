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

import static org.nebulae2us.stardust.Builders.entityAttribute;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hsqldb.lib.Collection;
import org.nebulae2us.electron.util.ImmutableList;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.db.domain.ColumnBuilder;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.db.domain.TableBuilder;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;
import org.nebulae2us.stardust.internal.util.NameUtils;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.EntityAttributeBuilder;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.RelationalType;

import static org.nebulae2us.stardust.Builders.*;

/**
 * @author Trung Phan
 *
 */
public class EntityAttributeScanner {

	private final EntityBuilder<?> ownningEntity;
	private final EntityBuilder<?> fieldEntity;
	private final Field field;
	
	private final TableBuilder<?> defaultTable;
	
	
	public EntityAttributeScanner(EntityBuilder<?> owningEntity, Field field, EntityBuilder<?> fieldEntity) {
		this.ownningEntity = owningEntity;
		this.fieldEntity = fieldEntity;
		this.field = field;
		
		
		this.defaultTable = ScannerUtils.getDefaultTable(owningEntity.getDeclaringClass(), field.getName());
	}
	
	public EntityAttributeBuilder<?> produce() {
		
		// TODO fix the insertable, updatable and nullable
		
		
		
		EntityAttributeBuilder<?> attributeBuilder = entityAttribute()
				.fullName(this.field.getName())
				.field(this.field)
				.entity(this.fieldEntity)
				.owningEntity(this.ownningEntity)
				.insertable(true)
				.updatable(true)
				.nullable(true)
				;

		RelationalType relationType = extractRelationalType(field);
		String mappedBy = extractMappedByValue(field);
		
		switch (relationType) {
		case MANY_TO_ONE: {
			attributeBuilder.relationalType(RelationalType.MANY_TO_ONE)
			.owningSide(true)
			.rightColumns(fieldEntity.getEntityIdentifier().getColumns())
				;
	
			List<JoinColumn> joinColumns = new ListBuilder<JoinColumn>()
					.addNonNullElements(this.field.getAnnotation(JoinColumn.class))
					.addNonNullElements(this.field.getAnnotation(JoinColumns.class) != null ? this.field.getAnnotation(JoinColumns.class).value() : null)
					.toList();
			
			if (joinColumns.size() > 0) {
				boolean nullable = false;
				for (JoinColumn joinColumn : joinColumns) {
					ColumnBuilder<?> column = attributeBuilder.leftColumns$addColumn()
						.name(joinColumn.name().trim().toUpperCase())
						.table$begin()
							.name(joinColumn.table().trim().toUpperCase())
						.end();
					
					if (ObjectUtils.isEmpty(column.getTable().getName()) ) {
						column.table(defaultTable);
					}
					
					if (joinColumn.nullable()) {
						nullable = true;
					}
				}
				attributeBuilder.joinType(nullable ? JoinType.LEFT_JOIN : JoinType.INNER_JOIN);
			}
			else {
				attributeBuilder.joinType(JoinType.LEFT_JOIN);
				
				for (ColumnBuilder<?> fieldEntityIdColumn : fieldEntity.getEntityIdentifier().getColumns()) {
					attributeBuilder.leftColumns$addColumn()
						.name(fieldEntityIdColumn.getName())
						.table(defaultTable);
				}
			}

			break;
		}
		case ONE_TO_MANY: {
			if (ObjectUtils.notEmpty(mappedBy)) {
				Class<?> fieldEntityClass = this.fieldEntity.getDeclaringClass();

				Field field;
				try {
					field = fieldEntityClass.getDeclaredField(mappedBy);
				} catch (SecurityException e) {
					throw new IllegalStateException("Failed to find conresponding definition by using mappedBy: " + mappedBy, e);
				} catch (NoSuchFieldException e) {
					throw new IllegalStateException("Failed to find conresponding definition by using mappedBy: " + mappedBy, e);
				}
				AssertState.isTrue(field.getAnnotation(ManyToOne.class) != null || !Collection.class.isAssignableFrom(fieldEntityClass), "Failed to find corresponding definition by using mappedBy: %s", mappedBy);
				
				
				EntityAttributeBuilder<?> foreignAttributeBuilder = new EntityAttributeScanner(this.fieldEntity, field, this.ownningEntity).produce();
				attributeBuilder
						.relationalType(RelationalType.ONE_TO_MANY)
						.joinType(JoinType.LEFT_JOIN)
						.inverseAttributeName(mappedBy)
						.leftColumns(foreignAttributeBuilder.getRightColumns())
						.rightColumns(foreignAttributeBuilder.getLeftColumns())
						.junctionTable(foreignAttributeBuilder.getJunctionTable())
						.junctionLeftColumns(foreignAttributeBuilder.getJunctionRightColumns())
						.junctionRightColumns(foreignAttributeBuilder.getJunctionLeftColumns())
						;
					
			}
			
			break;
		}
		case ONE_TO_ONE: {

			if (ObjectUtils.notEmpty(mappedBy)) {
				Class<?> fieldEntityClass = this.fieldEntity.getDeclaringClass();

				Field field;
				try {
					field = fieldEntityClass.getDeclaredField(mappedBy);
				} catch (SecurityException e) {
					throw new IllegalStateException("Failed to find conresponding definition by using mappedBy: " + mappedBy, e);
				} catch (NoSuchFieldException e) {
					throw new IllegalStateException("Failed to find conresponding definition by using mappedBy: " + mappedBy, e);
				}
				AssertState.notNull(field.getAnnotation(OneToOne.class), "Failed to find conresponding definition by using mappedBy: %s", mappedBy);
				
				EntityAttributeBuilder<?> foreignAttributeBuilder = new EntityAttributeScanner(this.fieldEntity, field, this.ownningEntity).produce();
				attributeBuilder
						.relationalType(RelationalType.ONE_TO_ONE)
						.joinType(JoinType.LEFT_JOIN)
						.inverseAttributeName(mappedBy)
						.leftColumns(foreignAttributeBuilder.getRightColumns())
						.rightColumns(foreignAttributeBuilder.getLeftColumns())
						.junctionTable(foreignAttributeBuilder.getJunctionTable())
						.junctionLeftColumns(foreignAttributeBuilder.getJunctionRightColumns())
						.junctionRightColumns(foreignAttributeBuilder.getJunctionLeftColumns())
						;
				
			}
			else {
				attributeBuilder.relationalType(RelationalType.ONE_TO_ONE)
					.owningSide(true)
					.rightColumns(this.fieldEntity.getEntityIdentifier().getColumns())
					;
	
				List<JoinColumn> joinColumns = new ListBuilder<JoinColumn>()
						.addNonNullElements(this.field.getAnnotation(JoinColumn.class))
						.addNonNullElements(this.field.getAnnotation(JoinColumns.class) != null ? this.field.getAnnotation(JoinColumns.class).value() : null)
						.toList();
				
				if (joinColumns.size() > 0) {
					boolean nullable = false;
					for (JoinColumn joinColumn : joinColumns) {
						ColumnBuilder<?> column = attributeBuilder.leftColumns$addColumn()
							.name(joinColumn.name().trim().toUpperCase())
							.table$begin()
								.name(joinColumn.table().trim().toUpperCase())
							.end();
						
						
						if (ObjectUtils.isEmpty(column.getTable().getName()) ) {
							column.table(defaultTable);
						}
						
						if (joinColumn.nullable()) {
							nullable = true;
						}
					}
					attributeBuilder.joinType(nullable ? JoinType.LEFT_JOIN : JoinType.INNER_JOIN);
				}
				else {
					attributeBuilder.joinType(JoinType.LEFT_JOIN)
						.leftColumns$addColumn()
							.name(NameUtils.camelCaseToUpperCase(field.getName()));
				}
				
				
			}
			
			
			break;
		}
		case MANY_TO_MANY: {

			if (ObjectUtils.notEmpty(mappedBy)) {
				Class<?> fieldEntityClass = this.fieldEntity.getDeclaringClass();

				Field field;
				try {
					field = fieldEntityClass.getDeclaredField(mappedBy);
				} catch (SecurityException e) {
					throw new IllegalStateException("Failed to find conresponding definition by using mappedBy: " + mappedBy, e);
				} catch (NoSuchFieldException e) {
					throw new IllegalStateException("Failed to find conresponding definition by using mappedBy: " + mappedBy, e);
				}
				AssertState.notNull(field.getAnnotation(ManyToMany.class), "Failed to find conresponding definition by using mappedBy: %s", mappedBy);
				AssertState.empty(field.getAnnotation(ManyToMany.class).mappedBy(), "Failed to find conresponding definition by using mappedBy: %s", mappedBy);
				
				EntityAttributeBuilder<?> foreignAttributeBuilder = new EntityAttributeScanner(this.fieldEntity, field, this.ownningEntity).produce();
				attributeBuilder
						.relationalType(RelationalType.MANY_TO_MANY)
						.joinType(JoinType.LEFT_JOIN)
						.inverseAttributeName(mappedBy)
						.leftColumns(foreignAttributeBuilder.getRightColumns())
						.rightColumns(foreignAttributeBuilder.getLeftColumns())
						.junctionTable(foreignAttributeBuilder.getJunctionTable())
						.junctionLeftColumns(foreignAttributeBuilder.getJunctionRightColumns())
						.junctionRightColumns(foreignAttributeBuilder.getJunctionLeftColumns())
						;

			}
			else {
				attributeBuilder
						.relationalType(RelationalType.MANY_TO_MANY)
						.joinType(JoinType.LEFT_JOIN);
						
					
					JoinTable joinTableAnnot = this.field.getAnnotation(JoinTable.class);
					
					if (joinTableAnnot != null) {
						TableBuilder<?> junctionTable = attributeBuilder.junctionTable$begin()
								.name(joinTableAnnot.name())
								.schemaName(joinTableAnnot.schema())
								.catalogName(joinTableAnnot.catalog());
						
						List<JoinColumn> joinColumns = new ImmutableList<JoinColumn>(joinTableAnnot.joinColumns());
						List<JoinColumn> inverseJoinColumns = new ImmutableList<JoinColumn>(joinTableAnnot.inverseJoinColumns());
						
						
						if (joinColumns.size() > 0) {
							
							
							
							for (JoinColumn joinColumn : joinColumns) {
								
								
								ColumnBuilder<?> column = attributeBuilder.leftColumns$addColumn()
										.name(joinColumn.referencedColumnName().trim().toUpperCase())
										.table(this.ownningEntity.getLinkedTableBundle().getRoot().getTable())
										;
								
								ColumnBuilder<?> junctionColumn = attributeBuilder.junctionLeftColumns$addColumn()
										.name(joinColumn.name().trim().toUpperCase())
										.table(junctionTable)
										;
								
							}
						}
						
						
						if (inverseJoinColumns.size() > 0) {
							for (JoinColumn inverseJoinColumn : inverseJoinColumns) {
								ColumnBuilder<?> rightColumn = attributeBuilder.rightColumns$addColumn()
										.name(inverseJoinColumn.referencedColumnName().trim().toUpperCase())
										.table(this.fieldEntity.getLinkedTableBundle().getRoot().getTable())
										;
								
								EntityAttributeBuilder<?> junctionRightColumn = attributeBuilder.junctionRightColumns$addColumn()
										.name(inverseJoinColumn.name().trim().toUpperCase())
										.table(junctionTable)
										.end();
							}
						}
						
					}
					else {
						TableBuilder<?> joinTable = table()
								.name(this.ownningEntity.getLinkedTableBundle().getRoot().getTable().getName() + "_" + this.fieldEntity.getLinkedTableBundle().getRoot().getTable().getName());
		
					}
				}
			
			break;
		}
		}
		
		
		return attributeBuilder;
	}
	
	private static String extractMappedByValue(Field field) {
		String result = "";
		
		if (field.getAnnotation(ManyToOne.class) != null) {
		}
		else if (field.getAnnotation(OneToMany.class) != null) {
			result = field.getAnnotation(OneToMany.class).mappedBy();
		}
		else if (field.getAnnotation(OneToOne.class) != null) {
			result = field.getAnnotation(OneToOne.class).mappedBy();
		}
		else if (field.getAnnotation(ManyToMany.class) != null) {
			result = field.getAnnotation(ManyToMany.class).mappedBy();
		}
		
		return result;
	}
	
	private static RelationalType extractRelationalType(Field field) {

		RelationalType result = null;
		
		if (Collection.class.isAssignableFrom(field.getType())) {
			result = RelationalType.ONE_TO_MANY;
		}
		else if (Map.class.isAssignableFrom(field.getType())) {
			throw new UnsupportedOperationException("Map is not supported for relational mapping for field: " + field.getName());
		}
		else {
			result = RelationalType.MANY_TO_ONE;
		}
		
		if (field.getAnnotation(ManyToOne.class) != null) {
			result = RelationalType.MANY_TO_ONE;
		}
		else if (field.getAnnotation(OneToMany.class) != null) {
			result = RelationalType.ONE_TO_MANY;
		}
		else if (field.getAnnotation(OneToOne.class) != null) {
			result = RelationalType.ONE_TO_ONE;
		}
		else if (field.getAnnotation(ManyToMany.class) != null) {
			result = RelationalType.MANY_TO_MANY;
		}
		
		return result;
	}
	
}
