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
import java.util.List;

import org.nebulae2us.stardust.Query;
import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.expr.domain.AnyAllExpression;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.NamedParamExpression;
import org.nebulae2us.stardust.expr.domain.WildcardExpression;
import org.nebulae2us.stardust.internal.util.ScalarValueIterator;

/**
 * @author Trung Phan
 *
 */
public class AnyAllTranslator implements Translator {

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof AnyAllExpression;
	}

	public SqlBundle translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {
		
		AnyAllExpression anyAllExpression = (AnyAllExpression)expression;
		
		List<Object> scalarValues = new ArrayList<Object>();
		int count  = 0;
		final int maxInListSize = context.getDialect().getMaxInListSize();
		boolean extended = false;
		
		StringBuilder result = new StringBuilder();

		TranslatorController controller = context.getTranslatorController();
		Translator translator = controller.findTranslator(anyAllExpression.getLeftOperand(), paramValues);
		SqlBundle selectorResult = translator.translate(context, anyAllExpression.getLeftOperand(), paramValues);
		
		scalarValues.addAll(selectorResult.getParamValues());
		result.append(selectorResult.getSql()).append(' ')
			.append(anyAllExpression.getOperator()).append(' ')
			.append(anyAllExpression.getQuantifier()).append(" (");
		
		for (Expression param : anyAllExpression.getParams()) {
			Object value = null;
			
			if (param instanceof WildcardExpression) {
				value = paramValues.getNextWildcardValue();
			}
			else if (param instanceof NamedParamExpression) {
				String paramName = ((NamedParamExpression)param).getParamName();
				value = paramValues.getParamValue(paramName);
			}
			else {
				Translator paramTranslator = controller.findTranslator(param, paramValues);
				SqlBundle paramTranslationResult = paramTranslator.translate(context, param, paramValues);
				scalarValues.addAll(paramTranslationResult.getParamValues());

				if (count < maxInListSize) {
					result.append(paramTranslationResult.getSql()).append(',');
					count++;
				}
				else {
					count = 1;
					result.replace(result.length() - 1,  result.length(), ")");
					result.append("all".equals(anyAllExpression.getQuantifier()) ? " and " : " or ")
						.append(selectorResult.getSql())
						.append(' ').append(anyAllExpression.getOperator()).append(' ').append(anyAllExpression.getQuantifier()).append(" (");
					
					result.append(paramTranslationResult.getSql()).append(',');
					
					extended = true;
					scalarValues.addAll(selectorResult.getParamValues());
				}
				
				continue;
			}
			
			if (value instanceof Query) {
				SqlBundle subQueryTranslationResult = ((Query<?>)value).translate();
				AssertSyntax.isTrue(anyAllExpression.getParams().size() == 1, "Invalid in-list expression that has sub-query: %s", expression);
				count++;
				result.append(subQueryTranslationResult.getSql()).append(',');
				scalarValues.addAll(subQueryTranslationResult.getParamValues());
				break;
			}

			for (Object scalarValue : new ScalarValueIterator<Object>(value)) {
				
				if (count < maxInListSize) {
					result.append("?,");
					count++;
				}
				else {
					count = 1;
					result.replace(result.length() - 1,  result.length(), ")");
					result.append("all".equals(anyAllExpression.getQuantifier()) ? " and " : " or ")
						.append(selectorResult.getSql())
						.append(' ').append(anyAllExpression.getOperator()).append(' ').append(anyAllExpression.getQuantifier()).append(" (?,");
					extended = true;
					scalarValues.addAll(selectorResult.getParamValues());
				}
				scalarValues.add(scalarValue);
			}
		}

		AssertSyntax.isTrue(count > 0, "No values are supplied for the in-list expression '%s'.", anyAllExpression.getExpression());
		
		result.replace(result.length() - 1, result.length(), ")");
		
		if (extended) {
			result.insert(0, '(');
			result.append(')');
		}
		
		if (anyAllExpression.isNegated()) {
			result.insert(0, "not (").append(')');
		}
		
		return new SingleStatementSqlBundle(result.toString(), scalarValues);		

	}

}
