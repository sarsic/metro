drop table if exists order_items;
drop table if exists products;
drop table if exists orders;
drop sequence if exists seq_products;
drop sequence if exists seq_orders;
drop sequence if exists seq_order_items;

CREATE SEQUENCE seq_products START 1 INCREMENT 1;
CREATE SEQUENCE seq_orders START 1 INCREMENT 1;
CREATE SEQUENCE seq_order_items START 1 INCREMENT 1;
create table products
(
  id bigint primary key not null,
  name varchar(255),
  price float8
);
create unique index index_products_pk on products(id);
create table orders
(
  id bigint primary key not null,
  email varchar(255),
  dtc timestamp
);
create index index_orders_email on orders(dtc);
create unique index index_orders_pk on orders(id);
create table order_items
(
  id bigint primary key NOT NULL,
  order_id bigint not null,
  price float8,
  quantity float8,
  product_id bigint,
  constraint order_items_products_fk foreign key (product_id) REFERENCES products(id) on delete restrict on update restrict,
  constraint order_items_orders_fk foreign key (order_id) REFERENCES orders(id) on delete restrict on update restrict
);
create index index_order_items_order_id on order_items(order_id);
create index index_order_items_product_id on order_items(product_id);
create unique index index_order_items_pk on order_items(id);
