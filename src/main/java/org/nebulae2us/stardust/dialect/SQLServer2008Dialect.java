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

/**
 * @author Trung Phan
 *
 */
public class SQLServer2008Dialect extends SQLServerDialect {

	@Override
	public String applyOffsetLimit(String sql, long offsetValue, long limitValue) {
		String newSql = "select * from (select tmp_t.*, row_number() over () tmp_rn from (select top " + (limitValue + offsetValue) + sql.substring(6) + ") tmp_t) tmp_t2 where tmp_rn > " + offsetValue + " order by tmp_rn";
		return newSql;
	}

	@Override
	public String applyOffset(String sql, long offsetValue) {
		String newSql = "select * from (select tmp_t.*, row_number() over () tmp_rn from (" + sql + ") tmp_t) tmp_t2 where tmp_rn > " + offsetValue + " order by tmp_rn";
		return newSql;
	}

}
