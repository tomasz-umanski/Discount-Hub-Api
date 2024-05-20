# Discount Hub API

## Overview

This Java Spring Boot-based web API is designed to facilitate the management of discounts and deals for various products. It provides a robust platform for users to create, manage, and view discount offers efficiently.
## Table of Contents

1. [Features](#features)
2. [Technology Stack](#technology-stack)
3. [Design patterns](#design-patterns)
4. [Installation](#installation)
5. [Database](#database)
6. [Postman Collection](#postman-collection)
7. [API Documentation](#api-documentation)
8. [Contributing](#contributing)
9. [License](#license)


## Features

1. **User Management**
    - Register new users with email, username, and password.
    - Patch user details, ensuring only non-blank values are updated.
    - Remove users from the system.
    - Secure login and authentication mechanisms.
2. **Post Management**
    - Users can create posts detailing discount offers, including prices, description, and related URLs.
    - Modify existing posts with only valid and provided values.
    - Remove posts that are no longer relevant.
    - Retrieve detailed information about discount offers.
3. **Category Management**
    - Add new categories for organizing discount posts.
    - Patch category details selectively based on provided values.
    - Remove categories that are obsolete.
    - List all available categories.
4. **Exception Handling**
    - Handle specific scenarios such as user not found, category not found, and data integrity violations.
    - Provide user-friendly error messages for validation failures and other exceptions.

## Technology Stack

- **Java** The primary programming language.
- **Spring Boot** Framework for building the API.
- **Hibernate** ORM for database interactions.
- **Jakarta Validation** For validating incoming request data.
- **Lombok** To reduce boilerplate code.
- **JUnit** For unit and integration testing.
- **H2 Database** In-memory database for development and testing purposes.
- **Docker** For app deployment.

## Design Patterns

1. **Facade**
    - Controller classes (CategoryController, PostController, UserController) act as facades, simplifying the interface to more complex subsystems (services and repositories).
2. **Service**
    - Service classes (CategoryService, PostService, UserService) separate business logic from the data access layer and the user interface.
3. ***Repository***
    - Repository classes (CategoryRepository, PostRepository, UserRepository, RoleRepository) provide abstraction over data access and database operations.
4. ***Mapper***
   - Mapping classes (CategoryMapper, PostMapper, UserMapper) transform domain objects to DTOs and vice versa, supporting the separation of business logic from presentation logic.
5. ***Singleton***
   - Used in the implementation of services and repositories through dependency injection in the context of the Spring Framework, ensuring a single instance of a service or repository throughout the application.
6. ***Data Transfer Object (DTO)***
   - DTO classes (e.g., CategoryDto, CreateCategoryDto, PatchCategoryDto, PostDto, UserDto) are used to transfer data between application layers, minimizing the amount of transferred data and enhancing security.
7. ***Builder***
   - Builder classes are used for constructing complex objects. They provide a flexible solution to instantiate objects by separating the construction process from the representation. This pattern is used in DTO classes.

## Installation
The project is dockerized for easy setup and deployment. Follow these steps to get the project up and running:
1. **Clone the Repository**
    - `git@github.com:tomasz-umanski/Discount-Hub-Api.git`
2. **Navigate to the Project Directory**
3. **Docker Setup:**
    - Ensure Docker and Docker Compose are installed on your system. In the project directory in the docker folder, you'll find Docker configuration files.
4. **Build and start Docker Containers:**
    - `docker-compose build`
    - `docker-compose up -d`
5. **Access the Application:**
    - After the containers are up and running, you can access the application by default on port `8818`.

## Database
1. The application uses H2 in-memory database for development and testing purposes.
2. Access the H2 database console at `http://localhost:8818/h2-console` with the default credentials provided in application.yml.

## Postman Collection
- A Postman collection is provided to facilitate testing and exploring the API endpoints. 
- Import the collection file `discounthub.postman_collection.json` into Postman.
- Use the predefined requests to interact with the API endpoints easily.

## Api Documentation
1. The API documentation is available via Swagger UI once the application is running.
   - `http://localhost:8818/swagger-ui/index.html`

## Contributing
If you're interested in contributing to the project, you can:
- Fork the repository on GitHub.
- Make your desired changes or additions.
- Submit a pull request with your changes for review by the project maintainers.
- Participate in discussions and collaborate with other contributors to improve the project.

## License

This project is licensed under the MIT License