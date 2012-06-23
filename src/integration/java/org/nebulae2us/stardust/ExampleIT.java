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

import java.util.List;

import javax.persistence.Id;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.stardust.ddl.domain.DDLGenerator;
import org.nebulae2us.stardust.my.domain.EntityRepository;

/**
 * @author Trung Phan
 *
 */
public class ExampleIT extends BaseIntegrationTest {

	private DaoManager daoManager;

	private DDLGenerator ddlGenerator;

	public ExampleIT() {
		super("h2-in-memory");
	}
	
	@Before
	public void setup() {
		this.daoManager = new DaoManager(dataSource, dialect);
		this.ddlGenerator = new DDLGenerator(dialect, daoManager.getEntityRepository());
	}
	
	@Test
	public void example1() {
		class Person {
			@Id
			private Long personId;
			
			private String firstName;
			private String lastName;
			private Integer age;
		}
		
		this.daoManager.getEntityRepository().scanEntities(Person.class);
		
		List<String> ddls = ddlGenerator.generateCreateSchemaObjectsDDL();
		for (String ddl : ddls) {
			jdbcExecutor.execute(ddl);
		}
	
		List<Person> allPeople = daoManager.newQuery(Person.class).list();
		
		List<Person> children = daoManager.newQuery(Person.class)
				.filterBy("age < ?", 18)
				.list();
		
		List<Person> toddler = daoManager.newQuery(Person.class)
				.filterBy("age between ? and ?", 1, 3)
				.list();

		List<Person> first10Eldest = daoManager.newQuery(Person.class)
				.maxResults(10)
				.orderBy("age desc")
				.list();
		
		List<Person> next10Eldest = daoManager.newQuery(Person.class)
				.firstResult(10)
				.maxResults(10)
				.orderBy("age desc")
				.list();
		
		
		ddls = ddlGenerator.generateDropSchemaObjectsDDL();
		for (String ddl : ddls) {
			jdbcExecutor.execute(ddl);
		}
		
	}

	
	
}
