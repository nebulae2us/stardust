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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.nebulae2us.stardust.ddl.domain.DDLGenerator;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.translate.domain.CommonTranslatorController;

import org.nebulae2us.stardust.DaoManager;

/**
 * @author Trung Phan
 *
 */
@RunWith(Parameterized.class)
public class OneEntityWithSequence_IT extends BaseIntegrationTest {
	private EntityRepository entityRepository;
	
	private DaoManager daoManager;
	
	private DDLGenerator ddlGenerator;

	public static class Person {
		
		@Id
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="person_seq")
		@SequenceGenerator(name="person_seq", sequenceName="person_seq")
		private Long id;
		
		private String firstName;
		
		private String lastName;
		
		private Date dateBorn;
		
		@Override public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || o.getClass() != this.getClass()) {
				return false;
			}
			Person p = (Person)o;
			return ObjectUtils.equal(this.firstName, p.firstName) &&
					ObjectUtils.equal(this.lastName, p.lastName) &&
					ObjectUtils.equal(this.dateBorn, p.dateBorn);
		}
		
		@Override public int hashCode() {
			return 0;
		}
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{"derby-in-memory"}, {"h2-in-memory"}, {"hsqldb-in-memory"}, {"mckoi-embedded"}
		});
	}
	
	public OneEntityWithSequence_IT(String config) {
		super(config);
	}

	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
		this.ddlGenerator = new DDLGenerator(dialect, entityRepository);
		this.entityRepository.scanEntities(Person.class);
		this.daoManager = new DaoManager(jdbcExecutor, entityRepository, new CommonTranslatorController(), dialect);
		
		List<String> ddls = ddlGenerator.generateCreateSchemaObjectsDDL();
		for (String ddl : ddls) {
			jdbcExecutor.execute(ddl);
		}
		
	}
	
	@After
	public void tearDown() throws Exception {
		List<String> ddls = ddlGenerator.generateDropSchemaObjectsDDL();
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
		person.dateBorn = new Date(0);
		
		daoManager.save(person);
		
		assertEquals(Long.valueOf(1), person.id);
		
		List<Person> people = daoManager.newQuery(Person.class).list();
		
		assertEquals(1, people.size());
		
	}
	
	@Test
	public void should_be_able_to_update_record() {
		
		
	}

}
