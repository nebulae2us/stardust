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
package org.nebulae2us.stardust;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.stardust.OneEntity_H2_IT.Person;
import org.nebulae2us.stardust.ddl.domain.H2DDLGenerator;
import org.nebulae2us.stardust.dialect.H2Dialect;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.translate.domain.CommonTranslatorController;

import org.nebulae2us.stardust.DaoManager;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class OneEntity_H2_IT extends BaseIntegrationTest {

	private EntityRepository entityRepository;
	
	private DaoManager daoManager;

	public static class Person {
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long id;
		
		private String firstName;
		
		private String lastName;
		
		private Date dateBorn;
	}
	
	
	public OneEntity_H2_IT() {
		super("h2-in-memory");
	}

	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
		this.entityRepository.scanEntities(Person.class);
		this.daoManager = new DaoManager(jdbcExecutor, entityRepository, new CommonTranslatorController(), new H2Dialect());
		
		H2DDLGenerator schemaGenerator = new H2DDLGenerator();
		List<String> ddls = schemaGenerator.generateTable(entityRepository);
		for (String ddl : ddls) {
			jdbcExecutor.execute(ddl);
		}
	}
	
	@Test
	public void should_return_empty_for_blank_table() {
		
		List<Person> people =
		daoManager.newQuery(Person.class)
			.list();
		
		assertEquals(0, people.size());
		
	}
	
	@Test
	public void should_be_able_to_insert_one_record() {
		
		Person person = new Person();
		person.firstName = "First";
		person.lastName = "Last";
		person.dateBorn = new Date(100);
		
		daoManager.save(person);
		
		assertEquals(Long.valueOf(1), person.id);
	}
	
	@Test
	public void should_be_able_to_use_customized_sql() {

		should_be_able_to_insert_one_record();
		
		List<Person> people = daoManager.newQuery(Person.class)
			.backedBySql("select lower(first_name) first_name, last_name from person")
			.filterBy()
				.predicate("firstName = ?", "first")
			.endFilter()
			.list();

		assertEquals(1, people.size());
		
		Person person = people.get(0);
		assertNull(person.dateBorn);
		assertNull(person.id);
		assertEquals("first", person.firstName);
	}
	
	@Test
	public void should_be_able_to_count() {
		
		should_be_able_to_insert_one_record();
		
		long count = daoManager.newQuery(Person.class)
				.count();

		assertEquals(1, count);
		
	}
}
