START TRANSACTION;

INSERT INTO `project`.`order_status` (status_id, status_description) VALUES (1, 'CREATED');
INSERT INTO `project`.`order_status` (status_id, status_description) VALUES (2, 'FINISHED');
INSERT INTO `project`.`order_status` (status_id, status_description) VALUES (3, 'CANCELLED');

INSERT INTO `project`.`user_roles` (role_id, role_description) VALUES (1, 'USER');
INSERT INTO `project`.`user_roles` (role_id, role_description) VALUES (2, 'CASHIER');
INSERT INTO `project`.`user_roles` (role_id, role_description) VALUES (3, 'SENIOR_CASHIER');
INSERT INTO `project`.`user_roles` (role_id, role_description) VALUES (4, 'MERCHANT');
INSERT INTO `project`.`user_roles` (role_id, role_description) VALUES (5, 'ADMIN');

INSERT INTO `project`.`users` (user_name, user_password, role_id, user_notes)
VALUES ('Yaroslav', 'yaroslav', 1, 'Created by test script');
INSERT INTO `project`.`users` (user_name, user_password, role_id, user_notes)
VALUES ('Client', 'client', 1, 'Created by test script');
INSERT INTO `project`.`users` (user_name, user_password, role_id, user_notes)
VALUES ('Cashier', 'cashier', 2, 'Created by test script');
INSERT INTO `project`.`users` (user_name, user_password, role_id, user_notes)
VALUES ('Senior cashier', 'senior cashier', 3, 'Created by test script');
INSERT INTO `project`.`users` (user_name, user_password, role_id, user_notes)
VALUES ('Merchant', 'merchant', 4, 'Created by test script');
INSERT INTO `project`.`users` (user_name, user_password, role_id, user_notes)
VALUES ('Admin', 'admin', 5, 'Created by test script');

INSERT INTO `project`.`stock` (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D001',
  'Royal Canin X-Small Junior',
  'Royal Canin X-Small Junior',
  'Royal Canin dry feed for small and miniature puppets',
  'Корм Роял Канин для щенков миниатюрных пород',
  282.00,
  50,
  'kg',
  'кг',
  'Created by test script',
  'Сгенерировано тестовым скриптом'
);
INSERT INTO `project`.`stock` (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D002',
  'Royal Canin X-Small Adult',
  'Royal Canin X-Small Adult',
  'Royal Canin dry feed for small and miniature adult dogs',
  'Корм Роял Канин для взрослых собак миниатюрных пород',
  258.00,
  50,
  'kg',
  'кг',
  'Created by test script',
  'Сгенерировано тестовым скриптом'
);
INSERT INTO `project`.`stock` (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D003',
  'Royal Canin Mini Junior',
  'Royal Canin Mini Junior',
  'Royal Canin dry feed for small puppets',
  'Корм Роял Канин для щенков мeлких пород',
  226.25,
  100,
  'kg',
  'кг',
  'Created by test script',
  'Сгенерировано тестовым скриптом'
);
INSERT INTO `project`.`stock` (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D004',
  'Royal Canin Mini Adult',
  'Royal Canin Mini Adult',
  'Royal Canin dry feed for small adult dogs',
  'Корм Роял Канин для взрослых собак мелких пород',
  216.25,
  150,
  'kg',
  'кг',
  'Created by test script',
  'Сгенерировано тестовым скриптом'
);
INSERT INTO `project`.`stock` (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D005',
  'Royal Canin Medium Junior',
  'Royal Canin Medium Junior',
  'Royal Canin dry feed for medium size puppets',
  'Корм Роял Канин для щенков средних пород',
  189.00,
  250,
  'kg',
  'кг',
  'Created by test script',
  'Сгенерировано тестовым скриптом'
);
INSERT INTO `project`.`stock` (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D006',
  'Royal Canin Medium Adult',
  'Royal Canin Medium Adult',
  'Royal Canin dry feed for medium size adult dogs',
  'Корм Роял Канин для взрослых собак средних пород',
  189.00,
  300,
  'kg',
  'кг',
  'Created by test script',
  'Сгенерировано тестовым скриптом'
);
INSERT INTO `project`.`stock` (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D007',
  'Royal Canin Maxi Junior',
  'Royal Canin Maxi Junior',
  'Royal Canin dry feed for big size puppets',
  'Корм Роял Канин для щенков крупных пород',
  197.00,
  300,
  'kg',
  'кг',
  'Created by test script',
  'Сгенерировано тестовым скриптом'
);
INSERT INTO `project`.`stock` (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D008',
  'Royal Canin Maxi Adult',
  'Royal Canin Maxi Adult',
  'Royal Canin dry feed for big size adult dogs',
  'Корм Роял Канин для взрослых собак крупных пород',
  148.50,
  400,
  'kg',
  'кг',
  'Created by test script',
  'Сгенерировано тестовым скриптом'
);
INSERT INTO `project`.`stock` (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D009',
  'Royal Canin Energy',
  'Royal Canin Energy',
  'Royal Canin crockets for adult dogs training',
  'Крокеты Роял Канин для дрессировки взрослых собак',
  21.70,
  1500,
  'pcs',
  'шт',
  'Created by test script',
  'Сгенерировано тестовым скриптом'
);
INSERT INTO `project`.`stock` (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D010',
  'Royal Canin Educ',
  'Royal Canin Educ',
  'Royal Canin crockets for adult dogs and puppets training',
  'Крокеты Роял Канин для дрессировки взрослых собак и щенков',
  21.70,
  3000,
  'pcs',
  'шт',
  'Created by test script',
  'Сгенерировано тестовым скриптом'
);

INSERT INTO project.orders (user_id, status_id, order_notes) VALUES (1, 1, 'Created by test script');
INSERT INTO project.payments (order_id, product_code, quantity, payment_notes) VALUES (1, 'D006', 5.0, 'Created by test script');
INSERT INTO project.payments (order_id, product_code, quantity, payment_notes) VALUES (1, 'D008', 7.0, 'Created by test script');
INSERT INTO project.payments (order_id, product_code, quantity, payment_notes) VALUES (1, 'D009', 20, 'Created by test script');

INSERT INTO project.orders (user_id, status_id, order_notes) VALUES (2, 1, 'Created by test script');
INSERT INTO project.payments (order_id, product_code, quantity, payment_notes) VALUES (2, 'D003', 3.0, 'Created by test script');
INSERT INTO project.payments (order_id, product_code, quantity, payment_notes) VALUES (2, 'D010', 10, 'Created by test script');

INSERT INTO project.orders (user_id, status_id, order_notes) VALUES (2, 1, 'Created by test script');
INSERT INTO project.payments (order_id, product_code, quantity, payment_notes) VALUES (3, 'D008', 20, 'Created by test script');
INSERT INTO project.payments (order_id, product_code, quantity, payment_notes) VALUES (3, 'D009', 50, 'Created by test script');
INSERT INTO project.payments (order_id, product_code, quantity, payment_notes) VALUES (3, 'D010', 50, 'Created by test script');

COMMIT;