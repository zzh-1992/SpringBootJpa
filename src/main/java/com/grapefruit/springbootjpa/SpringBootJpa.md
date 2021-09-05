
[springboot jpa 官方教程](https://spring.io/guides/gs/accessing-data-jpa/)

#相关参数

###spring.jpa.hibernate.ddl-auto
```json
{
      "name": "spring.jpa.hibernate.ddl-auto",
      "values": [
        {
          "value": "none",
          "description": "Disable DDL handling."
        },
        {
          "value": "validate",
          "description": "Validate the schema, make no changes to the database."
        },
        {
          "value": "update",
          "description": "Update the schema if necessary."
        },
        {
          "value": "create",
          "description": "Create the schema and destroy previous data."
        },
        {
          "value": "create-drop",
          "description": "Create and then destroy the schema at the end of the session."
        }
      ]
    },
```

# Using @Query

Using named queries to declare queries for entities is a valid approach and works fine for a small number of queries. 
As the queries themselves are tied to the Java method that runs them, you can actually bind them directly by using
the Spring Data JPA @Query annotation rather than annotating them to the domain class. This frees the domain class 
from persistence specific information and co-locates the query to the repository interface.

Queries annotated to the query method take precedence over queries defined using @NamedQuery 
or named queries declared in orm.xml.

The following example shows a query created with the @Query annotation:

**Example 61. Declare query at the query method using @Query**
```java
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("select u from User u where u.emailAddress = ?1")
  User findByEmailAddress(String emailAddress);
}
```

# Using Advanced LIKE Expressions

The query running mechanism for manually defined queries created with @Query allows the definition of advanced 
LIKE expressions inside the query definition, as shown in the following example:

**Example 62. Advanced** **like expressions in @Query**
```java
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("select u from User u where u.firstname like %?1")
  List<User> findByFirstnameEndsWith(String firstname);
}
```

In the preceding example, the LIKE delimiter character (%) is recognized, and the query is transformed 
into a valid JPQL query (removing the %). Upon running the query, the parameter passed 
to the method call gets augmented with the previously recognized LIKE pattern.

# Native Queries

The @Query annotation allows for running native queries by setting the nativeQuery flag to true,
as shown in the following example:

**Example 63. Declare a native query at the query method using @Query**
```java
public interface UserRepository extends JpaRepository<User, Long> {
  @Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
  User findByEmailAddress(String emailAddress);
}
```
A similar approach also works with named native queries, by adding the .count suffix to a copy of your query. 
You probably need to register a result set mapping for your count query, though.

# Using Sort

Sorting can be done by either providing a PageRequest or by using Sort directly. 
The properties actually used within the Order instances of Sort need to match your domain model,
which means they need to resolve to either a property or an alias used within the query. 
The JPQL defines this as a state field path expression.

However, using Sort together with @Query lets you sneak in non-path-checked Order instances containing functions 
within the ORDER BY clause. This is possible because the Order is appended to the given query string. By default,
Spring Data JPA rejects any Order instance containing function calls, but you can use JpaSort.unsafe 
to add potentially unsafe ordering.

The following example uses Sort and JpaSort, including an unsafe option on JpaSort:

**Example 65. Using Sort and JpaSort**

```java
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.lastname like ?1%")
  List<User> findByAndSort(String lastname, Sort sort);

  @Query("select u.id, LENGTH(u.firstname) as fn_len from User u where u.lastname like ?1%")
  List<Object[]> findByAsArrayAndSort(String lastname, Sort sort);
}

repo.findByAndSort("lannister", Sort.by("firstname"));                
repo.findByAndSort("stark", Sort.by("LENGTH(firstname)"));            
repo.findByAndSort("targaryen", JpaSort.unsafe("LENGTH(firstname)")); 
repo.findByAsArrayAndSort("bolton", Sort.by("fn_len"));               
```
    Valid Sort expression pointing to property in domain model.
    Invalid Sort containing function call. Throws Exception.
    Valid Sort containing explicitly unsafe Order.
    Valid Sort expression pointing to aliased function.

# Using Named Parameters
By default, Spring Data JPA uses position-based parameter binding, as described in all the preceding examples. 
This makes query methods a little error-prone when refactoring regarding the parameter position. To solve this issue, 
you can use @Param annotation to give a method parameter a concrete name and bind the name in the query, 
as shown in the following example:

**Example 66. Using named parameters**
```java
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
  User findByLastnameOrFirstname(@Param("lastname") String lastname,
                                 @Param("firstname") String firstname);
}
```

# Using SpEL Expressions

As of Spring Data JPA release 1.4, we support the usage of restricted SpEL template expressions 
in manually defined queries that are defined with @Query. Upon the query being run, these expressions 
are evaluated against a predefined set of variables. Spring Data JPA supports a variable called entityName. 
Its usage is select x from #{#entityName} x. It inserts the entityName of the domain type associated 
with the given repository. The entityName is resolved as follows: If the domain type has set the name property 
on the @Entity annotation, it is used. Otherwise, the simple class-name of the domain type is used.

The following example demonstrates one use case for the #{#entityName} expression in a query string where you want 
to define a repository interface with a query method and a manually defined query:

**Example 67. Using SpEL expressions in repository query methods - entityName**
```java
@Entity
public class User {
  @Id
  @GeneratedValue
  Long id;
  String lastname;
}
public interface UserRepository extends JpaRepository<User,Long> {
  @Query("select u from #{#entityName} u where u.lastname = ?1")
  List<User> findByLastname(String lastname);
}
```

To avoid stating the actual entity name in the query string of a @Query annotation, 
you can use the #{#entityName} variable.

	The entityName can be customized by using the @Entity annotation. Customizations in orm.xml 
    are not supported for the SpEL expressions.

Of course, you could have just used User in the query declaration directly, but that would require 
you to change the query as well. The reference to #entityName picks up potential future remappings of the User class
to a different entity name (for example, by using @Entity(name = "MyUser").

Another use case for the #{#entityName} expression in a query string is if you want 
to define a generic repository interface with specialized repository interfaces for a concrete domain type. 
To not repeat the definition of custom query methods on the concrete interfaces, you can use the entity name expression 
in the query string of the @Query annotation in the generic repository interface, as shown in the following example:

**Example 68. Using SpEL expressions in repository query methods - entityName with inheritance**
```java
@MappedSuperclass
public abstract class AbstractMappedType {
  …
  String attribute
}

@Entity
public class ConcreteType extends AbstractMappedType { … }

@NoRepositoryBean
public interface MappedTypeRepository<T extends AbstractMappedType>
  extends Repository<T, Long> {

  @Query("select t from #{#entityName} t where t.attribute = ?1")
  List<T> findAllByAttribute(String attribute);
}

public interface ConcreteRepository
  extends MappedTypeRepository<ConcreteType> { … }
```

In the preceding example, the MappedTypeRepository interface is the common parent interface for a few domain types 
extending AbstractMappedType. It also defines the generic findAllByAttribute(…) method, which can be used on instances 
of the specialized repository interfaces. If you now invoke findByAllAttribute(…) on ConcreteRepository, 
the query becomes select t from ConcreteType t where t.attribute = ?1.

SpEL expressions to manipulate arguments may also be used to manipulate method arguments. 
In these SpEL expressions the entity name is not available, but the arguments are. They can be accessed by name 
or index as demonstrated in the following example.

**Example 69. Using SpEL expressions in repository query methods - accessing arguments.**
```java
@Query("select u from User u where u.firstname = ?1 and u.firstname=?#{[0]} and u.emailAddress = ?#{principal.emailAddress}")
List<User> findByFirstnameAndCurrentUserWithCustomQuery(String firstname);
```

For like-conditions one often wants to append % to the beginning or the end of a String valued parameter.
This can be done by appending or prefixing a bind parameter marker 
or a SpEL expression with %. Again the following example demonstrates this.

**Example 70. Using SpEL expressions in repository query methods - wildcard shortcut.**
```java
@Query("select u from User u where u.lastname like %:#{[0]}% and u.lastname like %:lastname%")
List<User> findByLastnameWithSpelExpression(@Param("lastname") String lastname);
```

When using like-conditions with values that are coming from a not secure source the values 
should be sanitized so they can’t contain any wildcards and thereby allow attackers to select more data 
than they should be able to. For this purpose the escape(String) method is made available in the SpEL context. 
It prefixes all instances of _ and % in the first argument with the single character from the second argument. 
In combination with the escape clause of the like expression available in JPQL 
and standard SQL this allows easy cleaning of bind parameters.

**Example 71. Using SpEL expressions in repository query methods - sanitizing input values.**
```java
@Query("select u from User u where u.firstname like %?#{escape([0])}% escape ?#{escapeCharacter()}")
List<User> findContainingEscaped(String namePart);
```

#Modifying Queries
All the previous sections describe how to declare queries to access a given entity or collection of entities. 
You can add custom modifying behavior by using the custom method facilities described 
in “Custom Implementations for Spring Data Repositories”. As this approach is feasible 
for comprehensive custom functionality, you can modify queries that only need parameter binding 
by annotating the query method with @Modifying, as shown in the following example:

**Example 72. Declaring manipulating queries**
```java
@Modifying
@Query("update User u set u.firstname = ?1 where u.lastname = ?2")
int setFixedFirstnameFor(String firstname, String lastname);
```

Doing so triggers the query annotated to the method as an updating query instead of a selecting one. 
As the EntityManager might contain outdated entities after the execution of the modifying query,
we do not automatically clear it (see the JavaDoc of EntityManager.clear() for details), 
since this effectively drops all non-flushed changes still pending in the EntityManager. 
If you wish the EntityManager to be cleared automatically, 
you can set the @Modifying annotation’s clearAutomatically attribute to true.

The @Modifying annotation is only relevant in combination with the @Query annotation. 
Derived query methods or custom methods do not require this annotation.

#Derived Delete Queries
Spring Data JPA also supports derived delete queries that let you avoid having 
to declare the JPQL query explicitly, as shown in the following example:

**Example 73. Using a derived delete query**
```java
interface UserRepository extends Repository<User, Long> {

  void deleteByRoleId(long roleId);

  @Modifying
  @Query("delete from User u where u.role.id = ?1")
  void deleteInBulkByRoleId(long roleId);
}
```

Although the deleteByRoleId(…) method looks like it basically produces the same result as the deleteInBulkByRoleId(…), 
there is an important difference between the two method declarations in terms of the way they are run. 
As the name suggests, the latter method issues a single JPQL query (the one defined in the annotation) 
against the database. This means even currently loaded instances of User do not see lifecycle callbacks invoked.

To make sure lifecycle queries are actually invoked, an invocation of deleteByRoleId(…) runs a query 
and then deletes the returned instances one by one, so that the persistence provider 
can actually invoke @PreRemove callbacks on those entities.

In fact, a derived delete query is a shortcut for running the query and 
then calling CrudRepository.delete(Iterable<User> users) on the result and keeping behavior 
in sync with the implementations of other delete(…) methods in CrudRepository.

#Applying Query Hints
To apply JPA query hints to the queries declared in your repository interface, you can use the @QueryHints annotation. 
It takes an array of JPA @QueryHint annotations plus a boolean flag to potentially disable the hints applied
to the additional count query triggered when applying pagination, as shown in the following example:

**Example 74. Using QueryHints with a repository method**
```java
public interface UserRepository extends Repository<User, Long> {

  @QueryHints(value = { @QueryHint(name = "name", value = "value")},
              forCounting = false)
  Page<User> findByLastname(String lastname, Pageable pageable);
}
```

The preceding declaration would apply the configured @QueryHint for that actually query but omit applying it 
to the count query triggered to calculate the total number of pages.

#Configuring Fetch- and LoadGraphs
The JPA 2.1 specification introduced support for specifying Fetch- and LoadGraphs that we also support
with the @EntityGraph annotation, which lets you reference a @NamedEntityGraph definition. 
You can use that annotation on an entity to configure the fetch plan of the resulting query. 
The type (Fetch or Load) of the fetching can be configured by using the type attribute on the @EntityGraph annotation. 
See the JPA 2.1 Spec 3.7.4 for further reference.

The following example shows how to define a named entity graph on an entity:

**Example 75. Defining a named entity graph on an entity.**

```java
@Entity
@NamedEntityGraph(name = "GroupInfo.detail",
  attributeNodes = @NamedAttributeNode("members"))
public class GroupInfo {

  // default fetch mode is lazy.
  @ManyToMany
  List<GroupMember> members = new ArrayList<GroupMember>();

  …
}
```

The following example shows how to reference a named entity graph on a repository query method:

**Example 76. Referencing a named entity graph definition on a repository query method.**
```java
public interface GroupRepository extends CrudRepository<GroupInfo, String> {

  @EntityGraph(value = "GroupInfo.detail", type = EntityGraphType.LOAD)
  GroupInfo getByGroupName(String name);

}
```

It is also possible to define ad hoc entity graphs by using @EntityGraph. 
The provided attributePaths are translated into the according EntityGraph 
without needing to explicitly add @NamedEntityGraph to your domain types, as shown in the following example:

**Example 77. Using AD-HOC entity graph definition on an repository query method.**
```java
public interface GroupRepository extends CrudRepository<GroupInfo, String> {

  @EntityGraph(attributePaths = { "members" })
  GroupInfo getByGroupName(String name);

}
```

#Projections
Spring Data query methods usually return one or multiple instances of the aggregate root managed by the repository. 
However, it might sometimes be desirable to create projections based on certain attributes of those types. 
Spring Data allows modeling dedicated return types, to more selectively retrieve partial views of the managed aggregates.

Imagine a repository and aggregate root type such as the following example:

**Example 78. A sample aggregate and repository**
```java
class Person {

  @Id UUID id;
  String firstname, lastname;
  Address address;

  static class Address {
    String zipCode, city, street;
  }
}

interface PersonRepository extends Repository<Person, UUID> {

  Collection<Person> findByLastname(String lastname);
}
```

Now imagine that we want to retrieve the person’s name attributes only. 
What means does Spring Data offer to achieve this? The rest of this chapter answers that question.

#Interface-based Projections
The easiest way to limit the result of the queries to only the name attributes is by declaring an interface 
that exposes accessor methods for the properties to be read, as shown in the following example:

**Example 79. A projection interface to retrieve a subset of attributes**
```java
interface NamesOnly {
  String getFirstname();
  String getLastname();
}
```

The important bit here is that the properties defined here exactly match properties in the aggregate root. 
Doing so lets a query method be added as follows:

**Example 80. A repository using an interface based projection with a query method**
```java
interface PersonRepository extends Repository<Person, UUID> {

  Collection<NamesOnly> findByLastname(String lastname);
}
```

The query execution engine creates proxy instances of that interface at runtime for each element returned
and forwards calls to the exposed methods to the target object.

Projections can be used recursively. If you want to include some of the Address information as well, 
create a projection interface for that and return that interface from the declaration of getAddress(), as shown in the following example:

**Example 81. A projection interface to retrieve a subset of attributes**
```java
interface PersonSummary {

  String getFirstname();
  String getLastname();
  AddressSummary getAddress();

  interface AddressSummary {
    String getCity();
  }
}
```

On method invocation, the address property of the target instance is obtained and wrapped into a projecting proxy in turn.

Closed Projections
A projection interface whose accessor methods all match properties of the target aggregate 
is considered to be a closed projection. The following example (which we used earlier in this chapter, too) 
is a closed projection:

**Example 82. A closed projection**
```java
interface NamesOnly {

  String getFirstname();
  String getLastname();
}
```

If you use a closed projection, Spring Data can optimize the query execution, 
because we know about all the attributes that are needed to back the projection proxy. 
For more details on that, see the module-specific part of the reference documentation.

#Open Projections
Accessor methods in projection interfaces can also be used to compute new values by using the @Value annotation,
as shown in the following example:

**Example 83. An Open Projection**
```java
interface NamesOnly {

  @Value("#{target.firstname + ' ' + target.lastname}")
  String getFullName();
  …
}
```

The aggregate root backing the projection is available in the target variable. 
A projection interface using @Value is an open projection. 
Spring Data cannot apply query execution optimizations in this case,
because the SpEL expression could use any attribute of the aggregate root.

The expressions used in @Value should not be too complex — you want to avoid programming in String variables. 
For very simple expressions, one option might be to resort to default methods (introduced in Java 8), 
as shown in the following example:

**Example 84. A projection interface using a default method for custom logic**
```java
interface NamesOnly {

  String getFirstname();
  String getLastname();

  default String getFullName() {
    return getFirstname().concat(" ").concat(getLastname());
  }
}
```
This approach requires you to be able to implement logic purely based on the other accessor methods 
exposed on the projection interface. A second, more flexible, option is to implement the custom logic 
in a Spring bean and then invoke that from the SpEL expression, as shown in the following example:

**Example 85. Sample Person object**
````java
@Component
class MyBean {

  String getFullName(Person person) {
    …
  }
}

interface NamesOnly {

  @Value("#{@myBean.getFullName(target)}")
  String getFullName();
  …
}
````

Notice how the SpEL expression refers to myBean and invokes the getFullName(…) method and 
forwards the projection target as a method parameter. Methods backed by SpEL expression evaluation 
can also use method parameters, which can then be referred to from the expression.
The method parameters are available through an Object array named args. 
The following example shows how to get a method parameter from the args array:

**Example 86. Sample Person object**
```java
interface NamesOnly {

  @Value("#{args[0] + ' ' + target.firstname + '!'}")
  String getSalutation(String prefix);
}
```

Again, for more complex expressions, you should use a Spring bean 
and let the expression invoke a method, as described earlier.

#Nullable Wrappers
Getters in projection interfaces can make use of nullable wrappers for improved null-safety. 
Currently supported wrapper types are:

* java.util.Optional

* com.google.common.base.Optional

* scala.Option

* io.vavr.control.Option

**Example 87. A projection interface using nullable wrappers**
```java
interface NamesOnly {
  Optional<String> getFirstname();
}
```

If the underlying projection value is not null, then values are returned 
using the present-representation of the wrapper type. In case the backing value is null, 
then the getter method returns the empty representation of the used wrapper type.

#Class-based Projections (DTOs)
Another way of defining projections is by using value type DTOs (Data Transfer Objects) 
that hold properties for the fields that are supposed to be retrieved. These DTO types can be used 
in exactly the same way projection interfaces are used, except that no proxying happens 
and no nested projections can be applied.

If the store optimizes the query execution by limiting the fields to be loaded, 
the fields to be loaded are determined from the parameter names of the constructor that is exposed.

The following example shows a projecting DTO:

**Example 88. A projecting DTO**
```java
class NamesOnly {

  private final String firstname, lastname;

  NamesOnly(String firstname, String lastname) {

    this.firstname = firstname;
    this.lastname = lastname;
  }

  String getFirstname() {
    return this.firstname;
  }

  String getLastname() {
    return this.lastname;
  }

  // equals(…) and hashCode() implementations
}
```

**Avoid boilerplate code for projection DTOs**
You can dramatically simplify the code for a DTO by using Project Lombok, which provides 
an @Value annotation (not to be confused with Spring’s @Value annotation shown in the earlier interface examples). 
If you use Project Lombok’s @Value annotation, the sample DTO shown earlier would become the following:
```java
@Value
class NamesOnly {
	String firstname, lastname;
}
```
Fields are private final by default, and the class exposes a constructor that takes all fields 
and automatically gets equals(…) and hashCode() methods implemented.

Class based projections do not work with native queries. As a workaround you may use named queries
with ResultSetMapping or the Hibernate specific ResultTransformer


#Dynamic Projections
So far, we have used the projection type as the return type or element type of a collection. However, 
you might want to select the type to be used at invocation time (which makes it dynamic). 
To apply dynamic projections, use a query method such as the one shown in the following example:

**Example 89. A repository using a dynamic projection parameter**
```java
interface PersonRepository extends Repository<Person, UUID> {

  <T> Collection<T> findByLastname(String lastname, Class<T> type);
}
```

This way, the method can be used to obtain the aggregates as is or with a projection applied,
as shown in the following example:

**Example 90. Using a repository with dynamic projections**

```java
void someMethod(PersonRepository people) {

  Collection<Person> aggregates =
    people.findByLastname("Matthews", Person.class);

  Collection<NamesOnly> aggregates =
    people.findByLastname("Matthews", NamesOnly.class);
}
```

# [5.1.4. Stored Procedures](https://docs.spring.io/spring-data/jpa/docs/2.5.4/reference/html/#jpa.stored-procedures)






