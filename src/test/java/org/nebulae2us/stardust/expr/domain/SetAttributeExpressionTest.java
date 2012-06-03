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
package org.nebulae2us.stardust.expr.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class SetAttributeExpressionTest {

	@Test
	public void set_attribute_to_wildcard() {
		SetAttributeExpression setExpression = (SetAttributeExpression)SetExpression.parse("a.age = ?");

		assertEquals("a.age", ((AttributeExpression)setExpression.getLeftOperandExpression()).getExpression());
		assertTrue(setExpression.getRightOperandExpression() == WildcardExpression.getInstance());
	}

	@Test
	public void set_attribute_to_param() {
		SetAttributeExpression setExpression = (SetAttributeExpression)SetExpression.parse("a.age = :my_param");

		assertEquals("a.age", ((AttributeExpression)setExpression.getLeftOperandExpression()).getExpression());
		assertEquals("my_param", ((NamedParamExpression)setExpression.getRightOperandExpression()).getParamName() );
	}
	
	@Test
	public void set_attribute_to_other_attribute() {
		SetAttributeExpression setExpression = (SetAttributeExpression)SetExpression.parse("a.age = a.maxAge");
		
		assertEquals("a.age", ((AttributeExpression)setExpression.getLeftOperandExpression()).getExpression());
		assertEquals("a.maxAge", ((AttributeExpression)setExpression.getRightOperandExpression()).getExpression());
	}
	
	@Test
	public void set_attribute_to_function() {
		SetAttributeExpression setExpression = (SetAttributeExpression)SetExpression.parse("a.name = lower(a.name)");
		
		assertEquals("a.name", ((AttributeExpression)setExpression.getLeftOperandExpression()).getExpression());
		assertEquals("lower", ((FunctionExpression)setExpression.getRightOperandExpression()).getFunctionName());
		assertEquals(SelectorExpression.parse("a.name"), ((FunctionExpression)setExpression.getRightOperandExpression()).getParams().get(0)  );
	}
}
