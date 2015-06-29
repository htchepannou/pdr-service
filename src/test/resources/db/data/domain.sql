-- findById
insert into t_domain(id, name, description, deleted) values(100, 'admin.moralab.com', 'Admin site', 0);
insert into t_domain(id, name, description, deleted) values(101, 'www.moralab.com', 'Main site', 0);
insert into t_domain(id, name, description, deleted) values(102, 'api.moralab.com', 'API site', 0);
insert into t_domain(id, name, description, deleted) values(103, 'deleted.moralab.com', 'API site', 1);

-- update
insert into t_domain(id, name, description, deleted) values(200, 'update.me', null, 0);
insert into t_domain(id, name, description, deleted) values(201, 'update.me-deleted', null, 1);

-- delete
insert into t_domain(id, name, description, deleted) values(300, 'delete.me', null, 0);
