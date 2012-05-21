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
package org.nebulae2us.stardust.my.domain.scanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import org.nebulae2us.electron.util.ImmutableMap;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.electron.util.ObjectEqualityComparator;
import org.nebulae2us.stardust.db.domain.ColumnBuilder;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.EntityBuilder;

import static org.nebulae2us.stardust.Builders.*;

/**
 * @author Trung Phan
 *
 */
public class AttributeOverrideScanner {

	private final Map<String, ColumnBuilder<?>> columns;
	
	public AttributeOverrideScanner() {
		this.columns = new ImmutableMap<String, ColumnBuilder<?>>();
	}
	
	public AttributeOverrideScanner(EntityBuilder<?> owningEntity, AttributeOverride attributeOverride, AttributeOverrides attributeOverrides) {
		this.columns = new HashMap<String, ColumnBuilder<?>>();
		
		List<AttributeOverride> overrides = new ListBuilder<AttributeOverride>()
				.addNonNullElements(attributeOverride)
				.addNonNullElements(attributeOverrides == null ? null : attributeOverrides.value())
				.toList()
				;
		
		for (AttributeOverride override : overrides) {
			ColumnBuilder<?> column = column()
						.name(override.column().name());
			if (ObjectUtils.isEmpty(override.column().table())) {
				column.table(owningEntity.getLinkedTableBundle().getRoot().getTable());
			}
			else {
				column.table$begin()
					.name(override.column().table())
				.end();
			}
			
			columns.put(override.name(), column);
		}
	}
	
	private AttributeOverrideScanner(Map<String, ColumnBuilder<?>> columns) {
		this.columns = new ImmutableMap<String, ColumnBuilder<?>>(columns, ObjectEqualityComparator.getInstance());
	}

	
	
	public Map<String, ColumnBuilder<?>> getColumns() {
		return columns;
	}

	public AttributeOverrideScanner sub(String name) {
		if (columns.isEmpty()) {
			return this;
		}
		
		String _name = name + ".";
		
		Map<String, ColumnBuilder<?>> newColumnMap = new HashMap<String, ColumnBuilder<?>>();
		
		for (Entry<String, ColumnBuilder<?>> entry : this.columns.entrySet()) {
			if (entry.getKey().startsWith(_name)) {
				newColumnMap.put(entry.getKey().substring(_name.length()), entry.getValue());
			}
		}
		
		return new AttributeOverrideScanner(newColumnMap);
	}

	/**
	 * @param table
	 * @param annotation
	 * @param annotation2
	 * @return
	 */
	public AttributeOverrideScanner combine(String defaultTableName, AttributeOverride attributeOverride, AttributeOverrides attributeOverrides, boolean overrideMode) {
		HashMap<String, ColumnBuilder<?>> newColumns = new HashMap<String, ColumnBuilder<?>>(this.columns);
		
		List<AttributeOverride> overrides = new ListBuilder<AttributeOverride>()
				.addNonNullElements(attributeOverride)
				.addNonNullElements(attributeOverrides == null ? null : attributeOverrides.value())
				.toList()
				;
		
		for (AttributeOverride override : overrides) {
			ColumnBuilder<?> column = column()
						.name(override.column().name());
			if (ObjectUtils.isEmpty(override.column().table())) {
				column.table$begin()
					.name(defaultTableName);
			}
			else {
				column.table$begin()
					.name(override.column().table())
				.end();
			}

			if (overrideMode || !newColumns.containsKey(override.name())) {
				newColumns.put(override.name(), column);
			}
		}
		
		return new AttributeOverrideScanner(newColumns);
	}
	
}
