The Stardust is a SQL-centric ORM framework for Java. It utilize JPA annotation 2.0 for defining entity and value objects.

##Quick Examples

Given a class Person:

``` java
class Person {

	@Id
	private Long personId;

	private String firstName;
	
	private String lastName;
	
	// getters and setters;
}
```
To get a list of people:

``` java
DaoManager daoManager = new DaoManager(dataSource, OracleDialect.getInstance());

List<Person> allPeople = daoManager.newQuery(Person.class).list();
```

