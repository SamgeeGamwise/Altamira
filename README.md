# Altamira Banking API

Altamira is a RESTful banking API built with Java 21 and Spring Boot. It is designed as a backend portfolio project for practicing API design, JPA relationships, service-layer business logic, validation, and PostgreSQL persistence.

## Tech Stack

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Jakarta Bean Validation
- PostgreSQL
- Maven
- Spring Boot Actuator

## Features

- Customer creation and retrieval
- Customer status tracking
- Account creation and retrieval
- Account status tracking
- Deposits
- Withdrawals
- Transfers
- Account transaction history
- Centralized API response format
- Centralized exception handling
- Request validation
- PostgreSQL persistence
- JPA entity relationships

## API Overview

### Customers

```text
POST   /api/customers
GET    /api/customers/{customerId}
DELETE /api/customers/{customerId}
```

### Accounts

```text
POST   /api/accounts
GET    /api/accounts/{accountId}
DELETE /api/accounts/{accountId}
GET    /api/accounts/{accountId}/transactions
```

### Transactions

```text
POST   /api/accounts/{accountId}/deposits
POST   /api/accounts/{accountId}/withdrawals
POST   /api/accounts/{fromAccountId}/transfers
GET    /api/transactions/{transactionId}
```

### Health

```text
GET /api/health
```

## Domain Model

Altamira currently models three main resources:

```text
Customer
Account
Transaction
```

Relationships:

```text
Customer 1 -> many Accounts
Account 1 -> many Transactions
Transaction many -> 1 Account
```

Example:

```text
customers.id
    ↓
accounts.customer_id

accounts.id
    ↓
transactions.account_id
```

## Business Rules

The API includes basic banking-style business rules, such as:

- Accounts belong to customers
- Transactions belong to accounts
- Deposits increase account balance
- Withdrawals decrease account balance
- Transfers move funds between accounts
- Inactive customers can be blocked from account actions
- Closed or inactive accounts can be blocked from transactions

## Project Structure

```text
src/main/java/com/samwise/altamira
├── AltamiraApplication.java
├── account
│   ├── domain
│   ├── dto
│   ├── AccountController.java
│   ├── AccountService.java
│   └── AccountRepository.java
├── customer
│   ├── domain
│   ├── dto
│   ├── CustomerController.java
│   ├── CustomerService.java
│   └── CustomerRepository.java
├── transaction
│   ├── domain
│   ├── dto
│   ├── TransactionController.java
│   ├── TransactionService.java
│   └── TransactionRepository.java
├── health
└── common
    ├── ApiResponse.java
    ├── ApiError.java
    ├── Meta.java
    ├── GlobalExceptionHandler.java
    └── exceptions
```

## API Response Format

Responses use a consistent wrapper format:

```json
{
  "success": true,
  "message": "Success",
  "data": {},
  "errors": [],
  "meta": {
    "timestamp": "2026-06-20T00:00:00Z"
  }
}
```

Error responses follow the same general shape:

```json
{
  "success": false,
  "message": "Validation failed",
  "data": null,
  "errors": [
    {
      "code": "400 BAD_REQUEST",
      "field": "amount",
      "message": "must be greater than or equal to 0.01"
    }
  ],
  "meta": {
    "timestamp": "2026-06-20T00:00:00Z"
  }
}
```

## Database

This project uses PostgreSQL.

Create a local database:

```sql
CREATE DATABASE altamira;
```

Example `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/altamira
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Running Locally

Run the application with Maven:

```bash
mvn spring-boot:run
```

The API will start at:

```text
http://localhost:8080
```

Check health:

```text
GET http://localhost:8080/api/health
```

## Example Requests

### Create Customer

```http
POST /api/customers
Content-Type: application/json
```

```json
{
  "firstName": "Sam",
  "lastName": "Wise",
  "email": "sam@example.com",
  "password": "password123"
}
```

### Create Account

```http
POST /api/accounts
Content-Type: application/json
```

```json
{
  "customerId": "customer-uuid-here",
  "accountType": "SAVINGS"
}
```

### Create Deposit

```http
POST /api/accounts/{accountId}/deposits
Content-Type: application/json
```

```json
{
  "amount": 100.00,
  "description": "Initial deposit"
}
```

### Create Withdrawal

```http
POST /api/accounts/{accountId}/withdrawals
Content-Type: application/json
```

```json
{
  "amount": 25.00,
  "description": "ATM withdrawal"
}
```

### Get Account Transactions

```http
GET /api/accounts/{accountId}/transactions
```

## Development Notes

This project is intended as a learning and portfolio API. It focuses on backend fundamentals including:

- RESTful endpoint structure
- DTO-based request and response models
- JPA entity relationships
- Service-layer business rules
- Validation with Jakarta Bean Validation
- Centralized exception handling
- PostgreSQL-backed persistence

## Future Improvements

Potential future improvements include:

- Authentication and authorization
- Role-based access control
- Pagination for transaction history
- Database migrations with Flyway or Liquibase
- Integration tests
- Docker Compose for local PostgreSQL setup
- OpenAPI/Swagger documentation
- Account statements or summaries
- Soft deletes instead of hard deletes
- More detailed audit logging

## Status

In active development as a Java and Spring Boot portfolio project.
