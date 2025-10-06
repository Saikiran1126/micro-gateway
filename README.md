# Microservices Project Documentation
 
This project is a Spring Boot-based microservices architecture consisting of an API Gateway, User Service, Product Service, and Order Service. The services are registered with Eureka for service discovery and secured with Basic Authentication. The API Gateway routes requests and implements circuit breakers for resilience.
 
## Table of Contents
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
  - [API Gateway Endpoints](#api-gateway-endpoints)
  - [User Service Endpoints](#user-service-endpoints)
  - [Product Service Endpoints](#product-service-endpoints)
  - [Order Service Endpoints](#order-service-endpoints)
- [Authentication](#authentication)
- [Testing with Postman](#testing-with-postman)
  - [Setup Postman](#setup-postman)
  - [Example Requests](#example-requests)
- [Troubleshooting](#troubleshooting)
- [H2 Database Management](#h2-database-management)
- [Contributing](#contributing)
- [License](#license)
 
## Overview
- **API Gateway**: Acts as the entry point, routing requests to User, Product, and Order services, with security and circuit breaker support.
- **User Service**: Manages user CRUD operations.
- **Product Service**: Manages product CRUD operations.
- **Order Service**: Manages order CRUD operations, linking users and products.
- **Eureka**: Handles service discovery.
 
## Project Structure
- `com.microservice.apigateway`: API Gateway application.
- `com.microservice.user_service`: User Service application.
- `com.microservice.product_service`: Product Service application.
- `com.microservice.order_service`: Order Service application.
- Configuration files: `application.properties` in each service.
 
## Prerequisites
- **Java**: JDK 17 or higher.
- **Maven**: For building the project.
- **Postman**: For API testing.
- **Eureka Server**: Running at `http://localhost:8761` (implement separately if not included).
- **IDE**: IntelliJ IDEA or Eclipse (recommended).
 
## Setup Instructions
1. **Clone the Repository**:
   - Clone your project repository or set up the provided code in your workspace.
2. **Configure Environment**:
   - Ensure `application.properties` files are in `src/main/resources` for each module.
   - Update `eureka.client.service-url.defaultZone=http://localhost:8761/eureka` if Eureka runs on a different host.
3. **Install Dependencies**:
   - Run `mvn clean install` in the root directory (or each module) to download dependencies.
 
## Running the Application
1. **Start Eureka Server**:
   - Run your Eureka Server application (port 8761).
2. **Start Services**:
   - **API Gateway**: Run `ApiGatewayApplication` (port 8080).
   - **User Service**: Run `UserServiceApplication` (port 8081).
   - **Product Service**: Run `ProductServiceApplication` (port 8082).
   - **Order Service**: Run `OrderServiceApplication` (port 8083).
3. **Verify Registration**:
   - Open `http://localhost:8761` to ensure all services (apigateway, user-service, product-service, order-service) are registered as "UP".
 
## API Endpoints
All requests go through the API Gateway (`http://localhost:8080`) and require Basic Authentication.
 
### API Gateway Endpoints
- **GET /admin/test**:
  - Response: `"Admin access granted"` (requires ADMIN role).
- **GET /customer/test**:
  - Response: `"Customer access granted"` (requires CUSTOMER role).
- **GET /fallback**:
  - Response: `"Fallback: Service is down. Please try again later. Error: <message>"` (triggered by circuit breaker).
 
### User Service Endpoints
- **GET /users**:
  - Retrieve all users.
  - Response: Array of user objects.
- **GET /users/{id}**:
  - Retrieve user by ID.
  - Response: Single user object or 404 if not found.
- **POST /users**:
  - Create a new user.
  - Body: `{"username":"testuser","email":"test@example.com","password":"testpass","role":"CUSTOMER"}`.
  - Response: Created user object.
- **PUT /users/{id}**:
  - Update user by ID.
  - Body: `{"username":"updateduser","email":"updated@example.com","password":"newpass","role":"ADMIN"}`.
  - Response: Updated user object or 404 if not found.
- **DELETE /users/{id}**:
  - Delete user by ID.
  - Response: 204 No Content or 404 if not found.
 
### Product Service Endpoints
- **GET /products**:
  - Retrieve all products.
  - Response: Array of product objects.
- **GET /products/{id}**:
  - Retrieve product by ID.
  - Response: Single product object or 404 if not found.
- **POST /products**:
  - Create a new product.
  - Body: `{"name":"Phone","price":499.99,"quantity":5}`.
  - Response: Created product object.
- **PUT /products/{id}**:
  - Update product by ID.
  - Body: `{"name":"Updated Phone","price":599.99,"quantity":10}`.
  - Response: Updated product object or 404 if not found.
- **DELETE /products/{id}**:
  - Delete product by ID.
  - Response: 204 No Content or 404 if not found.
 
### Order Service Endpoints
- **GET /orders**:
  - Retrieve all orders.
  - Response: Array of order objects.
- **GET /orders/{id}**:
  - Retrieve order by ID.
  - Response: Single order object or 404 if not found.
- **POST /orders**:
  - Create a new order.
  - Body: `{"userId":1,"productId":1,"quantity":2,"totalPrice":999.98,"orderDate":"2025-10-03T12:00:00"}`.
  - Response: Created order object.
- **PUT /orders/{id}**:
  - Update order by ID.
  - Body: `{"userId":1,"productId":2,"quantity":5,"totalPrice":2499.95,"orderDate":"2025-10-02T12:00:00"}`.
  - Response: Updated order object or 404 if not found.
- **DELETE /orders/{id}**:
  - Delete order by ID.
  - Response: 204 No Content or 404 if not found.
 
## Authentication
- **Method**: Basic Authentication.
- **Credentials**:
  - Admin: Username: `admin`, Password: `adminpass` (role: ADMIN).
  - User: Username: `user`, Password: `userpass` (role: CUSTOMER).
- **Configuration**: Defined in `SecurityConfig` of the API Gateway using `MapReactiveUserDetailsService`.
 
## Testing with Postman
### Setup Postman
1. **Install Postman**: Download from [postman.com](https://www.postman.com/downloads/).
2. **Create a Collection**: Name it "Microservices API Tests".
3. **Set Environment**:
   - Create an environment (e.g., "Local").
   - Add variables:
     - `base_url = http://localhost:8080`
     - `admin_username = admin`
     - `admin_password = adminpass`
     - `user_id`, `product_id` (set dynamically via tests).
 
### Example Requests
- **Create User**:
  - URL: `{{base_url}}/users`
  - Method: POST
  - Authorization: Basic Auth (`{{admin_username}}`/`{{admin_password}}`)
  - Headers: `Content-Type: application/json`
  - Body:
    ```json
    {
      "username": "testuser",
      "email": "test@example.com",
      "password": "testpass",
      "role": "CUSTOMER"
    }
