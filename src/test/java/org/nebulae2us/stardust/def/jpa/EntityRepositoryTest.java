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
package org.nebulae2us.stardust.def.jpa;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.jpa.group1.*;
import org.nebulae2us.stardust.my.domain.*;

import static org.junit.Assert.*;


/**
 * @author Trung Phan
 *
 */
public class EntityRepositoryTest {

	private EntityRepository entityRepository;
	
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
		assertEquals("HOUSE_ID", linkedTables.get(1).getParentColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", linkedTables.get(1).getParentColumns().get(1).getName());
		assertEquals("SEQUENCE_NUMBER", linkedTables.get(1).getParentColumns().get(2).getName());
		assertEquals("HOUSE_ID", linkedTables.get(1).getColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", linkedTables.get(1).getColumns().get(1).getName());
		assertEquals("SEQUENCE_NUMBER", linkedTables.get(1).getColumns().get(2).getName());
		
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

		LinkedTable tableJoin1 = house.getLinkedTableBundle().getLinkedTables().get(1);
		
		assertEquals("HOUSE_ID", tableJoin1.getParentColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", tableJoin1.getParentColumns().get(1).getName());
		
		assertEquals("HOUSE_ID", tableJoin1.getColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", tableJoin1.getColumns().get(1).getName());

		LinkedTable tableJoin2 = house.getLinkedTableBundle().getLinkedTables().get(2);
		
		assertEquals("HOUSE_ID", tableJoin2.getParentColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", tableJoin2.getParentColumns().get(1).getName());
		
		assertEquals("HOUSE_ID", tableJoin2.getColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", tableJoin2.getColumns().get(1).getName());
		
	}
	
	@Test
	public void table_for_bungalow() {
		
		Entity bungalow = entityRepository.getEntity(Bungalow.class);
		LinkedTableBundle linkedTableBundle = bungalow.getLinkedTableBundle();
		List<LinkedTable> linkedTable = linkedTableBundle.getLinkedTables();
		
		assertEquals("HOUSE", linkedTableBundle.getRoot().getTable().getName());
		assertEquals(4, linkedTable.size());
		
		for (int i = 1; i < linkedTable.size(); i++) {
			assertEquals("HOUSE_ID", linkedTable.get(i).getParentColumns().get(0).getName());
			assertEquals("HOUSE_LETTER", linkedTable.get(i).getParentColumns().get(1).getName());
		}

		assertEquals("BUNGALOW_HOUSE_ID", linkedTable.get(3).getColumns().get(0).getName());
		assertEquals("BUNGALOW_HOUSE_LETTER", linkedTable.get(3).getColumns().get(1).getName());
		
		assertEquals("BUNGALOW", linkedTable.get(3).getTable().getName());
		
	}
	
	@Test
	public void room_to_house_many_to_one() {
		
		Entity room = entityRepository.getEntity(Room.class);
		
		EntityAttribute attribute = (EntityAttribute)room.getAttribute("house");
		
		assertEquals(2, attribute.getLeftColumns().size());
		assertEquals(2, attribute.getRightColumns().size());
		
		assertEquals("HOUSE_ID", attribute.getLeftColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", attribute.getLeftColumns().get(1).getName());

		assertEquals("HOUSE_ID", attribute.getRightColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", attribute.getRightColumns().get(1).getName());
		
	}
	
	
}
