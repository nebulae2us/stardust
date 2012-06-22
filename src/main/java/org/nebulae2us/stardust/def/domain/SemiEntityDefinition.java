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
import org.nebulae2us.stardust.generator.ValueGenerator;

/**
 * @author Trung Phan
 *
 */
public class SemiEntityDefinition extends AttributeRelationshipHolderDefinition {

	private final List<String> identifiers;

	private final Map<String, ValueGenerator> identifierGenerators;

	public SemiEntityDefinition(Mirror mirror) {
		super(mirror);

		this.identifiers = mirror.toListOf(String.class, "identifiers");
		this.identifierGenerators = mirror.toMapOf(String.class, ValueGenerator.class, "identifierGenerators");
	}

	public final List<String> getIdentifiers() {
		return identifiers;
	}

	public final Map<String, ValueGenerator> getIdentifierGenerators() {
		return identifierGenerators;
	}
	
	
}
