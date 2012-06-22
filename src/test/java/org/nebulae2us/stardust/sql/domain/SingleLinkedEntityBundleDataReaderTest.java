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
package org.nebulae2us.stardust.sql.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Id;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.adapter.YesNoAdapter;
import org.nebulae2us.stardust.annotation.TypeAdapter;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityRepository;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class SingleLinkedEntityBundleDataReaderTest {

	
	private EntityRepository entityRepository;
	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
	}
	
	@Test
	public void read_adapted_boolean_value() {
		
		class Person {
			@Id
			Long id;
			
			@TypeAdapter(YesNoAdapter.class)
			Boolean married;
		}

		List<String> columnNames = Arrays.asList("ID", "MARRIED");
		List<Object[]> data = new ListBuilder<Object[]>()
				.add(new Object[]{1L, "Y"})
				.toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);
		
		Entity entity = entityRepository.getEntity(Person.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Immutables.emptyList(AliasJoin.class));

		List<Person> result = (List<Person>)bundle.readData(this.entityRepository, dataReader);
		
		assertEquals(1, result.size());
		assertEquals(Boolean.TRUE, result.get(0).married);
	}

	
	@Test
	public void read_adapted_boolean_value_as_id() {
		
		class MarriageStatus {
			@Id
			@TypeAdapter(YesNoAdapter.class)
			Boolean id;
		}

		List<String> columnNames = Arrays.asList("ID");
		List<Object[]> data = new ListBuilder<Object[]>()
				.add(new Object[]{"Y"})
				.toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);
		
		Entity entity = entityRepository.getEntity(MarriageStatus.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Immutables.emptyList(AliasJoin.class));

		List<MarriageStatus> result = (List<MarriageStatus>)bundle.readData(this.entityRepository, dataReader);
		
		assertEquals(1, result.size());
		assertEquals(Boolean.TRUE, result.get(0).id);
	}
	
}
