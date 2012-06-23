The Stardust is a SQL-centric ORM framework for Java. It utilize JPA annotation 2.0 for defining entity and value objects.

##Quick Examples

Given a class Person:

``` java
class Person {

	@Id
	private Long personId;

	private String firstName;
	private String lastName;
	private Integer age;
	
	// getters and setters;
}
```
To get a list of people:

``` java
DaoManager daoManager = new DaoManager(dataSource, OracleDialect.getInstance());

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
```

To create a new Person:
``` jav
Person person = new Person(1L, "Michael", "Scott", 40);
daoManager.save(person);
```

To update the person's name:
``` java
person = daoManager.get(Person.class, 1L);
person.firstName = "Mike";
daoManager.update(person);
```


