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
public class LogicalExpression extends PredicateExpression{

	private final List<PredicateExpression> expressions;
	
	private final String operator;

	public LogicalExpression(boolean negated, String operator, List<PredicateExpression> expressions) {
		super(negated, operator);
		
		this.operator = operator;
		this.expressions = expressions;
	}

	public List<PredicateExpression> getExpressions() {
		return expressions;
	}

	public String getOperator() {
		return operator;
	}

}
