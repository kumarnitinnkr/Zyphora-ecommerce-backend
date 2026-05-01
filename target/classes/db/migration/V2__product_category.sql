CREATE TABLE categories(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(150),
 image_url VARCHAR(500)
);

CREATE TABLE products(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 title VARCHAR(255),
 description TEXT,
 price DECIMAL(10,2),
 stock INT,
 image_url VARCHAR(500),
 rating DOUBLE,
 active BOOLEAN,
 category_id BIGINT,
 FOREIGN KEY(category_id) REFERENCES categories(id)
);