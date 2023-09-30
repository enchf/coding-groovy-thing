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
