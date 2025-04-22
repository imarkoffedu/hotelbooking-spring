# hotel-booking-api

## Description
This is a simple hotel booking REST API built with Spring Boot. 
It allows users to manage bookings, including creating, updating, deleting, and retrieving booking listings. 
The API is designed to be easy to use and provides a simple documentation for managing property data.

Also, I tried my best to make the code as clean and readable as possible, following best practices and design patterns.

## Technologies Used
- Spring Boot
- Spring Data JPA
- H2 Database (in-memory)
- OpenAPI (Swagger)
- Logging

## Endpoints

To view the API documentation, visit http://localhost:8080/swagger-ui.html after starting the application.

| Endpoint                  | Method   | Description              |
|---------------------------|----------|--------------------------|
| `/users`                  | `GET`    | Get all users            |
| `/users/{id}`             | `GET`    | Get a user by ID         |
| `/users`                  | `POST`   | Create a new user        |
| `/bookings`               | `GET`    | Get all bookings         |
| `/bookings/{id}`          | `GET`    | Get a booking by ID      |
| `/bookings`               | `POST`   | Create a new booking     |
| `/bookings/{id}`          | `PUT`    | Update a booking by ID   |
| `/bookings/{id}`          | `DELETE` | Delete a booking by ID   |
| `/bookings/user/{userId}` | `GET`    | Get bookings by user ID  |

## Project Structure

| Directory       | Description                                                       |
|-----------------|-------------------------------------------------------------------|
| `configuration` | Contains configuration classes for the application (e.g. OpenAPI) |
| `controller`    | Contains REST controllers for handling API requests               |
| `dto`           | Contains Data Transfer Objects (DTOs) for request/response models |
| `exception`     | Contains custom exceptions                                        |
| `model`         | Contains entity classes representing the database tables          |
| `repository`    | Contains Spring Data JPA repositories for database access         |
| `service`       | Contains service classes for business logic                       |
| `type`          | Contains enums and typealiases used in the application            |
| `util`          | Contains utility classes                                          |