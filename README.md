# Users service

Part of [Micro-backend-app](https://github.com/PetreVane/Micro-backend-app)

### Responsabilities
    * Handles user account creation / deletion
    * Deals with JWT Token creation / validation
    * Handles calls to File Uploader service endpoints for uploading / deleting files 
    * Provides fallback logic when File Uploader service is unavailable


### Dependencies

    * spring-cloud-starter-openfeign
    * spring-cloud-starter-netflix-hystrix
    * spring-cloud-starter-sleuth
    * logstash-logback-encoder
    * spring-boot-starter-validation
    * spring-boot-starter-web
    * spring-cloud-starter-netflix-eureka-client
    * spring-boot-devtools
    * lombok
    * h2 database
    * spring-boot-starter-actuator
    * spring-boot-starter-data-jpa
    * mysql-connector-java
    * modelmapper
    * spring-boot-starter-security
    * io.jsonwebtoken
    * spring-cloud-starter-config
    * spring-cloud-starter-bootstrap
    * spring-cloud-starter-bus-amqp
    * spring-cloud-starter-loadbalancer
