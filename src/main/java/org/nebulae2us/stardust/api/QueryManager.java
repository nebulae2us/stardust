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
package org.nebulae2us.stardust.api;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.dao.domain.GenericDataReader;
import org.nebulae2us.stardust.dao.domain.JdbcOperation;
import org.nebulae2us.stardust.expr.domain.InsertEntityExpression;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.sql.domain.AliasJoin;
import org.nebulae2us.stardust.sql.domain.DataReader;
import org.nebulae2us.stardust.sql.domain.LinkedEntityBundle;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;
import org.nebulae2us.stardust.translate.domain.ParamValues;
import org.nebulae2us.stardust.translate.domain.Translator;
import org.nebulae2us.stardust.translate.domain.TranslatorContext;
import org.nebulae2us.stardust.translate.domain.TranslatorController;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * 
 * Thread safe. Should be singleton.
 * 
 * @author Trung Phan
 *
 */
public class QueryManager {

	private final EntityRepository entityRepository;
	
	private final TranslatorController controller;
	
	private final JdbcOperation jdbcOperation;
	
	public QueryManager(JdbcOperation jdbcOperation, EntityRepository entityRepository, TranslatorController controller) {
		this.jdbcOperation = jdbcOperation;
		this.entityRepository = entityRepository;
		this.controller = controller;
	}
	
	public EntityRepository getEntityRepository() {
		return entityRepository;
	}

	public TranslatorController getController() {
		return controller;
	}

	public <T> QueryBuilder<T> newQuery(Class<T> entityClass) {
		return new QueryBuilder<T>(this, entityClass);
	}
	
	public <T> List<T> query(Query<T> query) {
		Pair<String, List<?>> translateResult = query.translate();
		
		String sql = translateResult.getItem1();
		List<?> values = translateResult.getItem2();
		
		ResultSet resultSet = jdbcOperation.query(sql, values);
		DataReader dataReader = new GenericDataReader(resultSet);
		
		TranslatorContext context = query.getTranslatorContext();
		return (List<T>)context.getLinkedEntityBundle().readData(this.entityRepository, dataReader);
	}
	
	public void save(Object object) {
		
		Entity entity = entityRepository.getEntity(object.getClass());
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entity, "", Immutables.emptyList(AliasJoin.class));
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle, false);
		
		TranslatorContext context = new TranslatorContext(this.controller, linkedTableEntityBundle, linkedEntityBundle, false);

		for (int i = 0; i < linkedTableEntityBundle.getLinkedTableEntities().size(); i++) {
			InsertEntityExpression insertEntityExpression = new InsertEntityExpression("insert", object.getClass(), i);
			
			ParamValues paramValues = new ParamValues(Immutables.emptyStringMap(), Collections.singletonList(object));
			Translator translator = controller.findTranslator(insertEntityExpression, paramValues);
			Pair<String, List<?>> translateResult = translator.translate(context, insertEntityExpression, paramValues);
			
			String sql = translateResult.getItem1();
			List<?> values = translateResult.getItem2();
			
			int rowsInserted = jdbcOperation.update(sql, values);
		}
		
	}
	
}
