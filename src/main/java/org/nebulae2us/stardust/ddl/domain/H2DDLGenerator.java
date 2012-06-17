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
import org.nebulae2us.stardust.dialect.Dialect;
import org.nebulae2us.stardust.generator.IdentifierGenerator;
import org.nebulae2us.stardust.generator.IdentityValueRetriever;
import org.nebulae2us.stardust.generator.SequenceIdentifierGenerator;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.EntityIdentifier;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class H2DDLGenerator {
	
	private final Dialect dialect;

	private final Map<Table, String> ddlCreateTables = new HashMap<Table, String>();
	
	private final Map<Column, CreateColumn> createColumnRepository = new HashMap<Column, CreateColumn>();
	
	private final Map<Table, Set<String>> ddlForeignKeyConstraints = new HashMap<Table, Set<String>>();
	
	private final List<String> ddlSequences = new ArrayList<String>();
	
	public H2DDLGenerator(Dialect dialect) {
		this.dialect = dialect;
	}
	
	public List<String> generateTable(EntityRepository entityRepository) {


		List<Class<?>> entityClasses = new ArrayList<Class<?>>();
		for (Entity entity : entityRepository.getAllEntities()) {
			entityClasses.add(entity.getDeclaringClass());
		}
		
		entityClasses = ClassUtils.sortClassesByLevelOfInheritance(entityClasses);
		
		for (Class<?> entityClass : entityClasses) {
			generateCreateTableDDL(entityRepository, entityClass);
			generateSequenceDDL(entityRepository, entityClass);
		}

		for (Class<?> entityClass : entityClasses) {
			generateCreateJunctionTableDDL(entityRepository, entityClass);
			generateForeignKeyConstraint(entityRepository, entityClass);
		}
		
		
		List<String> result = new ArrayList<String>();
		
		for (String sql : ddlCreateTables.values()) {
			result.add(sql);
		}
		
		for (String sql : ddlSequences) {
			result.add(sql);
		}
		
		for (Set<String> foreignKeys : ddlForeignKeyConstraints.values()) {
			for (String sql : foreignKeys) {
				result.add(sql);
			}
		}

		return result;
	}

	private void generateCreateJunctionTableDDL(EntityRepository entityRepository, Class<?> entityClass) {
		
		Entity entity = entityRepository.getEntity(entityClass);
		
		
		for (EntityAttribute entityAttribute : entity.getEntityAttributes()) {
			Table table = entityAttribute.getJunctionTable();
			
			List<CreateColumn> createColumns = new ArrayList<CreateColumn>();

			if (table != null && !ddlCreateTables.containsKey(table)) {
				
				StringBuilder junctionTableBuilder = new StringBuilder();
				junctionTableBuilder.append("create table ").append(table.getName()).append(" (\n");
				
				for (int i = 0; i < entityAttribute.getJunctionLeftColumns().size(); i++) {
					Column column = entityAttribute.getJunctionLeftColumns().get(i);
					Column foreignColumn = entityAttribute.getLeftColumns().get(i);
					CreateColumn foreignCreateColumn = createColumnRepository.get(foreignColumn);
					AssertState.notNull(foreignCreateColumn, "Column not found: ", foreignColumn);
					CreateColumn createColumn = new CreateColumn(column, foreignCreateColumn.getColumnType(), false);
					createColumns.add(createColumn);
					createColumnRepository.put(column, createColumn);
				}
				for (int i = 0; i < entityAttribute.getJunctionRightColumns().size(); i++) {
					Column column = entityAttribute.getJunctionRightColumns().get(i);
					Column foreignColumn = entityAttribute.getRightColumns().get(i);
					CreateColumn foreignCreateColumn = createColumnRepository.get(foreignColumn);
					AssertState.notNull(foreignCreateColumn, "Column not found: ", foreignColumn);
					CreateColumn createColumn = new CreateColumn(column, foreignCreateColumn.getColumnType(), false);
					createColumns.add(createColumn);
					createColumnRepository.put(column, createColumn);
				}

				for (CreateColumn createColumn : createColumns) {
					junctionTableBuilder.append("    ").append(createColumn.toString());
					junctionTableBuilder.append(",\n");
				}

				junctionTableBuilder.append("    constraint pk_").append(table.getName()).append(" primary key (");
				for (CreateColumn createColumn : createColumns) {
					junctionTableBuilder.append(createColumn.getColumn().getName()).append(", ");
				}
				junctionTableBuilder.replace(junctionTableBuilder.length() - 2, junctionTableBuilder.length(), "),\n");
				
				junctionTableBuilder.replace(junctionTableBuilder.length() - 2, junctionTableBuilder.length(), "\n)");

				ddlCreateTables.put(table, junctionTableBuilder.toString());

			}
		}
	}

	private void generateSequenceDDL(EntityRepository entityRepository, Class<?> entityClass) {
		Entity entity = entityRepository.getEntity(entityClass);
		for (ScalarAttribute scalarAttribute : entity.getScalarAttributes()) {
			IdentifierGenerator generator = scalarAttribute.getValueGenerator();
			if (generator instanceof SequenceIdentifierGenerator) {
				SequenceIdentifierGenerator sequenceGenerator = (SequenceIdentifierGenerator)generator;
				String sql = dialect.getSqlToCreateSequence(sequenceGenerator.getName());
				this.ddlSequences.add(sql.toString());
			}
		}
	}
	
	private void generateCreateTableDDL(EntityRepository entityRepository, Class<?> entityClass) {
		
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

		if (entity.getRootEntity() == entity) {
			if (entity.getEntityDiscriminator() != null) {
				ColumnType discriminatorColumnType = getColumnType(entity.getEntityDiscriminator().getValue().getClass(), 0);
				CreateColumn createColumn = new CreateColumn(entity.getEntityDiscriminator().getColumn(), discriminatorColumnType, false);
				createColumns.add(createColumn);
			}
		}
		
		for (CreateColumn createColumn : createColumns) {
			createColumnRepository.put(createColumn.getColumn(), createColumn);
		}
		
		for (LinkedTable linkedTable : entity.getLinkedTableBundle().getLinkedTables()) {
			Table table = linkedTable.getTable();

			if (ddlCreateTables.containsKey(table)) {
				continue;
			}

			Set<CreateColumn> createPrimaryKeys = new LinkedHashSet<CreateColumn>();
			
			if (linkedTable.getParent() != null) {
				for (int i = 0; i < linkedTable.getColumns().size(); i++) {
					Column column = linkedTable.getColumns().get(i);
					
					Column parentColumn = linkedTable.getParentColumns().get(i);
					CreateColumn parentCreateColumn = createColumnRepository.get(parentColumn);
					AssertState.notNull(parentCreateColumn, "parentColumn not found: %s.", parentColumn);
					
					CreateColumn createColumn = new CreateColumn(column, parentCreateColumn.getColumnType(), false);
					createColumnRepository.put(column, createColumn);
					createColumns.add(createColumn);
					createPrimaryKeys.add(createColumn);
				}
			}
			else if (entity.getEntityIdentifier() != null) {
				for (ScalarAttribute scalarAttrbute : entity.getEntityIdentifier().getScalarAttributes()) {
					createPrimaryKeys.add(createColumnRepository.get(scalarAttrbute.getColumn()));
				}
			}
			
			StringBuilder sqlBuilder = new StringBuilder();
			
			sqlBuilder.append("create table ").append(table.getName()).append(" (\n");
			
			for (CreateColumn createColumn : createColumns) {
				if (createColumn.getColumn().getTable().equals(table)) {
					sqlBuilder.append("    ").append(createColumn.toString());
					
					if (createColumn.isIdentity()) {
						sqlBuilder.append(' ').append(dialect.getIdentityDeclare());
					}
					
					sqlBuilder.append(",\n");
				}
			}
			if (createPrimaryKeys.size() > 0) {
				sqlBuilder.append("    constraint pk_").append(table.getName()).append(" primary key (");
				for (CreateColumn createPrimaryKey : createPrimaryKeys) {
					sqlBuilder.append(createPrimaryKey.getColumn().getName()).append(", ");
				}
				sqlBuilder.replace(sqlBuilder.length() - 2, sqlBuilder.length(), "),\n");
			}
			
			sqlBuilder.replace(sqlBuilder.length() - 2, sqlBuilder.length(), "\n)");
			
			ddlCreateTables.put(table, sqlBuilder.toString());

		}
	}
	
	private void generateForeignKeyConstraint(EntityRepository entityRepository, Class<?> entityClass) {
		Entity entity = entityRepository.getEntity(entityClass);
		
		for (LinkedTable linkedTable : entity.getLinkedTableBundle().getLinkedTables()) {
			Table table = linkedTable.getTable();
			if (ddlForeignKeyConstraints.containsKey(table)) {
				continue;
			}
			
			for (EntityAttribute entityAttribute : entity.getEntityAttributes()) {
				if (entityAttribute.isOwningSide() && entityAttribute.getLeftColumns().get(0).getTable().equals(table)) {

					Set<String> foreignKeys = ddlForeignKeyConstraints.get(table);
					if (foreignKeys == null) {
						foreignKeys = new LinkedHashSet<String>();
						ddlForeignKeyConstraints.put(table, foreignKeys);
					}

					StringBuilder referenceBuilder = new StringBuilder(" references ").append(entityAttribute.getRightColumns().get(0).getTable().getName()).append(" (");
					StringBuilder foreignKeyConstraintBuilder = new StringBuilder("alter table ").append(table.getName())
							.append(" add\n    constraint fk_").append(table.getName()).append(foreignKeys.size() + 1).append(" foreign key (");
					
					for (int i = 0; i < entityAttribute.getLeftColumns().size(); i++) {
						Column leftColumn = entityAttribute.getLeftColumns().get(i);
						Column rightColumn = entityAttribute.getRightColumns().get(i);
						referenceBuilder.append(rightColumn.getName()).append(", ");
						foreignKeyConstraintBuilder.append(leftColumn.getName()).append(", ");
					}

					referenceBuilder.replace(referenceBuilder.length() - 2, referenceBuilder.length(), ")");
					foreignKeyConstraintBuilder.replace(foreignKeyConstraintBuilder.length() - 2, foreignKeyConstraintBuilder.length(), ")")
						.append(referenceBuilder);
					
					foreignKeys.add(foreignKeyConstraintBuilder.toString());

				}
			}

			if (linkedTable.getParent() != null) {
				StringBuilder referenceBuilder = new StringBuilder(" references ").append(linkedTable.getParent().getTable().getName()).append(" (");
				StringBuilder foreignKeyConstraintBuilder = new StringBuilder("alter table ").append(table.getName())
						.append(" add\n    constraint fk_").append(table.getName()).append(" foreign key (");
				
				for (int i = 0; i < linkedTable.getColumns().size(); i++) {
					Column column = linkedTable.getColumns().get(i);
					Column parentColumn = linkedTable.getParentColumns().get(i);
					
					referenceBuilder.append(parentColumn.getName()).append(", ");
					foreignKeyConstraintBuilder.append(column.getName()).append(", ");
				}
				
				referenceBuilder.replace(referenceBuilder.length() - 2, referenceBuilder.length(), ")");
				foreignKeyConstraintBuilder.replace(foreignKeyConstraintBuilder.length() - 2, foreignKeyConstraintBuilder.length(), ")")
					.append(referenceBuilder);
				
				Set<String> foreignKeys = ddlForeignKeyConstraints.get(table);
				if (foreignKeys == null) {
					foreignKeys = new LinkedHashSet<String>();
					ddlForeignKeyConstraints.put(table, foreignKeys);
				}
				foreignKeys.add(foreignKeyConstraintBuilder.toString());
				
			}

		}		
	}
	
	protected CreateColumn newCreateColumn(ScalarAttribute scalarAttribute) {
		return new CreateColumn(scalarAttribute.getColumn(), getColumnType(scalarAttribute.getScalarType(), 0),  scalarAttribute.getValueGenerator() instanceof IdentityValueRetriever);
	}
	
	protected List<CreateColumn> newCreateColumns(EntityAttribute entityAttribute) {
		
		List<CreateColumn> result = new ArrayList<CreateColumn>();
		
		EntityIdentifier identifier = entityAttribute.getEntity().getEntityIdentifier();
		int numColumns = identifier.getScalarAttributes().size();
		for (int i = 0; i < numColumns; i++) {
			ScalarAttribute scalarAttribute = identifier.getScalarAttributes().get(i);
			result.add(new CreateColumn(entityAttribute.getLeftColumns().get(i), getColumnType(scalarAttribute.getScalarType(), 0) , false));
		}
		
		return result;
	}
	
	
	protected ColumnType getColumnType(Class<?> javaType, int length) {
		
		if (javaType == String.class) {
			return new ColumnType("varchar", length == 0? 255 : length);
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
		else if (javaType == byte[].class) {
			return new ColumnType("blob");
		}
		else if (javaType.isEnum()) {
			return new ColumnType("varchar", length == 0 ? 255 : length);
		}
		
		throw new IllegalStateException("Unknown type: " + javaType.getSimpleName());
	}
	
}
