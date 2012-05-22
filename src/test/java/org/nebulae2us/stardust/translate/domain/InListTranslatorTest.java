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

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Trung Phan
 *
 */
public class InListTranslatorTest extends BaseTranslatorTest {

	private InListTranslator inListTranslator = new InListTranslator();
	
	@Test
	public void in_single_value() {
		new TestCase().withTranslator(inListTranslator).translate("age in (?)", 12)
			.expectResult("mockedAttribute_age in (?)")
			.expectValues(12);
	}
	
	@Test
	public void not_in_single_value() {
		new TestCase().withTranslator(inListTranslator).translate("age not in (?)", 12)
			.expectResult("mockedAttribute_age not in (?)")
			.expectValues(12);
	}

	@Test
	public void negated_in_single_value() {
		new TestCase().withTranslator(inListTranslator).translateNegationOf("age in (?)", 12)
			.expectResult("mockedAttribute_age not in (?)")
			.expectValues(12);
	}

	@Test
	public void negated_not_in_single_value() {
		new TestCase().withTranslator(inListTranslator).translateNegationOf("age not in (?)", 12)
			.expectResult("mockedAttribute_age in (?)")
			.expectValues(12);
	}

	@Test
	public void in_two_values() {
		new TestCase().withTranslator(inListTranslator).translate("age in (?, ?)", 12, 15)
			.expectResult("mockedAttribute_age in (?,?)")
			.expectValues(12, 15);
	}
	
	@Test
	public void wildcard_in_two_values() {
		new TestCase().withTranslator(inListTranslator).translate("? in (?, ?)", 1, 10, 11)
			.expectResult("? in (?,?)")
			.expectValues(1, 10, 11);
	}
	
	@Test
	public void in_a_long_list() {
		new TestCase().withTranslator(inListTranslator).translate("age in (?)", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
			.expectResult("(mockedAttribute_age in (?,?,?,?,?,?,?,?,?,?) or mockedAttribute_age in (?,?,?,?,?))")
			.expectValues(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
		
	}

	@Test
	public void negated_in_a_long_list() {
		new TestCase().withTranslator(inListTranslator).translateNegationOf("age in (?)", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
			.expectResult("(mockedAttribute_age not in (?,?,?,?,?,?,?,?,?,?) and mockedAttribute_age not in (?,?,?,?,?))")
			.expectValues(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
		
	}
	
	@Test
	public void in_multiple_long_list() {
		new TestCase().withTranslator(inListTranslator).assignParam("ageList", Arrays.asList(1, 2, 3, 4, 5, 6, 7))
		.translate("age in (?, :ageList)", Arrays.asList(11, 12, 13, 14, 15, 16, 17))
		.expectResult("(mockedAttribute_age in (?,?,?,?,?,?,?,?,?,?) or mockedAttribute_age in (?,?,?,?))")
		.expectValues(11, 12, 13, 14, 15, 16, 17, 1, 2, 3, 4, 5, 6, 7);
	}

	@Test
	public void in_long_list_with_attribute() {
		new TestCase().withTranslator(inListTranslator).assignParam("ageList", Arrays.asList(1, 2, 3, 4, 5, 6, 7))
		.translate("age in (?, myAge, :ageList)", Arrays.asList(11, 12, 13, 14, 15, 16, 17))
		.expectResult("(mockedAttribute_age in (?,?,?,?,?,?,?,mockedAttribute_myAge,?,?) or mockedAttribute_age in (?,?,?,?,?))")
		.expectValues(11, 12, 13, 14, 15, 16, 17, 1, 2, 3, 4, 5, 6, 7);
		
	}

	@Test
	public void in_long_list_with_attribute_going_to_next_list() {
		new TestCase().withTranslator(inListTranslator).assignParam("ageList", Arrays.asList(1, 2, 3, 4, 5, 6, 7))
		.translate("age in (?, myAge, :ageList)", Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19, 20))
		.expectResult("(mockedAttribute_age in (?,?,?,?,?,?,?,?,?,?) or mockedAttribute_age in (mockedAttribute_myAge,?,?,?,?,?,?,?))")
		.expectValues(11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 1, 2, 3, 4, 5, 6, 7);
		
	}
	
	@Test
	public void in_sub_query() {
		new TestCase().withTranslator(inListTranslator).translate("age in (?)", mockedSubQuery)
			.expectResult("mockedAttribute_age in (select ? from mockedSubQuery)")
			.expectValues("mockedSubQueryValue");
	}
	
	@Test
	public void wildcard_in_sub_query() {
		new TestCase().withTranslator(inListTranslator).translate("? in (?)", 1, mockedSubQuery)
		.expectResult("? in (select ? from mockedSubQuery)")
		.expectValues(1, "mockedSubQueryValue");
	}
}
