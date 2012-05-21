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

import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nebulae2us.electron.Mirror;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class OrderExpression extends Expression {

	private final static Pattern PATTERN = Pattern.compile("(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?) *( asc| desc)?");
	
	private final SelectorExpression selector;
	
	private final String direction;
	
	public OrderExpression(Mirror mirror) {
		super(mirror);
		
		this.selector = mirror.to(SelectorExpression.class, "selector");
		this.direction = mirror.toString("direction");
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notNull(selector, "selector cannot be null");
		Assert.isTrue(this.direction.equals("asc") || this.direction.equals("desc"), "direction must be either asc or desc.");
	}

	public OrderExpression(String expression, SelectorExpression selector, String direction) {
		super(expression);
		this.selector = selector;
		this.direction = direction;

		assertInvariant();
	}

	public SelectorExpression getSelector() {
		return selector;
	}

	public String getDirection() {
		return direction;
	}
	
	public static OrderExpression parse(String expression) {
		Matcher matcher = PATTERN.matcher(expression);
		if (matcher.matches()) {
			
			String direction = matcher.group(2) == null ? "asc" : matcher.group(2).trim();

			SelectorExpression selector = SelectorExpression.parse(matcher.group(1).trim());
			
			AssertSyntax.notNull(selector, "Syntax is invalid for expression \"%s\"", expression);
			
			return new OrderExpression(expression, selector, direction);
		}
		
		return null;
	}
	
	@Override
	public Iterator<? extends Expression> expressionIterator() {
		return new ExpressionIterator(this, Collections.singletonList(selector));
	}
	
	
}
