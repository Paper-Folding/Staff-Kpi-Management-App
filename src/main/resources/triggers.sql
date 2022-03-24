-- on delete a role, delete its corresponding role_scope(s)
create trigger before_role_delete
    before delete
    on role
    for each row
begin
    delete from user_roles where role_id = OLD.id;
    delete from role_scope where role_id = OLD.id;
end;