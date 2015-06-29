-- findById
insert into t_role(id, name) values(1, 'admin');
insert into t_role(id, name) values(2, 'member');

-- permissions
insert into t_permission(id, name) value(101, 'Permission101');
insert into t_permission(id, name) value(102, 'Permission102');
insert into t_permission(id, name) value(103, 'Permission103');

insert into t_role_permission(role_fk, permission_fk) values(1, 101);
insert into t_role_permission(role_fk, permission_fk) values(1, 102);

