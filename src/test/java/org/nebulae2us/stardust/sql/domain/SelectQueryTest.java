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

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.stardust.jpa.group1.BedRoom;
import org.nebulae2us.stardust.jpa.group1.Kitchen;
import org.nebulae2us.stardust.jpa.group1.Person;
import org.nebulae2us.stardust.jpa.group1.Room;
import org.nebulae2us.stardust.my.domain.EntityRepository;

import static org.nebulae2us.stardust.Builders.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Trung Phan
 *
 */
public class SelectQueryTest {
	
	private EntityRepository entityRepository;
	
	@Before
	public void setup() {
		entityRepository = new EntityRepository();
	}

	
	@Test
	public void select_person() {
		
		SelectQuery selectQuery = selectQuery()
			.entityClass(Person.class)
			.toSelectQuery();
		
		SelectQueryParseResult parseResult = selectQuery.toSelectSql(entityRepository);

		assertThat(parseResult.toString(), equalToIgnoringWhiteSpace(
				"select ssn, date_born, first_name, last_name, gender, version, updated_date from person"));
		
	}
	
	@Test
	public void selectRoom() {

		entityRepository.getEntity(BedRoom.class);
		entityRepository.getEntity(Kitchen.class);
		
		SelectQuery selectQuery = selectQuery()
				.entityClass(Room.class)
				.toSelectQuery();
			
			SelectQueryParseResult parseResult = selectQuery.toSelectSql(entityRepository);

			assertThat(parseResult.toString(), equalToIgnoringWhiteSpace(
					"select ssn, date_born, first_name, last_name, gender, version, updated_date from person"));

	}
	
	
	
	
}
