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
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.SetAttributeExpression;

/**
 * @author Trung Phan
 *
 */
public class SetAttributeTranslator implements Translator {

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof SetAttributeExpression;
	}

	public SqlBundle translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {

		TranslatorController controller = context.getTranslatorController();
		
		SetAttributeExpression setAttributeExpression = (SetAttributeExpression)expression;

		Translator leftTranslator = controller.findTranslator(setAttributeExpression.getLeftOperandExpression(), paramValues);
		SqlBundle leftTranslationResult = leftTranslator.translate(context, setAttributeExpression.getLeftOperandExpression(), paramValues);

		Translator rightTranslator = controller.findTranslator(setAttributeExpression.getRightOperandExpression(), paramValues);
		SqlBundle rightTranslationResult = rightTranslator.translate(context, setAttributeExpression.getRightOperandExpression(), paramValues);

		String sql = leftTranslationResult.getSql() + " = " + rightTranslationResult.getSql();
		
		List<Object> values = new ArrayList<Object>();
		values.addAll(leftTranslationResult.getParamValues());
		values.addAll(rightTranslationResult.getParamValues());

		return new SingleStatementSqlBundle(sql, values);
	}

}
