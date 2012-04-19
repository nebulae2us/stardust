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

import org.nebulae2us.electron.Mirror;

/**
 * @author Trung Phan
 *
 */
public class ComparisonExpression<T> extends Expression {

	private final T value;
	
	private final ComparisonOperator operator;
	
	public ComparisonExpression(Mirror mirror) {
		super(mirror);

		this.value = (T)mirror.toObject("value");
		this.operator = mirror.to(ComparisonOperator.class, "operator");
	}

	public T getValue() {
		return value;
	}

	public ComparisonOperator getOperator() {
		return operator;
	}
	
	
}
