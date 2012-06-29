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

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.SequenceQueryExpression;

/**
 * @author Trung Phan
 *
 */
public class SequenceQueryTranslator implements Translator {

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof SequenceQueryExpression;
	}

	public SqlBundle translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {

		SequenceQueryExpression sequenceExpression = (SequenceQueryExpression)expression;
		
		String sql = context.getDialect().getSqlToRetrieveNextSequenceValue(sequenceExpression.getSequenceName());

		return new SingleStatementSqlBundle(sql, Immutables.emptyList());
	}

}
