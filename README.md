# Solactive Case Study

This is a web service which is responsible for create a new product and retrive all available attributes by two APIs.


## Table Of Contents

1. [Service Documentation](#service-documentation)
2. [Application Components](#application-components)
3. [Tecnology Stac](#technology-stack)
4. [Project Setup](#project-setup)

## Service Documentation

- https://app.swaggerhub.com/apis/SerenBolat/solactive-APIs/0.1

## Application Components

|    Package    |                  Description                 |
|---------------|----------------------------------------------|
|  Controller   |  The Rest Interface and Implementation.      |
|  Service      |  It contains the business logic              |
|  Repository   |  It contains all the data repositories       |

## Technology Stack

- Spring Framework 2.3.2.RELEASE
- MongoDB
- Java 1.8
- Maven
- Rest
- Docker

## Project Setup

- git clone https://github.com/SerenBolat/solactive-case-study.git
- mvn clean install
- docker-compose up -d --build