# ✈️ Flight Management System

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=springboot)
![JWT](https://img.shields.io/badge/JWT-Security-blueviolet?logo=jsonwebtokens)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-red?logo=apachemaven)

---

## 📖 Introduction
The **Flight Management System** is a monolithic Spring Boot application designed to streamline flight reservations and airline operations.  
It allows **users** to search, book, and cancel flights, with booking details delivered directly via email. Cancellations include refund handling, ensuring a smooth experience.  
On the **admin side**, the system provides complete control over flight management, including adding, updating, deleting, and changing flight status.  
With **Spring Security & JWT-based authentication**, the application ensures secure access. The project also implements **global exception handling, logging, pagination, and sorting** for robustness and scalability.  
This project demonstrates real-world airline reservation workflows with clean architecture and modular services, making it an excellent showcase for backend development. 

---

## 📌 Features  

### 👤 User Features  
- Book a flight ticket.  
- Receive ticket booking details via **email**.  
- Cancel a ticket and receive a refund.  
- View all past and upcoming flight details.  
- Search flights by:
  - Flight name  
  - Source → Destination  
  - Other parameters  

### 🛠️ Admin Features  
- Manage flights and bookings.  
- Role-based authentication using **Spring Security + JWT**.  
- Pagination & Sorting for large datasets.  
- Centralized **Global Exception Handling**.  
- Separate modules for:  
  - **Email Service**  
  - **File Handling**  
  - **Logging** (kept separate from terminal logs for cleaner output & future audits).  

---

## 🏗️ Project Architecture  
- **Architecture:** Monolithic  
- **Backend:** Spring Boot  
- **Database:** SQL (MySQL/PostgreSQL supported)  
- **Security:** Spring Security with JWT Authentication  
- **Logging:** Dedicated log files for future traceability  
- **Exception Handling:** Global Exception Handler  

---

## 📂 Project Structure  

```bash
flight-management-system/
├── src/main/java/com/example/flightmanagement/
│   ├── controller/        # REST Controllers (User & Admin APIs)
│   ├── service/           # Business logic
│   ├── repository/        # Data access layer
│   ├── model/             # Entity classes (Flight, Booking, User, etc.)
│   ├── config/            # Security, JWT, Logging configuration
│   ├── exception/         # Global exception handler
│   ├── util/              # Utility classes (Email, File handling, etc.)
├── src/main/resources/
│   ├── application.properties   # Database & app configuration
│   ├── static/                  # Static resources (if any)
│   ├── templates/               # Email templates
├── logs/                        # Application logs
└── README.md

```

## 🚀 Getting Started
✅ Prerequisites

Make sure you have installed:
Java 17+
Maven
MySQL
 or any SQL-compatible DB
Git

## 1.⚙️ Setup Instructions

Clone the Repository

git clone https://github.com/your-username/flight-management-system.git
cd flight-management-system


## 2. Configure Database
Update src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/flight_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


## 3. Build & Run the Application

mvn clean install
mvn spring-boot:run


## 4.Access the Application

API Base URL: http://localhost:8080/api

Swagger Documentation (if enabled): http://localhost:8080/swagger-ui.html

## 🔐 Authentication & Security

Implemented using Spring Security + JWT.

After login, users receive a JWT token.

The token must be included in the Authorization header for all secured requests.

## 📊 API Endpoints (Sample)
User APIs
```
Method	Endpoint	Description
POST	/api/users/book	Book a flight ticket
GET	/api/users/bookings	View all user bookings
DELETE	/api/users/cancel/{id}	Cancel a ticket & refund
GET	/api/flights/search	Search flights
Admin APIs
Method	Endpoint	Description
POST	/api/admin/flights	Add a new flight
PUT	/api/admin/flights/{id}	Update flight details
DELETE	/api/admin/flights/{id}	Delete a flight
GET	/api/admin/bookings	View all bookings

```

Flights API
```
POST   /api/flights/add               → Add a flight
POST   /api/flights/addInBulk         → Add flights in bulk
GET    /api/flights/getAll            → Get all flights
GET    /api/flights/get/{id}          → Get flight by ID
GET    /api/flights/getbydate         → Search flights by source, destination & date
PUT    /api/flights/update/{id}       → Update flight details
PUT    /api/flights/update/status/{id} → Update flight status
DELETE /api/flights/delete/{id}       → Delete flight
```
## 📧 Email & Notifications

Users receive booking confirmation details via email.

Email templates stored in src/main/resources/templates.

## 📝 Logging

Logs stored in a dedicated file instead of the terminal.

Useful for monitoring and future debugging.

Add screenshots of Swagger UI, Database schema, or Postman examples here.

🤝 Contributing

## Contributions are welcome! To contribute:
Fork the repository
Create a new feature branch (git checkout -b feature-name)
Commit your changes (git commit -m "Added feature XYZ")
Push to your branch (git push origin feature-name)
Open a Pull Request