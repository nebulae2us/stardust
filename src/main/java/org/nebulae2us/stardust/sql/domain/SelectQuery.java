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
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.ListBuilder;
import static org.nebulae2us.stardust.Builders.*;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.JoinedTables;
import org.nebulae2us.stardust.db.domain.Table;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.internal.util.ObjectUtils;
import org.nebulae2us.stardust.my.domain.Entity;
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
		
		Map<Table, String> tableAliases = new HashMap<Table, String>();
		
		List<Entity> entities = entityRepository.getEntitiesAndSub(entityClass);
		Entity entity = entities.get(0);
		Entity rootEntity = entity.getRootEntity();

		JoinedTables joinedTables = entity.getJoinedTables();
		List<ScalarAttribute> scalarAttributes = new ArrayList<ScalarAttribute>();
		for (Entity e : entities) {
			scalarAttributes.addAll(e.getScalarAttributes());
		}
		
		if (rootEntity.getInheritanceType() == InheritanceType.JOINED) {
			for (Entity e : entities) {
				if (e != entity) {
					joinedTables = joinedTables.join(e.getJoinedTables().getTableJoins());
				}
			}
		}
		
		ListBuilder<Column> columnListBuilder = new ListBuilder<Column>();
		for (ScalarAttribute scalarAttribute : scalarAttributes) {
			columnListBuilder.add(scalarAttribute.getColumn());
		}
		List<Column> columns = columnListBuilder.toList();
		
		
		System.out.println(joinedTables.toString());

		RelationalEntities relationalEntities = RelationalEntities.newInstance(entity, initialAlias, aliasJoins);

		SelectQueryParseResult selectQueryParseResult = selectQueryParseResult()
				.relationalEntities$wrap(relationalEntities)
				.joinedTables$wrap(joinedTables)
				.columns$wrap(columns)
				.toSelectQueryParseResult();

		return selectQueryParseResult;

	}
	
}
