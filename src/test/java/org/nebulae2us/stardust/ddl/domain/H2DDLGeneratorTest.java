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
package org.nebulae2us.stardust.ddl.domain;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.stardust.dialect.H2Dialect;
import org.nebulae2us.stardust.jpa.group1.AbstractEntity;
import org.nebulae2us.stardust.jpa.group1.BedRoom;
import org.nebulae2us.stardust.jpa.group1.Bungalow;
import org.nebulae2us.stardust.jpa.group1.Castle;
import org.nebulae2us.stardust.jpa.group1.House;
import org.nebulae2us.stardust.jpa.group1.Kitchen;
import org.nebulae2us.stardust.jpa.group1.Person;
import org.nebulae2us.stardust.jpa.group1.Room;
import org.nebulae2us.stardust.my.domain.EntityRepository;

/**
 * @author Trung Phan
 *
 */
public class H2DDLGeneratorTest {

	private EntityRepository entityRepository = new EntityRepository();
	
	private H2DDLGenerator generator = new H2DDLGenerator(new H2Dialect());
	
	private List<Class<? extends AbstractEntity>> entityClasses = 
			Arrays.asList(Person.class, Room.class, BedRoom.class, Kitchen.class, House.class, Bungalow.class, Castle.class);
	
	@Before
	public void setup() {
		for (Class<? extends AbstractEntity> entityClass : entityClasses) {
			entityRepository.getEntity(entityClass);
		}
		
	}
	
	@Test
	public void create_person_table() throws Exception {

		generator.generateTable(entityRepository);

//		String ddl = generator.generateTable(entityRepository, Room.class);
//		
//		System.out.println(ddl);
	}
}
