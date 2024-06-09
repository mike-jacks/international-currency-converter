# International Currency Converter

A Java-based application for converting currencies when shipping products across borders with import duty rates and import tax rates calculated. 

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The International Currency Converter is designed to help users calculate final cost of products sold between borders, including currency exchange rate, duty rate, and tax rate. The application is built using Spring Boot and utilizes Netflix's DGS framework for GraphQL Query's and Mutation's.

## Features

- Convert amount between different currencies.
- Starts with multiple currencies including USD, EUR, GBP, JPY, CHF, and CAD.
- Add Currency pairs with conversion rates as needed
- Add Products with price and currency codes as needed4
- Add Countries with currency codes as needed

## Technologies

- Java
- Spring Boot
- Hibernate
- PostgreSQL
- Docker
- Railway for deployment
- GraphQL

## Installation

### Prerequisites

- Java 21 or higher
- Maven
- Docker

### Initial setup

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/mike-jacks/international-currency-converter.git
   cd international-currency-converter

### Create a \`.env\` file

1. Using the provided \`*.env.example*\` file, make a copy of it called \`*.env*\`:

   ```env
   cp .env.example .env

2. Within the .env file, please modify the following variable values to your preference:

   ```env
   POSTGRES_USER=your-postgres-user
   POSTGRES_PASSWORD=your-postgres-password
   POSTGRES_DB=your-local-database-name

### Update the \`docker-compose.yaml\` file
3. Open the \`docker-compose.yaml\` file and comment out the \`app\` portion of services for the moment, so we can deploy just the postgres docker container for now. Whether you run the application locally through your IDE for debugging or run the application through a docker container, this will be the database used for both applications.
   
   ```yaml
   version: "3.1"

   services:
   # app:
   #   build:
   #     context: .
   #     dockerfile: Dockerfile.local
   #   ports:
   #     - "8081:8080"
   #   env_file:
   #     - .env
   #   environment:
   #     SPRING_PROFILES_ACTIVE: docker
   #   depends_on:
   #     - postgres

   postgres:
      image: postgres:latest
      env_file:
         - .env
      environment:
         POSTGRES_DB: ${POSTGRES_DB}
         POSTGRES_USER: ${POSTGRES_USER}
         POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
      ports:
         - "5433:5432"
      volumes:
         - postgres-data:/var/lib/postgresql/data

   volumes:
      postgres-data:

### Build and deploy Postgres Docker Container
1. Within a terminal session in the root directory of your project, run the following command:

    ```bash
    docker-compose up -d
   

bash
Copy code
mvn spring-boot:run
Usage
Once the application is running, you can access the API at http://localhost:8080. You can use tools like Postman or cURL to interact with the API.

Example Request
To convert an amount from one currency to another, you can use the following endpoint:

http
Copy code
GET /convert?amount=100&from=USD&to=EUR
Contributing
Contributions are welcome! Please follow these steps to contribute:

Fork the repository.
Create a new branch: git checkout -b feature-name.
Make your changes and commit them: git commit -m 'Add some feature'.
Push to the branch: git push origin feature-name.
Open a pull request.
License
This project is licensed under the MIT License. See the LICENSE file for details.

python
Copy code

Feel free to provide more specific details or any additional sections you'd like to include. I'll tailor the README accordingly based on your repository content.