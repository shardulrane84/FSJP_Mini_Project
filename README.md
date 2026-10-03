# FSJP Mini Project — Event & Property Management System

A role-based **Event and Property Management System** developed as a college mini project using **Spring Boot, Spring Security, Thymeleaf, JPA/Hibernate, and MySQL**.

The application provides separate workflows for **Administrators, Speakers, and Students**, allowing users to manage events, properties, event applications, registrations, and attendance through a centralized web application.

---

## 📌 Project Overview

The **FSJP Mini Project** is a web-based management system designed to simplify the organization and management of college events and their associated resources.

The system provides different functionalities according to the user's role:

* **Admin** can manage users, events, properties, applications, and attendance.
* **Speaker** can view available properties and events, apply for event-property assignments, and view attendance for approved events.
* **Student** can browse available events, register for events, view registrations, and access a QR-code ticket for attendance.

The project demonstrates the practical implementation of a **full-stack Java web application** using the Spring Boot ecosystem.

---

## 🎯 Objectives

The main objectives of this project are:

* Develop a centralized platform for managing college events.
* Implement role-based access control.
* Provide secure user registration and authentication.
* Allow administrators to manage events and properties.
* Allow speakers to apply for properties associated with events.
* Allow students to register for events.
* Generate unique event ticket codes and QR codes.
* Implement event attendance tracking.
* Store and manage application data using a relational database.
* Demonstrate layered application architecture using Spring Boot.

---

## ✨ Key Features

### 🔐 Authentication & Authorization

* User registration and login.
* Three application roles:

  * `ADMIN`
  * `SPEAKER`
  * `STUDENT`
* Role-based dashboard redirection after login.
* Password hashing using **BCrypt**.
* Protected routes using **Spring Security**.
* Logout functionality.
* Duplicate email detection during registration.

### 👨‍💼 Admin Module

Administrators can:

* Access the admin dashboard.
* View registered users.
* Create and manage properties.
* Edit and delete properties.
* View property applications.
* Approve or reject speaker applications.
* Create events.
* Cancel events.
* View event attendance.
* Mark attendance using a ticket code.

### 🎤 Speaker Module

Speakers can:

* Access their dashboard.
* View available properties.
* View available events.
* Apply for a property for a specific event.
* View their submitted applications.
* Check the status of applications.
* View attendance for events where their property application has been approved.

### 🎓 Student Module

Students can:

* Access their dashboard.
* Browse available events.
* Register for events.
* View their event registrations.
* Receive a unique ticket code.
* Access a QR code generated for their event registration.

### 🎟️ Event Registration & Attendance

The system maintains event registration information including:

* Registered student
* Event
* Registration timestamp
* Registration status
* Unique ticket code
* Attendance status
* Attendance timestamp

The application prevents duplicate registration for the same student and event.

### 📱 QR Code Generation

Registered students can access a QR code generated from their unique ticket code.

The project uses **ZXing** for QR-code generation.

The generated QR code can be used as part of the event attendance workflow.

---

## 🏗️ System Architecture

The project follows a layered Spring Boot architecture:

```text
                        ┌──────────────────────┐
                        │      Web Browser      │
                        └──────────┬───────────┘
                                   │
                                   ▼
                        ┌──────────────────────┐
                        │ Thymeleaf Templates  │
                        │   HTML / CSS / JS    │
                        └──────────┬───────────┘
                                   │
                                   ▼
                        ┌──────────────────────┐
                        │     Controllers      │
                        │ Admin / Speaker /    │
                        │ Student / Auth       │
                        └──────────┬───────────┘
                                   │
                                   ▼
                        ┌──────────────────────┐
                        │      Services        │
                        │ Business Logic       │
                        └──────────┬───────────┘
                                   │
                                   ▼
                        ┌──────────────────────┐
                        │    Repositories      │
                        │ Spring Data JPA      │
                        └──────────┬───────────┘
                                   │
                                   ▼
                        ┌──────────────────────┐
                        │   MySQL Database     │
                        └──────────────────────┘
```

Security is implemented through **Spring Security**, which controls access to role-specific application routes.

---

## 🛠️ Technology Stack

| Technology            | Purpose                                     |
| --------------------- | ------------------------------------------- |
| **Java 26**           | Primary programming language                |
| **Spring Boot 4.1.0** | Backend application framework               |
| **Spring MVC**        | Web application and controller layer        |
| **Spring Security**   | Authentication and role-based authorization |
| **Spring Data JPA**   | Database persistence                        |
| **Hibernate**         | ORM implementation                          |
| **Thymeleaf**         | Server-side HTML rendering                  |
| **MySQL**             | Relational database                         |
| **HTML5**             | Frontend structure                          |
| **CSS3**              | Frontend styling                            |
| **JavaScript**        | Client-side interactions                    |
| **ZXing**             | QR-code generation                          |
| **Maven**             | Dependency management and build system      |
| **Lombok**            | Reduced boilerplate where applicable        |
| **Docker**            | Containerized deployment support            |

---

## 📂 Project Structure

```text
FSJP_Mini_Project/
│
├── .vscode/
│
└── demo/
    │
    ├── .mvn/
    │   └── wrapper/
    │
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/example/demo/
    │   │   │       │
    │   │   │       ├── controller/
    │   │   │       │   ├── AdminController.java
    │   │   │       │   ├── AuthController.java
    │   │   │       │   ├── SpeakerController.java
    │   │   │       │   ├── StudentController.java
    │   │   │       │   └── applicationService.java
    │   │   │       │
    │   │   │       ├── entity/
    │   │   │       │   ├── User.java
    │   │   │       │   ├── Event.java
    │   │   │       │   ├── EventRegistration.java
    │   │   │       │   ├── Property.java
    │   │   │       │   └── PropertyApplication.java
    │   │   │       │
    │   │   │       ├── exception/
    │   │   │       │   └── EmailAlreadyExistsException.java
    │   │   │       │
    │   │   │       ├── repository/
    │   │   │       │   ├── UserRepository.java
    │   │   │       │   ├── EventRepository.java
    │   │   │       │   ├── EventRegistrationRepository.java
    │   │   │       │   ├── PropertyRepository.java
    │   │   │       │   └── PropertyApplicationRepository.java
    │   │   │       │
    │   │   │       ├── security/
    │   │   │       │   ├── SecurityConfig.java
    │   │   │       │   └── CustomUserDetailsService.java
    │   │   │       │
    │   │   │       ├── service/
    │   │   │       │   ├── UserService.java
    │   │   │       │   ├── EventService.java
    │   │   │       │   ├── EventRegistrationService.java
    │   │   │       │   ├── PropertyService.java
    │   │   │       │   ├── PropertyApplicationService.java
    │   │   │       │   └── QrCodeService.java
    │   │   │       │
    │   │   │       └── DemoApplication.java
    │   │   │
    │   │   └── resources/
    │   │       ├── static/
    │   │       │   ├── css/
    │   │       │   │   └── style.css
    │   │       │   └── js/
    │   │       │       └── script.js
    │   │       │
    │   │       ├── templates/
    │   │       │   ├── admin/
    │   │       │   ├── speaker/
    │   │       │   ├── student/
    │   │       │   ├── fragments/
    │   │       │   ├── login.html
    │   │       │   └── signup.html
    │   │       │
    │   │       └── application.properties
    │   │
    │   ├── test/
    │   │   └── java/
    │   │       └── com/example/demo/
    │   │           └── DemoApplicationTests.java
    │   │
    │   ├── pom.xml
    │   ├── mvnw
    │   ├── mvnw.cmd
    │   └── dockerfile
    │
    └── README.md
```

---

## 🗃️ Main Data Models

The application uses the following major entities:

### User

Stores application users and their roles.

```text
User
├── id
├── name
├── email
├── password
├── role
└── createdAt
```

Supported roles:

```text
ADMIN
SPEAKER
STUDENT
```

### Event

Represents an event managed by the system.

```text
Event
├── id
├── title
├── description
├── eventDate
├── startTime
├── endTime
├── speaker
├── property
└── status
```

Event status can be:

```text
SCHEDULED
CANCELLED
COMPLETED
```

### Property

Represents a venue/resource that can be associated with an event.

```text
Property
├── id
├── propertyName
├── description
├── location
├── capacity
├── available
└── createdAt
```

### Property Application

Connects a speaker, property, and event.

```text
PropertyApplication
├── id
├── speaker
├── property
├── event
├── status
└── appliedAt
```

Application status:

```text
PENDING
APPROVED
REJECTED
```

### Event Registration

Stores student registrations and attendance information.

```text
EventRegistration
├── id
├── student
├── event
├── registeredAt
├── status
├── ticketCode
├── attended
└── attendedAt
```

---

## 🔄 Application Workflow

### 1. User Registration

```text
User
  │
  ▼
Signup
  │
  ▼
Select Role
  │
  ▼
Account Created
  │
  ▼
Login
```

Passwords are stored using BCrypt hashing rather than plain-text storage.

### 2. Role-Based Login

After successful authentication, the application redirects the user according to their role:

```text
                     Login
                       │
                       ▼
              Spring Security
                       │
          ┌────────────┼────────────┐
          ▼            ▼            ▼
        ADMIN        SPEAKER      STUDENT
          │            │            │
          ▼            ▼            ▼
       Admin         Speaker      Student
      Dashboard      Dashboard    Dashboard
```

### 3. Speaker Property Application

```text
Speaker
   │
   ▼
View Properties & Events
   │
   ▼
Select Property + Event
   │
   ▼
Submit Application
   │
   ▼
Admin Reviews Application
   │
   ├───────────────┐
   ▼               ▼
 APPROVED        REJECTED
   │
   ▼
Speaker Can View
Event Attendance
```

### 4. Student Event Registration

```text
Student
   │
   ▼
Browse Events
   │
   ▼
Select Event
   │
   ▼
Register
   │
   ▼
Unique Ticket Code
   │
   ▼
QR Code Generated
   │
   ▼
Event Attendance
```

---

## 🔐 Security

Spring Security is used to protect the application's role-specific routes.

The current authorization structure includes:

| Route                   | Required Role |
| ----------------------- | ------------- |
| `/admin/**`             | ADMIN         |
| `/speaker/**`           | SPEAKER       |
| `/student/**`           | STUDENT       |
| `/login`                | Public        |
| `/signup`               | Public        |
| Static CSS/JS resources | Public        |

Passwords are encoded using:

```text
BCryptPasswordEncoder
```

The application also uses a custom `UserDetailsService` to load users from the database.

> **Note:** CSRF protection is currently disabled in the project configuration for learning/development simplicity. For a production deployment, CSRF protection should be properly enabled and configured.

---

## 🗄️ Database Configuration

The application uses **MySQL** as its primary relational database.

Database connectivity is configured through environment variables rather than hard-coding database credentials:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

JPA/Hibernate is configured with:

```properties
spring.jpa.hibernate.ddl-auto=update
```

This allows Hibernate to update the database schema based on the application's entity definitions during development.

### Required Environment Variables

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Example:

```text
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/fsjp
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_password
```

**Do not commit actual database passwords or other sensitive credentials to GitHub.**

---

## 💻 Getting Started

### Prerequisites

Before running the project locally, make sure you have:

* Java 26
* MySQL
* Git
* Maven, or use the included Maven Wrapper
* A code editor/IDE such as IntelliJ IDEA, Eclipse, or VS Code

---

### 1. Clone the Repository

```bash
git clone https://github.com/it25shardulrane-dev/FSJP_Mini_Project.git
```

Move into the project directory:

```bash
cd FSJP_Mini_Project/demo
```

---

### 2. Create the MySQL Database

Create a database in MySQL:

```sql
CREATE DATABASE fsjp;
```

---

### 3. Configure Database Credentials

Set the required environment variables.

#### Windows PowerShell

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/fsjp"
$env:SPRING_DATASOURCE_USERNAME="root"
$env:SPRING_DATASOURCE_PASSWORD="your_password"
```

#### Linux / macOS

```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/fsjp"
export SPRING_DATASOURCE_USERNAME="root"
export SPRING_DATASOURCE_PASSWORD="your_password"
```

---

### 4. Run the Application

Using Maven Wrapper:

#### Windows

```cmd
mvnw.cmd spring-boot:run
```

#### Linux / macOS

```bash
./mvnw spring-boot:run
```

Or, if Maven is installed:

```bash
mvn spring-boot:run
```

---

### 5. Open the Application

Once the application starts, open:

```text
http://localhost:8080/login
```

You can create an account using the signup page:

```text
http://localhost:8080/signup
```

---

## 🐳 Docker Support

The repository also contains a Dockerfile under:

```text
demo/dockerfile
```

The project can therefore be containerized for deployment on platforms that support Docker-based Spring Boot applications.

A typical Docker workflow is:

```bash
docker build -t fsjp-mini-project .
```

Then:

```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host:3306/fsjp" \
  -e SPRING_DATASOURCE_USERNAME="root" \
  -e SPRING_DATASOURCE_PASSWORD="your_password" \
  fsjp-mini-project
```

Database credentials should be supplied through environment variables rather than included directly in the image or source code.

---

## ☁️ Deployment

The application is suitable for deployment using a container-based hosting platform.

A typical deployment architecture is:

```text
                ┌──────────────────────┐
                │       Browser        │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │   Spring Boot App    │
                │      Render /        │
                │   Docker Container   │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │    MySQL Database    │
                │ Railway / MySQL Host │
                └──────────────────────┘
```

The application reads the database connection details from environment variables, making it suitable for separating the application server and database server.

---

## 🧪 Testing

The project contains a test structure under:

```text
src/test/java/
```

The current repository includes:

```text
DemoApplicationTests.java
```

Tests can be executed using:

```bash
mvn test
```

or:

```bash
./mvnw test
```

On Windows:

```cmd
mvnw.cmd test
```

---

## 📋 Functional Modules

| Module              | Main Functionality                                  |
| ------------------- | --------------------------------------------------- |
| Authentication      | Signup, login, logout                               |
| Admin               | Users, events, properties, applications, attendance |
| Speaker             | Property/event selection, applications, attendance  |
| Student             | Event browsing, registration, tickets, QR codes     |
| Event Management    | Create, view, cancel and manage events              |
| Property Management | Create, update, delete and manage properties        |
| Registration        | Student event registration                          |
| Attendance          | Ticket-based attendance tracking                    |
| QR Code             | Generate QR codes for registrations                 |
| Database            | Persistent MySQL storage                            |

---

## 🔮 Future Enhancements

The current project provides the core functionality required for the mini project. Possible future improvements include:

* Email notifications for registration and application status.
* Automated event reminders.
* Improved QR-code scanning directly through a device camera.
* Advanced attendance analytics.
* Search and filtering for events and properties.
* Pagination for large datasets.
* Improved validation and error handling.
* Audit logs for administrative operations.
* CSRF protection and additional production security hardening.
* Password reset and account recovery.
* REST API support for mobile or third-party clients.
* Improved automated test coverage.
* Cloud-based file/image storage for event and property media.

---

## 📚 Learning Outcomes

This project provided practical experience with:

* Java-based web application development.
* Spring Boot application architecture.
* MVC design pattern.
* Spring Security authentication and authorization.
* Role-based access control.
* Spring Data JPA and Hibernate.
* MySQL database integration.
* Entity relationships and database persistence.
* Server-side rendering using Thymeleaf.
* Form handling and validation.
* QR-code generation.
* Maven project management.
* Docker-based application deployment.
* Basic software testing and debugging.

---

## 🎓 Academic Context

This project was developed as a **college-level mini project** to demonstrate the practical application of concepts learned in Java, web development, database management, and software engineering.

The project focuses on integrating multiple technologies into a single working application rather than representing a production-grade enterprise system.

---

## 👨‍💻 Author

**Shardul Rane**

College Mini Project
Java / Spring Boot / MySQL

---

## 📄 License

This project was developed for **academic and educational purposes**.

If you intend to reuse or extend the project, please provide appropriate attribution to the original author.

---

## ⭐ Acknowledgement

This project was developed as part of academic coursework and served as a practical exercise in designing, developing, securing, testing, and deploying a database-driven web application using the Spring Boot ecosystem.
