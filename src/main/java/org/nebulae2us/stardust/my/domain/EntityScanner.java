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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.nebulae2us.electron.Constants;
import org.nebulae2us.electron.WrapConverter;
import org.nebulae2us.electron.internal.util.ClassUtils;
import org.nebulae2us.stardust.Builders;
import org.nebulae2us.stardust.db.domain.ColumnBuilder;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.db.domain.TableJoinBuilder;
import org.nebulae2us.stardust.internal.util.ObjectUtils;

import static org.nebulae2us.stardust.Builders.*;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class EntityScanner {
	
	private final Class<?> entityClass;
	
	private final Map<Class<?>, EntityBuilder<?>> scannedEntityBuilders;
	
	/**
	 * @return the scannedEntityBuilders
	 */
	public Map<Class<?>, EntityBuilder<?>> getScannedEntityBuilders() {
		return scannedEntityBuilders;
	}


	private final Map<Class<?>, Entity> scannedEntities;
	
	public EntityScanner(Class<?> entityClass, Map<Class<?>, Entity> scannedEntities) {
		this(entityClass, new IdentityHashMap<Class<?>, EntityBuilder<?>>(), scannedEntities);
	}
	
	private EntityScanner(Class<?> entityClass, Map<Class<?>, EntityBuilder<?>> scannedEntityBuilders, Map<Class<?>, Entity> scannedEntities) {
		if (entityClass == null) {
			throw new NullPointerException();
		}
		this.entityClass = entityClass;
		
		this.scannedEntityBuilders = scannedEntityBuilders;
		this.scannedEntities = scannedEntities;
	}

	
	public void scan() {
		EntityBuilder<?> result = produceRaw();

		for (EntityBuilder<?> entityBuilder : scannedEntityBuilders.values()) {
			fixJoinedTables(entityBuilder);
			fixEntityAttributes(entityBuilder);
		}
		
//		for (EntityBuilder<?> entityBuilder : scannedEntityBuilders.values()) {
//			System.out.println("**** " + entityBuilder.getDeclaringClass());
//			
//			for (AttributeBuilder<?> attribute : entityBuilder.getAttributes()) {
//				System.out.println(" ----" + attribute.getField().getName());
//				if (attribute instanceof ScalarAttributeBuilder) {
//					ScalarAttributeBuilder<?> scalarAttribute = (ScalarAttributeBuilder<?>)attribute;
//					System.out.println("column: " + scalarAttribute.getColumn().getName());
//					System.out.println("table: " + scalarAttribute.getColumn().getTable().getName());
//				}
//				else if (attribute instanceof EntityAttributeBuilder) {
//					EntityAttributeBuilder<?> entityAttribute = (EntityAttributeBuilder<?>)attribute;
//					System.out.println("left columns: " + entityAttribute.getLeftColumns());
//					System.out.println("right columns: " + entityAttribute.getLeftColumns());
//				}
//			}
//		}
		
	}

	/**
	 * @param result
	 */
	private void fixEntityAttributes(EntityBuilder<?> entity) {
		for (EntityAttributeBuilder<?> entityAttribute : ScannerUtils.extractEntityAttributes(entity.getAttributes())) {
			Field field = entityAttribute.getField();

			List<ScalarAttributeBuilder<?>> idAttributes = ScannerUtils.extractScalarAttributes(entityAttribute.getEntity().getEntityIdentifier().getAttributes());

			
			
			if (field.getAnnotation(ManyToOne.class) != null) {
				entityAttribute.relationalType(RelationalType.MANY_TO_ONE)
					.joinType(JoinType.LEFT_JOIN);
				
				List<JoinColumn> annotJoinColumns = new ArrayList<JoinColumn>();
				if (field.getAnnotation(JoinColumn.class) != null) {
					annotJoinColumns.add(field.getAnnotation(JoinColumn.class));
				}
				else if (field.getAnnotation(JoinColumns.class) != null) {
					annotJoinColumns.addAll(
						Arrays.asList(field.getAnnotation(JoinColumns.class).value())
					);
				}
				
				if (annotJoinColumns.size() > 0) {
					if (annotJoinColumns.size() != idAttributes.size()) {
						throw new IllegalStateException(String.format("columns size are not matching for a join %d vs %d", annotJoinColumns.size(), idAttributes.size()));
					}
					
					for (int i = 0; i < annotJoinColumns.size(); i++) {
						JoinColumn annotJoinColumn = annotJoinColumns.get(i);
						ScalarAttributeBuilder<?> idAttribute = idAttributes.get(i);
						entityAttribute
							.rightColumn()
								.name(annotJoinColumn.name())
								.table()
									.name(annotJoinColumn.table())
								.end()
							.end()
							.leftColumn(idAttribute.getColumn())
							;
					}
					
					for (ColumnBuilder<?> column : entityAttribute.getRightColumns()) {
						if (ObjectUtils.isEmpty(column.getTable().getName())) {
							column.table(entity.getJoinedTables().getTable());
						}
					}
					
				}
				
				
				
			}
			else if (field.getAnnotation(OneToMany.class) != null) {
				OneToMany oneToMany = field.getAnnotation(OneToMany.class);
				if (ObjectUtils.notEmpty(oneToMany.mappedBy())) {
					
				}
			}
		}
	}

	private void fixJoinedTables(EntityBuilder<?> result) {
		if (ObjectUtils.notEmpty(result.getJoinedTables().getTableJoins())) {
			List<ScalarAttributeBuilder<?>> idAttributes = ScannerUtils.extractScalarAttributes(
					result.getEntityIdentifier().getAttributes()
			);
			
			for (TableJoinBuilder<?> tableJoin : result.getJoinedTables().getTableJoins()) {
				if (ObjectUtils.isEmpty(tableJoin.getLeftColumns())) {
					for (ScalarAttributeBuilder<?> idAttribute : idAttributes) {
						tableJoin.leftColumn()
							.name(idAttribute.getColumn().getName())
							.table()
								.name(idAttribute.getColumn().getTable().getName());
					}
				}
				if (ObjectUtils.isEmpty(tableJoin.getRightColumns())) {
					for (ScalarAttributeBuilder<?> idAttribute : idAttributes) {
						tableJoin.rightColumn()
							.name(idAttribute.getColumn().getName())
							.table()
								.name(idAttribute.getColumn().getTable().getName());
					}
				}
			}
		}
	}
	
	private EntityBuilder<?> produceRaw() {
		
		if (scannedEntityBuilders.containsKey(entityClass)) {
			return scannedEntityBuilders.get(entityClass);
		}
		
		if (scannedEntities.containsKey(entityClass)) {
			Entity scannedEntity = scannedEntities.get(entityClass);
			EntityBuilder<?> wrap = new WrapConverter(CONVERTER_OPTIONS_MUTABLE).convert(scannedEntity).to(EntityBuilder.class);
			return wrap;
		}

		EntityBuilder<?> rootEntity = null;
		List<Class<?>> relatedClasses = extractRelatedClasses();
		for (Class<?> relatedClass : relatedClasses) {
			if (relatedClass != entityClass && relatedClass.getAnnotation(javax.persistence.Entity.class) != null) {
				rootEntity = new EntityScanner(relatedClass, scannedEntityBuilders, scannedEntities).produceRaw();
			}
		}
		
		Class<?> rootEntityClass = rootEntity != null ? rootEntity.getDeclaringClass() : entityClass;
		InheritanceType inheritanceType = determineInheritanceType(rootEntityClass);
		
		EntityBuilder<?> result = entity()
				.declaringClass(entityClass)
				.joinedTables(ScannerUtils.extractTableInfo(entityClass, rootEntityClass, inheritanceType))
				.inheritanceType(inheritanceType)
				.rootEntity(rootEntity)
				;
		
		if (rootEntity == null) {
			rootEntity = result;
			result.setRootEntity(rootEntity);
		}
		
		scannedEntityBuilders.put(entityClass, result);
		
		for (Class<?> relatedClass : relatedClasses) {
			
			EntityIdentifierBuilder<?> entityIdentifierBuilder = null;
			
			for (Field field : relatedClass.getDeclaredFields()) {
				
				if ((field.getModifiers() & Modifier.TRANSIENT) != 0 || field.getAnnotation(Transient.class) != null) {
					continue;
				}
				
				Class<?> fieldClass = ClassUtils.getClass(field.getType());
				
				if (Constants.SCALAR_TYPES.contains(fieldClass) || fieldClass.isEnum()) {
					ScalarAttributeBuilder<?> attributeBuilder = scalarAttribute()
							.field(field)
							.scalarType(fieldClass)
							.owningEntity(result)
							.column(ScannerUtils.extractColumnInfo(field, result.getJoinedTables()))
							;
					
					result.attribute(attributeBuilder);
					
					if (field.getAnnotation(Id.class) != null) {
						if (entityIdentifierBuilder != null || result.getEntityIdentifier() == null) {
							if (entityIdentifierBuilder == null) {
								entityIdentifierBuilder = Builders.entityIdentifier();
							}
							entityIdentifierBuilder.attribute(attributeBuilder);
						}
					}
					
				}
				else if (field.getAnnotation(Embedded.class) != null || field.getAnnotation(EmbeddedId.class) != null || fieldClass.getAnnotation(Embeddable.class) != null) {
					ValueObjectBuilder<?> valueObjectBuilder = new ValueObjectScanner(result, fieldClass).produce();
					
					ValueObjectAttributeBuilder<?> attributeBuilder = valueObjectAttribute()
							.field(field)
							.valueObject(valueObjectBuilder)
							.owningEntity(result)
							;
					
					result.attribute(attributeBuilder);
					
					if (field.getAnnotation(Id.class) != null || field.getAnnotation(EmbeddedId.class) != null) {
						if (entityIdentifierBuilder != null || result.getEntityIdentifier() == null) {
							if (entityIdentifierBuilder == null) {
								entityIdentifierBuilder = Builders.entityIdentifier();
							}
							entityIdentifierBuilder.attribute(attributeBuilder);
						}
					}
				
				}
				else if (Collection.class.isAssignableFrom(fieldClass)) {
	
					Type fieldSubType = ClassUtils.getGenericSubType(field.getGenericType());
					Class<?> fieldSubClass = ClassUtils.getClass(fieldSubType);
					
					if (fieldSubClass.getAnnotation(Embeddable.class) != null) {
					}
					else if (Constants.SCALAR_TYPES.contains(fieldSubClass)) {						
					}
					else {
						
						EntityBuilder<?> subEntityBuilder = new EntityScanner(fieldSubClass, scannedEntityBuilders, scannedEntities).produceRaw();
						
						EntityAttributeBuilder<?> attributeBuilder = entityAttribute()
								.field(field)
								.entity(subEntityBuilder)
								.owningEntity(result)
								;
						
						result.attribute(attributeBuilder);
					}
					
				}
				else {
					EntityBuilder<?> subEntityBuilder = new EntityScanner(fieldClass, scannedEntityBuilders, scannedEntities).produceRaw();
					
					EntityAttributeBuilder<?> attributeBuilder = entityAttribute()
							.field(field)
							.entity(subEntityBuilder)
							.owningEntity(result)
							;
					
					result.attribute(attributeBuilder);
				}
			}
			
			if (entityIdentifierBuilder != null) {
				result.entityIdentifier(entityIdentifierBuilder);
			}
			
		}

		return result;
	}

	private InheritanceType determineInheritanceType(final Class<?> rootEntityClass) {
		
		if (rootEntityClass.getAnnotation(Inheritance.class) != null) {
			Inheritance inheritance = rootEntityClass.getAnnotation(Inheritance.class);
			if (inheritance.strategy() != null) {
				switch (inheritance.strategy()) {
				case JOINED:
					return InheritanceType.JOINED;
				case SINGLE_TABLE:
					return InheritanceType.SINGLE_TABLE;
				case TABLE_PER_CLASS:
					throw new IllegalStateException("Inheritance type TABLE_PER_CLASS is not supported.");
				}
				
			}
		}
		
		return InheritanceType.SINGLE_TABLE;
	}

	private List<Class<?>> extractRelatedClasses() {
		List<Class<?>> relatedClasses = new ArrayList<Class<?>>();
		relatedClasses.add(entityClass);
		Class<?> c = entityClass;
		while ( (c = c.getSuperclass()) != null) {
			if (c.getAnnotation(javax.persistence.Entity.class) != null || c.getAnnotation(MappedSuperclass.class) != null) {
				relatedClasses.add(c);
			}
		}
		return relatedClasses;
	}

}
