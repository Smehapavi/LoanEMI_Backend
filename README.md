# Loan EMI Calculator - Backend

Spring Boot REST API for loan EMI calculations with MySQL database integration.

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Database Setup

1. **Start MySQL Server**
   ```bash
   # On Windows
   net start mysql
   
   # On macOS/Linux
   sudo systemctl start mysql
   ```

2. **Create Database**
   ```bash
   mysql -u root -p
   CREATE DATABASE loan_emi_db;
   ```

3. **Update Configuration**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```

### Run Application

1. **Install Dependencies**
   ```bash
   mvn clean install
   ```

2. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

3. **Verify**
   - Application starts on `http://localhost:8080`
   - Health check: `http://localhost:8080/api/loan/health`

## 📚 API Documentation

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/loan/calculate` | Calculate EMI |
| GET | `/api/loan/calculations` | Get all calculations |
| GET | `/api/loan/calculations/{id}` | Get calculation by ID |
| DELETE | `/api/loan/calculations/{id}` | Delete calculation |
| GET | `/api/loan/health` | Health check |

### Request Example
```json
POST /api/loan/calculate
{
  "principalAmount": 1000000,
  "interestRate": 8.5,
  "tenureMonths": 120
}
```

### Response Example
```json
{
  "id": 1,
  "principalAmount": 1000000,
  "interestRate": 8.5,
  "tenureMonths": 120,
  "monthlyEmi": 12345.67,
  "totalAmount": 1481480.40,
  "totalInterest": 481480.40,
  "calculatedAt": "2024-01-15T10:30:00"
}
```

## 🏗️ Project Structure

```
src/main/java/com/loanemi/
├── controller/
│   └── LoanCalculationController.java    # REST endpoints
├── service/
│   └── LoanCalculationService.java       # Business logic
├── repository/
│   └── LoanCalculationRepository.java    # Data access
├── model/
│   └── LoanCalculation.java             # Entity class
├── dto/
│   ├── LoanRequest.java                 # Request DTO
│   └── LoanResponse.java                # Response DTO
└── LoanEmiCalculatorApplication.java     # Main class
```

## 🔧 Configuration

### Database Configuration
```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/loan_emi_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### CORS Configuration
```properties
# CORS for React frontend
spring.web.cors.allowed-origins=http://localhost:3000
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
```

## 🧮 EMI Calculation Formula

The application uses the standard EMI formula:
```
EMI = P × r × (1 + r)^n / ((1 + r)^n - 1)
```

Where:
- P = Principal amount
- r = Monthly interest rate (annual rate ÷ 12 ÷ 100)
- n = Number of months

## 🚀 Deployment

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/loan-emi-calculator-0.0.1-SNAPSHOT.jar
```

### Docker (Optional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/loan-emi-calculator-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check username/password in application.properties
   - Ensure database exists

2. **Port Already in Use**
   - Change port in application.properties: `server.port=8081`

3. **Java Version Error**
   - Ensure Java 17+ is installed
   - Check JAVA_HOME environment variable

4. **Maven Dependencies**
   - Run `mvn clean install` to download dependencies

## 📊 Database Schema

```sql
CREATE TABLE loan_calculations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    principal_amount DECIMAL(15,2) NOT NULL,
    interest_rate DECIMAL(5,2) NOT NULL,
    tenure_months INT NOT NULL,
    monthly_emi DECIMAL(15,2) NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    total_interest DECIMAL(15,2) NOT NULL,
    calculated_at DATETIME NOT NULL
);
```

---

**Backend is ready! 🚀** 