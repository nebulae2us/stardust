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
package org.nebulae2us.stardust.translate.domain;

import java.util.ArrayList;
import java.util.List;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.stardust.dao.NullObject;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.LinkedTable;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.InsertEntityExpression;
import org.nebulae2us.stardust.generator.IdentityValueRetriever;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.EntityDiscriminator;
import org.nebulae2us.stardust.my.domain.EntityIdentifier;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntity;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;

/**
 * @author Trung Phan
 *
 */
public class InsertEntityTranslator implements Translator {

	public boolean accept(Expression expression, ParamValues paramValues) {
		return expression instanceof InsertEntityExpression;
	}

	public Pair<String, List<?>> translate(TranslatorContext context,
			Expression expression, ParamValues paramValues) {
		
		InsertEntityExpression insertEntityExpression = (InsertEntityExpression)expression;
		int tableIndex = insertEntityExpression.getTableIndex();
		
		LinkedTableEntityBundle linkedTableEntityBundle = context.getLinkedTableEntityBundle();
		
		Entity entity = context.getLinkedEntityBundle().getRoot().getEntity();
		
		LinkedTable linkedTable = entity.getLinkedTableBundle().getLinkedTables().get(tableIndex);
		
		LinkedTableEntity linkedTableEntity = linkedTableEntityBundle.findLinkedTableEntity(linkedTable.getTable());

		Object entityToInsert = paramValues.getNextWildcardValue();
		
		List<Object> values = new ArrayList<Object>();
		
		StringBuilder insertSql = new StringBuilder();
		StringBuilder wildcardBuilder = new StringBuilder();
		
		insertSql.append("insert into " + linkedTableEntity.getTable()).append(" (");
		wildcardBuilder.append("\n    values (");
		
		EntityDiscriminator entityDiscriminator = entity.getEntityDiscriminator();
		if (tableIndex == 0 && entityDiscriminator != null) {
			insertSql.append(entityDiscriminator.getColumn().getName()).append(", ");
			wildcardBuilder.append("?, ");
			Object value = entityDiscriminator.getValue();
			values.add(value);
		}
		
		if (!linkedTableEntity.isRoot()) {
			EntityIdentifier identifier = entity.getEntityIdentifier();
			List<ScalarAttribute> identifierScalarAttributes = identifier.getScalarAttributes();
			
			for (int i = 0; i < linkedTableEntity.getColumns().size(); i++) {
				Column idColumn = linkedTableEntity.getColumns().get(i);
				ScalarAttribute identifierScalarAttribute = identifierScalarAttributes.get(i);

				insertSql.append(idColumn.getName())
					.append(", ");
				wildcardBuilder.append("?, ");

				Object value = identifierScalarAttribute.extractValueForPersistence(entityToInsert);
				if (value != null) {
					values.add(value);
				}
				else {
					values.add(NullObject.valueOf(identifierScalarAttribute.getPersistenceType()));
				}
			}
		}
		
		for (Attribute attribute : linkedTableEntity.getOwningSideAttributes()) {
			if (attribute instanceof ScalarAttribute) {
				if (!attribute.isInsertable()) {
					continue;
				}
				
				ScalarAttribute scalarAttribute = (ScalarAttribute)attribute;
				if (scalarAttribute.getValueGenerator() instanceof IdentityValueRetriever) {
					continue;
				}
				
				
				insertSql.append(scalarAttribute.getColumn().getName())
					.append(", ");
				wildcardBuilder.append("?, ");
				
				Object value = attribute.extractValueForPersistence(entityToInsert);
				values.add(value != null ? value : NullObject.valueOf(scalarAttribute.getScalarType()));
				
			}
			else if (attribute instanceof EntityAttribute) {
				EntityAttribute entityAttribute = (EntityAttribute)attribute;
				
				Object friendObject = attribute.extractValueForPersistence(entityToInsert);
				Entity friendEntity = entityAttribute.getEntity();
				EntityIdentifier friendIdentifier = friendEntity.getEntityIdentifier();
				List<ScalarAttribute> friendIdentifierAttributes = friendIdentifier.getScalarAttributes();
				
				for (int i = 0; i < entityAttribute.getLeftColumns().size(); i++) {
					Column leftColumn = entityAttribute.getLeftColumns().get(i);
					ScalarAttribute friendIdentifierAttribute = friendIdentifierAttributes.get(i);
					
					insertSql.append(leftColumn.getName()).append(", ");
					wildcardBuilder.append("?, ");
					
					if (friendObject == null) {
						values.add(null);
					}
					else {
						Object value = friendIdentifierAttribute.extractValueForPersistence(friendObject);
						values.add(value);
					}
					
				}
			}
		}
		
		wildcardBuilder.replace(wildcardBuilder.length() - 2, wildcardBuilder.length(), ")");
		insertSql.setCharAt(insertSql.length() - 2, ')');
		insertSql.append(wildcardBuilder);
		
		return new Pair<String, List<?>>(insertSql.toString(), values);
	}

}
