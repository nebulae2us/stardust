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

/**
 * @author Trung Phan
 *
 */
public class AttributeHolderDefinitionBuilder<P> {

	
	protected final Map<String, String> scalarAttributeColumnNames = new HashMap<String, String>();
	
	protected final List<String> embeddedAttributes = new ArrayList<String>();
	
	protected final List<String> notNullableAttributes = new ArrayList<String>();
	
	protected final List<String> notInsertableAttributes = new ArrayList<String>();
	
	protected final List<String> notUpdatableAttributes = new ArrayList<String>();
	
	protected final List<String> excludedAttributes = new ArrayList<String>();

	public AttributeHolderDefinitionBuilder() {
	}

	public AttributeColumnBuilder mapAttribute(Object attributeLocator) {
		return new AttributeColumnBuilder(attributeLocator.toString());
	}
	
	public class AttributeColumnBuilder {
		final String attributeName;
		
		public AttributeColumnBuilder(String attributeName) {
			this.attributeName = attributeName;
		}
		
		public P toColumn(String columnName) {
			Map<String, String> map = AttributeHolderDefinitionBuilder.this.scalarAttributeColumnNames;
			map.put(attributeName, columnName);
			return (P)AttributeHolderDefinitionBuilder.this;
		}
	}

	public P embedAttributes(Object ... attributeLocators) {
		addAttributesToList(embeddedAttributes, attributeLocators);
		return (P)this;
	}
	
	public P notNullableAttributes(Object ... attributeLocators) {
		addAttributesToList(notNullableAttributes, attributeLocators);
		return (P)this;
	}
	
	public P notInsertableAttributes(Object ... attributeLocators) {
		addAttributesToList(notInsertableAttributes, attributeLocators);
		return (P)this;
	}
	
	public P notUpdatableAttributes(Object ... attributeLocators) {
		addAttributesToList(notUpdatableAttributes, attributeLocators);
		return (P)this;
	}
	
	public P excludeAttributes(Object ... attributeLocators) {
		addAttributesToList(excludedAttributes, attributeLocators);
		return (P)this;
	}

	private void addAttributesToList(List<String> list, Object ...attributeLocators) {
		for (Object attributeLocator : attributeLocators) {
			String attributeName = attributeLocator.toString();
			if (!list.contains(attributeName)) {
				list.add(attributeName);
			}
		}
	}
	
	
}
