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

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

import java.util.List;

import org.nebulae2us.electron.Pair;

/**
 * @author Trung Phan
 *
 */
public class DB2Dialect extends Dialect {

	@Override
	public String getSqlToRetrieveIdentityValue() {
		return "select identity_val_local() as val from sysibm.sysdummy1";
	}

	@Override
	public String getSqlToRetrieveNextSequenceValue(String sequenceName) {
		return "select nextval for " + sequenceName + " from sysibm.sysdummy1";
	}

	@Override
	public Pair<String, List<?>> applyLimit(String sql, List<?> values, long offsetValue, long limitValue, String orderBy, List<?> orderByValues) {
		String newSql = sql + "fetch first " + limitValue + " rows only";
		return new Pair<String, List<?>>(newSql, values);
	}

	@Override
	public Pair<String, List<?>> applyOffsetLimit(String sql, List<?> values, long offsetValue, long limitValue, String orderBy, List<?> orderByValues) {
		AssertSyntax.notEmpty(orderBy, "Paging SQL requires ORDER BY clause for DB2 dialect.");

		String newSql = "selct * from (select tmp_t.*, row_number() over(order by order of tmp_t) as tmp_rn from (" + sql + " fetch first " + (limitValue + offsetValue) + " rows only) as tmp_t) where tmp_rn > " + offsetValue + " order by tmp_rn";
		return new Pair<String, List<?>>(newSql, values);
	}

	@Override
	public Pair<String, List<?>> applyOffset(String sql, List<?> values, long offsetValue, long limitValue, String orderBy, List<?> orderByValues) {
		AssertSyntax.notEmpty(orderBy, "Paging SQL requires ORDER BY clause for DB2 dialect.");

		String newSql = "select tmp_t.* from (" + sql + ") as tmp_t where row_number() over(order by order of tmp_t) > " + offsetValue;
		return new Pair<String, List<?>>(newSql, values);
	}

	@Override
	public String getIdentityDeclare() {
		return "generated always as identity(start with 1, increment by 1)";
	}

}
