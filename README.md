#  Backend - Online Meeting Hall Booking System

This is the backend service built using "Java + Spring Boot" to handle hall bookings, user management, and admin operations.

## Features

- RESTful APIs for CRUD operations
- Booking conflict prevention
- User login & registration logic
- Admin functionality
- MySQL database integration

---

## Tech Stack

- Java 
- Spring Boot
- MySQL
- Maven
- Postman (for testing)

---
🔗 API Base URL: `http://localhost:8080`

---

## Example Endpoints

| Method | Endpoint              | Description             |
|--------|------------------------|-------------------------|
| GET    | /api/halls             | Get available halls     |
| POST   | /api/bookings          | Book a hall             |
| POST   | /api/auth/login        | Login user              |
| GET    | /api/bookings/user/1   | Get bookings for user 1 |

---
