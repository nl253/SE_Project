# Software Engineering Project 

## Structure

```
├── doc
│   └── meetings 
         ...
├── LICENSE.txt
├── META-INF
│   ├── MANIFEST.MF
│   └── persistance.xml
├── NOTICE.txt
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── uk
│   │   │       └── ac
│   │   │           └── kent
│   │   │               ├── controllers
│   │   │               │   ├── BaseController.java
│   │   │               │   ├── LoginController.java
│   │   │               │   ├── MainController.java
│   │   │               │   └── package-info.java
│   │   │               ├── Database.java
│   │   │               ├── Main.java
│   │   │               ├── models
│   │   │               │   ├── people
│   │   │               │   │   ├── Department.java
│   │   │               │   │   ├── Director.java
│   │   │               │   │   ├── Employee.java
│   │   │               │   │   ├── Manager.java
│   │   │               │   │   └── package-info.java 
│   │   │               │   └── records
│   │   │               │       ├── Address.java
│   │   │               │       ├── AnnualReviewRecord.java
│   │   │               │       ├── BaseRecord.java
│   │   │               │       ├── EmploymentDetailsRecord.java
│   │   │               │       ├── package-info.java
│   │   │               │       ├── PersonalDetailsRecord.java
│   │   │               │       ├── Position.java
│   │   │               │       ├── ProbationRecord.java
│   │   │               │       ├── PromotionRecord.java
│   │   │               │       ├── recommendations
│   │   │               │       │   ├── package-info.java
│   │   │               │       │   ├── ProbationRecommendation.java
│   │   │               │       │   ├── PromotionRecommendation.java
│   │   │               │       │   ├── Recommendation.java
│   │   │               │       │   ├── RemainRecommendation.java
│   │   │               │       │   ├── SalaryIncreaseRecommendation.java
│   │   │               │       │   └── TerminationRecommendation.java
│   │   │               │       ├── Relative.java
│   │   │               │       ├── SalaryIncreaseRecord.java
│   │   │               │       ├── TerminationReason.java
│   │   │               │       └── TerminationRecord.java 
│   │   │               └── package-info.java
│   │   └── resources
│   │       └── views
│   │           ├── login.fxml
│   │           └── main.fxml
│   └── test
         ...
└──── uml 
       └── use-cases
              ...
```

## Meetings 

See [the meetings directory](doc/meetings/).

## Tests

```bash
mvn test
```

## Building

### Jar

```bash
mvn package
```

The jar will appear in the `./target` directory.

### Compile to class files

```bash
mvn compile
```

The classes will appear in the `./target/classes` directory.

## Libraries and Dependencies

- [Hibernate ORM](http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html) for database stuff 
- [JavaFX](https://docs.oracle.com/javase/8/javafx/get-started-tutorial/get_start_apps.htm#JFXST804) for GUI
- build tool [Maven](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)

-----------------------------------------

[1] https://maven.apache.org/guides/introduction/introduction-to-the-pom.html <br>
[2] https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html
