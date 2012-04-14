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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.nebulae2us.electron.Constants;
import org.nebulae2us.electron.WrapConverter;
import org.nebulae2us.electron.internal.util.ClassUtils;
import org.nebulae2us.stardust.Builders;
import org.nebulae2us.stardust.db.domain.LinkedTableBuilder;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttributeBuilder;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.EntityIdentifierBuilder;
import org.nebulae2us.stardust.my.domain.InheritanceType;
import org.nebulae2us.stardust.my.domain.ScalarAttributeBuilder;
import org.nebulae2us.stardust.my.domain.ValueObjectAttributeBuilder;
import org.nebulae2us.stardust.my.domain.ValueObjectBuilder;

import static org.nebulae2us.stardust.Builders.*;

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
			fillDefaultValuesForLinkedTableBundle(entityBuilder);
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

	private void fillDefaultValuesForLinkedTableBundle(EntityBuilder<?> result) {
		if (ObjectUtils.notEmpty(result.getLinkedTableBundle().getNonRoots())) {
			List<ScalarAttributeBuilder<?>> idAttributes = result.getScalarAttributesForIdentifier();
			
			for (LinkedTableBuilder<?> linkedTable : result.getLinkedTableBundle().getNonRoots()) {
				if (ObjectUtils.isEmpty(linkedTable.getParentColumns())) {

					for (ScalarAttributeBuilder<?> idAttribute : idAttributes) {
						linkedTable
							.parentColumns$addColumn()
								.name(idAttribute.getColumn().getName())
								.table(idAttribute.getColumn().getTable())
							.end();
					}
				}
				if (ObjectUtils.isEmpty(linkedTable.getColumns())) {
					for (ScalarAttributeBuilder<?> idAttribute : idAttributes) {
						linkedTable
							.columns$addColumn()
								.name(idAttribute.getColumn().getName())
								.table(linkedTable.getTable())
							.end();
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
			EntityBuilder<?> wrap = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(scannedEntity).to(EntityBuilder.class);
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
				.linkedTableBundle(ScannerUtils.extractTableInfo(entityClass, rootEntityClass, inheritanceType))
				.inheritanceType(inheritanceType)
				.rootEntity(rootEntity)
				;
		
		if (rootEntity == null) {
			rootEntity = result;
			result.setRootEntity(rootEntity);
		}
		
		populateIdentifier(result, relatedClasses);
		
		scannedEntityBuilders.put(entityClass, result);
		
		populateAttributes(result, relatedClasses);

		return result;
	}

	private void populateAttributes(EntityBuilder<?> result,
			List<Class<?>> relatedClasses) {
		for (Class<?> relatedClass : relatedClasses) {
			
			for (Field field : relatedClass.getDeclaredFields()) {
				
				if ((field.getModifiers() & Modifier.TRANSIENT) != 0 || field.getAnnotation(Transient.class) != null) {
					continue;
				}
				
				Class<?> fieldClass = ClassUtils.getClass(field.getType());
				
				if (Constants.SCALAR_TYPES.contains(fieldClass) || fieldClass.isEnum()) {
					if (field.getAnnotation(Id.class) != null) {
						continue;
					}
					
					result.attributes(new ScalarAttributeScanner(result, field, "").produce());
					
				}
				else if (field.getAnnotation(Embedded.class) != null || field.getAnnotation(EmbeddedId.class) != null || fieldClass.getAnnotation(Embeddable.class) != null) {
					if (field.getAnnotation(Id.class) != null || field.getAnnotation(EmbeddedId.class) != null) {
						continue;
					}
					
					AttributeOverrideScanner attributeOverrideAnnots = new AttributeOverrideScanner(result, field.getAnnotation(AttributeOverride.class), field.getAnnotation(AttributeOverrides.class));

					ValueObjectBuilder<?> valueObjectBuilder = new ValueObjectScanner(result, fieldClass, field.getName(), attributeOverrideAnnots).produce();
					
					ValueObjectAttributeBuilder<?> attributeBuilder = valueObjectAttribute()
							.fullName(field.getName())
							.field(field)
							.valueObject(valueObjectBuilder)
							.owningEntity(result)
							;
					
					result.attributes(attributeBuilder);
				}
				else if (Collection.class.isAssignableFrom(fieldClass)) {
	
					Type fieldSubType = ClassUtils.getGenericSubType(field.getGenericType());
					Class<?> fieldSubClass = ClassUtils.getClass(fieldSubType);
					
					if (fieldSubClass == Object.class || fieldSubClass.getAnnotation(Embeddable.class) != null || Constants.SCALAR_TYPES.contains(fieldSubClass)) {
						continue;
					}
					else {
						
						EntityBuilder<?> subEntityBuilder = new EntityScanner(fieldSubClass, scannedEntityBuilders, scannedEntities).produceRaw();
						
						EntityAttributeBuilder<?> attributeBuilder = entityAttribute()
								.fullName(field.getName())
								.field(field)
								.entity(subEntityBuilder)
								.owningEntity(result)
								;
						
						result.attributes(attributeBuilder);
					}
					
				}
				else {
					EntityBuilder<?> fieldEntity = new EntityScanner(fieldClass, scannedEntityBuilders, scannedEntities).produceRaw();
					
					EntityAttributeBuilder<?> attributeBuilder = new EntityAttributeScanner(result, field, fieldEntity).produce();
					
					result.attributes(attributeBuilder);
				}
			}			
		}
	}

	@SuppressWarnings("unchecked")
	private void populateIdentifier(EntityBuilder<?> result, List<Class<?>> relatedClasses) {
		
		for (Class<?> relatedClass : relatedClasses) {
			
			EntityIdentifierBuilder<?> entityIdentifierBuilder = null;
			
			for (Field field : relatedClass.getDeclaredFields()) {
				
				if ((field.getModifiers() & Modifier.TRANSIENT) != 0 || field.getAnnotation(Transient.class) != null) {
					continue;
				}
				
				Class<?> fieldClass = ClassUtils.getClass(field.getType());
				
				if (Constants.SCALAR_TYPES.contains(fieldClass) || fieldClass.isEnum()) {
					if (field.getAnnotation(Id.class) != null) {
						if (entityIdentifierBuilder != null || result.getEntityIdentifier() == null) {
							if (entityIdentifierBuilder == null) {
								entityIdentifierBuilder = Builders.entityIdentifier();
							}
							
							ScalarAttributeBuilder<?> attributeBuilder = new ScalarAttributeScanner(result, field, "").produce();
							result.attributes(attributeBuilder);
							entityIdentifierBuilder.attributes(attributeBuilder);
						}
					}
				}
				else if (field.getAnnotation(Embedded.class) != null || field.getAnnotation(EmbeddedId.class) != null || fieldClass.getAnnotation(Embeddable.class) != null) {
					if (field.getAnnotation(Id.class) != null || field.getAnnotation(EmbeddedId.class) != null) {
						if (entityIdentifierBuilder != null || result.getEntityIdentifier() == null) {
							if (entityIdentifierBuilder == null) {
								entityIdentifierBuilder = Builders.entityIdentifier();
							}

							AttributeOverrideScanner attributeOverrideAnnots = new AttributeOverrideScanner(result, field.getAnnotation(AttributeOverride.class), field.getAnnotation(AttributeOverrides.class));

							ValueObjectBuilder<?> valueObjectBuilder = new ValueObjectScanner(result, fieldClass, field.getName(), attributeOverrideAnnots).produce();
							
							ValueObjectAttributeBuilder<?> attributeBuilder = valueObjectAttribute()
									.fullName(field.getName())
									.field(field)
									.valueObject(valueObjectBuilder)
									.owningEntity(result)
									;
							
							result.attributes(attributeBuilder);
							
							entityIdentifierBuilder.attributes(attributeBuilder);
						}
					}
				}
			}
			
			if (entityIdentifierBuilder != null) {
				result.entityIdentifier(entityIdentifierBuilder);
			}
			
		}
		if (result.getEntityIdentifier() == null) {
			result.entityIdentifier$begin()
				.attributes(Collections.EMPTY_LIST)
			.end();
		}
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
