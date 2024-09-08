# SmartGarageProject 🚗🔧

SmartGarage is a comprehensive web application designed for auto repair shop owners to efficiently manage their day-to-day operations. The system provides role-based access for **employees** and **customers** to manage vehicles, services, and visits, and generate detailed reports.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)
![MariaDB](https://img.shields.io/badge/MariaDB-10.5-blue)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-yellow)
![License](https://img.shields.io/badge/license-MIT-blue)

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [API Documentation](#api-documentation)
- [Endpoints](#endpoints)
- [Environment Variables](#environment-variables)
- [License](#license)

## Features 🚀

- **User Management**:
    - Create, update, and delete customers, employees, and administrators.
- **Vehicle Management**:
    - Manage vehicles linked to customers, including details like VIN, license plate, model, and year of creation.
- **Service Management**:
    - View and manage the list of services offered by the shop, including pricing.
- **Visit Tracking**:
    - Track all services performed on a customer's vehicle, including start and end dates, and generate detailed reports.
- **Currency Conversion**:
    - Generate visit reports in different currencies using real-time conversion rates (via OpenExchangeRates API).
- **Password Reset**:
    - Users can request a password reset link via email.

## Technologies 🛠️

- **Java 17**
- **Spring Boot**: Core framework for building REST APIs.
- **Spring Data JPA**: ORM for database operations.
- **Spring Security**: Authentication and authorization.
- **OpenAPI/Swagger**: API documentation.
- **MariaDB / H2 Database**: Database support.
- **OpenExchangeRates API**: Currency conversion.
- **Lombok**: Reduces boilerplate code.

## Installation 🛠️

### 1. Clone the repository:

```bash
git clone https://github.com/yourusernameIl/smartgarage.git
cd smartgarage
```

### 2. Set up the database:

Modify the `application.properties` file to configure your database (MariaDB or H2).

### 3. Build the project:

./gradlew build

### 4. Run the project:

./gradlew bootRun

### 5. Access the application:

- Frontend: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html


## API Documentation 📄

This project uses Swagger for API documentation. You can access the API documentation via Swagger UI at:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Endpoints 🔗

Some of the key endpoints are:

### User Management:
- `POST /api/users/create`: Create a new user.
- `PUT /api/users/{id}`: Update user details.
- `DELETE /api/users/{id}`: Delete a user.

### Vehicle Management:
- `POST /api/vehicles`: Create a new vehicle.
- `GET /api/vehicles/{id}`: Get vehicle details.
- `PUT /api/vehicles/{id}`: Update vehicle information.

### Visit Management:
- `POST /api/visits`: Create a new visit.
- `GET /api/visits/{id}/report`: Generate a visit report.

### Currency Conversion:
- Visit reports can be generated in different currencies by passing the currency parameter when generating the report.

