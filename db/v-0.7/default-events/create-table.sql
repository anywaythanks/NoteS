insert into saga_events (id, name) values (0, 'SCHEDULED_INDEX');

GO

insert into saga_events (id, name) values (1, 'SCHEDULED_REINDEX');

GO

insert into saga_events (id, name) values (2, 'SCHEDULED_REMOVE_INDEX');

GO

insert into saga_events (id, name) values (3, 'SUCCESS_CREATED');

GO

insert into saga_events (id, name) values (4, 'SUCCESS_EDITED');

GO

insert into saga_events (id, name) values (5, 'SUCCESS_DELETED');

GO
insert into saga_events (id, name) values (6, 'ERROR_CREATED');

GO

insert into saga_events (id, name) values (7, 'ERROR_EDITED');

GO
insert into saga_events (id, name) values (8, 'ERROR_DELETED');

GO
insert into saga_events (id, name) values (9, 'FAILED');

GO
insert into saga_events (id, name) values (10, 'COMPENSATE');

GO


