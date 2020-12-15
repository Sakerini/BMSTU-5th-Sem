--1
create or replace function get_age_by_id(id_ in number)
    return number is
    result number;
begin
    select AGE into result from USERs where ID = id_;
    return result;
end;


--2
create or replace type room_record as object
(
    room_name varchar2(100 char),
    room_cap  int
);
create or replace type room_record_table as table of room_record;

create or replace function get_room_by_user_id(id_ in number)
    return room_record_table is
    T room_record_table;
begin
    select room_record(CR.ROOM_NAME, CR.CAPACITY) bulk collect into T
    from USERS
             join PARTICIPANTS P on USERS.ID = P.USER_ID
             join CHAT_ROOMS CR on CR.ID = P.ROOM_ID
    where USERS.ID = id_;

    return T;
end;

select * from table (GET_ROOM_BY_USER_ID(10));
--6
create or replace type user_record as object
(
    username varchar2(100 char),
    userAge  int
);
create or replace type user_record_table as table of user_record;
-- Function 2
create or replace function getUsersFromRoom(cr_id in number)
    return user_record_table is
    T user_record_table;
begin
    select user_record(USERNAME, AGE) bulk collect
    into T
    from USERS
             join PARTICIPANTS P on USERS.ID = P.USER_ID
             join CHAT_ROOMS CR on CR.ID = P.ROOM_ID
    where CR.ID = cr_id;
    return T;
end;

select *
from table (getUsersFromRoom(5));

create table moderators(
    id Integer,
    userid integer,
    mod_level integer
);
drop table moderators;
select * from CHAT_ROOMS where ID = 1050;

select CAPACITY from CHAT_ROOMS where id = 3;


with username_count as (
select USERNAME, count(*) as user_count
    from USERS group by USERNAME )
select u.USERNAME as Username, uc.user_count from USERS u join username_count uc on u.USERNAME = uc.USERNAME;

create table temp (
    v_column_name   varchar2(50 char),
    v_table_name    varchar2(50 char),
    v_data_type     varchar2(50 char)
);
CREATE OR REPLACE PROCEDURE get_table_columns
(table_name in varchar2)
AS
begin
    INSERT INTO TEMP
        SELECT T.TABLE_NAME, T.COLUMN_NAME, T.DATA_TYPE
        FROM ALL_TAB_COLUMNS T
        WHERE T.TABLE_NAME = table_name;
end;

SELECT T.COLUMN_NAME, T.DATA_TYPE
        FROM ALL_TAB_COLUMNS T
        WHERE T.TABLE_NAME = 'USERS';

call get_table_columns('USERS');

SELECT USER
  FROM DUAL;
