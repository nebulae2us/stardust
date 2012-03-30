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

import java.io.File;
import java.util.Arrays;

import org.nebulae2us.electron.BuilderGenerator;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.expr.domain.*;
import org.nebulae2us.stardust.my.domain.*;
import org.nebulae2us.stardust.sql.domain.*;

/**
 * 
 * @author Trung Phan
 *
 */
public class GenerateBuilders {

	@SuppressWarnings("unchecked")
	public static void main(String ... arguments) {
		File genFolder = new File("src/main/java");
		
		BuilderGenerator.generateBuilders(genFolder, "org.nebulae2us.stardust", 
				Arrays.asList(
						Table.class,
						Column.class,
						TableJoin.class,
						JoinedTables.class,
						Attribute.class,
						AttributeHolder.class,
						Entity.class,
						EntityAttribute.class,
						EntityIdentifier.class,
						ScalarAttribute.class,
						ValueObject.class,
						ValueObjectAttribute.class,
						AliasJoin.class,
						EntityJoin.class,
						RelationalEntities.class,
						SelectQuery.class,
						Expression.class,
						LogicalExpression.class,
						SelectQueryParseResult.class
						));
		
	}
	
}
