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
import org.nebulae2us.stardust.adapter.TypeAdapter;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.generator.ValueGenerator;

/**
 * @author Trung Phan
 *
 */
public class ScalarAttribute extends Attribute {

	private final Class<?> scalarType;
	
	private final TypeAdapter<?, ?> typeAdapter;
	
	private final Column column;
	
	private final ValueGenerator valueGenerator;
	
	public ScalarAttribute(Mirror mirror) {
		super(mirror);
		mirror.bind(this);
		
		this.scalarType = mirror.to(Class.class, "scalarType");
		this.column = mirror.to(Column.class, "column");
		this.valueGenerator = mirror.to(ValueGenerator.class, "valueGenerator");
		this.typeAdapter = mirror.to(TypeAdapter.class, "typeAdapter");
	}

	public Class<?> getScalarType() {
		return scalarType;
	}
	
	public Column getColumn() {
		return column;
	}

	public final ValueGenerator getValueGenerator() {
		return valueGenerator;
	}
	
	public final TypeAdapter<?, ?> getTypeAdapter() {
		return typeAdapter;
	}
	
	public Class<?> getPersistenceType() {
		return typeAdapter == null ? scalarType : typeAdapter.getPersistenceType();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object extractValueForPersistence(Object object) {
		Object result = super.extractValueForPersistence(object);
		return typeAdapter == null ? result : ((TypeAdapter<Object, Object>)typeAdapter).toPersistenceType(result);
	}
	
	@Override
	public String toString() {
		return this.getFullName() + ": " + this.getScalarType().getSimpleName();
	}
	
	public Object convertValueToAttributeType(Object value) {
		return typeAdapter != null ? ((TypeAdapter)typeAdapter).toAttributeType(scalarType, value) : value;
	}

}
