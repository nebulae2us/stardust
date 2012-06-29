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
package org.nebulae2us.stardust.api;

import java.util.Arrays;
import javax.persistence.Table;

import mockit.Mocked;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.stardust.DaoManager;
import org.nebulae2us.stardust.dao.JdbcExecutor;
import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.dialect.H2Dialect;
import org.nebulae2us.stardust.jpa.group1.House;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.translate.domain.CommonTranslatorController;
import org.nebulae2us.stardust.translate.domain.TranslatorController;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Trung Phan
 *
 */
public class QueryTest {

	private EntityRepository entityRepository;
	
	private TranslatorController controller;
	
	private DaoManager daoManager;
	
	@Mocked JdbcExecutor jdbcExecutor;
	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();

		this.controller = new CommonTranslatorController();
		this.daoManager = new DaoManager(jdbcExecutor, entityRepository, controller, new H2Dialect());
	}
	
	@Test
	public void createQuery() {

		daoManager.newQuery(House.class)
		.toQuery();
		
	}
	
	@Test
	public void filter_equal() {
		daoManager.newQuery(House.class)
		.distinct()
		.select("houseId.houseId")
		.filterBy()
			  .predicate("doorColor.opacity is null")
	    .and().predicate("houseId.houseLetter in (:letters)")
	    .and().predicate("houseId.houseLetter not between ? and ?", "a", "b")
		.and().group()
		         .predicate("houseId.houseLetter in (?)", Arrays.asList("a", "b", "c", "d", "e"))
		    .or().predicate(":age = ?", 1)
		.endGroup()
		.endFilter()
		.assignParam("letters", Arrays.asList(12, 13, 14))
		.assignParam("age", 15)
		.orderBy("doorColor.opacity")
		.firstResult(10)
		.maxResults(100)
		.toQuery();
	}
	
	@Test
	public void from_clause_should_have_pre_defined_schema_name() {
		
		@Table(schema="MY_SCHEMA")
		class Person {
			String name;
		}
		
		SqlBundle translateResult = daoManager.newQuery(Person.class)
			.toQuery()
			.translate();
		
		assertThat(translateResult.getSql(), equalToIgnoringWhiteSpace("select b.name as name from my_schema.person b"));
	}
	
	@Test
	public void from_clause_should_handle_empty_schema() {
		@Table(name="MY_PERSON")
		class Person {
			String name;
		}
		
		SqlBundle translateResult = daoManager.newQuery(Person.class)
			.toQuery()
			.translate();
		
		assertThat(translateResult.getSql(), equalToIgnoringWhiteSpace("select b.name as name from my_person b"));
	}

	
	@Test
	public void from_clause_should_handle_overriding_schema() {
		@Table(name="MY_PERSON", schema="MY_SCHEMA")
		class Person {
			String name;
		}
		
		SqlBundle translateResult = daoManager.newQuery(Person.class)
				.schema("MY_OTHER_SCHEMA")
				.toQuery()
				.translate();
		
		assertThat(translateResult.getSql(), equalToIgnoringWhiteSpace("select b.name as name from my_other_schema.my_person b"));
	}
	
	@Test
	public void from_clause_should_handle_default_schema() {
		this.daoManager = new DaoManager(jdbcExecutor, entityRepository, controller, new H2Dialect(), "DEFAULT_SCHEMA");

		@Table(name="MY_PERSON")
		class Person {
			String name;
		}
		
		SqlBundle translateResult = daoManager.newQuery(Person.class)
				.toQuery()
				.translate();
		
		assertThat(translateResult.getSql(), equalToIgnoringWhiteSpace("select b.name as name from default_schema.my_person b"));
		
	}
	
}
