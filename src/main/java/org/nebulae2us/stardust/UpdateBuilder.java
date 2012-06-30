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

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;
import static org.nebulae2us.stardust.internal.util.Builders.aliasJoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.Procedure;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.dao.JdbcExecutor;
import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.expr.domain.PredicateExpression;
import org.nebulae2us.stardust.expr.domain.SetExpression;
import org.nebulae2us.stardust.expr.domain.UpdateExpression;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityIdentifier;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;
import org.nebulae2us.stardust.sql.domain.AliasJoin;
import org.nebulae2us.stardust.sql.domain.LinkedEntityBundle;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;
import org.nebulae2us.stardust.translate.domain.ParamValues;
import org.nebulae2us.stardust.translate.domain.TranslatorContext;
import org.nebulae2us.stardust.translate.domain.TranslatorController;

/**
 * @author Trung Phan
 *
 */
public class UpdateBuilder<T> {
	
	private final DaoManager daoManager;
	
	private String sql = "";
	
	private final Class<?> entityClass;
	private final List<AliasJoin> aliasJoins = new ArrayList<AliasJoin>();
	private String overridingSchema = "";

	private final List<SetExpression> setExpressions = new ArrayList<SetExpression>();
	private final List<PredicateExpression> predicateExpressions = new ArrayList<PredicateExpression>();

	private final Map<String, Object> namedParamValues = new HashMap<String, Object>();
	private final List<Object> setWildcardValues = new ArrayList<Object>();
	private final List<Object> fromWildcardValues = new ArrayList<Object>();
	private final List<Object> filterWildcardValues = new ArrayList<Object>();
	
	public UpdateBuilder(DaoManager daoManager, Class<T> entityClass) {
		
		AssertSyntax.notNull(entityClass, "Entity Class cannot be null");
		AssertSyntax.notNull(sql, "SQL cannot be null.");
		
		this.daoManager = daoManager;
		this.entityClass = entityClass;
	}
	
	public UpdateBuilder<T> backedBySql(String sql, Object ... values) {
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
	
	public UpdateBuilder<T> join(String target, String alias) {
		return _join(target, alias, JoinType.LEFT_JOIN);
	}
	
	public UpdateBuilder<T> innerJoin(String target, String alias) {
		return _join(target, alias, JoinType.INNER_JOIN);
	}
	
	public UpdateBuilder<T> outerJoin(String target, String alias) {
		return _join(target, alias, JoinType.LEFT_JOIN);
	}
	
	public UpdateBuilder<T> filterById(Object ... idValues) {

		Entity entity = this.daoManager.getEntityRepository().getEntity(entityClass);
		EntityIdentifier entityIdentifier = entity.getEntityIdentifier();
		List<Attribute> attributes = entityIdentifier.getAttributes();

		if (attributes.size() == 0) {
			AssertSyntax.fail("Cannot filter by ID for non-id entity");
		}
		AssertSyntax.isTrue(attributes.size() == idValues.length, "Expected %d values for ID", attributes.size());

		FilterBuilder filterBuilder = new FilterBuilder();
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute = attributes.get(i);
			AssertSyntax.isTrue(attribute instanceof ScalarAttribute, "Filter by composite id is not yet implemented.");
			ScalarAttribute scalarAttribute = (ScalarAttribute)attribute;
			filterBuilder.predicate(scalarAttribute.getFullName() + " = ?", idValues[i]);
		}
		
		Filter filter = filterBuilder.toFilter();
		Pair<PredicateExpression, List<?>> result = filter.toExpression();
		predicateExpressions.add(result.getItem1());
		filterWildcardValues.addAll(result.getItem2());
		
		return this;
	}
	
	public UpdateBuilder<T> filterBy(String expression, Object ...values) {
		Filter filter = new FilterBuilder(expression, values).toFilter();
		Pair<PredicateExpression, List<?>> result = filter.toExpression();
		predicateExpressions.add(result.getItem1());
		filterWildcardValues.addAll(result.getItem2());
		return this;
	}
	
	public ChainedFilterBuilder<UpdateBuilder<T>> filterBy() {
		return new ChainedFilterBuilder<UpdateBuilder<T>>(this, new Procedure() {
			public void execute(Object... arguments) {
				Filter filter = (Filter)arguments[0];
				
				Pair<PredicateExpression, List<?>> result = filter.toExpression();
				predicateExpressions.add(result.getItem1());
				
				filterWildcardValues.addAll(result.getItem2());
			}
		});
	}
	
	public UpdateBuilder<T> assignParam(String param, Object value) {
		namedParamValues.put(param, value);
		return this;
	}
	
	public UpdateBuilder<T> set(String expression, Object ... values) {
		
		SetExpression setExpression = SetExpression.parse(expression);
		AssertSyntax.notNull(setExpression, "Invalid set expression: %s.", expression);
		AssertSyntax.isTrue(setExpression.countWildcardExpressions() == values.length, "Values do not match with wildcards in the select expression \"%s\"", expression);
		
		setExpressions.add(setExpression);
		setWildcardValues.addAll(Arrays.asList(values));
		
		return this;
	}
	
	private UpdateBuilder<T> _join(String target, String alias, JoinType joinType) {
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
	
	public UpdateBuilder<T> schema(String schemaName) {
		AssertSyntax.notEmpty(schemaName, "Schema Name cannot be empty");
		this.overridingSchema = schemaName;
		return this;
	}
	
	public Update<T> toUpdate() {
		
		EntityRepository entityRepository = daoManager.getEntityRepository();
		TranslatorController controller = daoManager.getController();
		
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entityRepository.getEntity(this.entityClass), "", this.aliasJoins);
		
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle, true);

		TranslatorContext context = new TranslatorContext(daoManager.getDialect(), controller, linkedTableEntityBundle, linkedEntityBundle, sql.length() > 0, daoManager.getDefaultSchema());

		UpdateExpression updateExpression = new UpdateExpression("update", sql,
				this.setExpressions, this.predicateExpressions, 
				this.overridingSchema);
		
		ParamValues paramValues = new ParamValues(namedParamValues, 
				new ListBuilder<Object>().add(setWildcardValues).add(filterWildcardValues).toList());

		return new Update<T>(context, updateExpression, paramValues);
	}
	
	public void update() {
		Update<T> update = toUpdate();
		SqlBundle sqlBundle = update.translate();
		
		JdbcExecutor jdbcExecutor = daoManager.getJdbcExecutor();

		jdbcExecutor.beginUnitOfWork();
		try {
			for (int i = 0; i < sqlBundle.size(); i++) {
				String sql = sqlBundle.getSql(i);
				List<?> values = sqlBundle.getParamValues(i);
				jdbcExecutor.update(sql, values);
			}
			
		}
		finally {
			jdbcExecutor.endUnitOfWork();
		}
		
		
	}

	
}
