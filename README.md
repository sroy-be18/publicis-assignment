# **Publicis Assignment - Spring Boot Application**

## **Overview**
This is a **Spring Boot MVC application** implementing RESTful APIs with a structured architecture, security mechanisms, caching, and exception handling. The project is built using **Maven** and follows best practices for maintainability and scalability.

## **Architecture**
The application follows a **layered architecture**:
- **Controller Layer:** Handles HTTP requests and responses.
- **Service Layer:** Contains business logic.
- **Repository Layer:** Interfaces with the database using JPA/Hibernate.
- **Configuration Layer:** Handles security, caching, and database configurations.
- **Exception Handling Layer:** Centralized error handling using `@ControllerAdvice`.

## **Key Features**
✅ **Spring Boot MVC Architecture**  
✅ **Spring Security for Authentication & Authorization**  
✅ **Exception Handling with `@ControllerAdvice`**  
✅ **Caching using Spring Cache**  
✅ **Database Interaction using JPA/Hibernate**  
✅ **RESTful API Development**  
✅ **Unit & Integration Testing**

## **Technology Stack**
- **Backend:** Java 17, Spring Boot 3.4.3, Spring Security, Spring Data JPA
- **Database:** H2 (In-memory for testing, can be switched to MySQL/PostgreSQL)
- **Build Tool:** Maven
- **Testing:** JUnit, Mockito
- **Tools:** Swagger for API Documentation

## **Setup & Installation**
### **Prerequisites**
- Install **Java 17**
- Install **Maven**

### **Steps to Run Locally**
```sh
# Clone the Repository
git clone https://github.com/sroy-be18/publicis-assignment.git
cd publicis-assignment

# Build the Project
mvn clean install

# Run the Application
mvn spring-boot:run
```

### **Access APIs:**
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

## **API Endpoints**
| Method | Endpoint               | Description                  |
|--------|------------------------|------------------------------|
| `GET`  | `api/users/{id}`       | Fetch user by ID             |
| `GET`  | `api/users/search`     | Search user by keyword       |
| `GET`  | `api/users/email`      | Fetch user by email Id       |
| `GET`  | `api/users/load-users` | Load users from external API |
## **Security Configuration**
- Uses **Spring Security** for authentication and authorization.
- Disables **CSRF** for Swagger and H2 Console.

## **Exception Handling**
- Centralized exception handling using `@ControllerAdvice`.
- Custom exceptions return meaningful HTTP status codes.

