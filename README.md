Employee Management REST API
A RESTful Employee Management System built using Java, Spring Boot, Spring Data JPA, and PostgreSQL.
This project provides REST APIs to manage employee information, including creating, retrieving, updating, and deleting employee records.
Features
Create a new employee
Retrieve all employees
Retrieve an employee by ID
Update employee details
Delete an employee
Centralized exception handling
Database integration using PostgreSQL
Unit and controller testing
Technologies Used
Technology
Purpose
Java
Programming Language
Spring Boot
Backend Framework
Spring Data JPA
Database Operations
PostgreSQL
Relational Database
Maven
Dependency Management
JUnit
Unit Testing
Mockito
Mock Testing
Git & GitHub
Version Control

Project Structure
employee-management/
├── src/
│   ├── main/
│   │   ├── java/com/krisha/employeemanagement/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── EmployeeManagementApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
├── pom.xml
└── README.md
REST API Endpoints
Method
Endpoint
Description
POST
/api/employees
Create employee
GET
/api/employees
Get all employees
GET
/api/employees/{id}
Get employee by ID
PUT
/api/employees/{id}
Update employee
DELETE
/api/employees/{id}
Delete employee

Database Configuration
This project uses PostgreSQL.
Configure your database credentials in application.properties.
How to Run the Project
Clone the repository:
git clone https://github.com/Krisha1803/employee-management.git

Open the project in IntelliJ IDEA.
Configure PostgreSQL database credentials.
Run the Spring Boot application.
The application will start on:
http://localhost:8080
Testing
Unit tests and controller tests are included in the project.
Run tests using:
mvn test
Author
Krisha
License
This project is created for learning and educational purposes.

