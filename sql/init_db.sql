-- Скрипт инициализации базы данных системы учета ПО

-- 1. Таблицы
CREATE TABLE IF NOT EXISTS rooms (
    id SERIAL PRIMARY KEY,
    number VARCHAR(10) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE IF NOT EXISTS software_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS software (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    version VARCHAR(50),
    category_id INTEGER REFERENCES software_categories(id),
    vendor VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS licenses (
    id SERIAL PRIMARY KEY,
    software_id INTEGER REFERENCES software(id),
    license_key VARCHAR(100),
    license_type VARCHAR(50),
    expiry_date DATE
);

CREATE TABLE IF NOT EXISTS computers (
    id SERIAL PRIMARY KEY,
    room_id INTEGER REFERENCES rooms(id),
    inventory_number VARCHAR(50) UNIQUE NOT NULL,
    ip_address VARCHAR(15),
    cpu_info VARCHAR(100),
    ram_gb INTEGER,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS installations (
    id SERIAL PRIMARY KEY,
    computer_id INTEGER REFERENCES computers(id),
    software_id INTEGER REFERENCES software(id),
    license_id INTEGER REFERENCES licenses(id),
    installed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS audit_logs (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(100),
    operation VARCHAR(20),
    table_name VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);