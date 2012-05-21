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

import java.util.Collections;
import java.util.List;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.stardust.expr.domain.AttributeExpression;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntity;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class AttributeTranslator implements Translator {

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof AttributeExpression;
	}

	public Pair<String, List<?>> translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {

		AttributeExpression attributeExpression = (AttributeExpression)expression;
		
		LinkedTableEntityBundle linkedTableEntityBundle = context.getLinkedTableEntityBundle();
		
		String alias = "";
		String attrExpr = attributeExpression.getExpression();
		
		Pair<LinkedTableEntity, ScalarAttribute> result = linkedTableEntityBundle.findAttribute(alias, attrExpr);
		if (result == null) {
			int index = attrExpr.indexOf('.');
			if (index > -1) {
				alias = attrExpr.substring(0, index);
				attrExpr = attrExpr.substring(index + 1);
				result = linkedTableEntityBundle.findAttribute(alias, attrExpr);
			}
		}
		
		AssertSyntax.notNull(result, "Unrecognized expression: %s.", attributeExpression.getExpression());
			
		if (context.isExternalSql()) {
			return new Pair<String, List<?>>(result.getItem1().getAlias() + "_" + result.getItem2().getColumn().getName(), Collections.emptyList());
		}
		else {
			return new Pair<String, List<?>>(result.getItem1().getTableAlias() + "." + result.getItem2().getColumn().getName(), Collections.emptyList());
		}
	}

}
