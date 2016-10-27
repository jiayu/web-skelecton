# Web-Skeleton

A skeleton project for WebSerivce based on [Spring Boot](https://github.com/spring-projects/spring-boot)

##Goals

* Standalone server
* Restful API with swagger to show API spec
* Interceptor based authorization and performance calculation
* Integration with database migration tool
* JPA ready
* Exception handling
* Monitoring features

## Standalone server
In this project, we use Spring Boot to power the application server.
There are a couple advantages in this approach, some of them are below:
* a mature framework with all Spring features
* easy to develop/debug/deploy
* quick start

## Restful API
A example of restful API is given in [UserController](src/main/java/me/jamc/skeleton/controller/UserController.java).
I don't feel like there is much to say about how easy Spring helps to get all the
parameters in URI.

However, I would love to mention that, with the help of [Swagger](http://swagger.io/),
your documentation writing time is saved.

## Interceptors
Yes, for every WebService server, authorization is a must. I have done a lot of this in
my day to day projects. I think it is a good idea to hide it somewhere instead of doing
in every logical endpoint.

Interceptor in Spring in a savior. Thanks to AOP, interceptor performs its logic
before and after each http request without touching any of the code in controller classes.
Take a look at [ValidationInterceptor](src/main/java/me/jamc/skeleton/interceptor/ValidationInterceptor.java)
and [ExecutionInterceptor](src/main/java/me/jamc/skeleton/interceptor/ExecutionInterceptor.java) and
feel how they work.You can also add your customized interceptor and register it in MvcConfiguration class.

## Database migration tool
A good practise of managing database schema is to manage it in your code. This help in
several ways including:
* deployment to different environments
* writing integration tests

Spring Boot supports [Liquidbase](http://www.liquibase.org/) and [Flyway](https://flywaydb.org/) by default.
The reason I choose liquidbase is because the flexibility of managing database schema. I use [YAML](http://yaml.org/)
for schema file as it is database agnostic, allowing me the use the same schema file in production and
integration test. Even better, if for same reasons, I want to switch MySQL to ProgresSQL
or vice versa, I don't need to update any of these schema files.

## Spring JPA
Just a easy way to handle database operation. Pretty common requirements in web server, I believe.

## Exception handling
Exception handling is another common requirement in not just web server but all kinds of
applications. Spring Boot(MVC) takes care of most of the heavy lifting already. Meaning that,
it deals with most of the exception it generally you can meet with its framework. But I want to
add some specific to my application, eg: validation exception. I added a [GlobalExceptionHandler](src/main/java/me/jamc/skeleton/handler/GlobalExceptionHandler.java).
It saves those exceptions that I didn't think of which I can later revisit.
Furthermore, it catches validation exception and returns a proper http status code in response.

## Monitoring
Already enabled [Spring Actuator](http://docs.spring.io/spring-boot/docs/1.4.1.RELEASE/reference/htmlsingle/#production-ready).
Need to read more in the doc to find out how it works.
