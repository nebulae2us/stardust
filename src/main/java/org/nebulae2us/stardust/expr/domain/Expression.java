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

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public abstract class Expression {
	
	private final String expression;
	
	public Expression(String expression) {
		Assert.notEmpty(expression, "expression cannot be empty");
		
		this.expression = expression;
	}

	public final String getExpression() {
		return expression;
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<? extends Expression> expressionIterator() {
		return new ExpressionIterator(this, Collections.EMPTY_LIST);
	}
	
	public Iterable<? extends Expression> nestedExpressions() {
		return new Iterable<Expression>() {
			@SuppressWarnings("unchecked")
			public Iterator<Expression> iterator() {
				return (Iterator<Expression>)expressionIterator();
			}
		};
	}
	
	public int countWildcardExpressions() {
		int count = 0;
		for (Expression expr : nestedExpressions()) {
			if (expr instanceof WildcardExpression) {
				count++;
			}
		}
		return count;
	}
}
