CREATE TABLE rental
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT           NOT NULL,
    rented_at   TIMESTAMP        NOT NULL,
    due_at      TIMESTAMP        NOT NULL,
    returned_at TIMESTAMP        NULL,
    status      VARCHAR(30)      NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_rental_customer FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE rental_item
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    rental_id   BIGINT           NOT NULL,
    movie_id    BIGINT           NOT NULL,
    item_price  DOUBLE PRECISION NOT NULL,
    returned_at TIMESTAMP        NULL,
    CONSTRAINT fk_rental_item_rental FOREIGN KEY (rental_id) REFERENCES rental (id),
    CONSTRAINT fk_rental_item_movie FOREIGN KEY (movie_id) REFERENCES movie (id)
);

CREATE INDEX idx_rental_customer_id ON rental (customer_id);
CREATE INDEX idx_rental_item_rental_id ON rental_item (rental_id);
CREATE INDEX idx_rental_item_movie_id ON rental_item (movie_id);