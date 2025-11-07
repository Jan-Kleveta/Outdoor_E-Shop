# Outdoor E-Shop

A bachelor’s thesis project focused on the **design and implementation of an e-commerce system** for selling outdoor clothing.  
The project demonstrates a **modular, scalable microservice architecture**, including user management, product administration, order processing, and online payments.

---

## Project Overview

This project was developed as part of my **Bachelor’s Thesis at the Czech Technical University in Prague (CTU FEL, 2025)**.  
It implements a **minimal viable product (MVP)** of an e-shop for outdoor clothing, supporting:

- Product browsing and administration  
- Order creation and management  
- User registration and authentication  
- Secure payments via **Stripe**  
- Scalable, containerized microservices using **Docker**

---

## SW Analysis

All analytical documentation, including the **goals** and the **requirements specification**,  
is available in the full thesis document [`thesis.pdf`](./thesis.pdf) included in this repository.
On the diagram below, there are all defined Use-Cases, for their detailed description, refer to thesis mentioned above.


<img width="1773" height="811" alt="Use-Case-Diagrams" src="https://github.com/user-attachments/assets/869625cb-d72e-4c86-8c64-4df16f137df4" />

---

## System Architecture

The application consists of several backend microservices, an API Gateway, and a React/Next.js frontend.  
All services communicate via REST APIs and are deployed together using Docker Compose.  
Each microservice follows a **multi-layered architecture** pattern (`Controller → Service → Repository`).

<img width="1164" height="721" alt="Component-diagram" src="https://github.com/user-attachments/assets/5aea72ec-1ddc-434b-a1d5-5eaf8722fe43" />

### Component Overview

The components built on top of analytical domain models, together with additional system modules,  
are illustrated in the **component diagram** above. Their purpose is described below:

- **Relational DB** – Stores data in three distinct schemas accessed by the `User`, `Product`, and `Order` microservices. The schemas correspond to the class diagrams described in the thesis.  
- **User MS** – Provides user account management, authentication, and registration.  
- **Product MS** – Manages the product catalog and creates Stripe sessions for order payments.  
- **Order MS** – Handles existing orders and stores new ones based on Stripe webhooks.  
- **Stripe** – Third-party service responsible for processing and confirming customer payments.  
- **API Gateway** – Manages authentication and aggregates endpoints from all backend services under a single URL.  
- **Web Server** – Handles server-side rendering of pages and retrieves data via the API Gateway.  
- **Client** – Represents the customer’s browser, which fetches pages from the Web Server or directly via the API Gateway in client-side rendering.  
- **Admin Postman** – Represents the Postman tool used by the administrator for API testing and system management.

---

## Technologies Used

### Backend
- **Java 21** – Core backend language ensuring reliability and scalability  
- **Spring Boot 3** – Framework for building modular microservices  
- **Spring Security + JWT** – Authentication and authorization across services  
- **Spring Data JPA (Hibernate)** – ORM layer for database access  
- **PostgreSQL 17** – Relational database with separate schemas for each microservice  
- **Stripe API** – Payment processing via checkout sessions & webhooks  
- **Spring Cloud Gateway** – Central API gateway handling routing and authentication  
- **Lombok** – Reduces boilerplate code for entities and DTOs  
- **Swagger UI** – API documentation for REST endpoints  
- **Docker & Docker Compose** – Containerized deployment for all services  

---

### Frontend
- **Next.js 14** – Framework supporting SSR and CSR for a fast and SEO-friendly UI  
- **React 18** – Component-based library for building dynamic user interfaces  
- **TypeScript** – Strong typing for maintainable and safe frontend code  
- **Axios** – HTTP client for communication with backend API Gateway  
- **Tailwind CSS** – Utility-first CSS framework for styling and layout  

---

### Tools
- **IntelliJ IDEA** - IDE used for BE development
- **Webstorm** - IDE used for FE development
- **Git & GitHub** – Version control and repository management (Gitflow workflow)  
- **Postman** – Manual testing of REST endpoints  
- **Swagger UI** – Visual documentation of API endpoints
- **Enterprise Architect** Modelling of diagrams for documentation

---

