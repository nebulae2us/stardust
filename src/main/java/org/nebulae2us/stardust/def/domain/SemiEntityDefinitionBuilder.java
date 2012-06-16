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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nebulae2us.stardust.generator.IdentifierGenerator;

/**
 * @author Trung Phan
 *
 */
public class SemiEntityDefinitionBuilder<P> extends AttributeRelationshipHolderDefinitionBuilder<P> {

	
	private final List<String> identifiers = new ArrayList<String>();

	private final Map<String, IdentifierGenerator> identifierGenerators = new HashMap<String, IdentifierGenerator>();
	
	public P identifier(Object ... attributeLocators ) {
		for (Object attributeLocator : attributeLocators) {
			String attributeName = attributeLocator.toString();
			if (!this.identifiers.contains(attributeName)) {
				this.identifiers.add(attributeName);
			}
		}
		return (P)this;
	}

	public P identifierGenerator(String expression, IdentifierGenerator identifierGenerator) {
		this.identifierGenerators.put(expression, identifierGenerator);
		return (P)this;
	}
	
	
	
}
