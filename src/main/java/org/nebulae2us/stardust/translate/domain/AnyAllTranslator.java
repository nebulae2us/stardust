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

import static org.nebulae2us.stardust.internal.util.BaseAssert.AssertSyntax;

import java.util.ArrayList;
import java.util.List;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.stardust.Query;
import org.nebulae2us.stardust.expr.domain.AnyAllExpression;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.NamedParamExpression;
import org.nebulae2us.stardust.expr.domain.SelectorExpression;
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

	public Pair<String, List<?>> translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {
		
		AnyAllExpression anyAllExpression = (AnyAllExpression)expression;
		
		List<Object> scalarValues = new ArrayList<Object>();
		int count  = 0;
		final int maxInListSize = context.getDialect().getMaxInListSize();
		boolean extended = false;
		
		StringBuilder result = new StringBuilder();

		TranslatorController controller = context.getTranslatorController();
		Translator translator = controller.findTranslator(anyAllExpression.getLeftOperand(), paramValues);
		Pair<String, List<?>> selectorResult = translator.translate(context, anyAllExpression.getLeftOperand(), paramValues);
		
		scalarValues.addAll(selectorResult.getItem2());
		result.append(selectorResult.getItem1()).append(' ')
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
				Pair<String, List<?>> paramTranslationResult = paramTranslator.translate(context, param, paramValues);
				scalarValues.addAll(paramTranslationResult.getItem2());

				if (count < maxInListSize) {
					result.append(paramTranslationResult.getItem1()).append(',');
					count++;
				}
				else {
					count = 1;
					result.replace(result.length() - 1,  result.length(), ")");
					result.append("all".equals(anyAllExpression.getQuantifier()) ? " and " : " or ")
						.append(selectorResult.getItem1())
						.append(' ').append(anyAllExpression.getOperator()).append(' ').append(anyAllExpression.getQuantifier()).append(" (");
					
					result.append(paramTranslationResult.getItem1()).append(',');
					
					extended = true;
					scalarValues.addAll(selectorResult.getItem2());
				}
				
				continue;
			}
			
			if (value instanceof Query) {
				Pair<String, List<?>> subQueryTranslationResult = ((Query)value).translate();
				AssertSyntax.isTrue(anyAllExpression.getParams().size() == 1, "Invalid in-list expression that has sub-query: %s", expression);
				count++;
				result.append(subQueryTranslationResult.getItem1()).append(',');
				scalarValues.addAll(subQueryTranslationResult.getItem2());
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
						.append(selectorResult.getItem1())
						.append(' ').append(anyAllExpression.getOperator()).append(' ').append(anyAllExpression.getQuantifier()).append(" (?,");
					extended = true;
					scalarValues.addAll(selectorResult.getItem2());
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
		
		return new Pair<String, List<?>>(result.toString(), scalarValues);		

	}

}
