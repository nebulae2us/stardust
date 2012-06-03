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

import java.util.List;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.my.domain.RelationalType;

/**
 * @author Trung Phan
 *
 */
public class RelationshipDefinition {

	private final RelationalType relationalType;
	
	private final List<String> joins;
	
	private final String junctionTableName;
	
	private final String mappedBy;
	
	public RelationshipDefinition(Mirror mirror) {
		mirror.bind(this);
		
		this.relationalType = mirror.to(RelationalType.class, "relationalType");
		this.joins = mirror.toListOf(String.class, "joins");
		this.junctionTableName = mirror.toString("junctionTableName");
		this.mappedBy = mirror.toString("mappedBy");
	}
	
	public RelationshipDefinition(RelationalType relationalType, List<String> joins, String joinTableName, String mappedBy) {
		this.relationalType = relationalType;
		this.joins = Immutables.$(joins);
		this.junctionTableName = joinTableName;
		this.mappedBy = mappedBy;
	}

	public RelationalType getRelationalType() {
		return relationalType;
	}

	public List<String> getJoins() {
		return joins;
	}

	public String getJunctionTableName() {
		return junctionTableName;
	}

	public String getMappedBy() {
		return mappedBy;
	}
	
	
}
