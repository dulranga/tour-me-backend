# Tour Me - Bidding Platform for Sri Lanka Travel

## Overview

Tour Me is a web-based bidding platform designed to help travelers find the best deals for accommodations and services in Sri Lanka. Built with Spring Boot, it features:

- JWT-based authentication
- RESTful API for bid submissions
- Database persistence (PostgreSQL/H2)
- Spring Security integration

## How to Run

1. **Prerequisites**
   - Java 25
   - PostgreSQL (or H2 for development)
   - Maven 3.8+

2. **Startup**

   ```bash
   # From project root
   ./mvnw spring-boot:run
   ```

   - Or use `mvnw` directly if no wrapper exists

3. **Environment Variables**
   - Copy `.env` to `.env.local` and set required variables
   - Key variables include `DB_USERNAME`, `DB_PASSWORD`, and `JWT_SECRET`

## Project Structure

```
src/
├── main/
│   ├── java/            # Core application code
│   │   ├── com.tourme/  # Main package
│   │   │   ├── dto/      # Data transfer objects
│   │   │   ├── config/   # Spring configurations
│   │   │   ├── repositories/  # Database interfaces
│   │   │   ├── models/   # Entity classes
│   │   │   ├── controllers/  # API endpoints
│   │   │   ├── services/  # Business logic
│   │   │   └── exceptions/  # Error handling
│   ├── resources/       # Configuration files
└── test/                # Unit/integration tests
```

## Making Changes

- **Code Conventions**: Follow Java 25 syntax and Spring Boot best practices
- **Testing**: Run `mvn test` to execute unit tests
- **Documentation**: Update API documentation via Swagger UI (accessed at `/swagger-ui.html`)
- **Version Control**: Commit changes to `main` branch with meaningful messages

## Key Dependencies

- Spring Boot 4.0.3
- Spring Security 5.7
- JWT (jjwt-0.13.0)
- PostgreSQL driver
- SpringDoc OpenAPI (for API docs)

## Contribution Guidelines

1. Create a feature branch from `main`
2. Add documentation changes to `API_DOCUMENTATION.md`
3. Run tests before merging
4. Follow security best practices (no hardcoded secrets)
