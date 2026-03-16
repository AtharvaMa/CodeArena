# 🏟️ CodeArena

A web-based coding practice platform where users can write, compile, and run code directly in the browser — powered by the **JDoodle API** for code execution.

---

## 🚀 Features

- **Online Code Editor** — Write and execute code without any local setup
- **Multi-language Support** — Supports all languages available through the JDoodle API (Java, Python, C, C++, JavaScript, and more)
- **Instant Output** — Get real-time execution results in the browser
- **Practice Problems** — Sharpen your coding skills with challenges and exercises
- **Clean UI** — Simple and intuitive interface to focus on coding

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Java (Spring Boot) |
| Build Tool | Maven |
| Code Execution | JDoodle Compiler API |
| Frontend | HTML / CSS / JavaScript (Thymeleaf templates) |

---

## ⚙️ How It Works

CodeArena uses **wrapper classes** to communicate with the JDoodle API:

1. User writes code in the browser editor and selects a language.
2. The frontend sends the code to the Spring Boot backend.
3. The backend wraps the request using a `JDoodleRequest` wrapper class (containing `clientId`, `clientSecret`, `script`, `language`, `versionIndex`).
4. The backend calls the JDoodle `/v1/execute` endpoint.
5. The response is unwrapped via a `JDoodleResponse` wrapper class and returned to the frontend for display.

```
User → Browser Editor → Spring Boot Controller → JDoodle API Wrapper → JDoodle API
                                                                              ↓
User ← Output Display  ← Spring Boot Controller ← JDoodle Response Wrapper ←┘
```

---

## 📦 Project Structure

```
CodeArena/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/codearena/
│       │       ├── controller/       # REST controllers
│       │       ├── model/            # JDoodle request/response wrapper classes
│       │       └── service/          # Business logic & API calls
│       └── resources/
│           ├── templates/            # Thymeleaf HTML templates
│           └── application.properties
├── pom.xml
└── mvnw
```

---

## 🔑 JDoodle API Setup

1. Sign up at [https://www.jdoodle.com](https://www.jdoodle.com) and get your **Client ID** and **Client Secret**.
2. Add them to `src/main/resources/application.properties`:

```properties
jdoodle.clientId=YOUR_CLIENT_ID
jdoodle.clientSecret=YOUR_CLIENT_SECRET
jdoodle.url=https://api.jdoodle.com/v1/execute
```

> ⚠️ **Never commit your API credentials to version control.** Use environment variables or a `.env` file in production.

---

## ▶️ Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+

### Run Locally

```bash
# Clone the repository
git clone https://github.com/AtharvaMa/CodeArena.git
cd CodeArena

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

Then open your browser and go to:
```
http://localhost:8080
```

---

## 📡 JDoodle API — Wrapper Classes

### Request Wrapper (`JDoodleRequest.java`)

```java
public class JDoodleRequest {
    private String clientId;
    private String clientSecret;
    private String script;
    private String language;
    private String versionIndex;
    // getters and setters
}
```

### Response Wrapper (`JDoodleResponse.java`)

```java
public class JDoodleResponse {
    private String output;
    private int statusCode;
    private String memory;
    private String cpuTime;
    // getters and setters
}
```

---

## 🌐 Supported Languages (via JDoodle)

- Java, Python 3, C, C++, JavaScript (Node.js)
- Ruby, Go, Kotlin, Swift, PHP, and [many more](https://www.jdoodle.com/compiler-api)

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you'd like to change.

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Open a Pull Request

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👤 Author

**Atharva Ma**
- GitHub: [@AtharvaMa](https://github.com/AtharvaMa)
