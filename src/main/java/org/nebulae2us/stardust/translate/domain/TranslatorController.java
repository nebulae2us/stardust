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

import java.util.List;

import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.expr.domain.Expression;

/**
 * @author Trung Phan
 *
 */
public class TranslatorController {

	private final List<Translator> translators;
	
	public TranslatorController(List<Translator> translators) {
		this.translators = Immutables.$(translators);
	}
	
	public Translator findTranslator(Expression expression, ParamValues paramValues) {
		for (int i = translators.size() - 1; i >= 0; i--) {
			Translator translator = translators.get(i);
			if (translator.accept(expression, paramValues)) {
				return translator;
			}
		}
		return null;
	}
}
