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

/**
 * @author Trung Phan
 *
 */
public class MySQLDialect extends Dialect {

	@Override
	public String getSqlToRetrieveIdentityValue() {
		return "select last_insert_id()";
	}

	@Override
	public String getSqlToRetrieveNextSequenceValue(String sequenceName) {
		throw new UnsupportedOperationException("MySQL does not support sequence.");
	}

	@Override
	public String applyLimit(String sql, long limitValue) {
		String newSql = sql + " limit " + limitValue;
		return newSql;
	}

	@Override
	public String applyOffsetLimit(String sql, long offsetValue, long limitValue) {
		String newSql = sql + " limit " + limitValue + " offset " + offsetValue;
		return newSql;
	}

	@Override
	public String applyOffset(String sql, long offsetValue) {
		String newSql = sql + " offset " + offsetValue;
		return newSql;
	}

	@Override
	public String getIdentityDeclare() {
		return "auto_increment";
	}

	@Override
	public String getSqlToCreateSequence(String sequenceName) {
		throw new UnsupportedOperationException("MySQL does not support sequence.");
	}

	@Override
	public String getSqlToDropSequence(String sequenceName) {
		throw new UnsupportedOperationException("MySQL does not support sequence.");
	}

}
