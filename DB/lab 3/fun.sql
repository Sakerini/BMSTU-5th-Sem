-- function 1
create or replace function getAge(user_id in number)
    return number is
    result number(10, 2);
begin
    select AGE into result from USERS where ID = user_id;
    return result;
end;

select ID, getAge(ID)
from USERS;
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

--function 3

create or replace function getRoomNameById(room_id in int)
return varchar2
is result varchar2(242);
begin
    if room_id < 1 then
        RAISE_APPLICATION_ERROR(1, 'id can not be nagative');
    end if;

    select CR.ROOM_NAME into result
        from CHAT_ROOMS CR
    where CR.ID = room_id;

    return result;
end;

select getRoomNameById(3) as NAME from CHAT_ROOMS where CHAT_ROOMS.ID = 3;

--function 4

CREATE OR REPLACE FUNCTION Fib(n IN BINARY_INTEGER)
  RETURN BINARY_INTEGER AS
BEGIN
  IF n = 0 OR n = 1 THEN
    RETURN n;
  ELSE
    RETURN Fib(n - 1) + Fib(n - 2);
  END IF;
END Fib;
