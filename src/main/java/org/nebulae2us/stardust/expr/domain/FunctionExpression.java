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
package org.nebulae2us.stardust.expr.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.internal.util.StringUtils;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class FunctionExpression extends SelectorExpression {

	public final static String ABS = "abs";
	
	public final static String MOD = "mod";
	
	public final static String EXP = "exp";
	
	public final static String LN = "ln";
	
	public final static String POWER = "power";
	
	public final static String SQRT = "sqrt";
	
	public final static String CEILING = "ceiling";
	
	public final static String FLOOR = "floor";

	public final static String DAY = "day";
	
	public final static String MONTH = "month";
	
	public final static String YEAR = "year";
	
	public final static String HOUR = "hour";
	
	public final static String MINUTE = "minute";
	
	public final static String SECOND = "second";

	public final static String LENGTH = "length";
	
	public final static String LOWER = "lower";
	
	public final static String UPPER = "upper";
	
	public final static String SUBSTRING = "substring";

	public final static String INSTR = "inStr";

	public final static String CONCAT = "concat";
	
	public final static String LTRIM = "ltrim";
	
	public final static String RTRIM = "rtrim";
	
	public final static String TRIM = "trim";
	
	public final static String CURRENT_DATE = "currentDate";
	
	public final static String CURRENT_TIME = "currentTime";
	
	public final static String CURRENT_TIMESTAMP = "currentTimestamp";
	
	private final static List<String> NO_INPUT_SCALAR_FUNCTIONS = Arrays.asList(CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP);
	
	private final static List<String> ONE_INPUT_SCALAR_FUNCTIONS = Arrays.asList(ABS, EXP, LN, SQRT, CEILING, FLOOR, DAY, MONTH, YEAR, HOUR, MINUTE, SECOND, LENGTH, LOWER, UPPER, LTRIM, RTRIM, TRIM);
	
	private final static List<String> TWO_INPUT_SCALAR_FUNCTIONS = Arrays.asList(MOD, POWER);
	
	private final static List<String> VARIABLE_INPUT_SCALAR_FUNCTIONS = Arrays.asList(SUBSTRING, INSTR, CONCAT);
	
	public final static Pattern PATTERN = Pattern.compile("([a-zA-Z0-9_.]+) *\\((.*)\\)");
	
	private final String functionName;
	
	private final List<? extends SelectorExpression> params;
	
	public FunctionExpression(String expression, String functionName, List<? extends SelectorExpression> params) {
		super(expression);
		
		this.functionName = functionName;
		this.params = Immutables.$(params);
	}

	
	public static FunctionExpression parse(String expression) {
		Matcher matcher = PATTERN.matcher(expression.trim());
		if (matcher.matches()) {
			
			String functionName = matcher.group(1);
			
			String functionInput = matcher.group(2).trim();
			List<SelectorExpression> params = new ArrayList<SelectorExpression>();
			
			if (NO_INPUT_SCALAR_FUNCTIONS.contains(functionName)) {
				AssertSyntax.empty(functionInput, "Syntax is invalid for function %s in expression %s.", functionName, expression);
			}
			else if (ONE_INPUT_SCALAR_FUNCTIONS.contains(functionName)) {
				AssertSyntax.notEmpty(functionInput, "Syntax is invalid for function %s in expression %s.", functionName, expression);
				SelectorExpression param = SelectorExpression.parse(functionInput);
				AssertSyntax.notNull(param, "Syntax is invalid for function %s in expression %s.", functionName, expression);
				params.add(param);
			}
			else if (TWO_INPUT_SCALAR_FUNCTIONS.contains(functionName) || VARIABLE_INPUT_SCALAR_FUNCTIONS.contains(functionName)) {
				AssertSyntax.notEmpty(functionInput, "Syntax is invalid for function %s in expression %s.", functionName, expression);
				
				List<String> paramStrings = StringUtils.splitFunctionInput(functionInput);
				if (TWO_INPUT_SCALAR_FUNCTIONS.contains(functionName)) {
					AssertSyntax.isTrue(paramStrings.size() == 2, "Syntax is invalid for function %s in expression %s.", functionName, expression);
				}
				else {
					if (functionName.equals(SUBSTRING) || functionName.equals(INSTR)) {
						AssertSyntax.isTrue(paramStrings.size() == 2 || paramStrings.size() == 3, "Syntax is invalid for function %s in expression %s.", functionName, expression);
					}
					else if (functionName.equals(CONCAT)) {
						AssertSyntax.isTrue(paramStrings.size() >= 2, "Syntax is invalid for function %s in expression %s.", functionName, expression);
					}
				}
				
				for (String paramString : paramStrings) {
					AssertSyntax.notEmpty(paramString, "Syntax is invalid for function %s in expression %s.", functionName, expression);
					SelectorExpression param = SelectorExpression.parse(paramString);
					AssertSyntax.notNull(param, "Syntax is invalid for function %s in expression %s.", functionName, expression);
					params.add(param);
				}
			}
			else {
				AssertSyntax.isTrue(false, "Unrecognized function %s in expression %s.", functionName, expression);
			}
			
			return new FunctionExpression(expression, functionName, params);
		}
		
		return null;
	}
	

	public final List<? extends SelectorExpression> getParams() {
		return params;
	}

	public final String getFunctionName() {
		return functionName;
	}
	
	@Override
	public Iterator<? extends Expression> expressionIterator() {
		return new ExpressionIterator(this, params);
	}
}
