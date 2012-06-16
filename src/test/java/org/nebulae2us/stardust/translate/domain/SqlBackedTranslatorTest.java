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
package org.nebulae2us.stardust.translate.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.electron.Converter;
import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.dialect.Dialect;
import org.nebulae2us.stardust.dialect.H2Dialect;
import org.nebulae2us.stardust.expr.domain.PredicateExpression;
import org.nebulae2us.stardust.jpa.group1.Person;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.sql.domain.AliasJoin;
import org.nebulae2us.stardust.sql.domain.AliasJoinBuilder;
import org.nebulae2us.stardust.sql.domain.LinkedEntityBundle;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;

import static org.nebulae2us.stardust.internal.util.Builders.*;
import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class SqlBackedTranslatorTest {
	
	private EntityRepository entityRepository;
	
	private TranslatorController translatorController;
	
	private TranslatorContext context;
	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
		
		this.translatorController = new CommonTranslatorController();

		Entity person = this.entityRepository.getEntity(Person.class);
		List<AliasJoinBuilder<?>> aliasJoinBuilders = new ListBuilder<AliasJoinBuilder<?>>()
			.add(
				aliasJoin().name("houses").alias("h").joinType(JoinType.LEFT_JOIN),
				aliasJoin().name("h.rooms").alias("r").joinType(JoinType.LEFT_JOIN),
				aliasJoin().name("h.owners").alias("o").joinType(JoinType.LEFT_JOIN)
			).toList();
		
		List<AliasJoin> aliasJoins = new Converter(DESTINATION_CLASS_RESOLVER, true).convert(aliasJoinBuilders).toListOf(AliasJoin.class);
		
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(person, "", aliasJoins);
		
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle, true);
		
		context = new TranslatorContext(new H2Dialect(), translatorController, linkedTableEntityBundle, linkedEntityBundle, true);
	
	
	}

	
	private Pair<String, List<?>> filter(String expression, Object ... values) {
		ParamValues paramValues = new ParamValues(new HashMap<String, Object>(), Arrays.asList(values));
		
		PredicateExpression expr = PredicateExpression.parse(expression, false);
		
		Translator translator = context.getTranslatorController().findTranslator(expr, paramValues);
		
		Pair<String, List<?>> result = translator.translate(context, expr, paramValues);
		
		return result;
	}

	@Test
	public void primary_attribute() {

		Pair<String, List<?>> pair = filter("name.firstName in (?)", "John");
		assertEquals("FIRST_NAME in (?)", pair.getItem1());
		assertEquals(Arrays.asList("John"), pair.getItem2());
		
	}
	
	@Test
	public void referenced_attribute() {

		Pair<String, List<?>> pair = filter("h.color.red = ?", 12);
		assertEquals("h_HOUSE_COLOR_RED = ?", pair.getItem1());
		assertEquals(Arrays.asList(12), pair.getItem2());
		
	}
	
}
