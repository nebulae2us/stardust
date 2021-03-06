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
	
	/**
	 * In normal scenario, sql = "". Only useful for sql-backed query.
	 */
	private final String sql;
	
	private final List<SelectExpression> selectors;
	
	private final List<PredicateExpression> filters;
	
	private final List<OrderExpression> orders;
	
	private final String overridingSchema;
	
	public QueryExpression(String expression, String sql,
			List<SelectExpression> selectors, List<PredicateExpression> filters, List<OrderExpression> orders,
			String overridingSchema, boolean distinct, int firstResult, int maxResults, boolean count) {
		
		super(expression);
		
		this.sql = sql;
		this.selectors = selectors;
		this.filters = filters;
		this.orders = orders;
		this.overridingSchema = overridingSchema;
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

	public final String getOverridingSchema() {
		return overridingSchema;
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

	public final String getSql() {
		return sql;
	}
	
	public final boolean isBackedBySql() {
		return sql.length() > 0;
	}
	
}
