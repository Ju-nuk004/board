--?…Œ?´ë¸”ì‚­? œ
drop table product;

--?‹œ???Š¤?‚­? œ
drop sequence product_product_id_seq;

---------
--?ƒ?’ˆê´?ë¦?
--------
create table product(
    product_id number(4),
    title       varchar(30),
    text_log   varchar(2000),
    user_name  varchar(30),
    cdate       timestamp, --?ƒ?„±?¼?‹œ
    udate       timestamp  --?ˆ˜? •?¼?‹œ
);
--ê¸°ë³¸?‚¤
alter table product add constraint product_product_id_pk primary key(product_id);

--?‹œ???Š¤?ƒ?„±
create sequence product_product_id_seq;

--?””?´?Š¸
alter table product modify cdate default systimestamp; --?š´?˜ì²´ì œ ?¼?‹œë¥? ê¸°ë³¸ê°’ìœ¼ë¡?
alter table product modify udate default systimestamp; --?š´?˜ì²´ì œ ?¼?‹œë¥? ê¸°ë³¸ê°’ìœ¼ë¡?

-- --?ƒ?„±--
-- insert into product(product_id,pname,quantity,price)
--      values(product_product_id_seq.nextval, 'ì»´í“¨?„°', 5, 1000000);

-- insert into product(product_id,pname,quantity,price)
--      values(product_product_id_seq.nextval, 'ëª¨ë‹ˆ?„°', 5, 500000);

-- insert into product(product_id,pname,quantity,price)
--      values(product_product_id_seq.nextval, '?”„ë¦°í„°', 1, 300000);
commit;
