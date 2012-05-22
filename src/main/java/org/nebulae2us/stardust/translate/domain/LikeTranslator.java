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
import org.nebulae2us.stardust.expr.domain.LikeExpression;

/**
 * @author Trung Phan
 *
 */
public class LikeTranslator implements Translator {

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof LikeExpression;
	}

	public Pair<String, List<?>> translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {

		TranslatorController controller = context.getTranslatorController();
		
		LikeExpression likeExpression = (LikeExpression)expression;
		
		Translator leftOperandTranslator = controller.findTranslator(likeExpression.getLeftOperand(), paramValues);
		Pair<String, List<?>> leftOperandTranslationResult = leftOperandTranslator.translate(context, likeExpression.getLeftOperand(), paramValues);
		
		Translator rightOperandTranslator = controller.findTranslator(likeExpression.getRightOperand(), paramValues);
		Pair<String, List<?>> rightOperandTranslationResult = rightOperandTranslator.translate(context, likeExpression.getRightOperand(), paramValues);

		StringBuilder sql = new StringBuilder()
		    .append(leftOperandTranslationResult.getItem1()).append(likeExpression.isNegated() ? " not like " : " like ")
			.append(rightOperandTranslationResult.getItem1());
		
		List<Object> scalarValues = new ArrayList<Object>();
		scalarValues.addAll(leftOperandTranslationResult.getItem2());
		scalarValues.addAll(rightOperandTranslationResult.getItem2());
		
		return new Pair<String, List<?>>(sql.toString(), scalarValues);
	}

}
