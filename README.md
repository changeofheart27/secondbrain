# SecondBrain
***
**SecondBrain** is a Personal Knowledge Manager where you can easily store and organize notes, tag them, and track associated events. This project helps you manage your personal knowledge base with powerful search capabilities, making it easier to find and track relevant information over time.

## Tech Stack
- **Kotlin**: a modern programming language that runs on the Java Virtual Machine (JVM) and fully interoperable with Java.
- **Spring Boot**: a framework built on top of the Spring Framework that simplifies the process of creating production-ready applications.
- **Spring Data JPA**: a part of the larger Spring Data project and provides an easy way to interact with databases using Java Persistence API.
- **PostgreSQL**: an open-source relational database management system (RDBMS) known for its reliability, data integrity, and support for advanced features like JSONB data types and full-text search.
- **Mockk**: a mocking framework specifically designed for Kotlin. It simplifies the process of creating mocks and stubs for unit testing.
- **springmockk**: a specialized integration library that seamlessly combines the power of Mockk with Spring's testing framework.

## Project Structure
```
secondbrain/
├── build.gradle.kts                             # Gradle build script for project configuration and dependencies management
├── settings.gradle.kts                          # Gradle entry point script to define project structure
├── src/main/kotlin/
│   ├── resources                                # application configurations, SQL scripts...
│   └── com/hieuduongmanh/secondbrain
│       ├── controller                           # REST API endpoints
│       ├── dto/                                 # DTO classes
│           ├── mapper                           # mapper methods between entity and DTO
│           ├── request                          # DTO classes for request specific
│           └── response                         # DTO classes for response specific
│       ├── entity/                              # entities representing database tables
│       ├── exception/                           # global exception handlers
│       ├── repository                           # database interaction
│       ├── service                              # business logic handling
│       └── SecondbrainApplication.kt            # main class to run application
```