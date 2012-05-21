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

import java.util.List;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.OrderExpression;

/**
 * @author Trung Phan
 *
 */
public class OrderTranslator implements Translator {

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof OrderExpression;
	}

	public Pair<String, List<?>> translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {

		TranslatorController controller = context.getTranslatorController();
		
		OrderExpression orderExpression = (OrderExpression)expression;
		
		Translator translator = controller.findTranslator(orderExpression.getSelector(), paramValues);
		Pair<String, List<?>> translationResult = translator.translate(context, orderExpression.getSelector(), paramValues);
		
		return new Pair<String, List<?>>(orderExpression.getDirection().equals("desc") ? translationResult.getItem1() + " desc" : translationResult.getItem1(),
				translationResult.getItem2());
		
	}

}
