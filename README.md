# 💖 VLSI Chatbot - Spring Boot Edition

A modern Spring Boot web application that serves as an intelligent VLSI (Very Large Scale Integration) Q&A chatbot with user authentication and SQLite database storage.

## ✨ Features

- 🎯 **50+ VLSI Q&A Pairs** - Comprehensive knowledge base about VLSI, semiconductors, and chip design
- 🧠 **Smart Fuzzy Matching** - Intelligent question matching using token-based similarity
- 💬 **Real-time Chat Interface** - Beautiful, responsive chat UI
- 🔐 **Secure Authentication** - User registration and login with Spring Security
- 💾 **SQLite Database** - Lightweight persistent storage for users, Q&A, and chat history
- 📱 **Responsive Design** - Works on desktop and mobile devices

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: SQLite with Hibernate ORM
- **Security**: Spring Security with BCrypt password hashing
- **Frontend**: Thymeleaf templates, vanilla JavaScript, CSS3
- **Build**: Maven

## 📁 Project Structure

```
├── pom.xml                          # Maven configuration
├── src/main/java/com/vlsi/chatbot/
│   ├── ChatbotApplication.java      # Main Spring Boot application
│   ├── config/
│   │   ├── DatabaseConfig.java      # SQLite DataSource configuration
│   │   └── SecurityConfig.java      # Spring Security configuration
│   ├── controller/
│   │   ├── AuthController.java      # REST API for authentication
│   │   ├── ChatController.java      # REST API for chat
│   │   └── PageController.java      # Thymeleaf page controllers
│   ├── dto/                         # Data Transfer Objects
│   ├── entity/                      # JPA Entities (User, QAEntry, ChatMessage)
│   ├── repository/                  # Spring Data JPA Repositories
│   └── service/                     # Business logic services
├── src/main/resources/
│   ├── application.properties       # Application configuration
│   ├── vlsi_faq.json               # Q&A data source
│   ├── static/
│   │   ├── css/styles.css          # Stylesheet
│   │   └── js/chat.js              # Chat JavaScript
│   └── templates/                   # Thymeleaf HTML templates
└── files/                          # File storage directory
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

1. **Clone or download the project**

2. **Navigate to the project directory**
   ```bash
   cd "Java Project"
   ```

3. **Build and run with Maven**
   ```bash
   mvn spring-boot:run
   ```

4. **Open your browser**
   ```
   http://localhost:8080
   ```

### Building a JAR

```bash
mvn clean package
java -jar target/chatbot-1.0.0.jar
```

## 📖 Usage

1. **Register** - Create a new account
2. **Login** - Sign in with your credentials
3. **Chat** - Ask questions about VLSI topics:
   - "What is VLSI?"
   - "What is Moore's Law?"
   - "Difference between ASIC and FPGA"
   - "What is CMOS technology?"
   - "time" or "date" for current time/date
   - "get file <filename>" to read files

## 🔧 Configuration

Edit `src/main/resources/application.properties`:

```properties
# Server port
server.port=8080

# Database (SQLite)
spring.datasource.url=jdbc:sqlite:chatbot.db

# Session timeout
server.servlet.session.timeout=30m
```

## 📊 Database Tables

- **users** - User accounts (username, password hash, timestamps)
- **qa_entries** - VLSI questions and answers
- **chat_messages** - Chat history per user

## 🔐 Security

- Passwords are hashed using BCrypt
- Session-based authentication
- Protected chat routes require authentication

## 📝 API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/` | GET | Home page |
| `/login` | GET | Login page |
| `/register` | GET | Registration page |
| `/chat` | GET | Chat page (authenticated) |
| `/api/register` | POST | Register new user |
| `/api/chat` | POST | Send chat message |
| `/api/whoami` | GET | Get current user |
| `/auth/login` | POST | Login |
| `/auth/logout` | POST | Logout |

## 🎨 Screenshots

The application features a beautiful pink-themed UI with:
- Gradient backgrounds
- Rounded cards and buttons
- Smooth hover animations
- Chat bubbles for messages

## 📄 License

This project is open source and available for educational purposes.

---

Made with 💖 for VLSI enthusiasts!
