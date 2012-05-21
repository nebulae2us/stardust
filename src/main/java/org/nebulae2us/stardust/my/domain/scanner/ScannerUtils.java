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

import static org.nebulae2us.stardust.Builders.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;

import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.db.domain.LinkedTableBuilder;
import org.nebulae2us.stardust.db.domain.LinkedTableBundleBuilder;
import org.nebulae2us.stardust.internal.util.NameUtils;
import org.nebulae2us.stardust.my.domain.InheritanceType;

/**
 * @author Trung Phan
 *
 */
public class ScannerUtils {
	
	private static LinkedTableBundleBuilder<?> _extractTableInfo(Class<?> entityClass) {
		

		LinkedTableBundleBuilder<?> result = linkedTableBundle();
		
		javax.persistence.Table table = entityClass.getAnnotation(javax.persistence.Table.class);
		
		if (table != null) {
			result
				.linkedTables$addLinkedTable()
					.table$begin()
						.name(table.name())
						.schemaName(table.schema())
						.catalogName(table.catalog())
					.end()
				.end();
		}
		else {
			result
			.linkedTables$addLinkedTable()
				.table$begin()
					.name(NameUtils.camelCaseToUpperCase(entityClass.getSimpleName()))
				.end()
			.end();
		}

		ArrayList<SecondaryTable> secondaryTables = new ArrayList<SecondaryTable>();
		if (entityClass.getAnnotation(SecondaryTable.class) != null) {
			secondaryTables.add(entityClass.getAnnotation(SecondaryTable.class));
		}
		else if (entityClass.getAnnotation(SecondaryTables.class) != null) {
			secondaryTables.addAll(
					Arrays.asList(entityClass.getAnnotation(SecondaryTables.class).value())
			);
		}
		
		for (SecondaryTable secondaryTable : secondaryTables) {
			LinkedTableBuilder<?> linkedTable = result.linkedTables$addLinkedTable()
				.joinType(JoinType.LEFT_JOIN)
				.parent(result.getRoot())
				.table$begin()
					.name(secondaryTable.name())
					.schemaName(secondaryTable.schema())
					.catalogName(secondaryTable.catalog())
				.end()
				;
			
			if (secondaryTable.pkJoinColumns() != null || secondaryTable.pkJoinColumns().length > 0) {
				for (PrimaryKeyJoinColumn pk : secondaryTable.pkJoinColumns()) {
					
					linkedTable
						.columns$addColumn()
							.table(linkedTable.getTable())
							.name(pk.name())
						.end()
						;
					
					linkedTable
						.parentColumns$addColumn()
							.table(linkedTable.getParent().getTable())
							.name(pk.referencedColumnName())
						.end()
						;
				}
			}
		}
		
		
		return result;
	}
	
	
	
	public static LinkedTableBundleBuilder<?> extractTableInfo(Class<?> entityClass, Class<?> rootEntityClass, InheritanceType inheritanceType) {
		
		LinkedTableBundleBuilder<?> result = _extractTableInfo(rootEntityClass);
		
		if (entityClass == rootEntityClass || inheritanceType == InheritanceType.SINGLE_TABLE) {
			return result;
		}
		
		List<Class<?>> subEntityClasses = new ArrayList<Class<?>>();
		Class<?> c = entityClass;
		while (c != null && c != rootEntityClass) {
			if (c.getAnnotation(Entity.class) != null) {
				subEntityClasses.add(c);
			}
			c = c.getSuperclass();
		}
		
		Collections.reverse(subEntityClasses);
		
		for (Class<?> subEntityClass : subEntityClasses) {
			LinkedTableBundleBuilder<?> linkedTableBundle = _extractTableInfo(subEntityClass);
			
			LinkedTableBuilder<?> subEntityRootLinkedTable = linkedTableBundle.getRoot();
			subEntityRootLinkedTable.parent(result.getRoot())
				.joinType(JoinType.LEFT_JOIN);
			
			
			List<PrimaryKeyJoinColumn> pkColumns = new ArrayList<PrimaryKeyJoinColumn>();
			if (subEntityClass.getAnnotation(PrimaryKeyJoinColumn.class) != null) {
				pkColumns.add(subEntityClass.getAnnotation(PrimaryKeyJoinColumn.class));
			}
			else if (subEntityClass.getAnnotation(PrimaryKeyJoinColumns.class) != null) {
				pkColumns.addAll(Arrays.asList(subEntityClass.getAnnotation(PrimaryKeyJoinColumns.class).value()));
			}
			
			for (PrimaryKeyJoinColumn pkColumn : pkColumns) {
				subEntityRootLinkedTable
					.columns$addColumn()
						.name(pkColumn.name())
						.table(subEntityRootLinkedTable.getTable())
					.end();
			}
			
			result.linkedTables(linkedTableBundle.getLinkedTables());
		}
		
		
		
		return result;

	}

}
