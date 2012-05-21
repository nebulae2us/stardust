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

import org.nebulae2us.electron.Converter;
import org.nebulae2us.electron.DestinationClassResolverByAnnotation;
import org.nebulae2us.electron.Procedure;

/**
 * @author Trung Phan
 *
 */
public class ChainedFilterBuilder<P> extends BaseFilterBuilder {

	private final P parent;

	private final Procedure callback;

	public ChainedFilterBuilder(P parent, Procedure callback) {
		this.parent = parent;
		this.callback = callback;
	}
	
	@Override
	public RootBuilder3 predicate(String expression, Object ... values) {
		super.predicate(expression, values);
		return new RootBuilder3();
	}

	@Override
	public RootBuilder3 negatedPredicate(String expression, Object ... values) {
		super.negatedPredicate(expression, values);
		return new RootBuilder3();
	}
	
	@Override
	public GroupFilterBuilder<RootBuilder3> group() {
		return _group(false);
	}

	@Override
	public GroupFilterBuilder<RootBuilder3> negatedGroup() {
		return _group(true);
	}

	private GroupFilterBuilder<RootBuilder3> _group(boolean negated) {
		return new GroupFilterBuilder<RootBuilder3>(new RootBuilder3(), negated, new Procedure(){
			public void execute(Object... arguments) {
				BaseFilterBuilder newFilter = (BaseFilterBuilder)arguments[0];
				addPredicate("and", newFilter);
			}
		});
	}
	
	
	public class RootBuilder3 extends BaseFilterBuilder.RootBuilder {
		
		@Override
		public ConnectBuilder<RootBuilder3> and() {
			return new ConnectBuilder<RootBuilder3>(this, "and");
		}
		
		@Override
		public ConnectBuilder<RootBuilder3> or() {
			return new ConnectBuilder<RootBuilder3>(this, "or");
		}
		
		public P endFilter() {
			Filter filter = new Converter(new DestinationClassResolverByAnnotation(), true).convert(ChainedFilterBuilder.this).to(Filter.class);
			ChainedFilterBuilder.this.callback.execute(filter);
			return ChainedFilterBuilder.this.parent;
		}
		
	}
	
	
}
