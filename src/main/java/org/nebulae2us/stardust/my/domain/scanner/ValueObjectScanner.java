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

import static org.nebulae2us.stardust.Builders.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Transient;

import org.nebulae2us.electron.Constants;
import org.nebulae2us.stardust.Builders;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.ScalarAttributeBuilder;
import org.nebulae2us.stardust.my.domain.ValueObjectAttributeBuilder;
import org.nebulae2us.stardust.my.domain.ValueObjectBuilder;

/**
 * 
 * This class will scan the Embedded field to return ValueObject class
 * 
 * @author Trung Phan
 *
 */
public class ValueObjectScanner {

	private final EntityBuilder<?> owningEntity;
	
	private final Class<?> valueObjectClass;
	
	private final AttributeOverrideScanner attributeOverrideScanner;
	
	private final String owningFieldFullName;
	
	public ValueObjectScanner(EntityBuilder<?> owningEntity, Class<?> valueObjectClass, String owningFieldFullName, AttributeOverrideScanner attributeOverrideScanner) {
		this.owningEntity = owningEntity;
		this.valueObjectClass = valueObjectClass;
		this.attributeOverrideScanner = attributeOverrideScanner;
		this.owningFieldFullName = owningFieldFullName;
	}
	
	public ValueObjectBuilder<?> produce() {
		
		ValueObjectBuilder<?> result = Builders.valueObject()
				.declaringClass(valueObjectClass)
				;
		
		List<Class<?>> relatedClasses = new ArrayList<Class<?>>();
		relatedClasses.add(valueObjectClass);
		Class<?> c = valueObjectClass;
		while ( (c = c.getSuperclass()) != null) {
			if (c.getAnnotation(javax.persistence.Embeddable.class) != null) {
				relatedClasses.add(c);
			}
		}
		
		
		for (Class<?> relatedClass : relatedClasses) {
			
			for (Field field : relatedClass.getDeclaredFields()) {
				
				Class<?> fieldClass = field.getType();
				
				if ((field.getModifiers() & Modifier.TRANSIENT) != 0 || field.getAnnotation(Transient.class) != null) {
					continue;
				}
				
				if (Constants.SCALAR_TYPES.contains(fieldClass) || fieldClass.isEnum()) {
					
					ScalarAttributeBuilder<?> attributeBuilder = new ScalarAttributeScanner(this.owningEntity, field, this.owningFieldFullName).produce() ;
					
					if (this.attributeOverrideScanner.getColumns().containsKey(field.getName())) {
						attributeBuilder.column(this.attributeOverrideScanner .getColumns().get(field.getName()));
					}
					
					result.attributes(attributeBuilder);
				}
				else if (field.getAnnotation(Embedded.class) != null || field.getAnnotation(EmbeddedId.class) != null || fieldClass.getAnnotation(Embeddable.class) != null) {
					String fullName = this.owningFieldFullName + "." + field.getName();
					ValueObjectBuilder<?> valueObjectBuilder = new ValueObjectScanner(this.owningEntity, fieldClass, fullName, this.attributeOverrideScanner.sub(field.getName())).produce();
					
					ValueObjectAttributeBuilder<?> attributeBuilder = valueObjectAttribute()
							.fullName(fullName)
							.field(field)
							.valueObject(valueObjectBuilder)
							.owningEntity(this.owningEntity)
							;
					
					result.attributes(attributeBuilder);
				}
			}
		}	
		
		return result;
		
	}
	
}
