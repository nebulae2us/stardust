The Stardust is a SQL-centric ORM framework for Java. It utilizes JPA annotation 2.0 for defining entity and value objects. However, it's not following JPA specs. It has its own API for query and update database.

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

// Subquery
Query<?> subQuery = daoManager.newQuery(Person.class)
		.select("personId")
		.filterBy("firstName like ?", "M%")
		.toQuery();

List<Person> result = daoManager.newQuery(Person.class)
		.filterBy("personId in (?)", subQuery)
		.list();

String yourOwnSql = "select person_id, first_name, last_name from person";
List<Person> mikes = daoManager.newQuery(Person.class)
		.backedBySql(yourOwnSql)
		.filterBy("firstName = ?", "Mike")
		.list();

```

To create a new person:
``` java
Person person = new Person(1L, "Michael", "Scott", 40);
daoManager.save(person);
```

To update the person's name:
``` java
person = daoManager.get(Person.class, 1);
person.firstName = "Mike";
daoManager.update(person);
```

##Why Stardust
__SQL centric__: Stardust's DaoManager not only automatically generates SQL for you, but also let you plugs your own SQL. Moreover, its JdbcExecutor let you execute SQL at ease.

__Clean POJO object__: Objects are not proxied. No error like Hibernate's `LazyInitializationException`.


##Getting Started
Required  JAR:
* stardust-1.0.0.jar
* electron-1.0.0.jar
* slf4-api.jar

The heart of Stardust are JdbcExecutor and DaoManager. JdbcExecutor helps you execute SQL. DaoManagers help you to map relational data to java objects. DaoManager contains 2 engines behind the scene. The first engine translate query requirement into SQL. It then uses JdbcExecutor to execute the SQL. The second engine is to translate the SQL's result into java objects. As the 2 engines are separate, you can plugs your own SQL and utilize the second engine to convert data into java objects.

###JdbcExecutor

To create JdbcExecutor:
``` java
JdbcExecutor jdbcExecutor = new JdbcExecutor(dataSource, dialect);
```

Let's execute some SQLs:
``` java
Long one = jdbcExecutor.queryForLong("select 1 from dual");
```

To execute any SQL, JdbcExecutor will get a `Connection` out of the `DataSource` and will release the connection. To release the connection, if the dataSource implements `ReleaseConnectionHandler` interface, it will call `releaseConnection(connection)`, otherwise, it will close the connection using `connection.close()`.

If most cases, closing connection is desirable. For example, if you use `ConnectionPoolDataSource`, closing connection will return the connection back to the pool to make it available for other threads. If you have `TransactionManager` (which will be discussed later), the TransactionManager will take care of the transaction for you even though connection is closed on every SQL executed.

If closing the connection can be a problem for you, there are a few options. First, use method `beginUnitOfWord` and `endUnitOfWork`:

``` java
jdbcExecutor.beginUnitOfWork();
try {
	// ... execute SQL statements
}
finally {
	jdbcExecutor.endUnitOfWork();
}
```
When `jdbcExecutor.beginUnitOfWork()` is called, it increases a lock. Each SQL execution within the unit of work will not close the connection. When `endUnitOfWork()` is called, the lock value is decreased. If the lock value is reduced to 0, the `JdbcExecutor` will call method `JdbcExecutor.releaseConnection(Connection)`. `JbdcExecutor.endUnitOfWork()` needs to be in finally block to make sure it release the lock, otherwise, connection leak will surely happen.

The second option is when you implements your own DataSource. This DataSource needs to implement the interface `ReleaseConnectionHandler`. Every time the connection is released, it actually call the releaseConnection() method of the DataSource. As you implement the method releaseConnection, it's in your control to decide if you want to close the connection or not.

###DaoManager
To instantiate DaoManager:
``` java
DaoManager daoManager = new DaoManager(jdbcExecutor);
```


