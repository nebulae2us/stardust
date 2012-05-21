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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.electron.util.MapBuilder;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class ComparisonExpression extends PredicateExpression {

	private final static Pattern PATTERN = Pattern.compile("(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?) *(=|!=|>|<|>=|<=| eq | ne | lt | le | gt | ge ) *(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?)");
	
	private final static List<String> OPERATORS1 = Arrays.asList("=", "!=", ">", "<", ">=", "<=");
	private final static List<String> OPERATORS2 = Arrays.asList("eq", "ne", "gt", "lt", "ge", "le");
	private final static Map<String, String> NEGATED_OPERATORS = new MapBuilder<String, String>()
			.put("=", "!=").put("!=", "=")
			.put(">", "<=").put("<=", "<")
			.put("<", ">=").put(">=", "<")
			.toMap();
	
	private final String operator;
	
	private final SelectorExpression leftOperandExpression;
	
	private final SelectorExpression rightOperandExpression;

	public ComparisonExpression(Mirror mirror) {
		super(mirror);

		this.operator = mirror.toString("operator");
		this.leftOperandExpression = mirror.to(SelectorExpression.class, "leftOperandExpression");
		this.rightOperandExpression = mirror.to(SelectorExpression.class, "rightOperandExpression");
		
		assertInvarant();
	}
	
	public ComparisonExpression(boolean negated, String expression, String operator, SelectorExpression leftOperandExpression, SelectorExpression rightOperandExpression) {
		super(negated, expression);
		this.operator = operator;
		this.leftOperandExpression = leftOperandExpression;
		this.rightOperandExpression = rightOperandExpression;
		
		assertInvarant();
	}
	
	private void assertInvarant() {
		AssertSyntax.isTrue(OPERATORS1.contains(operator), "Unrecognized operator %s.", operator);
		AssertSyntax.notNull(leftOperandExpression, "leftOperandExpression cannot be null.");
		AssertSyntax.notNull(rightOperandExpression, "rightOperandExpression cannot be null.");
	}

	

	public String getOperator() {
		return operator;
	}

	public SelectorExpression getLeftOperandExpression() {
		return leftOperandExpression;
	}

	public SelectorExpression getRightOperandExpression() {
		return rightOperandExpression;
	}
	
	@Override
	public Iterator<Expression> expressionIterator() {
		return new ExpressionIterator(this, new ListBuilder<Expression>().add(leftOperandExpression).add(rightOperandExpression).toList());
	}
	
	public static ComparisonExpression parse(String expression) {
		return parse(expression, false);
	}
	
	public static ComparisonExpression parse(String expression, boolean negated) {
		Matcher matcher = PATTERN.matcher(expression);
		if (matcher.matches()) {
			SelectorExpression param1 = SelectorExpression.parse(matcher.group(1).trim());
			SelectorExpression param2 = SelectorExpression.parse(matcher.group(3).trim());
			
			String operator = matcher.group(2).trim();
			int index = OPERATORS2.indexOf(operator);
			if (index > -1) {
				operator = OPERATORS1.get(index);
			}
			if (negated) {
				operator = NEGATED_OPERATORS.get(operator);
			}
			return new ComparisonExpression(false, expression, operator, param1, param2);
		}
		
		return null;
	}
}
