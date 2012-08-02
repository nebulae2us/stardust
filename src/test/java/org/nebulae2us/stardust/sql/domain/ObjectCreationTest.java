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

import javax.persistence.Embedded;
import javax.persistence.Id;

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.electron.Mirror;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityRepository;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class ObjectCreationTest {
	
	private EntityRepository entityRepository;
	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
	}

	@Test
	public void can_populate_immutable_value_object() {
		
		class Color {
			public Color(Mirror mirror) {
				mirror.bind(this);
				this.red = mirror.toIntValue("red");
				this.green = mirror.toIntValue("green");
				this.blue = mirror.toIntValue("blue");
			}
			
			private final int red;
			private final int green;
			private final int blue;
		}
		
		class Car {
			@Id
			private int carId;
			
			@Embedded
			private Color color;
		}
		
		List<String> columnNames = Arrays.asList("CAR_ID", "RED", "GREEN", "BLUE");
		List<Object[]> data = new ListBuilder<Object[]>()
				.add(new Object[]{1, 100, 100, 100})
				.toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);
		
		Entity entity = entityRepository.getEntity(Car.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Immutables.emptyList(AliasJoin.class));

		List<Car> cars = (List<Car>)bundle.readData(this.entityRepository, dataReader);
		assertEquals(1, cars.size());
		
		Car car = cars.get(0);
		
		assertEquals(100, car.color.red);
		assertEquals(100, car.color.green);
		assertEquals(100, car.color.blue);
	}
	
	@Test
	public void can_populate_mixed_immutable_and_mutable_fields() {
		
		class Color {
			public Color(Mirror mirror) {
				mirror.bind(this);
				this.green = mirror.toIntValue("green");
				this.blue = mirror.toIntValue("blue");
			}
			
			private int red;
			private final int green;
			private final int blue;
		}
		
		class Car {
			@Id
			private int carId;
			
			@Embedded
			private Color color;
		}
		
		List<String> columnNames = Arrays.asList("CAR_ID", "RED", "GREEN", "BLUE");
		List<Object[]> data = new ListBuilder<Object[]>()
				.add(new Object[]{1, 100, 100, 100})
				.toList();

		DataReader dataReader = new MockedDataReader(columnNames, data);
		
		Entity entity = entityRepository.getEntity(Car.class);
		LinkedEntityBundle bundle = LinkedEntityBundle.newInstance(entity, "", Immutables.emptyList(AliasJoin.class));

		List<Car> cars = (List<Car>)bundle.readData(this.entityRepository, dataReader);
		assertEquals(1, cars.size());
		
		Car car = cars.get(0);
		
		assertEquals(100, car.color.red);
		assertEquals(100, car.color.green);
		assertEquals(100, car.color.blue);
	}
	
}
