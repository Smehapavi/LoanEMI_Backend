# Loan EMI Calculator Backend - Render Deployment Guide

## Overview
This Spring Boot application is configured for deployment on Render.com with MySQL database integration.

## Prerequisites
- Render.com account
- MySQL database (can be provisioned on Render)
- Git repository with this code

## Deployment Steps

### 1. Database Setup
1. Create a new MySQL database on Render:
   - Go to your Render dashboard
   - Click "New" → "PostgreSQL" (or use external MySQL)
   - Note down the database credentials

### 2. Environment Variables
Set these environment variables in your Render service:
- `MYSQL_HOST`: Your MySQL host
- `MYSQL_PORT`: Your MySQL port (usually 3306)
- `MYSQL_DATABASE`: Your database name
- `MYSQL_USERNAME`: Your database username
- `MYSQL_PASSWORD`: Your database password

### 3. Deploy to Render
1. Connect your GitHub repository to Render
2. Create a new Web Service
3. Configure the service:
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/loan-emi-calculator-0.0.1-SNAPSHOT.jar`
   - **Environment**: Java

### 4. Health Check
The application includes a health check endpoint at `/api/loan/health`

## API Endpoints

### Base URL
`https://your-app-name.onrender.com`

### Available Endpoints
- `POST /api/loan/calculate` - Calculate EMI
- `GET /api/loan/calculations` - Get all calculations
- `GET /api/loan/calculations/{id}` - Get calculation by ID
- `DELETE /api/loan/calculations/{id}` - Delete calculation
- `GET /api/loan/health` - Health check

## Configuration Files

### render.yaml
This file contains the Render service configuration with:
- Build and start commands
- Environment variables
- Health check path
- Memory optimization settings

### Application Properties
- `application.properties` - Default configuration
- `application-render.properties` - Render-specific configuration
- `application-prod.properties` - Production configuration

## Troubleshooting

### Common Issues

1. **Build Failures**
   - Ensure Java 17 is available
   - Check Maven wrapper permissions
   - Verify all dependencies are accessible

2. **Database Connection Issues**
   - Verify environment variables are set correctly
   - Check database accessibility from Render
   - Ensure database is running

3. **Port Issues**
   - Render assigns port via `PORT` environment variable
   - Application is configured to use port 10000 or `SERVER_PORT`

4. **Memory Issues**
   - JVM options are set for 512MB max heap
   - Monitor memory usage in Render dashboard

### Logs
Check Render logs for:
- Application startup messages
- Database connection status
- Error messages

## Local Development

### Running Locally
```bash
# Build the project
./mvnw clean package

# Run with default profile
java -jar target/loan-emi-calculator-0.0.1-SNAPSHOT.jar

# Run with render profile
java -jar target/loan-emi-calculator-0.0.1-SNAPSHOT.jar --spring.profiles.active=render
```

### Database Setup
1. Install MySQL locally
2. Create database: `loanemidb`
3. Update `application.properties` with local credentials

## Security Notes
- CORS is configured for production
- Database credentials are environment variables
- SSL is disabled for development (enable for production)

## Performance Optimization
- JVM memory settings optimized for Render free tier
- Database connection pooling configured
- Logging levels adjusted for production 