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
public class AnyAllTranslatorTest extends BaseTranslatorTest {

	private AnyAllTranslator anyAllTranslator = new AnyAllTranslator();
	
	@Test
	public void greater_than_any_of_list_of_one() {
		new TestCase().withTranslator(anyAllTranslator).translate("age > any (?)", 12)
			.expectResult("mockedAttribute_age > any (?)")
			.expectValues(12);
	}

	@Test
	public void greater_than_some_of_list_of_one() {
		new TestCase().withTranslator(anyAllTranslator).translate("age > some (?)", 12)
			.expectResult("mockedAttribute_age > any (?)")
			.expectValues(12);
	}

	@Test
	public void greater_than_all_of_list_of_one() {
		new TestCase().withTranslator(anyAllTranslator).translate("age > all (?)", 12)
			.expectResult("mockedAttribute_age > all (?)")
			.expectValues(12);
	}

	@Test
	public void less_than_any_of_sub_query() {
		new TestCase().withTranslator(anyAllTranslator).translate("age < any (?)", mockedSubQuery)
			.expectResult("mockedAttribute_age < any (select ? from mockedSubQuery)")
			.expectValues("mockedSubQueryValue");
	}
}
