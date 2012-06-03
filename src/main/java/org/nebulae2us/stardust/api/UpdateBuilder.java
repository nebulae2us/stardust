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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nebulae2us.electron.Pair;
import org.nebulae2us.electron.Procedure;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.expr.domain.AttributeExpression;
import org.nebulae2us.stardust.expr.domain.OrderExpression;
import org.nebulae2us.stardust.expr.domain.PredicateExpression;
import org.nebulae2us.stardust.expr.domain.SelectExpression;
import org.nebulae2us.stardust.expr.domain.SetAttributeExpression;
import org.nebulae2us.stardust.expr.domain.SetExpression;
import org.nebulae2us.stardust.expr.domain.UpdateExpression;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;
import org.nebulae2us.stardust.sql.domain.AliasJoin;
import org.nebulae2us.stardust.sql.domain.LinkedEntityBundle;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntity;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;
import org.nebulae2us.stardust.translate.domain.ParamValues;
import org.nebulae2us.stardust.translate.domain.TranslatorContext;
import org.nebulae2us.stardust.translate.domain.TranslatorController;

import static org.nebulae2us.stardust.Builders.*;

import static org.nebulae2us.stardust.internal.util.BaseAssert.*;

/**
 * @author Trung Phan
 *
 */
public class UpdateBuilder<T> {

	private final QueryManager queryManager;
	
	private final Class<?> entityClass;

	private final List<SetExpression> setExpressions = new ArrayList<SetExpression>();
	private final List<PredicateExpression> predicateExpressions = new ArrayList<PredicateExpression>();
	
	private final Map<String, Object> namedParamValues = new HashMap<String, Object>();
	private final List<Object> selectWildcardValues = new ArrayList<Object>();
	private final List<Object> filterWildcardValues = new ArrayList<Object>();
	
	
	public UpdateBuilder(QueryManager queryManager, Class<T> entityClass) {
		
		AssertSyntax.notNull(entityClass, "Entity Class cannot be null");
		
		this.queryManager = queryManager;
		this.entityClass = entityClass;
	}

	public ChainedFilterBuilder<UpdateBuilder<T>> filterBy() {
		return new ChainedFilterBuilder<UpdateBuilder<T>>(this, new Procedure() {
			public void execute(Object... arguments) {
				Filter filter = (Filter)arguments[0];
				
				Pair<PredicateExpression, List<?>> result = filter.toExpression();
				predicateExpressions.add(result.getItem1());
				
				filterWildcardValues.addAll(result.getItem2());
			}
		});
	}
	
	public UpdateBuilder<T> assignParam(String param, Object value) {
		namedParamValues.put(param, value);
		return this;
	}
	
	public UpdateBuilder<T> set(String expression, Object ... values) {
		
		SetExpression setExpression = SetExpression.parse(expression);
		AssertSyntax.notNull(setExpression, "Invalid select expression: %s.", expression);
		AssertSyntax.isTrue(setExpression.countWildcardExpressions() == values.length, "Values do not match with wildcards in the select expression \"%s\"", expression);
		
		setExpressions.add(setExpression);
		selectWildcardValues.addAll(Arrays.asList(values));
		
		return this;
	}
	
	public List<Update<T>> toUpdates() {
		
		EntityRepository entityRepository = queryManager.getEntityRepository();
		TranslatorController controller = queryManager.getController();
		
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(entityRepository.getEntity(this.entityClass), "", Immutables.emptyList(AliasJoin.class));
		
		LinkedTableEntityBundle linkedTableEntityBundle = LinkedTableEntityBundle.newInstance(entityRepository, linkedEntityBundle, true);

		TranslatorContext context = new TranslatorContext(controller, linkedTableEntityBundle, linkedEntityBundle, false);
		
		for (SetExpression setExpression : setExpressions) {
			SetAttributeExpression setAttributeExpression = (SetAttributeExpression)setExpression;
			AttributeExpression attributeExpression = setAttributeExpression.getLeftOperandExpression();
			
			String alias = "";
			String attrExpr = attributeExpression.getExpression();
			Pair<LinkedTableEntity, ScalarAttribute> searchResult = linkedTableEntityBundle.findAttribute(alias, attrExpr);
			if (searchResult == null) {
				int i = attrExpr.indexOf('.');
				if (i > -1) {
					alias = attrExpr.substring(0, i);
					attrExpr = attrExpr.substring(i + 1);
					searchResult = linkedTableEntityBundle.findAttribute(alias, attrExpr);
				}
			}
			AssertSyntax.notNull(searchResult, "Invalid expression %s", setExpression.getExpression());
			
			LinkedTableEntity linkedTableEntity = searchResult.getItem1();
			ScalarAttribute scalarAttribute = searchResult.getItem2();
			
		}

		UpdateExpression queryExpression = new UpdateExpression("update", this.setExpressions, this.predicateExpressions);
		
//		ParamValues paramValues = new ParamValues(namedParamValues, 
//				new ListBuilder<Object>().add(selectWildcardValues).add(filterWildcardValues).add(orderExpressions).toList());

//		return new Query<T>(context, queryExpression, paramValues);
		
		return null;
	}
	
}
