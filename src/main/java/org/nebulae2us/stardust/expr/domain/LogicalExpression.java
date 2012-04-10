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

import org.nebulae2us.electron.Mirror;

/**
 * @author Trung Phan
 *
 */
public class LogicalExpression extends Expression implements Reducible<LogicalExpression> {

	private final List<Expression> expressions;
	
	private final LogicalOperator operator;

	public LogicalExpression(Mirror mirror) {
		super(mirror);
		mirror.bind(this);
		
		this.expressions = mirror.toListOf(Expression.class, "expressions");
		this.operator = mirror.to(LogicalOperator.class, "operator");
		
		assert expressions.size() > 1;
	}

	public List<Expression> getExpressions() {
		return expressions;
	}

	public LogicalOperator getOperator() {
		return operator;
	}

	public LogicalExpression reduce() {

		
		
		return null;
	}
	
	

}