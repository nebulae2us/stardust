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
package org.nebulae2us.stardust;

import cucumber.annotation.After;
import org.junit.Before;
import org.junit.Test;
import org.nebulae2us.stardust.ddl.domain.DDLGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Trung Phan
 *
 */
public class ExampleIT extends BaseIntegrationTest {

	private DaoManager daoManager;

	private DDLGenerator ddlGenerator;

	public ExampleIT() {
		super("h2-in-memory");
	}
	
	@Before
	public void setup() {
		this.daoManager = new DaoManager(dataSource, dialect);
		this.ddlGenerator = new DDLGenerator(dialect, daoManager.getEntityRepository());
	}

    @After
    public void tearDown() {
        List<String> ddls = ddlGenerator.generateDropSchemaObjectsDDL();
        for (String ddl : ddls) {
            jdbcExecutor.execute(ddl);
        }
    }
	
	@Test
	public void example1() {


		class Person {
			@Id
			private Long personId;

			private String firstName;
			private String lastName;
			private Integer age;

			public Person() {

			}

			public Person(Long personId, String firstName, String lastName, Integer age) {
				this.personId = personId;
				this.firstName = firstName;
				this.lastName = lastName;
				this.age = age;
			}
		}

		this.daoManager.getEntityRepository().scanEntities(Person.class);
		
		List<String> ddls = ddlGenerator.generateCreateSchemaObjectsDDL();
		for (String ddl : ddls) {
			jdbcExecutor.execute(ddl);
		}
	
		List<Person> allPeople = daoManager.newQuery(Person.class).list();
		
		List<Person> children = daoManager.newQuery(Person.class)
				.filterBy("age < ?", 18)
				.list();
		
		List<Person> toddler = daoManager.newQuery(Person.class)
				.filterBy("age between ? and ?", 1, 3)
				.list();

		List<Person> first10Eldest = daoManager.newQuery(Person.class)
				.maxResults(10)
				.orderBy("age desc")
				.list();
		
		List<Person> next10Eldest = daoManager.newQuery(Person.class)
				.firstResult(10)
				.maxResults(10)
				.orderBy("age desc")
				.list();
		
		Person person = new Person(1L, "Michael", "Scott", 40);
		daoManager.save(person);
		
		person = daoManager.get(Person.class, 1);
		person.firstName = "Mike";
		daoManager.update(person);
		
		String complexSql = "select person_id, first_name, last_name from person";
		
		List<Person> mikes = daoManager.newQuery(Person.class)
				.backedBySql(complexSql)
				.filterBy("firstName = ?", "Mike")
				.list();
		
		// Subquery
		Query<?> subQuery = daoManager.newQuery(Person.class)
				.select("personId")
				.filterBy("firstName like ?", "M%")
				.toQuery();
		
		List<Person> result = daoManager.newQuery(Person.class)
				.filterBy("personId in (?)", subQuery)
				.list();
		
		
		System.out.println(mikes);
		
		person = daoManager.get(Person.class, 1);
		System.out.println(person.firstName);
		

	}

    @Test
    public void example2() {

        @Entity
        @Inheritance(strategy= InheritanceType.JOINED)
        @DiscriminatorColumn(name="PERSON_TYPE_ID", discriminatorType= DiscriminatorType.INTEGER)
        @DiscriminatorValue("0")
        class Person {
            @Id
            private Long personId;

            private String firstName;
            private String lastName;
            private Integer age;

            public Person() {

            }

            public Person(Long personId, String firstName, String lastName, Integer age) {
                this.personId = personId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.age = age;
            }
        }

        @Entity
        @DiscriminatorValue("1")
        class Student extends Person {
            private BigDecimal gpa;
            public Student() {
            }

            public Student(Long personId, String firstName, String lastName, Integer age, BigDecimal gpa) {
                super(personId, firstName, lastName, age);
                this.gpa = gpa;
            }
        }

        @Entity
        @DiscriminatorValue("2")
        class Teacher extends Person {
            private Boolean fulltime;
            public Teacher() {}
            public Teacher(Long personId, String firstName, String lastName, Integer age, Boolean fulltime) {
                super(personId, firstName, lastName, age);
                this.fulltime = fulltime;
            }
        }

        this.daoManager.getEntityRepository().scanEntities(Person.class, Student.class, Teacher.class);

        List<String> ddls = ddlGenerator.generateCreateSchemaObjectsDDL();
        for (String ddl : ddls) {
            jdbcExecutor.execute(ddl);
        }

        Student student1 = new Student(1L, "Dwight", "Schrute", 40, new BigDecimal("3.8"));
        daoManager.save(student1);

        Teacher teacher1 = new Teacher(2L, "Michael", "Scott", 40, true);
        daoManager.save(teacher1);

//        List<Student> allStudents = daoManager.newQuery(Student.class).list();
//        assertEquals(1, allStudents.size());
//        assertEquals(new BigDecimal("3.8"), allStudents.get(0).gpa);

        List<Person> allPersons = daoManager.newQuery(Person.class).orderBy("personId").list();
        assertEquals(2, allPersons.size());
        assertTrue(allPersons.get(0) instanceof Student);
        assertTrue(allPersons.get(1) instanceof Teacher);


    }
	
	
}
