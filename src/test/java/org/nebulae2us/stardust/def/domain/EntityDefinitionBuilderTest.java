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

import org.junit.Test;
import org.nebulae2us.stardust.def.domain.EntityDefinition;
import org.nebulae2us.stardust.def.domain.EntityDefinitionBuilder;
import org.nebulae2us.stardust.generator.SequenceValueGenerator;
import org.nebulae2us.stardust.jpa.group1.House;
import org.nebulae2us.stardust.jpa.group1.Passport;
import org.nebulae2us.stardust.jpa.group1.Person;
import org.nebulae2us.stardust.my.domain.InheritanceType;
import org.nebulae2us.stardust.my.domain.RelationalType;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class EntityDefinitionBuilderTest {

	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_table() {

		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.table("PERSON")
			.toEntityDefinition();
		
		assertEquals("PERSON", definition.getTableName());
		
	}
	
	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_secondary_table() {
		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.table("PERSON")
			.joinSecondaryTable("PERSON_DETAIL")
				.on("this.SSN = that.SSN",
					"this.DATE_BORN = that.DATE_BORN")
			.toEntityDefinition();
		
		assertEquals(1, definition.getSecondaryTables().size());
		assertEquals(Arrays.asList("this.SSN = that.SSN", "this.DATE_BORN = that.DATE_BORN"),
				definition.getSecondaryTables().get("PERSON_DETAIL")
				);
	}
	
	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_identifiers() {
		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.identifier("ssn", "dateBorn")
			.toEntityDefinition();
		
		assertEquals(Arrays.asList("ssn", "dateBorn"), definition.getIdentifiers());
	}
	
	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_column_for_scalarAttribute() {
		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.mapAttribute("gender").toColumn("GENDER")
			.toEntityDefinition();
		
		assertEquals("GENDER", definition.getScalarAttributeColumnNames().get("gender"));
	}
	
	@Test
	public void entityDefinitionBuilder_should_be_able_to_embed_attribute() {
		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.embedAttributes("name", "gender")
			.toEntityDefinition();
		
		assertEquals(Arrays.asList("name", "gender"), definition.getEmbeddedAttributes());
	}
	
	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_not_nullable_attributes() {
		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.notNullableAttributes("name", "gender")
			.toEntityDefinition();
		
		assertEquals(Arrays.asList("name", "gender"), definition.getNotNullableAttributes());
	}

	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_not_insertable_attributes() {
		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.notInsertableAttributes("name", "gender")
			.toEntityDefinition();
		
		assertEquals(Arrays.asList("name", "gender"), definition.getNotInsertableAttributes());
	}

	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_not_updatable_attributes() {
		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.notUpdatableAttributes("name", "gender")
			.toEntityDefinition();
		
		assertEquals(Arrays.asList("name", "gender"), definition.getNotUpdatableAttributes());
	}
	
	@Test
	public void entityDefinitionBuilder_should_be_able_to_exclude_attributes() {
		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.excludeAttributes("name", "gender")
			.toEntityDefinition();
		
		assertEquals(Arrays.asList("name", "gender"), definition.getExcludedAttributes());
	}

	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_one_to_one_relationship() {
		EntityDefinition definition = new EntityDefinitionBuilder(Passport.class)
			.oneToOneJoin("person").on("this.PERSON_SSN = that.SSN")
			.toEntityDefinition();
		
		assertEquals(1, definition.getRelationships().size());
		assertEquals(RelationalType.ONE_TO_ONE, definition.getRelationships().get("person").getRelationalType());
		assertEquals(Arrays.asList("this.PERSON_SSN = that.SSN"), definition.getRelationships().get("person").getJoins());
	}
	
	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_inverse_one_to_one_relationship() {
		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.oneToOneJoin("passport").mappedBy("owner")
			.toEntityDefinition();
		
		assertEquals(1, definition.getRelationships().size());
		assertEquals(RelationalType.ONE_TO_ONE, definition.getRelationships().get("passport").getRelationalType());
		assertEquals("owner", definition.getRelationships().get("passport").getMappedBy());
	}
	
	@Test
	public void entityDefinitionBuilder_should_be_table_to_define_many_to_many_relationship() {
		EntityDefinition definition = new EntityDefinitionBuilder(Person.class)
			.manyToManyJoin("house").usingJunctionTable("OWNER_DWELLING od")
				.on("this.SSN = od.OWNER_SSN",
					"this.DATE_BORN = od.DATE_BORN",
					"that.HOUSE_ID = od.DWELLING_HOUSE_ID",
					"that.HOUSE_LETTER = od.HOUSE_LETTER"
					)
			.toEntityDefinition();

		assertEquals(1, definition.getRelationships().size());
		assertEquals(RelationalType.MANY_TO_MANY, definition.getRelationships().get("house").getRelationalType());
		assertEquals("OWNER_DWELLING od", definition.getRelationships().get("house").getJunctionTableName());
	}

	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_inheritance() {
		
		EntityDefinition definition = new EntityDefinitionBuilder(House.class)
			.inheritanceType(InheritanceType.JOINED)
			.discriminatorColumn("HOUSE_TYPE_ID")
			.discriminatorValue(12)
			.toEntityDefinition();

		assertEquals(InheritanceType.JOINED, definition.getInheritanceType());
		assertEquals("HOUSE_TYPE_ID", definition.getDiscriminatorColumn());
		assertEquals(12, definition.getDiscriminatorValue());
		
	}
	
	@Test
	public void entityDefinitionBuilder_should_be_able_to_define_id_generator() {
		
		EntityDefinition definition = new EntityDefinitionBuilder(House.class)
			.identifierGenerator("houseId.houseId", new SequenceValueGenerator("house_seq"))
			.toEntityDefinition();
		
		assertTrue(definition.getIdentifierGenerators().get("houseId.houseId") instanceof SequenceValueGenerator);
		
	}

}
