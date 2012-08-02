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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EmbeddedId;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.generator.IdentityValueRetriever;
import org.nebulae2us.stardust.generator.SequenceValueGenerator;
import org.nebulae2us.stardust.internal.util.ObjectUtils;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class AnnotationEntityDefinitionBuilder extends AbstractAnnotationDefinitionBuilder {

	private final static List<String> OUTER_CLASS_THIS_FIELD = new ListBuilder<String>().add("this$0", "this$1", "this$2", "this$3", "this$4", "this$5", "this$6", "this$7", "this$8", "this$9", "this$10", "this$11", "this$12").toList();
	
	private final Class<?> entityClass;
	
	public AnnotationEntityDefinitionBuilder(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	
	public EntityDefinition toEntityDefinition() {
		
		EntityDefinitionBuilder builder = new EntityDefinitionBuilder(entityClass);
		
		if (entityClass.getAnnotation(Table.class) != null) {
			Table table = entityClass.getAnnotation(Table.class);
			String tableName = getTableName(table.name(), table.schema(), table.catalog());
			builder.table(tableName);
		}
		
		List<SecondaryTable> secondaryTables = new ListBuilder<SecondaryTable>()
				.addNonNullElements(entityClass.getAnnotation(SecondaryTable.class))
				.addNonNullElements(entityClass.getAnnotation(SecondaryTables.class) == null ? null : entityClass.getAnnotation(SecondaryTables.class).value())
				.toList();

		if (secondaryTables.size() > 0) {
			for (SecondaryTable secondaryTable : secondaryTables) {
				AssertSyntax.notEmpty(secondaryTable.name(), "Secondary table's name cannot be empty");
				String secondaryTableName = getTableName(secondaryTable.name(), secondaryTable.schema(), secondaryTable.catalog());

				List<String> joins = new ArrayList<String>();
				if (secondaryTable.pkJoinColumns().length > 0) {
					for (PrimaryKeyJoinColumn pkJoinColumn : secondaryTable.pkJoinColumns()) {
						StringBuilder join = new StringBuilder();
						if (ObjectUtils.isEmpty(pkJoinColumn.referencedColumnName())) {
							join.append("this.[default id]");
						}
						else {
							join.append("this.").append(pkJoinColumn.referencedColumnName());
						}
						
						join.append(" = that.");
						if (ObjectUtils.isEmpty(pkJoinColumn.name())) {
							join.append("[default id]");
						}
						else {
							join.append(pkJoinColumn.name());
						}
						joins.add(join.toString());
					}
					
				}
				builder.joinSecondaryTable(secondaryTableName).on(joins.toArray(new String[0]));
			}
		}
		
		if (entityClass.getAnnotation(DiscriminatorColumn.class) != null) {
			DiscriminatorColumn discriminatorColumn = entityClass.getAnnotation(DiscriminatorColumn.class);
			builder.discriminatorColumn(discriminatorColumn.name());
		}
		
		if (entityClass.getAnnotation(DiscriminatorValue.class) != null) {
			DiscriminatorValue discriminatorValue = entityClass.getAnnotation(DiscriminatorValue.class);
			String value = discriminatorValue.value();
			DiscriminatorType type = DiscriminatorType.STRING;
			if (entityClass.getAnnotation(DiscriminatorColumn.class) != null) {
				type = entityClass.getAnnotation(DiscriminatorColumn.class).discriminatorType();
			}
			if (type == DiscriminatorType.INTEGER) {
				builder.discriminatorValue(Integer.valueOf(value));
			}
			else if (type == DiscriminatorType.CHAR) {
				builder.discriminatorValue(value.charAt(0));
			}
			else {
				builder.discriminatorValue(value);
			}
		}
		
		if (entityClass.getAnnotation(Inheritance.class) != null) {
			Inheritance inheritance = entityClass.getAnnotation(Inheritance.class);
			if (inheritance.strategy() == InheritanceType.SINGLE_TABLE) {
				builder.inheritanceType(org.nebulae2us.stardust.my.domain.InheritanceType.SINGLE_TABLE);
			}
			else if (inheritance.strategy() == InheritanceType.JOINED) {
				builder.inheritanceType(org.nebulae2us.stardust.my.domain.InheritanceType.JOINED);
			}
			else {
				throw new IllegalStateException("Unsupported inheritance strategy: " + inheritance.strategy());
			}
		}

		
		for (Field field : entityClass.getDeclaredFields()) {
			if ((field.getModifiers() & Modifier.TRANSIENT) > 0 || field.getAnnotation(Transient.class) != null || (field.getModifiers() & Modifier.NATIVE) > 0 ||
					(field.getModifiers() & Modifier.STATIC) > 0 || OUTER_CLASS_THIS_FIELD.contains(field.getName())) {
				builder.excludeAttributes(field.getName());
				continue;
			}
			
			if (field.getAnnotation(Id.class) != null) {
				builder.identifier(field.getName());
			}
			
			if (field.getAnnotation(EmbeddedId.class) != null) {
				builder.identifier(field.getName());
				builder.embedAttributes(field.getName());
			}
			
			if (field.getAnnotation(GeneratedValue.class) != null && field.getAnnotation(Id.class) != null) {
				GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
				switch (generatedValue.strategy()) {
				case IDENTITY:
					builder.identifierGenerator(field.getName(), IdentityValueRetriever.getInstance());
					break;
				case SEQUENCE:
					SequenceGenerator sequenceGenerator = searchForSequenceGeneratorAnnot(generatedValue.generator(), entityClass);
					AssertSyntax.notNull(sequenceGenerator, "Cannot find definition for sequence generator \"%s\". Hint: define @SequenceGenerator.", generatedValue.generator());

					String schemaName = "";
					try {
						schemaName = sequenceGenerator.schema();
					}
					catch (NoSuchMethodError e) {
						// happen if this is jpa-api version 1.0
					}
					
					
					builder.identifierGenerator(field.getName(), new SequenceValueGenerator(schemaName, sequenceGenerator.sequenceName()));
					break;
				default:
					AssertSyntax.fail("Unsupported identifier generator strategy %s.", generatedValue.strategy().toString());
					break;
				}
			}
		
			if (scanFieldForEmbedded(builder, field)) {
				continue;
			}
			
			scanFieldForColumnInfo(builder, field);
			scanFieldForAdapter(builder, field);
			scanFieldForRelationship(builder, field);
		}
		
		return builder.toEntityDefinition();
	}
	
	private SequenceGenerator searchForSequenceGeneratorAnnot(String name, Class<?> aClass) {
		SequenceGenerator generator = (SequenceGenerator)aClass.getAnnotation(SequenceGenerator.class);
		if (generator != null && generator.name().equals(name)) {
			return generator;
		}
		
		for (Field field : aClass.getDeclaredFields()) {
			generator = field.getAnnotation(SequenceGenerator.class);
			
			if (generator != null && generator.name().equals(name)) {
				return generator;
			}
		}

		Class<?> superClass = aClass.getSuperclass();
		if (superClass != null && superClass != Object.class) {
			return searchForSequenceGeneratorAnnot(name, superClass);
		}

		return null;
	}

	
	
	@Override
	protected Column getColumnAnnot(Field field) {
		return field.getAnnotation(Column.class);
	}
	
}
