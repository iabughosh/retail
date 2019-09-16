# Retails project

This project was initiated to apply retails customer discounts on particular stores.

## Getting Started

These instructions will help you to run the project and use other tools like test coverage & static code analysis.

### Prerequisites

* [Java 8+](http://openjdk.java.net/install/) - Programming language.
* [Maven](https://maven.apache.org/) - Dependency Management & build tool.  


## Static code analysis tools

Common plugins in main projects are :

1- <b>Checkstyle</b> : it is static code analysis for your java code styles & indents, you can run it by navigate to your folder and execute the below maven target :

```
mvn checkstyle:checkstyle
```

2- <b>Find bugs</b> : static code analysis to determine common mistakes by developers like static encoding and using null references, it can be run using the below target :
To generate findbugs report use :

```
mvn findbugs:findbugs
```

to fail build if there is any bug in your code use (good for CI) :

```
mvn findbugs:check
```

you can check what bugs you have by the generated bug report at target/ path or visually using :

```
mvn findbugs:gui
```

### Running tests with test coverage
to run application with unit tests use the below command:

```
mvn clean test -Damount={double amount}
```
If system property <b>amount</b> is not provided then default value will be 600.
Coverage report will be generated at target/coverage-report
