--?��?��블삭?��
drop table product;

--?��???��?��?��
drop sequence product_product_id_seq;

---------
--?��?���?�?
--------
create table product(
    product_id number(4),
    title       varchar(30),
    text_log   varchar(2000),
    user_name  varchar(30),
    cdate       timestamp, --?��?��?��?��
    udate       timestamp  --?��?��?��?��
);
--기본?��
alter table product add constraint product_product_id_pk primary key(product_id);

--?��???��?��?��
create sequence product_product_id_seq;

--?��?��?��
alter table product modify cdate default systimestamp; --?��?��체제 ?��?���? 기본값으�?
alter table product modify udate default systimestamp; --?��?��체제 ?��?���? 기본값으�?

-- --?��?��--
-- insert into product(product_id,pname,quantity,price)
--      values(product_product_id_seq.nextval, '컴퓨?��', 5, 1000000);

-- insert into product(product_id,pname,quantity,price)
--      values(product_product_id_seq.nextval, '모니?��', 5, 500000);

-- insert into product(product_id,pname,quantity,price)
--      values(product_product_id_seq.nextval, '?��린터', 1, 300000);
commit;
