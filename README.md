# Simple Blog Platform API

Dự án demo xây dựng RESTful API cho hệ thống Blog đơn giản, tích hợp xác thực và phân quyền bằng **Spring Security** và **Keycloak**, sử dụng cơ sở dữ liệu **PostgreSQL** chạy trên **Docker**.

## 🛠 Công nghệ sử dụng
* **Core:** Java (Spring Boot 3.x)
* **Database:** PostgreSQL
* **Security:** Spring Security (OAuth2 Resource Server), Keycloak (IAM)
* **Containerization:** Docker, Docker Compose
* **Build Tool:** Maven

## 🚀 Hướng dẫn Cài đặt & Chạy (Local)

### 1. Chuẩn bị môi trường
* Java JDK 17 trở lên
* Docker & Docker Compose
* Maven

### 2. Cấu hình bảo mật (Quan trọng)
Dự án sử dụng biến môi trường để bảo mật thông tin. Bạn cần tạo một file tên là `.env` tại thư mục gốc của dự án (cùng cấp với `docker-compose.yml`) và điền nội dung sau:

```env
# Database Configuration
DB_USER=bloguser
DB_PASS=blogpass
DB_NAME=blogdb

# Keycloak Configuration
KEYCLOAK_USER=admin
KEYCLOAK_PASS=admin
