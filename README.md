# 📓 Journal App – Spring Boot REST API

A **Spring Boot–based RESTful backend application** for a digital journaling system, focused on secure API design, clean architecture, and professional configuration management.

This project demonstrates **real-world backend development practices**, including environment-based configuration, authentication, role-based APIs, and comprehensive API documentation using Swagger (OpenAPI).

---

## 🚀 Features

### 🔐 Authentication & Authorization
- Spring Security–based authentication
- JWT-ready architecture
- Google OAuth2 login support
- Role-based access (User / Admin)

### 📝 Journal Entry Management
- Create, update, delete, and view journal entries
- User-specific access control

### 👤 User & Admin APIs
- Secure user management APIs
- Admin-level APIs for managing users and journal entries

### ⚡ Event-Driven Architecture (Kafka)
- Apache Kafka integration for asynchronous processing
- Event publishing on key actions (e.g., journal creation, updates)
- Decoupled producer–consumer design

### 🚀 Caching & Performance (Redis)
- Redis integration for caching frequently accessed data
- Reduced database load
- Improved API response times

### 📚 API Documentation
- Interactive Swagger UI (OpenAPI)
- Well-grouped and ordered APIs

### ⚙️ Configuration Management
- Environment variable–based configuration

### 🗄️ Database
- MongoDB integration
- Auto-index creation enabled

---

## 🛠️ Tech Stack

- **Backend:** Spring Boot 3.4.11
- **Security:** Spring Security, JWT, OAuth2 (Google)
- **Database:** MongoDB
- **API Docs:** Swagger (springdoc-openapi)
- **Build Tool:** Maven
- **Language:** Java

---

## 📖 API Documentation (Swagger)

After running the application locally, access Swagger UI at:
http://localhost:8080/swagger-ui.html


### Swagger UI Preview

<img width="1926" height="2071" alt="localhost_8080_swagger-ui_index html" src="https://github.com/user-attachments/assets/0d6fd387-c76f-4906-a4ca-17179d1e1f4f" />

---

## ⚙️ Local Setup

### Prerequisites
- Java 17+
- MongoDB (local or Atlas)
- Git
- Redis 
- Apache Kafka (local)

---

### Clone the Repository
```bash
git clone https://github.com/SNagarjuna07/JournalApp.git
cd JournalApp
```

--- 

### 📄 License

This project is licensed under the MIT License.

---

🙌 Author
S Nagarjuna
