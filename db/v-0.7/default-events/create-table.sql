insert into saga_events (id, name) values (0, 'INIT');

GO

insert into saga_events (id, name) values (1, 'CREATE IN ELASTIC');

GO

insert into saga_events (id, name) values (2, 'COMMIT IN DATABASE');

GO

insert into saga_events (id, name) values (3, 'COMMIT IN ELASTIC');

GO

insert into saga_events (id, name) values (4, 'DONE');

GO

insert into saga_events (id, name) values (5, 'RECOMMIT IN ELASTIC');

GO

insert into saga_events (id, name) values (6, 'DELETE IN ELASTIC');

GO

insert into saga_events (id, name) values (7, 'FAILED');

GO

