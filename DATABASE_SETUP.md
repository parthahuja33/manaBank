# Database Setup Guide

## Quick Start

### 1. Install MySQL

Download and install MySQL 8.0+ from [MySQL Official Website](https://dev.mysql.com/downloads/mysql/).

### 2. Start MySQL Service

**Windows:**
```cmd
net start MySQL80
```

**Linux:**
```bash
sudo systemctl start mysql
# or
sudo service mysql start
```

**macOS:**
```bash
brew services start mysql
```

### 3. Create Database

**Option A: Using MySQL Command Line**
```bash
mysql -u root -p
```

Then execute:
```sql
SOURCE src/main/resources/database_schema.sql
```

**Option B: Using MySQL Workbench**
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Open `src/main/resources/database_schema.sql`
4. Execute the script

**Option C: Manual Creation**
```sql
CREATE DATABASE IF NOT EXISTS bankmanagement;
USE bankmanagement;

-- Then copy and paste the SQL from database_schema.sql
```

### 4. Verify Database Setup

```sql
USE bankmanagement;
SHOW TABLES;
```

You should see:
- `users`
- `customers`
- `accounts`
- `transactions`

### 5. Verify Default Users

```sql
SELECT * FROM users;
```

You should see two default users:
- admin / admin123
- teller / teller123

### 6. Configure Application

If your MySQL configuration differs from defaults, update `DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/bankmanagement";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

### 7. Test Connection

Run the application and try to login. If connection fails, check:
- MySQL service is running
- Database name is correct
- Username and password are correct
- Port 3306 is accessible

## Troubleshooting

### Connection Refused
- Verify MySQL service is running
- Check if MySQL is listening on port 3306
- Verify firewall settings

### Access Denied
- Verify username and password
- Check MySQL user privileges
- Ensure user has access to `bankmanagement` database

### Database Not Found
- Verify database name in connection string
- Run the schema script to create database
- Check database exists: `SHOW DATABASES;`

### Driver Not Found
- Ensure Maven dependencies are downloaded: `mvn dependency:resolve`
- Check `pom.xml` includes MySQL connector
- Verify MySQL connector version compatibility

## Default Credentials

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| teller | teller123 | TELLER |

**Change these in production!**

