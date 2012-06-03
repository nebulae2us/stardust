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

import java.awt.Color;
import java.util.Arrays;

import org.junit.Test;
import org.nebulae2us.stardust.def.domain.ValueObjectDefinition;
import org.nebulae2us.stardust.def.domain.ValueObjectDefinitionBuilder;
import org.nebulae2us.stardust.jpa.group1.House;
import org.nebulae2us.stardust.jpa.group1.Person;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class ValueObjectBuilderTest {

	@Test
	public void valueObjectDefinitionBuilder_should_be_able_to_define_column_for_scalarAttribute() {
		ValueObjectDefinition definition = new ValueObjectDefinitionBuilder(House.class, "color", Color.class)
			.mapAttribute("gender").toColumn("GENDER")
			.toValueObjectDefinition();
		
		assertEquals("GENDER", definition.getScalarAttributeColumnNames().get("gender"));
	}
	
//	@Test
//	public void valueObjectDefinitionBuilder_should_be_able_to_embed_attribute() {
//		ValueObjectDefinition definition = new ValueObjectDefinitionBuilder(Person.class)
//			.embedAttributes("name", "gender")
//			.toValueObjectDefinition();
//		
//		assertEquals(Arrays.asList("name", "gender"), definition.getEmbeddedAttributes());
//	}
//	
	@Test
	public void valueObjectDefinitionBuilder_should_be_able_to_define_not_nullable_attributes() {
		ValueObjectDefinition definition = new ValueObjectDefinitionBuilder(House.class, "color", Color.class)
			.notNullableAttributes("red", "green", "blue")
			.toValueObjectDefinition();
		
		assertEquals(Arrays.asList("red", "green", "blue"), definition.getNotNullableAttributes());
	}

	@Test
	public void valueObjectDefinitionBuilder_should_be_able_to_define_not_insertable_attributes() {
		ValueObjectDefinition definition = new ValueObjectDefinitionBuilder(House.class, "color", Color.class)
			.notInsertableAttributes("red")
			.toValueObjectDefinition();
		
		assertEquals(Arrays.asList("red"), definition.getNotInsertableAttributes());
	}

	@Test
	public void valueObjectDefinitionBuilder_should_be_able_to_define_not_updatable_attributes() {
		ValueObjectDefinition definition = new ValueObjectDefinitionBuilder(House.class, "color", Color.class)
			.notUpdatableAttributes("red", "green")
			.toValueObjectDefinition();
		
		assertEquals(Arrays.asList("red", "green"), definition.getNotUpdatableAttributes());
	}
	
	@Test
	public void valueObjectDefinitionBuilder_should_be_able_to_exclude_attributes() {
		ValueObjectDefinition definition = new ValueObjectDefinitionBuilder(House.class, "color", Color.class)
			.excludeAttributes("red", "green")
			.toValueObjectDefinition();
		
		assertEquals(Arrays.asList("red", "green"), definition.getExcludedAttributes());
	}

	
}
