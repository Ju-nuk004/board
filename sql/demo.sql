--??΄λΈμ­? 
drop table product;

--????€?­? 
drop sequence product_product_id_seq;

---------
--??κ΄?λ¦?
--------
create table product(
    product_id number(4),
    title       varchar(30),
    text_log   varchar(2000),
    user_name  varchar(30),
    cdate       timestamp, --??±?Ό?
    udate       timestamp  --?? ?Ό?
);
--κΈ°λ³Έ?€
alter table product add constraint product_product_id_pk primary key(product_id);

--????€??±
create sequence product_product_id_seq;

--??΄?Έ
alter table product modify cdate default systimestamp; --?΄?μ²΄μ  ?Ό?λ₯? κΈ°λ³Έκ°μΌλ‘?
alter table product modify udate default systimestamp; --?΄?μ²΄μ  ?Ό?λ₯? κΈ°λ³Έκ°μΌλ‘?

-- --??±--
-- insert into product(product_id,pname,quantity,price)
--      values(product_product_id_seq.nextval, 'μ»΄ν¨?°', 5, 1000000);

-- insert into product(product_id,pname,quantity,price)
--      values(product_product_id_seq.nextval, 'λͺ¨λ?°', 5, 500000);

-- insert into product(product_id,pname,quantity,price)
--      values(product_product_id_seq.nextval, '?λ¦°ν°', 1, 300000);
commit;
