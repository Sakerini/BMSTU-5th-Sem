
--1)
create extension plpython3u;

create or replace function get_name_by_id(id_ int) returns varchar
as $$
users = plpy.execute("select * from chat_scheme.users")
for user in users:
	if user['id'] == id_:
		return user['username']
return 'None'$$ language plpython3u;

create or replace function get_age(uid int) returns text
as $$
users = plpy.execute("select * from chat_scheme.users")
for user in users:
	if user['id'] == uid:
		return user['age']
$$ language plpython3u;

select * from get_age(10);

select age from chat_scheme.users where id = 10;

--2)

create or replace function count_room(cap int) returns int
as $$
plan = plpy.prepare("select count(*) as res from chat_scheme.chat_rooms where capacity < $1", ["int"])
res = plpy.execute(plan, [cap])
return res[0]['res'];
$$ language plpython3u;

select * from count_room(10);
select count(*) as res from chat_scheme.chat_rooms where capacity < 10;

drop function get_users_age(age_ int);
--3)
create or replace function get_users_age(age_ int)
returns table (id int, username varchar, password varchar, email varchar, gender char, age int)
as $$
users = plpy.execute("select * from chat_scheme.users")
res_table = []
if age_ < 0:
	return res_table
for u in users:
	if u['age'] == age_:
		res_table.append(u)
return res_table
$$ language plpython3u;

select *
from get_users_age(23);

--4)
create or replace procedure update_name(id_ int, newname text) as
$$
plan = plpy.prepare("update chat_scheme.users set username = $2 where id = $1",["int", "text"])
plpy.execute(plan, [id_, newname])
$$ language plpython3u;

call update_name(11,'xaxa');
select * from chat_scheme.users where id = 11;

update chat_scheme.users set username = 'Mare' where id = 10;

--5)
create table chat_scheme.changes_names(id_d integer,old_name text,new_name text);

create or replace function notice_updated_name()
returns trigger
as $$
plan = plpy.prepare("insert into chat_scheme.changes_names(id_d, old_name, new_name) values($1, $2, $3);", ["int", "text", "text"])
strnew = TD['new']
strold = TD['old']
rv = plpy.execute(plan, [strold["id"], strold["username"], strnew["username"]])
return 'Modify'
$$ language  plpython3u;

create trigger notice_updated
after update on chat_scheme.users for each row
execute procedure notice_updated_name();

update chat_scheme.users set username = 'oooma2ad' where id = 10;

select * from changes_names;

--6)
create type moderator as
(
	name text,
	email text,
	level integer
);
create or replace function get_moderator(id_ int) returns moderator
as $$
users = plpy.execute("select * from chat_scheme.users")
for u in users:
	if u['id'] == id_:
		return [u['username'], u['email'], u['age']]
$$ language plpython3u;

select * from get_moderator(5);


