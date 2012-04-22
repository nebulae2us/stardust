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

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.electron.Converter;
import org.nebulae2us.electron.util.ListBuilder;
import org.nebulae2us.stardust.Builders;
import org.nebulae2us.stardust.db.domain.JoinType;
import org.nebulae2us.stardust.jpa.group1.*;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityRepository;

import static org.nebulae2us.stardust.Builders.*;
import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class LinkedEntityBundleTest {

	private EntityRepository entityRepository;
	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
	}
	
	@Test
	public void alias_level_one() {
		

		Entity house = this.entityRepository.getEntity(House.class);
		List<AliasJoinBuilder<?>> aliasJoinBuilders = Arrays.asList(new AliasJoinBuilder<?>[] {
				aliasJoin().name("rooms").alias("r").joinType(JoinType.INNER_JOIN),
				aliasJoin().name("owners").alias("o").joinType(JoinType.INNER_JOIN)
				});

		List<AliasJoin> aliasJoins = new Converter(Builders.DESTINATION_CLASS_RESOLVER, true).convert(aliasJoinBuilders).toListOf(AliasJoin.class);
		
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(house, "", aliasJoins);
		
		assertTrue(linkedEntityBundle.getRoot().getEntity() == house);
		assertNotNull(linkedEntityBundle.getLinkedEntity("r"));
		assertNotNull(linkedEntityBundle.getLinkedEntity("o"));
		
		
	}
	
	@Test
	public void alias_level_two() {
		
		Entity person = this.entityRepository.getEntity(Person.class);
		List<AliasJoinBuilder<?>> aliasJoinBuilders = new ListBuilder<AliasJoinBuilder<?>>()
			.add(
				aliasJoin().name("houses").alias("h").joinType(JoinType.LEFT_JOIN),
				aliasJoin().name("h.rooms").alias("r").joinType(JoinType.LEFT_JOIN),
				aliasJoin().name("h.owners").alias("o").joinType(JoinType.LEFT_JOIN)
			).toList();
		
		List<AliasJoin> aliasJoins = new Converter(Builders.DESTINATION_CLASS_RESOLVER, true).convert(aliasJoinBuilders).toListOf(AliasJoin.class);
		
		LinkedEntityBundle linkedEntityBundle = LinkedEntityBundle.newInstance(person, "", aliasJoins);
		
		assertNotNull(linkedEntityBundle.getLinkedEntity("h"));
		assertEquals(JoinType.LEFT_JOIN, linkedEntityBundle.getLinkedEntity("h").getJoinType());
		assertNotNull(linkedEntityBundle.getLinkedEntity("r"));
		assertEquals(JoinType.LEFT_JOIN, linkedEntityBundle.getLinkedEntity("r").getJoinType());
		assertNotNull(linkedEntityBundle.getLinkedEntity("o"));
		assertEquals(JoinType.LEFT_JOIN, linkedEntityBundle.getLinkedEntity("o").getJoinType());
	}
	
	
	
}
