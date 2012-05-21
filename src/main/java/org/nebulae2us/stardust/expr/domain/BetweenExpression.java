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

/**
 * @author Trung Phan
 *
 */
public class BetweenExpression extends PredicateExpression {
	
	private final static Pattern PATTERN = Pattern.compile("(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?)( +not)? +between +(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?) +and +(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?)");
	
	private final SelectorExpression selector;
	
	private final SelectorExpression lowerBound;
	
	private final SelectorExpression higherBound;

	public BetweenExpression(boolean negated, String expression, SelectorExpression selector, SelectorExpression lowerBound, SelectorExpression higherBound) {
		super(negated, expression);

		this.selector = selector;
		this.lowerBound = lowerBound;
		this.higherBound = higherBound;
	}

	public final SelectorExpression getSelector() {
		return selector;
	}
	
	public SelectorExpression getLowerBound() {
		return lowerBound;
	}

	public SelectorExpression getHigherBound() {
		return higherBound;
	}

	public static BetweenExpression parse(String expression, boolean negated) {
		Matcher matcher = PATTERN.matcher(expression);
		if (matcher.matches()) {
			
			if ("not".equals(ObjectUtils.nvl(matcher.group(2)).trim())) {
				negated = !negated;
			}
			
			SelectorExpression selector = SelectorExpression.parse(matcher.group(1).trim());
			AssertSyntax.notNull(selector, "Invalid syntax: %s.", expression);

			SelectorExpression lowerBound = SelectorExpression.parse(matcher.group(3).trim());
			AssertSyntax.notNull(lowerBound, "Invalid syntax: %s.", expression);
			
			SelectorExpression higherBound = SelectorExpression.parse(matcher.group(4).trim());
			AssertSyntax.notNull(higherBound, "Invalid syntax: %s.", expression);
			
			return new BetweenExpression(negated, expression, selector, lowerBound, higherBound);
		}
		
		return null;
	}

	@Override
	public Iterator<? extends Expression> expressionIterator() {
		return new ExpressionIterator(this, Arrays.asList(selector, lowerBound, higherBound));
	}

}
