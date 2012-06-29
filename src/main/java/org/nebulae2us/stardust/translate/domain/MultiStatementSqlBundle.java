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

import org.nebulae2us.stardust.dao.SqlBundle;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class MultiStatementSqlBundle extends SqlBundle {

	private final String[] sqls;
	private final List<?>[] paramValues;
	
	private MultiStatementSqlBundle(String[] sqls, List<?>[] paramValues) {
		this.sqls = sqls;
		this.paramValues = paramValues;
	}
	

	@Override
	public int size() {
		return sqls.length;
	}

	@Override
	public String getSql(int index) {
		return sqls[index];
	}

	@Override
	public List<?> getParamValues(int index) {
		return paramValues[index];
	}

	public static MultiStatementSqlBundle join(final SqlBundle sqlBundle, final SqlBundle ... bundles) {
		Assert.notEmpty(bundles, "bundles cannot be empty");
		int count = sqlBundle.size();
		for (SqlBundle bundle : bundles) {
			count += bundle.size();
		}
		String[] sqls = new String[count];
		List<?>[] paramValues = new List<?>[count];
		
		int run = 0;
		for (int i = -1; i < bundles.length; i++) {
			SqlBundle bundle = i == -1 ? sqlBundle : bundles[i];
			for (int j = 0; j < bundle.size(); j++) {
				sqls[run] = bundle.getSql(j);
				paramValues[run] = bundle.getParamValues(j);
				run++;
			}
		}
		
		return new MultiStatementSqlBundle(sqls, paramValues);
	}

	@Override
	public SqlBundle join(SqlBundle... sqlBundles) {
		return MultiStatementSqlBundle.join(this, sqlBundles);
	}

	@Override
	public String getSql() {
		return sqls[0];
	}


	@Override
	public List<?> getParamValues() {
		return paramValues[0];
	}
	
	
}
