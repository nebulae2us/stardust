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

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.util.ImmutableList;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.db.domain.LinkedTable;
import org.nebulae2us.stardust.db.domain.LinkedTableBundle;
import org.nebulae2us.stardust.db.domain.Table;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;
import static org.nebulae2us.stardust.internal.util.Builders.linkedTableEntity;
import static org.nebulae2us.stardust.internal.util.Builders.linkedTableEntityBundle;

/**
 * @author Trung Phan
 *
 */
public class LinkedTableEntityBundle {

	private final List<LinkedTableEntity> linkedTableEntities;
	
	public LinkedTableEntityBundle(Mirror mirror) {
		mirror.bind(this);
		
		this.linkedTableEntities = mirror.toListOf(LinkedTableEntity.class, "linkedTableEntities");
		
		assertInvariant();
	}

	private void assertInvariant() {
		Assert.notEmpty(this.linkedTableEntities, "linkedTableEntities cannot be empty.");
		Assert.isNull(getRoot().getParent(), "root node cannot have parent");
	}

	public List<LinkedTableEntity> getLinkedTableEntities() {
		return linkedTableEntities;
	}
	
	public LinkedTableEntity getRoot() {
		return this.linkedTableEntities.get(0);
	}
	
	public List<LinkedTableEntity> getNonRoots() {
		return this.linkedTableEntities.subList(1, this.linkedTableEntities.size());
	}
	
	public Pair<LinkedTableEntity, ScalarAttribute> findAttribute(String alias, String attributeExpression) {
		for (LinkedTableEntity linkedTableEntity : this.linkedTableEntities) {
			if (alias.equals(linkedTableEntity.getAlias())) {
				for (Attribute attribute : linkedTableEntity.getOwningSideAttributes()) {
					if (attribute instanceof ScalarAttribute && attribute.getFullName().equals(attributeExpression)) {
						return new Pair<LinkedTableEntity, ScalarAttribute>(linkedTableEntity, (ScalarAttribute)attribute);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Based on the linkedEntityBundle, this method will expand to build the linkedTableEntityBundle. The flag includeSubEntities is for the root entity.
	 * If includeSubEntities is true, not only the root tables involved in the entity is considered, but also the tables in the sub entities.
	 * For example, room entity involves table ROOM, but with includeSubEntities, it also involves table KITCHEN and BEDROOM. Normally, for select query,
	 * the includeSubEntities = true because we don't know yet what the discriminator for the object is, but for insert or update, the 
	 * includeSubEntities = false because we already know the object type, hence the discriminator.
	 * 
	 * @param entityRepository
	 * @param linkedEntityBundle
	 * @param includeSubEntities
	 * @return
	 */
	public static LinkedTableEntityBundle newInstance(EntityRepository entityRepository, LinkedEntityBundle linkedEntityBundle, boolean includeSubEntities) {
		Set<String> usedAliases = new HashSet<String>(linkedEntityBundle.getAliases().elementToLowerCase());

		IdentityHashMap<LinkedEntity, LinkedTableEntityBuilder<?>> linkedEntity2LinkedTableEntity = new IdentityHashMap<LinkedEntity, LinkedTableEntityBuilder<?>>();
		
		LinkedTableEntityBundleBuilder<?> result = null;
		
		for (LinkedEntity linkedEntity : linkedEntityBundle.getLinkedEntities()) {
			LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle = buildLinkedTableEntityBundle(entityRepository, linkedEntity.getEntity().getDeclaringClass(), linkedEntity.isRoot() ? includeSubEntities : true);
			populateAlias(linkedTableEntityBundle, linkedEntity.getAlias(), linkedEntityBundle, usedAliases);
			
			LinkedTableEntityBuilder<?> linkedTableEntity = linkedTableEntityBundle.getRoot();

			linkedEntity2LinkedTableEntity.put(linkedEntity, linkedTableEntity);
			
			if (result == null) {
				result = linkedTableEntityBundle;
			}
			else if (linkedEntity.getAttribute().getJunctionTable() == null) {
				linkedTableEntity
					.parent(linkedEntity2LinkedTableEntity.get(linkedEntity.getParent()))
					.parentColumns$wrap(linkedEntity.getAttribute().getLeftColumns())
					.columns$wrap(linkedEntity.getAttribute().getRightColumns())
					.joinType(linkedEntity.getJoinType())
					;

				result.linkedTableEntities(linkedTableEntityBundle.getLinkedTableEntities());
				
			}
			else {
				LinkedTableEntityBuilder<?> junction = linkedTableEntity()
						.table$wrap(linkedEntity.getAttribute().getJunctionTable())
						.tableAlias( getNextUnusedAlias(usedAliases, linkedEntity.getAlias()) )
						.parent(linkedEntity2LinkedTableEntity.get(linkedEntity.getParent()))
						.parentColumns$wrap(linkedEntity.getAttribute().getLeftColumns())
						.columns$wrap(linkedEntity.getAttribute().getJunctionLeftColumns())
						.joinType(linkedEntity.getJoinType())
						;
				
				linkedTableEntity
						.parent(junction)
						.parentColumns$wrap(linkedEntity.getAttribute().getJunctionRightColumns())
						.columns$wrap(linkedEntity.getAttribute().getRightColumns())
						.joinType(linkedEntity.getJoinType())
						;
				result.linkedTableEntities(junction)
					.linkedTableEntities(linkedTableEntityBundle.getLinkedTableEntities());
				
			}
			
		}		
		
		return result.toLinkedTableEntityBundle();
	}
	
	private static LinkedTableEntityBundleBuilder<?> buildLinkedTableEntityBundle(EntityRepository entityRepository, Class<?> entityClass, boolean includeSubEntities) {

		Entity entity = entityRepository.getEntity(entityClass);

		LinkedTableBundle linkedTableBundle = entity.getLinkedTableBundle();
		Table primaryTable = linkedTableBundle.getRoot().getTable();
		
		LinkedTableEntityBundleBuilder<?> result = linkedTableEntityBundle()
				.linkedTableEntities$addLinkedTableEntity()
					.table$wrap(primaryTable)
					.entity$wrap(entity)
					.owningSideAttributes$wrap(entity.getOwningSideAttributes(primaryTable))
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
				.owningSideAttributes$wrap(entity.getOwningSideAttributes(secondaryTable))
				.columns$wrap(linkedTable.getColumns())
				.parentColumns$wrap(linkedTable.getParentColumns())
				.joinType(linkedTable.getJoinType())
				;
			
			linkedTable2LinkedTableEntity.put(linkedTable, linkedTableEntity);
			
		}
		

		if (includeSubEntities) {
			
			List<Entity> subEntities = entityRepository.getSubEntities(entity);
			
			for (Entity subEntity : subEntities) {
				
				for (LinkedTable subLinkedTable : subEntity.getLinkedTableBundle().getLinkedTables()) {
					ImmutableList<Attribute> subOwningSideAttributes = subEntity.getOwningSideAttributes(subLinkedTable.getTable());
					ImmutableList<Attribute> owningSideAttributes = entity.getOwningSideAttributes(subLinkedTable.getTable());
					if (subOwningSideAttributes.size() > 0) {
						if (owningSideAttributes.size() == 0) {
							result.linkedTableEntities$addLinkedTableEntity()
								.table$wrap(subLinkedTable.getTable())
								.entity$wrap(subEntity)
								.parent(result.getRoot())
								.owningSideAttributes$wrap(subOwningSideAttributes)
								.columns$wrap(subLinkedTable.getColumns())
								.parentColumns$wrap(subLinkedTable.getParentColumns())
								.joinType(subLinkedTable.getJoinType())
							.end();
						}
						else {
							subOwningSideAttributes = subOwningSideAttributes
									.changeComparator(Attribute.COMPARATOR_BY_NAME).minus(owningSideAttributes);
							
							if (subOwningSideAttributes.size() > 0) {
								for (Entry<LinkedTable, LinkedTableEntityBuilder<?>> entry : linkedTable2LinkedTableEntity.entrySet()) {
									LinkedTable linkedTable = entry.getKey();
									if (linkedTable.getTable().equals(subLinkedTable.getTable())) {
										LinkedTableEntityBuilder<?> linkedTableEntity = entry.getValue();
										linkedTableEntity.owningSideAttributes$wrap(subOwningSideAttributes);
									}
								}
							}
							
						}
					}
				}
	
			}
		}
		
		return result;
	}	
	
	/**
	 * @param linkedTableEntityBundle
	 * @param alias
	 * @param recommendedDefaultAlias
	 */
	private static void populateAlias(LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle, String alias, LinkedEntityBundle linkedEntityBundle, Set<String> usedAliases) {

		String tableAlias = alias;
		if (tableAlias.length() == 0) {
			tableAlias = getDefaultAlias(usedAliases);
		}
		
		linkedTableEntityBundle.getRoot().alias(alias).tableAlias(tableAlias);
		
		for (LinkedTableEntityBuilder<?> linkedTableEntity : linkedTableEntityBundle.getNonRoots()) {
			linkedTableEntity.alias(alias).tableAlias(getNextUnusedAlias(usedAliases, tableAlias));
		}
		
	}
	
	private static String getNextUnusedAlias(Set<String> usedAliases, String baseAlias) {
		int i = 1;
		while (true) {
			String nextUnusedAlias = baseAlias + i++;
			if (!usedAliases.contains(nextUnusedAlias)) {
				usedAliases.add(nextUnusedAlias);
				return nextUnusedAlias;
			}
		}
	}

	private static final List<String> RESERVE_KEYWORDS = new ListBuilder<String>().add("as").toList();
	
	private static String getDefaultAlias(Set<String> usedAliases) {
		String result = null;
		
		if (!usedAliases.contains("b")) {
			result = "b";
		}

		if (result == null) {
			for (char c = 'a'; c  <= 'z'; c++) {
				if (!usedAliases.contains(c)) {
					result = String.valueOf(c);
					break;
				}
			}
		}

		if (result == null) {
			for (char c = 'a'; c  <= 'z'; c++) {
				for (char c2 = 'a'; c2 <= 'z'; c2++) {
					String newAlias = "" + c + c2;
					if (!RESERVE_KEYWORDS.contains(newAlias) && !usedAliases.contains(newAlias)) {
						result = newAlias;
						break;
					}
				}
			}
		}

		AssertState.notNull(result, "Cannot find unused alias");
		
		usedAliases.add(result);
		return result;
	}	
	
	
	/**
	 * This method should print the select and from (join) clause (without where clause) for this query
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("select ");
		
		for (LinkedTableEntity linkedTableEntity : this.linkedTableEntities) {
			
			if (linkedTableEntity.getEntity() == null) {
				continue;
			}
			
			Set<Column> columns = new LinkedHashSet<Column>();

			if (linkedTableEntity.getEntity().getEntityDiscriminator() != null) {
				Column column = linkedTableEntity.getEntity().getEntityDiscriminator().getColumn();
				if (column.getTable().equals(linkedTableEntity.getTable())) {
					columns.add(column);
				}
			}
			
			for (Attribute attribute : linkedTableEntity.getOwningSideAttributes()) {
				if (attribute instanceof ScalarAttribute) {
					ScalarAttribute scalarAttribute = (ScalarAttribute)attribute;
					columns.add(scalarAttribute.getColumn());
				}
				else if (attribute instanceof EntityAttribute) {
					columns.addAll(((EntityAttribute)attribute).getLeftColumns());
				}
			}
			
			for (Column column : columns) {
				result
					.append(linkedTableEntity.getTableAlias())
					.append('.')
					.append(column.getName())
					.append(' ')
					.append(linkedTableEntity.getAlias().length() == 0 ? "" : linkedTableEntity.getAlias() + '_')
					.append(column.getName())
					.append(",\n       ");
			}
			
		}
		
		result.delete(result.length() - 9, result.length());
		
		result.append("\n  from ")
			.append(getRoot().getTable())
			.append(' ')
			.append(getRoot().getTableAlias());
		
		for (LinkedTableEntity linkedTableEntity : getNonRoots()) {
			result
				.append("\n           ")
				.append(linkedTableEntity.getJoinType() == JoinType.INNER_JOIN ? "inner join " : "left outer join ")
				.append(linkedTableEntity.getTable()).append(' ')
				.append(linkedTableEntity.getTableAlias()).append("\n               on (")
			;
			
			for (int i = 0; i < linkedTableEntity.getColumns().size(); i++) {
				Column parentColumn = linkedTableEntity.getParentColumns().get(i);
				Column column = linkedTableEntity.getColumns().get(i);
				
				result.append(linkedTableEntity.getParent().getTableAlias())
					.append('.').append(parentColumn.getName())
					.append(" = ")
					.append(linkedTableEntity.getTableAlias())
					.append('.')
					.append(column.getName())
					.append("\n               and ");
				
			}

			result.delete(result.length() - 20, result.length());
			result.append(")");
		}
		
		return result.toString();
	}

	public LinkedTableEntity findLinkedTableEntity(Table table) {
		for (LinkedTableEntity linkedTableEntity : this.linkedTableEntities) {
			if (linkedTableEntity.getTable().equals(table)) {
				return linkedTableEntity;
			}
		}
		return null;
	}
}
