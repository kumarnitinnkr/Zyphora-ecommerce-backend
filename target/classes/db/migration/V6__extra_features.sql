CREATE TABLE notifications(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 user_id BIGINT,
 title VARCHAR(255),
 message TEXT,
 is_read BOOLEAN,
 FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE reviews(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 user_id BIGINT,
 product_id BIGINT,
 rating INT,
 comment TEXT,
 FOREIGN KEY(user_id) REFERENCES users(id),
 FOREIGN KEY(product_id) REFERENCES products(id)
);

CREATE TABLE coupons(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 code VARCHAR(100),
 discount_percent DOUBLE,
 active BOOLEAN
);