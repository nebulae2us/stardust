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
package org.nebulae2us.stardust;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.expr.domain.LogicalExpression;
import org.nebulae2us.stardust.expr.domain.PredicateExpression;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class Filter {

	private final boolean negated;
	
	private final String expression;
	
	private final List<?> values;
	
	public Filter(Mirror mirror) {
		mirror.bind(this);
		
		this.negated = mirror.toBooleanValue("negated");
		this.expression = mirror.toString("expression");
		this.values = mirror.toListOf(Object.class, "values");
	}
	
	public Filter(String expression, List<?> values) {
		this(false, expression, values);
	}	
	
	public Filter(boolean negated, String expression, List<?> values) {
		this.negated = negated;
		this.expression = expression;
		this.values = Immutables.$(values);
	}

	public Filter(boolean negated, String expression, Object ... values) {
		this(negated, expression, Arrays.asList(values));
	}
	
	public Filter(String expression, Object ... values) {
		this(false, expression, values);
	}
	public boolean isNegated() {
		return negated;
	}
	
	public String getExpression() {
		return expression;
	}

	public List<?> getValues() {
		return values;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		if (this.negated) {
			result.append("not ");
		}
		
		result.append(this.expression);
		if (this.values != null && this.values.size() > 0) {
			result.append(this.values.toString());
		}
		
		return result.toString();
	}
	
	@SuppressWarnings("unchecked")
	public Pair<PredicateExpression, List<?>> toExpression() {
		PredicateExpression result = null;
		List<Object> scalarValues = new ArrayList<Object>();
		
		if (this.expression.equals("and") || this.expression.equals("or")) {
			List<PredicateExpression> subExpressions = new ArrayList<PredicateExpression>();
			
			for (Filter filter : (List<Filter>)values) {
				Pair<PredicateExpression, List<?>> subResult = filter.toExpression();
				subExpressions.add(subResult.getItem1());
				scalarValues.addAll(subResult.getItem2());
			}
			
			result = new LogicalExpression(this.negated, this.expression, subExpressions);
		}
		else {
			result = PredicateExpression.parse(this.expression, this.negated);
			AssertSyntax.isTrue(this.values.size() == result.countWildcardExpressions(), "Values do not match with wildcards in this expression: %s.", result.getExpression());
			scalarValues.addAll(this.values);
		}
		
		return new Pair<PredicateExpression, List<?>>(result, scalarValues);
	}
}
