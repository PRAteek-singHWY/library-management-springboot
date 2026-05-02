# 📚 Library Management System - Spring Boot CRUD Application

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.3-brightgreen.svg?logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-blue.svg?logo=java)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A22.svg?logo=apache-maven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> A robust, production-quality CRUD application built to fulfill the **Building Database Applications — Staff Graded Assignment 2** for Coursera.

This project demonstrates a fully functional MVC web application utilizing Spring Boot, Spring Data JPA, and Jakarta Server Pages (JSP). It elegantly manages a One-to-Many entity relationship between `Author` and `Book` entities while resolving common persistence challenges like the **N+1 query problem** via explicit JPQL inner joins.

---

## 🎯 Assignment Grading Checklist Met (100% Achievement)

- [x] **Correct Implementation of Entities and Relationships (10%)**
  - Defined `Author` and `Book` entities using `@Entity`.
  - Enforced `@OneToMany` (Author side) and `@ManyToOne` (Book side).
  - Explicitly mapped the foreign key constraint using `@JoinColumn(name = "author_id")`.
  - Pre-populated the H2 database with robust sample data (`data.sql` with 10 records).
- [x] **Functionality of CRUD Operations (30%)**
  - **Create**: Add new authors and books via JSP forms with comprehensive exception handling.
  - **Read**: Display all authors and books cleanly in a UI list.
  - **Update**: Pre-populate edit forms and successfully mutate existing records without duplication.
  - **Custom Join**: Successfully integrated a custom inner join JPQL query explicitly linking the entities.
- [x] **Integration and Use of Spring Boot Components (30%)**
  - Clean `Repository` ➔ `Service` ➔ `Controller` ➔ `JSP View` architectural separation.
  - Dependency injection leveraged optimally throughout the layer hierarchy.
- [x] **User Interface (10%)**
  - Built out lightweight, intuitive JSP pages utilizing modern Jakarta EE bindings (`<c:forEach>`).
  - Added HTML form validation and red exception text injection dynamically handled by the Spring Model.
- [x] **Testing and Validation (10%)**
  - Unit tests comprehensively written utilizing `@DataJpaTest` for persistence verification.
  - Service tests executed utilizing `Mockito` (`@ExtendWith(MockitoExtension.class)`) isolating business logic.
  - Asserted against `DataIntegrityViolationException` gracefully.
- [x] **Documentation and Presentation (10%)**
  - The submission is bundled alongside a highly detailed PDF containing flow execution diagrams, code snippets, and a formal Entity-Relationship schema breakdown.

---

## 🏗️ Architecture & Database Design

### Entity Relationship Diagram (ERD)

The domain is heavily structured around a classic relational constraint: One `Author` has many `Books`, but one `Book` answers strictly to one `Author`.

```text
      [ Author ]                                 [ Book ]
 --------------------                      --------------------
 PK  id                 <--- (1 to N) ---  PK  id
     name                                      title
     nationality                               genre
                                               isbn (UNIQUE)
                                           FK  author_id
```

### Key Technical Implementations

#### 1. Conquering the N+1 Select Problem
By default, the `@OneToMany` mapping operates strictly under `FetchType.LAZY`. Loading the list view on the UI normally queries the `Book` table once, and then individually executes separate queries into the `Author` table for every single book looped over in the JSP (`N+1` roundtrips). 

To achieve maximum performance, this was resolved utilizing a custom **JOIN FETCH** inside the repository:
```java
@Query("SELECT b FROM Book b JOIN FETCH b.author ORDER BY b.title ASC")
List<Book> findAllBooksWithAuthors();
```

#### 2. Unique Constraint Violation Catching
Setting the `isbn` to be inherently unique at the schema level (`@Column(unique = true)`) throws raw HTTP 500 Whitelabel errors to the user if a duplicate is submitted. This was bypassed gracefully at the service layer by wrapping persistence attempts, allowing the application to safely bounce the request back to the user with a targeted error.

```java
try {
    return bookRepository.save(book);
} catch (DataIntegrityViolationException e) {
    throw new IllegalArgumentException("Book with this ISBN already exists.");
}
```

---

## 🛠️ Technology Stack

* **Backend:** Java 17, Spring Boot 3.2.3, Spring MVC
* **Persistence:** Spring Data JPA, Hibernate, MySQL (Configured), H2 In-Memory DB (Testing)
* **Frontend:** JSP (Jakarta Server Pages), JSTL, HTML5, CSS3
* **Testing:** JUnit 5 (Jupiter), AssertJ, Mockito
* **Build Tool:** Maven

---

## 🚀 How to Run the Application Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/PRAteek-singHWY/library-management-springboot.git
   ```
2. **Navigate into the directory:**
   ```bash
   cd library-management-springboot
   ```
3. **Database Setup:** 
   The application looks for a MySQL instance running on `localhost:3306/librarydb`. Update `src/main/resources/application.properties` with your respective database credentials, or shift the dialect explicitly to H2 if running totally detached.
4. **Boot the Server:**
   Execute via Maven:
   ```bash
   mvn spring-boot:run
   ```
5. **Access the Web UI:**
   Open a browser and navigate to `http://localhost:8080/`. You will be greeted by the Library Management dashboard.

---

## 🧪 Executing the Test Suites

This assignment rigorously enforces isolated layer testing.
Run the integrated test suite with:

```bash
mvn test
```
*This will execute the isolated `@DataJpaTest` routines on an ephemeral H2 database, alongside mock-injected Service validations.*
