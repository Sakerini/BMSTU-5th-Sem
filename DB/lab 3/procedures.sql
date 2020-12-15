/* --- Процедуры --- */
-- Хранимую процедуру без параметров или с параметрами
create or replace procedure insert_room(cr_id in int, cr_name in varchar2, cr_capacity in int, cr_password in varchar2)
    is
begin
    insert into CHAT_ROOMS values (cr_id, cr_name, cr_capacity, cr_password);
end;

begin
    insert_room(9999, 'testroom', 15, 'testroompassword');
end;

select *
from CHAT_ROOMS
where id = 9999;

-- Рекурсивную хранимую процедуру или процедуры с рекурсивным OTB
CREATE OR REPLACE PROCEDURE FACTORIAL_TEST AS
  FUNCTION FACTORIAL(N NUMBER)
    RETURN NUMBER AS
BEGIN
    IF N <= 1 THEN
      RETURN 1;
    ELSE
      RETURN N * FACTORIAL(N-1);
    END IF;
END FACTORIAL;
begin
  DBMS_OUTPUT.PUT_LINE('3! = ' ||
    TO_CHAR(FACTORIAL(3)));
  DBMS_OUTPUT.PUT_LINE('10! = ' ||
    TO_CHAR(FACTORIAL(10)));
  DBMS_OUTPUT.PUT_LINE('64! = ' ||
    TO_CHAR(FACTORIAL(64)));
end;

call FACTORIAL_TEST();

-- Хранимую процедуру с курсором
CREATE OR REPLACE PROCEDURE getUserCursor (
    user_like IN USERS.USERNAME%type,
    c_user OUT SYS_REFCURSOR)
AS
begin

    OPEN c_user FOR
        SELECT * FROM USERS U WHERE U.USERNAME LIKE2 user_like;

end;

declare
    cur_user          sys_refcursor;
    temp_user          USERS%ROWTYPE;
begin
    getUserCursor('%Alfredo', cur_user);

    loop
        FETCH cur_user INTO temp_user;
        EXIT WHEN cur_user%NOTFOUND;

        dbms_output.put_line(temp_user.USERNAME);
    end loop;

    close cur_user;
end;

-- Хранумыю процедуру доступа к метаданным
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
SELECT * FROM ALL_TAB_COLUMNS;

call get_table_columns('USERS');
SELECT * FROM temp;
DELETE FROM temp;
drop table temp;

DROP PROCEDURE FACTORIAL_TEST;

