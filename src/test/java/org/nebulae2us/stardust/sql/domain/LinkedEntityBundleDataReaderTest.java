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

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.jpa.group1.House;
import org.nebulae2us.stardust.jpa.group1.Passport;
import org.nebulae2us.stardust.jpa.group1.Person;
import org.nebulae2us.stardust.jpa.group1.Room;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityRepository;

import static org.junit.Assert.*;
import static org.nebulae2us.stardust.Builders.*;

/**
 * @author Trung Phan
 *
 */
public class LinkedEntityBundleDataReaderTest {

	private EntityRepository entityRepository;
	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
	}
	
	@Test
	public void read_no_records() {

		DataReader dataReader = new MockedDataReader(Arrays.asList("SSN", "DATE_BORN"));
		
		Entity entity = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Collections.EMPTY_LIST);

		assertEquals(0, bundle.readData(dataReader).size());
		
	}
	
	@Test
	public void read_one_person() {
		List<String> columnNames = Arrays.asList("SSN", "DATE_BORN");
		List<Object[]> data = new ListBuilder<Object[]>()
				.add(new Object[]{"123-12-1234", new Date(1000)})
				.toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Collections.EMPTY_LIST);

		List<Person> persons = (List<Person>)bundle.readData(dataReader);
		assertEquals(1, persons.size());
		
		assertEquals("123-12-1234", persons.get(0).getSsn());
		assertEquals(new Date(1000), persons.get(0).getDateBorn());
	}
	
	@Test
	public void read_two_persons() {
		List<String> columnNames = Arrays.asList("SSN", "DATE_BORN");
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{"123-12-0001", new Date(1001)},
				new Object[]{"123-12-0002", new Date(1002)}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Collections.EMPTY_LIST);

		List<Person> persons = (List<Person>)bundle.readData(dataReader);
		assertEquals(2, persons.size());
		
		assertEquals("123-12-0001", persons.get(0).getSsn());
		assertEquals(new Date(1001), persons.get(0).getDateBorn());
		assertEquals("123-12-0002", persons.get(1).getSsn());
		assertEquals(new Date(1002), persons.get(1).getDateBorn());
	}

	@Test
	public void read_person_collapse_records() {
		List<String> columnNames = Arrays.asList("SSN", "DATE_BORN");
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{"123-12-0001", new Date(1001)},
				new Object[]{"123-12-0001", new Date(1001)}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Collections.EMPTY_LIST);

		List<Person> persons = (List<Person>)bundle.readData(dataReader);
		assertEquals(1, persons.size());
		
		assertEquals("123-12-0001", persons.get(0).getSsn());
		assertEquals(new Date(1001), persons.get(0).getDateBorn());
	}
	
	@Test
	public void read_person_with_names() {
		
		List<String> columnNames = Arrays.asList("SSN", "DATE_BORN", "FIRST_NAME", "LAST_NAME");
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{"123-12-0001", new Date(1001), "John", "Deer"}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Collections.EMPTY_LIST);
		
		List<Person> persons = (List<Person>)bundle.readData(dataReader);
		
		assertEquals(1, persons.size());
		
		Person person = persons.get(0);
		
		assertNotNull(person.getName());
		assertEquals("John", person.getName().getFirstName());
		assertEquals("Deer", person.getName().getLastName());
		
	}
	
	@Test
	public void read_room_with_house() {
		
		List<String> columnNames = Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "SEQUENCE_NUMBER");
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{1, "a", 1}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Room.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Collections.EMPTY_LIST);
		
		List<Room> rooms = (List<Room>)bundle.readData(dataReader);
		
		assertEquals(1, rooms.size());
		Room room = rooms.get(0);
		assertNotNull(room.getHouseId());
		assertEquals(1, room.getHouseId().getHouseId());
		assertEquals("a", room.getHouseId().getHouseLetter());
		assertEquals(1, room.getRoomSequence());
		
		assertNotNull(room.getHouse());
		assertEquals(1, room.getHouse().getHouseId().getHouseId());
		assertEquals("a", room.getHouse().getHouseId().getHouseLetter());
	}
	
	@Test
	public void read_rooms_with_shared_house() {
		
		List<String> columnNames = Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "SEQUENCE_NUMBER");
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{1, "a", 1},
				new Object[]{1, "a", 2},
				new Object[]{1, "a", 3}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Room.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Collections.EMPTY_LIST);
		
		List<Room> rooms = (List<Room>)bundle.readData(dataReader);
		
		assertEquals(3, rooms.size());
		assertEquals(2, rooms.get(1).getRoomSequence());
		
		assertTrue(rooms.get(0).getHouse() == rooms.get(1).getHouse());
	}
	
	@Test
	public void read_room_and_house() {
		List<String> columnNames = Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "SEQUENCE_NUMBER", "H_HOUSE_ID", "H_HOUSE_LETTER", "H_WINDOW_COUNT");
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{1, "a", 1, 1, "a", 3}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Room.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("h").joinType(JoinType.INNER_JOIN).name("house").toAliasJoin()));
		
		List<Room> rooms = (List<Room>)bundle.readData(dataReader);
		
		assertEquals(1, rooms.size());
		Room room = rooms.get(0);
		House house = room.getHouse();
		assertEquals(3, house.getWindowCount());
		assertEquals(1, house.getRooms().size());
		assertTrue(house.getRooms().get(0) == room);
		
	}

	@Test
	public void read_rooms_and_house() {
		List<String> columnNames = Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "SEQUENCE_NUMBER", "H_HOUSE_ID", "H_HOUSE_LETTER", "H_WINDOW_COUNT");
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{1, "a", 1, 1, "a", 3},
				new Object[]{1, "a", 2, 1, "a", 3},
				new Object[]{1, "a", 3, 1, "a", 3}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Room.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("h").joinType(JoinType.INNER_JOIN).name("house").toAliasJoin()));
		
		List<Room> rooms = (List<Room>)bundle.readData(dataReader);
		
		assertEquals(3, rooms.size());
		Room room = rooms.get(0);
		House house = room.getHouse();
		assertEquals(3, house.getWindowCount());
		assertEquals(3, house.getRooms().size());
		assertTrue(house == rooms.get(1).getHouse());
		assertTrue(house == rooms.get(2).getHouse());
		assertTrue(house.getRooms().get(0) == rooms.get(0));
		assertTrue(house.getRooms().get(1) == rooms.get(1));
		assertTrue(house.getRooms().get(2) == rooms.get(2));
		
	}

	@Test
	public void read_house_and_rooms() {
		List<String> columnNames = Arrays.asList("HOUSE_ID", "HOUSE_LETTER", "R_HOUSE_ID", "R_HOUSE_LETTER", "R_SEQUENCE_NUMBER");
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{1, "a", 1, "a", 1},
				new Object[]{1, "a", 1, "a", 2},
				new Object[]{1, "a", 1, "a", 3}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(House.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("r").joinType(JoinType.INNER_JOIN).name("rooms").toAliasJoin()));
		
		List<House> houses = (List<House>)bundle.readData(dataReader);
		
		assertEquals(1, houses.size());
		
		House house = houses.get(0);
		
		assertEquals(3, house.getRooms().size());
		assertTrue(house == house.getRooms().get(0).getHouse());
		assertEquals(1, house.getRooms().get(0).getRoomSequence());
		assertTrue(house == house.getRooms().get(1).getHouse());
		assertEquals(2, house.getRooms().get(1).getRoomSequence());
		assertTrue(house == house.getRooms().get(2).getHouse());
		assertEquals(3, house.getRooms().get(2).getRoomSequence());
		
	}
	
	@Test
	public void read_person_and_passport() {
		List<String> columnNames = Arrays.asList("SSN", "DATE_BORN", "PP_PASSPORT_NUMBER");
		
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{"123-12-0001", new Date(1001), 1}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("pp").name("passport").joinType(JoinType.INNER_JOIN).toAliasJoin()));
		
		List<Person> people = (List<Person>)bundle.readData(dataReader);
		
		assertEquals(1, people.size());
		Person person = people.get(0);
		Passport passport = person.getPassport();
		assertNotNull(passport);
		assertTrue(person.getPassport().getOwner() == person);
	}
	
	@Test
	public void read_passport_and_person() {
		List<String> columnNames = Arrays.asList("PASSPORT_NUMBER", "O_SSN", "O_DATE_BORN");
		
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{1, "123-12-0001", new Date(1001)}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Passport.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("o").name("owner").joinType(JoinType.INNER_JOIN).toAliasJoin()));
		
		List<Passport> passports = (List<Passport>)bundle.readData(dataReader);
		
		assertEquals(1, passports.size());
		Passport passport = passports.get(0);
		Person person = passport.getOwner();
		assertNotNull(person);
		assertTrue(passport.getOwner().getPassport() == passport);
	}
	
	@Test
	public void read_person_house() {
		List<String> columnNames = Arrays.asList("SSN", "DATE_BORN", "H_HOUSE_ID", "H_HOUSE_LETTER");
		
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{"123-12-0001", new Date(1001), 1, "a"}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("h").name("houses").joinType(JoinType.INNER_JOIN).toAliasJoin()));
		
		List<Person> people = (List<Person>)bundle.readData(dataReader);

		assertEquals(1, people.size());
		
		Person person  = people.get(0);
		
		assertEquals(1, person.getHouses().size());
		
		House house = person.getHouses().get(0);
		
		assertTrue(person == house.getOwners().get(0));
	}

	@Test
	public void read_people_houses() {
		List<String> columnNames = Arrays.asList("SSN", "DATE_BORN", "H_HOUSE_ID", "H_HOUSE_LETTER");
		
		List<Object[]> data = new ListBuilder<Object[]>().add(
				new Object[]{"123-12-0001", new Date(1001), 1, "a"},
				new Object[]{"123-12-0001", new Date(1001), 1, "b"},
				new Object[]{"123-12-0002", new Date(1002), 1, "a"},
				new Object[]{"123-12-0002", new Date(1002), 1, "c"}
				).toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);

		Entity entity = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", 
				Arrays.asList(aliasJoin().alias("h").name("houses").joinType(JoinType.INNER_JOIN).toAliasJoin()));
		
		List<Person> people = (List<Person>)bundle.readData(dataReader);

		assertEquals(2, people.size());
		assertEquals(2, people.get(0).getHouses().get(0).getOwners().size());
		
	}


}
