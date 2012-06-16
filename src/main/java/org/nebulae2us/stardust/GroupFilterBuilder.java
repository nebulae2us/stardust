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

import org.nebulae2us.electron.Procedure;

/**
 * @author Trung Phan
 *
 */
public class GroupFilterBuilder<P> extends BaseFilterBuilder {
	
	private final boolean negatedGroup;

	private final P parent;
	
	private final Procedure createdCallback;
	
	public GroupFilterBuilder(P parent, boolean negatedGroup, Procedure createdCallback) {
		this.parent = parent;
		this.negatedGroup = negatedGroup;
		this.createdCallback = createdCallback;
	}
	
	@Override
	protected void addPredicate(String operator, BaseFilterBuilder newExpression) {
		if (!negatedGroup) {
			super.addPredicate(operator, newExpression);
		}
		else {
			if (this.expression != null && this.expression.length() > 0) {
				this.negated = !negated;
			}
			super.addPredicate(operator, newExpression);
			this.negated = !negated;
		}
	}
	
	@Override
	public RootBuilder2 predicate(String expression, Object ... values) {
		super.predicate(expression, values);
		if (negatedGroup) {
			this.negated = !negated;
		}
		createdCallback.execute(this);
		return new RootBuilder2();
	}

	@Override
	public RootBuilder2 negatedPredicate(String expression, Object ... values) {
		super.negatedPredicate(expression, values);
		if (negatedGroup) {
			this.negated = !negated;
		}
		createdCallback.execute(this);
		return new RootBuilder2();
	}
	
	@Override
	public GroupFilterBuilder<RootBuilder2> group() {
		return _group(false);
	}

	@Override
	public GroupFilterBuilder<RootBuilder2> negatedGroup() {
		return _group(true);
	}

	private GroupFilterBuilder<RootBuilder2> _group(boolean negated) {
		return new GroupFilterBuilder<RootBuilder2>(new RootBuilder2(), negated, new Procedure(){
			public void execute(Object... arguments) {
				BaseFilterBuilder newFilter = (BaseFilterBuilder)arguments[0];
				addPredicate("and", newFilter);
				
				GroupFilterBuilder.this.createdCallback.execute(GroupFilterBuilder.this);
			}
		});
	}
	
	
	public class RootBuilder2 extends BaseFilterBuilder.RootBuilder {
		
		@Override
		public ConnectBuilder<RootBuilder2> and() {
			return new ConnectBuilder<RootBuilder2>(this, "and");
		}
		
		@Override
		public ConnectBuilder<RootBuilder2> or() {
			return new ConnectBuilder<RootBuilder2>(this, "or");
		}
		
		public P endGroup() {
			return GroupFilterBuilder.this.parent;
		}
		
	}
	
}
