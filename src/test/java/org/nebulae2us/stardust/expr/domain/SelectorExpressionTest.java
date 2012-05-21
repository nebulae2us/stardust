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

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class SelectorExpressionTest {

	@Test
	public void attribute_expression() {
		SelectorExpression expr = SelectorExpression.parse("a.age");
		assertTrue(expr instanceof AttributeExpression);
		assertEquals("a.age", ((AttributeExpression)expr).getExpression());
	}
	
	@Test
	public void wildcard_expression() {
		SelectorExpression expr = SelectorExpression.parse("?");
		assertTrue(expr instanceof WildcardExpression);
	}

	@Test
	public void named_param_expression() {
		SelectorExpression expr = SelectorExpression.parse(":my_param");
		assertTrue(expr instanceof NamedParamExpression);
		assertEquals("my_param", ((NamedParamExpression)expr).getParamName());
	}

	@Test
	public void no_param_function_expression() {
		
		List<String> functionNames = Arrays.asList("currentDate", "currentTime", "currentTimestamp");
		
		for (String functionName : functionNames) {
			SelectorExpression expr = SelectorExpression.parse(functionName + "()");
			assertTrue(expr instanceof FunctionExpression);
			assertEquals(functionName, ((FunctionExpression)expr).getFunctionName());
			assertEquals(0, ((FunctionExpression)expr).getParams().size());
		}
	}
	
	@Test
	public void one_param_function_expression() {
		
		List<String> functionNames = Arrays.asList("abs", "exp", "ln", "sqrt", "ceiling", "floor", "day", "month", "year", "hour", "minute", "second", "length", "lower", "upper", "ltrim", "rtrim", "trim");
		
		for (String functionName : functionNames) {
			SelectorExpression expr = SelectorExpression.parse(functionName + "(a.age)");
			assertTrue(expr instanceof FunctionExpression);
			assertEquals(functionName, ((FunctionExpression)expr).getFunctionName());
			assertEquals(1, ((FunctionExpression)expr).getParams().size());
			
			SelectorExpression param = ((FunctionExpression)expr).getParams().get(0);
			assertTrue(param instanceof AttributeExpression);
			assertEquals("a.age", ((AttributeExpression)param).getExpression());
		}
	}
	
	@Test
	public void two_param_function_expression() {
		
		List<String> functionNames = Arrays.asList("mod", "power");
		
		for (String functionName : functionNames) {
			SelectorExpression expr = SelectorExpression.parse(functionName + "(a.age, ?)");
			assertTrue(expr instanceof FunctionExpression);
			assertEquals(functionName, ((FunctionExpression)expr).getFunctionName());
			assertEquals(2, ((FunctionExpression)expr).getParams().size());
			
			SelectorExpression param1 = ((FunctionExpression)expr).getParams().get(0);
			assertTrue(param1 instanceof AttributeExpression);
			assertEquals("a.age", ((AttributeExpression)param1).getExpression());
			
			SelectorExpression param2 = ((FunctionExpression)expr).getParams().get(1);
			assertTrue(param2 instanceof WildcardExpression);
		}
	}

	@Test
	public void function_concat_expression() {
	}
	
}
