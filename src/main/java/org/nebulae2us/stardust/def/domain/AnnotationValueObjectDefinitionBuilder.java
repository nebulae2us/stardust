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
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.internal.util.ClassUtils;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.internal.util.ObjectUtils;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class AnnotationValueObjectDefinitionBuilder extends AbstractAnnotationDefinitionBuilder {

	private Class<?> voClass;
	
	private Class<?> owningEntityClass;
	
	private final String attributeFullName;
	
	
	public AnnotationValueObjectDefinitionBuilder(Class<?> owningEntityClass, String attributeFullName, Class<?> voClass) {
		this.voClass = voClass;
		this.owningEntityClass = owningEntityClass;
		this.attributeFullName = attributeFullName;
	}
	
	public ValueObjectDefinition toValueObjectDefinition() {
		ValueObjectDefinitionBuilder builder = new ValueObjectDefinitionBuilder(owningEntityClass, attributeFullName, voClass);
		
		for (Field field : voClass.getDeclaredFields()) {
			if ((field.getModifiers() & Modifier.TRANSIENT) > 0 || field.getAnnotation(Transient.class) != null) {
				builder.excludeAttributes(field.getName());
				continue;
			}
		
			if (scanFieldForEmbedded(builder, field)) {
				continue;
			}

			scanFieldForColumnInfo(builder, field);
		}		
		
		return builder.toValueObjectDefinition();
	}
	
	@Override
	protected Column getColumnAnnot(Field field) {
		
		Class<?> startClass = this.owningEntityClass;
		
		String path = this.attributeFullName + "." + field.getName();

		while (path.length() > 0) {
			List<AttributeOverride> overrides = new ListBuilder<AttributeOverride>()
					.addNonNullElements(startClass.getAnnotation(AttributeOverride.class))
					.addNonNullElements(startClass.getAnnotation(AttributeOverrides.class) == null ? null : startClass.getAnnotation(AttributeOverrides.class).value())
					.toList();
			
			if (overrides.size() > 0) {
				for (AttributeOverride override : overrides) {
					if (override.name().equals(path)) {
						return override.column();
					}
				}
			}

			Pair<String, String> splitResult = ObjectUtils.splitPath(path, '.');
			String classFieldName = splitResult.getItem1();
			path = splitResult.getItem2();
			
			Field classField = ClassUtils.getField(startClass, classFieldName);

			AssertState.notNull(classField, "Cannot locate field %s for class %s.", classFieldName, startClass.getSimpleName());
			
			overrides = new ListBuilder<AttributeOverride>()
					.addNonNullElements(classField.getAnnotation(AttributeOverride.class))
					.addNonNullElements(classField.getAnnotation(AttributeOverrides.class) == null ? null : classField.getAnnotation(AttributeOverrides.class).value())
					.toList();

			if (overrides.size() > 0) {
				for (AttributeOverride override : overrides) {
					if (override.name().equals(path)) {
						return override.column();
					}
				}
			}
			
			startClass = classField.getType();
			
			if (path.equals(field.getName())) {
				break;
			}
		}
		
		return field.getAnnotation(Column.class);
	}
	
}
