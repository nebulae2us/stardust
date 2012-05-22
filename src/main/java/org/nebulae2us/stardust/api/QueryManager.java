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

import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.translate.domain.TranslatorController;

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
	
	public QueryManager(EntityRepository entityRepository, TranslatorController controller) {
		this.entityRepository = entityRepository;
		this.controller = controller;
	}
	
	public <T> QueryBuilder<T> newQuery(Class<T> entityClass) {
		return new QueryBuilder<T>(this.entityRepository, this.controller, entityClass);
	}
	
	
	
}
