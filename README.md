# Order App 

## Overview

This project implements a RESTful API using Spring Boot and represents a restaurant service for the purpose of placing an order with the following features:

- **Basic Authentication:** Secure client authentication in app.
- **Personal order registration and management:** Create and tracking the order .

## API Endpoints

### Registartion

- **POST /app/register:** Create a new client account.

### Basic functionality

- **POST /app/order:** Create an order.
- **GET /app/order/orders/{id}** Get an order by ID for the authenticated client.
- **DELETE /app/order/orders/{id}** Delete an order by ID for the authenticated client.
- **GET /app/order/my-orders:** Get all orders for the authenticated client.

### Search or filter

- **GET /app/order/rst-by-address** Search restraunt by his address.
- **GET /app/order/restraunts/all-rst** Get all addresses of restraunt.
- **GET /app/order/foods/food-by-name** Search food by his name.
- **GET /app/order/foods/filter-by-price** Filter foods by cost range.
- **GET /app/order/foods/filter-by-category** Filter foods by category food.

## Technologies Used

- Spring Boot
- PostgreSQL
- Basic Authentication/Spring Security
- Hibernate/JPA for Database
- Spring AOP
- Swagger for OpenAPI specifications
- Test coverage by Junit/MockMVC
- Postman for testing API
- Docker for deploy app
