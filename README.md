# 💰 Fund Management System (FMS)

A Spring Boot-based fund management system built with Java 21 and Gradle, designed for secure, role-based financial operations. It supports both CockroachDB and PostgreSQL as databases for scalable and reliable deployments.

---

## 🛠️ Tech Stack

- Java 21  
- Spring Boot 3.x  
- Spring Data JPA (Hibernate)  
- CockroachDB & PostgreSQL  
- JWT Authentication  
- Gradle  
- Docker (optional)

---

## 🚀 Getting Started

### 📦 Prerequisites

- Java 21+
- PostgreSQL or CockroachDB instance
- Gradle
- Git

### 🧪 Clone the Repository

```bash
git clone https://github.com/Wikiwow786/Fund-Management-System-FMS.git
cd Fund-Management-System-FMS
```

⚙️ Configuration
1. Copy the example config
   cp src/main/resources/application.properties.example src/main/resources/application.properties
2. Open application.properties and update the database settings:
   spring.datasource.url=jdbc:postgresql://<host>:<port>/<db-name>  # For PostgreSQL or CockroachDB
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>

🧬 Project Structure
src/
 └── main/
     ├── java/
     │    └── com.yourcompany.fms
     └── resources/
          ├── application.properties
          └── application.properties.example
          
▶️ Run the Application
./gradlew bootRun
Visit:
 http://localhost:8086/fms

 🔐 Authentication
JWT-based token authentication is implemented. You'll need to log in and pass a valid token in the Authorization header to access secured endpoints.

✅ Features
Role-based user access control

Revenue account management

Permission & audit tracking

CockroachDB and PostgreSQL support

JWT-secured API endpoints

Modular and scalable architecture
