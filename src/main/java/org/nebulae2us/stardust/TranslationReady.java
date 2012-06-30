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

import org.nebulae2us.stardust.dao.SqlBundle;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.translate.domain.ParamValues;
import org.nebulae2us.stardust.translate.domain.Translator;
import org.nebulae2us.stardust.translate.domain.TranslatorContext;
import org.nebulae2us.stardust.translate.domain.TranslatorController;

/**
 * @author Trung Phan
 *
 */
public abstract class TranslationReady {
	
	private final Expression expression;
	
	private final TranslatorContext translatorContext;
	
	private final ParamValues paramValues;
	
	public TranslationReady(TranslatorContext translatorContext, Expression expression, ParamValues paramValues) {
		this.translatorContext = translatorContext;
		this.expression = expression;
		this.paramValues = paramValues;
	}

	public SqlBundle translate() {
		TranslatorController controller = translatorContext.getTranslatorController();
		Translator translator = controller.findTranslator(this.expression, this.paramValues);
		return translator.translate(this.translatorContext, this.expression, this.paramValues);
	}

	public TranslatorContext getTranslatorContext() {
		return translatorContext;
	}

}
