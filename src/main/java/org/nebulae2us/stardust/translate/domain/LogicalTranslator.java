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

import org.nebulae2us.electron.Pair;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.LogicalExpression;
import org.nebulae2us.stardust.expr.domain.PredicateExpression;

/**
 * @author Trung Phan
 *
 */
public class LogicalTranslator implements Translator {

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof LogicalExpression;
	}

	public Pair<String, List<?>> translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {

		TranslatorController controller = context.getTranslatorController();
		
		LogicalExpression logicalExpression = (LogicalExpression)expression;
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(logicalExpression.isNegated() ? "not (" : "(");
		List<Object> scalarValues = new ArrayList<Object>();
		
		boolean firstExpression = true;
		for (PredicateExpression subExpression : logicalExpression.getExpressions()) {
			
			Translator translator = controller.findTranslator(subExpression, paramValues);
			Pair<String, List<?>> subResult = translator.translate(context, subExpression, paramValues);
			
			if (firstExpression) {
				firstExpression = false;
			}
			else {
				sqlBuilder.append(' ').append(logicalExpression.getOperator()).append(' ');
			}
			sqlBuilder.append(subResult.getItem1());
			scalarValues.addAll(subResult.getItem2());
		}
		
		sqlBuilder.append(')');
		
		return new Pair<String, List<?>>(sqlBuilder.toString(), scalarValues);
	}

}
