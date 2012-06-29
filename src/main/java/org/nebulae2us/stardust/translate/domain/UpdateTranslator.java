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

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.LogicalExpression;
import org.nebulae2us.stardust.expr.domain.PredicateExpression;
import org.nebulae2us.stardust.expr.domain.SetExpression;
import org.nebulae2us.stardust.expr.domain.UpdateExpression;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntity;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;

/**
 * @author Trung Phan
 *
 */
public class UpdateTranslator implements Translator {

	private static final SqlBundle EMPTY_RESULT = EmptySqlBundle.getInstance();
	
	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof UpdateExpression;
	}

	public SqlBundle translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {

		TranslatorController controller = context.getTranslatorController();
		
		UpdateExpression updateExpression = (UpdateExpression)expression;
		
		StringBuilder sql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		
		sql.append("update ");
		
		SqlBundle joinTranslationResult = toJoinClause(context, updateExpression, paramValues);
		sql.append(joinTranslationResult.getSql()).append(' ');
		values.addAll(joinTranslationResult.getParamValues());
		
		for (SetExpression setExpression : updateExpression.getSetExpressions()) {
			Translator setTranslator = controller.findTranslator(setExpression, paramValues);
			SqlBundle setTranslationResult = setTranslator.translate(context, setExpression, paramValues);
			
			sql.append(setTranslationResult.getSql()).append(", ");
			values.addAll(setTranslationResult.getParamValues());
		}
		
		sql.setCharAt(sql.length() - 2, ' ');
		
		SqlBundle whereTranslationResult = toWhereClause(context, updateExpression, paramValues);
		
		if (whereTranslationResult != EMPTY_RESULT) {
			sql.append(" where ").append(whereTranslationResult.getSql());
			values.addAll(whereTranslationResult.getParamValues());
		}
		
		return new SingleStatementSqlBundle(sql.toString(), values);
	}

	
	protected SqlBundle toJoinClause(TranslatorContext context, Expression expression, ParamValues paramValues) {
		
		LinkedTableEntityBundle linkedTableEntityBundle = context.getLinkedTableEntityBundle();
		
		StringBuilder result = new StringBuilder();
		
		result.append(linkedTableEntityBundle.getRoot().getTable())
			.append(' ')
			.append(linkedTableEntityBundle.getRoot().getTableAlias());
		
		for (LinkedTableEntity linkedTableEntity : linkedTableEntityBundle.getNonRoots()) {
			result
				.append("\n           ")
				.append(linkedTableEntity.getJoinType() == JoinType.INNER_JOIN ? "inner join " : "left outer join ")
				.append(linkedTableEntity.getTable()).append(' ')
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
		
		UpdateExpression queryExpression = (UpdateExpression)expression;
		
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

	
}
