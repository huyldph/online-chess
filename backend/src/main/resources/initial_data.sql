-- Xóa nếu đã tồn tại
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

-- Bảng users
CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    name        VARCHAR(255),
    password    VARCHAR(255),
    provider    ENUM('LOCAL', 'GOOGLE', 'FACEBOOK') DEFAULT 'LOCAL',
    provider_id VARCHAR(255),
    enabled     BOOLEAN   DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng roles
CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Bảng trung gian user_roles
CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- Thêm role mặc định
INSERT INTO roles(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

-- Thêm user mẫu (có mật khẩu mã hóa sẵn bằng bcrypt (admin123))
INSERT INTO users(email, name, password, provider, enabled)
VALUES ('admin@example.com', 'Admin', '$2a$10$Dn0oSx8P9f6c9F6Xj9XnOelqjUJXa1I4pA8jHVbOdfn5LMqHYb8vq', 'LOCAL', TRUE);

-- Gán quyền cho user
-- Lưu ý: kiểm tra ID user vừa thêm, giả sử ID là 1 và ROLE_ADMIN là ID 2
INSERT INTO user_roles(user_id, role_id)
VALUES (1, 2);
