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
	public String applyLimit(String sql, long limitValue) {
		AssertState.isTrue("select".equalsIgnoreCase(sql.substring(0, 6)),"Unexpected sql: %s", sql);
		
		String newSql = "select top " + limitValue + sql.substring(6);
		return newSql;
	}

	@Override
	public String applyOffsetLimit(String sql, long offsetValue, long limitValue) {
		AssertSyntax.isTrue(sql.indexOf("order by") > -1, "Query must have ORDER BY for pagination.");
		String newSql = sql + " offset " + offsetValue + " rows fetch next " + limitValue + " rows only";
		return newSql;
	}

	@Override
	public String applyOffset(String sql, long offsetValue) {
		AssertSyntax.isTrue(sql.indexOf("order by") > -1, "Query must have ORDER BY for pagination.");
		String newSql = sql + " offset " + offsetValue + " rows";
		return newSql;
	}

	@Override
	public String getIdentityDeclare() {
		return "identity(1, 1)";
	}

	@Override
	public String getSqlToCreateSequence(String sequenceName) {
		return "create sequence " + sequenceName + " start with 1 increment by 1";
	}

	@Override
	public String getSqlToDropSequence(String sequenceName) {
		return "drop sequence " + sequenceName;
	}


}
