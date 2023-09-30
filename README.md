# Coding is a Groovy thing!

This is a repository intended to show how Groovy is Groovy programming language.
It is granulated by commits. Each commit shows one or more language features.
The use case is an Integration Tests framework, developed on top of the popular Spock framework.

## Commit History

This is the commit history. There is no direct correlation between the following sections and the commits
but the order itself. Checkout the commit to test and run what is mentioned in each section.

### 1 - Project setup

Project is setup as an executable JAR able to run integration tests with some argument specification.
It has the following pre-requisites:

* Groovy language. As a suggestion use sdkman: `sdk man install groovy 3.0.6`.
* Maven, as this project is structured using it.
* OCI CLI. As an example, we are doing integration tests against some features of the Oracle Cloud Infrastructure.
* Have an account in OCI. This needs a payment method that is charged with USD$1 and then revoked.
* Install your OCI user profile in your `~/.oci/config` file with its corresponding PEM public/private keys.

This framework will test some functionality of the [Oracle Cloud Infrastructure](https://cloud.oracle.com/?region=mx-queretaro-1).

#### 1.1 Setup project in IntelliJ

Project is set up using IntelliJ new project wizard. As it is already created, we can import it as a Maven project.

#### 1.2 Setup Maven dependencies

Maven pom.xml file is automatically created by IntelliJ. Groovy, Spock and other required dependencies are added,
along with configuration to produce the executable JAR. Execute `mvn clean package` to have the jar available at
`target` and then execute `java -jar target/<jar-name>.jar` to run the project.

### 2 - Fetch Spock specifications

In this second commit, Spock features can be seen at a glance:

* Logical blocks, having more advantage than frameworks like Cucumber.
* Take advantage of string based method names to create meaningful test names.
* Dynamically execution of specifications using EmbeddedSpecRunner.

Also, we can notice the use of a third-party library that provides some efficient Reflections utilities.

Regarding Groovy syntax features, we can appreciate the following:

* Semicolon is optional and discouraged.
* Dynamically typed variables.
* Closures in action, syntax using brackets and have an implicit `it` variable for single parameters closures.

### 3 - A real test is added

A real test listing OCI compartments is added. Spock features can be seen in action, with the blocks defined showing
assignations, executions and assertions.

The most relevant change here is the introduction of a trait, working as a helper for the Spock specifications.
The trait injects the authentication provider used in any OCI API client as an instance variable, available in all
test specifications implementing the OCI trait.

### 4 - Introduce Annotation based transformers

A wrapper for the Identity OCI client class is introduced to perform all API requests. It uses the following 
annotations to transform the class:

* @Slf4j - Automatically injects a log to the class, avoiding all the Logger boilerplate.
* @Singleton - Enables a variable called `instance` as the unique instance of the class.
* @Lazy - Assigns the value of an instance variable on-demand using a closure trick for compilation.
* @Delegate - Used in two forms: a full delegation and a selective one for a single method.

This wrapper class also shows the use of these other Groovy features:

* Initialization of lists as in scripting languages, using simply `[]`.
* String interpolation with the `${}` operator.
* Tap functional method, which invokes a method on an object and returns the object itself.

To continue with the mixin approach in the test helpers, OCI trait provides the single instance of the client to the
specifications implementing it, and also provides a default override of the cleanup method, which is the Spock version 
of the JUnit tearDown method, invoked after each method of each specification.

We can appreciate how the tests code are more focused in what they should test, using delegated methods and injected
instance variables appropriately. Also, another Spock feature is shown in the CreateCompartment test: the use of
`where:` blocks for data driven testing.

### 5 - Using associative arrays

Associative arrays are Java Maps used as JS/Ruby objects. We can use the keys directly as properties. We can see how
we can inject fully functionality into a class as a composition using @Delegate or traits as mixins. Release trait
encapsulates all release functionality and object handling.
