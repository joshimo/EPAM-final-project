Задание, вариант №4
Система Кассовый Аппарат. Кассир может открыть чек, 
добавить выбранные товары по коду из базы данных (петрушка = 234, хлеб = 222) или по названию товара, 
указать кол-во товаров или вес. Закрыть чек. Старший кассир может отменить чек, отменить один товар в чеке и вернуть деньги покупателю. 
Сделать X и Z отчеты. Товаровед может создавать товары и указывать их кол-во на складе.

Инструкция по установке приложения:
1. Для создания тестовой базы данных необходимо запустить скрипт DataBase_creation_script.sql
Для доступа к базе даннных:
Имя пользователя: testproject
Пароль: testproject
2. Для наполнения базы тестовыми данными необходимо запустить скритп TestData_creation_script.sql

Инструкция по запуску приложения:
После деплоя на Tomcat сервер, приложение будет доступно по ссылке 
http://ipaddress:port/project (например, http://localhost:8888/project)