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

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nebulae2us.stardust.internal.util.ObjectUtils;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class LikeExpression extends PredicateExpression {

	private final static Pattern PATTERN = Pattern.compile("(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?) +(not +)?like +(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?)");
	
	private final SelectorExpression leftOperand;

	private final SelectorExpression rightOperand;

	public LikeExpression(boolean negated, String expression, SelectorExpression leftOperand, SelectorExpression rightOperand) {
		super(negated, expression);

		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
		
		Assert.notNull(this.leftOperand, "leftOperand cannot be null");
		Assert.notNull(rightOperand, "rightOperand cannot be null");
	}

	public SelectorExpression getLeftOperand() {
		return leftOperand;
	}

	public SelectorExpression getRightOperand() {
		return rightOperand;
	}

	public static LikeExpression parse(String expression, boolean negated) {
		Matcher matcher = PATTERN.matcher(expression);
		if (matcher.matches()) {
			if ("not".equals(ObjectUtils.nvl(matcher.group(2)).trim())) {
				negated = !negated;
			}
			
			SelectorExpression leftOperand = SelectorExpression.parse(matcher.group(1).trim());
			AssertSyntax.notNull(leftOperand, "Invalid syntax: %s.", expression);

			SelectorExpression rightOperand = SelectorExpression.parse(matcher.group(3).trim());
			AssertSyntax.notNull(rightOperand, "Invalid syntax: %s.", expression);

			return new LikeExpression(negated, expression, leftOperand, rightOperand);
		}
		
		return null;
	}

	@Override
	public Iterator<? extends Expression> expressionIterator() {
		return new ExpressionIterator(this, Arrays.asList(leftOperand, rightOperand));
	}	
}
