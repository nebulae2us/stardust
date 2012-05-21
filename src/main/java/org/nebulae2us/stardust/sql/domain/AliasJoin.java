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

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.db.domain.JoinType;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class AliasJoin {

	private final String name;
	
	private final String alias;
	
	private final JoinType joinType;
	
	public AliasJoin(Mirror mirror) {
		mirror.bind(this);
		
		this.name = mirror.toString("name");
		this.alias = mirror.toString("alias");
		this.joinType = mirror.to(JoinType.class, "joinType");
		
		assertInvariant();
	}
	
	private void assertInvariant() {
		Assert.notEmpty(this.name, "name cannot be emtpty");
		Assert.notEmpty(this.alias, "alias cannot be empty");
		Assert.notNull(this.joinType, "joinType cannot be null");
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public JoinType getJoinType() {
		return joinType;
	}
	
	public String getFirstSegment() {
		int idx = this.name.indexOf('.');
		return idx < 0 ? "" : this.name.substring(0, idx);
	}
	
	public String getSecondSegment() {
		int idx = this.name.indexOf('.');
		return idx < 0 ? this.name : this.name.substring(idx + 1, this.name.length());
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(name).append(" as ").append(alias)
		      .append(this.joinType == JoinType.DEFAULT_JOIN ? "(?)" : this.joinType == JoinType.LEFT_JOIN ? "(+)" : "");
		
		return result.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}

		AliasJoin aj = (AliasJoin)o;
		
		return this.joinType == aj.joinType && this.alias.equals(aj.alias) && this.name.equals(aj.name);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode() ^ this.alias.hashCode() ^ this.joinType.hashCode();
	}
	
}
