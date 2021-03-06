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
public class MckoiDialect extends Dialect {

	@Override
	public String getSqlToRetrieveIdentityValue() {
		throw new UnsupportedOperationException("Mckoi does not support IDENTITY.");
	}

	@Override
	public String getSqlToRetrieveNextSequenceValue(String sequenceName) {
		return "select nextval('" + sequenceName + "')";
	}

	@Override
	public String applyLimit(String sql, long limitValue) {
		throw new UnsupportedOperationException("Mckoi does not support paginated SQL.");
	}

	@Override
	public String applyOffsetLimit(String sql, long offsetValue, long limitValue) {
		throw new UnsupportedOperationException("Mckoi does not support paginated SQL.");
	}

	@Override
	public String applyOffset(String sql, long offsetValue) {
		throw new UnsupportedOperationException("Mckoi does not support paginated SQL.");
	}

	@Override
	public String getIdentityDeclare() {
		throw new UnsupportedOperationException("McKoi does not support IDENTITY.");
	}

	@Override
	public String getSqlToCreateSequence(String sequenceName) {
		return "create sequence " + sequenceName + " increment 1 start 0";
	}

	@Override
	public String getSqlToDropSequence(String sequenceName) {
		return "drop sequence " + sequenceName;
	}

}
