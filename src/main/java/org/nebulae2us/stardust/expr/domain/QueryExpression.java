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
public class QueryExpression extends Expression {

	private final boolean distinct;
	
	private final int firstResult;
	
	private final int maxResults;
	
	private final boolean count;
	
	private final List<SelectExpression> selectors;
	
	private final List<PredicateExpression> filters;
	
	private final List<OrderExpression> orders;
	
	public QueryExpression(String expression, 
			List<SelectExpression> selectors, List<PredicateExpression> filters, List<OrderExpression> orders,
			boolean distinct, int firstResult, int maxResults, boolean count) {
		
		super(expression);
		
		this.selectors = selectors;
		this.filters = filters;
		this.orders = orders;
		this.distinct = distinct;
		this.firstResult = firstResult;
		this.maxResults = maxResults;
		this.count = count;
	}

	public List<SelectExpression> getSelectors() {
		return selectors;
	}

	public List<PredicateExpression> getFilters() {
		return filters;
	}

	public List<OrderExpression> getOrders() {
		return orders;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public boolean isCount() {
		return count;
	}
	
}
