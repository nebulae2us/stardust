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

import org.nebulae2us.electron.Converter;
import org.nebulae2us.electron.DestinationClassResolver;
import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.Builders;

/**
 * @author Trung Phan
 *
 */
public abstract class Expression {

	private final boolean negated;
	
	public Expression(Mirror mirror) {
		mirror.bind(this);
		
		this.negated = mirror.toBooleanValue("negated");
	}

	public boolean isNegated() {
		return negated;
	}
	
	public Expression negate() {
		return new Converter(getDestinationClassResolver(), true).convert(this).to(Expression.class);
	}
	
	protected DestinationClassResolver getDestinationClassResolver() {
		return null;
	}
}
