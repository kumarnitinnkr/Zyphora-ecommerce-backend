CREATE TABLE cart_items(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 user_id BIGINT,
 product_id BIGINT,
 quantity INT,
 FOREIGN KEY(user_id) REFERENCES users(id),
 FOREIGN KEY(product_id) REFERENCES products(id)
);

CREATE TABLE wishlist_items(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 user_id BIGINT,
 product_id BIGINT,
 FOREIGN KEY(user_id) REFERENCES users(id),
 FOREIGN KEY(product_id) REFERENCES products(id)
);