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
package org.nebulae2us.stardust.my.domain;

import org.nebulae2us.electron.Mirror;

/**
 * @author Trung Phan
 *
 */
public class ValueObject extends AttributeHolder {

	protected final ValueObject superValueObject;
	
	public ValueObject(Mirror mirror) {
		super(mirror);
		mirror.bind(this);
		
		this.superValueObject = mirror.to(ValueObject.class, "superValueObject");
	}

	public ValueObject getSuperValueObject() {
		return superValueObject;
	}

	@Override
    public String toString() {
    	return "VO: " + declaringClass.getSimpleName();
    }
	
}
