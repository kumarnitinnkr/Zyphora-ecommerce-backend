CREATE TABLE addresses(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 user_id BIGINT,
 full_name VARCHAR(150),
 mobile VARCHAR(20),
 line1 VARCHAR(255),
 line2 VARCHAR(255),
 city VARCHAR(100),
 state VARCHAR(100),
 pincode VARCHAR(20),
 country VARCHAR(100),
 FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE orders(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 user_id BIGINT,
 address_id BIGINT,
 total_amount DECIMAL(10,2),
 payment_method VARCHAR(50),
 payment_status VARCHAR(50),
 status VARCHAR(50),
 created_at DATETIME,
 FOREIGN KEY(user_id) REFERENCES users(id),
 FOREIGN KEY(address_id) REFERENCES addresses(id)
);

CREATE TABLE order_items(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 order_id BIGINT,
 product_id BIGINT,
 quantity INT,
 price DECIMAL(10,2),
 FOREIGN KEY(order_id) REFERENCES orders(id),
 FOREIGN KEY(product_id) REFERENCES products(id)
);