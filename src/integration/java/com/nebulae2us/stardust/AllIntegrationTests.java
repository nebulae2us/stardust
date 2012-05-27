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
package com.nebulae2us.stardust;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.nebulae2us.stardust.api.QueryManager_H2_IT;
import com.nebulae2us.stardust.dao.domain.H2_SQL_IT;
import com.nebulae2us.stardust.dao.domain.JdbcOperation_prepareStatement_IT;

/**
 * @author Trung Phan
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	QueryManager_H2_IT.class,
	H2_SQL_IT.class,
	JdbcOperation_prepareStatement_IT.class
})
public class AllIntegrationTests {

}
