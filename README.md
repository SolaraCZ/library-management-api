# Library Management REST API

A RESTful API for managing books, authors, and genres built with Java 17, Spring Boot, Spring Data JPA, and an in-memory H2 database.

---

## Features

* Relational domain mapping (One-to-Many and Many-to-One) for Authors, Genres, and Books.
* Multi-parameter search filtering via custom JPQL (`/api/books?authorId=1&genreId=2`).
* Centralized exception handling via `@RestControllerAdvice` returning standardized JSON envelopes.
* Payload validation via Bean Validation (`@Valid`, `@NotNull`, `@NotBlank`).
* Unit test coverage for the business service layer using JUnit 5 and Mockito.

---

## Tech Stack

* Java 17
* Spring Boot 3
* Spring Data JPA / Hibernate
* H2 In-Memory Database
* JUnit 5 & Mockito
* Apache Maven

---

## Architecture Overview

```
src/main/java/cz/solaracz/library_management_api/
├── config/        # Database initialization & sample data seeding
├── controller/    # REST endpoints
├── dto/           # Request and response transfer objects
├── exception/     # Global exception handlers and error schemas
├── model/         # JPA entities (Author, Book, Genre)
├── repository/    # Spring Data repositories with custom JPQL queries
└── service/       # Business logic layer
```

---

The application runs on `http://localhost:8080`.

### Database Console
Access the in-memory H2 database interface:
* URL: `http://localhost:8080/h2-console`
* JDBC URL: `jdbc:h2:mem:librarydb`
* Username: `sa`
* Password: *(leave blank)*

---

## API Endpoints

### Books
* `GET /api/books` - Retrieve all books
* `GET /api/books?authorId={id}` - Filter books by author ID
* `GET /api/books?genreId={id}` - Filter books by genre ID
* `GET /api/books?authorId={id}&genreId={id}` - Combined author and genre filter
* `GET /api/books/{id}` - Retrieve single book by ID
* `POST /api/books` - Create new book
* `PUT /api/books/{id}` - Update existing book
* `DELETE /api/books/{id}` - Remove book by ID

### Authors
* `GET /api/authors` - Retrieve all authors
* `GET /api/authors/{id}` - Retrieve author by ID
* `POST /api/authors` - Create author
* `DELETE /api/authors/{id}` - Remove author

### Genres
* `GET /api/genres` - Retrieve all genres
* `GET /api/genres/{id}` - Retrieve genre by ID
* `POST /api/genres` - Create genre
* `DELETE /api/genres/{id}` - Remove genre

---

## Error Response Format

All API errors return a uniform JSON schema:

```json
{
  "timestamp": "2026-03-31T14:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Input payload contains validation errors",
  "path": "/api/books",
  "validationErrors": {
    "title": "Title is required",
    "authorId": "Author ID is required"
  }
}
```

---

## License

MIT License.