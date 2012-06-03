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

import java.util.Map;

import org.nebulae2us.electron.Mirror;

/**
 * @author Trung Phan
 *
 */
public class AttributeRelationshipHolderDefinition extends AttributeHolderDefinition {

	private final Map<String, RelationshipDefinition> relationships;
	
	public AttributeRelationshipHolderDefinition(Mirror mirror) {
		super(mirror);
		
		this.relationships = mirror.toMapOf(String.class, RelationshipDefinition.class, "relationships");
	}

	public final Map<String, RelationshipDefinition> getRelationships() {
		return relationships;
	}

}
