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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.Procedure;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.expr.domain.OrderExpression;
import org.nebulae2us.stardust.expr.domain.PredicateExpression;
import org.nebulae2us.stardust.expr.domain.QueryExpression;
import org.nebulae2us.stardust.expr.domain.SelectExpression;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.sql.domain.AliasJoin;
import org.nebulae2us.stardust.sql.domain.LinkedEntityBundle;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;
import org.nebulae2us.stardust.translate.domain.ParamValues;
import org.nebulae2us.stardust.translate.domain.TranslatorContext;
import org.nebulae2us.stardust.translate.domain.TranslatorController;

import static org.nebulae2us.stardust.Builders.*;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class QueryBuilder<T> {

	private final DaoManager daoManager;
	
	private String sql = "";
	
	private final Class<?> entityClass;
	private final List<AliasJoin> aliasJoins = new ArrayList<AliasJoin>();
	private int firstResult;
	private int maxResults;
	private boolean distinct;

	private final List<SelectExpression> selectorExpressions = new ArrayList<SelectExpression>();
	private final List<PredicateExpression> predicateExpressions = new ArrayList<PredicateExpression>();
	private final List<OrderExpression> orderExpressions = new ArrayList<OrderExpression>();

	
	private final Map<String, Object> namedParamValues = new HashMap<String, Object>();
	private final List<Object> selectWildcardValues = new ArrayList<Object>();
	private final List<Object> fromWildcardValues = new ArrayList<Object>();
	private final List<Object> filterWildcardValues = new ArrayList<Object>();
	private final List<Object> orderWildcardValues = new ArrayList<Object>();
	
	
	public QueryBuilder(DaoManager daoManager, Class<T> entityClass) {
		
		AssertSyntax.notNull(entityClass, "Entity Class cannot be null");
		AssertSyntax.notNull(sql, "SQL cannot be null.");
		
		this.daoManager = daoManager;
		this.entityClass = entityClass;
	}
	
	public QueryBuilder<T> backedBySql(String sql, Object ... values) {
		AssertSyntax.notEmpty(sql, "sql cannot be empty");
		AssertSyntax.empty(this.sql, "Cannot change the backed sql from \"%s\" to \"%s\"", this.sql, sql);

		int countWildcards = 0;
		for (int i = 0; i < sql.length(); i++) {
			if (sql.charAt(i) == '?') {
				countWildcards++;
			}
		}
		AssertSyntax.isTrue(values.length == countWildcards, "Supplied values do not match variables required by sql \"%s\"", sql);
		
		this.sql = sql;
		fromWildcardValues.addAll(Arrays.asList(values));
		return this;
	}
	
	public QueryBuilder<T> join(String target, String alias) {
		return _join(target, alias, JoinType.DEFAULT_JOIN);
	}
	
	public QueryBuilder<T> innerJoin(String target, String alias) {
		return _join(target, alias, JoinType.INNER_JOIN);
	}
	
	public QueryBuilder<T> outerJoin(String target, String alias) {
		return _join(target, alias, JoinType.LEFT_JOIN);
	}
	
	public ChainedFilterBuilder<QueryBuilder<T>> filterBy() {
		return new ChainedFilterBuilder<QueryBuilder<T>>(this, new Procedure() {
			public void execute(Object... arguments) {
				Filter filter = (Filter)arguments[0];
				
				Pair<PredicateExpression, List<?>> result = filter.toExpression();
				predicateExpressions.add(result.getItem1());
				
				filterWildcardValues.addAll(result.getItem2());
			}
		});
	}
	
	public QueryBuilder<T> assignParam(String param, Object value) {
		namedParamValues.put(param, value);
		return this;
	}
	
	public QueryBuilder<T> select(String expression, Object ... values) {
		
		SelectExpression selector = SelectExpression.parse(expression);
		AssertSyntax.notNull(selector, "Invalid select expression: %s.", expression);
		AssertSyntax.isTrue(selector.countWildcardExpressions() == values.length, "Values do not match with wildcards in the select expression \"%s\"", expression);
		
		selectorExpressions.add(selector);
		selectWildcardValues.addAll(Arrays.asList(values));
		
		return this;
	}
	
	public QueryBuilder<T> orderBy(String expression, Object ... values) {
		
		OrderExpression orderExpression = OrderExpression.parse(expression);
		AssertSyntax.notNull(orderExpression, "Invalid order expression: %s", expression);
		AssertSyntax.isTrue(orderExpression.countWildcardExpressions() == values.length, "Values do not match wildcards in this expression \"%s\"", expression);

		orderWildcardValues.addAll(Arrays.asList(values));
		
		orderExpressions.add(orderExpression);
		
		return this;
	}
	
	private QueryBuilder<T> _join(String target, String alias, JoinType joinType) {
		AssertSyntax.notEmpty(target, "Join target cannot be empty.");
		AssertSyntax.notEmpty(alias, "Alias cannot be empty for %s.", target);
		Assert.notNull(joinType, "joinType cannot be null");
		
		aliasJoins.add(
			aliasJoin()
				.name(target)
				.alias(alias)
				.joinType(joinType)
				.toAliasJoin()
			);
			
		return this;
	}
	
	public QueryBuilder<T> firstResult(int firstResult) {
		AssertSyntax.isTrue(firstResult >= 0, "firstResult of %s is a negative value.", firstResult);
		this.firstResult = firstResult;
		return this;
	}
	
	public QueryBuilder<T> maxResults(int maxResults) {
		AssertSyntax.isTrue(maxResults >= 0, "firstResult of %s is a negative value.", maxResults);
		this.maxResults = maxResults;
		return this;
	}
	
	public QueryBuilder<T> distinct() {
		return distinct(true);
	}
	
	public QueryBuilder<T> distinct(boolean distinct) {
		this.distinct = distinct;
		return this;
	}
	
	public Query<T> toQuery() {
		
		EntityRepository entityRepository = daoManager.getEntityRepository();
		TranslatorController controller = daoManager.getController();
		
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entityRepository.getEntity(this.entityClass), "", this.aliasJoins);
		
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle, true);

		TranslatorContext context = new TranslatorContext(daoManager.getDialect(), controller, linkedTableEntityBundle, linkedEntityBundle, sql.length() > 0);

		QueryExpression queryExpression = new QueryExpression("query", sql,
				this.selectorExpressions, this.predicateExpressions, this.orderExpressions, 
				this.distinct, this.firstResult, this.maxResults,
				false);
		
		ParamValues paramValues = new ParamValues(namedParamValues, 
				new ListBuilder<Object>().add(selectWildcardValues).add(filterWildcardValues).add(orderExpressions).toList());

		return new Query<T>(context, queryExpression, paramValues);
	}
	
	public List<T> list() {
		Query<T> query = toQuery();
		return daoManager.query(query);
	}

	public T uniqueValue() {
		Query<T> query = toQuery();
		List<T> result = daoManager.query(query);
		
		AssertState.isTrue(result.size() == 1, "Expected one record result.");
		
		return result.get(0);
	}
	
	
	
}
