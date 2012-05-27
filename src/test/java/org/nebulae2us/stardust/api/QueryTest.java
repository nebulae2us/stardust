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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mockit.Mocked;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.stardust.dao.domain.JdbcOperation;
import org.nebulae2us.stardust.jpa.group1.House;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.translate.domain.AttributeTranslator;
import org.nebulae2us.stardust.translate.domain.BetweenTranslator;
import org.nebulae2us.stardust.translate.domain.ComparisonTranslator;
import org.nebulae2us.stardust.translate.domain.InListTranslator;
import org.nebulae2us.stardust.translate.domain.IsNullTranslator;
import org.nebulae2us.stardust.translate.domain.LogicalTranslator;
import org.nebulae2us.stardust.translate.domain.NamedParamTranslator;
import org.nebulae2us.stardust.translate.domain.OrderTranslator;
import org.nebulae2us.stardust.translate.domain.QueryTranslator;
import org.nebulae2us.stardust.translate.domain.Translator;
import org.nebulae2us.stardust.translate.domain.TranslatorController;
import org.nebulae2us.stardust.translate.domain.WildcardTranslator;

/**
 * @author Trung Phan
 *
 */
public class QueryTest {

	private EntityRepository entityRepository;
	
	private TranslatorController controller;
	
	private QueryManager queryManager;
	
	@Mocked JdbcOperation jdbcOperation;
	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
		List<Translator> translators = new ArrayList<Translator>();
		translators.add(new LogicalTranslator());
		translators.add(new AttributeTranslator());
		translators.add(new ComparisonTranslator());
		translators.add(new InListTranslator());
		translators.add(new BetweenTranslator());
		translators.add(new IsNullTranslator());
		translators.add(new WildcardTranslator());
		translators.add(new NamedParamTranslator());
		translators.add(new OrderTranslator());
		translators.add(new QueryTranslator());

		this.controller = new TranslatorController(translators);
		this.queryManager = new QueryManager(jdbcOperation, entityRepository, controller);
	}
	
	@Test
	public void createQuery() {

		queryManager.newQuery(House.class)
		.toQuery();
		
	}
	
	@Test
	public void filter_equal() {
		queryManager.newQuery(House.class)
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
	
}
