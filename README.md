# Academy Accounting

A full-stack web application for managing an English academy's operations, including student enrollment, course management, payment tracking, and financial reporting.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![License](https://img.shields.io/badge/license-MIT-blue)

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

Academy Accounting is a comprehensive management system built for a real-world English academy. The project showcases a **dual-architecture approach**: a RESTful API backend that can be used by any client, coupled with a Thymeleaf-based web interface for immediate, standalone functionality.

This hybrid approach demonstrates:
- **Backend-first design**: Core business logic exposed through REST endpoints
- **Pragmatic delivery**: Web UI for immediate usability without requiring separate frontend development
- **Real-world application**: Actively used by an English academy for daily operations

## ✨ Features

### Student Management
- Student registration with family grouping support
- Automatic family discount calculation (2+ siblings)
- Student profile management with contact information

### Course Management
- Multi-level course system (from 3-year-olds to advanced adult courses)
- Academic year organization
- Flexible pricing structure

### Enrollment System
- Course enrollment with automatic payment schedule generation
- 10-month payment plan (March-December)
- Family discount integration

### Payment Tracking
- Monthly payment management
- Payment status monitoring (Pending, Overdue, Paid)
- Outstanding payment reports
- Payment history tracking

### Exam Management
- Exam scheduling and registration
- Student exam enrollment
- Exam fee tracking

### Reporting
- Monthly revenue reports
- Outstanding payments summary
- Dashboard with key metrics

## 🛠 Technology Stack

### Backend
- **Java 21** - Latest LTS version with modern language features
- **Spring Boot 3.x** - Application framework
- **Spring Web** - RESTful API implementation
- **Spring Data JPA** - Data persistence layer
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management and build tool

### Frontend
- **Thymeleaf** - Server-side template engine
- **Bootstrap 5.3** - Responsive UI framework
- **Bootstrap Icons** - Icon library

### Database
- **H2** - In-memory database for local development
- **MySQL** - Production-ready relational database (configured, not currently active)

### Development Tools
- **IntelliJ IDEA** - IDE
- **Git** - Version control

## 🏗 Architecture

The project implements a **dual-architecture pattern**:

1. **RESTful API Layer**: Fully functional REST endpoints for programmatic access
2. **Web Interface Layer**: Thymeleaf-based UI consuming the same business logic

This approach provides:
- API-first design for future mobile apps or external integrations
- Immediate usability through the web interface
- Shared business logic ensuring consistency
- Easy switching between H2 (development) and MySQL (production)

### Key Architectural Decisions

**Controller Pattern**: Separate controller packages for API and web endpoints
- `controller.web.*` - Thymeleaf-based controllers returning views
- `controller.api.*` - RESTful controllers returning JSON (future implementation)

**Service Layer**: Business logic isolated from presentation concerns

**Repository Pattern**: Spring Data JPA repositories for data access

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- Git

### Installation

1. Clone the repository
```bash
git clone https://github.com/Caliburno/academy-accounting.git
cd academy-accounting
```

2. Build the project
```bash
mvn clean install
```

3. Run the application (Windows)
```bash
run.bat
```

The application will start on `http://localhost:8080` with an in-memory H2 database.

### Configuration

#### H2 Database (Default - Development)
The application is pre-configured to use H2 for easy local development:
- Console available at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: _(empty)_

#### MySQL Database (Production)
To switch to MySQL, update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/academy_accounting
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## 📱 Usage

### Dashboard
Access the main dashboard at `http://localhost:8080/` to view:
- Total students, courses, and enrollments
- Pending and overdue payment counts
- Quick action buttons

### Main Workflows

1. **Set Up Academic Year**
   - Navigate to Academic Years
   - Create and activate the current academic year

2. **Add Students**
   - Go to Students → New Student
   - Optional: Assign to a family group for automatic discounts

3. **Create Courses**
   - Navigate to Courses → New Course
   - Set level and monthly price
   - Link to active academic year

4. **Enroll Students**
   - Go to Enrollments → New Enrollment
   - Select student and course
   - System automatically generates 10 monthly payments

5. **Track Payments**
   - View Payments for current month
   - Mark payments as paid when received
   - Check Outstanding Payments for overdue amounts

6. **Generate Reports**
   - Navigate to Reports
   - Select month/year for revenue analysis

## 📁 Project Structure

```
academy-accounting/
├── src/main/java/io/github/caliburno/academy_accounting/
│   ├── controller/
│   │   └── web/              # Thymeleaf web controllers
│   ├── model/                # Entity classes
│   ├── repository/           # Spring Data repositories
│   ├── service/              # Business logic layer
│   └── dto/                  # Data transfer objects
├── src/main/resources/
│   ├── templates/            # Thymeleaf templates
│   │   ├── layout/           # Layout fragments
│   │   ├── student/          # Student views
│   │   ├── course/           # Course views
│   │   ├── enrollment/       # Enrollment views
│   │   ├── payment/          # Payment views
│   │   ├── exam/             # Exam views
│   │   └── report/           # Report views
│   └── application.properties
└── pom.xml
```

## 🗺 Roadmap

### In Progress
- [ ] Comprehensive unit and integration tests
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Proper REST API endpoints (currently web-only)

### Planned Features
- [ ] Access control for security (username and password)
- [ ] Export reports to PDF/Excel
- [ ] Online server implementation, both for desktop and mobile

### Future Enhancements
- [ ] React/Angular frontend consuming REST API
- [ ] Docker containerization
- [ ] CI/CD pipeline
- [ ] Cloud deployment guides (AWS, Azure, Heroku)

## 👥 Contributing

This is a portfolio project, but suggestions and feedback are welcome! If you'd like to contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 Notes

### Frontend Development
The web interface was built with AI assistance (Claude). While I have backend development experience, the HTML/CSS/Thymeleaf implementation demonstrates leveraging AI tools for full-stack delivery.

### Current Limitations
- **Testing**: Automated tests are not yet implemented
- **Security**: No authentication/authorization system (intended for internal use)
- **API**: RESTful endpoints exist in structure but web controllers are primary implementation
- **Validation**: Basic validation present; comprehensive validation pending

### Why This Stack?
- **Spring Boot**: Industry-standard framework demonstrating enterprise patterns
- **Thymeleaf**: Server-side rendering for rapid development without JavaScript complexity
- **H2**: Zero-configuration database perfect for portfolio demos
- **MySQL**: Production-ready option showing environment flexibility

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👤 Author

**Caliburno**
- GitHub: [@Caliburno](https://github.com/Caliburno)
- Project Link: [https://github.com/Caliburno/academy-accounting](https://github.com/Caliburno/academy-accounting)

## 🙏 Acknowledgments

- Built for a real English academy's operational needs
- Web interface development assisted by Claude AI
- Inspired by the need for accessible, practical business software

---

⭐ If this project helped you, please consider giving it a star!

---

Screenshots:

![Students](https://github.com/user-attachments/assets/ad6537c2-96bf-441c-adfa-713277dd99e7)

![Pending Payments](https://github.com/user-attachments/assets/b0b7c302-c7a3-43d1-9289-b69222c569bb)

![New Exam](https://github.com/user-attachments/assets/21d28d96-f527-4be0-8e33-8dde14bd4bf0)

![New Enrollment](https://github.com/user-attachments/assets/a1c7cdf9-9586-4e16-97cb-fa74baaedc02)

![Monthly Report](https://github.com/user-attachments/assets/cb5df1ad-7d46-451f-96bf-775ed8267bcb)

![Home](https://github.com/user-attachments/assets/c98371f8-4463-48b3-93f6-9f75edf0d74b)

![Courses](https://github.com/user-attachments/assets/238ee348-5d2d-41fb-bc6e-967238fa46ce)

![Academic Year](https://github.com/user-attachments/assets/b7448b08-8cbd-4645-b2e7-a8b060b70be8)

