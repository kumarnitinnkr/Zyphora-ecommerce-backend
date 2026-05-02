CREATE TABLE seller_profiles(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 user_id BIGINT UNIQUE,
 business_name VARCHAR(200),
 gst_number VARCHAR(100),
 mobile VARCHAR(20),
 address VARCHAR(255),
 status VARCHAR(50),
 FOREIGN KEY(user_id) REFERENCES users(id)
);