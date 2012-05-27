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
package org.nebulae2us.stardust.expr.domain;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class InsertEntityExpression extends InsertExpression {

	private final Class<?> entityClass;

	/**
	 * In normal case, tableIndex = 0. However, some entity also defines secondary table, so the index here to tell which table is interested in.
	 */
	private final int tableIndex;
	
	public InsertEntityExpression(String expression, Class<?> entityClass, int tableIndex) {
		super(expression);

		this.entityClass = entityClass;
		this.tableIndex = tableIndex;
		
		Assert.notNull(this.entityClass, "entityClass cannot be null");
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public int getTableIndex() {
		return tableIndex;
	}


}
