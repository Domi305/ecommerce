--DROP TABLE products IF EXISTS;

CREATE TABLE products (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    title VARCHAR(255) NOT NULL,

    description TEXT DEFAULT '',

    thumbnailUrl VARCHAR(511) NOT NULL,

    price DECIMAL(18, 2) NOT NULL
);