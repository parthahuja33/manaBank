# Bank Management System

A professional Java-based Bank Management Application built with Swing GUI and MySQL database. This application provides comprehensive banking operations including customer management, account management, and transaction processing.

## 🎯 Project Overview

This Bank Management System is a desktop application designed for bank tellers and administrators to manage customer accounts, process transactions, and maintain banking records. The application follows enterprise-level design patterns and best practices for maintainability and scalability.

## ✨ Features

### Customer Management
- **Create New Account**: Register new customers with comprehensive personal information
- **Modify Customer**: Update customer details and information
- **Delete Customer**: Remove customers and associated accounts (with confirmation)
- **View All Customers**: Browse and search through all registered customers

### Account Operations
- **Account Creation**: Create savings or current accounts with various options
- **Account Types**: Support for Savings and Current accounts
- **Account Services**: Enable/disable SMS alerts, Internet Banking, and ATM cards
- **Account Modes**: Self-operated or Joint account options

### Transaction Management
- **Deposit**: Deposit money into accounts with transaction recording
- **Withdraw**: Withdraw money from accounts with balance validation
- **Transfer**: Transfer funds between accounts with automatic transaction logging
- **Balance Inquiry**: Check account balance and details
- **Account Statement**: View complete transaction history for any account

### Security & Authentication
- **User Authentication**: Secure login system with username and password
- **Role-Based Access**: Support for different user roles (Admin, Teller)
- **Database Security**: Prepared statements to prevent SQL injection

## 🏗️ Architecture Overview

The application follows the **MVC (Model-View-Controller)** architecture pattern combined with **DAO (Data Access Object)** pattern for clean separation of concerns:

### Architecture Layers

1. **Model Layer** (`com.bankmanagement.model`)
   - `Customer`: Represents customer information
   - `Account`: Represents bank account details
   - `Transaction`: Represents financial transactions
   - `User`: Represents system users for authentication

2. **DAO Layer** (`com.bankmanagement.dao`)
   - `CustomerDAO`: Handles all customer-related database operations
   - `AccountDAO`: Manages account database operations
   - `TransactionDAO`: Handles transaction database operations
   - `UserDAO`: Manages user authentication database operations

3. **Controller Layer** (`com.bankmanagement.controller`)
   - `BankController`: Contains business logic for banking operations
   - `AuthController`: Handles authentication logic

4. **View Layer** (`com.bankmanagement.view`)
   - `LoginView`: User authentication interface
   - `MainView`: Main application window with menu system
   - `NewAccountView`: Customer account creation interface
   - `DepositView`, `WithdrawView`, `TransferView`: Transaction interfaces
   - `BalanceView`, `StatementView`: Account inquiry interfaces
   - `ModifyCustomerView`, `DeleteCustomerView`, `ViewCustomersView`: Customer management interfaces

5. **Utility Layer** (`com.bankmanagement.util`)
   - `DatabaseConnection`: Singleton pattern for database connection management

### Design Patterns Used

- **Singleton Pattern**: Database connection management
- **DAO Pattern**: Data access abstraction
- **MVC Pattern**: Separation of business logic, data, and presentation
- **Factory Pattern**: Object creation through controllers

## 🛠️ Tech Stack

- **Language**: Java 11+
- **GUI Framework**: Java Swing
- **Database**: MySQL 8.0+
- **Build Tool**: Maven
- **JDBC**: MySQL Connector/J 8.0.33
- **Testing**: JUnit 5 (optional)

## 📋 Prerequisites

Before running the application, ensure you have the following installed:

1. **Java Development Kit (JDK) 11 or higher**
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - Verify installation: `java -version`

2. **MySQL Server 8.0 or higher**
   - Download from [MySQL Official Website](https://dev.mysql.com/downloads/mysql/)
   - Ensure MySQL service is running

3. **Maven 3.6 or higher**
   - Download from [Maven Official Website](https://maven.apache.org/download.cgi)
   - Verify installation: `mvn -version`

4. **IDE (Optional but recommended)**
   - IntelliJ IDEA
   - Eclipse
   - NetBeans
   - VS Code with Java extensions

## 🚀 Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd manaBank
```

### 2. Database Setup

1. **Start MySQL Server**
   ```bash
   # On Windows
   net start MySQL80
   
   # On Linux/Mac
   sudo systemctl start mysql
   ```

2. **Create Database and Tables**
   - Open MySQL command line or MySQL Workbench
   - Run the SQL script located at `src/main/resources/database_schema.sql`:
   ```bash
   mysql -u root -p < src/main/resources/database_schema.sql
   ```
   Or execute the SQL file manually in MySQL Workbench.

3. **Verify Database Creation**
   ```sql
   USE bankmanagement;
   SHOW TABLES;
   ```

### 3. Configure Database Connection

Update the database connection settings in `src/main/java/com/bankmanagement/util/DatabaseConnection.java` if needed:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/bankmanagement";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password"; // Update with your MySQL password
```

### 4. Build the Project

Using Maven command line:

```bash
# Clean and compile
mvn clean compile

# Package the application
mvn clean package
```

### 5. Run the Application

**Option 1: Using Maven**
```bash
mvn exec:java -Dexec.mainClass="com.bankmanagement.BankManagementApp"
```

**Option 2: Using IDE**
- Import the project as a Maven project
- Run `BankManagementApp.java` as the main class

**Option 3: Using Java Command**
```bash
# After building with Maven
java -cp target/classes:target/dependency/* com.bankmanagement.BankManagementApp
```

## 🔐 Default Login Credentials

The application comes with default user accounts:

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| teller | teller123 | TELLER |

**⚠️ Important**: Change these default passwords in production environments!

## 📊 Database Schema

### Tables

1. **users**: System user accounts for authentication
2. **customers**: Customer personal information
3. **accounts**: Bank account details
4. **transactions**: Transaction records

### Key Relationships

- `customers` → `accounts` (One-to-Many)
- `accounts` → `transactions` (One-to-Many)
- `transactions` → `accounts` (Foreign key for transfer transactions)

## 📁 Project Structure

```
manaBank/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bankmanagement/
│   │   │           ├── model/          # Model classes
│   │   │           ├── dao/            # Data Access Objects
│   │   │           ├── controller/     # Business logic controllers
│   │   │           ├── view/           # GUI views
│   │   │           ├── util/           # Utility classes
│   │   │           └── BankManagementApp.java  # Main entry point
│   │   └── resources/
│   │       └── database_schema.sql     # Database schema
│   └── test/
│       └── java/                       # Test classes (optional)
├── pom.xml                             # Maven configuration
└── README.md                           # This file
```

## 🔧 Configuration

### Database Configuration

Modify database connection parameters in `DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/bankmanagement";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";
```

### Application Settings

- Default database: `bankmanagement`
- Default port: `3306`
- Connection timeout: Configurable in `DatabaseConnection.java`

## 🧪 Testing

Run unit tests (if available):

```bash
mvn test
```

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify MySQL service is running
   - Check database credentials in `DatabaseConnection.java`
   - Ensure database `bankmanagement` exists
   - Verify MySQL port (default: 3306)

2. **ClassNotFoundException: com.mysql.cj.jdbc.Driver**
   - Ensure MySQL Connector/J dependency is included in `pom.xml`
   - Run `mvn clean install` to download dependencies

3. **GUI Not Displaying**
   - Verify Java version (Java 11+ required)
   - Check for display issues on Linux: `export DISPLAY=:0`

4. **Build Errors**
   - Clean Maven cache: `mvn clean`
   - Update Maven dependencies: `mvn dependency:resolve`
   - Verify Java version matches Maven configuration

## 📈 Future Improvements

### Planned Enhancements

1. **Enhanced Security**
   - Password encryption (BCrypt)
   - Session management
   - Audit logging

2. **Advanced Features**
   - Loan management system
   - Interest calculation
   - Report generation (PDF/Excel)
   - Email notifications
   - Multi-branch support

3. **User Interface**
   - Modern UI theme
   - Responsive design improvements
   - Data visualization charts
   - Export functionality

4. **Testing**
   - Comprehensive unit tests
   - Integration tests
   - UI automation tests

5. **Performance**
   - Connection pooling
   - Caching mechanisms
   - Database query optimization

6. **Documentation**
   - API documentation
   - User manual
   - Developer guide

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

Developed as a professional Java application demonstrating enterprise-level design patterns and best practices.

## 🙏 Acknowledgments

- Java Swing framework for GUI development
- MySQL for robust database management
- Maven for dependency management and build automation

## 📞 Support

For issues, questions, or contributions, please open an issue in the repository.

---

**Note**: This application is for educational and demonstration purposes. For production use, implement additional security measures, error handling, and compliance with banking regulations.
