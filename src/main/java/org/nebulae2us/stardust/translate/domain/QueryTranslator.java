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
package org.nebulae2us.stardust.translate.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.exception.IllegalSyntaxException;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.LogicalExpression;
import org.nebulae2us.stardust.expr.domain.OrderExpression;
import org.nebulae2us.stardust.expr.domain.PredicateExpression;
import org.nebulae2us.stardust.expr.domain.QueryExpression;
import org.nebulae2us.stardust.expr.domain.SelectAttributeExpression;
import org.nebulae2us.stardust.expr.domain.SelectExpression;
import org.nebulae2us.stardust.expr.domain.SelectorExpression;
import org.nebulae2us.stardust.internal.util.SQLUtils;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntity;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class QueryTranslator implements Translator {

	private static final SqlBundle EMPTY_RESULT = EmptySqlBundle.getInstance();
	
	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof QueryExpression;
	}

	public SqlBundle translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {

		QueryExpression queryExpression = (QueryExpression)expression;
		
		StringBuilder sql = new StringBuilder();
		List<Object> scalarValues = new ArrayList<Object>();

		SqlBundle selectResult = toSelectClause(context, queryExpression, paramValues);
		
		if (!queryExpression.isCount() || queryExpression.isDistinct()) {
			sql.append("select ");
			if (queryExpression.isDistinct()) {
				sql.append("distinct ");
			}
			sql.append(selectResult.getSql());
			scalarValues.addAll(selectResult.getParamValues());
		}
		else {
			sql.append("select count(*)");
		}
		
		if (queryExpression.isBackedBySql()) {
			SqlBundle fromResult = transformSql(queryExpression.getSql(), paramValues);
			sql.append("\n from (").append(fromResult.getSql()).append(") tmp_in");
			scalarValues.addAll(fromResult.getParamValues());
		}
		else {
			SqlBundle fromResult = toFromClause(context, queryExpression, paramValues);
			sql.append("\n  from ").append(fromResult.getSql());
			scalarValues.addAll(fromResult.getParamValues());
		}
		
		SqlBundle whereResult = toWhereClause(context, queryExpression, paramValues);
		if (whereResult != EMPTY_RESULT) {
			sql.append("\n where ").append(whereResult.getSql());
			scalarValues.addAll(whereResult.getParamValues());
		}

		SqlBundle orderResult = toOrderClause(context, queryExpression, paramValues);
		
		if (!queryExpression.isCount() && orderResult != EMPTY_RESULT) {
			sql.append("\n order by ").append(orderResult.getSql());
			scalarValues.addAll(orderResult.getParamValues());
		}

		String finalSql = sql.toString();

		if (queryExpression.isCount() && queryExpression.isDistinct()) {
			finalSql = "select count(*) from (" + sql.toString() + ") tmp";
		}
		else if (!queryExpression.isCount()) {
			if (queryExpression.getMaxResults() > 0 && queryExpression.getFirstResult() > 0) {
				finalSql = context.getDialect().applyOffsetLimit(sql.toString(), queryExpression.getFirstResult(), queryExpression.getMaxResults());
			}
			else if (queryExpression.getMaxResults() > 0) {
				finalSql = context.getDialect().applyLimit(sql.toString(), queryExpression.getMaxResults());
			}
			else if (queryExpression.getFirstResult() > 0) {
				finalSql = context.getDialect().applyOffset(sql.toString(), queryExpression.getFirstResult());
			}
		}
		
		return new SingleStatementSqlBundle(finalSql, scalarValues);
	}
	
	private SqlBundle transformSql(String sql, ParamValues paramValues) {
		List<Object> values = new ArrayList<Object>();
		StringBuilder newSql = new StringBuilder();
		
		int length = sql.length();
		
		boolean inParam = false;
		StringBuilder paramNameBuilder = null;
		
		for (int i = 0; i <= length; i++) {
			char c = i < length ? sql.charAt(i) : '\0';
			if (c == '?') {
				if (inParam) {
					throw new IllegalSyntaxException("Invalid parameter expression \":" + paramNameBuilder.toString() + "\"");
				}
				values.add(paramValues.getNextWildcardValue());
				newSql.append(c);
			}
			else if (c == ':') {
				if (inParam) {
					throw new IllegalSyntaxException("Invalid parameter expression \":" + paramNameBuilder.toString() + "\"");
				}
				
				paramNameBuilder = new StringBuilder();
				inParam = true;
			}
			else if (inParam) {
				if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c < '9' || c == '_' || c == '$' || c == '#') {
					paramNameBuilder.append(c);
				}
				else {
					inParam = false;
					String paramName = paramNameBuilder.toString();
					paramNameBuilder = null;
					values.add(paramValues.getParamValue(paramName));
					newSql.append('?').append(c);
				}
			}
			else {
				newSql.append(c);
			}
		}

		newSql.deleteCharAt(newSql.length() - 1);
		
		return new SingleStatementSqlBundle(newSql.toString(), values);
	}	

	private List<ColumnHolder> extractAllColumns(TranslatorContext context) {
		
		List<ColumnHolder> result = new ArrayList<ColumnHolder>();
		
		LinkedTableEntityBundle linkedTableEntityBundle = context.getLinkedTableEntityBundle();
		
		for (LinkedTableEntity linkedTableEntity : linkedTableEntityBundle.getLinkedTableEntities()) {
			
			if (linkedTableEntity.getEntity() == null) {
				continue;
			}
			
			Set<Column> columns = new LinkedHashSet<Column>();

			if (linkedTableEntity.getEntity().getEntityDiscriminator() != null) {
				Column column = linkedTableEntity.getEntity().getEntityDiscriminator().getColumn();
				if (column.getTable().equals(linkedTableEntity.getTable())) {
					columns.add(column);
				}
			}
			
			for (Attribute attribute : linkedTableEntity.getOwningSideAttributes()) {
				if (attribute instanceof ScalarAttribute) {
					ScalarAttribute scalarAttribute = (ScalarAttribute)attribute;
					columns.add(scalarAttribute.getColumn());
				}
				else if (attribute instanceof EntityAttribute) {
					columns.addAll(((EntityAttribute)attribute).getLeftColumns());
				}
			}
			
			for (Column column : columns) {
				result.add(new ColumnHolder(column, linkedTableEntity.getTableAlias(), linkedTableEntity.getAlias()));
			}
			
		}

		return result;
	}

	protected SqlBundle toSelectClause(TranslatorContext context, Expression expression, ParamValues paramValues) {
		
		TranslatorController controller = context.getTranslatorController();
		
		QueryExpression queryExpression = (QueryExpression)expression;

		List<ColumnHolder> columnHolders = extractAllColumns(context);

		StringBuilder result = new StringBuilder();
		List<Object> scalarValues = new ArrayList<Object>();

		if (queryExpression.getSelectors().size() == 0) {
			
			if (queryExpression.isBackedBySql()) {
				result.append('*');
			}
			else {
				for (ColumnHolder columnHolder : columnHolders) {
					result.append(columnHolder.tableAlias).append('.').append(columnHolder.column.getName())
					.append(" as ").append(columnHolder.alias.length() == 0 ? "" : columnHolder.alias + '_').append(columnHolder.column.getName())
					.append(",\n       ");
				}
				result.delete(result.length() - 9, result.length());
			}
		}
		else {
			
			for (SelectExpression selectExpression : queryExpression.getSelectors()) {
				
				if (selectExpression instanceof SelectAttributeExpression) {
					SelectAttributeExpression selectAttributeExpression = (SelectAttributeExpression)selectExpression;
					SelectorExpression selector = selectAttributeExpression.getSelector();
					
					Translator translator = controller.findTranslator(selector, paramValues);
					SqlBundle selectorTranslationResult = translator.translate(context, selector, paramValues);
					
					scalarValues.addAll(selectorTranslationResult.getParamValues());
					
					result.append(selectorTranslationResult.getSql())
						.append(",\n       ");
				}
				
			}
			result.delete(result.length() - 9, result.length());
			
		}

		return new SingleStatementSqlBundle(result.toString(), scalarValues);

	}

	protected SqlBundle toFromClause(TranslatorContext context, QueryExpression expression, ParamValues paramValues) {
		
		LinkedTableEntityBundle linkedTableEntityBundle = context.getLinkedTableEntityBundle();
		
		StringBuilder result = new StringBuilder();
		
		result.append(SQLUtils.getFullTableName(expression.getOverridingSchema(), context.getDefaultSchema(), linkedTableEntityBundle.getRoot().getTable()))
			.append(' ')
			.append(linkedTableEntityBundle.getRoot().getTableAlias());
		
		for (LinkedTableEntity linkedTableEntity : linkedTableEntityBundle.getNonRoots()) {
			result
				.append("\n           ")
				.append(linkedTableEntity.getJoinType() == JoinType.INNER_JOIN ? "inner join " : "left outer join ")
				.append(SQLUtils.getFullTableName(expression.getOverridingSchema(), context.getDefaultSchema(), linkedTableEntity.getTable()))
				.append(' ')
				.append(linkedTableEntity.getTableAlias()).append("\n               on (")
			;
			
			for (int i = 0; i < linkedTableEntity.getColumns().size(); i++) {
				Column parentColumn = linkedTableEntity.getParentColumns().get(i);
				Column column = linkedTableEntity.getColumns().get(i);
				
				result.append(linkedTableEntity.getParent().getTableAlias())
					.append('.').append(parentColumn.getName())
					.append(" = ")
					.append(linkedTableEntity.getTableAlias())
					.append('.')
					.append(column.getName())
					.append("\n               and ");
				
			}

			result.delete(result.length() - 20, result.length());
			result.append(")");
		}
		
		return new SingleStatementSqlBundle(result.toString(), Collections.emptyList());
	}
	
	protected SqlBundle toWhereClause(TranslatorContext context, Expression expression, ParamValues paramValues) {
		TranslatorController controller = context.getTranslatorController();
		
		QueryExpression queryExpression = (QueryExpression)expression;
		
		if (queryExpression.getFilters().size() == 0) {
			return EMPTY_RESULT;
		}
		
		PredicateExpression whereExpression = queryExpression.getFilters().size() == 1 ? queryExpression.getFilters().get(0) :
			new LogicalExpression(false, "and", queryExpression.getFilters());
		
		if (queryExpression.getFilters().size() == 1) {
			
		}
		
		Translator translator = controller.findTranslator(whereExpression, paramValues);
		AssertSyntax.notNull(translator, "Unrecognized expression: %s.", whereExpression.getExpression());
		
		return translator.translate(context, whereExpression, paramValues);
	}

	protected SqlBundle toOrderClause(TranslatorContext context, Expression expression, ParamValues paramValues) {
		TranslatorController controller = context.getTranslatorController();
		
		QueryExpression queryExpression = (QueryExpression)expression;
		
		if (queryExpression.getOrders().size() == 0) {
			return EMPTY_RESULT;
		}
		
		StringBuilder sql = new StringBuilder();
		List<Object> scalarValues = new ArrayList<Object>();
		
		for (OrderExpression orderExpression : queryExpression.getOrders()) {
			Translator translator = controller.findTranslator(orderExpression, paramValues);
			AssertSyntax.notNull(translator, "Unrecognized expression: %s.", orderExpression.getExpression());
			
			SqlBundle translationResult = translator.translate(context, orderExpression, paramValues);

			if (sql.length() > 0) {
				sql.append(", ");
			}
			sql.append(translationResult.getSql());
			scalarValues.addAll(translationResult.getParamValues());
		}
		
		return new SingleStatementSqlBundle(sql.toString(), scalarValues);
	}

	
	private static class ColumnHolder {
		private final Column column;
		private final String tableAlias;
		private final String alias;
		
		private ColumnHolder(Column column, String tableAlias, String alias) {
			this.column = column;
			this.tableAlias = tableAlias;
			this.alias = alias;
		}
	}

}
