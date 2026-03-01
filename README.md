ContactPro – Intelligent Relationship Management System
Overview

ContactPro is a web-based relationship management backend system built using Spring Boot and PostgreSQL.
It provides structured contact management, follow-up tracking, task management, and relationship analytics.

Unlike traditional contact storage systems, ContactPro focuses on intelligent relationship tracking and automation.

This project is developed as part of internship experience and backend learning journey.

Tech Stack

Backend:

Java 17+

Spring Boot 4

Spring Data JPA (Hibernate)

Spring Security (JWT – upcoming)

Maven

Database:

PostgreSQL

Testing:

Postman / Thunder Client

Architecture

The project follows a layered architecture:

Controller → Service → Repository → Database

Controller handles HTTP requests

Service contains business logic

Repository handles database operations

Entity maps Java classes to database tables

DTO is used for secure API communication

Project Structure
com.contactpro.contactpro
├── controller
├── service
├── repository
├── model
├── dto
├── config
├── security
├── exception
└── ContactproApplication.java
Features (Current Phase)
User Module

Create User

Get All Users

Entity-based table creation

DTO-based API structure

Layered architecture

Contact Module (In Progress)

One-to-Many relationship with User

Contact entity with foreign key mapping

Category, notes, block/favorite flags

Database Schema
Users Table

id (Primary Key)

name

email (Unique)

password

created_at

Contacts Table

id (Primary Key)

user_id (Foreign Key)

name

phone

email

category

notes

is_blocked

is_favorite

created_at

How To Run The Project

Clone repository

Install PostgreSQL

Create database:

contactpro

Update application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/contactpro
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update

Run:

mvn spring-boot:run

Test APIs using Postman.
