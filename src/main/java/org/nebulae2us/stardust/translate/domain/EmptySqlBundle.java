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

import java.util.Arrays;
import java.util.List;

import org.nebulae2us.stardust.dao.SqlBundle;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public final class EmptySqlBundle extends SqlBundle {

	private EmptySqlBundle () {}
	
	@Override
	public int size() {
		return 0;
	}

	@Override
	public String getSql() {
		throw new IndexOutOfBoundsException("size: 0");
	}

	@Override
	public String getSql(int index) {
		throw new IndexOutOfBoundsException("size: 0");
	}

	@Override
	public List<?> getParamValues() {
		throw new IndexOutOfBoundsException("size: 0");
	}

	@Override
	public List<?> getParamValues(int index) {
		throw new IndexOutOfBoundsException("size: 0");
	}

	@Override
	public SqlBundle join(SqlBundle... sqlBundles) {
		Assert.notEmpty(sqlBundles, "sqlBundles cannot be empty");
		if (sqlBundles.length == 1) {
			return sqlBundles[0];
		}
		return MultiStatementSqlBundle.join(sqlBundles[0], Arrays.copyOfRange(sqlBundles, 1, sqlBundles.length));
	}
	
	private final static EmptySqlBundle instance = new EmptySqlBundle();
	
	public static EmptySqlBundle getInstance() {
		return instance;
	}

}
