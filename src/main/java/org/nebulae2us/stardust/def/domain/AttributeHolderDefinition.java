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
package org.nebulae2us.stardust.def.domain;

import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Mirror;

/**
 * @author Trung Phan
 *
 */
public class AttributeHolderDefinition {
	
	private final Map<String, String> scalarAttributeColumnNames;
	
	private final List<String> embeddedAttributes;
	
	private final List<String> notNullableAttributes;
	
	private final List<String> notInsertableAttributes;
	
	private final List<String> notUpdatableAttributes;
	
	private final List<String> excludedAttributes;

	public AttributeHolderDefinition(Mirror mirror) {
		mirror.bind(this);
		
		this.scalarAttributeColumnNames = mirror.toMapOf(String.class, String.class, "scalarAttributeColumnNames");
		this.embeddedAttributes = mirror.toListOf(String.class, "embeddedAttributes");
		this.notNullableAttributes = mirror.toListOf(String.class, "notNullableAttributes");
		this.notInsertableAttributes = mirror.toListOf(String.class, "notInsertableAttributes");
		this.notUpdatableAttributes = mirror.toListOf(String.class, "notUpdatableAttributes");
		this.excludedAttributes = mirror.toListOf(String.class, "excludedAttributes");
	}

	public final Map<String, String> getScalarAttributeColumnNames() {
		return scalarAttributeColumnNames;
	}

	public final List<String> getEmbeddedAttributes() {
		return embeddedAttributes;
	}

	public final List<String> getNotNullableAttributes() {
		return notNullableAttributes;
	}

	public final List<String> getNotInsertableAttributes() {
		return notInsertableAttributes;
	}

	public final List<String> getNotUpdatableAttributes() {
		return notUpdatableAttributes;
	}

	public final List<String> getExcludedAttributes() {
		return excludedAttributes;
	}
	
}
