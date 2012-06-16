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
package org.nebulae2us.stardust.ddl.domain;

/**
 * @author Trung Phan
 *
 */
public class ColumnType {

	private final String typeName;
	private final int length;
	private final int precision;
	private final int scale;

	public ColumnType(String typeName) {
		this(typeName, 0, 0, 0);
	}
	
	public ColumnType(String typeName, int length) {
		this(typeName, length, 0, 0);
	}
	
	public ColumnType(String typeName, int length, int precision, int scale) {
		this.typeName = typeName;
		this.length = length;
		this.precision = precision;
		this.scale = scale;
	}

	public String getTypeName() {
		return typeName;
	}

	public int getLength() {
		return length;
	}

	public int getPrecision() {
		return precision;
	}

	public int getScale() {
		return scale;
	}

	@Override
	public String toString() {
		if (length == 0 && precision == 0 && scale == 0) {
			return typeName;
		}
		if (length > 0) {
			return typeName + "(" + length + ")";
		}
		if (precision > 0) {
			if (scale > 0) {
				return typeName + "(" + precision + "," + scale + ")";
			}
			else {
				return typeName + "(" + precision + ")";
			}
		}
		return typeName;
	}
	
}
