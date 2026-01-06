# 📝 Simple Blog Platform API

Dự án demo xây dựng **RESTful API** quản lý bài viết (Blog) sử dụng **Spring Boot**, **PostgreSQL** và tích hợp bảo mật với **Keycloak (OAuth2 / JWT)**.

## 🚀 Công nghệ sử dụng

- **Core**: Java 17, Spring Boot 3.x  
- **Database**: PostgreSQL (Docker)  
- **Security**: Spring Security, OAuth2 Resource Server, Keycloak  
- **Containerization**: Docker, Docker Compose  

## 📋 Tính năng chính

### CRUD Blog Posts
- Xem danh sách & chi tiết bài viết (User / Admin)
- Thêm, sửa, xóa bài viết (Admin)

### Authentication & Authorization
- Quản lý người dùng bằng Keycloak
- Phân quyền theo Role (`user`, `admin`)
- Bảo mật bằng JWT

## 🛠 Hướng dẫn Cài đặt & Chạy

### Yêu cầu
- Java JDK 17+
- Docker & Docker Compose

### Biến môi trường

Tạo file `.env` tại thư mục gốc:

```env
DB_USER=bloguser
DB_PASS=blogpass
DB_NAME=blogdb

KEYCLOAK_USER=admin
KEYCLOAK_PASS=admin
```

### Chạy Database & Keycloak

```bash
docker-compose up -d
```

- PostgreSQL: `localhost:5432`
- Keycloak: `http://localhost:8180`

### Cấu hình Keycloak

- Realm: `blog-realm`
- Client ID: `blog-app`
- Roles: `user`, `admin`
- Tạo user test và gán role

### Chạy ứng dụng

```bash
./mvnw spring-boot:run
```

Ứng dụng chạy tại: `http://localhost:8080`

## 🔌 API Endpoints

| Method | Endpoint | Mô tả | Quyền |
|------|--------|------|------|
| GET | /posts | Lấy danh sách bài viết | User, Admin |
| GET | /posts/{id} | Lấy chi tiết bài viết | User, Admin |
| POST | /posts | Tạo bài viết | Admin |
| PUT | /posts/{id} | Cập nhật bài viết | Admin |
| DELETE | /posts/{id} | Xóa bài viết | Admin |
