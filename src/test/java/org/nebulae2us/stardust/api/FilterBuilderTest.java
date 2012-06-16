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

import java.util.List;

import org.junit.Test;
import org.nebulae2us.stardust.Filter;
import org.nebulae2us.stardust.FilterBuilder;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class FilterBuilderTest {

	@Test
	public void a() {
		
		Filter filter = new FilterBuilder()
			.predicate("a")
			.toFilter();
		
		assertEquals("a", filter.toString());
		
	}
	
	@Test
	public void not_a() {
		Filter filter = new FilterBuilder()
			.negatedPredicate("a")
			.toFilter();
		
		assertEquals("not a", filter.toString());
	}
	
	@Test
	public void a_and_b() {
		Filter filter = new FilterBuilder()
			.predicate("a")
			.and().predicate("b")
			.toFilter();
		
		assertEquals("and[a, b]", filter.toString());
	}
	
	@Test
	public void a_or_b() {
		Filter filter = new FilterBuilder()
			.predicate("a")
			.or().predicate("b")
			.toFilter();
		
		assertEquals("or[a, b]", filter.toString());
	}
	
	@Test
	public void not_a_or_b_or_c() {
		Filter filter = new FilterBuilder()
			.negatedGroup()
				.predicate("a")
				.or().predicate("b")
			.endGroup()
			.or().predicate("c")
			.toFilter();
		
		assertEquals("or[not or[a, b], c]", filter.toString());
	}
	
}
