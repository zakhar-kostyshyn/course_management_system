do $$
    declare
        created_user_id uuid := uuid_generate_v4();
        admin_id uuid := (select id from role where name = 'admin');
    begin
        insert into "user"(id, username, password) values (created_user_id, 'admin001', 'admin001');
        insert into user_role(role_id, user_id, predefined) values (admin_id, created_user_id, true);
    end $$;