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
