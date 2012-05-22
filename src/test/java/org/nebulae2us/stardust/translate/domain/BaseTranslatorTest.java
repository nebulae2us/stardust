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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mockit.Expectations;
import mockit.NonStrict;

import org.junit.Before;
import org.nebulae2us.electron.Pair;
import org.nebulae2us.stardust.api.Query;
import org.nebulae2us.stardust.expr.domain.AttributeExpression;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.NamedParamExpression;
import org.nebulae2us.stardust.expr.domain.PredicateExpression;
import org.nebulae2us.stardust.expr.domain.WildcardExpression;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class BaseTranslatorTest {

	@NonStrict protected Query mockedSubQuery;
	
	@NonStrict protected TranslatorContext context;
	
	@NonStrict protected TranslatorController translatorController;
	
	protected ParamValues paramValues;
	
	private final List<Object> wildcardValues = new ArrayList<Object>();
	private final Map<String, Object> namedParamValues = new HashMap<String, Object>();
	
	protected Translator mockedAttributeTranslator;
	
	@Before
	public void setup() {
		
		paramValues = new ParamValues(null, null) {
			
			int index = 0;
			
			@Override
			public Object getNextWildcardValue() {
				return wildcardValues.get(index++);
			}

			@Override
			public Object getParamValue(String param) {
				if (!namedParamValues.containsKey(param)) {
					throw new IllegalStateException("Param is not defined: " + param + ". Make sure param is defined before translating the expression");
				}
				return namedParamValues.get(param);
			}
		};
		
		mockedAttributeTranslator = new Translator() {
			public Pair<String, List<?>> translate(TranslatorContext context, Expression expression, ParamValues paramValues) {
				return new Pair<String, List<?>>("mockedAttribute_" + expression.getExpression(), Collections.emptyList());
			}
			public boolean accept(Expression expression, ParamValues paramValues) {
				return expression instanceof AttributeExpression;
			}
		};

		new Expectations() {{
			mockedSubQuery.translate();
			result = new Pair<String, List<?>>("select ? from mockedSubQuery", Collections.singletonList("mockedSubQueryValue"));
			
			context.getMaxInListSize();
			result = 10;
			
			context.getTranslatorController();
			result = translatorController;
			
			translatorController.findTranslator(withInstanceOf(WildcardExpression.class), paramValues);
			result = new WildcardTranslator();

			translatorController.findTranslator(withInstanceOf(NamedParamExpression.class), paramValues);
			result = new NamedParamTranslator();

			translatorController.findTranslator(withInstanceOf(AttributeExpression.class), paramValues);
			result = mockedAttributeTranslator;
		}};
	}
	
	public class TestCase {
		
		private Translator translatorInTest;
		private Pair<String, List<?>> result;
		
		public TestCase withTranslator(Translator translatorInTest) {
			this.translatorInTest = translatorInTest;
			return this;
		}
		
		public TestCase assignParam(String param, Object value) {
			namedParamValues.put(param, value);
			return this;
		}
		
		public TestCase translate(String expression, Object ...values) {
			return _translate(expression, false, values);
		}
		
		public TestCase translateNegationOf(String expression, Object ... values) {
			return _translate(expression, true, values);
		}

		public TestCase _translate(String expression, boolean negated, Object[] values) {
			wildcardValues.clear();
			wildcardValues.addAll(Arrays.asList(values));
			
			PredicateExpression predicateExpression = PredicateExpression.parse(expression, negated);
			if (predicateExpression == null) {
				throw new IllegalStateException("Invalid expresssion for test: " + expression);
			}
			if (predicateExpression.countWildcardExpressions() != values.length) {
				throw new IllegalStateException("Values do not match wildcards in the expression: " + expression);
			}
			result = translatorInTest.translate(context, predicateExpression, paramValues);
			
			return this;
		}
		
		public TestCase expectResult(String expectedResult) {
			assertEquals(expectedResult, result.getItem1());
			return this;
		}
		
		public TestCase expectValues(Object ... expectedValues) {
			assertEquals( Arrays.asList(expectedValues), result.getItem2());
			return this;
		}
	}
	
}
