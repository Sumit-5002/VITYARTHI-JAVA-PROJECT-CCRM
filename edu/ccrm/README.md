A comprehensive Java SE console application for managing students, courses, enrollments, and grades in an educational institution.

## Project Overview

CCRM is a feature-rich academic management system that demonstrates modern Java programming concepts including:
- Object-Oriented Programming (OOP) principles
- Design patterns (Singleton, Builder)
- Modern Java I/O with NIO.2
- Stream API and functional programming
- Exception handling and custom exceptions
- File operations and data persistence

## How to Run

### Prerequisites
- JDK 17 or higher
- Eclipse IDE (recommended) or any Java IDE

### Installation Steps
1. Clone or download the project
2. Import into Eclipse as existing Java project
3. Ensure project is configured for Java 17+
4. Run the Main class: `edu.ccrm.Main`

### Command Line Usage
```bash
# Compile the project
javac -d out src/edu/ccrm/**/*.java

# Run the application
java -cp out edu.ccrm.Main

# Run with assertions enabled
java -ea -cp out edu.ccrm.Main

# Show version
java -cp out edu.ccrm.Main --version

# Show help
java -cp out edu.ccrm.Main --help
```

## Evolution of Java

- **1995**: Java 1.0 - Initial release by Sun Microsystems
- **1997**: Java 1.1 - Added inner classes, JDBC, RMI
- **1998**: Java 1.2 (J2SE) - Collections framework, Swing
- **2000**: Java 1.3 - HotSpot JVM, JNDI
- **2002**: Java 1.4 - NIO, logging, assertions, regex
- **2004**: Java 5 - Generics, annotations, enums, autoboxing
- **2006**: Java 6 - Performance improvements, scripting
- **2011**: Java 7 - Try-with-resources, diamond operator
- **2014**: Java 8 - Lambda expressions, Stream API, Optional
- **2017**: Java 9 - Modules, JShell, private interface methods
- **2018**: Java 10 - Local variable type inference (var)
- **2018**: Java 11 (LTS) - HTTP client, string methods
- **2019**: Java 12-13 - Switch expressions, text blocks preview
- **2020**: Java 14-15 - Records preview, pattern matching
- **2021**: Java 16-17 (LTS) - Records, sealed classes, pattern matching

## Java Platform Editions

| Edition | Description | Use Case | Components |
|---------|-------------|----------|------------|
| **Java ME** (Micro Edition) | Lightweight platform for mobile and embedded devices | IoT devices, mobile apps, smart cards | CDC, CLDC, MIDP |
| **Java SE** (Standard Edition) | Core Java platform for desktop and server applications | Desktop apps, console applications, development tools | JVM, Core APIs, Development tools |
| **Java EE** (Enterprise Edition) | Enterprise platform for large-scale applications | Web applications, enterprise services, distributed systems | Servlets, JSP, EJB, JMS, JPA |

## Java Architecture

### JDK (Java Development Kit)
- Complete development environment
- Includes JRE + development tools (javac, jar, javadoc)
- Required for Java development

### JRE (Java Runtime Environment)  
- Runtime environment for Java applications
- Includes JVM + core libraries
- Required to run Java applications

### JVM (Java Virtual Machine)
- Executes Java bytecode
- Platform-specific implementation
- Provides memory management, garbage collection, security

**Interaction Flow**: Java Source → javac (JDK) → Bytecode → JVM (JRE) → Native Machine Code

## Project Structure

```
src/
└── edu/ccrm/
    ├── cli/           # Menu system and user interface
    ├── config/        # Application configuration (Singleton)
    ├── domain/        # Entity classes (Student, Course, etc.)
    ├── exceptions/    # Custom exception classes
    ├── io/            # Import/Export services (NIO.2)
    ├── service/      # Business logic services
    ├── util/         # Utility classes and validators
    └── Main.java     # Application entry point

test-data/            # Sample CSV files for testing
screenshots/          # Documentation screenshots  
exports/              # Exported data files
backups/              # Backup directories
```

## Technical Requirements Mapping

| Syllabus Topic | Implementation Location | Description |
|----------------|------------------------|-------------|
| **OOP Principles** | | |
| Encapsulation | `domain/*.java` | Private fields with getters/setters |
| Inheritance | `domain/Person.java` → `Student.java`, `Instructor.java` | Abstract base class with concrete implementations |
| Abstraction | `domain/Person.java`, `service/*.java` interfaces | Abstract classes and interfaces |
| Polymorphism | `service/DataService.java`, method overriding | Runtime polymorphism and interface implementation |
| **Design Patterns** | | |
| Singleton | `config/AppConfig.java` | Thread-safe singleton with double-checked locking |
| Builder | `domain/Course.java` | Fluent builder pattern for complex object creation |
| **Advanced Features** | | |
| Enums | `domain/Grade.java`, `domain/Semester.java` | Enums with constructors and methods |
| Generics | `service/Searchable.java`, `service/DataService.java` | Generic interfaces and type safety |
| Lambda Expressions | `service/StudentService.java` | Functional programming with streams |
| Stream API | `service/*.java`, `io/ImportExportService.java` | Data processing and filtering |
| NIO.2 | `io/ImportExportService.java`, `util/FileUtils.java` | Modern file I/O operations |
| Exception Handling | `exceptions/*.java` | Custom checked and unchecked exceptions |
| **Language Features** | | |
| Switch Expressions | `cli/MenuSystem.java` | Enhanced switch with arrow syntax |
| Try-with-resources | `io/ImportExportService.java` | Automatic resource management |
| Assertions | `Main.java` | Runtime assertion checks |
| Recursion | `util/FileUtils.java` | Recursive directory operations |

## Enabling Assertions

Assertions are used for invariant checking and debugging:

```bash
# Enable assertions for all classes
java -ea edu.ccrm.Main

# Enable assertions for specific package
java -ea:edu.ccrm... edu.ccrm.Main

# Enable system assertions
java -esa edu.ccrm.Main
```

## Sample Commands

### Import Data
```
1 → 5 → 1 → students.csv
1 → 5 → 2 → courses.csv
```

### Add Student
```
1 → 1 → Enter student details
```

### Enroll Student in Course
```
3 → 1 → Enter student ID and course code
```

### Generate Reports
```
7 → Various report options
```

### Backup Data
```
6 → 1 → Creates timestamped backup
```

## Features Demonstrated

### Core Java Concepts
-  Primitive variables and operators (arithmetic, relational, logical, bitwise)
-   Decision structures (if/else, nested if, switch)
-   Loop constructs (while, do-while, for, enhanced for)
-   Jump controls (break, continue, labeled break)
-   Arrays and Arrays utility class
-   String manipulation methods

### Object-Oriented Programming
-   Classes and objects with proper encapsulation
-   Inheritance hierarchy with abstract base classes
-   Method overriding and overloading
-   Polymorphism with interface implementations
-   Access modifiers (private, protected, public, default)

### Advanced Features
-   Interfaces with default methods (diamond problem resolution)
-   Anonymous inner classes and lambda expressions
-   Functional interfaces and method references
-   Generic types and collections
-   Exception handling with try-catch-finally
-   Custom checked and unchecked exceptions

### Modern Java APIs
-   Date/Time API for timestamps
-   NIO.2 for file operations
-   Stream API for data processing
-   Optional for null safety

## Error Handling

The application implements comprehensive error handling:

### Errors vs Exceptions
- **Errors**: Serious problems that applications shouldn't catch (OutOfMemoryError, StackOverflowError)
- **Exceptions**: Conditions that applications can handle (IOException, IllegalArgumentException)

### Exception Types Used
- **Checked Exceptions**: `DuplicateEnrollmentException`, `IOException`
- **Unchecked Exceptions**: `MaxCreditLimitExceededException`, `IllegalArgumentException`

### Exception Handling Patterns
- Try-with-resources for automatic resource management
- Multi-catch blocks for similar exception handling
- Custom exception classes with detailed error information

## Screenshots

Screenshots demonstrating the application are stored in the `screenshots/` folder:
- JDK installation verification
- Eclipse project setup
- Application running with menu navigation
- Import/Export operations
- Backup folder structure

## Contributing

1. Fork the repository
2. Create a feature branch
3. Implement changes following Java coding standards
4. Add appropriate tests and documentation
5. Submit a pull request

## License

This project is created for educational purposes as part of Java programming coursework.
