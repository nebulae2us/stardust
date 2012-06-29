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
package org.nebulae2us.stardust.translate.domain.oracle;

import java.util.Map;

import org.nebulae2us.electron.util.MapBuilder;
import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.FunctionExpression;
import org.nebulae2us.stardust.expr.domain.SelectorExpression;
import org.nebulae2us.stardust.translate.domain.ParamValues;
import org.nebulae2us.stardust.translate.domain.SingleStatementSqlBundle;
import org.nebulae2us.stardust.translate.domain.Translator;
import org.nebulae2us.stardust.translate.domain.TranslatorContext;
import org.nebulae2us.stardust.translate.domain.TranslatorController;

import static org.nebulae2us.stardust.expr.domain.FunctionExpression.*;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class OracleFunctionTranslator implements Translator {

	public final static Map<String, String> SIMPLE_ONE_INPUT_FUNCTIONS = new MapBuilder<String, String>()
			.put(ABS, "ABS")
			.put(EXP, "EXP")
			.put(LN, "LN")
			.put(SQRT, "SQRT")
			.put(CEILING, "CEIL")
			.put(FLOOR, "FLOOR")
			.put(LENGTH, "LENGTH")
			.put(LOWER, "LOWER")
			.put(UPPER, "UPPER")
			.put(LTRIM, "LTRIM")
			.put(RTRIM, "RTRIM")
			.put(TRIM, "TRIM")
			.toMap();
	
	public OracleFunctionTranslator() {
	}

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof FunctionExpression;
	}
	
	public SqlBundle translate(TranslatorContext context, Expression expression, ParamValues paramValues) {
		
		TranslatorController controller = context.getTranslatorController();
		
		FunctionExpression functionExpression = (FunctionExpression)expression;
		
		if (SIMPLE_ONE_INPUT_FUNCTIONS.containsKey(functionExpression.getFunctionName())) {
			
			AssertState.isTrue(functionExpression.getParams().size() == 1, "Expected one parameter for function %s.", functionExpression.getFunctionName());
			
			SelectorExpression param = functionExpression.getParams().get(0);
			Translator translator = controller.findTranslator(param, paramValues);
			SqlBundle paramResult = translator.translate(context, param, paramValues);
			
			StringBuilder result = new StringBuilder();
			result.append(SIMPLE_ONE_INPUT_FUNCTIONS.get(functionExpression.getFunctionName()))
				.append('(')
				.append(paramResult.getSql())
				.append(')');
			
			return new SingleStatementSqlBundle(result.toString(), paramResult.getParamValues());
		}
		
		return null;
	}
	
}
