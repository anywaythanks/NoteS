create table accounts
(
    id       int      not null auto_increment primary key unique,
    name     char(64) not null unique,
    nickname char(64) not null unique
);

create table notes
(
    id         int       not null auto_increment primary key unique,
    account_id int       not null,
    title      char(128) not null unique,
    content    text,
    created_on timestamp not null,
    foreign key (account_id) references accounts (id)
);

insert into accounts value (1, 'u1', 'n1');
insert into notes (account_id, title, content, created_on)
values (1, '5', '', current_timestamp()),
       (1, '6', '', current_timestamp()),
       (1, '7', '', current_timestamp()),
       (1, '8', '', current_timestamp());

create or replace view sliceTime as
select id, title, content, created_on
from notes
where created_on between
              (select created_on from notes where id = 1)
          and
              (select created_on from notes where id = 2);


CREATE PROCEDURE clear(IN val datetime)
begin
    delete from notes where datediff(created_on, val) <= 0;
end;

create function concat_note(id_c int) returns text
begin
    RETURN (select trim(concat(concat(title, '\n'), content))
            from sliceTime
            where id = id_c);
end;

create function search(regex text) returns int
begin
    RETURN (select id
            from sliceTime
            where (content REGEXP regex) > 0
            limit 1);#limit is bad... maybe
end;

select *
from notes;
call clear('2024-06-14 10:09:07');
select *
from notes;
set @sid = search('[0-9]{4}');
select @sid;
set @con = concat_note(@sid);
select @con;
