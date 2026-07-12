# Health-Care-Appointment-App

> Spring Boot microservices platform for patient registration, scheduling, and notifications.

![GitHub stars](https://img.shields.io/github/stars/arpitprajj/Health-Care-Appointment-App?style=for-the-badge&logo=github) ![GitHub forks](https://img.shields.io/github/forks/arpitprajj/Health-Care-Appointment-App?style=for-the-badge&logo=github) ![GitHub issues](https://img.shields.io/github/issues/arpitprajj/Health-Care-Appointment-App?style=for-the-badge&logo=github) ![Last commit](https://img.shields.io/github/last-commit/arpitprajj/Health-Care-Appointment-App?style=for-the-badge&logo=github)

## 📑 Table of Contents

- [Description](#description)
- [Key Features](#key-features)
- [Use Cases](#use-cases)
- [Tech Stack](#tech-stack)
- [Quick Start](#quick-start)
- [Key Dependencies](#key-dependencies)
- [Project Structure](#project-structure)
- [Contributors](#contributors)
- [Contributing](#contributing)

## 📝 Description

Health-Care-Appointment-App is a scalable healthcare management platform designed to streamline doctor management, patient registration, slot booking, and billing workflows. By decoupling these critical administrative domains into independent, dedicated services, the system ensures high availability and resilience across operations.

## ✨ Key Features

- **🏗️ Decoupled Microservices Architecture** — Utilizes a highly modular structure with distinct services for auth, patients, doctors, slots, appointments, payments, and notifications.
- **🔍 Centralized Service Registry** — Employs a dedicated service-registry directory to register microservices dynamically and manage internal discovery.
- **🛡️ API Gateway and Auth** — Ensures secure request routing and token validation through a centralized API gateway and auth-service.
- **📅 Slot and Appointment Management** — Coordinates physician availability and patient bookings using specialized slot-service and appointment-service engines.
- **📨 Event-Driven Notifications** — Integrates message-driven notification dispatches configured via a root Kafka Compose setup.
- **💳 Dedicated Payment Service** — Manages transactional billing flows independently from core scheduling processes using a dedicated service module.

## 🎯 Use Cases

- Building a reference implementation for a distributed, microservice-based healthcare registry and scheduler.
- Learning secure service-to-service communication with Spring Cloud, JWT, and Kafka integration.
- Developing a clinical application skeleton with separated bounded contexts for patients, doctors, and slots.

## 🛠️ Tech Stack

![Java (Maven)](https://img.shields.io/badge/Java%20(Maven)-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

## ⚡ Quick Start

```bash

# 1. Clone the repository
git clone https://github.com/arpitprajj/Health-Care-Appointment-App.git

# Build with Maven
mvn install
```

## 📦 Key Dependencies

```
spring-cloud-starter-gateway-server-webflux: managed
spring-cloud-starter-netflix-eureka-client: managed
jjwt-api: managed
jjwt-impl: managed
jjwt-jackson: managed
spring-boot-starter-test: managed
reactor-test: managed
lombok: managed
spring-cloud-dependencies: managed
```

## 📁 Project Structure

```
.
├── api-gateway
│   ├── .mvn
│   │   └── wrapper
│   │       └── maven-wrapper.properties
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ...
│       │   └── resources
│       │       └── application.yaml
│       └── test
│           └── java
│               └── com
│                   └── ...
├── appointment-service
│   ├── .mvn
│   │   └── wrapper
│   │       └── maven-wrapper.properties
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ...
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ...
├── auth-service
│   ├── .mvn
│   │   └── wrapper
│   │       └── maven-wrapper.properties
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ...
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ...
├── doctor-service
│   ├── .mvn
│   │   └── wrapper
│   │       └── maven-wrapper.properties
│   ├── mongodb-compose.yaml
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ...
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ...
├── kafka-compose.yaml
├── notification-service
│   ├── .mvn
│   │   └── wrapper
│   │       └── maven-wrapper.properties
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ...
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ...
├── patient-service
│   ├── .mvn
│   │   └── wrapper
│   │       └── maven-wrapper.properties
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ...
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ...
├── payment-service
│   ├── .mvn
│   │   └── wrapper
│   │       └── maven-wrapper.properties
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ...
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ...
├── service-registry
│   ├── .mvn
│   │   └── wrapper
│   │       └── maven-wrapper.properties
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── ...
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── com
│                   └── ...
└── slot-service
    ├── .mvn
    │   └── wrapper
    │       └── maven-wrapper.properties
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── ...
        │   └── resources
        │       └── application.properties
        └── test
            └── java
                └── com
                    └── ...
```

## 👥 Contributors

Thanks to everyone who has contributed to this project:

<p align="left">
<a href="https://github.com/arpitprajj" title="arpitprajj"><img src="https://avatars.githubusercontent.com/u/98028010?v=4&s=64" width="64" height="64" alt="arpitprajj" style="border-radius:50%" /></a>
</p>

[See the full list of contributors →](https://github.com/arpitprajj/Health-Care-Appointment-App/graphs/contributors)

## 👥 Contributing

Contributions are welcome! Here's the standard flow:

1. **Fork** the repository
2. **Clone** your fork: `git clone https://github.com/arpitprajj/Health-Care-Appointment-App.git`
3. **Branch**: `git checkout -b feature/your-feature`
4. **Commit**: `git commit -m 'feat: add some feature'`
5. **Push**: `git push origin feature/your-feature`
6. **Open** a pull request

Please follow the existing code style and include tests for new behavior where applicable.

---

<div align="center">

[![Made with ReadmeBuddy](https://img.shields.io/badge/Made%20with-ReadmeBuddy-8B5CFF?style=for-the-badge&logo=markdown&logoColor=white)](https://readmebuddy.com)

<sub>Generate beautiful READMEs in seconds → <a href="https://readmebuddy.com">readmebuddy.com</a></sub>

</div>
