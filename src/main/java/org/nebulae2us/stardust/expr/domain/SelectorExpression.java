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

import org.nebulae2us.electron.Mirror;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public abstract class SelectorExpression extends Expression {

	private final static ConcurrentMap<String, SelectorExpression> CACHE = new ConcurrentHashMap<String, SelectorExpression>();
	
	public SelectorExpression(String expression) {
		super(expression);
	}
	
	public SelectorExpression(Mirror mirror) {
		super(mirror);
	}
	
	public static SelectorExpression parse(String expression) {

		if (expression.equals("?")) {
			return WildcardExpression.getInstance();
		}
		
		SelectorExpression result = CACHE.get(expression);
		if (result != null) {
			return result;
		}

		if (expression.charAt(0) == ':') {
			result = NamedParamExpression.parse(expression);
			AssertSyntax.notNull(result, "Invalid parameter syntax '%s'.", expression);
			SelectorExpression _result = CACHE.putIfAbsent(expression, result);
			return _result != null ? _result : result;
		}

		// TODO function case when may not have ()
		if (expression.indexOf('(') > -1) {
			result = FunctionExpression.parse(expression);
			AssertSyntax.notNull(result, "Invalid function syntax '%s'.", expression);
			SelectorExpression _result = CACHE.putIfAbsent(expression, result);
			return _result != null ? _result : result;
		}
		
		result = AttributeExpression.parse(expression);
		if (result != null) {
			SelectorExpression _result = CACHE.putIfAbsent(expression, result);
			return _result != null ? _result : result;
		}
		
		return result;
	}
}
