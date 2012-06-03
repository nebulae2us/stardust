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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mockit.Mocked;

import org.junit.Test;
import org.nebulae2us.stardust.api.QueryManager;
import org.nebulae2us.stardust.api.Update;
import org.nebulae2us.stardust.api.UpdateBuilder;
import org.nebulae2us.stardust.dao.domain.JdbcOperation;
import org.nebulae2us.stardust.expr.domain.PredicateExpression;
import org.nebulae2us.stardust.expr.domain.UpdateExpression;
import org.nebulae2us.stardust.jpa.group1.BedRoom;
import org.nebulae2us.stardust.jpa.group1.Bungalow;
import org.nebulae2us.stardust.jpa.group1.Castle;
import org.nebulae2us.stardust.jpa.group1.CastleAssociation;
import org.nebulae2us.stardust.jpa.group1.House;
import org.nebulae2us.stardust.jpa.group1.Kitchen;
import org.nebulae2us.stardust.jpa.group1.Passport;
import org.nebulae2us.stardust.jpa.group1.Person;
import org.nebulae2us.stardust.jpa.group1.Room;
import org.nebulae2us.stardust.my.domain.EntityRepository;

/**
 * @author Trung Phan
 *
 */
public class UpdateTranslatorTest {

	private final EntityRepository entityRepository;
	private final TranslatorController controller;
	private final QueryManager queryManager;
	@Mocked private JdbcOperation jdbcOperation;

	public UpdateTranslatorTest() {
		this.entityRepository = new EntityRepository();
		this.entityRepository.scanEntities(Passport.class, Person.class, Room.class, Kitchen.class, BedRoom.class, House.class, Bungalow.class, Castle.class);

		this.controller = new CommonTranslatorController(Collections.EMPTY_LIST);
		
		this.queryManager = new QueryManager(jdbcOperation, entityRepository, controller);
	}
	
	@Test
	public void test() {
		
		UpdateBuilder<CastleAssociation> updateBuilder = new UpdateBuilder<CastleAssociation>(queryManager, CastleAssociation.class);
		
		List<Update<CastleAssociation>> updates = updateBuilder.set("name = ?", "a name")
			.filterBy()
				.predicate("id=?", 100)
			.endFilter()
			.toUpdates();
		;
		

		Update<CastleAssociation> update = updates.get(0);
		
		UpdateExpression updateExpression = update.getUpdateExpression();
		

	}
	
}
