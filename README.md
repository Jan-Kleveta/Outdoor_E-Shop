# Outdoor E-Shop

A bachelor’s thesis project focused on the **design and implementation of an e-commerce system** for selling outdoor clothing.  
The project demonstrates a **modular, scalable microservice architecture**, including user management, product administration, order processing, and online payments.

---

## Project Overview

This project was developed as part of my **Bachelor’s Thesis at the Czech Technical University in Prague (CTU FEL, 2025)**.  
It implements a minimal viable product (MVP) of an e-shop for outdoor clothing, supporting:

- Product browsing and administration  
- Order creation and management  
- User registration and authentication  
- Secure payments via **Stripe**  
- Scalable, containerized microservices using **Docker**

All analytical documentation, including the **requirements specification** and **use case diagrams**,  
is available in the full thesis document [`thesis.pdf`](./thesis.pdf) included in this repository.

---

## System Architecture

The application is composed of several backend microservices, an API gateway, and a React/Next.js frontend.  
All services communicate over REST APIs and are deployed together via Docker Compose.

<img width="1164" height="721" alt="Component-MVP" src="https://github.com/user-attachments/assets/5aea72ec-1ddc-434b-a1d5-5eaf8722fe43" />

The components built on top of analytical domain models, together with additional system components,  
are shown in the **component diagram** included in this repository.  
Their purpose is described below:

- **Relational DB** – Stores data in three distinct schemas accessed by the `User`, `Product`, and `Order` microservices. The schemas correspond to the class diagrams described in the thesis.  
- **User MS** – Provides user account management, authentication, and registration.  
- **Product MS** – Manages product catalog and creates Stripe sessions for order payment.  
- **Order MS** – Handles existing orders and stores new ones based on Stripe webhooks.  
- **Stripe** – Third-party service responsible for processing and confirming customer payments.  
- **API Gateway** – Manages authentication and aggregates endpoints from `User`, `Product`, and `Order` services under a single URL.  
- **Web Server** – Responsible for server-side rendering of pages, fetching necessary data via the API Gateway.  
- **Client** – Represents the customer’s browser, rendering pages from the Web Server and fetching data directly via the API Gateway when client-side rendering is used.  
- **Admin Postman** – Represents the Postman tool used by the administrator for system testing and API management.

<img width="1633" height="850" alt="image" src="https://github.com/user-attachments/assets/92b341e9-5a68-4087-af41-c6cb2b1067c6" />


