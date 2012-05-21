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
package org.nebulae2us.stardust.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nebulae2us.electron.Builder;
import org.nebulae2us.electron.Procedure;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
@Builder(destination=Filter.class)
public class BaseFilterBuilder {

	protected boolean negated;
	
	protected String expression;
	
	protected List<Object> values;

	public BaseFilterBuilder() {}
	
	public BaseFilterBuilder(String expression, List<Object> values) {
		this(false, expression, values);
	}	
	
	public BaseFilterBuilder(String expression, Object ... values) {
		this(false, expression, values);
	}	
	
	public BaseFilterBuilder(boolean negated, String expression, List<Object> values) {
		
		this.negated = negated;
		this.expression = expression;
		this.values = new ArrayList<Object>(values);
	}
	
	public BaseFilterBuilder(boolean negated, String expression, Object ... values) {
		this(negated, expression, Arrays.asList(values));
	}
	
	private static int countWildcards(String expression) {
		int count = 0;
		for (int i = 0; i < expression.length(); i++) {
			if (expression.charAt(i) == '?') {
				count++;
			}
		}
		return count;
	}
	
	public RootBuilder predicate(String expression, Object ... values) {
		AssertSyntax.isTrue(countWildcards(expression) == values.length, "Values are not matching with wildcards in this expression \"%s\"", expression);

		this.negated = false;
		this.expression = expression;
		this.values = new ArrayList<Object>(Arrays.asList(values));

		return new RootBuilder();
	}

	public RootBuilder negatedPredicate(String expression, Object ... values) {
		AssertSyntax.isTrue(countWildcards(expression) == values.length, "Values are not matching with wildcards in this expression \"%s\"", expression);

		this.negated = true;
		this.expression = expression;
		this.values = new ArrayList<Object>(Arrays.asList(values));

		return new RootBuilder();
	}
	
	public GroupFilterBuilder<? extends RootBuilder> group() {
		return _group(false);
	}
	
	public GroupFilterBuilder<? extends RootBuilder> negatedGroup() {
		return _group(true);
	}

	private GroupFilterBuilder<? extends RootBuilder> _group(boolean negated) {
		return new GroupFilterBuilder<RootBuilder>(new RootBuilder(), negated, new Procedure() {
			public void execute(Object... arguments) {
				BaseFilterBuilder newFilter = (BaseFilterBuilder)arguments[0];
				addPredicate("and", newFilter);
				
			}
		});
	}
	
	
	public class RootBuilder {
		
		public ConnectBuilder<? extends RootBuilder> and() {
			return new ConnectBuilder<RootBuilder>(this, "and");
		}
		
		public ConnectBuilder<? extends RootBuilder> or() {
			return new ConnectBuilder<RootBuilder>(this, "or");
		}
	}
	
	
	protected void addPredicate(String operator, boolean negated, String predicate, Object ... values) {
		AssertSyntax.isTrue(countWildcards(predicate) == values.length, "Values are not matching with wildcards in this expression \"%s\"", predicate);
		
		BaseFilterBuilder newExpression = new BaseFilterBuilder(negated, predicate, values);
		addPredicate(operator, newExpression);
	}
	
	protected void addPredicate(String operator, BaseFilterBuilder newExpression) {
		
		if (this.expression == null || this.expression.length() == 0) {
			this.negated = false;
			this.expression = operator;
			this.values = new ArrayList<Object>();
			this.values.add(newExpression);
		}
		else if (operator.equals("and")) {
			if (this.expression.equals("and")) {
				this.values.add(newExpression);
			}
			else if (this.expression.equals("or")) {
				BaseFilterBuilder lastExpression = (BaseFilterBuilder)this.values.get(this.values.size() - 1);
				if (lastExpression.expression.equals("and")) {
					lastExpression.values.add(newExpression);
				}
				else {
					BaseFilterBuilder andExpression = new BaseFilterBuilder(false, "and", lastExpression, newExpression);
					this.values.set(this.values.size() - 1, andExpression);
				}
			}
			else {
				BaseFilterBuilder clonedExpression = new BaseFilterBuilder(this.negated, this.expression, this.values);
				this.negated = false;
				this.expression = "and";
				this.values = new ArrayList<Object>();
				this.values.add(clonedExpression);
				this.values.add(newExpression);

			}
		}
		else {
			if (this.expression.equals("or")) {
				this.values.add(newExpression);
			}
			else if (this.expression.equals("and") && this.values.size() == 1) {
				this.expression = "or";
				this.values.add(newExpression);
			}
			else {
				BaseFilterBuilder clonedExpression = new BaseFilterBuilder(this.negated, this.expression, this.values);
				this.negated = false;
				this.expression = "or";
				this.values = new ArrayList<Object>();
				this.values.add(clonedExpression);
				this.values.add(newExpression);
			}
		}
	}
	
	public class ConnectBuilder<P2> {
		
		private final P2 parent;
		
		private final String operator;
		
		public ConnectBuilder(P2 parent, String operator) {
			this.parent = parent;
			this.operator = operator;
		}
		
		public P2 predicate(String predicate, Object ... values) {
			addPredicate(this.operator, false, predicate, values);
			return parent;
		}
		
		public P2 negatedPredicate(String predicate, Object ... values) {
			addPredicate(this.operator, true, predicate, values);
			return parent;
		}
		
		public GroupFilterBuilder<P2> group() {
			return _group(false);
		}

		public GroupFilterBuilder<P2> negatedGroup() {
			return _group(true);
		}
		
		private GroupFilterBuilder<P2> _group(boolean negated) {
			return new GroupFilterBuilder<P2>(parent, negated, new Procedure() {
				public void execute(Object... arguments) {
					BaseFilterBuilder newFilter = (BaseFilterBuilder)arguments[0];
					addPredicate(operator, newFilter);
				}
			});
		}

	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		if (negated) {
			result.append("not ");
		}
		
		result.append(expression);
		if (values != null && values.size() > 0) {
			result.append(values.toString());
		}
		
		return result.toString();
	}
	
}
