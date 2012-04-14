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

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	public EntityAttributeScanner(EntityBuilder<?> owningEntity, Field field, EntityBuilder<?> fieldEntity) {
		this.ownningEntity = owningEntity;
		this.fieldEntity = fieldEntity;
		this.field = field;
	}
	
	public EntityAttributeBuilder<?> produce() {
		
		EntityAttributeBuilder<?> attributeBuilder = entityAttribute()
				.fullName(this.field.getName())
				.field(this.field)
				.entity(this.fieldEntity)
				.owningEntity(this.ownningEntity)
				;
		
		if (this.field.getAnnotation(ManyToOne.class) != null) {
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
						column.table(this.ownningEntity.getLinkedTableBundle().getRoot().getTable());
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
		else if (this.field.getAnnotation(OneToMany.class) != null) {
			OneToMany oneToMany = this.field.getAnnotation(OneToMany.class);
			if (ObjectUtils.notEmpty(oneToMany.mappedBy())) {
				Class<?> fieldEntityClass = this.fieldEntity.getDeclaringClass();
				try {
					Field field = fieldEntityClass.getDeclaredField(oneToMany.mappedBy());
					AssertState.notNull(field.getAnnotation(ManyToOne.class), "Failed to find conresponding definition by using mappedBy: %s",oneToMany.mappedBy());
					
					EntityAttributeBuilder<?> foreignAttributeBuilder = new EntityAttributeScanner(this.fieldEntity, field, this.ownningEntity).produce();
					attributeBuilder
							.relationalType(RelationalType.ONE_TO_MANY)
							.joinType(JoinType.LEFT_JOIN)
							.leftColumns(foreignAttributeBuilder.getRightColumns())
							.junctionLeftColumns(foreignAttributeBuilder.getJunctionRightColumns())
							.rightColumns(foreignAttributeBuilder.getLeftColumns())
							.junctionRightColumns(foreignAttributeBuilder.getJunctionLeftColumns())
							;
					
				} catch (Exception e) {
					throw new IllegalStateException("Failed to find conresponding definition by using mappedBy: " + oneToMany.mappedBy());
				}
				
			}
		}
		else if (this.field.getAnnotation(ManyToMany.class) != null) {
			
			ManyToMany manyToMany = this.field.getAnnotation(ManyToMany.class);
			
			
			if (ObjectUtils.notEmpty(manyToMany.mappedBy())) {
				Class<?> fieldEntityClass = this.fieldEntity.getDeclaringClass();
				try {
					Field field = fieldEntityClass.getDeclaredField(manyToMany.mappedBy());
					AssertState.notNull(field.getAnnotation(ManyToMany.class), "Failed to find conresponding definition by using mappedBy: %s", manyToMany.mappedBy());
					AssertState.empty(field.getAnnotation(ManyToMany.class).mappedBy(), "Failed to find conresponding definition by using mappedBy: %s", manyToMany.mappedBy());
					
					EntityAttributeBuilder<?> foreignAttributeBuilder = new EntityAttributeScanner(this.fieldEntity, field, this.ownningEntity).produce();
					attributeBuilder
							.relationalType(RelationalType.MANY_TO_MANY)
							.joinType(JoinType.LEFT_JOIN)
							.leftColumns(foreignAttributeBuilder.getRightColumns())
							.junctionLeftColumns(foreignAttributeBuilder.getJunctionRightColumns())
							.rightColumns(foreignAttributeBuilder.getLeftColumns())
							.junctionRightColumns(foreignAttributeBuilder.getJunctionLeftColumns())
							;
					
				} catch (Exception e) {
					throw new IllegalStateException("Failed to find conresponding definition by using mappedBy: " + manyToMany.mappedBy());
				}

			}
			else {
				attributeBuilder
						.relationalType(RelationalType.MANY_TO_MANY)
						.joinType(JoinType.LEFT_JOIN);
						
					
					JoinTable joinTableAnnot = this.field.getAnnotation(JoinTable.class);
					
					if (joinTableAnnot != null) {
						TableBuilder<?> joinTable = table()
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
										.table(joinTable)
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
										.table(joinTable)
										.end();
							}
						}
						
					}
					else {
						TableBuilder<?> joinTable = table()
								.name(this.ownningEntity.getLinkedTableBundle().getRoot().getTable().getName() + "_" + this.fieldEntity.getLinkedTableBundle().getRoot().getTable().getName());
		
					}
				}
			}
			
		
		return attributeBuilder;
	}
	
}
