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

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;
import org.nebulae2us.stardust.my.domain.ValueObjectAttribute;

import static org.nebulae2us.stardust.Builders.*;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * 
 * EntityMappingRepository is meant be live for one LinkedEntityBundle; therefore, it's not shared among thread, hence it's not thread safe.
 * 
 * @author Trung Phan
 *
 */
public class EntityMappingRepository {

	private final Map<Pair<Entity, String>, EntityMapping> entityMappings = new HashMap<Pair<Entity, String>, EntityMapping>();
	
	private final Map<String, Map<Object, Object>> mainList = new HashMap<String, Map<Object,Object>>();
	
	private final Map<Pair<String, Field>, Map<Object, Object>> sideList = new HashMap<Pair<String,Field>, Map<Object,Object>>();
	
	public List<?> readData(LinkedEntityBundle bundle, DataReader dataReader) {
		
		LinkedEntity rootLinkedEntity = bundle.getRoot();
		
		List<Object> result = new ArrayList<Object>();
		
		EntityMapping rootEntityMapping = getEntityMapping(rootLinkedEntity.getEntity(), rootLinkedEntity.getAlias(), dataReader);
		Map<Object, Object> rootCache = getMainCache(rootLinkedEntity.getAlias());

		Map<String, Object> alias2Instance = new HashMap<String, Object>();
		
		while (dataReader.next()) {
			alias2Instance.clear();
			
			int sizeBefore = rootCache.size();
			
			Object rootEntityInstance = readObject(rootLinkedEntity.getAlias(), rootEntityMapping, dataReader);
			alias2Instance.put(rootLinkedEntity.getAlias(), rootEntityInstance);
			
			int sizeAfter = rootCache.size();
			if (sizeAfter > sizeBefore) {
				result.add(rootEntityInstance);
			}
			
			for (LinkedEntity linkedEntity : bundle.getNonRoots()) {
				Object parentInstance = alias2Instance.get(linkedEntity.getParent().getAlias());
				if (parentInstance != null) {
					EntityMapping entityMapping = getEntityMapping(linkedEntity.getEntity(), linkedEntity.getAlias(), dataReader);
					Object entityInstance = readObject(linkedEntity.getAlias(), entityMapping, dataReader);
					alias2Instance.put(linkedEntity.getAlias(), entityInstance);
					
					EntityAttribute entityAttribute = linkedEntity.getAttribute();
					switch (entityAttribute.getRelationalType()) {
					case MANY_TO_ONE:
						setValue(entityAttribute.getField(), parentInstance, entityInstance);
						if (ObjectUtils.notEmpty(entityAttribute.getInverseAttributeName())) {
							Attribute foreignAttribute = entityAttribute.getEntity().getAttribute(entityAttribute.getInverseAttributeName());
							AssertState.isTrue(foreignAttribute instanceof EntityAttribute, "inverse attribute is invalid");
							EntityAttribute foreignEntityAttribute = (EntityAttribute)foreignAttribute;
							Object values = getValue(foreignEntityAttribute.getField(), entityInstance);
							if (values == null) {
								values = newCollectionInstance(foreignEntityAttribute.getField().getType());
								setValue(foreignEntityAttribute.getField(), entityInstance, values);
							}
							AssertState.isTrue(values instanceof Collection, "Unknown collection type %s", values.getClass());
							((Collection)values).add(parentInstance);
							
						}
						break;
					case ONE_TO_MANY:
						Object values = getValue(entityAttribute.getField(), parentInstance);
						if (values == null) {
							values = newCollectionInstance(entityAttribute.getField().getType());
							setValue(entityAttribute.getField(), parentInstance, values);
						}
						AssertState.isTrue(values instanceof Collection, "Unknown collection type %s", values.getClass());
						((Collection)values).add(entityInstance);
						
						if (ObjectUtils.notEmpty(entityAttribute.getInverseAttributeName())) {
							Attribute foreignAttribute = entityAttribute.getEntity().getAttribute(entityAttribute.getInverseAttributeName());
							AssertState.isTrue(foreignAttribute instanceof EntityAttribute, "inverse attribute is invalid");
							EntityAttribute foreignEntityAttribute = (EntityAttribute)foreignAttribute;
							setValue(foreignEntityAttribute.getField(), entityInstance, parentInstance);
						}
						break;
					}
					
				}
				
			}
			
		}
		
		return result;
	}
	
	private Object newCollectionInstance(Class<?> expectedClass) {
		if (expectedClass == List.class || expectedClass == Collection.class || expectedClass == ArrayList.class) {
			return new ArrayList<Object>();
		}
		else if (expectedClass == LinkedList.class) {
			return new LinkedList<Object>();
		}
		else if (expectedClass == Vector.class) {
			return new Vector<Object>();
		}
		else if (expectedClass == Set.class || expectedClass == HashSet.class) {
			return new HashSet<Object>();
		}
		else if (expectedClass == LinkedHashSet.class) {
			return new LinkedHashSet<Object>();
		}
		else if (expectedClass == NavigableSet.class || expectedClass == SortedSet.class || expectedClass == TreeSet.class) {
			return new TreeSet<Object>();
		}
		
		throw new IllegalStateException("Unknown collection type: " + expectedClass);
	}
	
	private Map<Object, Object> getMainCache(String alias) {
		Map<Object, Object> result = this.mainList.get(alias);
		if (result == null) {
			result = new HashMap<Object, Object>();
			this.mainList.put(alias, result);
		}
		return result;
	}
	
	private Map<Object, Object> getSideCache(String alias, Field field) {
		Pair<String, Field> key = new Pair<String, Field>(alias, field);
		Map<Object, Object> result = this.sideList.get(key);
		if (result == null) {
			result = new HashMap<Object, Object>();
			this.sideList.put(key, result);
		}
		return result;
	}

	private Object readIdValues(List<ScalarAttributeMapping> identifierAttributeMappings, DataReader dataReader) {
		Object keyValue = null;
		int idCount = identifierAttributeMappings.size();
		if (idCount == 1) {
			ScalarAttributeMapping identifierAttributeMapping = identifierAttributeMappings.get(0);
			keyValue = dataReader.readObject(identifierAttributeMapping.getAttribute().getField().getType(), identifierAttributeMapping.getColumnIndex());
		}
		else if (idCount > 1) {
			keyValue = new Object[idCount];
			for (int i = 0; i < idCount; i++) {
				ScalarAttributeMapping identifierAttributeMapping = identifierAttributeMappings.get(i);
				((Object[])keyValue)[i] = dataReader.readObject(identifierAttributeMapping.getAttribute().getField().getType(), identifierAttributeMapping.getColumnIndex());
			}
		}
		
		if (keyValue != null) {
			return keyValue.getClass().isArray() ? new ArrayWrapper((Object[])keyValue) : keyValue;
		}
		
		return null;
	}
	
	private Object readObject(String alias, EntityMapping entityMapping, DataReader dataReader) {
		
		Map<Object, Object> mainCache = getMainCache(alias);
		
		Object key = readIdValues(entityMapping.getIdentifierAttributeMappings(), dataReader);
		
		Object result = key == null ? null : mainCache.get(key);
		if (result != null) {
			return result;
		}
		
		result = readObject(alias, entityMapping.getEntity().getDeclaringClass(), entityMapping.getAttributeMappings(), dataReader);
		mainCache.put(key, result);
		return result;
		
	}
	
	private void setValue(Field field, Object object, Object value) {
		try {
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	private Object getValue(Field field, Object object) {
		try {
			return field.get(object);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private Object readObject(String alias, Class<?> expectedClass, List<AttributeMapping> attributeMappings, DataReader dataReader) {
		Object result = instantiateObject(expectedClass);
		
		for (AttributeMapping attributeMapping : attributeMappings) {
			
			if (attributeMapping instanceof ScalarAttributeMapping) {
				
				ScalarAttributeMapping scalarAttributeMapping = (ScalarAttributeMapping)attributeMapping;
				Object value = dataReader.readObject(scalarAttributeMapping.getAttribute().getField().getType(), scalarAttributeMapping.getColumnIndex());
				setValue(attributeMapping.getAttribute().getField(), result, value);
			}
			else if (attributeMapping instanceof ValueObjectAttributeMapping) {
				ValueObjectAttributeMapping valueObjectAttributeMapping = (ValueObjectAttributeMapping)attributeMapping;
				Object value = readObject(alias, valueObjectAttributeMapping.getAttribute().getValueObject().getDeclaringClass(), valueObjectAttributeMapping.getAttributeMappings(), dataReader);
				setValue(attributeMapping.getAttribute().getField(), result, value);
			}
			else if (attributeMapping instanceof EntityAttributeMapping) {
				EntityAttributeMapping entityAttributeMapping = (EntityAttributeMapping)attributeMapping;
				
				Object key = readIdValues(entityAttributeMapping.getIdentifierAttributeMappings(), dataReader);
				Map<Object, Object> sideCache = getSideCache(alias, entityAttributeMapping.getAttribute().getField());
				Object value = sideCache.get(key);
				if (value == null) {
					value = readObject(alias, entityAttributeMapping.getAttribute().getEntity().getDeclaringClass(), entityAttributeMapping.getAttributeMappings(), dataReader);
					sideCache.put(key, value);
				}
				setValue(attributeMapping.getAttribute().getField(), result, value);
			}
		}

		return result;
		
	}
	
	
	private Object instantiateObject(Class<?> type) {
		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}
	
	public EntityMapping getEntityMapping(Entity entity, String alias, DataReader dataReader) {
		
		EntityMapping result = entityMappings.get(new Pair<Entity, String>(entity, alias));
		
		if (result != null) {
			return result;
		}
		
		String prefix = alias.length() == 0 ? "" : alias + '_';
		
		EntityMappingBuilder<?> resultBuilder = entityMapping()
				.entity$wrap(entity)
				;
		
		List<ScalarAttribute> identifierAttributes = entity.getEntityIdentifier().getScalarAttributes();
		
		

		boolean hasAllIdentifierColumns = true;
		for (ScalarAttribute identifierAttribute : identifierAttributes) {
			int columnIndex = dataReader.findColumn(prefix + identifierAttribute.getColumn().getName());
			if (columnIndex < 0) {
				hasAllIdentifierColumns = false;
				break;
			}
			
			resultBuilder.identifierAttributeMappings$addScalarAttributeMapping()
					.attribute$wrap(identifierAttribute)
					.columnIndex(columnIndex)
					;
			
		}
		if (!hasAllIdentifierColumns) {
			resultBuilder.setIdentifierAttributeMappings(null);
		}
		
		for (Attribute attribute : entity.getAttributes()) {
			if (attribute instanceof ScalarAttribute) {
				ScalarAttribute scalarAttribute  = (ScalarAttribute)attribute;
				int columnIndex = dataReader.findColumn(prefix + scalarAttribute.getColumn().getName());
				if (columnIndex > 0) {
					resultBuilder.attributeMappings$addScalarAttributeMapping()
							.attribute$wrap(scalarAttribute)
							.columnIndex(columnIndex)
							;
				}
			}
			else if (attribute instanceof ValueObjectAttribute) {
				ValueObjectAttribute valueObjectAttribute = (ValueObjectAttribute)attribute;
				ValueObjectAttributeMappingBuilder<?> compositeAttributeMapping = getValueObjectAttributeMapping(valueObjectAttribute, prefix, dataReader);
				if (compositeAttributeMapping != null) {
					resultBuilder.attributeMappings(compositeAttributeMapping);
				}
			}
			else if (attribute instanceof EntityAttribute) {
				EntityAttribute entityAttribute = (EntityAttribute)attribute;
				if (entityAttribute.isOwningSide()) {
					EntityAttributeMappingBuilder<?> compositeAttributeMapping = getEntityAttributeMapping(entityAttribute, prefix, dataReader);
					if (compositeAttributeMapping != null) {
						resultBuilder.attributeMappings(compositeAttributeMapping);
					}
				}
			}
		}
		
		
		result = resultBuilder.toEntityMapping();
		
		this.entityMappings.put(new Pair<Entity, String>(entity, alias), result);
		
		return result;
	}
	
	private ValueObjectAttributeMappingBuilder<?> getValueObjectAttributeMapping(ValueObjectAttribute parentAttribute, String prefix, DataReader dataReader) {
		
		List<Attribute> attributes = parentAttribute.getValueObject().getAttributes();
		
		List<AttributeMappingBuilder<?>> attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		
		for (Attribute attribute : attributes) {
			if (attribute instanceof ScalarAttribute) {
				ScalarAttribute scalarAttribute = (ScalarAttribute)attribute;
				int columnIndex = dataReader.findColumn(prefix + scalarAttribute.getColumn().getName());
				if (columnIndex > 0) {
					attributeMappings.add(scalarAttributeMapping().attribute$wrap(scalarAttribute).columnIndex(columnIndex));
				}
			}
			else if (attribute instanceof ValueObjectAttribute) {
				ValueObjectAttribute valueObjectAttribute = (ValueObjectAttribute)attribute;
				ValueObjectAttributeMappingBuilder<?> attributeMapping = getValueObjectAttributeMapping(valueObjectAttribute, prefix, dataReader);
				if (attributeMapping != null) {
					attributeMappings.add(attributeMapping);
				}
			}
		}
		
		if (attributeMappings.size() == 0) {
			return null;
		}
		
		return valueObjectAttributeMapping().attribute$wrap(parentAttribute).attributeMappings(attributeMappings);
	}

	private EntityAttributeMappingBuilder<?> getEntityAttributeMapping(EntityAttribute parentAttribute, String prefix, DataReader dataReader) {
		
		EntityAttributeMappingBuilder<?> result = entityAttributeMapping()
				.attribute$wrap(parentAttribute)
				;
		
		List<ScalarAttribute> identifierAttributes = parentAttribute.getEntity().getEntityIdentifier().getScalarAttributes();
		
		boolean hasAllIdentifierColumns = true;
		for (ScalarAttribute identifierAttribute : identifierAttributes) {
			int idx = parentAttribute.getRightColumns().indexOf(identifierAttribute.getColumn());
			AssertState.isTrue(idx >= 0, "rightColumns does not contain id column");

			int columnIndex = dataReader.findColumn(prefix + parentAttribute.getLeftColumns().get(idx).getName());
			if (columnIndex < 0) {
				hasAllIdentifierColumns = false;
				break;
			}
			
			result.identifierAttributeMappings$addScalarAttributeMapping()
					.attribute$wrap(identifierAttribute)
					.columnIndex(columnIndex)
					;
			
		}
		if (!hasAllIdentifierColumns) {
			return null;
		}
		
		List<Attribute> attributes = parentAttribute.getEntity().getEntityIdentifier().getAttributes();
		
		for (Attribute attribute : attributes) {
			if (attribute instanceof ScalarAttribute) {
				ScalarAttribute scalarAttribute = (ScalarAttribute)attribute;
				int columnIndex = dataReader.findColumn(prefix + scalarAttribute.getColumn().getName());
				if (columnIndex > 0) {
					result.attributeMappings$addScalarAttributeMapping()
							.attribute$wrap(scalarAttribute)
							.columnIndex(columnIndex)
							;
				}
			}
			else if (attribute instanceof ValueObjectAttribute) {
				ValueObjectAttribute valueObjectAttribute = (ValueObjectAttribute)attribute;
				ValueObjectAttributeMappingBuilder<?> attributeMapping = getValueObjectAttributeMapping(valueObjectAttribute, prefix, dataReader);
				if (attributeMapping != null) {
					result.attributeMappings(attributeMapping);
				}
			}
		}
		
		return result;
	}
	
}
