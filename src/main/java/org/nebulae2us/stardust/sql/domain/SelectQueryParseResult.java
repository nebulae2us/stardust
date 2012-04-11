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

import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Mirror;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.LinkedTableBundle;
import org.nebulae2us.stardust.db.domain.Table;

/**
 * @author Trung Phan
 *
 */
public class SelectQueryParseResult {

	private final LinkedEntityBundle relationalEntities;
	private final LinkedTableBundle joinedTables;
	private final List<Column> columns;
	
	public SelectQueryParseResult(Mirror mirror) {
		mirror.bind(this);
		
		this.relationalEntities = mirror.to(LinkedEntityBundle.class, "relationalEntities");
		this.joinedTables = mirror.to(LinkedTableBundle.class, "joinedTables");
		this.columns = mirror.toListOf(Column.class, "columns");
	}

	public LinkedEntityBundle getRelationalEntities() {
		return relationalEntities;
	}

	public LinkedTableBundle getJoinedTables() {
		return joinedTables;
	}

	public List<Column> getColumns() {
		return columns;
	}

	
}
