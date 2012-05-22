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

import org.junit.Test;

/**
 * @author Trung Phan
 *
 */
public class IsNullTranslatorTest extends BaseTranslatorTest {

	private IsNullTranslator isNullTranslator = new IsNullTranslator();
	
	@Test
	public void wildcard_is_null() {
		new TestCase().withTranslator(isNullTranslator).translate("? is null", (Object)null)
			.expectResult("? is null").expectValues((Object)null);
		
	}

	@Test
	public void negated_wildcard_is_null() {
		
		new TestCase().withTranslator(isNullTranslator).translateNegationOf("? is null", 1)
			.expectResult("? is not null").expectValues(1);
		
	}
	
	@Test
	public void wildcard_is_not_null() {
		new TestCase().withTranslator(isNullTranslator).translate("? is not null", 1)
			.expectResult("? is not null").expectValues(1);
	}

	@Test
	public void negated_wildcard_is_not_null() {
		new TestCase().withTranslator(isNullTranslator).translateNegationOf("? is not null", 1)
			.expectResult("? is null").expectValues(1);
	}
	
}
