# 🏟️ CodeArena — Online Code Execution Platform

CodeArena is a backend system for an online coding platform that allows users to register, solve problems, and execute code in multiple programming languages. The platform securely handles user authentication, processes code execution through an external API, and stores submission results with performance metrics.

---

# 🚀 Features

### 🔐 JWT-based Authentication

Secure login and registration using token-based authentication implemented with Spring Security and JWT.

### 💻 Multi-language Code Execution

Execute code in **Java, C++, and Python** using the JDoodle Execution Engine API.

### ⏱️ Performance Analysis

Each submission records **execution time and memory usage** for performance tracking.

### 🗄️ Relational Database Design

Structured MySQL schema with separate tables for:

* Users
* Problems
* Submissions
* Results

### 🔒 Protected Routes

All problem and submission endpoints are secured using JWT authentication filters.

### 📡 RESTful API Architecture

Built using **Spring Boot MVC layered architecture** with clean separation of controller, service, and repository layers.

### ✅ Fully Tested APIs

All endpoints tested end-to-end using Postman.

---

# 🛠️ Tech Stack

| Layer          | Technology                          |
| -------------- | ----------------------------------- |
| Backend        | Java, Spring Boot                   |
| Security       | Spring Security, JWT                |
| Database       | MySQL                               |
| Code Execution | JDoodle API                         |
| Architecture   | REST API, MVC, Layered Architecture |
| Testing        | Postman                             |
| Build Tool     | Maven                               |

---

# 📁 Project Structure

```
CodeArena
│
├── src
│   └── main
│       ├── java
│       │   └── com/codearena
│       │       ├── controller     # REST Controllers
│       │       ├── service        # Business Logic
│       │       ├── repository     # Database Layer
│       │       ├── model          # Entity Classes
│       │       ├── security       # JWT Filter & Security Configuration
│       │       └── dto            # Request/Response DTOs
│       │
│       └── resources
│           └── application.properties
│
├── pom.xml
└── README.md
```

---

# 🗃️ Database Schema Overview

**users**
Stores registered user information.

**problems**
Contains coding problems with metadata.

**submissions**
Stores code submissions made by users.

**results**
Stores execution output, runtime, and memory statistics.

---

# 🔑 API Endpoints (Sample)

## Authentication

| Method | Endpoint           | Description                 |
| ------ | ------------------ | --------------------------- |
| POST   | /api/auth/register | Register new user           |
| POST   | /api/auth/login    | Login and receive JWT token |

## Problems

| Method | Endpoint           | Description        |
| ------ | ------------------ | ------------------ |
| GET    | /api/problems      | Fetch all problems |
| GET    | /api/problems/{id} | Get problem by ID  |

## Submissions

| Method | Endpoint                  | Description                 |
| ------ | ------------------------- | --------------------------- |
| POST   | /api/submit               | Submit code for execution   |
| GET    | /api/submissions/{userId} | Get user submission history |

⚠️ All `/api/problems` and `/api/submit` endpoints require a valid:

```
Authorization: Bearer <JWT_TOKEN>
```

---

# ⚙️ Getting Started

## Prerequisites

* Java 17+
* Maven
* MySQL
* JDoodle API credentials

---

## Clone the Repository

```
git clone https://github.com/AtharvaMa/CodeArena.git
cd CodeArena
```

---

## Configure `application.properties`

```
spring.datasource.url=jdbc:mysql://localhost:3306/codearena
spring.datasource.username=your_username
spring.datasource.password=your_password

jdoodle.clientId=your_jdoodle_client_id
jdoodle.clientSecret=your_jdoodle_client_secret

jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
```

---

## Run the Application

```
mvn spring-boot:run
```

---

## Test APIs

Use Postman to test the APIs:

1. Register a user
2. Login and obtain JWT token
3. Add token in Authorization header
4. Access protected endpoints

---

# 📬 Sample Request — Code Submission

### Request

```
POST /api/submit
Authorization: Bearer <your_jwt_token>
```

```
{
  "problemId": 1,
  "language": "java",
  "code": "public class Main { public static void main(String[] args) { System.out.println(\"Hello\"); } }"
}
```

### Response

```
{
  "output": "Hello",
  "cpuTime": "0.1",
  "memory": "10800",
  "status": "Accepted"
}
```

---

# 🗺️ Roadmap

* JWT Authentication & Authorization
* Multi-language code execution (Java, C++, Python)
* Time & memory analysis per submission
* RESTful backend APIs
* Frontend integration (React / Thymeleaf) — In Progress
* Problem difficulty tagging (Easy / Medium / Hard)
* User dashboard with submission history
* Leaderboard system

---

# 👨‍💻 Author

**Atharva Maddalwar**

GitHub: https://github.com/AtharvaMa
Email: [atharvamaddalwar10@gmail.com](mailto:atharvamaddalwar10@gmail.com)

---

⭐ If you found this project helpful, consider giving it a star!
