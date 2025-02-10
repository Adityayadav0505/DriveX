# 📁 DriveX 
File Upload & Sharing Service (Drive Clone)

A cloud-based file storage and sharing service built with **Spring Boot** and **AWS S3**.

## 🚀 Features

- 📤 **Upload & Download Files**: Users can securely upload and download files.
- 🗄️ **Metadata Storage**: Store file metadata (name, size, type, upload date) in a database.
- 🔐 **Secure Access**: Authentication and authorization using **JWT (JSON Web Token)**.
- ☁️ **Cloud Storage**: Files are stored in **AWS S3** for scalability and reliability.
- 📂 **Organized Structure**: Users can manage their files efficiently.

## 🛠️ Tech Stack

- **Backend**: Spring Boot, Spring Security, JWT Authentication
- **Storage**: AWS S3
- **Database**: PostgreSQL / MySQL (Configurable)
- **Build Tool**: Maven / Gradle

## 📌 Setup Instructions

### 1️⃣ Prerequisites
Ensure you have the following installed:
- Java 17+
- Maven or Gradle
- AWS Account with S3 Bucket
- PostgreSQL/MySQL Database

### 2️⃣ Clone the Repository
```sh
git clone https://github.com/yourusername/drive-clone.git
cd drive-clone
```

### 3️⃣ Configure the Application
Create a `secrets.properties` file to store sensitive credentials:

```properties
aws.accessKey=your-access-key
aws.secretKey=your-secret-key
aws.s3.bucketName=your-bucket-name
jwt.secret=your-jwt-secret
```

Add `secrets.properties` to `.gitignore` to keep it out of Git.

### 4️⃣ Run the Application

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

## 🤝 Contributions
Feel free to contribute! Fork the repo, make changes, and submit a PR.

## 📧 Contact
For any questions, reach out via [your-email@example.com](mailto:your-email@example.com).

