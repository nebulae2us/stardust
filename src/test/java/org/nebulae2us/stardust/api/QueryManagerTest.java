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

import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.electron.util.Immutables;
import org.nebulae2us.stardust.jpa.group1.BedRoom;
import org.nebulae2us.stardust.jpa.group1.Bungalow;
import org.nebulae2us.stardust.jpa.group1.Castle;
import org.nebulae2us.stardust.jpa.group1.House;
import org.nebulae2us.stardust.jpa.group1.Kitchen;
import org.nebulae2us.stardust.jpa.group1.Passport;
import org.nebulae2us.stardust.jpa.group1.Person;
import org.nebulae2us.stardust.jpa.group1.Room;
import org.nebulae2us.stardust.my.domain.EntityRepository;
import org.nebulae2us.stardust.translate.domain.Translator;
import org.nebulae2us.stardust.translate.domain.TranslatorController;

/**
 * @author Trung Phan
 *
 */
public class QueryManagerTest {

	private EntityRepository entityRepository;
	private QueryManager queryManager;
	private TranslatorController controller;
	
	@Before
	public void setup() {
		this.entityRepository = new EntityRepository();
		this.entityRepository.scanEntities(Bungalow.class, Castle.class, Kitchen.class, Person.class, Passport.class, BedRoom.class);
		this.queryManager = new QueryManager(null, entityRepository, controller);
		this.controller = new TranslatorController(Immutables.emptyList(Translator.class));
	}
	
	
	@Test
	public void insert_house() {
		House house = new House();
		
		queryManager.save(house);
	}


	@Test
	public void insert_passport() {
		Passport passport = new Passport();
		
		queryManager.save(passport);
	}

}
