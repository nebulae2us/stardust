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
import org.nebulae2us.stardust.my.domain.Attribute;

/**
 * 
 * Attribute mapping is the mapping between entity's attribute to dataReader's columnIndex
 * 
 * @author Trung Phan
 *
 */
public class AttributeMapping {

	private final Attribute attribute;
	
	public AttributeMapping(Mirror mirror) {
		mirror.bind(this);
		
		this.attribute = mirror.to(Attribute.class, "attribute");
	}

	public Attribute getAttribute() {
		return attribute;
	}
	
}
