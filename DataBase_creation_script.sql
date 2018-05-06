CREATE DATABASE project;

GRANT ALL PRIVILEGES ON project.* TO 'testproject'@'localhost' IDENTIFIED BY 'testproject';
GRANT ALL PRIVILEGES ON project.* TO 'testproject'@'%' IDENTIFIED BY 'testproject';

CREATE TABLE project.user_roles (
  role_id SMALLINT NOT NULL KEY,
  role_description VARCHAR(32) UNIQUE
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.order_status (
  status_id SMALLINT NOT NULL KEY,
  status_description VARCHAR(32) UNIQUE
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.stock (
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

CREATE TABLE project.orders (
  order_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
  order_code BIGINT UNIQUE NOT NULL,
  user_name VARCHAR(128),
  status_id SMALLINT NOT NULL,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  order_notes VARCHAR(255),
  FOREIGN KEY (user_name) REFERENCES project.users (user_name)
    ON UPDATE CASCADE
    ON DELETE SET NULL,
  FOREIGN KEY (status_id) REFERENCES project.order_status(status_id)
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.payments (
  payment_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
  order_code BIGINT NOT NULL,
  product_code VARCHAR(12) NOT NULL,
  quantity DOUBLE NOT NULL DEFAULT 0,
  payment_value DOUBLE NOT NULL DEFAULT 0,
  status_id SMALLINT NOT NULL,
  payment_notes VARCHAR(255),
  FOREIGN KEY (product_code) REFERENCES project.stock(product_code)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  FOREIGN KEY (order_code) REFERENCES project.orders(order_code)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  FOREIGN KEY (status_id) REFERENCES project.order_status(status_id)
) ENGINE InnoDB DEFAULT CHARSET=utf8;