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
package org.nebulae2us.stardust.dialect;

import java.util.List;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.util.ListBuilder;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class SQLServerDialect extends Dialect {

	@Override
	public String getSqlToRetrieveIdentityValue() {
		return "select scope_identity()";
	}

	@Override
	public String getSqlToRetrieveNextSequenceValue(String sequenceName) {
		return "select next value for " + sequenceName;
	}

	@Override
	public Pair<String, List<?>> applyLimit(String sql, List<?> values, long offsetValue, long limitValue, String orderBy, List<?> orderByValues) {
		AssertState.isTrue("select".equalsIgnoreCase(sql.substring(0, 6)),"Unexpected sql: %s", sql);
		
		String newSql = "select top " + limitValue + sql.substring(6);
		return new Pair<String, List<?>>(newSql, values);
	}

	@Override
	public Pair<String, List<?>> applyOffsetLimit(String sql, List<?> values, long offsetValue, long limitValue, String orderBy, List<?> orderByValues) {
		AssertSyntax.notEmpty(orderBy, "Paging SQL requires ORDER BY clause for SQLServer dialect.");
		AssertState.isTrue("select".equalsIgnoreCase(sql.substring(0, 6)),"Unexpected sql: %s", sql);

		List<?> newValues = orderByValues.size() == 0 ? values :
			new ListBuilder<Object>().add(orderByValues).add(values).toList();
		
		String newSql = "select tmp_t.*, row_number() over (order by " + orderBy + ") tmp_rn from (select top " + (limitValue + offsetValue) + sql.substring(6) + ") tmp_t where tmp_rn > " + offsetValue;

		return new Pair<String, List<?>>(newSql, newValues);
	}

	@Override
	public Pair<String, List<?>> applyOffset(String sql, List<?> values, long offsetValue, long limitValue, String orderBy, List<?> orderByValues) {
		AssertSyntax.notEmpty(orderBy, "Paging SQL requires ORDER BY clause for SQLServer dialect.");

		List<?> newValues = orderByValues.size() == 0 ? values :
			new ListBuilder<Object>().add(orderByValues).add(values).toList();
		
		String newSql = "select tmp_t.*, row_number() over (order by " + orderBy + ") tmp_rn from (" + sql + ") tmp_t where tmp_rn > " + offsetValue;

		return new Pair<String, List<?>>(newSql, newValues);
	}

	@Override
	public String getIdentityDeclare() {
		return "identity(1, 1)";
	}

}
