CREATE SEQUENCE account_seq
    INCREMENT 50
    START 1;
CREATE SEQUENCE purchases_seq
    INCREMENT 50
    START 1;
CREATE SEQUENCE purchases_number_seq
    INCREMENT 1
    START 1;
CREATE SEQUENCE products_seq
    INCREMENT 50
    START 1;
create table accounts
(
    id       int                not null default nextval('account_seq'::regclass),
    name     varchar(64) unique not null,
    password varchar(128)       not null,
    role     varchar(64)        not null check (role in ('salesman', 'buyer')),
    amount   numeric(10, 2)     not null,
    primary key (id)
);

create table products
(
    id           int                not null default nextval('products_seq'::regclass),
    name         varchar(64) unique not null,
    account_id   int                not null references accounts,
    visible_name varchar(64)        not null,
    description  text               not null,
    cost         numeric(10, 2)     not null check ( cost >= 0),
    quantity     int                not null check (quantity >= 0),
    primary key (id)
);


create table purchases
(
    id         int            not null default nextval('purchases_seq'::regclass),
    product_id int            not null references products,
    account_id int            not null references accounts,
    number     int                     default nextval('purchases_number_seq'::regclass),
    quantity   int            not null check (quantity >= 0),
    cost       numeric(10, 2) not null check (cost >= 0),
    primary key (id)
);
CREATE FUNCTION defaulter()
    RETURNS trigger AS
    $$
BEGIN
    IF
NEW.number IS NULL THEN
        NEW.number := nextval('purchases_number_seq'::regclass);
END IF;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER my_trigger
    BEFORE INSERT
    ON purchases
    FOR EACH ROW
    EXECUTE PROCEDURE defaulter();

