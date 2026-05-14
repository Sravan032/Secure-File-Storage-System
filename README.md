# Secure File Storage System

A backend project built using Spring Boot that provides secure user authentication using JWT and file upload functionality.

---

## Features

- User Registration and Login
- Password Hashing using BCrypt
- JWT Authentication
- Spring Security Integration
- Protected APIs
- File Upload API
- File Metadata Storage using MySQL

---

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT
- MySQL
- Spring Data JPA
- Maven

---

## Project Structure

```text
src/main/java/com/sravan/Secure/File/Storage

├── Controller
├── Service
├── Repository
├── model
├── dto
├── Security
└── config
```

---

## Authentication Flow

```text
Login
  ↓
JWT Token Generated
  ↓
Token sent in Authorization Header
  ↓
JWT Filter validates token
  ↓
Protected API accessed
```

---

## API Endpoints

### Register

```http
POST /api/auth/register
```

Request Body:

```json
{
  "username": "sravan",
  "password": "sravan123"
}
```

---

### Login

```http
POST /api/auth/login
```

Request Body:

```json
{
  "username": "sravan",
  "password": "sravan123"
}
```

---

### Upload File

```http
POST /api/files/upload
```

Header:

```text
Authorization: Bearer <JWT_TOKEN>
```

Body:
- form-data
- Key: `file`
- Type: File

---

---

## Run the Project

```bash
git clone https://github.com/Sravan032/Secure-File-Storage-System.git
cd Secure-File-Storage-System
mvn spring-boot:run
```

---

---

## Author

Sravan Jyothi Reddy Nale

GitHub: [https://github.com/Sravan032](https://github.com/Sravan032/Secure-File-Storage-System)
