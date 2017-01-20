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

* [http://localhost:8080/info/one](http://localhost:8080/info/one)
* [http://localhost:8080/debug/two](http://localhost:8080/debug/two)
* [http://localhost:8080/trace/three](http://localhost:8080/trace/three)

### How-To: Spring Boot

Simply add `{projectDir}/src/main/resources/logback-spring.xml` and configure in `{projectDir}/src/main/resources/application.yml`.

### How-To: Spring Web MVC

Configure [Spring Logback extension]((https://github.com/qos-ch/logback-extensions/wiki/Spring#spring-configuration)) as follows:

#### Dependencies

##### For Maven `pom.xml`

```xml
<!-- 1. exclude commons-logging -->
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-core</artifactId>
	<version>${spring.version}</version>
	<exclusions>
	  <exclusion>
		<groupId>commons-logging</groupId>
		<artifactId>commons-logging</artifactId>
	  </exclusion>
	</exclusions>
</dependency>

<!-- 2. bridge logging from JUL and JCL to SLF4j -->
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>jul-to-slf4j</artifactId>
</dependency>
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>jcl-over-slf4j</artifactId>
</dependency>

<!-- 3. logback -->
<dependency>
	<groupId>ch.qos.logback</groupId>
	<artifactId>logback-classic</artifactId>
	<version>${logback.version}</version>
</dependency>
<dependency>
    <groupId>org.logback-extensions</groupId>
    <artifactId>logback-ext-spring</artifactId>
    <version>${logback.ext.spring.version}</version>
</dependency>
```

*Note: If you are using Spring Java configuration, do not set `<scope>runtime</scope>` in Maven for Logback dependencies.*

##### For Gradle `build.gradle`

```groovy
// 1. exclude commons-logging
configurations.all {
   exclude group: "commons-logging", module: "commons-logging"
}

dependencies {
 	//2. bridge logging from JUL and JCL to SLF4j
 	compile 'org.slf4j:jul-to-slf4j'
 	compile 'org.slf4j:jcl-over-slf4j'

	//3. Logback
	compile 'ch.qos.logback:logback-classic:${logback.version}'
	compile 'org.logback-extensions:logback-ext-spring:${logback.ext.spring.version}'

}
```

#### ServletContext

##### Set context listener in `web.xml`
```xml
<listener>
    <listener-class>ch.qos.logback.ext.spring.web.LogbackConfigListener</listener-class>
</listener>
```
Or, use `WebApplicationInitializer`
```java
public class MyWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
      // prepare rootContext
      // ...

      // Manage the lifecycle of the root application context
      container.addListener(new LogbackConfigListener(rootContext));

      // ...
    }

 }
```

##### Create `logback.xml`

Define a `{projectDir}/src/main/resources/logback.xml` file defining one or more `DelegatingLogbackAppender` elements. For example:

```xml
<configuration>
    <appender name="consoleAppender" class="ch.qos.logback.ext.spring.DelegatingLogbackAppender"/>

    <root level="INFO">
        <appender-ref ref="consoleAppender"/>
    </root>
</configuration>
```

##### Java config

```java
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.ext.spring.ApplicationContextHolder;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogbackConfig {

    @Bean 
    public static ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder ();
    }

    @Bean 
    public static LoggerContext loggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    @Bean (initMethod = "start", destroyMethod = "stop")
    public static PatternLayoutEncoder encoder (LoggerContext ctx) {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(ctx);
        encoder.setPattern("%date %-5level [%thread] %logger{36} %m%n");
        return encoder;
    }

    @Bean (initMethod = "start", destroyMethod = "stop")
    public static ConsoleAppender consoleAppender (LoggerContext ctx, PatternLayoutEncoder encoder) {
        ConsoleAppender appender = new ConsoleAppender();
        appender.setContext(ctx);
        appender.setEncoder(encoder);
        return appender;
    }
}
```

*Note: that the method `consoleAppender` has the same name as the appender defined in `logback.xml`.*

In this method you may use `@Profile` to create Spring profile specific beans.
 

## References

* [Spring Boot Logging](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-logging.html)
* [Logback JSON encoder](https://github.com/logstash/logstash-logback-encoder)
* Sample [logback-spring.xml](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-logback/src/main/resources/logback-spring.xml)
* Under the hood [base.xml](https://github.com/spring-projects/spring-boot/blob/master/spring-boot/src/main/resources/org/springframework/boot/logging/logback/base.xml)
* [Logback Spring extension](https://github.com/qos-ch/logback-extensions/wiki/Spring)
* [Resources as dependencies](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/resources.html#resources-as-dependencies)
* [How to log in Spring with SLF4J and Logback](http://www.codingpedia.org/ama/how-to-log-in-spring-with-slf4j-and-logback/)
* [Spring MVC + Logback SLF4j example](https://www.mkyong.com/spring-mvc/spring-mvc-logback-slf4j-example/)
* [How to inject active spring profile into logback](http://stackoverflow.com/questions/39546422/how-to-inject-active-spring-profile-into-logback)
* [logback.xml is evaluated before application.yml is](http://stackoverflow.com/questions/29669049/logback-xml-is-evaluated-before-application-yml-is)
* [Using YAML in Spring Boot to configure Logback](https://springframework.guru/using-yaml-in-spring-boot-to-configure-logback/)
* [logback-spring-profile-experiment](https://github.com/kurron/logback-spring-profile-experiment)

