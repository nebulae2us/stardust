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
import java.util.Collection;
import java.util.Collections;

import mockit.Expectations;
import mockit.NonStrict;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.nebulae2us.stardust.jpa.group1.*;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityRepository;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
@RunWith(Parameterized.class)
public class EntityMappingTest {

	@NonStrict DataReader dataReader;

	private EntityRepository entityRepository;
	private EntityMappingRepository mappingRepository;
	private String prefix;
	private String alias;
	
	public EntityMappingTest(String alias) {
		this.alias = alias;
		this.prefix = alias.length() == 0 ? "" : alias + '_';
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { "" }, { "a" }};
		return Arrays.asList(data);
	}	
	
	@Before
	public void setup() {
		entityRepository = new EntityRepository();
		mappingRepository = new EntityMappingRepository();
		
	}
	
	@Test
	public void map_person_identifier() {
		
		new Expectations() {{
			dataReader.findColumn(prefix + "SSN"); result = 1;
			dataReader.findColumn(prefix + "DATE_BORN"); result = 2;
			dataReader.findColumn(anyString); result = -1;
		}};

		Entity person = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(person, alias, Collections.EMPTY_LIST);
		EntityMapping personMapping = mappingRepository.getEntityMapping(bundle, alias, dataReader);

		
		assertNotNull(personMapping);
		assertEquals(2, personMapping.getIdentifierAttributeMappings().size());
		
		assertEquals(1, personMapping.getScalarAttributeMapping("ssn").getColumnIndex());
		assertEquals(2, personMapping.getScalarAttributeMapping("dateBorn").getColumnIndex());
		
	}
	
	@Test
	public void map_person_identifier_2() {
		new Expectations() {{
			dataReader.findColumn(prefix + "SSN"); result = 1;
			dataReader.findColumn(anyString); result = -1;
		}};

		Entity person = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(person, alias, Collections.EMPTY_LIST);
		EntityMapping personMapping = mappingRepository.getEntityMapping(bundle, alias, dataReader);

		assertNotNull(personMapping);
		assertEquals(0, personMapping.getIdentifierAttributeMappings().size());
		
	}

	
	@Test
	public void map_person_gender() {
		new Expectations() {{
			dataReader.findColumn(prefix + "GENDER"); result = 1;
			dataReader.findColumn(anyString); result = -1;
		}};
		
		Entity person = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(person, alias, Collections.EMPTY_LIST);
		EntityMapping personMapping = mappingRepository.getEntityMapping(bundle, alias, dataReader);
		
		assertEquals(1, personMapping.getAttributeMappings().size());
		assertEquals(1, personMapping.getScalarAttributeMapping("gender").getColumnIndex());
	}
	
	@Test
	public void map_person_basicName() {
		new Expectations() {{
			dataReader.findColumn(prefix + "FIRST_NAME"); result = 1;
			dataReader.findColumn(anyString); result = -1;
		}};
		
		Entity person = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(person, alias, Collections.EMPTY_LIST);
		EntityMapping personMapping = mappingRepository.getEntityMapping(bundle, alias, dataReader);
		
		assertEquals(1, personMapping.getAttributeMappings().size());
		assertEquals(1, personMapping.getValueObjectAttributeMapping("name").getAttributeMappings().size());
		assertEquals(1, personMapping.getValueObjectAttributeMapping("name").getScalarAttributeMapping("firstName").getColumnIndex());
	}
	
	@Test
	public void map_room_house() {
		new Expectations() {{
			dataReader.findColumn(prefix + "HOUSE_ID"); result = 1;
			dataReader.findColumn(prefix + "HOUSE_LETTER"); result = 2;
			dataReader.findColumn(anyString); result = -1;
		}};
		
		Entity room = entityRepository.getEntity(Room.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(room, alias, Collections.EMPTY_LIST);
		EntityMapping roomMapping = mappingRepository.getEntityMapping(bundle, alias, dataReader);

		EntityAttributeMapping houseAttributeMapping = roomMapping.getEntityAttributeMapping("house");
		assertNotNull(houseAttributeMapping);
		assertEquals(2, houseAttributeMapping.getIdentifierAttributeMappings().size());
		assertEquals(1, houseAttributeMapping.getAttributeMappings().size());
		
	}
	
	@Test
	public void map_passport_person() {
		new Expectations() {{
			dataReader.findColumn(prefix + "OWNER_SSN"); result = 1;
			dataReader.findColumn(prefix + "OWNER_DATE_BORN"); result = 2;
			dataReader.findColumn(anyString); result = -1;
		}};
		
		Entity passport = entityRepository.getEntity(Passport.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(passport, alias, Collections.EMPTY_LIST);
		EntityMapping passportMapping = mappingRepository.getEntityMapping(bundle, alias, dataReader);

		EntityAttributeMapping personAttributeMapping = passportMapping.getEntityAttributeMapping("owner");
		assertNotNull(personAttributeMapping);
	}
	
}
