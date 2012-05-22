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
package org.nebulae2us.stardust.ddl.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nebulae2us.electron.internal.util.ClassUtils;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.LinkedTable;
import org.nebulae2us.stardust.db.domain.Table;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.EntityIdentifier;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.my.domain.InheritanceType;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;

/**
 * @author Trung Phan
 *
 */
public class H2DDLGenerator {

	
	public String generateTable(EntityRepository entityRepository) throws Exception {

		Class.forName("org.h2.Driver");
		
		Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");


		Map<Table, String> ddlCreateTables = new HashMap<Table, String>();
		
		List<Class<?>> entityClasses = new ArrayList<Class<?>>();
		for (Entity entity : entityRepository.getAllEntities()) {
			entityClasses.add(entity.getDeclaringClass());
		}
		
		entityClasses = ClassUtils.sortClassesByLevelOfInheritance(entityClasses);
		
		for (Class<?> entityClass : entityClasses) {
			
			Entity entity = entityRepository.getEntity(entityClass);
			
			Set<CreateColumn> createColumns = new LinkedHashSet<CreateColumn>();
			
			for (ScalarAttribute scalarAttribute : entity.getScalarAttributes()) {
				createColumns.add(newCreateColumn(scalarAttribute));
			}

			for (EntityAttribute entityAttribute : entity.getEntityAttributes()) {
				if (entityAttribute.isOwningSide()) {
					createColumns.addAll(newCreateColumns(entityAttribute));
				}
			}
			
			for (LinkedTable linkedTable : entity.getLinkedTableBundle().getLinkedTables()) {
				Table table = linkedTable.getTable();
				if (ddlCreateTables.containsKey(table)) {
					continue;
				}
				
				StringBuilder sqlBuilder = new StringBuilder();
				
				sqlBuilder.append("create table ").append(table.getName()).append(" (");

				for (CreateColumn createColumn : createColumns) {
					if (createColumn.getColumn().getTable().equals(table)) {
						sqlBuilder.append(createColumn.toString());
						sqlBuilder.append(", ");
					}
				}
				sqlBuilder.replace(sqlBuilder.length() - 2, sqlBuilder.length(), ")");
				
				ddlCreateTables.put(table, sqlBuilder.toString());

				System.out.println(sqlBuilder.toString());
				
		        Statement sst = conn.createStatement(); 
		        sst.executeUpdate(sqlBuilder.toString()); 
			}			
			
			
		}


		

		return "";
	}
	
	protected CreateColumn newCreateColumn(ScalarAttribute scalarAttribute) {
		return new CreateColumn(scalarAttribute.getColumn(), getColumnType(scalarAttribute.getScalarType(), 0));
	}
	
	protected List<CreateColumn> newCreateColumns(EntityAttribute entityAttribute) {
		
		List<CreateColumn> result = new ArrayList<CreateColumn>();
		
		EntityIdentifier identifier = entityAttribute.getEntity().getEntityIdentifier();
		int numColumns = identifier.getScalarAttributes().size();
		for (int i = 0; i < numColumns; i++) {
			ScalarAttribute scalarAttribute = identifier.getScalarAttributes().get(i);
			result.add(new CreateColumn(entityAttribute.getLeftColumns().get(i), getColumnType(scalarAttribute.getScalarType(), 0) ));
		}
		
		return result;
	}
	
	
	protected ColumnType getColumnType(Class<?> javaType, int length) {
		
		if (javaType == String.class) {
			return new ColumnType("varchar", length == 0? 100 : length);
		}
		else if (javaType == Date.class) {
			return new ColumnType("timestamp");
		}
		else if (javaType == Integer.class || javaType == int.class) {
			return new ColumnType("int");
		}
		else if (javaType == Long.class || javaType == long.class) {
			return new ColumnType("bigint");
		}
		else if (javaType == Double.class || javaType == double.class) {
			return new ColumnType("double");
		}
		else if (javaType == Float.class || javaType == float.class) {
			return new ColumnType("real");
		}
		else if (javaType == Boolean.class || javaType == boolean.class) {
			return new ColumnType("boolean");
		}
		else if (javaType.isEnum()) {
			return new ColumnType("varchar", length == 0 ? 100 : length);
		}
		
		throw new IllegalStateException("Unknown type: " + javaType.getSimpleName());
	}
	
}
