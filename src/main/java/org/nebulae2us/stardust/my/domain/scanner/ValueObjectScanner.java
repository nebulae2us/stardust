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
import java.util.ArrayList;
import java.util.List;

import org.nebulae2us.stardust.Builders;
import org.nebulae2us.stardust.def.domain.DefinitionRepository;
import org.nebulae2us.stardust.def.domain.ValueObjectDefinition;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.FetchType;
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
	
	private final String owningFieldFullName;
	
	public ValueObjectScanner(EntityBuilder<?> owningEntity, Class<?> valueObjectClass, String owningFieldFullName) {
		this.owningEntity = owningEntity;
		this.valueObjectClass = valueObjectClass;
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
				
				ValueObjectDefinition voDefinition = DefinitionRepository.getInstance().getValueObjectDefinition(owningEntity.getDeclaringClass(), owningFieldFullName, relatedClass);
				
				Class<?> fieldClass = field.getType();
				
				if (voDefinition.getExcludedAttributes().contains(field.getName())) {
					continue;
				}
				
				if (ScannerUtils.isScalarType(field)) {
					
					ScalarAttributeBuilder<?> attributeBuilder = new ScalarAttributeScanner(this.owningEntity, field, this.owningFieldFullName, voDefinition).produce() ;
					
					result.attributes(attributeBuilder);
				}
				else if (voDefinition.getEmbeddedAttributes().contains(field.getName())) {
					String fullName = this.owningFieldFullName + "." + field.getName();
					ValueObjectBuilder<?> valueObjectBuilder = new ValueObjectScanner(this.owningEntity, fieldClass, fullName).produce();
					
					ValueObjectAttributeBuilder<?> attributeBuilder = valueObjectAttribute()
							.fullName(fullName)
							.field(field)
							.valueObject(valueObjectBuilder)
							.owningEntity(this.owningEntity)
							.fetchType(FetchType.EAGER)
							.insertable(true)
							.updatable(true)
							.nullable(true)
							;
					
					result.attributes(attributeBuilder);
				}
			}
		}	
		
		return result;
		
	}
	
}
