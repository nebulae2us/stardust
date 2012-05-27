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
package org.nebulae2us.stardust.sql.domain;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.jpa.group1.House;
import org.nebulae2us.stardust.jpa.group1.Room;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityRepository;

import static org.nebulae2us.stardust.Builders.*;
import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class EntityMappingWithJoinsTest {

	private EntityMappingRepository entityMappingRepository;
	private EntityRepository entityRepository;
	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
		this.entityMappingRepository = new EntityMappingRepository(this.entityRepository);
	}
	
	@Test
	public void map_room_and_house() {
		
		List<String> columnNames = Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "SEQUENCE_NUMBER", "H_HOUSE_ID", "H_HOUSE_LETTER");
		
		DataReader dataReader = new MockedDataReader(columnNames);
		
		Entity entity = entityRepository.getEntity(Room.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("h").name("house").joinType(JoinType.INNER_JOIN).toAliasJoin()));
		
		EntityMapping roomEntityMapping = this.entityMappingRepository.getEntityMapping(bundle, "", bundle.getLinkedEntity("").getEntity(), dataReader);
		assertEquals(1, roomEntityMapping.getIdentifierAttributeMappings().get(0).getColumnIndex());
		assertEquals(2, roomEntityMapping.getIdentifierAttributeMappings().get(1).getColumnIndex());
		assertEquals(3, roomEntityMapping.getIdentifierAttributeMappings().get(2).getColumnIndex());
		
		EntityMapping houseEntityMapping = this.entityMappingRepository.getEntityMapping(bundle, "h", bundle.getLinkedEntity("h").getEntity(), dataReader);
		
		assertEquals(4, houseEntityMapping.getIdentifierAttributeMappings().get(0).getColumnIndex());
		assertEquals(5, houseEntityMapping.getIdentifierAttributeMappings().get(1).getColumnIndex());
		
	}

	@Test
	public void map_room_and_house_borrow_column() {
		
		List<String> columnNames = Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "SEQUENCE_NUMBER", "H_HOUSE_ID");
		
		DataReader dataReader = new MockedDataReader(columnNames);
		
		Entity entity = entityRepository.getEntity(Room.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("h").name("house").joinType(JoinType.INNER_JOIN).toAliasJoin()));
		
		EntityMapping houseEntityMapping = this.entityMappingRepository.getEntityMapping(bundle, "h", bundle.getLinkedEntity("h").getEntity(), dataReader);

		assertEquals(2, houseEntityMapping.getIdentifierAttributeMappings().get(1).getColumnIndex());
		// this assertion means that instead of using column H_HOUSE_LETTER for the id of house entity, it use the column HOUSE_LETTER
		// instead because the column H_HOUSE_LETTER is missing
	}
	
	@Test
	public void map_room_and_house_remove_redundant_house_mapping() {
		List<String> columnNames = Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "SEQUENCE_NUMBER", "H_HOUSE_ID", "H_HOUSE_LETTER");
		
		DataReader dataReader = new MockedDataReader(columnNames);
		
		Entity entity = entityRepository.getEntity(Room.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("h").name("house").joinType(JoinType.INNER_JOIN).toAliasJoin()));
		
		EntityMapping roomEntityMapping = this.entityMappingRepository.getEntityMapping(bundle, "", bundle.getLinkedEntity("").getEntity(), dataReader);

		assertNull(roomEntityMapping.getAttributeMapping("house"));
		// this should be null because it's redundant. The alias h should already define the house entity for room.
	}

	@Test
	public void map_house_and_rooms_remove_redundant_house_mapping() {
		List<String> columnNames = Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "R_HOUSE_ID", "R_HOUSE_LETTER", "R_SEQUENCE_NUMBER");
		
		DataReader dataReader = new MockedDataReader(columnNames);
		
		Entity entity = entityRepository.getEntity(House.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("r").name("rooms").joinType(JoinType.LEFT_JOIN).toAliasJoin()));
		
		EntityMapping roomEntityMapping = this.entityMappingRepository.getEntityMapping(bundle, "r", bundle.getLinkedEntity("r").getEntity(), dataReader);

		assertNull(roomEntityMapping.getAttributeMapping("house"));
		// this should be null because it's redundant.
	}

}
