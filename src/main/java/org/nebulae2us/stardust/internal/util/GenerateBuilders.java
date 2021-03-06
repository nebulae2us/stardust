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
package org.nebulae2us.stardust.internal.util;

import java.io.File;

import org.nebulae2us.electron.BuilderGenerator;
import org.nebulae2us.stardust.Query;
import org.nebulae2us.stardust.adapter.TypeAdapter;
import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.my.domain.*;
import org.nebulae2us.stardust.sql.domain.*;

/**
 * 
 * @author Trung Phan
 *
 */
public class GenerateBuilders {

	public static void main(String ... arguments) {
		File genFolder = new File("src/main/java");
		
		new BuilderGenerator()
			.baseFolder(genFolder)
			.buildersClassName("org.nebulae2us.stardust.internal.util.Builders")
			.builderSuffix("Builder")
			.ignoreTypes(TypeAdapter.class, SqlBundle.class, Query.class)
			.generate(
					Table.class,
					Column.class,
					LinkedTable.class,
					LinkedTableBundle.class,
					Attribute.class,
					AttributeHolder.class,
					Entity.class,
					EntityAttribute.class,
					EntityIdentifier.class,
					EntityDiscriminator.class,
					ScalarAttribute.class,
					ValueObject.class,
					ValueObjectAttribute.class,
					AliasJoin.class,
					LinkedEntity.class,
					LinkedEntityBundle.class,
					LinkedTableEntity.class,
					LinkedTableEntityBundle.class,
					AttributeMapping.class,
					ScalarAttributeMapping.class,
					ValueObjectAttributeMapping.class,
					EntityAttributeMapping.class,
					EntityMapping.class
					);
		
	}
	
}
