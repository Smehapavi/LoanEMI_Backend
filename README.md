# Loan EMI Calculator

A full-stack web application for calculating and managing loan EMIs (Equated Monthly Installments) built with Spring Boot backend, React frontend, and MySQL database.

## Features

### Backend (Spring Boot)
- **User Authentication**: JWT-based authentication with login/signup
- **EMI Calculation**: Real-time EMI calculation with accurate formulas
- **Loan Management**: Apply for loans, view loan history, delete loans
- **RESTful APIs**: Clean REST API design with proper error handling
- **Security**: Spring Security with JWT token authentication
- **Database**: MySQL integration with JPA/Hibernate

### Frontend (React + TypeScript)
- **Modern UI**: Clean, responsive design with gradient backgrounds
- **Real-time Calculation**: EMI calculation updates as you type
- **User Dashboard**: Comprehensive dashboard with calculator and loan management
- **Authentication Flow**: Secure login/signup with error handling
- **Responsive Design**: Mobile-friendly interface
- **Type Safety**: Full TypeScript implementation

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.5.3
- **Security**: Spring Security with JWT
- **Database**: MySQL with JPA/Hibernate
- **Language**: Java 21
- **Build Tool**: Maven

### Frontend
- **Framework**: React 18 with TypeScript
- **Routing**: React Router v6
- **HTTP Client**: Axios
- **Styling**: Pure CSS with modern design
- **Build Tool**: Create React App

## Project Structure

```
/
├── src/main/java/com/example/LoanEMI/     # Spring Boot Backend
│   ├── config/                           # Security configuration
│   ├── controller/                       # REST controllers
│   ├── dto/                             # Data Transfer Objects
│   ├── jwt/                             # JWT utilities
│   ├── model/                           # JPA entities
│   ├── repository/                      # Data repositories
│   └── service/                         # Business logic
├── frontend/                            # React Frontend
│   ├── src/
│   │   ├── components/                  # React components
│   │   ├── context/                     # Authentication context
│   │   ├── services/                    # API services
│   │   └── types/                       # TypeScript types
│   └── public/                          # Static files
└── README.md
```

## Setup Instructions

### Prerequisites
- Java 21 or higher
- Node.js 16 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Setup
1. Install MySQL and create a database:
```sql
CREATE DATABASE loanemi;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/loanemi
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Backend Setup
1. Navigate to the project root directory
2. Install dependencies and run:
```bash
./mvnw spring-boot:run
```
The backend will start on `http://localhost:8080`

### Frontend Setup
1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```
The frontend will start on `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/login` - User login

### Loan Management
- `POST /api/loans/calculate` - Calculate EMI for loan parameters
- `POST /api/loans` - Apply for a loan
- `GET /api/loans` - Get user's loans
- `DELETE /api/loans/{id}` - Delete a loan

## Usage

1. **Sign Up**: Create a new account with username and password
2. **Login**: Sign in with your credentials
3. **Calculate EMI**: Use the EMI calculator to see monthly payments
4. **Apply for Loan**: Save loan applications to your profile
5. **Manage Loans**: View and delete your loan applications

## EMI Calculation Formula

The application uses the standard EMI formula:
```
EMI = P × r × (1 + r)^n / ((1 + r)^n - 1)
```
Where:
- P = Principal loan amount
- r = Monthly interest rate (annual rate / 12 / 100)
- n = Number of monthly installments

## Features Breakdown

### EMI Calculator
- Real-time calculation as you type
- Support for loan amounts from ₹1,000 to ₹1,00,00,000
- Interest rates from 0% to 50%
- Tenure from 1 month to 30 years
- Detailed breakdown showing EMI, total amount, and total interest

### User Dashboard
- Clean tabbed interface
- EMI Calculator tab for calculations
- My Loans tab for loan management
- User profile display with logout functionality

### Security Features
- JWT token-based authentication
- Password encryption
- Protected routes
- Automatic token refresh handling

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, email support@loanemicalculator.com or create an issue in the repository.