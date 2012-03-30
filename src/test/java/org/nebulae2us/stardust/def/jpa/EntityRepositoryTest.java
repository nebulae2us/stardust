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
		
		assertEquals("PERSON", person.getJoinedTables().getTable().getName());
		assertEquals("", person.getJoinedTables().getTable().getSchemaName());
	}

	@Test
	public void table_for_room() {
		Entity room = entityRepository.getEntity(Room.class);

		List<TableJoin> tableJoins = room.getJoinedTables().getTableJoins();
		
		assertEquals("ROOM", room.getJoinedTables().getTable().getName());
		assertEquals(1, tableJoins.size());
		assertTrue(room.getJoinedTables().getTable() == tableJoins.get(0).getLeftTable());
		assertEquals("ROOM_DETAIL", tableJoins.get(0).getRightTable().getName());

		assertEquals(3, tableJoins.get(0).getLeftColumns().size());
		assertEquals("HOUSE_ID", tableJoins.get(0).getLeftColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", tableJoins.get(0).getLeftColumns().get(1).getName());
		assertEquals("SEQUENCE_NUMBER", tableJoins.get(0).getLeftColumns().get(2).getName());
		assertEquals("HOUSE_ID", tableJoins.get(0).getRightColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", tableJoins.get(0).getRightColumns().get(1).getName());
		assertEquals("SEQUENCE_NUMBER", tableJoins.get(0).getRightColumns().get(2).getName());
		
	}
	
	@Test
	public void table_for_bedroom() {
		
		Entity bedRoom = entityRepository.getEntity(BedRoom.class);
		Entity room = entityRepository.getEntity(Room.class);
		
		assertEquals(room.getJoinedTables(), bedRoom.getJoinedTables());
		
	}
	
	@Test
	public void table_for_house() {
		Entity house = entityRepository.getEntity(House.class);
		
		assertEquals("HOUSE", house.getJoinedTables().getTable().getName());
		assertEquals(2, house.getJoinedTables().getTableJoins().size());

		TableJoin tableJoin0 = house.getJoinedTables().getTableJoins().get(0);
		
		assertEquals("HOUSE_ID", tableJoin0.getLeftColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", tableJoin0.getLeftColumns().get(1).getName());
		
		assertEquals("HOUSE_ID", tableJoin0.getRightColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", tableJoin0.getRightColumns().get(1).getName());

		TableJoin tableJoin1 = house.getJoinedTables().getTableJoins().get(1);
		
		assertEquals("HOUSE_ID", tableJoin1.getLeftColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", tableJoin1.getLeftColumns().get(1).getName());
		
		assertEquals("HOUSE_ID", tableJoin1.getRightColumns().get(0).getName());
		assertEquals("HOUSE_LETTER", tableJoin1.getRightColumns().get(1).getName());
		
	}
	
	@Test
	public void table_for_bungalow() {
		
		Entity bungalow = entityRepository.getEntity(Bungalow.class);
		JoinedTables joinedTables = bungalow.getJoinedTables();
		List<TableJoin> tableJoins = joinedTables.getTableJoins();
		
		assertEquals("HOUSE", joinedTables.getTable().getName());
		assertEquals(3, tableJoins.size());
		
		for (int i = 0; i < tableJoins.size(); i++) {
			assertEquals("HOUSE_ID", tableJoins.get(0).getLeftColumns().get(0).getName());
			assertEquals("HOUSE_LETTER", tableJoins.get(0).getLeftColumns().get(1).getName());
		}

		assertEquals("BUNGALOW_HOUSE_ID", tableJoins.get(2).getRightColumns().get(0).getName());
		assertEquals("BUNGALOW_HOUSE_LETTER", tableJoins.get(2).getRightColumns().get(1).getName());
		
		assertEquals("BUNGALOW", tableJoins.get(2).getRightTable().getName());
		
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
