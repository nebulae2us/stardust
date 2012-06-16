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
import org.nebulae2us.electron.util.ListBuilder;

/**
 * @author Trung Phan
 *
 */
public class CommonTranslatorController extends TranslatorController {

	public CommonTranslatorController() {
		this(Immutables.emptyList(Translator.class));
	}
	
	public CommonTranslatorController(List<Translator> translators) {
		super(new ListBuilder<Translator>()
				.add(new AnyAllTranslator())
				.add(new AttributeTranslator())
				.add(new BetweenTranslator())
				.add(new ComparisonTranslator())
				.add(new InListTranslator())
				.add(new IsNullTranslator())
				.add(new LikeTranslator())
				.add(new LogicalTranslator())
				.add(new NamedParamTranslator())
				.add(new OrderTranslator())
				.add(new QueryTranslator())
				.add(new WildcardTranslator())
				.add(new InsertEntityTranslator())
				.add(new UpdateEntityTranslator())
				.add(translators)
				.toList());
	}

}
