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
package org.nebulae2us.stardust.my.domain;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.electron.ElementContext;
import org.nebulae2us.electron.Function1;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.jpa.group1.*;
import org.nebulae2us.stardust.my.domain.*;

import static org.junit.Assert.*;


/**
 * @author Trung Phan
 *
 */
public class Group1EntityRepositoryTest {

	private EntityRepository entityRepository;
	
	private final Function1<String, ElementContext<Column>> EXTRACT_COLUMN_NAME = new Function1<String, ElementContext<Column>>() {
		public String execute(ElementContext<Column> context) {
			return context.getElement().getName();
		}};
		
	private final Function1<String, ElementContext<Column>> EXTRACT_TABLE_NAME = new Function1<String, ElementContext<Column>>() {
		public String execute(ElementContext<Column> context) {
			return context.getElement().getTable().getName();
		}};
	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
		
	}
	
	@Test
	public void scan_entities() {
		entityRepository.getEntity(Bungalow.class);
		
		List<Class<?>> unexpectedEntityClasses = Arrays.asList(new Class<?>[] {BasicName.class, Color.class, Gender.class, HouseId.class, RoomType.class, TranslucentColor.class});

		for (Entity entity : entityRepository.entities.values()) {
			assertFalse(unexpectedEntityClasses.contains(entity.getDeclaringClass()));
		}
	}
	
	
	@Test
	public void house_entity_has_identifer() {
		
		Entity house = entityRepository.getEntity(House.class);
		
		assertNotNull(house.getEntityIdentifier());
		assertEquals(1, house.getEntityIdentifier().getAttributes().size());

	}
	
	@Test
	public void person_entity_has_identifier() {
		Entity person = entityRepository.getEntity(Person.class);
		
		assertNotNull(person.getEntityIdentifier());
		assertEquals(2, person.getEntityIdentifier().getAttributes().size());
	}
	
	@Test
	public void bungalow_entity_has_root_entity() {
		Entity house = entityRepository.getEntity(House.class);
		Entity bungalow = entityRepository.getEntity(Bungalow.class);
		
		assertTrue(bungalow.getRootEntity() == house);
	}
	
	@Test
	public void bungalow_entity_has_identifier() {
		Entity bungalow = entityRepository.getEntity(Bungalow.class);
		
		assertNotNull(bungalow.getEntityIdentifier());
	}
	
	@Test
	public void table_for_person_entity() {
		Entity person = entityRepository.getEntity(Person.class);
		
		assertEquals("PERSON", person.getLinkedTableBundle().getRoot().getTable().getName());
		assertEquals("", person.getLinkedTableBundle().getRoot().getTable().getSchemaName());
	}

	@Test
	public void table_for_room() {
		Entity room = entityRepository.getEntity(Room.class);

		List<LinkedTable> linkedTables = room.getLinkedTableBundle().getLinkedTables();
		
		assertEquals("ROOM", room.getLinkedTableBundle().getRoot().getTable().getName());
		assertEquals(2, linkedTables.size());
		assertTrue(room.getLinkedTableBundle().getRoot().getTable() == linkedTables.get(1).getParent().getTable());
		assertEquals("ROOM_DETAIL", linkedTables.get(1).getTable().getName());

		assertEquals(3, linkedTables.get(1).getParentColumns().size());
		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "SEQUENCE_NUMBER"), linkedTables.get(1).getParentColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("ROOM", "ROOM", "ROOM"), linkedTables.get(1).getParentColumns().toList(EXTRACT_TABLE_NAME));
		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "SEQUENCE_NUMBER"), linkedTables.get(1).getColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("ROOM_DETAIL", "ROOM_DETAIL", "ROOM_DETAIL"), linkedTables.get(1).getColumns().toList(EXTRACT_TABLE_NAME));
	}
	
	@Test
	public void table_for_bedroom() {
		
		Entity bedRoom = entityRepository.getEntity(BedRoom.class);
		Entity room = entityRepository.getEntity(Room.class);
		
		assertEquals(room.getLinkedTableBundle(), bedRoom.getLinkedTableBundle());
		
	}
	
	@Test
	public void table_for_house() {
		Entity house = entityRepository.getEntity(House.class);
		
		assertEquals("HOUSE", house.getLinkedTableBundle().getRoot().getTable().getName());
		assertEquals(3, house.getLinkedTableBundle().getLinkedTables().size());

		LinkedTable linkedTable1 = house.getLinkedTableBundle().getLinkedTables().get(1);
		assertEquals("HOUSE_INTERIOR_DETAIL", linkedTable1.getTable().getName());
		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER"), linkedTable1.getParentColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("HOUSE", "HOUSE"), linkedTable1.getParentColumns().toList(EXTRACT_TABLE_NAME));
		assertEquals(Arrays.asList("IN_HOUSE_ID", "IN_HOUSE_LETTER"), linkedTable1.getColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("HOUSE_INTERIOR_DETAIL", "HOUSE_INTERIOR_DETAIL"), linkedTable1.getColumns().toList(EXTRACT_TABLE_NAME));
		
		LinkedTable linkedTable2 = house.getLinkedTableBundle().getLinkedTables().get(2);
		assertEquals("HOUSE_EXTERIOR_DETAIL", linkedTable2.getTable().getName());
		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER"), linkedTable2.getParentColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("HOUSE", "HOUSE"), linkedTable2.getParentColumns().toList(EXTRACT_TABLE_NAME));
		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER"), linkedTable2.getColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("HOUSE_EXTERIOR_DETAIL", "HOUSE_EXTERIOR_DETAIL"), linkedTable2.getColumns().toList(EXTRACT_TABLE_NAME));
		
	}
	
	@Test
	public void table_for_bungalow() {
		
		Entity bungalow = entityRepository.getEntity(Bungalow.class);
		LinkedTableBundle linkedTableBundle = bungalow.getLinkedTableBundle();
		List<LinkedTable> linkedTables = linkedTableBundle.getLinkedTables();
		
		assertEquals("HOUSE", linkedTableBundle.getRoot().getTable().getName());
		assertEquals(4, linkedTables.size());
		
		for (LinkedTable linkedTable : linkedTableBundle.getNonRoots()) {
			assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER"), linkedTable.getParentColumns().toList(EXTRACT_COLUMN_NAME));			
			assertEquals(Arrays.asList("HOUSE", "HOUSE"), linkedTable.getParentColumns().toList(EXTRACT_TABLE_NAME));
		}
		
		assertEquals(Arrays.asList("BUNGALOW_HOUSE_ID", "BUNGALOW_HOUSE_LETTER"), linkedTables.get(3).getColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("BUNGALOW", "BUNGALOW"), linkedTables.get(3).getColumns().toList(EXTRACT_TABLE_NAME));
		
		assertEquals("BUNGALOW", linkedTables.get(3).getTable().getName());
		
	}
	
	@Test
	public void bungalow_attributes_should_belong_to_table_BUNGALOW_by_default() {
		Entity bungalow = entityRepository.getEntity(Bungalow.class);
		
		ScalarAttribute attribute = (ScalarAttribute)bungalow.getAttribute("bungalowStyle");
		assertEquals("BUNGALOW", attribute.getColumn().getTable().getName());
		
	}
	
	@Test
	public void bungalow_house_attributes_should_belong_to_table_HOUSE_by_default() {
		Entity bungalow = entityRepository.getEntity(Bungalow.class);
		
		ScalarAttribute attribute = (ScalarAttribute)bungalow.getAttribute("version");
		assertEquals("HOUSE", attribute.getColumn().getTable().getName());
		
	}
	
	@Test
	public void passport_version_override() {
		Entity passport = entityRepository.getEntity(Passport.class);
		ScalarAttribute attribute = (ScalarAttribute)passport.getAttribute("version");
		
		assertNotNull(attribute);
		assertEquals("PASSPORT_VERSION", attribute.getColumn().getName());
		assertEquals("PASSPORT", attribute.getColumn().getTable().getName());
	}
	
	@Test
	public void room_to_house_many_to_one() {
		
		Entity room = entityRepository.getEntity(Room.class);
		
		EntityAttribute attribute = (EntityAttribute)room.getAttribute("house");

		assertEquals(RelationalType.MANY_TO_ONE, attribute.getRelationalType());
		assertEquals(JoinType.INNER_JOIN, attribute.getJoinType());
		assertTrue(attribute.isOwningSide());
		
		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER"), attribute.getLeftColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("ROOM", "ROOM"), attribute.getLeftColumns().toList(EXTRACT_TABLE_NAME));
		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER"), attribute.getRightColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("HOUSE", "HOUSE"), attribute.getRightColumns().toList(EXTRACT_TABLE_NAME));
		
		assertEquals(0, attribute.getJunctionLeftColumns().size());
		assertEquals(0, attribute.getJunctionRightColumns().size());
	}

	@Test
	public void house_to_room_one_to_many() {
		Entity house = entityRepository.getEntity(House.class);
		
		EntityAttribute attribute = (EntityAttribute)house.getAttribute("rooms");
		
		assertEquals(RelationalType.ONE_TO_MANY, attribute.getRelationalType());
		assertEquals(JoinType.LEFT_JOIN, attribute.getJoinType());

		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER"), attribute.getLeftColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("HOUSE", "HOUSE"), attribute.getLeftColumns().toList(EXTRACT_TABLE_NAME));
		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER"), attribute.getRightColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("ROOM", "ROOM"), attribute.getRightColumns().toList(EXTRACT_TABLE_NAME));

		assertEquals(0, attribute.getJunctionLeftColumns().size());
		assertEquals(0, attribute.getJunctionRightColumns().size());
	}
	
	@Test
	public void person_to_house_many_to_many() {
		Entity person = entityRepository.getEntity(Person.class);
		
		EntityAttribute attribute = (EntityAttribute)person.getAttribute("houses");
		
		assertEquals(RelationalType.MANY_TO_MANY, attribute.getRelationalType());
		assertEquals(JoinType.LEFT_JOIN, attribute.getJoinType());
		
		assertEquals(Arrays.asList("SSN", "DATE_BORN"), attribute.getLeftColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("PERSON", "PERSON"), attribute.getLeftColumns().toList(EXTRACT_TABLE_NAME));
		assertEquals(Arrays.asList("OWNER_SSN", "OWNER_DATE_BORN"), attribute.getJunctionLeftColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("OWNER_DWELLING", "OWNER_DWELLING"), attribute.getJunctionLeftColumns().toList(EXTRACT_TABLE_NAME));
		
		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER"), attribute.getRightColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("HOUSE", "HOUSE"), attribute.getRightColumns().toList(EXTRACT_TABLE_NAME));
		assertEquals(Arrays.asList("DWELLING_HOUSE_ID", "DWELLING_HOUSE_LETTER"), attribute.getJunctionRightColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("OWNER_DWELLING", "OWNER_DWELLING"), attribute.getJunctionRightColumns().toList(EXTRACT_TABLE_NAME));
	}
	
	@Test
	public void house_to_person_many_to_many() {
		Entity house = entityRepository.getEntity(House.class);
		
		EntityAttribute attribute = (EntityAttribute)house.getAttribute("owners");
		
		assertEquals(RelationalType.MANY_TO_MANY, attribute.getRelationalType());
		assertEquals(JoinType.LEFT_JOIN, attribute.getJoinType());

		assertEquals(Arrays.asList("HOUSE_ID", "HOUSE_LETTER"), attribute.getLeftColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("HOUSE", "HOUSE"), attribute.getLeftColumns().toList(EXTRACT_TABLE_NAME));
		assertEquals(Arrays.asList("DWELLING_HOUSE_ID", "DWELLING_HOUSE_LETTER"), attribute.getJunctionLeftColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("OWNER_DWELLING", "OWNER_DWELLING"), attribute.getJunctionLeftColumns().toList(EXTRACT_TABLE_NAME));

		assertEquals(Arrays.asList("SSN", "DATE_BORN"), attribute.getRightColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("PERSON", "PERSON"), attribute.getRightColumns().toList(EXTRACT_TABLE_NAME));
		assertEquals(Arrays.asList("OWNER_SSN", "OWNER_DATE_BORN"), attribute.getJunctionRightColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("OWNER_DWELLING", "OWNER_DWELLING"), attribute.getJunctionRightColumns().toList(EXTRACT_TABLE_NAME));
		
	}
	
	@Test
	public void passport_to_person_one_to_one() {
		Entity passport = entityRepository.getEntity(Passport.class);
		
		EntityAttribute attribute = (EntityAttribute)passport.getAttribute("owner");
		
		assertEquals(RelationalType.ONE_TO_ONE, attribute.getRelationalType());
		assertEquals(JoinType.INNER_JOIN, attribute.getJoinType());
		assertTrue(attribute.isOwningSide());
		
		assertEquals(Arrays.asList("OWNER_SSN", "OWNER_DATE_BORN"), attribute.getLeftColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("PASSPORT", "PASSPORT"), attribute.getLeftColumns().toList(EXTRACT_TABLE_NAME));

		assertEquals(Arrays.asList("SSN", "DATE_BORN"), attribute.getRightColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("PERSON", "PERSON"), attribute.getRightColumns().toList(EXTRACT_TABLE_NAME));
		
		assertEquals(0, attribute.getJunctionLeftColumns().size());
		assertEquals(0, attribute.getJunctionRightColumns().size());
	}

	@Test
	public void person_to_passport_one_to_one() {
		Entity person = entityRepository.getEntity(Person.class);
		
		EntityAttribute attribute = (EntityAttribute)person.getAttribute("passport");
		
		assertEquals(RelationalType.ONE_TO_ONE, attribute.getRelationalType());
		assertEquals(JoinType.LEFT_JOIN, attribute.getJoinType());
		assertFalse(attribute.isOwningSide());
		
		assertEquals(Arrays.asList("SSN", "DATE_BORN"), attribute.getLeftColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("PERSON", "PERSON"), attribute.getLeftColumns().toList(EXTRACT_TABLE_NAME));

		assertEquals(Arrays.asList("OWNER_SSN", "OWNER_DATE_BORN"), attribute.getRightColumns().toList(EXTRACT_COLUMN_NAME));
		assertEquals(Arrays.asList("PASSPORT", "PASSPORT"), attribute.getRightColumns().toList(EXTRACT_TABLE_NAME));

		assertEquals(0, attribute.getJunctionLeftColumns().size());
		assertEquals(0, attribute.getJunctionRightColumns().size());
	}

}
