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
 * 
 * This is equivalent to JPA @javax.persistence.MappedSuperclass
 * 
 * @see javax.persistence.MappedSuperclass
 * @author Trung Phan
 *
 */
public class ExtensionDefinition extends SemiEntityDefinition {

	private final Class<?> extClass;
	
	private final Class<?> owningEntityClass;
	
	public ExtensionDefinition(Mirror mirror) {
		super(mirror);
		mirror.bind(this);

		this.extClass = mirror.to(Class.class, "extClass");
		this.owningEntityClass = mirror.to(Class.class, "owningEntityClass");
	}

	public final Class<?> getExtClass() {
		return extClass;
	}

	public final Class<?> getOwningEntityClass() {
		return owningEntityClass;
	}

	
}
