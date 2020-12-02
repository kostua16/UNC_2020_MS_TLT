/* Даны две таблицы: таблица с клиентами customers, таблица с заказами orders (для создания таблиц и наполнения данными запустить скрипты ниже).

Задача 1.
Написать запрос для вывода листа клиентских id, которые имеют созданные заказы с ценой price более 1.00.
В отчете вывести поля:
client_id

Задача 2.
Написать запрос для вывода общей суммы цен price для всех сделанных заказов.
В отчете вывести поля:
total_price (формат данных: 000$)

Задача 3.
Написать запрос для вывода списка всех созданных заказов.
В отчете вывести поля:
order_id
order_price (формат данных: 000.00$)
client_name (формат данных: в верхнем регистре)
client_email
Отсортировать список по id заказов.

Задача 4.
Написать запрос для вывода списка всех клиентов.
В отчете вывести поля:
client_name (формат данных: в верхнем регистре)
client_email
client_id
orders_count (количество созданных заказов)
orders_price_total (общая сумма созданных заказов, формат данных: 000.00$)
Отсортировать список по имени клиентов.
*/

create table orders (
  id varchar2(12),
  customer_id varchar2(12),
  price number(7,2)  
)
;

create table customers (
  id varchar2(12),
  name varchar2(20),
  email varchar2(20)
)
;

insert into customers
(id, name, email)
values (
  'c001', 'Daniel', 'daniel@mail.com'
);

insert into customers
(id, name, email)
values (
  'c002', 'Maria', 'maria@mail.com'
);

insert into customers
(id, name, email)
values (
  'c003', 'Alex', 'alex@mail.com'
);

insert into customers
(id, name, email)
values (
  'c004', 'Brown', 'brown@mail.com'
);

insert into orders
(id, customer_id, price)
values (
  'o1003', 'c003', 4.95
);

insert into orders
(id, customer_id, price)
values (
  'o1001', 'c001', 100.1
);

insert into orders
(id, customer_id, price)
values (
  'o1002', 'c002', 0.95
);

insert into orders
(id, customer_id, price)
values (
  'o1004', 'c002', 50.54
);

insert into orders
(id, customer_id, price)
values (
  'o1005', 'c001', 1.68
);

select * from orders;
select * from customers;