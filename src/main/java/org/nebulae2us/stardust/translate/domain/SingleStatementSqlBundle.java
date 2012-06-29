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

import java.util.List;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.dao.SqlBundle;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class SingleStatementSqlBundle extends SqlBundle {
	
	private final String sql;
	
	private final List<?> paramValues;

	public SingleStatementSqlBundle(String sql, List<?> paramValues) {
		Assert.notEmpty(sql, "SQL cannot be empty");
		Assert.notNull(paramValues, "paramValues cannot be null");
		this.sql = sql;
		this.paramValues = paramValues;
	}
	
	@Override
	public int size() {
		return 1;
	}

	@Override
	public String getSql(int index) {
		if (index != 0) {
			throw new IndexOutOfBoundsException("size: 1, index: " + index);
		}
		return sql;
	}

	@Override
	public List<?> getParamValues(int index) {
		if (index != 0) {
			throw new IndexOutOfBoundsException("size: 1, index: " + index);
		}
		return paramValues;
	}

	@Override
	public MultiStatementSqlBundle join(SqlBundle... sqlBundles) {
		return MultiStatementSqlBundle.join(this, sqlBundles);
	}

	@Override
	public String getSql() {
		return sql;
	}

	@Override
	public List<?> getParamValues() {
		return paramValues;
	}

}
