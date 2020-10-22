/* 1. Инструкция SELECT, использующая предикат сравнения. */
/* имя , пол и название 2 рой комнаты */
SELECT DISTINCT USERNAME, GENDER, CR.ROOM_NAME
FROM USERS
         JOIN PARTICIPANTS P on USERS.ID = P.USER_ID
         JOIN CHAT_ROOMS CR on CR.ID = P.ROOM_ID
WHERE P.ROOM_ID = 2
ORDER BY USERNAME;

/* 2. Инструкция SELECT, использующая предикат BETWEEN. */
/* имя, возраст, и пол, пользователей между 20-22 лет */
SELECT DISTINCT USERNAME, AGE, GENDER
FROM USERS
WHERE AGE BETWEEN 20 AND 22;

/* 3. Инструкция SELECT, использующая предикат LIKE. */
/* все пользователи у которых имя начинается на А и заканчивается на а */
SELECT USERNAME
FROM USERS
WHERE USERNAME LIKE ('A%a');

/* 4. Инструкция SELECT, использующая предикат IN с вложенным подзапросом. */
/* пользователи между 18 и 20 лет */
SELECT U.USERNAME, MESSAGE
FROM MESSAGES
         JOIN USERS U on U.ID = MESSAGES.USER_ID
WHERE USER_ID IN (
    SELECT DISTINCT ID
    FROM USERS
    WHERE AGE BETWEEN 18 AND 20);

/* 5. Инструкция SELECT, использующая предикат EXISTS с вложенным подзапросом. */
SELECT CR.*
FROM CHAT_ROOMS CR
WHERE EXISTS(
              SELECT *
              FROM CHAT_ROOMS
                       JOIN MESSAGES M on CHAT_ROOMS.ID = M.ROOM_ID
              WHERE LENGTH(M.MESSAGE) < 20
          );
/* 6. Инструкция SELECT, использующая предикат сравнения с квантором. */
/* пользователи в первой комнате у которых возраст больше чем среднаяя возраст 4 той комнате */
SELECT USERNAME, AGE
FROM USERS
         JOIN PARTICIPANTS P on USERS.ID = P.USER_ID
         JOIN CHAT_ROOMS CR on CR.ID = P.ROOM_ID
WHERE AGE > ALL (
    SELECT AVG(AGE)
    FROM USERS
             JOIN PARTICIPANTS P on USERS.ID = P.USER_ID
             JOIN CHAT_ROOMS CR on CR.ID = P.ROOM_ID
    WHERE CR.ID = 4)
  AND CR.ID = 1;

/* 7. Инструкция SELECT, использующая агрегатные функции в выражениях столбцов. */
select sum(AGE) as sum_age, count(age) as count_age, avg(AGE) as average_age
from (select *
      from USERS
               join PARTICIPANTS P on USERS.ID = P.USER_ID
               join CHAT_ROOMS CR on CR.ID = P.ROOM_ID
      where CR.ID = 2);

/* 8. Инструкция SELECT, использующая скалярные подзапросы в выражениях столбцов. */
/* средная возраст 2 рой комнате*/
SELECT ROOM_NAME,
       (
           SELECT AVG(AGE)
           FROM USERS
                    JOIN PARTICIPANTS P on USERS.ID = P.USER_ID
                    JOIN CHAT_ROOMS CR on CR.ID = P.ROOM_ID
           WHERE CR.ID = 2
       ) AS AVERAGE_ROOM_AGE
FROM CHAT_ROOMS
WHERE ID = 2;

/* 9. Инструкция SELECT, использующая простое выражение CASE. */
/* проверка возрасти на 30 */

SELECT USERNAME,
       AGE,
       CASE
           WHEN AGE >= 30 THEN 'AGE APPROVED'
           ELSE 'AGE NOT APPROVED' END
           AS AGE_RESTRICTION
FROM USERS;


/* 11. Создание новой временной локальной таблицы из результирующего набора данных инструкции SELECT. */
CREATE GLOBAL TEMPORARY TABLE TEMP_TABLE AS
SELECT USERNAME, CR.ROOM_NAME
FROM USERS
         JOIN PARTICIPANTS P on USERS.ID = P.USER_ID
         JOIN CHAT_ROOMS CR on CR.ID = P.ROOM_ID;

SELECT *
FROM TEMP_TABLE;
DROP TABLE TEMP_TABLE;

/* 12. Инструкция SELECT, использующая вложенные коррелированные подзапросы
   в качестве производных таблиц в предложении FROM.*/

SELECT *
FROM CHAT_ROOMS CR
         JOIN (
    SELECT USERNAME, P.ROOM_ID
    FROM USERS
             JOIN PARTICIPANTS P ON USERS.ID = P.ID
) U ON CR.ID = U.ROOM_ID;

/* 13. Инструкция SELECT, использующая вложенные подзапросы с уровнем вложенности 3. */
/* максимальная возразт каждой комнате */
SELECT Z.ROOM_NAME, Z.MAX_AGE
FROM (
         SELECT ID AS ROOM_ID, ROOM_NAME, K.MAX_AGE
         FROM CHAT_ROOMS
                  JOIN (
             SELECT ROOM_ID, MAX(U.AGE) AS MAX_AGE
             FROM PARTICIPANTS
                      JOIN USERS U on PARTICIPANTS.USER_ID = U.ID
                      JOIN CHAT_ROOMS CR on CR.ID = PARTICIPANTS.ROOM_ID
             GROUP BY ROOM_ID) K ON CHAT_ROOMS.ID = K.ROOM_ID) Z;

/* 14. Инструкция SELECT, консолидирующая данные с помощью предложения GROUP BY, но без предложения HAVING. */
/* пересчитать одинаковые имена */
SELECT USERNAME, COUNT(ID) AS QUANTITY
FROM USERS
GROUP BY USERS.USERNAME;

/* 15. Инструкция SELECT, консолидирующая данные с помощью предложения GROUP BY и предложения HAVING. */
/* пересчитать имена с А% */
SELECT USERNAME, COUNT(ID) AS QUANTITY
FROM USERS
GROUP BY USERS.USERNAME
HAVING USERNAME LIKE ('A%');

/* 16. Однострочная инструкция INSERT, выполняющая вставку в таблицу одной строки значений. */
INSERT INTO CHAT_ROOMS
VALUES (1001, 'TopChat', 100, 'qwerty1234');

/* 17. Многострочная инструкция INSERT, выполняющая вставку в таблицу результирующего набора данных вложенного подзапроса. */
INSERT INTO CHAT_ROOMS
SELECT (SELECT MAX(ID) + 1 FROM CHAT_ROOMS), 'BESTChat', 100, 'qwerty1234'
FROM CHAT_ROOMS CR
WHERE ROWNUM = 1;

/* 18. Простая инструкция UPDATE. */
UPDATE CHAT_ROOMS
SET ROOM_NAME = 'THEBESTCHAT'
WHERE ID = 1001;

/* 19. Инструкция UPDATE со скалярным подзапросом в предложении SET. */

UPDATE CHAT_ROOMS
SET ROOM_NAME = (
    SELECT ROOM_NAME
    FROM CHAT_ROOMS
    ORDER BY ID DESC FETCH NEXT 1 ROWS ONLY
)
WHERE ID = 1001;

/* 20. Простая инструкция DELETE. */
DELETE CHAT_ROOMS
WHERE ID = 1001;

/* 21. Инструкция DELETE с вложенным коррелированным подзапросом в предложении WHERE. */
SELECT ID
FROM USERS
WHERE AGE = 60;
DELETE USERS
WHERE ID = (
    SELECT ID
    FROM USERS
    WHERE AGE = 60
);

/* 22. Инструкция SELECT, использующая простое обобщенное табличное выражение */

WITH TEST (ID, USERNAME, GENDER) AS (
    SELECT ID, USERNAME, GENDER
    FROM USERS
)

SELECT *
FROM TEST;

/* 23. Инструкция SELECT, использующая рекурсивное обобщенное табличное выражение. */

create table category
(
    id              integer      not null primary key,
    name            varchar(100) not null,
    parent_category integer references category
);

INSERT INTO category
values (1, 'Root Node', null);
INSERT INTO category
values (2, 'Software', 1);
INSERT INTO category
values (3, 'Hardware', 1);
INSERT INTO category
values (4, 'Notebooks', 3);
INSERT INTO category
values (5, 'Phones', 3);
INSERT INTO category
values (6, 'Applications', 2);
INSERT INTO category
values (7, 'Database Software', 2);
INSERT INTO category
values (8, 'Relational DBMS', 7);
INSERT INTO category
values (9, 'Tools', 7);
INSERT INTO category
values (10, 'Command Line Tools', 9);
INSERT INTO category
values (11, 'GUI Tools', 9);
INSERT INTO category
values (12, 'Android Phones', 5);
INSERT INTO category
values (13, 'IPhone', 5);
INSERT INTO category
values (14, 'Windows Phones', 5);

WITH recursive (id, name, parent_category, depth) as (
    SELECT id, name, parent_category, 0 AS depth
    FROM category
    WHERE name = 'Software' -- start of recursion
    UNION ALL
    SELECT child.id, child.name, child.parent_category, depth + 1
    from category child
             join recursive parent on parent.id = child.parent_category -- the self join build up the recursion
    WHERE depth < 1 -- control depth ( <= 1 )
)
SELECT *
from recursive;


drop table category;

/* 24. Оконные функции. Использование конструкций MIN/MAX/AVG OVER() */
SELECT MAX(AGE) AS MAX_AGE, MIN(AGE) AS MIN_AGE
FROM USERS;
SELECT USERNAME, AGE, AVG(AGE) OVER ( PARTITION BY USERNAME) AS AVG_AGE, MAX(AGE)
FROM USERS
GROUP BY (USERNAME, AGE);

/* 25 */

Create table myRooms as
select *
from CHAT_ROOMS
union all
select *
from CHAT_ROOMS
union all
select *
from CHAT_ROOMS;

select *
from myRooms where id = 587;

delete
from myRooms
where rowid in (select rid
                from (select rowid rid,
                             row_number() over (partition by
                                 ID, ROOM_NAME, CAPACITY, PASSWORD order by ROWID) rn
                      from myRooms)
                where rn <> 1);

drop table myRooms;

/* Дополнительное задание

Создать таблицы:

- Table1{id: integer, var1: string, valid_from_dttm: date, valid_to_dttm: date}
- Table2{id: integer, var2: string, valid_from_dttm: date, valid_to_dttm: date}

Версионность в таблицах непрерывная, разрывов нет (если valid_to_dttm = '2018-09-05', то
для следующей строки соответсвующего ID valid_from_dttm = '2018-09-06', т.е. на день
больше). Для каждого ID дата начала версионности и дата конца версионности в Table1 и
Table2 совпадают.
Выполнить версионное соединение двух талиц по полю id.
 */

CREATE TABLE Table1
(
    id              INTEGER,
    var1            VARCHAR2(20 CHAR),
    valid_from_dttm DATE,
    valid_to_dttm   DATE
);

CREATE TABLE Table2
(
    id              INTEGER,
    var2            VARCHAR2(20 CHAR),
    valid_from_dttm DATE,
    valid_to_dttm   DATE
);

INSERT INTO Table1 (ID, VAR1, VALID_FROM_DTTM, VALID_TO_DTTM)
VALUES (1, 'A', TO_DATE('2018-09-01', 'yyyy-mm-dd'), TO_DATE('2018-09-15', 'yyyy-mm-dd'));
INSERT INTO Table1 (ID, VAR1, VALID_FROM_DTTM, VALID_TO_DTTM)
VALUES (1, 'B', TO_DATE('2018-09-16', 'yyyy-mm-dd'), TO_DATE('5999-12-31', 'yyyy-mm-dd'));
INSERT INTO Table1 (ID, VAR1, VALID_FROM_DTTM, VALID_TO_DTTM)
VALUES (3, 'A', TO_DATE('2018-09-01', 'yyyy-mm-dd'), TO_DATE('2018-09-20', 'yyyy-mm-dd'));
INSERT INTO Table1 (ID, VAR1, VALID_FROM_DTTM, VALID_TO_DTTM)
VALUES (3, 'B', TO_DATE('2018-09-21', 'yyyy-mm-dd'), TO_DATE('2018-09-25', 'yyyy-mm-dd'));
INSERT INTO Table1 (ID, VAR1, VALID_FROM_DTTM, VALID_TO_DTTM)
VALUES (3, 'C', TO_DATE('2018-09-26', 'yyyy-mm-dd'), TO_DATE('5999-12-31', 'yyyy-mm-dd'));
INSERT INTO Table1 (ID, VAR1, VALID_FROM_DTTM, VALID_TO_DTTM)
VALUES (2, 'B', TO_DATE('2018-09-16', 'yyyy-mm-dd'), TO_DATE('5999-12-31', 'yyyy-mm-dd'));


INSERT INTO Table2 (ID, VAR2, VALID_FROM_DTTM, VALID_TO_DTTM)
VALUES (1, 'A', TO_DATE('2018-09-01', 'yyyy-mm-dd'), TO_DATE('2018-09-18', 'yyyy-mm-dd'));
INSERT INTO Table2 (ID, VAR2, VALID_FROM_DTTM, VALID_TO_DTTM)
VALUES (1, 'B', TO_DATE('2018-09-19', 'yyyy-mm-dd'), TO_DATE('5999-12-31', 'yyyy-mm-dd'));
INSERT INTO Table2 (ID, VAR2, VALID_FROM_DTTM, VALID_TO_DTTM)
VALUES (3, 'A', TO_DATE('2018-09-01', 'yyyy-mm-dd'), TO_DATE('2018-09-24', 'yyyy-mm-dd'));
INSERT INTO Table2 (ID, VAR2, VALID_FROM_DTTM, VALID_TO_DTTM)
VALUES (3, 'B', TO_DATE('2018-09-25', 'yyyy-mm-dd'), TO_DATE('5999-12-31', 'yyyy-mm-dd'));
/* ---------------------------------- */


select Table1.id
     , Table1.var1
     , Table2.var2
     , case
           when Table1.VALID_FROM_DTTM < Table2.VALID_FROM_DTTM then Table2.VALID_FROM_DTTM
           else Table1.VALID_FROM_DTTM end as VALID_FROM_DTTM
     , case
           when Table1.VALID_TO_DTTM > Table2.VALID_TO_DTTM then Table2.VALID_TO_DTTM
           else Table1.VALID_TO_DTTM end   as VALID_TO_DTTM
from Table1
         inner join Table2 on Table1.id = Table2.id and Table1.VALID_FROM_DTTM < Table2.valid_to_dttm and
                              Table2.valid_from_dttm < Table1.VALID_TO_DTTM;

select Table1.id
     , Table1.var1
     , Table2.var2
     , case
           when Table1.VALID_FROM_DTTM < Table2.VALID_FROM_DTTM then Table2.VALID_FROM_DTTM
           else Table1.VALID_FROM_DTTM end as VALID_FROM_DTTM
     , case
           when Table1.VALID_TO_DTTM > Table2.VALID_TO_DTTM then Table2.VALID_TO_DTTM
           else Table1.VALID_TO_DTTM end   as VALID_TO_DTTM
from Table1
         full outer join  Table2 on Table1.id = Table2.id and Table1.VALID_FROM_DTTM < Table2.valid_to_dttm and
                              Table2.valid_from_dttm < Table1.VALID_TO_DTTM + Interval '23:59:59' hour to second;

DROP TABLE Table1;
DROP TABLE Table2;
