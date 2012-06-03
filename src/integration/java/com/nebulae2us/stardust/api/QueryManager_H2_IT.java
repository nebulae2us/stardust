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
package com.nebulae2us.stardust.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.nebulae2us.stardust.api.QueryManager;
import org.nebulae2us.stardust.ddl.domain.H2DDLGenerator;
import org.nebulae2us.stardust.jpa.group1.BasicName;
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
import org.nebulae2us.stardust.translate.domain.CommonTranslatorController;
import org.nebulae2us.stardust.translate.domain.InsertEntityTranslator;
import org.nebulae2us.stardust.translate.domain.Translator;
import org.nebulae2us.stardust.translate.domain.TranslatorController;

import com.nebulae2us.stardust.BaseIntegrationTest;
import static org.junit.Assert.*;

/**
 * @author Trung Phan
 *
 */
public class QueryManager_H2_IT extends BaseIntegrationTest {

	private final EntityRepository entityRepository;
	
	private final QueryManager queryManager;
	
	private final TranslatorController controller;
	
	public QueryManager_H2_IT() {
		super("h2-in-memory");
		
		this.entityRepository = new EntityRepository();
		this.entityRepository.scanEntities(Passport.class, Person.class, Room.class, Kitchen.class, BedRoom.class, House.class, Bungalow.class, Castle.class);
		
		List<Translator> translators = new ArrayList<Translator>();
		translators.add(new InsertEntityTranslator());
		
		this.controller = new CommonTranslatorController(translators);
		this.queryManager = new QueryManager(jdbcOperation, entityRepository, controller);
		
		H2DDLGenerator generator = new H2DDLGenerator();
		List<String> ddlSqls = generator.generateTable(entityRepository);
		
		for (String ddlSql : ddlSqls) {
			jdbcOperation.execute(ddlSql);
		}
	}


	@Test
	public void save_person() {
		
		Person person = new Person("123-12-1234", new Date(0));
		person.setName(new BasicName());
		person.getName().setFirstName("test first name");
		
		queryManager.save(person);
		
		List<Person> people = queryManager.newQuery(Person.class).list();

		assertEquals(1, people.size());
		
		assertEquals("123-12-1234", people.get(0).getSsn());
		assertEquals("test first name", people.get(0).getName().getFirstName());
		assertNull(people.get(0).getName().getLastName());
	}
	
	@Test
	public void save_password_for_person() {
		
		Person person = new Person("123-12-1234", new Date(0));
		
		queryManager.save(person);
		
		Passport passport = new Passport(100);
		passport.setOwner(person);
		
		queryManager.save(passport);
		
		List<Passport> passports = queryManager.newQuery(Passport.class).outerJoin("owner", "o").list();
		
		assertEquals(1, passports.size());
		assertEquals(100, passports.get(0).getPassportNumber());
		assertEquals("123-12-1234", passports.get(0).getOwner().getSsn());

		person = passports.get(0).getOwner();
		
		assertEquals("123-12-1234", person.getSsn());
		assertNotNull(person.getPassport());
		assertEquals(100, person.getPassport().getPassportNumber());
		
	}
	
	@Test
	public void save_house() {
		House house = new House(10, "a");
		house.setBedroomCount(3);
		
		queryManager.save(house);
		
		
		house = queryManager.newQuery(House.class).uniqueValue();
		assertEquals(10, house.getHouseId().getHouseId());
		assertEquals("a", house.getHouseId().getHouseLetter());
		assertEquals(3, house.getBedroomCount());
	}
	

	@Test
	public void save_castle() {
		CastleAssociation castleAssociation = new CastleAssociation(20L);
		queryManager.save(castleAssociation);
		
		Castle castle = new Castle(10, "a");
		castle.setCastleAssociation(castleAssociation);
		
		queryManager.save(castle);
		
		
		House house = queryManager.newQuery(House.class).uniqueValue();
		
		assertTrue(house instanceof Castle);

		castle = (Castle)house;
		
		assertNotNull(castle.getCastleAssociation());
		assertEquals(Long.valueOf(20), castle.getCastleAssociation().getId());
	}
	
	@Test
	public void update_person() {
		
		Person person = new Person("123-12-1234", new Date(0));
		
		queryManager.save(person);

		person.setName(new BasicName());
		person.getName().setFirstName("test first name");

		queryManager.update(person);
		
		List<Person> people = queryManager.newQuery(Person.class).list();

		assertEquals(1, people.size());
		
		assertEquals("123-12-1234", people.get(0).getSsn());
		assertEquals("test first name", people.get(0).getName().getFirstName());
		assertNull(people.get(0).getName().getLastName());
	}
	
	
}