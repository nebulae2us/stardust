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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.nebulae2us.stardust.exception.IllegalSyntaxException;

/**
 * @author Trung Phan
 *
 */
public class PredicateExpression extends Expression {

	private final static ConcurrentMap<String, PredicateExpression> EXPRESSIONS_CACHE = new ConcurrentHashMap<String, PredicateExpression>();

	private final static ConcurrentMap<String, PredicateExpression> NEGATED_EXPRESSIONS_CACHE = new ConcurrentHashMap<String, PredicateExpression>();
	
	private final boolean negated;

	public PredicateExpression(boolean negated, String expression) {
		super(expression);
		
		this.negated = negated;
	}

	public boolean isNegated() {
		return negated;
	}
	
	public static PredicateExpression parse(String expression) {
		return parse(expression, false);
	}
	
	public static PredicateExpression parse(String expression, boolean negated) {
		expression = expression.trim();
		if (expression.length() == 0) {
			return null;
		}
		
		PredicateExpression result = negated ? NEGATED_EXPRESSIONS_CACHE.get(expression) : EXPRESSIONS_CACHE.get(expression);
		if (result != null) {
			return result;
		}
		
		result = InListExpression.parse(expression, negated);
		if (result != null) {
			PredicateExpression _result = negated ? NEGATED_EXPRESSIONS_CACHE.putIfAbsent(expression, result)
					                              :	EXPRESSIONS_CACHE.putIfAbsent(expression, result);
			return _result != null ? _result : result;
		}
		
		result = ComparisonExpression.parse(expression, negated);
		if (result != null) {
			PredicateExpression _result = negated ? NEGATED_EXPRESSIONS_CACHE.putIfAbsent(expression, result)
												  :	EXPRESSIONS_CACHE.putIfAbsent(expression, result);
			return _result != null ? _result : result;
		}
	
		result = IsNullExpression.parse(expression, negated);
		if (result != null) {
			PredicateExpression _result = negated ? NEGATED_EXPRESSIONS_CACHE.putIfAbsent(expression, result)
												  :	EXPRESSIONS_CACHE.putIfAbsent(expression, result);
			return _result != null ? _result : result;
		}
		
		result = BetweenExpression.parse(expression, negated);
		if (result != null) {
			PredicateExpression _result = negated ? NEGATED_EXPRESSIONS_CACHE.putIfAbsent(expression, result)
												  :	EXPRESSIONS_CACHE.putIfAbsent(expression, result);
			return _result != null ? _result : result;
		}
		
		throw new IllegalSyntaxException("Unable to recognize this expression: " + expression);
	}
	
}
