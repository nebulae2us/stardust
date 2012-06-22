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

import java.util.List;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.junit.Test;
import org.nebulae2us.stardust.exception.IllegalSyntaxException;

import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class NonIdEntityTest {

	private final EntityRepository entityRepository = new EntityRepository();
	
	@Test
	public void nonIdEntity_allowed() throws Exception {
		
		class Person {
			String firstName;
			String lastName;
			
		}

		Entity person = this.entityRepository.getEntity(Person.class);
		
		assertEquals(0, person.getEntityIdentifier().getScalarAttributes().size());
	}
	
	
	@Test(expected=IllegalSyntaxException.class)
	public void nonIdEntity_cannot_have_many_to_one_relationship_with_other_nonIdEntity() {
		
		class Person {
			String firstName;
			String lastName;
			
			@ManyToOne
			Person parent;
		}
		
		Entity person = this.entityRepository.getEntity(Person.class);
		
	}
	
	@Test(expected=IllegalSyntaxException.class)
	public void nonIdEntity_cannot_have_one_to_many_relationship_with_other_entity() {
		class House {
			@Id
			String streetAddress;
		}
		
		class Person {
			String firstName;
			String lastName;
			
			@OneToMany
			List<House> houses;
		}
		
		this.entityRepository.getEntity(Person.class);
	}
	
	@Test(expected=IllegalSyntaxException.class)
	public void entity_cannot_have_many_to_one_relation_with_nonIdEntity() {
		class Person {
			String name;
		}

		class House {
			@Id
			Long id;
			
			@ManyToOne
			Person owner;
		}
		
		this.entityRepository.getEntity(House.class);
		
	}
	
	@Test
	public void entity_can_have_one_to_many_relation_with_nonIdEntity_but_not_recommended() {
		class Person {
			@Id Long id;
			@OneToMany(mappedBy="owner") List<House> houses;

			class House {
				String address;
				@ManyToOne Person owner;
			}
		}
		
		
		this.entityRepository.getEntity(Person.class);
	}
	
}
