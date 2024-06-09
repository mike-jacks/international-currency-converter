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

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/mike-jacks/international-currency-converter.git
   cd international-currency-converter


2. Set up the PostgreSQL database. You can use Docker for this:

    ```bash
    docker-compose up -d
   
3. Configure environment variables. Create a .env file in the root directory and add the necessary configurations:

    ```env
    POSTGRES_USER=yourusername
    POSTGRES_PASSWORD=yourpassword
    POSTGRES_DB=yourdatabase
 
4. Run the application:

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