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
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.electron.util.ImmutableList;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.jpa.group1.BedRoom;
import org.nebulae2us.stardust.jpa.group1.House;
import org.nebulae2us.stardust.jpa.group1.Kitchen;
import org.nebulae2us.stardust.jpa.group1.Person;
import org.nebulae2us.stardust.jpa.group1.Room;
import org.nebulae2us.stardust.my.domain.EntityRepository;

import static org.nebulae2us.stardust.Builders.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.nebulae2us.electron.util.Immutables.*;

/**
 * @author Trung Phan
 *
 */
public class LinkedTableEntityBundleTest {
	
	private EntityRepository entityRepository;
	
	@Before
	public void setup() {
		entityRepository = new EntityRepository();
	}

	private ImmutableList<String> extractSelectColumns(String simpleSql) {
		String sql = simpleSql.trim().toLowerCase();

		int index = sql.indexOf("from");
		
		assertTrue(index > -1);
		
		String selectClause = sql.substring("select".length(), index).trim();
		
		return $(selectClause.split(","))
				.trimElement()
				.extractMatchFromElement(Pattern.compile("([^ ]+) (.*)"), 2);

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void select_person() {
		
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entityRepository.getEntity(Person.class), "b", Collections.EMPTY_LIST);
		
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle);

		List<String> columns = extractSelectColumns(linkedTableEntityBundle.toString());
		
		assertThat(columns, hasItems("b_ssn", "b_date_born", "b_gender", "b_first_name", "b_last_name", "b_version", "b_updated_date"));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void selectRoom() {

		entityRepository.getEntity(BedRoom.class);
		entityRepository.getEntity(Kitchen.class);

		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entityRepository.getEntity(Room.class), "", Collections.EMPTY_LIST);
		
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle);
		
		List<String> columns = extractSelectColumns(linkedTableEntityBundle.toString());

		assertThat(columns, hasItems("house_id", "house_letter", "sequence_number", "room_type", "red", "green", "blue", "door_count"));
	}

	@Test
	public void selectHouse() {
		
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entityRepository.getEntity(House.class), "", Collections.EMPTY_LIST);
		
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle);
		
		List<String> columns = extractSelectColumns(linkedTableEntityBundle.toString());
		
		assertThat(columns, hasItems("house_id", "house_letter", "house_type_id"));
		
	}
	
	
	@Test
	public void select_room_with_house() {

		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entityRepository.getEntity(Room.class), "",
				Arrays.asList(
						aliasJoin().alias("h").name("house").joinType(JoinType.INNER_JOIN).toAliasJoin())
				);
		
		
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle);
		
		List<String> columns = extractSelectColumns(linkedTableEntityBundle.toString());
		
		assertThat(columns, hasItems("house_id", "house_letter", "sequence_number", "h_house_id", "h_house_letter", "h_house_type_id"));
		
		
	}
	
	@Test
	public void select_house_with_rooms() {
		
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entityRepository.getEntity(House.class), "",
				Arrays.asList(
						aliasJoin().alias("r").name("rooms").joinType(JoinType.LEFT_JOIN).toAliasJoin())
				);
		
		
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle);
		
		List<String> columns = extractSelectColumns(linkedTableEntityBundle.toString());
		
		assertThat(columns, hasItems("house_id", "house_letter", "house_type_id", "r_house_id", "r_house_letter", "r_sequence_number"));
		
	}
	
	@Test
	public void select_person_with_houses() {
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entityRepository.getEntity(Person.class), "",
				Arrays.asList(
						aliasJoin().alias("h").name("houses").joinType(JoinType.INNER_JOIN).toAliasJoin())
				);
		
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle);

		List<String> columns = extractSelectColumns(linkedTableEntityBundle.toString());
		
		assertThat(columns, hasItems("h_house_id", "h_house_letter", "h_house_type_id"));
		
	}
	
	
}
