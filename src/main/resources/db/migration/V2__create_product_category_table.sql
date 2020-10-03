DROP TABLE  product_categories IF EXISTS;

CREATE TABLE product_categories (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(255) NOT NULL,

    parent_Category BIGINT NOT NULL,

    FOREIGN KEY (parent_Category) REFERENCES product_categories
        (id)
);