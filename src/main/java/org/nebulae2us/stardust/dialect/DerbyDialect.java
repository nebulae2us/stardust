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

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Trung Phan
 *
 */
public class DerbyDialect extends Dialect {

	@Override
	public String getSqlToRetrieveIdentityValue() {
		return "select identity_val_local() from sysibm.sysdummy1";
	}

	@Override
	public String getSqlToRetrieveNextSequenceValue(String sequenceName) {
		return "select next value for " + sequenceName + " from sysibm.sysdummy1";
	}

	@Override
	public String applyLimit(String sql, long limitValue) {
		String newSql = sql + " fetch first " + limitValue + " rows only";
		return newSql;
	}

	@Override
	public String applyOffsetLimit(String sql, long offsetValue, long limitValue) {
		String newSql = sql + " offset " + offsetValue + " rows fetch next " + limitValue + " rows only";
		return newSql;
	}

	@Override
	public String applyOffset(String sql, long offsetValue) {
		String newSql = sql + " offset " + offsetValue + " rows";
		return newSql;
	}

	@Override
	public String getIdentityDeclare() {
		return "generated always as identity(start with 1, increment by 1)";
	}

	@Override
	public Class<?> getJavaTypeForPersistence(Class<?> javaType) {
		if (javaType == Date.class) {
			return Timestamp.class;
		}
		return super.getJavaTypeForPersistence(javaType);
	}

	@Override
	public String getSqlToCreateSequence(String sequenceName) {
		return "create sequence " + sequenceName + " start with 1 increment by 1";
	}

	@Override
	public String getSqlToDropSequence(String sequenceName) {
		return "drop sequence " + sequenceName + " restrict";
	}


}
