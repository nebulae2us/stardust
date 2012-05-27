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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.nebulae2us.electron.Constants;
import org.nebulae2us.electron.WrapConverter;
import org.nebulae2us.electron.internal.util.ClassUtils;
import org.nebulae2us.stardust.Builders;
import org.nebulae2us.stardust.db.domain.ColumnBuilder;
import org.nebulae2us.stardust.db.domain.LinkedTableBuilder;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttributeBuilder;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.EntityDiscriminatorBuilder;
import org.nebulae2us.stardust.my.domain.EntityIdentifierBuilder;
import org.nebulae2us.stardust.my.domain.InheritanceType;
import org.nebulae2us.stardust.my.domain.ScalarAttributeBuilder;
import org.nebulae2us.stardust.my.domain.ValueObjectAttributeBuilder;
import org.nebulae2us.stardust.my.domain.ValueObjectBuilder;

import static org.nebulae2us.stardust.Builders.*;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class EntityScanner {
	
	private final Class<?> entityClass;
	
	/**
	 * EntityBuilder in this map must have identifiers populated and inheritance populated.
	 * All attributes may not be populated yet. This is because of the circle reference nature among entities.
	 */
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
		Assert.notNull(entityClass, "entityClass cannot be null");

		this.entityClass = entityClass;
		
		this.scannedEntityBuilders = scannedEntityBuilders;
		this.scannedEntities = scannedEntities;
	}

	
	public void scan() {
		EntityBuilder<?> result = produceRaw();

		for (EntityBuilder<?> entityBuilder : scannedEntityBuilders.values()) {
			fillDefaultValuesForLinkedTableBundle(entityBuilder);
			fixInverseEntityAttribute(entityBuilder);
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
	
	/**
	 * This is to link entityAttribute with its inverseEntityAttribute to make sure entityAttribute.inverseEntityAttribute.inverseEntityAttribute == entitAttribute
	 * @param entityBuilder
	 */
	private void fixInverseEntityAttribute(EntityBuilder<?> entityBuilder) {
		
		for (EntityAttributeBuilder<?> entityAttribute : entityBuilder.getEntityAttributes()) {

			if (ObjectUtils.isEmpty(entityAttribute.getInverseAttributeName())) {
				EntityBuilder<?> foreignEntityBuilder = entityAttribute.getEntity();
				List<EntityAttributeBuilder<?>> foreignEntityAttributes = foreignEntityBuilder.getEntityAttributes();
				for (EntityAttributeBuilder<?> foreignEntityAttribute : foreignEntityAttributes) {
					if (entityAttribute.getFullName().equals(foreignEntityAttribute.getInverseAttributeName())) {
						entityAttribute.setInverseAttributeName(foreignEntityAttribute.getFullName());
						break;
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

		EntityBuilder<?> result = entity()
				.declaringClass(entityClass)
				;
		
		List<Class<?>> relatedClasses = extractRelatedClasses();

		populateTableInheritance(result);
		
		populateIdentifier(result, relatedClasses);
		
		scannedEntityBuilders.put(entityClass, result);
		
		populateAttributes(result, relatedClasses);
		
		return result;
	}

	/**
	 * By default, the inheritance is SINGLE_TABLE. If there is no inheritance, the rootClass = entityClass itself, and the inheritance = SINGLE_TABLE
	 * @param result
	 * @param relatedClasses
	 */
	private void populateTableInheritance(EntityBuilder<?> result) {

		Class<?> rootEntityClass = ScannerUtils.getRootEntityClass(entityClass);
		EntityBuilder<?> rootEntity = rootEntityClass == entityClass ? result : new EntityScanner(rootEntityClass, scannedEntityBuilders, scannedEntities).produceRaw();
		
		for (Class<?> superEntityClass : ScannerUtils.getSuperEntityClasses(entityClass)) {
			new EntityScanner(superEntityClass, scannedEntityBuilders, scannedEntities).produceRaw();
		}
		
		InheritanceType inheritanceType = ScannerUtils.determineInheritanceType(rootEntityClass);
		
		result
			.linkedTableBundle(ScannerUtils.extractTableInfo(entityClass, rootEntityClass, inheritanceType))
			.inheritanceType(inheritanceType)
			.rootEntity(rootEntity)
			;
		
		if (inheritanceType == InheritanceType.JOINED) {
			
			EntityDiscriminatorBuilder<?> discriminator = result.entityDiscriminator$begin();
			DiscriminatorColumn discriminatorColumn = rootEntityClass.getAnnotation(DiscriminatorColumn.class);
			DiscriminatorValue discriminatorValue = this.entityClass.getAnnotation(DiscriminatorValue.class);
			
			DiscriminatorType discriminatorType = discriminatorColumn != null ? discriminatorColumn.discriminatorType() : DiscriminatorType.STRING;
			
			discriminator.column$begin()
				.name(discriminatorColumn != null && ObjectUtils.notEmpty(discriminatorColumn.name()) ? discriminatorColumn.name().trim().toUpperCase() : "DTYPE")
				.table(result.getLinkedTableBundle().getRoot().getTable())
			.end();
			
			String value = discriminatorValue != null && ObjectUtils.notEmpty(discriminatorValue.value()) ? discriminatorValue.value() : this.entityClass.getSimpleName();
			
			switch (discriminatorType) {
			case STRING:
				discriminator.value(value);
				break;
			case CHAR:
				discriminator.value(value.length() == 1 ? value.charAt(0) : value);
				break;
			case INTEGER:
				try {
					discriminator.value(Integer.valueOf(value));
				}
				catch (Exception e) {
					discriminator.value(value);
				}
				break;
			default:
				throw new IllegalStateException("Unknown discriminatorType");
			}
			
		}
	}

	private void populateAttributes(EntityBuilder<?> result, List<Class<?>> relatedClasses) {
		
		AttributeOverrideScanner globalAttributeOverrideScanner = new AttributeOverrideScanner();
		
		for (Class<?> relatedClass : relatedClasses) {
			
			globalAttributeOverrideScanner = globalAttributeOverrideScanner.combine(result.getLinkedTableBundle().getRoot().getTable().getName(), 
					relatedClass.getAnnotation(AttributeOverride.class), relatedClass.getAnnotation(AttributeOverrides.class), false);
			
			for (Field field : relatedClass.getDeclaredFields()) {
				
				if ((field.getModifiers() & Modifier.TRANSIENT) != 0 || field.getAnnotation(Transient.class) != null) {
					continue;
				}
				
				Class<?> fieldClass = ClassUtils.getClass(field.getType());
				
				if (Constants.SCALAR_TYPES.contains(fieldClass) || fieldClass.isEnum()) {
					if (field.getAnnotation(Id.class) != null) {
						continue;
					}
					
					ScalarAttributeBuilder<?> scalarAttribute = new ScalarAttributeScanner(result, field, "").produce();
					
					if (globalAttributeOverrideScanner.getColumns().containsKey(scalarAttribute.getName())) {
						ColumnBuilder<?> column = globalAttributeOverrideScanner.getColumns().get(scalarAttribute.getName());
						scalarAttribute.column(column);
					}
					
					result.attributes(scalarAttribute);
					
					if (field.getAnnotation(Version.class) != null) {
						result.version(scalarAttribute);
					}
					
				}
				else if (field.getAnnotation(Embedded.class) != null || field.getAnnotation(EmbeddedId.class) != null || fieldClass.getAnnotation(Embeddable.class) != null) {
					if (field.getAnnotation(Id.class) != null || field.getAnnotation(EmbeddedId.class) != null) {
						continue;
					}
					
					AttributeOverrideScanner attributeOverrideScanner = globalAttributeOverrideScanner.sub(field.getName()).combine(result.getLinkedTableBundle().getRoot().getTable().getName(), field.getAnnotation(AttributeOverride.class), field.getAnnotation(AttributeOverrides.class), true);
					ValueObjectBuilder<?> valueObjectBuilder = new ValueObjectScanner(result, fieldClass, field.getName(), attributeOverrideScanner).produce();
					
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
						
						EntityAttributeBuilder<?> attributeBuilder = new EntityAttributeScanner(result, field, subEntityBuilder).produce();
						
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

							AttributeOverrideScanner attributeOverrideScanner = new AttributeOverrideScanner(result, field.getAnnotation(AttributeOverride.class), field.getAnnotation(AttributeOverrides.class));

							ValueObjectBuilder<?> valueObjectBuilder = new ValueObjectScanner(result, fieldClass, field.getName(), attributeOverrideScanner).produce();
							
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
