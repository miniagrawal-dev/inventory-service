CREATE TABLE product (

    id BIGSERIAL PRIMARY KEY,

    sku VARCHAR(50) NOT NULL UNIQUE,

    name VARCHAR(255) NOT NULL,

    description TEXT,

    category VARCHAR(100),

    price DECIMAL(12,2) NOT NULL,

    available_quantity INTEGER NOT NULL,

    reserved_quantity INTEGER NOT NULL DEFAULT 0,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL

);