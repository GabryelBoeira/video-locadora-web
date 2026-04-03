CREATE TABLE cart
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id  VARCHAR(36) NOT NULL UNIQUE,
    customer_id BIGINT      NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at  TIMESTAMP   NOT NULL,
    total_price DOUBLE      NOT NULL DEFAULT 0.0,
    status      VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id) REFERENCES customer (id),
    INDEX idx_cart_customer (customer_id),
    INDEX idx_cart_session (session_id),
    INDEX idx_cart_expires (expires_at)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE cart_item
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id                BIGINT    NOT NULL,
    movie_id               BIGINT    NOT NULL,
    rental_days            INT       NOT NULL,
    unit_price             DOUBLE    NOT NULL,
    subtotal               DOUBLE    NOT NULL,
    added_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reservation_expires_at TIMESTAMP NOT NULL,
    reserved               BOOLEAN   NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_movie FOREIGN KEY (movie_id) REFERENCES movie (id),
    INDEX idx_cart_item_movie (movie_id),
    INDEX idx_cart_item_reservation (reservation_expires_at)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;