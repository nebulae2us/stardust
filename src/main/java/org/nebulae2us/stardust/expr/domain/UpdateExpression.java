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

import java.util.List;

/**
 * @author Trung Phan
 *
 */
public class UpdateExpression extends Expression {
	
	private final List<SetExpression> setExpressions;
	
	private final List<PredicateExpression> filters;

	public UpdateExpression(String expression, List<SetExpression> setExpressions, List<PredicateExpression> filters) {
		super(expression);
		
		this.setExpressions = setExpressions;
		this.filters = filters;
	}

	public List<SetExpression> getSetExpressions() {
		return setExpressions;
	}

	public List<PredicateExpression> getFilters() {
		return filters;
	}

	
}
