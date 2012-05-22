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

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.electron.util.MapBuilder;
import org.nebulae2us.stardust.internal.util.StringUtils;

/**
 * @author Trung Phan
 *
 */
public class AnyAllExpression extends PredicateExpression {
	
	private final static Pattern PATTERN = Pattern.compile("(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?) *(=|!=|<>|>|!>|<|!<|>=|<=| eq | ne | lt | le | gt | ge ) *(all|any|some) +\\(([a-zA-Z0-9:_?, ]+)\\)");
	
	private final static List<String> VALID_OPERATORS = new ListBuilder<String>().add("=", "<>", ">", "<", ">=", "<=").toList();
	
	private final static Map<String, String> OPERATORS = new MapBuilder<String, String>()
			.put("=", "=").put("eq", "=")
			.put("<>", "<>").put("!=", "<>").put("ne", "<>")
			.put(">", ">").put("gt", ">")
			.put("<", "<").put("lt", "<")
			.put(">=", ">=").put("ge", ">=").put("!<", ">=")
			.put("<=", "<=").put("le", "<=").put("!>", "<=")
			.toMap();

	private final static List<String> VALID_QUANTIFIERS = Arrays.asList("all", "any");
	
	private final SelectorExpression leftOperand;

	private final List<SelectorExpression> params;
	
	private final String operator;
	
	private final String quantifier;
	
	public AnyAllExpression(boolean negated, String expression, SelectorExpression leftOperand, String operator, String quantifier, List<SelectorExpression> params) {
		super(negated, expression);
		this.leftOperand = leftOperand;
		this.params = Immutables.$(params);
		this.operator = operator;
		this.quantifier = quantifier;
		
		Assert.isTrue(VALID_OPERATORS.contains(operator), "Invalid operator \"%s\"", operator);
		Assert.isTrue(VALID_QUANTIFIERS.contains(quantifier), "Invalid quantifier \"%s\"", quantifier);
	}
	
	public static AnyAllExpression parse(String expression) {
		return parse(expression, false);
	}
	
	public static AnyAllExpression parse(String expression, boolean negated) {
		Matcher matcher = PATTERN.matcher(expression.trim());
		
		if (matcher.matches()) {
			
			String operator = OPERATORS.get(matcher.group(2).trim());
			
			String quantifier = matcher.group(3);
			if (quantifier.equals("some")) {
				quantifier = "any";
			}
			
			SelectorExpression leftOperand = SelectorExpression.parse(matcher.group(1).trim());
			AssertSyntax.notNull(leftOperand, "Syntax is invalid for %s in expression %s.", matcher.group(1), expression);
			
			List<SelectorExpression> params = new ArrayList<SelectorExpression>();

			List<String> paramStrings = StringUtils.splitFunctionInput(matcher.group(4).trim());
			for (String paramString : paramStrings) {
				SelectorExpression param = SelectorExpression.parse(paramString);
				AssertSyntax.notNull(param, "Syntax is invalid for %s in expression %s.", paramString, expression);
				
				params.add(param);
			}
			
			
			return new AnyAllExpression(negated, expression, leftOperand, operator, quantifier, params);
		}
		
		return null;
		
	}
	
	public SelectorExpression getLeftOperand() {
		return leftOperand;
	}

	public List<SelectorExpression> getParams() {
		return params;
	}
	
	public String getOperator() {
		return operator;
	}

	public String getQuantifier() {
		return quantifier;
	}

	@Override
	public String toString() {
		return "";
	}
	
	
	@Override
	public Iterator<? extends Expression> expressionIterator() {
		return new ExpressionIterator(this, new ListBuilder<Expression>().add(leftOperand).add(params).toList());
	}
}
