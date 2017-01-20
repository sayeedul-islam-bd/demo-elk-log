# demo-elk-log

## Run
```
$ ./mvnw spring-boot:run
2017-01-20 01:47:45.109 DEBUG 7021 --- [           main] com.example.DemoElkLogApplication        : Sample Debug Message


$ ./mvnw spring-boot:run -Dspring.profiles.active=elk,dev -Delk.host=elk
2017-01-20 01:47:45.109 DEBUG 7021 --- [           main] com.example.DemoElkLogApplication        : Sample Debug Message
2017-01-20 01:47:45.110 TRACE 7021 --- [           main] com.example.DemoElkLogApplication        : Sample Trace Message
```
## Browse

* http://localhost:8080/info/one
* http://localhost:8080/debug/two
* http://localhost:8080/trace/three

## References

### Spring Boot

* [Spring Boot Logging](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-logging.html)
* [Logback JSON encoder](https://github.com/logstash/logstash-logback-encoder)
* Sample [logback-spring.xml](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-logback/src/main/resources/logback-spring.xml)
* Under the hood [base.xml](https://github.com/spring-projects/spring-boot/blob/master/spring-boot/src/main/resources/org/springframework/boot/logging/logback/base.xml)

### Barefoot Spring (without the boots)

* [How to log in Spring with SLF4J and Logback](http://www.codingpedia.org/ama/how-to-log-in-spring-with-slf4j-and-logback/)
* [Spring MVC + Logback SLF4j example](https://www.mkyong.com/spring-mvc/spring-mvc-logback-slf4j-example/)
* [How to inject active spring profile into logback](http://stackoverflow.com/questions/39546422/how-to-inject-active-spring-profile-into-logback)
* [logback.xml is evaluated before application.yml is](http://stackoverflow.com/questions/29669049/logback-xml-is-evaluated-before-application-yml-is)
* [Using YAML in Spring Boot to configure Logback](https://springframework.guru/using-yaml-in-spring-boot-to-configure-logback/)
* [logback-spring-profile-experiment](https://github.com/kurron/logback-spring-profile-experiment)
