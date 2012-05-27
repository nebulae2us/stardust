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
import org.nebulae2us.stardust.api.BaseFilterBuilderTest;
import org.nebulae2us.stardust.api.FilterBuilderTest;
import org.nebulae2us.stardust.api.QueryTest;
import org.nebulae2us.stardust.dao.domain.JdbcOperation;
import org.nebulae2us.stardust.dao.domain.JdbcOperation_transformSql_Test;
import org.nebulae2us.stardust.ddl.domain.H2DDLGeneratorTest;
import org.nebulae2us.stardust.expr.domain.ComparisonExpressionTest;
import org.nebulae2us.stardust.expr.domain.InListExpressionTest;
import org.nebulae2us.stardust.expr.domain.SelectorExpressionTest;
import org.nebulae2us.stardust.my.domain.Group1EntityRepositoryTest;
import org.nebulae2us.stardust.sql.domain.*;
import org.nebulae2us.stardust.translate.domain.AnyAllTranslatorTest;
import org.nebulae2us.stardust.translate.domain.InListTranslatorTest;
import org.nebulae2us.stardust.translate.domain.IsNullTranslatorTest;
import org.nebulae2us.stardust.translate.domain.LikeTranslatorTest;
import org.nebulae2us.stardust.translate.domain.TranslatorIntegrationTest;

/**
 * @author Trung Phan
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	Group1EntityRepositoryTest.class, 
	LinkedEntityBundleTest.class, 
	LinkedTableEntityBundleTest.class,
	EntityMappingTest.class,
	EntityMappingWithJoinsTest.class,
	LinkedEntityBundleDataReaderTest.class,
	SelectorExpressionTest.class,
	ComparisonExpressionTest.class,
	InListExpressionTest.class,
	BaseFilterBuilderTest.class,
	FilterBuilderTest.class,
	QueryTest.class,
	TranslatorIntegrationTest.class,
	AnyAllTranslatorTest.class,
	InListTranslatorTest.class,
	IsNullTranslatorTest.class,
	LikeTranslatorTest.class,
	H2DDLGeneratorTest.class,
	JdbcOperation_transformSql_Test.class
	})
public class AllTests {
	

}
