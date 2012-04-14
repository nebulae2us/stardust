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

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.EqualityComparator;
import org.nebulae2us.electron.util.ImmutableList;

import static org.nebulae2us.stardust.Builders.*;
import org.nebulae2us.stardust.db.domain.LinkedTable;
import org.nebulae2us.stardust.db.domain.LinkedTableBundle;
import org.nebulae2us.stardust.db.domain.Table;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityRepository;
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

		IdentityHashMap<LinkedEntity, LinkedTableEntityBuilder<?>> linkedEntity2LinkedTableEntity = new IdentityHashMap<LinkedEntity, LinkedTableEntityBuilder<?>>();
		
		LinkedTableEntityBundleBuilder<?> result = null;
		
		for (LinkedEntity linkedEntity : linkedEntityBundle.getLinkedEntities()) {
			LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle = buildLinkedTableEntityBundle(entityRepository, linkedEntity.getEntity().getDeclaringClass());
			populateAlias(linkedTableEntityBundle, linkedEntity.getAlias(), linkedEntityBundle.getRecommendedDefaultAlias());
			
			LinkedTableEntityBuilder<?> linkedTableEntity = linkedTableEntityBundle.getRoot();

			linkedEntity2LinkedTableEntity.put(linkedEntity, linkedTableEntity);
			
			if (result == null) {
				result = linkedTableEntityBundle;
			}
			else {
				// TODO consider junction tables later
				linkedTableEntity
					.parent(linkedEntity2LinkedTableEntity.get(linkedEntity).getParent())
					.parentColumns$wrap(linkedEntity.getAttribute().getLeftColumns())
					.columns$wrap(linkedEntity.getAttribute().getRightColumns())
					.joinType(linkedEntity.getJoinType())
					;
			}
			
		}
		
		return selectQueryParseResult().linkedTableEntityBundle(result).toSelectQueryParseResult();

	}
	
	/**
	 * @param linkedTableEntityBundle
	 * @param alias
	 * @param recommendedDefaultAlias
	 */
	private void populateAlias(
			LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle,
			String alias, String tableAlias) {

		int i = 0;
		for (LinkedTableEntityBuilder<?> linkedTableEntity : linkedTableEntityBundle.getLinkedTableEntities()) {
			linkedTableEntity
				.alias(alias)
				.tableAlias(i == 0 ? tableAlias : tableAlias + i)
			;
			
			i++;
		}
		
	}

	private LinkedTableEntityBundleBuilder<?> buildLinkedTableEntityBundle(EntityRepository entityRepository, Class<?> entityClass) {
		
		
		List<Entity> entitiesAndSub = entityRepository.getEntitiesAndSub(entityClass);
		List<Entity> subEntities = entitiesAndSub.subList(1, entitiesAndSub.size());
		Entity entity = entitiesAndSub.get(0);
		Entity rootEntity = entity.getRootEntity();

		LinkedTableBundle linkedTableBundle = entity.getLinkedTableBundle();
		Table primaryTable = linkedTableBundle.getRoot().getTable();
		
		LinkedTableEntityBundleBuilder<?> result = linkedTableEntityBundle()
				.linkedTableEntities$addLinkedTableEntity()
					.table$wrap(primaryTable)
					.entity$wrap(entity)
					.attributes$wrap(entity.getScalarAttributes(primaryTable))
					.attributes$wrap(entity.getOwningSideEntityAttributes(primaryTable))
				.end()
				;
		
		IdentityHashMap<LinkedTable, LinkedTableEntityBuilder<?>> linkedTable2LinkedTableEntity = new IdentityHashMap<LinkedTable, LinkedTableEntityBuilder<?>>();
		linkedTable2LinkedTableEntity.put(linkedTableBundle.getRoot(), result.getRoot());
		
		// loop through secondary tables
		for (LinkedTable linkedTable : linkedTableBundle.getNonRoots()) {
			
			Table secondaryTable = linkedTable.getTable();
			
			LinkedTableEntityBuilder<?> linkedTableEntity = result.linkedTableEntities$addLinkedTableEntity()
				.parent(linkedTable2LinkedTableEntity.get(linkedTable.getParent()))
				.table$wrap(secondaryTable)
				.entity$wrap(entity)
				.attributes$wrap(entity.getScalarAttributes(secondaryTable))
				.attributes$wrap(entity.getOwningSideEntityAttributes(secondaryTable))
				.columns$wrap(linkedTable.getColumns())
				.parentColumns$wrap(linkedTable.getParentColumns())
				.joinType(linkedTable.getJoinType())
				;
			
			linkedTable2LinkedTableEntity.put(linkedTable, linkedTableEntity);
			
		}
		
		// loop through subEntities
		for (Entity subEntity : subEntities) {
			
			for (LinkedTable subLinkedTable : subEntity.getLinkedTableBundle().getLinkedTables()) {
				ImmutableList<ScalarAttribute> subScalarAttributes = subEntity.getScalarAttributes(subLinkedTable.getTable());
				ImmutableList<ScalarAttribute> scalarAttributes = subEntity.getScalarAttributes(subLinkedTable.getTable());
				if (subScalarAttributes.size() > 0) {
					if (scalarAttributes.size() == 0) {
						result.linkedTableEntities$addLinkedTableEntity()
							.table$wrap(subLinkedTable.getTable())
							.entity$wrap(subEntity)
							.parent(result.getRoot())
							.attributes$wrap(subScalarAttributes)
							.columns$wrap(subLinkedTable.getColumns())
							.parentColumns$wrap(subLinkedTable.getParentColumns())
							.joinType(subLinkedTable.getJoinType())
						.end();
					}
					else {
						subScalarAttributes = subScalarAttributes
								.changeComparator(Attribute.COMPARATOR_BY_NAME).minus(entity.getScalarAttributes(subLinkedTable.getTable()));
						
						if (subScalarAttributes.size() > 0) {
							for (Entry<LinkedTable, LinkedTableEntityBuilder<?>> entry : linkedTable2LinkedTableEntity.entrySet()) {
								LinkedTable linkedTable = entry.getKey();
								if (linkedTable.getTable().equals(subLinkedTable.getTable())) {
									LinkedTableEntityBuilder<?> linkedTableEntity = entry.getValue();
									linkedTableEntity.attributes$wrap(subScalarAttributes);
								}
							}
						}
						
					}
				}
				
//				List<ScalarAttribute> subScalarAttributes = subEntity.getScalarAttributes(subLinkedTable.getTable())
//						.changeComparator(new EqualityComparator<ScalarAttribute>() {
//							public int hashCode(ScalarAttribute object) {
//								return 0;
//							}
//							public boolean compare(ScalarAttribute sa1, ScalarAttribute sa2) {
//								return sa1.getFullName().equals(sa2.getFullName());
//							}
//						}).minus(entity.getScalarAttributes(subLinkedTable.getTable()));
//
//				if (subScalarAttributes.size() > 0) {
//				}
				
			}

		}
		
		return result;
	}
	
}
