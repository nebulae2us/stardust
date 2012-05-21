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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class BaseFilterBuilderTest {

	@Test
	public void one_expression() {
		
		BaseFilterBuilder builder = new BaseFilterBuilder() {};
		
		builder.predicate("condition 1");
		
		assertEquals("condition 1", builder.toString());
	}
	
	@Test
	public void expression_and_expression() {
		BaseFilterBuilder builder = new BaseFilterBuilder() {};
		
		builder.predicate("a")
			.and().predicate("b");
		
		assertEquals("and[a, b]", builder.toString());
	}
	
	@Test
	public void expression_or_expression() {
		BaseFilterBuilder builder = new BaseFilterBuilder() {};
		
		builder.predicate("a")
			.or().predicate("b");
		
		assertEquals("or[a, b]", builder.toString());
		
	}
	
	@Test
	public void a_and_b_or_c() {
		BaseFilterBuilder builder = new BaseFilterBuilder() {};
		
		builder.predicate("a")
			.and().predicate("b")
			.or().predicate("c");
		
		assertEquals("or[and[a, b], c]", builder.toString());
	}
	
	@Test
	public void a_or_b_and_c() {
		BaseFilterBuilder builder = new BaseFilterBuilder() {};

		builder.predicate("a")
			.or().predicate("b")
			.and().predicate("c");
		
		assertEquals("or[a, and[b, c]]", builder.toString());
	}
	
	@Test
	public void a_and_group_of_b_and_c() {
		
		BaseFilterBuilder builder = new BaseFilterBuilder() {};
		
		builder.predicate("a")
			.and().group()
				.predicate("b")
				.and().predicate("c")
			.endGroup();
		
		assertEquals("and[a, and[b, c]]", builder.toString());
		
	}
	
	@Test
	public void group_of_ab_and_c() {
		
		BaseFilterBuilder builder = new BaseFilterBuilder() {};
		
		builder
			.group()
				.predicate("a")
				.and().predicate("b")
			.endGroup()
			.and().predicate("c");

		assertEquals("and[and[a, b], c]", builder.toString());
		
	}
	
}
