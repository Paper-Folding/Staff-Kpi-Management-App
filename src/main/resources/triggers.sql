-- on delete a role, delete its corresponding role_scope(s)
create trigger before_role_delete
    before delete
    on role
    for each row
begin
    delete from user_roles where role_id = OLD.id;
    delete from role_scope where role_id = OLD.id;
end;

-- add default authentication with password 123456 after staff info added
create trigger after_staff_insert
    after insert
    on staff_info
    for each row
begin
    insert into authentication(staff_info_id, username, password)
    values (NEW.id, NEW.no, '$2a$10$on2WJ2SbqiwXeLJLnnaoGuZN7H2/cT5ay/DX9t8IwmwblyhAWkYSq'); -- 123456
end;

-- add initial role after authentication added
create trigger after_authentication_insert
    after insert
    on authentication
    for each row
begin
    insert into user_roles(authentication_id, role_id) values (NEW.id, 3);
end;

-- delete authentication on staff disable
create trigger before_staff_disable
    before update
    on staff_info
    for each row
begin
    if NEW.disabled = '1' then
        delete from authentication where staff_info_id = NEW.id;
    end if;
end;

-- delete user roles on authentication delete
create trigger before_authentication_delete
    before delete
    on authentication
    for each row
begin
    delete from user_roles where authentication_id = OLD.id;
end;