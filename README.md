# 🛍️ Spring Boot E-Commerce Backend

A fully functional **e-commerce backend** built with **Spring Boot**, featuring **JWT-based Authentication**, **Role-Based Authorization**, **Secure Payments with Stripe**.

---

## 🔐 Security & Authentication (Priority Features)

### 1. Spring Security 6
This project uses **Spring Security 6** for a modern, modular, and declarative security configuration.

- Stateless session management for REST APIs    
- Centralized security configuration via `SecurityFilterChain`    
- CSRF disabled for non-browser clients    
- Annotation authorization with `@PreAuthorize` and role checks  
  
💳 Stripe Payment Integration  
-Integrated with Stripe for secure and real-world payment processing.  
-Create and confirm payments using Stripe API  
-Integrated into checkout workflow   

🛒 Cart & Inventory Management  

-Users can manage their cart and see live inventory updates.  
-Add / remove products from cart  
-Update quantities  
-Automatic stock synchronization after checkout  

🎟️ Coupon System  
-Admin can create discount coupons that users can apply during checkout.  
-Percentage or fixed amount discounts  
-Validation for expiration and usage limits  

📦 Package by Feature  
-The project follows the Package by Feature structure for better scalability and modularity  
src/main/java/com/example/ecommerce  
 ┣ auth/  
 ┣ cart/  
 ┣ coupon/  
 ┣ payment/  
 ┣ product/  
 ┣ user/  
 ┣ common/  
 ┗ config/  

| Layer              | Technology                       |  
| ------------------ | -------------------------------- |  
| **Backend**        | Spring Boot 3, Spring Security 6 |  
| **Authentication** | JWT, Role-Based Authorization    |  
| **Database**       | PostgreSQL                       |  
| **Messaging**      | Apache Kafka                     |  
| **Payment**        | Stripe API                       |  
| **Mapping**        | MapStruct                        |  
| **Build Tool**     | Maven                            |  
| **Deployment**     | Docker github pipelines          |  


<img src="https://github.com/user-attachments/assets/edcf304d-f42e-4a7e-867b-3f9fb480728a" width=50% height=50%>

Frontend: https://github.com/furkancan0/ecommerce  
# Project Idea  
https://roadmap.sh/projects/ecommerce-api
