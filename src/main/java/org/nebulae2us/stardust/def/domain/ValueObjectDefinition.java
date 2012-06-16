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

import org.nebulae2us.electron.Mirror;

/**
 * @author Trung Phan
 *
 */
public class ValueObjectDefinition extends AttributeHolderDefinition {

	private Class<?> voClass;
	
	private Class<?> owningEntityClass;
	
	private final String attributeFullName;
	
	public ValueObjectDefinition(Mirror mirror) {
		super(mirror);
		
		this.voClass = mirror.to(Class.class, "voClass");
		this.owningEntityClass = mirror.to(Class.class, "owningEntityClass");
		this.attributeFullName = mirror.toString("attributeFullName");
	}

	public final Class<?> getVoClass() {
		return voClass;
	}

	public final void setVoClass(Class<?> voClass) {
		this.voClass = voClass;
	}

	public final Class<?> getOwningEntityClass() {
		return owningEntityClass;
	}

	public final void setOwningEntityClass(Class<?> owningEntityClass) {
		this.owningEntityClass = owningEntityClass;
	}

	public final String getAttributeFullName() {
		return attributeFullName;
	}


}
