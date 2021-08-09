# Booking-App
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Booking-App&metric=ncloc)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Booking-App)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Booking-App&metric=alert_status)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Booking-App)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Booking-App&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Booking-App)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Booking-App&metric=security_rating)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Booking-App)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Booking-App&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Booking-App)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Booking-App&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Booking-App)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Booking-App&metric=code_smells)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Booking-App)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Booking-App&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Booking-App)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Pavel-Kuropatin_Booking-App&metric=bugs)](https://sonarcloud.io/dashboard?id=Pavel-Kuropatin_Booking-App)

### Project description
This is a booking application made as part of training at IT-Academy.
Application deployed on Heroku and can be accessed via Swagger UI by [this link](https://booking-app-pk.herokuapp.com/swagger-ui/)

### Application features

1. There is roles for Admins, Moderators and Users based on Spring Security.
2. Users should be registered and logged in to use this application.
3. Users can rent a property (_Clients_) or host a property when renting it out (_Hosts_).
4. Clients can rent an apartment for a certain number of days, as well as cancel the order if the date of the rental period has not come.
5. Hosts can add their property and earn money by renting it out.
6. Hosts should accept order for renting it out and can decline order if they need.
7. Hosts can remove their property from listing without deleting it.
8. Moderators ensure order in the application and can ban users and delete property or images if they violate the rules of the service.
9. Moderator roles can be created only by Administrator.

### Technology stack
- Java
- Spring Boot
- Spring Data
- Spring Caches
- Spring AOP
- Spring Security (_basics_)
- PostgreSQL
- Swagger
- Git (_obviously_)
- Sonar Cloud _via GitHub Actions_

### Known issues
1. JWT exception can't be handled to send informative message to front side.

### TODO
1. Fix known issues.
2. Further improvement of Swagger UI documentation.
3. Add tests.
4. Further improvement of application functionality.