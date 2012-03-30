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
import java.util.List;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.ImmutableUniqueList;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.JoinedTables;
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
	
	public String toSelectSql(EntityRepository entityRepository) {
		
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
		
		List<Column> _columns = new ArrayList<Column>();
		for (ScalarAttribute scalarAttribute : scalarAttributes) {
			_columns.add(scalarAttribute.getColumn());
		}
		List<Column> columns = new ImmutableUniqueList<Column>(_columns);
		
		
		System.out.println(joinedTables.toString());

		for (Column column : columns) {
			System.out.println(column.getTable().getName() + "." + column.getName());
		}
		
		RelationalEntities re = RelationalEntities.newInstance(entity, initialAlias, aliasJoins);



		return "";

	}
	
}
