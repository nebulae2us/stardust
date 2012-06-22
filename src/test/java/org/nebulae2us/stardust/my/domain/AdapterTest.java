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
package org.nebulae2us.stardust.my.domain;

import javax.persistence.AccessType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.junit.Test;
import org.nebulae2us.stardust.adapter.NamedEnumAdapter;
import org.nebulae2us.stardust.adapter.YesNoAdapter;
import org.nebulae2us.stardust.annotation.TypeAdapter;

/**
 * @author Trung Phan
 *
 */
public class AdapterTest {

	@Test
	public void should_be_able_to_apply_yes_no_adapter() {
		
		class Person {
			@TypeAdapter(YesNoAdapter.class)
			private Boolean married;
		}
		
	}
	
	@Test
	public void should_be_able_to_define_adapter_for_enum() {

		class Something {
			@TypeAdapter(NamedEnumAdapter.class)
			private AccessType accessType;
		}
		
	}
}
