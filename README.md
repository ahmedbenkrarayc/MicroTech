# SmartShop - Commercial Management System

## 📋 Project Overview

SmartShop is a REST API backend application designed for **MicroTech Maroc**, a B2B IT equipment distributor based in Casablanca. The system manages a portfolio of 650 active clients with progressive loyalty discounts and multi-payment fractional payments per invoice.

### Key Features
- Complete customer relationship management
- Automatic loyalty system with progressive discounts
- Multi-product order management with stock validation
- Multi-payment system (Cash, Check, Bank Transfer)
- Complete financial event traceability
- Automatic calculation of discounts, VAT, and totals

---

## 🎯 Project Context

**Client:** MicroTech Maroc  
**Type:** Backend REST API (No Frontend)  
**Testing:** Postman or Swagger  
**Authentication:** HTTP Session (login/logout)  
**Roles:** ADMIN (MicroTech employees) and CLIENT (B2B customers)

---

## 🛠️ Technical Stack

- **Language:** Java 17
- **Framework:** Spring Boot
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA / Hibernate
- **API:** REST with JSON
- **Testing:** JUnit, Mockito
- **Tools:** Lombok, MapStruct, Builder Pattern
- **Authentication:** HTTP Session

---

## 📐 Architecture

The application follows a layered architecture:

- **Controller Layer:** REST endpoints and request handling
- **Service Layer:** Business logic implementation
- **Repository Layer:** Data access with Spring Data JPA
- **Entity Layer:** Database models
- **DTO Layer:** Data transfer objects
- **Mapper Layer:** Entity-DTO conversion (MapStruct)
- **Exception Handling:** Centralized with @ControllerAdvice

---

## 🗂️ Project Structure

```
smartshop/
├───.mvn
│   └───wrapper
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───microtech
│   │   │           └───microtech
│   │   │               ├───config
│   │   │               ├───controller
│   │   │               ├───dto
│   │   │               │   ├───request
│   │   │               │   │   ├───auth
│   │   │               │   │   ├───order
│   │   │               │   │   ├───payment
│   │   │               │   │   └───product
│   │   │               │   └───response
│   │   │               │       ├───auth
│   │   │               │       ├───order
│   │   │               │       ├───payment
│   │   │               │       ├───product
│   │   │               │       └───statistics
│   │   │               ├───event
│   │   │               ├───exception
│   │   │               ├───listener
│   │   │               ├───mapper
│   │   │               ├───model
│   │   │               │   └───enums
│   │   │               ├───repository
│   │   │               ├───security
│   │   │               │   ├───annotation
│   │   │               │   ├───config
│   │   │               │   ├───exception
│   │   │               │   ├───filter
│   │   │               │   ├───interceptor
│   │   │               │   └───service
│   │   │               └───service
│   │   │                   └───impl
│   │   └───resources
│   │       ├───static
│   │       └───templates
│   └───test
│       └───java
│           └───com
│               └───microtech
│                   └───microtech
│                       └───service
│                           └───impl
├── pom.xml
└── README.md
```

---

## 📊 Class Diagram

<img width="1121" height="1009" alt="SmartShop drawio (1)" src="https://github.com/user-attachments/assets/869b5f0e-6e6f-46f0-a818-6658f634d91d" />

---

## 🔑 Key Features

### 1. Customer Management
- CRUD operations for customers
- Automatic tracking of statistics (total orders, cumulative amount)
- Order history consultation
- First and last order date tracking

### 2. Automatic Loyalty System

**Tier Acquisition Rules:**
- **BASIC:** Default (0 orders)
- **SILVER:** 3+ orders OR 1,000 DH cumulative
- **GOLD:** 10+ orders OR 5,000 DH cumulative
- **PLATINUM:** 20+ orders OR 15,000 DH cumulative

**Discount Application:**
- **SILVER:** 5% if subtotal ≥ 500 DH
- **GOLD:** 10% if subtotal ≥ 800 DH
- **PLATINUM:** 15% if subtotal ≥ 1,200 DH

### 3. Product Management
- Add, modify, and delete products
- Soft delete for products in existing orders
- Product list with filters and pagination
- Stock management

### 4. Order Management
- Multi-product orders with quantities
- Stock validation
- Automatic calculations:
  - Subtotal (HT)
  - Loyalty and promo code discounts
  - VAT (20% on amount after discount)
  - Total (TTC)
- Order status management (PENDING, CONFIRMED, CANCELED, REJECTED)

### 5. Multi-Payment System

**Accepted Payment Methods:**
- **CASH:** Max 20,000 DH per payment (legal limit)
- **CHECK:** Deferred payment with maturity date
- **BANK TRANSFER:** Immediate or deferred payment

**Payment Statuses:**
- EN_ATTENTE (Pending)
- ENCAISSÉ (Cashed)
- REJETÉ (Rejected)

**Business Rule:** Orders must be fully paid (remaining_amount = 0) before an ADMIN can validate them to CONFIRMED status.

---

## 🔐 Authentication & Authorization

### User Roles

**ADMIN (MicroTech Employee):**
- Full CRUD operations
- View all clients
- Create orders for any client
- Register payments
- Validate, cancel, and reject orders

**CLIENT (B2B Customer):**
- Login/logout
- View own profile and loyalty level
- View own order history
- View product catalog (read-only)
- Cannot create, modify, or delete anything

---

## ⚙️ Business Rules

1. **Stock Validation:** requested_quantity ≤ available_stock
2. **Rounding:** All amounts rounded to 2 decimals
3. **Promo Codes:** Format `PROMO-XXXX`, single use possible
4. **VAT Rate:** 20% by default (configurable)
6. **Order Validation:** Only fully paid orders can be CONFIRMED
7. **Soft Delete:** Products in existing orders are marked as deleted, not removed

---

## 🚀 Getting Started

### Prerequisites
- Java 17
- Maven
- PostgreSQL
- Postman or Swagger for API testing

### Installation

1. Clone the repository:
```bash
git clone https://github.com/ahmedbenkrarayc/microtech.git
cd microtech
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### API Documentation
Access Swagger UI at: `http://localhost:8080/swagger-ui.html`

---

## 📡 API Endpoints

Detailed API documentation is available via Swagger UI or Postman collection.

### Main Endpoints Overview

**Authentication**
- User login and logout with session management

**Clients Management**
- CRUD operations for customers
- View client statistics and order history
- Loyalty level tracking

**Products Management**
- Product catalog with pagination and filters
- Stock management
- Soft delete for products in existing orders

**Orders Management**
- Create multi-product orders
- Automatic discount and VAT calculation
- Order status transitions (PENDING → CONFIRMED/CANCELED/REJECTED)

**Payments Management**
- Multi-payment registration per order
- Support for Cash, Check, and Bank Transfer
- Payment status tracking

---

## 📝 Error Handling

The application uses centralized exception handling with appropriate HTTP status codes:

- **400 Bad Request:** Validation errors
- **401 Unauthorized:** Not authenticated
- **403 Forbidden:** Insufficient permissions
- **404 Not Found:** Resource not found
- **422 Unprocessable Entity:** Business rule violation
- **500 Internal Server Error:** Server errors

Error Response Format:
```json
{
  "timestamp": "2025-12-08T10:30:00",
  "status": 422,
  "error": "Unprocessable Entity",
  "message": "Insufficient stock for product XYZ",
  "path": "/api/orders"
}
```
