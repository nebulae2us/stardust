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

import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nebulae2us.stardust.internal.util.ObjectUtils;

/**
 * @author Trung Phan
 *
 */
public class IsNullExpression extends PredicateExpression {
	
	private final static Pattern PATTERN = Pattern.compile("(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?) +is +(not +)?null");
	
	private final SelectorExpression selector;

	public IsNullExpression(boolean negated, String expression, SelectorExpression selector) {
		super(negated, expression);

		this.selector = selector;
		Assert.notNull(selector, "selector cannot be null");
	}

	public final SelectorExpression getSelector() {
		return selector;
	}
	
	public static IsNullExpression parse(String expression, boolean negated) {
		Matcher matcher = PATTERN.matcher(expression);
		if (matcher.matches()) {
			if ("not".equals(ObjectUtils.nvl(matcher.group(2)).trim())) {
				negated = !negated;
			}
			
			SelectorExpression selector = SelectorExpression.parse(matcher.group(1).trim());
			AssertSyntax.notNull(selector, "Invalid syntax: %s.", expression);

			return new IsNullExpression(negated, expression, selector);
		}
		
		return null;
	}

	@Override
	public Iterator<? extends Expression> expressionIterator() {
		return new ExpressionIterator(this, Collections.singletonList(selector));
	}
}
