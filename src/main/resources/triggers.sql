-- on delete a role, delete its corresponding role_scope(s)
create trigger before_role_delete
    before delete
    on role
    for each row
begin
    delete from user_roles where role_id = OLD.id;
    delete from role_scope where role_id = OLD.id;
end;

create trigger after_staff_insert
    after insert
    on staff_info
    for each row
begin
    insert into authentication(staff_info_id, username, password)
    values (NEW.id, NEW.no, '$2a$10$on2WJ2SbqiwXeLJLnnaoGuZN7H2/cT5ay/DX9t8IwmwblyhAWkYSq'); -- 123456
end;

create trigger after_authentication_insert
    after insert
    on authentication
    for each row
begin
    insert into user_roles(authentication_id, role_id) values (NEW.id, 3);
end;