# 📁 DriveX 
File Upload & Sharing Service (Drive Clone)

A cloud-based file storage and sharing service built with **Spring Boot** and **AWS S3**.

## 🚀 Features

- 📤 **Upload & Download Files**: Users can securely upload and download files.
- 🗄️ **Metadata Storage**: Store file metadata (name, size, type, upload date) in a database.
- 🔐 **Secure Access**: Authentication and authorization using **JWT (JSON Web Token)**.
- ☁️ **Cloud Storage**: Files are stored in **AWS S3** for scalability and reliability.
- 📂 **Organized Structure**: Users can manage their files efficiently.
- 🔗 **Centralized File Sharing**: Users can share files with others using secure links.

## 🛠️ Tech Stack

- **Backend**: Spring Boot, Spring Security, JWT Authentication
- **Storage**: AWS S3
- **Database**: MySQL/PostgreSQL (Configurable)
- **Build Tool**: Maven / Gradle

## 📌 Setup Instructions

### 1️⃣ Prerequisites
Ensure you have the following installed:
- Java 17+
- Maven or Gradle
- AWS Account with S3 Bucket
- MySQL/PostgreSQL Database

### 2️⃣ Clone the Repository
```sh
git clone https://github.com/Adityayadav0505/DriveX.git
cd DriveX
```

### 3️⃣ Configure the Application
Create a `secrets.properties` file to store sensitive credentials:

```properties
aws.accessKey=your-access-key
aws.secretKey=your-secret-key
aws.s3.bucketName=your-bucket-name
jwt.username=your-jwt-secret
jwt.password=your-jwt-secret
```

Add path to `application.properties` file

```path
spring.config.import=secrets.properties
```

Add `secrets.properties` to `.gitignore` to keep it out of Git.

### 4️⃣ Run the Application

Authenticate and Obtain JWT Token

To use the API, first authenticate by sending a POST request:

```
curl -X POST "http://localhost:8080/authenticate" \
     -H "Content-Type: application/json" \
     -d '{"username":"your-username", "password":"your-password"}'
```

The response will include a JWT token, which you need to include in subsequent API requests.

Upload a File Using cURL
```
curl -X POST "http://localhost:8080/upload" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     -F "file=@/path/to/your/file"
```

Download a File Using cURL
```
curl -X GET "http://localhost:8080/download/{fileId}" \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### Using Maven:
```sh
mvn spring-boot:run
```
#### Using Gradle:
```sh
gradle bootRun
```

### 5️⃣ API Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/upload` | Upload a file |
| `GET` | `/download/{fileId}` | Download a file |
| `GET` | `/files` | List all uploaded files |
| `POST` | `/authenticate` | Get your Jwt token |

