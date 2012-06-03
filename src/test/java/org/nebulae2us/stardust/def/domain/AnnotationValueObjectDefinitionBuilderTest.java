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

import java.util.Arrays;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;

import org.junit.Test;
import org.nebulae2us.stardust.def.domain.AnnotationValueObjectDefinitionBuilder;
import org.nebulae2us.stardust.def.domain.ValueObjectDefinition;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class AnnotationValueObjectDefinitionBuilderTest {

	@Test
	public void builder_should_recognize_column_name_for_scalar_attribute() {
		
		class Name {
			@Column(name="FIRST_NAME")
			private String firstName;
			
			private String lastName;
		}

		class Student {
			private Name name;
		}
		
		ValueObjectDefinition definition = new AnnotationValueObjectDefinitionBuilder(Student.class, "name", Name.class)
		.toValueObjectDefinition();
		
		assertEquals("FIRST_NAME", definition.getScalarAttributeColumnNames().get("firstName"));
	}
	
	@Test
	public void builder_should_recognize_not_nullable_for_scalar_attribute() {
		
		class Name {
			@Column(name="FIRST_NAME", nullable = false)
			private String firstName;
			
			private String lastName;
		}

		class Student {
			private Name name;
		}
		
		ValueObjectDefinition definition = new AnnotationValueObjectDefinitionBuilder(Student.class, "name", Name.class)
		.toValueObjectDefinition();
		
		assertEquals(Arrays.asList("firstName"), definition.getNotNullableAttributes());
	}

	
	@Test
	public void builder_should_recognize_overriden_attributes() {
		
		class Name {
			@Column(name="FIRST_NAME", nullable = false)
			private String firstName;
			
			private String lastName;
		}

		class Student {
			@AttributeOverride(name="firstName", column=@Column(name = "STUDENT_FIRST_NAME", insertable = false))
			private Name name;
		}
		
		ValueObjectDefinition definition = new AnnotationValueObjectDefinitionBuilder(Student.class, "name", Name.class)
		.toValueObjectDefinition();
		
		assertEquals("STUDENT_FIRST_NAME", definition.getScalarAttributeColumnNames().get("firstName"));
		assertEquals(0, definition.getNotNullableAttributes().size());
		assertEquals(Arrays.asList("firstName"), definition.getNotInsertableAttributes());
	}
	
}

