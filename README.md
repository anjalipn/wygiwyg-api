# Dummy API

A Spring Boot application that provides a customizable API endpoint for testing and development purposes. This API allows you to specify the exact response you want to receive, including status code, headers, and body.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

1. Clone the repository:
```bash
git clone <repository-url>
cd dummy-api
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on port 8080.

## API Documentation

### Endpoint

```
POST http://localhost:8080/api/dummy/response
```

### Request Body Structure

```json
{
    "statusCode": 200,           // HTTP status code (default: 200)
    "headers": {                 // Optional custom headers
        "header-name": "value"
    },
    "body": {                    // Optional response body (can be any valid JSON)
        "key": "value"
    }
}
```

## Examples

### 1. Basic Success Response
```bash
curl -X POST http://localhost:8080/api/dummy/response \
-H "Content-Type: application/json" \
-d '{
    "statusCode": 200,
    "body": {
        "message": "Hello World"
    }
}'
```

Response:
```json
{
    "message": "Hello World"
}
```

### 2. Response Without Body
```bash
curl -X POST http://localhost:8080/api/dummy/response \
-H "Content-Type: application/json" \
-d '{
    "statusCode": 204
}'
```

Response: Empty response body with status code 204

### 3. Custom Headers
```bash
curl -X POST http://localhost:8080/api/dummy/response \
-H "Content-Type: application/json" \
-d '{
    "statusCode": 200,
    "headers": {
        "X-Custom-Header": "CustomValue",
        "X-Request-ID": "12345"
    },
    "body": {
        "message": "Response with custom headers"
    }
}'
```

Response Headers:
```
X-Custom-Header: CustomValue
X-Request-ID: 12345
```

Response Body:
```json
{
    "message": "Response with custom headers"
}
```

### 4. Error Response
```bash
curl -X POST http://localhost:8080/api/dummy/response \
-H "Content-Type: application/json" \
-d '{
    "statusCode": 404,
    "body": {
        "error": "Resource not found",
        "code": "NOT_FOUND"
    }
}'
```

Response (Status: 404):
```json
{
    "error": "Resource not found",
    "code": "NOT_FOUND"
}
```

### 5. Complex Response Body
```bash
curl -X POST http://localhost:8080/api/dummy/response \
-H "Content-Type: application/json" \
-d '{
    "statusCode": 200,
    "body": {
        "user": {
            "id": 1,
            "name": "John Doe",
            "email": "john@example.com",
            "roles": ["USER", "ADMIN"]
        },
        "metadata": {
            "createdAt": "2024-03-18T12:00:00Z",
            "version": "1.0.0"
        }
    }
}'
```

Response:
```json
{
    "user": {
        "id": 1,
        "name": "John Doe",
        "email": "john@example.com",
        "roles": ["USER", "ADMIN"]
    },
    "metadata": {
        "createdAt": "2024-03-18T12:00:00Z",
        "version": "1.0.0"
    }
}
```

## Use Cases

1. **API Testing**: Test how your application handles different HTTP status codes and response formats
2. **Development**: Simulate API responses during frontend development
3. **Integration Testing**: Create test scenarios with specific response patterns
4. **Documentation**: Generate example responses for API documentation

## Notes

- The API will return exactly what you specify in the request
- If no status code is provided, it defaults to 200
- Headers are optional
- The body is optional - you can send requests without a body
- When no body is provided, the response will have an empty body
- The body can be any valid JSON structure when provided
- The response will maintain the exact structure of the body you provide

## Contributing

Feel free to submit issues and enhancement requests! 