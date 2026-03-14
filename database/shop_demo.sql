CREATE DATABASE IF NOT EXISTS shop_demo;
USE shop_demo;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    fullname VARCHAR(100),
    address VARCHAR(255),
    phone VARCHAR(20),
    is_admin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'pending',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    delivery_date TIMESTAMP NULL,
    shipping_address TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

INSERT INTO users (username, email, password, fullname, address, phone, is_admin) 
VALUES ('admin', 'admin@shop.com', '123456', 'Admin', 'Admin Address', '0123456789', TRUE);

INSERT INTO products (name, description, price, quantity, image_url) VALUES
('Laptop Dell XPS 13', 'Laptop mỏng nhẹ, hiệu suất cao', 25000000, 10, 'laptop1.jpg'),
('iPhone 14 Pro', 'Điện thoại thông minh cao cấp', 25000000, 15, 'iphone14.jpg'),
('Samsung Galaxy S23', 'Điện thoại flagship', 20000000, 20, 'galaxy23.jpg'),
('AirPods Pro', 'Tai nghe không dây', 4500000, 30, 'airpods.jpg'),
('Apple Watch', 'Đồng hồ thông minh', 8000000, 25, 'watch.jpg');
