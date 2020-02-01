# rest-proxy-engine

## Description
Example project of how to implement a proxy with spring cloud mvc

* [Stack](#stack)

## Stack
* kotlin
* spring-boot 2
* spock
*  redis
*  mongoDB

## Motivation
It's a sample project that I built just to practice, I hope people can use as reference for their 
own study on programming.

## Topics practices
###### Redis (Implemented cache with the help of spring framework)
###### Spring cloud mvc (package with classes to proxy HTTP requests)
###### Spring security
###### Spock for unit testing
###### MongoDb with SpringData
###### Spring context configuration
###### Handling REST services exception globally without try-catch blocks

## Features
* security (Layer of security for services without it)
* configurable (can add new services to be proxied through REST (CRUD) )
* Request and response mutation*

#### Request and response mutation:
The project is prepared to have a single execution flow that prepares a request send it to
an external service and modify the answer, in order to add a new mutation you only need to implement
an interface an add the desired functionality.
