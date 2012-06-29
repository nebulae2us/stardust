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
import java.util.List;

import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.expr.domain.ComparisonExpression;
import org.nebulae2us.stardust.expr.domain.Expression;

/**
 * @author Trung Phan
 *
 */
public class ComparisonTranslator implements Translator {

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof ComparisonExpression;
	}

	public SqlBundle translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {

		ComparisonExpression comparisonExpression = (ComparisonExpression)expression;

		List<Object> scalarValues = new ArrayList<Object>();
		
		Translator translator1 = context.getTranslatorController().findTranslator(comparisonExpression.getLeftOperandExpression(), paramValues);
		SqlBundle selectorResult1 = translator1.translate(context, comparisonExpression.getLeftOperandExpression(), paramValues);
		
		scalarValues.addAll(selectorResult1.getParamValues());
		
		Translator translator2 = context.getTranslatorController().findTranslator(comparisonExpression.getRightOperandExpression(), paramValues);
		SqlBundle selectorResult2 = translator2.translate(context, comparisonExpression.getRightOperandExpression(), paramValues);

		scalarValues.addAll(selectorResult2.getParamValues());
		
		StringBuilder result = new StringBuilder();
		result.append(selectorResult1.getSql()).append(' ').append(comparisonExpression.getOperator())
			.append(' ').append(selectorResult2.getSql());
		
		
		return new SingleStatementSqlBundle(result.toString(), scalarValues);
	}

}
