/*
    Users - Messages     - one to many
    chat-Rooms - Messages   - one to many
    Users  - chat-Rooms       - many to many
 */
alter session set current_schema = C##SAKERINI;
CREATE TABLE chat_rooms
(
    id        INTEGER     NOT NULL,
    room_name VARCHAR(34) NOT NULL,
    capacity  INTEGER,
    password  VARCHAR(34)
);

CREATE TABLE users
(
    id        INTEGER      NOT NULL,
    username  VARCHAR(16)  NOT NULL,
    password  VARCHAR(34)  NOT NULL,
    email     VARCHAR(34),
    gender    CHAR(1),
    age       INT
);

CREATE TABLE messages
(
    id        INTEGER NOT NULL,
    room_id   INT     NOT NULL,
    user_id   INT     NOT NULL,
    message   VARCHAR2(4000),
    timestamp LONG
);

CREATE TABLE participants
(
    id      INTEGER NOT NULL,
    room_id INT     NOT NULL,
    user_id INT     NOT NULL
);