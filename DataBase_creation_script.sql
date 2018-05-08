CREATE DATABASE project;

GRANT ALL PRIVILEGES ON project.* TO 'testproject'@'localhost' IDENTIFIED BY 'testproject';
GRANT ALL PRIVILEGES ON project.* TO 'testproject'@'%' IDENTIFIED BY 'testproject';

CREATE TABLE project.user_roles (
  role_id SMALLINT NOT NULL KEY,
  role_description VARCHAR(32) UNIQUE
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.invoice_status (
  status_id SMALLINT NOT NULL KEY,
  status_description VARCHAR(32) UNIQUE
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.products (
  product_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
  product_code VARCHAR(12) NOT NULL UNIQUE,
  is_available BIT(1) NOT NULL DEFAULT FALSE,
  product_name_en VARCHAR(128),
  product_name_ru VARCHAR(128),
  product_description_en VARCHAR(255),
  product_description_ru VARCHAR(255),
  product_cost DOUBLE NOT NULL DEFAULT 0,
  product_quantity DOUBLE NOT NULL DEFAULT 0,
  reserved_quantity DOUBLE NOT NULL DEFAULT 0,
  product_uom_en VARCHAR(32) NOT NULL,
  product_uom_ru VARCHAR(32) NOT NULL,
  product_notes_en VARCHAR(255),
  product_notes_ru VARCHAR(255)
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.users (
  user_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
  user_name VARCHAR(128) UNIQUE,
  user_password VARCHAR(128) UNIQUE,
  user_email VARCHAR(48),
  role_id SMALLINT NOT NULL DEFAULT 1,
  user_notes VARCHAR(255),
  FOREIGN KEY (role_id) REFERENCES project.user_roles (role_id)
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.invoices (
  invoice_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
  invoice_code BIGINT UNIQUE NOT NULL,
  user_name VARCHAR(128),
  is_paid BIT(1) NOT NULL DEFAULT FALSE,
  status_id SMALLINT NOT NULL,
  invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  invoice_notes VARCHAR(255),
  FOREIGN KEY (user_name) REFERENCES project.users (user_name)
    ON UPDATE CASCADE
    ON DELETE SET NULL,
  FOREIGN KEY (status_id) REFERENCES project.invoice_status(status_id)
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.payments (
  payment_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
  invoice_code BIGINT NOT NULL,
  product_code VARCHAR(12) NOT NULL,
  quantity DOUBLE NOT NULL DEFAULT 0,
  payment_value DOUBLE NOT NULL DEFAULT 0,
  status_id SMALLINT NOT NULL,
  payment_notes VARCHAR(255),
  FOREIGN KEY (product_code) REFERENCES project.products(product_code)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  FOREIGN KEY (invoice_code) REFERENCES project.invoices(invoice_code)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  FOREIGN KEY (status_id) REFERENCES project.invoice_status(status_id)
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.transactions (
  transaction_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
  payment_id BIGINT NOT NULL,
  invoice_code BIGINT NOT NULL,
  user_name VARCHAR(128),
  transaction_type VARCHAR(16) NOT NULL,
  payment_value DOUBLE NOT NULL DEFAULT 0,
  transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  transaction_notes VARCHAR(255),
  FOREIGN KEY (user_name) REFERENCES project.users (user_name)
    ON UPDATE CASCADE
    ON DELETE SET NULL
) ENGINE InnoDB DEFAULT CHARSET=utf8;