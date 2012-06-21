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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.junit.Test;
import org.nebulae2us.stardust.def.domain.AnnotationEntityDefinitionBuilder;
import org.nebulae2us.stardust.def.domain.EntityDefinition;
import org.nebulae2us.stardust.def.domain.RelationshipDefinition;
import org.nebulae2us.stardust.generator.IdentityValueRetriever;
import org.nebulae2us.stardust.generator.SequenceIdentifierGenerator;
import org.nebulae2us.stardust.my.domain.RelationalType;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
//@SuppressWarnings("unused")
public class AnnotationEntityDefinitionBuilderTest {

	@Test
	public void builder_should_recognize_table_name() {
		
		@Table(name = "STUDENT")
		class Student {			
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Student.class)
		.toEntityDefinition();
		
		assertEquals("STUDENT", definition.getTableName());
	}
	
	@Test
	public void builder_should_recognize_secondary_tables() {
		
		@Entity
		@Table(name="HOUSE")
		@SecondaryTables({
			@SecondaryTable(name="HOUSE_INTERIOR_DETAIL", pkJoinColumns={
					@PrimaryKeyJoinColumn(name="IN_HOUSE_ID", referencedColumnName="HOUSE_ID"),
					@PrimaryKeyJoinColumn(name="IN_HOUSE_LETTER", referencedColumnName="HOUSE_LETTER")}),
			@SecondaryTable(name="HOUSE_EXTERIOR_DETAIL")
		})
		class House {
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(House.class)
		.toEntityDefinition();
		
		assertEquals("HOUSE", definition.getTableName());
		assertEquals(2, definition.getSecondaryTables().size());
		
		List<String> secondaryTableJoins1 = definition.getSecondaryTables().get("HOUSE_INTERIOR_DETAIL");
		assertNotNull(secondaryTableJoins1);
		assertEquals(2, secondaryTableJoins1.size());
		assertEquals(Arrays.asList("this.HOUSE_ID = that.IN_HOUSE_ID", "this.HOUSE_LETTER = that.IN_HOUSE_LETTER"), secondaryTableJoins1);

		assertTrue(definition.getSecondaryTables().containsKey("HOUSE_EXTERIOR_DETAIL"));
		assertEquals(0, definition.getSecondaryTables().get("HOUSE_EXTERIOR_DETAIL").size());
	}
	
	@Test
	public void builder_should_recognize_table_and_schema_name() {

		@Table(name = "STUDENT", schema = "MY_SCHEMA")
		class Student {			
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Student.class)
		.toEntityDefinition();
		
		assertEquals("MY_SCHEMA.STUDENT", definition.getTableName());
		
	}
	
	@Test
	public void builder_should_recognize_basic_identifier() {
		class Student {
			@Id
			private Long studentId;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Student.class)
		.toEntityDefinition();
		
		assertEquals(Arrays.asList("studentId"), definition.getIdentifiers());
	}
	
	@Test
	public void builder_should_recognize_embedded_identifier() {
		class StudentId {
			private Long studentId;
		}
		
		class Student {
			@EmbeddedId
			private StudentId studentId;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Student.class)
		.toEntityDefinition();
		
		assertEquals(Arrays.asList("studentId"), definition.getIdentifiers());
		assertEquals(Arrays.asList("studentId"), definition.getEmbeddedAttributes());
		
	}
	
	@Test
	public void builder_should_recognize_multiple_keys_for_identifier() {
		class HouseId {
			private Long houseNumber;
			private String houseLetter;
		}
		class Room {
			@Id
			private Long roomNumber;
			@EmbeddedId
			private HouseId houseId;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Room.class)
		.toEntityDefinition();
		
		assertEquals(Arrays.asList("roomNumber", "houseId"), definition.getIdentifiers());
		assertEquals(Arrays.asList("houseId"), definition.getEmbeddedAttributes());
		
	}
	
	@Test
	public void builder_should_recognize_column_name_for_scalar_attribute() {
		
		class Student {
			@Column(name = "STUDENT_NAME")
			private String name;

			@Column
			private String gender;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Student.class)
		.toEntityDefinition();
		
		assertEquals("STUDENT_NAME", definition.getScalarAttributeColumnNames().get("name"));
	}
	
	@Test
	public void builder_should_recognize_not_nullable_for_scalar_attribute() {
		
		class Student {
			@Column(nullable = false)
			private String name;
			
			@Column
			private String gender;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Student.class)
		.toEntityDefinition();
		
		assertEquals(Arrays.asList("name"), definition.getNotNullableAttributes());
	}

	@Test
	public void builder_should_recognize_not_insertable_for_scalar_attribute() {
		
		class Student {
			@Column(insertable = false)
			private String name;

			@Column
			private String gender;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Student.class)
		.toEntityDefinition();
		
		assertEquals(Arrays.asList("name"), definition.getNotInsertableAttributes());
	}

	@Test
	public void builder_should_recognize_not_updatable_for_scalar_attribute() {
		
		class Student {
			@Column(updatable = false)
			private String name;
			
			@Column
			private String gender;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Student.class)
		.toEntityDefinition();
		
		assertEquals(Arrays.asList("name"), definition.getNotUpdatableAttributes());
	}

	@Test
	public void builder_should_recognize_transient_attribute() {
		class Student {
			private transient String firstName;
			
			@Transient
			private String lastName;
			
			private String gender;
		}
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Student.class)
		.toEntityDefinition();
		
		assertEquals(Arrays.asList("firstName", "lastName", "this$0"), definition.getExcludedAttributes());
		
	}
	
	@Test
	public void builder_should_recognize_one_to_one_relationship() {
		class Person {
			
		}
		
		class Passport {
			@OneToOne
			private Person owner;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Passport.class)
		.toEntityDefinition();
		
		RelationshipDefinition relationship = definition.getRelationships().get("owner");
		assertNotNull(relationship);
		assertEquals(RelationalType.ONE_TO_ONE, relationship.getRelationalType());
		assertEquals(0, relationship.getJoins().size());
	}

	@Test
	public void builder_should_recognize_one_to_one_relationship_with_mappedBy() {
		class Person {
			
		}
		
		class Passport {
			@OneToOne(mappedBy = "passport")
			private Person owner;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Passport.class)
		.toEntityDefinition();
		
		RelationshipDefinition relationship = definition.getRelationships().get("owner");
		assertNotNull(relationship);
		assertEquals(RelationalType.ONE_TO_ONE, relationship.getRelationalType());
		assertEquals(0, relationship.getJoins().size());
		assertEquals("passport", relationship.getMappedBy());
	}

	@Test
	public void builder_should_recognize_discrimininator_column() {
		@DiscriminatorColumn(name = "HOUSE_TYPE_ID", discriminatorType=DiscriminatorType.INTEGER)
		class House {
			
		}

		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(House.class)
		.toEntityDefinition();
	
		assertEquals("HOUSE_TYPE_ID", definition.getDiscriminatorColumn());
	}

	@Test
	public void builder_should_recognize_discrimininator_value() {
		@DiscriminatorColumn(name = "HOUSE_TYPE_ID", discriminatorType=DiscriminatorType.INTEGER)
		@DiscriminatorValue("12")
		class House {
			
		}

		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(House.class)
		.toEntityDefinition();
	
		assertEquals("HOUSE_TYPE_ID", definition.getDiscriminatorColumn());
		assertEquals(12, definition.getDiscriminatorValue());
	}

	@Test
	public void builder_should_recognize_inheritance_type() {
		@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
		class House {
			
		}

		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(House.class)
		.toEntityDefinition();

		assertEquals(org.nebulae2us.stardust.my.domain.InheritanceType.SINGLE_TABLE, definition.getInheritanceType());
	}
	
	@Test
	public void builder_should_recognize_identity_identifier_generator() {
		
		class Person {
			@Id
			@GeneratedValue(strategy=GenerationType.IDENTITY)
			private Long id;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Person.class)
		.toEntityDefinition();

		assertEquals(1, definition.getIdentifierGenerators().size());
		assertTrue(definition.getIdentifierGenerators().get("id") instanceof IdentityValueRetriever);
	}
	
	@Test
	public void builder_should_recognize_sequence_identifier_generator_defined_in_field() {
		
		class Person {
			@Id
			@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="person_seq_name")
			@SequenceGenerator(name="person_seq_name", sequenceName="person_seq")
			private Long id;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Person.class)
		.toEntityDefinition();

		assertEquals(1, definition.getIdentifierGenerators().size());
		SequenceIdentifierGenerator sequenceIdentifierGenerator = (SequenceIdentifierGenerator)definition.getIdentifierGenerators().get("id");
		assertEquals("person_seq", sequenceIdentifierGenerator.getName());
	}

	@Test
	public void builder_should_recognize_sequence_identifier_generator_defined_in_class() {
		
		@SequenceGenerator(name="person_seq_name", sequenceName="person_seq")
		class Person {
			@Id
			@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="person_seq_name")
			private Long id;
		}
		
		EntityDefinition definition = new AnnotationEntityDefinitionBuilder(Person.class)
		.toEntityDefinition();

		assertEquals(1, definition.getIdentifierGenerators().size());
		SequenceIdentifierGenerator sequenceIdentifierGenerator = (SequenceIdentifierGenerator)definition.getIdentifierGenerators().get("id");
		assertEquals("person_seq", sequenceIdentifierGenerator.getName());
	}
	
}
