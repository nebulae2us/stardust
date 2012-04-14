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
import javax.persistence.ManyToOne;

import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.db.domain.ColumnBuilder;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.internal.util.NameUtils;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.EntityAttributeBuilder;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.RelationalType;

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
		
		return attributeBuilder;
	}
	
}
