# Outdoor E-Shop

A bachelor’s thesis project focused on the **design and implementation of an e-commerce system** for selling outdoor clothing.  
The project demonstrates a **modular, scalable microservice architecture**, including user management, product administration, order processing, and online payments.

---

## Project Overview

This project was developed as part of my **Bachelor’s Thesis at the Czech Technical University in Prague (CTU FEL, 2025)**.  
It implements a **minimum viable product (MVP)** of an e-shop for outdoor clothing, supporting:

- Product browsing and administration  
- Order creation and management  
- User registration and authentication  
- Secure payments via **Stripe**  
- Scalable, containerized microservices using **Docker**

---

## Documentation

All analytical documentation, including the **business goals**, **requirements specification** and a detailed description of the diagrams, some of which are presented in this README, is available in the full thesis document [`BachelorTheses.pdf`](./BachelorTheses.pdf) included in this repository.  

### Use Cases
The diagram below shows defined use cases to briefly outline the functionalities of the developed system.

<img width="1773" height="875" alt="image" src="https://github.com/user-attachments/assets/0084de9b-d941-4221-a8fa-be702b627184" />


### Data Model

To cover all defined use cases and requirements, I created a data model divided into three microservices, as shown in the diagram below.

<img width="1633" height="850" alt="Class-diagram" src="https://github.com/user-attachments/assets/92b341e9-5a68-4087-af41-c6cb2b1067c6" />



### Component Overview

The components built on top of analytical domain models, together with additional system modules,  
are illustrated in the **component diagram** below.

<img width="1164" height="721" alt="Component-diagram" src="https://github.com/user-attachments/assets/5aea72ec-1ddc-434b-a1d5-5eaf8722fe43" />


The application consists of several backend microservices, an API Gateway, and a React/Next.js frontend.  
Microservices provide REST APIs and are deployed together using Docker Compose.  
Each microservice follows a **multi-layered architecture** pattern.

- **Relational DB** – Stores data in three distinct schemas accessed by the `User`, `Product`, and `Order` microservices.  
- **User MS** – Provides user account management, authentication, and registration.  
- **Product MS** – Manages the product catalog and creates Stripe sessions for order payments.  
- **Order MS** – Handles existing orders and stores new ones based on Stripe webhooks.  
- **Stripe** – Third-party service responsible for processing and confirming customer payments.  
- **API Gateway** – Manages authentication and aggregates endpoints from all backend services under a single URL.  
- **Web Server** – Handles server-side rendering of pages and retrieves data via the API Gateway.  
- **Client** – Represents the customer’s browser, which fetches pages from the Web Server or directly via the API Gateway during client-side rendering.  
- **Admin Postman** – Represents the Postman tool used by the administrator for API testing and system management.

---

## Technologies Used

### Backend
- **Java 21** – Core backend language  
- **Spring Boot 3** – Framework used to develop backend
- **Spring Security + JWT** – Authentication and authorization across services  
- **Spring Data JPA (Hibernate)** – ORM layer for database access  
- **PostgreSQL 17** – Relational database with separate schemas for each microservice  
- **Stripe API** – Payment processing via checkout sessions and webhooks  
- **Spring Cloud Gateway** – Central API gateway handling routing and authentication  
- **Lombok** – Reduces boilerplate code for entities and DTOs  
- **Swagger UI** – API documentation for REST endpoints  
- **Docker & Docker Compose** – Containerized deployment for all backend services  

---

### Frontend
- **Next.js 15.3** – Framework supporting SSR and CSR for building a fast and SEO-friendly UI  
- **React 19** – Component-based library for building dynamic user interfaces  
- **TypeScript** – Strong typing for maintainable and safe frontend code  
- **TanStack Query** – Efficient data fetching and state synchronization with the backend  
- **Zustand** – Lightweight state management for React  
- **Swiper** – Modern and flexible carousel/slider component 

---

### Tools
- **Enterprise Architect** – Modeling of diagrams for documentation  
- **IntelliJ IDEA** – IDE used for backend development  
- **WebStorm** – IDE used for frontend development  
- **Git & GitHub** – Version control and repository management (Gitflow workflow)  
- **Postman** – Manual testing of REST endpoints  
- **Swagger UI** – Visual documentation of API endpoints

---

## Prerequisites
- Docker and Docker Compose (backend)
- Node.js 18 or newer (frontend)
- Stripe secret key (to communicate with Stripe)
- Stripe webhook key (to receive Stripe webhooks)
- Stripe CLI (to forward stripe webhooks to Docker)


---

## Short Demo Video
[![Demo Video](https://img.youtube.com/vi/OSJdZPZTu_8/0.jpg)](https://youtu.be/OSJdZPZTu_8)
