# Zen Booking
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Zen-Booking&metric=ncloc)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Zen-Booking)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Zen-Booking&metric=alert_status)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Zen-Booking)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Zen-Booking&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Zen-Booking)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Zen-Booking&metric=security_rating)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Zen-Booking)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Zen-Booking&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Zen-Booking)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Zen-Booking&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Zen-Booking)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Zen-Booking&metric=code_smells)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Zen-Booking)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Zen-Booking&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Zen-Booking)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Zen-Booking&metric=bugs)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Zen-Booking)

### Project description:
This is a booking application made as part of training at IT-Academy.
Application is deployed on Heroku and can be accessed via Swagger UI by [this link](https://zen-booking.herokuapp.com/swagger-ui/)

### Application features:

1. There is roles for Admins, Moderators and Users based on Spring Security.
2. Users should be registered and logged in to use this application.
3. Users can rent a property (_Clients_) or host a property when renting it out (_Hosts_).
4. Clients can rent an apartment for a certain number of days, as well as cancel the order if the date of the rental period has not come.
5. Hosts can add their property and earn money by renting it out.
6. Hosts should accept order for renting it out and can decline order if they need.
7. Hosts can remove their property from listing without deleting it.
8. Moderators ensure order in the application and can ban users and delete property or images if they violate the rules of the service.
9. Moderator roles can be created only by Administrator.
10. Application has schedulers to accept and finish orders automatically:
    - order is accepted automatically if it was not accepted, declined or cancelled within ~5 minutes after it was placed;
    - ended orders are finished automatically once a day or upon restart.

_NOTE: Functionality for admins and moderators will be implemented on the later stage._

### Technology stack:
- Java 11
- Spring Boot
- Spring Data
- Spring Caches
- Spring AOP
- Spring Security with JWT Token
- PostgreSQL
- Swagger
- Unit testing (JUnit 5, AssertJ, Mockito)
- Git (_obviously_)
- Sonar Cloud _via GitHub Actions_

### Known issues:
1. JWT exception can't be handled to send informative message to front side.

### TODO:
1. Fix known issues.
2. Add more tests.
3. Further improvement of Swagger UI documentation.
4. Further improvement of application functionality.