# Microservice-Architecture

This source was developed for Java based microservice tutorial series from java brains.

# Microservice :

* It is an architecture based on which large applications are built as a collection of small functional modules.
* These functional modules are independently deployable, scalable, target specific business goals, and communicate with each other over standard protocols.
* Modules can also be implemented using different programming languages, have their databases, and deployed on different software environments.

# Microservices Inside This Project :

Here this project consist of mainly 3 microservices and those are,

* Movie Catalog Service(movie-catelog-service) : This service gives the list of the movies which were watched by the user along with their ratings.
* Movie Info Service(movie-info-service) : This service gives the detail information of the movie.
* Ratings Service(ratings-data-service) : This service provides the rating information of the movie.

# Technology Stack :
 
* Java 8
* Spring Boot
* Netflix Eureka Service Registry
* Netflix Eureka Service Client
* Spring Cloud API Gateway
* Spring Cloud Config Server
* Zipkin
* Spring Cloud Sleuth
* Rest Template
