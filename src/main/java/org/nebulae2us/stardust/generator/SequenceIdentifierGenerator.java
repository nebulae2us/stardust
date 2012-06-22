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

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

import org.nebulae2us.stardust.dao.JdbcExecutor;
import org.nebulae2us.stardust.dialect.Dialect;
import org.nebulae2us.stardust.internal.util.ObjectUtils;

/**
 * @author Trung Phan
 *
 */
public class SequenceIdentifierGenerator implements ValueGenerator {

	private final String schema;
	
	private final String sequenceName;
	
	public SequenceIdentifierGenerator(String sequenceName) {
		this("", sequenceName);
	}
	
	public SequenceIdentifierGenerator(String schema, String sequenceName) {
		this.schema = schema;
		this.sequenceName = sequenceName;
	}
	
	public boolean instancePersisted(Object entityInstance, Object identifierValue) {
		return identifierValue != null;
	}

	public boolean generationBeforeInsertion() {
		return true;
	}

	public <T> T generateValue(Class<T> expectedType, Dialect dialect, JdbcExecutor jdbcExecutor) {
		Assert.isTrue(Number.class.isAssignableFrom(expectedType), "Expected numeric type.");

		String sql = dialect.getSqlToRetrieveNextSequenceValue(ObjectUtils.isEmpty(schema) ? sequenceName : schema + "." + sequenceName);
		return jdbcExecutor.queryFor(expectedType, sql);
	}

	public String getName() {
		return this.sequenceName;
	}

}
