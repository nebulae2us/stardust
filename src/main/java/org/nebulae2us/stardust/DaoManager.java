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

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.dao.JdbcExecutor;
import org.nebulae2us.stardust.dao.RecordSetHandler;
import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.dialect.Dialect;
import org.nebulae2us.stardust.dialect.OracleDialect;
import org.nebulae2us.stardust.expr.domain.DeleteEntityExpression;
import org.nebulae2us.stardust.expr.domain.InsertEntityExpression;
import org.nebulae2us.stardust.expr.domain.UpdateEntityExpression;
import org.nebulae2us.stardust.generator.ValueGenerator;
import org.nebulae2us.stardust.internal.util.ReflectionUtils;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;
import org.nebulae2us.stardust.sql.domain.AliasJoin;
import org.nebulae2us.stardust.sql.domain.DataReader;
import org.nebulae2us.stardust.sql.domain.LinkedEntityBundle;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntity;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;
import org.nebulae2us.stardust.translate.domain.CommonTranslatorController;
import org.nebulae2us.stardust.translate.domain.ParamValues;
import org.nebulae2us.stardust.translate.domain.Translator;
import org.nebulae2us.stardust.translate.domain.TranslatorContext;
import org.nebulae2us.stardust.translate.domain.TranslatorController;
import org.nebulae2us.stardust.translate.domain.oracle.OracleFunctionTranslator;
import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * 
 * Thread safe. Should be singleton.
 * 
 * @author Trung Phan
 *
 */
public class DaoManager {

	private final EntityRepository entityRepository;
	
	private final TranslatorController controller;
	
	private final JdbcExecutor jdbcExecutor;
	
	private final Dialect dialect;
	
	private final String defaultSchema;
	
	public DaoManager(DataSource dataSource, Dialect dialect) {
		this(dataSource, dialect, "");
	}
	
	public DaoManager(DataSource dataSource, Dialect dialect, String defaultSchema) {
		this.jdbcExecutor = new JdbcExecutor(dataSource, dialect);
		this.dialect = dialect;
		this.entityRepository = new EntityRepository();
		this.controller = resolveTranslatorController(dialect);
		this.defaultSchema = defaultSchema;
	}
	
	public DaoManager(JdbcExecutor jdbcExecutor) {
		this(jdbcExecutor, "");
	}
	
	public DaoManager(JdbcExecutor jdbcExecutor, String defaultSchema) {
		this.jdbcExecutor = jdbcExecutor;
		this.dialect = jdbcExecutor.getDialect();
		this.entityRepository = new EntityRepository();
		this.controller = resolveTranslatorController(dialect);
		this.defaultSchema = defaultSchema;
	}

	public DaoManager(JdbcExecutor jdbcExecutor, EntityRepository entityRepository, TranslatorController controller, Dialect dialect) {
		this(jdbcExecutor, entityRepository, controller, dialect, "");
	}
	
	public DaoManager(JdbcExecutor jdbcExecutor, EntityRepository entityRepository, TranslatorController controller, Dialect dialect, String defaultSchema) {
		this.jdbcExecutor = jdbcExecutor;
		this.entityRepository = entityRepository;
		this.controller = controller;
		this.dialect = dialect;
		this.defaultSchema = defaultSchema;
	}
	
	public void beginUnitOfWork() {
		this.jdbcExecutor.beginUnitOfWork();
	}
	
	public void endUnitOfWork() {
		this.jdbcExecutor.endUnitOfWork();
	}

	public void setPackageToScan(String packageName) {
		this.entityRepository.scanPackage(packageName);
	}
	
	private TranslatorController resolveTranslatorController(Dialect dialect) {
		if (dialect instanceof OracleDialect) {
			return new CommonTranslatorController(new ListBuilder<Translator>()
					.add(new OracleFunctionTranslator())
					.toList());
		}
		else {
			return new CommonTranslatorController();
		}
	}
	
	public EntityRepository getEntityRepository() {
		return entityRepository;
	}

	public TranslatorController getController() {
		return controller;
	}

	public final Dialect getDialect() {
		return this.dialect;
	}
	
	public final String getDefaultSchema() {
		return defaultSchema;
	}

	public final JdbcExecutor getJdbcExecutor() {
		return jdbcExecutor;
	}
	
	public <T> T get(Class<T> entityClass, Object ... idValues) {
		
		Query<T> query = new QueryBuilder<T>(this, entityClass)
				.filterById(idValues)
				.toQuery();
		
		List<T> result = query(query);
		return result.size() == 1 ? result.get(0) : null;
	}

	public <T> QueryBuilder<T> newQuery(Class<T> entityClass) {
		return new QueryBuilder<T>(this, entityClass);
	}
	
	public <T> List<T> query(Query<T> query) {
		final TranslatorContext context = query.getTranslatorContext();

		SqlBundle translateResult = query.translate();
		
		String sql = translateResult.getSql();
		List<?> values = translateResult.getParamValues();

		final List<T>[] result = new List[1];

		jdbcExecutor.beginUnitOfWork();
		try {
			jdbcExecutor.query(sql, values, new RecordSetHandler<T>(RecordSetHandler.MODE_DATA_READER) {
				@Override
				public int handleRecordSet(DataReader dataReader) {
					result[0] = (List<T>)context.getLinkedEntityBundle().readData(entityRepository, dataReader);
					return RecordSetHandler.SKIP_MAPPING_RECORD;
				}
			});
		}
		finally {
			jdbcExecutor.endUnitOfWork();
		}
		
		return result[0];
	}
	
	public void save(Object object) {
		
		Entity entity = entityRepository.getEntity(object.getClass());
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entity, "", Immutables.emptyList(AliasJoin.class));
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle, false);
		
		TranslatorContext context = new TranslatorContext(this.dialect, this.controller, linkedTableEntityBundle, linkedEntityBundle, false, defaultSchema);
		
		jdbcExecutor.beginUnitOfWork();
		try {
			LinkedTableEntity linkedTableEntity = linkedTableEntityBundle.getLinkedTableEntities().get(0);

			// populate identity values
			for (ScalarAttribute scalarAttribute : linkedTableEntity.getScalarAttributes()) {
				ValueGenerator generator = scalarAttribute.getValueGenerator();
				if (generator != null && generator.generationBeforeInsertion()) {
					Object value = generator.generateValue(scalarAttribute.getPersistenceType(), this.dialect, this.jdbcExecutor);
					value = scalarAttribute.convertValueToAttributeType(value);
					ReflectionUtils.setValue(scalarAttribute.getField(), object, value);
				}
			}
			
			InsertEntityExpression insertEntityExpression = new InsertEntityExpression("insert", "");
			
			ParamValues paramValues = new ParamValues(Immutables.emptyStringMap(), Collections.singletonList(object));
			Translator translator = controller.findTranslator(insertEntityExpression, paramValues);
			SqlBundle translateResult = translator.translate(context, insertEntityExpression, paramValues);
			
			for (int i = 0; i < translateResult.size(); i++) {
				String sql = translateResult.getSql(i);
				List<?> values = translateResult.getParamValues(i);
				
				jdbcExecutor.update(sql, values);
				
				// retrieve identity values
				for (ScalarAttribute scalarAttribute : linkedTableEntity.getScalarAttributes()) {
					ValueGenerator generator = scalarAttribute.getValueGenerator();
					if (generator != null && !generator.generationBeforeInsertion()) {
						Object value = generator.generateValue(scalarAttribute.getPersistenceType(), this.dialect, this.jdbcExecutor);
						value = scalarAttribute.convertValueToAttributeType(value);
						ReflectionUtils.setValue(scalarAttribute.getField(), object, value);
					}
				}
			}
		}
		finally {
			jdbcExecutor.endUnitOfWork();
		}
	}
	
	
	public void update(Object object) {
		Entity entity = entityRepository.getEntity(object.getClass());
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entity, "", Immutables.emptyList(AliasJoin.class));
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle, false);
		
		TranslatorContext context = new TranslatorContext(this.dialect, this.controller, linkedTableEntityBundle, linkedEntityBundle, false, defaultSchema);
		UpdateEntityExpression updateEntityExpression = new UpdateEntityExpression("update", "");
		
		ParamValues paramValues = new ParamValues(Immutables.emptyStringMap(), Collections.singletonList(object));
		Translator translator = controller.findTranslator(updateEntityExpression, paramValues);
		SqlBundle translateResult = translator.translate(context, updateEntityExpression, paramValues);
		
		AssertState.isTrue(translateResult.size() > 0, "translated result is empty");
		

		jdbcExecutor.beginUnitOfWork();
		try {
			for (int i = 0; i < translateResult.size(); i++) {
				String sql = translateResult.getSql(i);
				List<?> values = translateResult.getParamValues(i);
				jdbcExecutor.update(sql, values);
			}
			
		}
		finally {
			jdbcExecutor.endUnitOfWork();
		}
	}
	
	public void delete(Object object) {
		Entity entity = entityRepository.getEntity(object.getClass());
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entity, "", Immutables.emptyList(AliasJoin.class));
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle, false);
		
		TranslatorContext context = new TranslatorContext(this.dialect, this.controller, linkedTableEntityBundle, linkedEntityBundle, false, defaultSchema);

		jdbcExecutor.beginUnitOfWork();
		try {
			DeleteEntityExpression deleteEntityExpression = new DeleteEntityExpression("delete", "");
			
			ParamValues paramValues = new ParamValues(Immutables.emptyStringMap(), Collections.singletonList(object));
			Translator translator = controller.findTranslator(deleteEntityExpression, paramValues);
			SqlBundle translateResult = translator.translate(context, deleteEntityExpression, paramValues);
			
			AssertState.isTrue(translateResult.size() > 0, "translated result is empty");
			
			for (int i = 0; i < translateResult.size(); i++) {
				String sql = translateResult.getSql(i);
				List<?> values = translateResult.getParamValues(i);
				jdbcExecutor.update(sql, values);
			}
		}
		finally {
			jdbcExecutor.endUnitOfWork();
		}
		
	}

}
