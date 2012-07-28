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


import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;

import org.nebulae2us.electron.Constants;
import org.nebulae2us.electron.internal.util.ClassUtils;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.db.domain.LinkedTableBuilder;
import org.nebulae2us.stardust.db.domain.LinkedTableBundleBuilder;
import org.nebulae2us.stardust.db.domain.TableBuilder;
import org.nebulae2us.stardust.internal.util.ClassToRootClassIterable;
import org.nebulae2us.stardust.internal.util.NameUtils;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.InheritanceType;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;
import static org.nebulae2us.stardust.internal.util.Builders.*;

/**
 * @author Trung Phan
 *
 */
public class ScannerUtils {
	
	private static final List<Class<?>> TYPES_FOR_SCALAR_ATTRIBUTES = new ListBuilder<Class<?>>()
			.add(Constants.SCALAR_TYPES)
			.add(byte[].class, char[].class, Blob.class, Clob.class)
			.toList();

	public static boolean isScalarType(Class<?> fieldClass) {
		return TYPES_FOR_SCALAR_ATTRIBUTES.contains(fieldClass) || fieldClass.isEnum();
	}
	
	public static boolean isScalarType(Field field) {
		Class<?> fieldClass = ClassUtils.getClass(field.getType());

		if (isScalarType(fieldClass)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isEntityType(Class<?> fieldClass) {
		if (isScalarType(fieldClass)) {
			return false;
		}
		if (fieldClass == Object.class) {
			return false;
		}
		if (fieldClass.isInterface() || fieldClass.isAnnotation() || fieldClass.isAnonymousClass() || fieldClass.isArray() || fieldClass.isEnum() || fieldClass.isPrimitive() || fieldClass.isSynthetic()) {
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Get list of strictly super class that has @Entity
	 * 
	 * @param entityClass
	 * @return
	 */
	public static List<Class<?>> getSuperEntityClasses(Class<?> entityClass) {
		Assert.notNull(entityClass, "entityClass cannot be null");

		List<Class<?>> result = new ArrayList<Class<?>>();

		Class<?> c = entityClass;
		while ((c = c.getSuperclass()) != null) {
			if (c.getAnnotation(Entity.class) != null) {
				result.add(0, c);
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * RootEntityClass is a super class with @Entity. If no super class has @Entity, then rootEntityClass = entityClass. Note that entityClass may not have @Entity
	 * 
	 * @param entityClass
	 * @return
	 */
	public static Class<?> getRootEntityClass(Class<?> entityClass) {
		Assert.notNull(entityClass, "entityClass cannot be null");
		
		Class<?> rootEntityClass = entityClass;
		Class<?> c = entityClass;
		while ( (c = c.getSuperclass()) != null) {
			if (c.getAnnotation(Entity.class) != null) {
				rootEntityClass = c;
			}
		}

		return rootEntityClass;
	}
	
	/**
	 * InheritanceType is based on the @Inheritance of the rootEntityClass. If none found, the default value is SINGLE_TABLE.
	 * 
	 * @param rootEntityClass
	 * @return
	 */
	public static InheritanceType determineInheritanceType(final Class<?> rootEntityClass) {
		
		if (rootEntityClass.getAnnotation(Inheritance.class) != null) {
			Inheritance inheritance = rootEntityClass.getAnnotation(Inheritance.class);
			if (inheritance.strategy() != null) {
				switch (inheritance.strategy()) {
				case JOINED:
					return InheritanceType.JOINED;
				case SINGLE_TABLE:
					return InheritanceType.SINGLE_TABLE;
				case TABLE_PER_CLASS:
					throw new IllegalStateException("Inheritance type TABLE_PER_CLASS is not supported.");
				}
				
			}
		}
		
		return InheritanceType.SINGLE_TABLE;
	}

	
	
	/**
	 * Try to determine the default table for this attribute. The classDeclaringAttribute is a super class of entityClass.
	 * In a simple case (inheritance = SINGLE_TABLE), the default class is the primary table for rootEntityClass.
	 * But in a JOINED inheritance case, the default class depends classDeclaringAttribute because the primary table for entityClass is different
	 * from the primary table of rootEntityClass, and also different from an entity class sitting in the middle of entityClass and rootEntityClass.
	 * If the classDeclaringAttribute = entityClass, then the default table is the primary table for entityClass. If the classDeclaringAttribute = rootClass, then
	 * the default table is the primary table of rootEntityClass. If the classDeclaringAttribute is a middle entity, then the default class is the
	 * primary table of that middle entity class.
	 * 
	 * If the classDeclaringAttribute is not an entity, but a class with MappedSuperClass defined, then it inherits the value of the entity before it.
	 * 
	 * @param entityClass
	 * @param attributeName
	 * @return
	 */
	public static TableBuilder<?> getDefaultTable(Class<?> entityClass, String attributeName) {
		
		Class<?> classDeclaringAttribute = entityClass;
		while (classDeclaringAttribute != null) {
			try {
				classDeclaringAttribute.getDeclaredField(attributeName);
				break;
			} catch (SecurityException e) {
				throw new RuntimeException("Fatal error.", e);
			} catch (NoSuchFieldException e) {
				classDeclaringAttribute = classDeclaringAttribute.getSuperclass();
			}
		}
		
		AssertState.notNull(classDeclaringAttribute, "attribute %s does not belong to this entity %s", attributeName, entityClass);
		
		Class<?> rootEntityClass = getRootEntityClass(entityClass);
		InheritanceType inheritanceType = determineInheritanceType(rootEntityClass);
		
		Class<?> entityClassThatDefinePrimaryTable = rootEntityClass;
		if (inheritanceType == InheritanceType.JOINED) {
			if (classDeclaringAttribute == entityClass || classDeclaringAttribute.getAnnotation(Entity.class) != null) {
				entityClassThatDefinePrimaryTable = classDeclaringAttribute;
			}
			else {
				for (Class<?> clazz : new ClassToRootClassIterable(entityClass)) {
					if (clazz == entityClass || clazz.getAnnotation(Entity.class) != null) {
						entityClassThatDefinePrimaryTable = clazz;
					}
					if (classDeclaringAttribute == clazz) {
						break;
					}
				}
			}
		}
		
		javax.persistence.Table table = entityClassThatDefinePrimaryTable.getAnnotation(javax.persistence.Table.class);
		
		String defaultTableName = NameUtils.camelCaseToUpperCase(entityClassThatDefinePrimaryTable.getSimpleName());
		
		if (table != null) {
			return table()
						.name(ObjectUtils.evl(table.name(), defaultTableName))
						.schemaName(table.schema())
						.catalogName(table.catalog());
		}
		else {
			return table()
					.name(defaultTableName);
		}
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
	
		
}
