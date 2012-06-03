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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.my.domain.RelationalType;

/**
 * @author Trung Phan
 *
 */
public class AttributeRelationshipHolderDefinitionBuilder<P> extends AttributeHolderDefinitionBuilder<P> {

	private Map<String, RelationshipDefinition> relationships = new HashMap<String, RelationshipDefinition>();

	public RelationshipBuilder oneToOneJoin(Object attributeLocator) {
		return new RelationshipBuilder(RelationalType.ONE_TO_ONE, attributeLocator.toString());
	}

	public RelationshipBuilder manyToOneJoin(Object attributeLocator) {
		return new RelationshipBuilder(RelationalType.MANY_TO_ONE, attributeLocator.toString());
	}

	public RelationshipBuilder oneToManyJoin(Object attributeLocator) {
		return new RelationshipBuilder(RelationalType.ONE_TO_MANY, attributeLocator.toString());
	}

	public class RelationshipBuilder extends RelationshipWithJoinTableBuilder {
		public RelationshipBuilder(RelationalType relationalType, String attributeName) {
			super(relationalType, attributeName);
		}
		
		public P on(String ... joins) {
			relationships.put(attributeName, new RelationshipDefinition(relationalType, Arrays.asList(joins), junctionTableName, ""));
			return (P)AttributeRelationshipHolderDefinitionBuilder.this;
		}

	}

	public RelationshipWithJoinTableBuilder manyToManyJoin(Object attributeLocator) {
		return new RelationshipWithJoinTableBuilder(RelationalType.MANY_TO_MANY, attributeLocator.toString());
	}

	public class RelationshipWithJoinTableBuilder {
		protected final RelationalType relationalType;
		protected final String attributeName;
		protected String junctionTableName;

		public RelationshipWithJoinTableBuilder(RelationalType relationalType, String attributeName) {
			this.relationalType = relationalType;
			this.attributeName = attributeName;
		}

		public P mappedBy(String mappedBy) {
			relationships.put(attributeName, new RelationshipDefinition(relationalType, Immutables.emptyList(String.class), "", mappedBy));
			return (P)AttributeRelationshipHolderDefinitionBuilder.this;
		}
		
		public JoinBuilder usingJunctionTable(String junctionTableName) {
			this.junctionTableName = junctionTableName;
			return new JoinBuilder();
		}
		
		public class JoinBuilder {
			public P on(String ... joins) {
				relationships.put(attributeName, new RelationshipDefinition(relationalType, Arrays.asList(joins), junctionTableName, ""));
				return (P)AttributeRelationshipHolderDefinitionBuilder.this;
			}
		}
		
	}

	
	
}
