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
package org.nebulae2us.stardust.generator;

import org.nebulae2us.stardust.dao.domain.JdbcHelper;
import org.nebulae2us.stardust.dialect.Dialect;
import org.nebulae2us.stardust.dialect.H2Dialect;
import org.nebulae2us.stardust.dialect.OracleDialect;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;


/**
 * @author Trung Phan
 *
 */
public class IdentityValueRetriever implements IdentifierGenerator {

	private static final IdentityValueRetriever instance = new IdentityValueRetriever();
	
	public static final IdentityValueRetriever getInstance() {
		return instance;
	}
	
	public boolean instancePersisted(Object entityInstance, Object identifierValue) {
		return identifierValue != null;
	}

	public boolean generationBeforeInsertion() {
		return false;
	}

	public <T> T generateIdentifierValue(Class<T> expectedType, Dialect dialect, JdbcHelper jdbcHelper) {
		Assert.isTrue(Number.class.isAssignableFrom(expectedType), "Expected numeric type.");
		
		if (dialect instanceof OracleDialect) {
			throw new UnsupportedOperationException("Oracle does not support IDENTITY type.");
		}
		else if (dialect instanceof H2Dialect) {
			String sql = "select scope_identity() from dual";
			return jdbcHelper.queryFor(expectedType, sql);
		}
		else {
			throw new UnsupportedOperationException("Unsupported dialect " + dialect.getClass().getSimpleName());
		}
	}

}
