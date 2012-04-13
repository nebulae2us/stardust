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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.ListBuilder;
import static org.nebulae2us.stardust.Builders.*;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.LinkedTable;
import org.nebulae2us.stardust.db.domain.LinkedTableBundle;
import org.nebulae2us.stardust.db.domain.Table;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.internal.util.BaseAssert;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.my.domain.InheritanceType;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;

/**
 * @author Trung Phan
 *
 */
public class SelectQuery {

	private final Class<?> entityClass;
	
	private final String initialAlias;
	
	private final List<AliasJoin> aliasJoins;
	
	private final List<Expression> expressions;
	
	public SelectQuery(Mirror mirror) {
		mirror.bind(this);
		
		this.entityClass = mirror.to(Class.class, "entityClass");
		this.initialAlias = ObjectUtils.nvl(mirror.toString("initialAlias"));
		this.aliasJoins = mirror.toListOf(AliasJoin.class, "aliasJoins");
		this.expressions = mirror.toListOf(Expression.class, "expressions");
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public String getInitialAlias() {
		return initialAlias;
	}

	public List<AliasJoin> getAliasJoins() {
		return aliasJoins;
	}

	public List<Expression> getExpressions() {
		return expressions;
	}
	
	public SelectQueryParseResult toSelectSql(EntityRepository entityRepository) {
		
		
//		List<Entity> entitiesAndSub = entityRepository.getEntitiesAndSub(entityClass);
//		List<Entity> subEntities = entitiesAndSub.subList(1, entitiesAndSub.size());
//		Entity entity = entitiesAndSub.get(0);
//		Entity rootEntity = entity.getRootEntity();
//
//		LinkedTableBundle linkedTableBundle = entity.getLinkedTableBundle();
//		List<ScalarAttribute> scalarAttributes = new ArrayList<ScalarAttribute>();
//		for (Entity e : entitiesAndSub) {
//			scalarAttributes.addAll(e.getScalarAttributes());
//		}
//		
//		if (rootEntity.getInheritanceType() == InheritanceType.JOINED) {
//			for (Entity e : subEntities) {
//				linkedTableBundle = linkedTableBundle.join(e.getLinkedTableBundle());
//			}
//		}

		// THINK SIMPLE FIRST, no Inheritance type of JOINED
		
		
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entityRepository.getEntity(this.entityClass), this.initialAlias, this.aliasJoins);

		SelectQueryParseResultBuilder<?> result = selectQueryParseResult();
		LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle = result.linkedTableEntityBundle$begin();
		
		IdentityHashMap<LinkedEntity, LinkedTableEntityBuilder<?>> linkedEntity2LinkedTableEntity = new IdentityHashMap<LinkedEntity, LinkedTableEntityBuilder<?>>();
		
		for (LinkedEntity linkedEntity : linkedEntityBundle.getLinkedEntities()) {
			
			Entity entity = linkedEntity.getEntity();
			List<ScalarAttribute> scalarAttributes = entity.getScalarAttributes();
			LinkedTableBundle linkedTableBundle = entity.getLinkedTableBundle();
			
			String alias = linkedEntity.getAlias();

			String tableAlias = alias;
			if (tableAlias.length() == 0) {
				tableAlias = linkedEntityBundle.getRecommendedDefaultAlias();
			}

			IdentityHashMap<LinkedTable, LinkedTableEntityBuilder<?>> linkedTable2LinkedTableEntity = new IdentityHashMap<LinkedTable, LinkedTableEntityBuilder<?>>();
			
			int i = 0;
			for (LinkedTable linkedTable : linkedTableBundle.getLinkedTables()) {
				LinkedTableEntityBuilder<?> linkedTableEntity = linkedTableEntityBundle.linkedTableEntities$addLinkedTableEntity()
					.entity$wrap(entity)
					.table$wrap(linkedTable.getTable())
					.alias(alias)
					.tableAlias(i == 0 ? tableAlias : tableAlias + i)
					;
				
				linkedTable2LinkedTableEntity.put(linkedTable, linkedTableEntity);
				
				for (ScalarAttribute scalarAttribute : scalarAttributes) {
					if (scalarAttribute.getColumn().getTable().equals(linkedTable.getTable())) {
						linkedTableEntity.scalarAttributes$wrap(scalarAttribute);
					}
				}
				
				if (i == 0) {
					linkedEntity2LinkedTableEntity.put(linkedEntity, linkedTableEntity);
					
					if (linkedEntity.getParent() != null) {
						EntityAttribute entityAttribute = linkedEntity.getAttribute();
						
						// TODO consider junction tables later
						linkedTableEntity
							.parentColumns$wrap(entityAttribute.getLeftColumns())
							.columns$wrap(entityAttribute.getRightColumns())
						;
						
						LinkedTableEntityBuilder<?> linkedTableEntityParent = linkedEntity2LinkedTableEntity.get(linkedEntity.getParent());
						BaseAssert.AssertState.notNull(linkedTableEntityParent, " check");
						linkedTableEntity.parent(linkedTableEntityParent);
					}
					
				}
				else {
					linkedTableEntity
						.parentColumns$wrap(linkedTable.getParentColumns())
						.columns$wrap(linkedTable.getColumns())
						.parent(linkedTable2LinkedTableEntity.get(linkedTable.getParent()))
						.joinType(linkedTable.getJoinType())
						;
					
					
				}
				
				i++;
				
			}
			
		}

		return result.toSelectQueryParseResult();

	}
	
}
