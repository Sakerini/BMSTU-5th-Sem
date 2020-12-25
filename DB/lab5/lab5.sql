--1. Из таблиц бд извлечь данные в JSON.

copy (select to_json(users)
      from chat_scheme.users)
    to '/home/sakerini/University/DB/res.json';

--2. Выполнить загрузку и сохранение JSON файла в таблицу.

create table json_import
(
    data json
);
drop table json_import;
copy json_import from '/home/sakerini/University/DB/res.json';
select *
from json_import;

drop type users;
create type users as
(
    id       integer,
    username text,
    pass     text,
    email    text,
    gender   char,
    age      integer
);

drop table copy_u;
create table copy_u
(
    id_       integer,
    username_ text,
    pass_     text,
    email_    text,
    gender_   char,
    age_      integer
);
insert into copy_u
select copy_users.*
from json_import,
     json_populate_record(null::users, json_import.data) as copy_users;
select *
from copy_u;

--3.  Создать таблицу, в которой будет атрибут(-ы) с типом JSON .Заполнить атрибут правдоподобными данными с помощью команд INSERT или UPDATE.
create table user_info_table
(
    id_d      integer not null primary key,
    user_info json    not null
);

insert into user_info_table(id_d, user_info)
values (1,
        '{
          "username": "gerge",
          "password": "999",
          "age": 15
        }'),
       (2,
        '{
          "username": "uewea",
          "password": "1234",
          "age": 55
        }'),
       (3,
        '{
          "username": "sakerini",
          "password": "990010",
          "age": 20
        }');

select * from user_info_table;

--4.1 Извлечь JSON фрагмент из JSON документа
select user_info
from user_info_table;

--4.2  Извлечь значения конкретных атрибутов JSON документа
select user_info-> 'age' as age
from user_info_table

--4.3 Выполнить проверку существования узла или атрибута
select id_d,user_info,
case
	when json_typeof(user_info_table.user_info->'age') = 'null' then 'Not exist'
	else 'Exist'
	end as check
from user_info_table;
--4.4 Изменить JSON документ

update user_info_table
set user_info = '{
          "username": "rocky",
          "password": "99030",
          "age": 25
        }'::json
where id_d = 2;

select * from user_info_table;

--4.5  Разделить JSON документ на несколько строк по узлам
drop type usertype;
create type usertype as (username text, pass text, age integer);
