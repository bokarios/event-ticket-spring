# Event Ticket Platform - Backend

This is the backend service for the Event Ticket Platform, built with Spring Boot.

## Features

- User registration and authentication
- Event creation and management
- Ticket purchasing and management
- Role-based access control (admin, organizer, user)
- RESTful API design

## Technologies

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security
- H2/PostgreSQL (configurable)
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Running Locally

```bash
git clone https://github.com/yourusername/event-ticket-backend.git
cd event-ticket-backend
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

### Configuration

Edit `src/main/resources/application.properties` to configure database and other settings.

## API Documentation

API documentation is available via Swagger UI at `http://localhost:8080/swagger-ui.html` (if enabled).

## Contributing

Contributions are welcome! Please open issues or submit pull requests.

## License

This project is licensed under the MIT License.
