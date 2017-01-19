# demo-elk-log

```
$ ./mvnw spring-boot:run
2017-01-20 01:47:45.109 DEBUG 7021 --- [           main] com.example.DemoElkLogApplication        : Sample Debug Message


$ ./mvnw spring-boot:run -Dspring.profiles.active=staging
2017-01-20 01:47:45.109 DEBUG 7021 --- [           main] com.example.DemoElkLogApplication        : Sample Debug Message
2017-01-20 01:47:45.110 TRACE 7021 --- [           main] com.example.DemoElkLogApplication        : Sample Trace Message
```

## See

* [Spring Boot Logging](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-logging.html)
* [Logback JSON encoder](https://github.com/logstash/logstash-logback-encoder)
* Sample [logback-spring.xml](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-logback/src/main/resources/logback-spring.xml)
* Under the hood [base.xml](https://github.com/spring-projects/spring-boot/blob/master/spring-boot/src/main/resources/org/springframework/boot/logging/logback/base.xml)
