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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

import org.nebulae2us.electron.Converter;
import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.MapBuilder;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;
import org.nebulae2us.stardust.my.domain.ValueObject;
import org.nebulae2us.stardust.my.domain.ValueObjectAttribute;

/**
 * @author Trung Phan
 *
 */
public class DataReaderMirror implements Mirror {

	private final DataReader dataReader;
	
	private final Map<String, AttributeMapping> attributeMappings;
	
	private final Converter converter = new Converter(null, true, Collections.EMPTY_LIST);
	
	public DataReaderMirror(DataReader dataReader, List<AttributeMapping> attributeMappings) {
		this.dataReader = dataReader;
		MapBuilder<String, AttributeMapping> attrMapBuilder = new MapBuilder<String, AttributeMapping>();
		for (AttributeMapping attrMap : attributeMappings) {
			attrMapBuilder.put(attrMap.getAttribute().getName(), attrMap);
		}
		this.attributeMappings = attrMapBuilder.toMap();
	}

	public boolean exists(String fieldName) {
		return this.attributeMappings.containsKey(fieldName);
	}

	public <T> T to(Class<T> objectClass, String fieldName) {
		if (!exists(fieldName)) {
			return null;
		}
		
		AttributeMapping attributeMapping = this.attributeMappings.get(fieldName);
		if (attributeMapping instanceof ScalarAttributeMapping) {
			ScalarAttributeMapping scalarAttributeMapping = (ScalarAttributeMapping)attributeMapping;
			ScalarAttribute scalarAttribute = scalarAttributeMapping.getAttribute();
			Object value = dataReader.read(scalarAttribute.getPersistenceType(), scalarAttributeMapping.getColumnIndex());
			value = scalarAttribute.convertValueToAttributeType(value);
			if (value == null) {
				return null;
			}
			else if (objectClass.isInstance(value)) {
				return (T)value;
			}
			else {
				T newValue = this.converter.convert(value).to(objectClass);
				return newValue;
			}
		}
		else if (attributeMapping instanceof ValueObjectAttributeMapping) {
			ValueObjectAttributeMapping valueObjectAttributeMapping = (ValueObjectAttributeMapping)attributeMapping;
			ValueObjectAttribute valueObjectAttribute = valueObjectAttributeMapping.getAttribute();
			ValueObject valueObject = valueObjectAttribute.getValueObject();
			Class<?> valueObjectClass = valueObject.getDeclaringClass();
			
			
			
			throw new IllegalStateException("Not yet implemented feature.");
		}
		else if (attributeMapping instanceof EntityAttributeMapping) {
			throw new IllegalStateException("Entity within Embeddable is not supported.");
		}
		else {
			throw new IllegalStateException("Unknown AttributeMapping: " + attributeMapping);
		}
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toListOf(java.lang.Class, java.lang.String)
	 */
	public <T> List<T> toListOf(Class<T> objectClass, String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toSortedSetOf(java.lang.Class, java.lang.String)
	 */
	public <T> NavigableSet<T> toSortedSetOf(Class<T> objectClass,
			String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toSetOf(java.lang.Class, java.lang.String)
	 */
	public <T> Set<T> toSetOf(Class<T> objectClass, String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toMapOf(java.lang.Class, java.lang.Class, java.lang.String)
	 */
	public <K, V> Map<K, V> toMapOf(Class<K> keyClass, Class<V> valueClass,
			String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toMultiValueMapOf(java.lang.Class, java.lang.Class, java.lang.String)
	 */
	public <K, V> Map<K, List<V>> toMultiValueMapOf(Class<K> keyClass,
			Class<V> valueClass, String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toObject(java.lang.String)
	 */
	public Object toObject(String fieldName) {
		return to(Object.class, fieldName);
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toString(java.lang.String)
	 */
	public String toString(String fieldName) {
		return to(String.class, fieldName);
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toInteger(java.lang.String)
	 */
	public Integer toInteger(String fieldName) {
		return Converter.convertToBasicType(Integer.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toIntValue(java.lang.String)
	 */
	public int toIntValue(String fieldName) {
		return Converter.convertToBasicType(int.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toLong(java.lang.String)
	 */
	public Long toLong(String fieldName) {
		return Converter.convertToBasicType(Long.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toLongValue(java.lang.String)
	 */
	public long toLongValue(String fieldName) {
		return Converter.convertToBasicType(long.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toShort(java.lang.String)
	 */
	public Short toShort(String fieldName) {
		return Converter.convertToBasicType(Short.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toShortValue(java.lang.String)
	 */
	public short toShortValue(String fieldName) {
		return Converter.convertToBasicType(short.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toByte(java.lang.String)
	 */
	public Byte toByte(String fieldName) {
		return Converter.convertToBasicType(Byte.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toByteValue(java.lang.String)
	 */
	public byte toByteValue(String fieldName) {
		return Converter.convertToBasicType(byte.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toDouble(java.lang.String)
	 */
	public Double toDouble(String fieldName) {
		return Converter.convertToBasicType(Double.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toDoubleValue(java.lang.String)
	 */
	public double toDoubleValue(String fieldName) {
		return Converter.convertToBasicType(double.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toFloat(java.lang.String)
	 */
	public Float toFloat(String fieldName) {
		return Converter.convertToBasicType(Float.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toFloatValue(java.lang.String)
	 */
	public float toFloatValue(String fieldName) {
		return Converter.convertToBasicType(float.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toBoolean(java.lang.String)
	 */
	public Boolean toBoolean(String fieldName) {
		return Converter.convertToBasicType(Boolean.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toBooleanValue(java.lang.String)
	 */
	public boolean toBooleanValue(String fieldName) {
		return Converter.convertToBasicType(boolean.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toCharacter(java.lang.String)
	 */
	public Character toCharacter(String fieldName) {
		return Converter.convertToBasicType(Character.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#toCharValue(java.lang.String)
	 */
	public char toCharValue(String fieldName) {
		return Converter.convertToBasicType(char.class, toObject(fieldName));
	}

	/* (non-Javadoc)
	 * @see org.nebulae2us.electron.Mirror#bind(java.lang.Object)
	 */
	public void bind(Object object) {
	}
	
}
