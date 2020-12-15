create table TriggerTest
(
    id    int primary key,
    value int
);

/* --- DML тригеры  --- */
-- Тригер AFTER

CREATE OR REPLACE TRIGGER check_trigger
    BEFORE INSERT ON TriggerTest
    FOR EACH ROW
begin
    if :new.value > 100 then
        raise_application_error(12334, 'value should be less or equal to 100');
    end if;
end check_trigger;


insert into TriggerTest values (3, 100);
insert into TriggerTest values (4, 101);

-- Тригер INSTEAD OF
CREATE OR REPLACE TRIGGER DenyDelete
    BEFORE DELETE ON TriggerTest FOR EACH ROW
begin
    RAISE_APPLICATION_ERROR(-20001, 'Record can not be deleted');
end;


DELETE FROM TriggerTest where id = 3;

-- написать тригер при апдейте выводим информация о старом строке и новой
CREATE OR REPLACE TRIGGER update_trigger
  BEFORE UPDATE ON TriggerTest
  FOR EACH ROW
BEGIN
    dbms_output.PUT_LINE('Old: ' || :old.value);
    dbms_output.PUT_LINE('New: ' || :new.value);
END;

select * from TriggerTest;
update TriggerTest set value = 20 where id = 3;
