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

import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.expr.domain.BetweenExpression;
import org.nebulae2us.stardust.expr.domain.Expression;

/**
 * @author Trung Phan
 *
 */
public class BetweenTranslator implements Translator {

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof BetweenExpression;
	}

	public SqlBundle translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {

		TranslatorController controller = context.getTranslatorController();
		
		BetweenExpression betweenExpression = (BetweenExpression)expression;
		
		Translator selectorTranslator = controller.findTranslator(betweenExpression.getSelector(), paramValues);
		SqlBundle selectorTranslationResult = selectorTranslator.translate(context, betweenExpression.getSelector(), paramValues);

		Translator lowerBoundTranslator = controller.findTranslator(betweenExpression.getLowerBound(), paramValues);
		SqlBundle lowerBoundTranslationResult = lowerBoundTranslator.translate(context, betweenExpression.getLowerBound(), paramValues);
		
		Translator higherBoundTranslator = controller.findTranslator(betweenExpression.getHigherBound(), paramValues);
		SqlBundle higherBoundTranslationResult = higherBoundTranslator.translate(context, betweenExpression.getHigherBound(), paramValues);
		
		StringBuilder result = new StringBuilder();
		result.append(selectorTranslationResult.getSql())
			.append(betweenExpression.isNegated() ? " not between " : " between ")
			.append(lowerBoundTranslationResult.getSql()).append(" and ")
			.append(higherBoundTranslationResult.getSql());
		
		return new SingleStatementSqlBundle(result.toString(),
				new ListBuilder<Object>()
					.add(selectorTranslationResult.getParamValues())
					.add(lowerBoundTranslationResult.getParamValues())
					.add(higherBoundTranslationResult.getParamValues())
					.toList());
	}

}
