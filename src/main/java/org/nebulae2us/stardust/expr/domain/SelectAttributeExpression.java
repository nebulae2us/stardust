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
import org.nebulae2us.stardust.internal.util.ObjectUtils;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class SelectAttributeExpression extends SelectExpression {
	
	private final static Pattern PATTERN = Pattern.compile("(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?) *(as +([a-zA-Z0-9_]+))?");
	

	private final SelectorExpression selector;
	
	/**
	 * by default, alias should be blank ""
	 */
	private final String alias;
	
	public SelectAttributeExpression(Mirror mirror) {
		super(mirror);
		
		this.selector = mirror.to(SelectorExpression.class, "selector");
		this.alias = mirror.toString("alias");
	}

	
	public SelectAttributeExpression(String expression, SelectorExpression selector, String alias) {
		super(expression);
		
		this.selector = selector;
		this.alias = alias;
	}

	public static SelectAttributeExpression parse(String expression) {
		Matcher matcher = PATTERN.matcher(expression);
		
		if (matcher.matches()) {
			SelectorExpression selector = SelectorExpression.parse(matcher.group(1).trim());
			AssertSyntax.notNull(selector, "Invalid syntax: %s.", expression);
			
			String alias = ObjectUtils.nvl(matcher.group(3)).trim();
			
			return new SelectAttributeExpression(expression, selector, alias);
		}
		return null;
	}
	
	public SelectorExpression getSelector() {
		return selector;
	}


	public String getAlias() {
		return alias;
	}
	
	@Override
	public Iterator<? extends Expression> expressionIterator() {
		return new ExpressionIterator(this, Collections.singletonList(selector));
	}
	
}
