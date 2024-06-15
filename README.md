# 🌱 Automated Greenhouse Management System (AGMS)

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.11-brightgreen?logo=springboot)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.1-brightgreen?logo=spring)
![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![License](https://img.shields.io/badge/License-Academic-blue)

---

## 📌 Overview

**AGMS** is a cloud-native, microservice-based application designed to monitor and control greenhouse environments using real-time IoT data. The system integrates with an external IoT provider to fetch temperature and humidity readings and automatically triggers control actions (e.g., Fan ON / Heater ON) based on configurable zone rules.

---

## 🎯 Features

| Feature | Description |
|---|---|
| 🌡️ Real-time Monitoring | Fetches temperature & humidity from an external IoT API every 10 seconds |
| 🤖 Rule-Based Automation | Automatically triggers fans/heaters based on predefined zone thresholds |
| 🏡 Zone Management | Create and manage greenhouse zones with configurable environmental thresholds |
| 🌱 Crop Lifecycle Tracking | Track crops from Seedling → Vegetative → Harvested stages |
| 🔐 JWT Authentication | Secure access via a dedicated Auth Service and API Gateway filter |
| ⚡ Microservice Architecture | Independently deployable services with centralized discovery and configuration |

---

## 🏗️ System Architecture

```
                        ┌─────────────────┐
                        │   Config Server  │  :8888
                        │   (configserver) │
                        └────────┬─────────┘
                                 │ config
               ┌─────────────────┼─────────────────────┐
               ▼                 ▼                       ▼
     ┌──────────────┐   ┌─────────────────┐   ┌─────────────────┐
     │ Service      │   │   API Gateway   │   │  Auth Service   │
     │ Registry     │   │   (api-gateway) │   │  (auth-service) │
     │ (Eureka)     │   │      :8080      │   │      :8085      │
     └──────────────┘   └────────┬────────┘   └─────────────────┘
                                 │ routes to
          ┌──────────────────────┼──────────────────────┐
          ▼                      ▼                       ▼                      ▼
 ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
 │  Zone Service   │  │ Sensor Service  │  │Automation Service│  │  Crop Service   │
 │    :8081        │  │    :8082        │  │     :8083        │  │    :8084        │
 └─────────────────┘  └─────────────────┘  └─────────────────┘  └─────────────────┘
```

### 🔹 Infrastructure Services

| Service | Module | Port | Responsibility |
|---|---|---|---|
| Config Server | `configserver` | 8888 | Centralized configuration management |
| Service Registry | `serviceregistry` | 8761 | Netflix Eureka service discovery |
| API Gateway | `api-gateway` | 8080 | Centralized routing & JWT validation |
| Auth Service | `auth-service` | 8085 | User authentication & JWT issuance |

### 🔹 Domain Microservices

| Service | Module | Port | Responsibility |
|---|---|---|---|
| Zone Service | `zone-service` | 8081 | Manage zones & environmental thresholds |
| Sensor Service | `sensor-service` | 8082 | Fetch IoT readings & push to automation |
| Automation Service | `automation-service` | 8083 | Rule engine for device control decisions |
| Crop Service | `crop-service` | 8084 | Manage crop lifecycle |

---

## ⚙️ Technologies Used

| Category | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5.11 |
| Cloud | Spring Cloud 2025.0.1 |
| API Gateway | Spring Cloud Gateway (WebFlux) |
| Service Discovery | Netflix Eureka |
| Config Management | Spring Cloud Config |
| Security | JWT (JJWT 0.11.5) |
| Communication | OpenFeign / REST APIs |
| Load Balancing | Spring Cloud LoadBalancer |
| API Testing | Postman |

---

## 🔗 External IoT API

- **Base URL:** `http://104.211.95.241:8080/api`
- Provides real-time temperature & humidity sensor readings
- Requires JWT bearer token authentication

---

## 🚀 How to Run the Project

> **Prerequisites:** Java 21, Maven 3.8+

### 1️⃣ Start Infrastructure Services (in order)

```bash
# 1. Config Server (must start first — all services depend on it)
cd configserver && mvn spring-boot:run

# 2. Service Registry (Eureka)
cd serviceregistry && mvn spring-boot:run

# 3. API Gateway
cd api-gateway && mvn spring-boot:run

# 4. Auth Service
cd auth-service && mvn spring-boot:run
```

### 2️⃣ Start Domain Microservices (any order)

```bash
cd zone-service       && mvn spring-boot:run
cd sensor-service     && mvn spring-boot:run
cd automation-service && mvn spring-boot:run
cd crop-service       && mvn spring-boot:run
```

---

## 🔄 System Workflow

```
IoT Provider ──► Sensor Service (every 10s)
                      │
                      ▼
              Automation Service
                      │
              checks zone thresholds
                      │
              ┌───────┴───────┐
              ▼               ▼
          Fan ON          Heater ON
              │               │
              └───────┬───────┘
                      ▼
                  Logs stored
```

1. **Sensor Service** polls the external IoT API every 10 seconds
2. Readings are forwarded to **Automation Service** via OpenFeign
3. **Automation Service** compares data against zone thresholds (from Zone Service)
4. Control actions are triggered (Fan ON / Heater ON)
5. All actions are logged for review

---

## 🔐 Authentication Flow

1. Register/Login via **Auth Service** (`POST /api/v1/auth/register` or `/login`)
2. Receive a **JWT token**
3. Include token in the `Authorization: Bearer <token>` header for all API calls
4. **API Gateway** validates the token before routing requests to downstream services

---

## 📬 API Testing with Postman

1. Import **`AGMS_Postman_Collection.json`** from the root of this repository
2. Set the `BASE_URL` variable to `http://localhost:8080`
3. Authenticate first to get a JWT token, then test all endpoints

---

## 📊 Eureka Dashboard

Access the Eureka dashboard at: **`http://localhost:8761`**

All registered services should show **Status: UP** once fully started.

> 📷 See `/docs` folder for a reference screenshot.

---

## 📁 Project Structure

```
greenhouse_management/
├── configserver/           # Spring Cloud Config Server
├── serviceregistry/        # Netflix Eureka Server
├── api-gateway/            # API Gateway with JWT filter
├── auth-service/           # Authentication & JWT issuance
├── zone-service/           # Zone & threshold management
├── sensor-service/         # IoT data fetching
├── automation-service/     # Rule-based automation engine
├── crop-service/           # Crop lifecycle management
├── config-repo/            # Externalized configuration files
├── AGMS_Postman_Collection.json
└── README.md
```

---

## 👨‍💻 Author

**Tharindu Thrishal**  
Software Engineering Undergraduate

---

## 📜 License

This project is developed for academic purposes.
