# Booking-App

### Project description
This is a booking application made as part of training at IT-Academy.
Application deployed on Heroku and can be accessed via Swagger UI by [this link](https://booking-app-pk.herokuapp.com/swagger-ui/)

### Application features

1. There is roles for Admins, Moderators and Users based on Spring Security.
2. Users should be registered and logged in to use this application.
3. Users can rent a property (_Clients_) or host a property when renting it out (_Hosts_).
4. Clients can rent an apartment for a certain number of days, as well as cancel the order if the date of the lease has not come.
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
2. PostgreSQL credentials is publicly exposed. Need to secure it and redeploy app.
3. Passwords should be encoded with BCryptPasswordEncoder.

### TODO
1. Fix known issues.
2. Add tests.
3. Further improvement of application functionality.