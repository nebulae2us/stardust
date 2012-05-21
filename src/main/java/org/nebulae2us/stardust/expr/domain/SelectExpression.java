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

/**
 * @author Trung Phan
 *
 */
public class SelectExpression extends Expression {

	private static final ConcurrentMap<String, SelectExpression> CACHE = new ConcurrentHashMap<String, SelectExpression>();
	
	public SelectExpression(String expression) {
		super(expression);
	}
	
	public static SelectExpression parse(String expression) {
		
		SelectExpression result = CACHE.get(expression);
		if (result != null) {
			return result;
		}
		
		result = SelectAttributeExpression.parse(expression);
		if (result != null) {
			SelectExpression _result = CACHE.putIfAbsent(expression, result);
			return _result != null ? _result : result;
		}
		
		return result;
	}
	
	
}
