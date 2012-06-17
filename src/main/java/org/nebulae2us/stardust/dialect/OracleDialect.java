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
public class OracleDialect extends Dialect {

	@Override
	public String getSqlToRetrieveIdentityValue() {
		throw new UnsupportedOperationException("Identity is unsupported in Oracle.");
	}

	@Override
	public String getSqlToRetrieveNextSequenceValue(String sequenceName) {
		return "select " + sequenceName + ".nextval from dual";
	}

	@Override
	public int getMaxInListSize() {
		return 1000;
	}

	@Override
	public Pair<String, List<?>> applyLimit(String sql, List<?> values, long offsetValue, long limitValue, String orderBy, List<?> orderByValues) {
		String newSql = "select * from (" + sql + ") tmp_t where rownum < " + (limitValue + 1);
		return new Pair<String, List<?>>(newSql, values);
	}

	@Override
	public Pair<String, List<?>> applyOffsetLimit(String sql, List<?> values, long offsetValue, long limitValue, String orderBy, List<?> orderByValues) {
		String newSql = "select * from (select tmp_t.*, rownum tmp_rn from (" + sql + ") tmp_t where rownum < " + (limitValue + offsetValue + 1) + ") where tmp_rn > " + offsetValue;
		return new Pair<String, List<?>>(newSql, values);
	}

	@Override
	public Pair<String, List<?>> applyOffset(String sql, List<?> values, long offsetValue, long limitValue, String orderBy, List<?> orderByValues) {
		String newSql = "select * from (select tmp_t.*, rownum tmp_rn from (" + sql + ") tmp_t) where tmp_rn > " + offsetValue;
		return new Pair<String, List<?>>(newSql, values);
	}


	
	
	
}
