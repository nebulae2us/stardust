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

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.internal.util.ObjectUtils;

/**
 * @author Trung Phan
 *
 */
public class AnnotationExtensionDefinitionBuilder extends AbstractAnnotationDefinitionBuilder {

	private final Class<?> extClass;
	private final Class<?> owningEntityClass;
	
	public AnnotationExtensionDefinitionBuilder(Class<?> owningEntityClass, Class<?> extClass) {
		Assert.isTrue(owningEntityClass != extClass, "owningEntityClass cannot be extClass");
		Assert.isTrue(extClass.isAssignableFrom(owningEntityClass), "% does not extends %s", owningEntityClass.getSimpleName(), extClass.getSimpleName());
		
		this.owningEntityClass = owningEntityClass;
		this.extClass = extClass;
		
	}
	
	public ExtensionDefinition toExtensionDefinition() {
		ExtensionDefinitionBuilder builder = new ExtensionDefinitionBuilder(owningEntityClass, extClass);
		
		for (Field field : extClass.getDeclaredFields()) {
			if ((field.getModifiers() & Modifier.TRANSIENT) > 0 || field.getAnnotation(Transient.class) != null) {
				builder.excludeAttributes(field.getName());
				continue;
			}
		
			if (field.getAnnotation(Id.class) != null) {
				builder.identifier(field.getName());
			}
			
			if (field.getAnnotation(EmbeddedId.class) != null) {
				builder.identifier(field.getName());
				builder.embedAttributes(field.getName());
			}
		
			if (scanFieldForEmbedded(builder, field)) {
				continue;
			}
			
			scanFieldForColumnInfo(builder, field);
			
			scanFieldForRelationship(builder, field);
			
		}
		
		
		return builder.toExtensionDefinition();
	}
	
	@Override
	protected Column getColumnAnnot(Field field) {
		
		Class<?> startClass = this.owningEntityClass;
		
		while (true) {
			
			List<AttributeOverride> overrides = new ListBuilder<AttributeOverride>()
					.addNonNullElements(startClass.getAnnotation(AttributeOverride.class))
					.addNonNullElements(startClass.getAnnotation(AttributeOverrides.class) == null ? null : startClass.getAnnotation(AttributeOverrides.class).value())
					.toList();
			
			if (overrides.size() > 0) {
				for (AttributeOverride override : overrides) {
					if (override.name().equals(field.getName())) {
						return override.column();
					}
				}
			}
			
			startClass = startClass.getSuperclass();
			if (startClass == this.extClass) {
				return field.getAnnotation(Column.class);
			}
			while (startClass.getAnnotation(Entity.class) == null || startClass.getAnnotation(MappedSuperclass.class) == null) {
				startClass = startClass.getSuperclass();
				if (startClass == this.extClass) {
					return field.getAnnotation(Column.class);
				}
			}

		}
	}	
}
