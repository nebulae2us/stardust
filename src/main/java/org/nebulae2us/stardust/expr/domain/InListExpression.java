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
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.electron.util.ListBuilder;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class InListExpression extends PredicateExpression {

	private final static Pattern PATTERN = Pattern.compile("(\\?|:[a-zA-Z0-9_]+|[a-zA-Z0-9_.() ]+?) +(not )? *in +\\( *(\\?|:[a-zA-Z0-9_]+)([a-zA-Z0-9:_?, ]*)\\)");
	
	private final static Pattern PATTERN_PARAM = Pattern.compile("(\\?|:[a-zA-Z0-9_]+)");
	
	private final SelectorExpression selectorExpression;

	private final List<SelectorExpression> params;
	
	public InListExpression(boolean negated, String expression, SelectorExpression selectorExpression, List<SelectorExpression> params) {
		super(negated, expression);
		this.selectorExpression = selectorExpression;
		this.params = Immutables.$(params);
	}
	
	public static InListExpression parse(String expression) {
		return parse(expression, false);
	}
	
	public static InListExpression parse(String expression, boolean negated) {
		Matcher matcher = PATTERN.matcher(expression.trim());
		
		if (matcher.matches()) {
			
			SelectorExpression selectorExpr = SelectorExpression.parse(matcher.group(1));
			AssertSyntax.notNull(selectorExpr, "Syntax is invalid for %s in expression %s.", matcher.group(1), expression);
			
			if ("not ".equals(matcher.group(2))) {
				negated = !negated;
			}
			
			List<SelectorExpression> params = new ArrayList<SelectorExpression>();
			params.add(SelectorExpression.parse(matcher.group(3)));
			
			String lastGroup = matcher.group(4).trim();
			if (lastGroup.length() > 0) {
				
				AssertSyntax.isTrue(lastGroup.charAt(0) == ',' || lastGroup.charAt(lastGroup.length() - 1) != ',', "Syntax is invalid for in-list expression: %.", expression);
				
				String[] otherParams = lastGroup.substring(1).split(",");
				for (String otherParam : otherParams) {
					Matcher paramMatcher = PATTERN_PARAM.matcher(otherParam.trim());
					if (!paramMatcher.matches()) {
						AssertSyntax.isTrue(lastGroup.charAt(0) == ',' || lastGroup.charAt(lastGroup.length() - 1) != ',', "Syntax is invalid for in-list expression: %.", expression);
					}
					params.add(SelectorExpression.parse(paramMatcher.group(1)));
				}
			}
			
			return new InListExpression(negated, expression, selectorExpr, params);
		}
		
		return null;
		
	}
	
	public SelectorExpression getSelectorExpression() {
		return selectorExpression;
	}

	public List<SelectorExpression> getParams() {
		return params;
	}

	@Override
	public String toString() {
		return "";
	}
	
	
	@Override
	public Iterator<? extends Expression> expressionIterator() {
		return new ExpressionIterator(this, new ListBuilder<Expression>().add(selectorExpression).add(params).toList());
	}
	
}
