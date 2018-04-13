START TRANSACTION;
INSERT INTO `project`.`users` (`USER_NAME`, `USER_PASSWORD`, `IS_CLIENT`, `IS_CASHIER`, `IS_SENIOR_CASHIER`, `IS_MERCHANT`, `USER_NOTES`)
VALUES ('Yaroslav', 'yaroslav', TRUE, FALSE, FALSE, FALSE, 'Created by test script');
INSERT INTO `project`.`users` (`USER_NAME`, `USER_PASSWORD`, `IS_CLIENT`, `IS_CASHIER`, `IS_SENIOR_CASHIER`, `IS_MERCHANT`, `USER_NOTES`)
VALUES ('Client', 'client', TRUE, FALSE, FALSE, FALSE, 'Created by test script');
INSERT INTO `project`.`users` (`USER_NAME`, `USER_PASSWORD`, `IS_CLIENT`, `IS_CASHIER`, `IS_SENIOR_CASHIER`, `IS_MERCHANT`, `USER_NOTES`)
VALUES ('Cashier', 'cashier', TRUE, TRUE, FALSE, FALSE, 'Created by test script');
INSERT INTO `project`.`users` (`USER_NAME`, `USER_PASSWORD`, `IS_CLIENT`, `IS_CASHIER`, `IS_SENIOR_CASHIER`, `IS_MERCHANT`, `USER_NOTES`)
VALUES ('Senior cashier', 'senior cashier', TRUE, TRUE, TRUE, FALSE, 'Created by test script');
INSERT INTO `project`.`users` (`USER_NAME`, `USER_PASSWORD`, `IS_CLIENT`, `IS_CASHIER`, `IS_SENIOR_CASHIER`, `IS_MERCHANT`, `USER_NOTES`)
VALUES ('Merchant', 'merchant', TRUE, FALSE, FALSE, TRUE, 'Created by test script');
INSERT INTO `project`.`stock` (
  `IS_AVAILABLE`,
  `PRODUCT_NAME_EN`,
  `PRODUCT_NAME_RU`,
  `PRODUCT_DESCRIPTION_EN`,
  `PRODUCT_DESCRIPTION_RU`,
  `PRODUCT_COST`,
  `PRODUCT_QUANTITY`,
  `PRODUCT_UOM_EN`,
  `PRODUCT_UOM_RU`,
  `PRODUCT_NOTES_EN`,
  `PRODUCT_NOTES_RU`
) VALUES (
  TRUE,
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
  `IS_AVAILABLE`,
  `PRODUCT_NAME_EN`,
  `PRODUCT_NAME_RU`,
  `PRODUCT_DESCRIPTION_EN`,
  `PRODUCT_DESCRIPTION_RU`,
  `PRODUCT_COST`,
  `PRODUCT_QUANTITY`,
  `PRODUCT_UOM_EN`,
  `PRODUCT_UOM_RU`,
  `PRODUCT_NOTES_EN`,
  `PRODUCT_NOTES_RU`
) VALUES (
  TRUE,
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
  `IS_AVAILABLE`,
  `PRODUCT_NAME_EN`,
  `PRODUCT_NAME_RU`,
  `PRODUCT_DESCRIPTION_EN`,
  `PRODUCT_DESCRIPTION_RU`,
  `PRODUCT_COST`,
  `PRODUCT_QUANTITY`,
  `PRODUCT_UOM_EN`,
  `PRODUCT_UOM_RU`,
  `PRODUCT_NOTES_EN`,
  `PRODUCT_NOTES_RU`
) VALUES (
  TRUE,
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
  `IS_AVAILABLE`,
  `PRODUCT_NAME_EN`,
  `PRODUCT_NAME_RU`,
  `PRODUCT_DESCRIPTION_EN`,
  `PRODUCT_DESCRIPTION_RU`,
  `PRODUCT_COST`,
  `PRODUCT_QUANTITY`,
  `PRODUCT_UOM_EN`,
  `PRODUCT_UOM_RU`,
  `PRODUCT_NOTES_EN`,
  `PRODUCT_NOTES_RU`
) VALUES (
  TRUE,
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
  `IS_AVAILABLE`,
  `PRODUCT_NAME_EN`,
  `PRODUCT_NAME_RU`,
  `PRODUCT_DESCRIPTION_EN`,
  `PRODUCT_DESCRIPTION_RU`,
  `PRODUCT_COST`,
  `PRODUCT_QUANTITY`,
  `PRODUCT_UOM_EN`,
  `PRODUCT_UOM_RU`,
  `PRODUCT_NOTES_EN`,
  `PRODUCT_NOTES_RU`
) VALUES (
  TRUE,
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
  `IS_AVAILABLE`,
  `PRODUCT_NAME_EN`,
  `PRODUCT_NAME_RU`,
  `PRODUCT_DESCRIPTION_EN`,
  `PRODUCT_DESCRIPTION_RU`,
  `PRODUCT_COST`,
  `PRODUCT_QUANTITY`,
  `PRODUCT_UOM_EN`,
  `PRODUCT_UOM_RU`,
  `PRODUCT_NOTES_EN`,
  `PRODUCT_NOTES_RU`
) VALUES (
  TRUE,
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
  `IS_AVAILABLE`,
  `PRODUCT_NAME_EN`,
  `PRODUCT_NAME_RU`,
  `PRODUCT_DESCRIPTION_EN`,
  `PRODUCT_DESCRIPTION_RU`,
  `PRODUCT_COST`,
  `PRODUCT_QUANTITY`,
  `PRODUCT_UOM_EN`,
  `PRODUCT_UOM_RU`,
  `PRODUCT_NOTES_EN`,
  `PRODUCT_NOTES_RU`
) VALUES (
  TRUE,
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
  `IS_AVAILABLE`,
  `PRODUCT_NAME_EN`,
  `PRODUCT_NAME_RU`,
  `PRODUCT_DESCRIPTION_EN`,
  `PRODUCT_DESCRIPTION_RU`,
  `PRODUCT_COST`,
  `PRODUCT_QUANTITY`,
  `PRODUCT_UOM_EN`,
  `PRODUCT_UOM_RU`,
  `PRODUCT_NOTES_EN`,
  `PRODUCT_NOTES_RU`
) VALUES (
  TRUE,
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
  `IS_AVAILABLE`,
  `PRODUCT_NAME_EN`,
  `PRODUCT_NAME_RU`,
  `PRODUCT_DESCRIPTION_EN`,
  `PRODUCT_DESCRIPTION_RU`,
  `PRODUCT_COST`,
  `PRODUCT_QUANTITY`,
  `PRODUCT_UOM_EN`,
  `PRODUCT_UOM_RU`,
  `PRODUCT_NOTES_EN`,
  `PRODUCT_NOTES_RU`
) VALUES (
  TRUE,
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
  `IS_AVAILABLE`,
  `PRODUCT_NAME_EN`,
  `PRODUCT_NAME_RU`,
  `PRODUCT_DESCRIPTION_EN`,
  `PRODUCT_DESCRIPTION_RU`,
  `PRODUCT_COST`,
  `PRODUCT_QUANTITY`,
  `PRODUCT_UOM_EN`,
  `PRODUCT_UOM_RU`,
  `PRODUCT_NOTES_EN`,
  `PRODUCT_NOTES_RU`
) VALUES (
  TRUE,
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
COMMIT;