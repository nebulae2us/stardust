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
package org.nebulae2us.stardust.sql.domain;

import org.nebulae2us.electron.Mirror;

/**
 * 
 * ScalarAttributeMapping cache the columnIndex from the DataReader. For example, to read data for an attribute age (alias p), the column name should be p_age.
 * The columnIndex for this column p_age is stored here.
 * 
 * @author Trung Phan
 *
 */
public class ScalarAttributeMapping extends AttributeMapping {

	private final int columnIndex;
	
	public ScalarAttributeMapping(Mirror mirror) {
		super(mirror);
		mirror.bind(this);
		
		this.columnIndex = mirror.toIntValue("columnIndex");
	}

	public int getColumnIndex() {
		return columnIndex;
	}
	
	
}
