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
package org.nebulae2us.stardust.translate.domain;

import org.nebulae2us.stardust.sql.domain.LinkedEntityBundle;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;

/**
 * @author Trung Phan
 *
 */
public class TranslatorContext {

	private final TranslatorController translatorController;
	
	private final int maxInListSize = 1000;
	
	private final boolean externalSql;
	
	private final LinkedTableEntityBundle linkedTableEntityBundle;
	
	private final LinkedEntityBundle linkedEntityBundle;
	
	public TranslatorContext(TranslatorController translatorController, LinkedTableEntityBundle linkedTableEntityBundle, LinkedEntityBundle linkedEntityBundle, boolean externalSql) {
		this.translatorController = translatorController;
		this.linkedTableEntityBundle = linkedTableEntityBundle;
		this.linkedEntityBundle = linkedEntityBundle;
		this.externalSql = externalSql;
	}

	public boolean isExternalSql() {
		return externalSql;
	}

	public LinkedTableEntityBundle getLinkedTableEntityBundle() {
		return linkedTableEntityBundle;
	}

	public LinkedEntityBundle getLinkedEntityBundle() {
		return linkedEntityBundle;
	}
	
	public TranslatorController getTranslatorController() {
		return translatorController;
	}

	public int getMaxInListSize() {
		return maxInListSize;
	}
	
}
