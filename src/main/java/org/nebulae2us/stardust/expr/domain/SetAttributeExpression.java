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

import static org.nebulae2us.stardust.internal.util.BaseAssert.AssertSyntax;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nebulae2us.electron.util.ListBuilder;

/**
 * @author Trung Phan
 *
 */
public class SetAttributeExpression extends SetExpression {

	private final static Pattern PATTERN = Pattern.compile("([a-zA-Z0-9_.]+?) *= *(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?)");
	
	private final AttributeExpression leftOperandExpression;
	
	private final SelectorExpression rightOperandExpression;

	public SetAttributeExpression(String expression, AttributeExpression leftOperandExpression, SelectorExpression rightOperandExpression) {
		super(expression);

		this.leftOperandExpression = leftOperandExpression;
		this.rightOperandExpression = rightOperandExpression;
	}
	
	@Override
	public Iterator<Expression> expressionIterator() {
		return new ExpressionIterator(this, new ListBuilder<Expression>().add(leftOperandExpression).add(rightOperandExpression).toList());
	}
	
	public static SetExpression parse(String expression) {
		Matcher matcher = PATTERN.matcher(expression);
		if (matcher.matches()) {
			SelectorExpression param1 = SelectorExpression.parse(matcher.group(1).trim());
			AssertSyntax.isTrue(param1 instanceof AttributeExpression, "Invalid syntax: %s", expression);
			
			SelectorExpression param2 = SelectorExpression.parse(matcher.group(2).trim());
			AssertSyntax.notNull(param2, "Invalid syntax: %s", expression);
			
			return new SetAttributeExpression(expression, (AttributeExpression)param1, param2);
		}
		
		return null;
	}

	public AttributeExpression getLeftOperandExpression() {
		return leftOperandExpression;
	}

	public SelectorExpression getRightOperandExpression() {
		return rightOperandExpression;
	}
	

	
}
