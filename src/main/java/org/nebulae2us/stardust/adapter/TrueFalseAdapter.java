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
package org.nebulae2us.stardust.adapter;

/**
 * @author Trung Phan
 *
 */
public class TrueFalseAdapter extends TypeAdapter<String, Boolean> {

	@Override
	public Class<String> getPersistenceType() {
		return String.class;
	}

	@Override
	public String toPersistenceType(Boolean object) {
		if (object == null) {
			return null;
		}
		return object ? "T" : "F";
	}

	@Override
	public Boolean toAttributeType(Class<? extends Boolean> expectedType, String object) {
		return object == null ? null : "T".equalsIgnoreCase(object);
	}

}