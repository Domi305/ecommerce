ALTER TABLE products ADD COLUMN IF NOT EXISTS type VARCHAR(63) NOT NULL;
ALTER TABLE products ADD COLUMN IF NOT EXISTS author BIGINT NOT NULL;
ALTER TABLE products ADD COLUMN IF NOT EXISTS category BIGINT NOT NULL;

ALTER TABLE products ADD CONSTRAINT FK_AUTHOR FOREIGN KEY (author) REFERENCES authors(id);
ALTER TABLE products ADD CONSTRAINT FK_PRODUCT_CATEGORY FOREIGN KEY (category) REFERENCES product_categories(id);