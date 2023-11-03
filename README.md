---

# Pawfectly Yours

Pawfectly Yours is a backend API designed to support a front-end shop admin application for pet shops. This project provides essential functionalities for managing pet shop inventory, orders, and customer data.

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [Usage](#usage)
- [Features](#features)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)

## Introduction

Pawfectly Yours simplifies the management of pet shop businesses by offering a robust backend API. It allows shop administrators to handle pet inventory, track orders, and maintain customer information. The system helps streamline day-to-day operations and provides a foundation for developing a user-friendly front-end admin interface.

## Prerequisites

Before using Pawfectly Yours, ensure you have the following prerequisites:

- Java 8 or later
- Apache Maven
- MySQL database server

## Getting Started

Follow these steps to set up and run Pawfectly Yours on your local development environment.

### Installation

1. Clone the repository:

```bash
git clone https://github.com/javiergenepaul/pawfectly-yours.git
```

2. Navigate to the project directory:

```bash
cd pawfectly-yours
```

3. Build the project:

```bash
mvn clean install
```

### Configuration

Pawfectly Yours uses a configuration file to manage database connections and other settings. You can find the configuration file at `src/main/resources/application.properties`. Update the properties to match your environment:

```properties
spring:
  datasource:
    name: dev-server
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/pawfectly_db
    username: root
    password: root
  jpa:
    hibernate:
      naming-strategy: org.hibernate.cfg.EJB3NamingStrategy
      ddl-auto: update
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: "true"
    database-platform: org.hibernate.dialect.MySQL57Dialect
server:
  servlet:
    context-path: /api
  port: 5000

jwt:
  secret: e2JVsI87jgelc61l8v07XIFEYE7YtclunYYP+AVYO4pkS9j0FFE+oZDGu/848Znw
  expiration: 86400000
  refresh-expiration: 2629746000

allowed:
  origin: http://localhost:4022
```

## Usage

Pawfectly Yours provides various API endpoints for managing pet shop data. Refer to the [API documentation](docs/API.md) for detailed information on the available routes, request formats, and responses.

## Features

Pawfectly Yours offers the following key features:

- **Inventory Management:** Add, update, and remove pet inventory items, including pet type, breed, and price.

- **Order Processing:** Create and manage customer orders, update order status, and view order history.

- **Customer Management:** Store and retrieve customer information, including contact details and order history.

- **User Authentication:** Secure endpoints with user authentication to protect sensitive data.
  
- **0Auth Authentication:** 

- **Customizable:** Extend the functionality with additional features tailored to your pet shop's needs.

## Acknowledgements

We'd like to express our gratitude to the open-source community and the creators of the Spring Boot framework for making this project possible.

---
