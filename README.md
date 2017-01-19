# demo-elk-log

```
$ ./mvnw spring-boot:run
2017-01-20 01:47:45.109 DEBUG 7021 --- [           main] com.example.DemoElkLogApplication        : Sample Debug Message


$ ./mvnw spring-boot:run -Dspring.profiles.active=staging
2017-01-20 01:47:45.109 DEBUG 7021 --- [           main] com.example.DemoElkLogApplication        : Sample Debug Message
2017-01-20 01:47:45.110 TRACE 7021 --- [           main] com.example.DemoElkLogApplication        : Sample Trace Message
```
