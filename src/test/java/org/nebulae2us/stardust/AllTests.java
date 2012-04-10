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
package org.nebulae2us.stardust;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.nebulae2us.stardust.db.domain.JoinedTablesTest;
import org.nebulae2us.stardust.def.jpa.EntityRepositoryTest;
import org.nebulae2us.stardust.sql.domain.RelationalEntitiesTest;
import org.nebulae2us.stardust.sql.domain.SelectQueryTest;

/**
 * @author Trung Phan
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	JoinedTablesTest.class, 
	EntityRepositoryTest.class, 
	RelationalEntitiesTest.class, 
	SelectQueryTest.class})
public class AllTests {
	
	

}