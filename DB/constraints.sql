/* PK's */
alter session set current_schema = C##SAKERINI;
ALTER TABLE chat_rooms ADD(CONSTRAINT chat_room_pk PRIMARY KEY (id));
ALTER TABLE users ADD(CONSTRAINT user_pk PRIMARY KEY (id));
ALTER TABLE messages ADD(CONSTRAINT message_pk PRIMARY KEY (id));
ALTER TABLE participants ADD(CONSTRAINT participant_pk PRIMARY KEY (id));
/* FK'S */
ALTER TABLE messages ADD(CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE);
ALTER TABLE messages ADD(CONSTRAINT chat_room_fk FOREIGN KEY (room_id) REFERENCES chat_rooms(id) ON DELETE CASCADE );
ALTER TABLE participants ADD(CONSTRAINT participant_user_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE);
ALTER TABLE participants ADD(CONSTRAINT participant_chat_room_fk FOREIGN KEY (room_id) REFERENCES chat_rooms(id) ON DELETE CASCADE);

/*Other Constaints*/
ALTER TABLE users ADD(CONSTRAINT age_restrict CHECK ( age >= 16 ));