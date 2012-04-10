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
package org.nebulae2us.stardust.db.domain;

import org.junit.Test;
import org.nebulae2us.electron.BuilderRepository;

import static org.nebulae2us.stardust.Builders.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Trung Phan
 *
 */
public class JoinedTablesTest {

	
	
	@Test
	public void join_same_root_table() {
		
		JoinedTables joinedTables =
				joinedTables()
				.table$begin()
					.name("Person")
				.end()
				.toJoinedTables();
		
		BuilderRepository repo = new BuilderRepository();
		
		TableJoin tableJoin = tableJoin()
			.leftTable$begin()
				.storeTo(repo, 1)
				.name("Person")
			.end()
			.rightTable$begin()
				.storeTo(repo, 2)
				.name("Speech")
			.end()
			.leftColumns$addColumn()
				.name("person_id")
				.table$restoreFrom(repo, 1)
			.end()
			.rightColumns$addColumn()
				.name("speaker_id")
				.table$restoreFrom(repo, 2)
			.end()
			.toTableJoin();
		
		
		JoinedTables newJoinedTables = joinedTables.join(tableJoin);
		assertThat(newJoinedTables.toString(), equalToIgnoringWhiteSpace(
				"Person left outer join Speech on (Person.person_id = Speech.speaker_id)"));

		
	}
	
}
