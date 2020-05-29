INSERT INTO "user" ("id", "created_at", "entity_status", "guid", "updated_at", "version", "password", "role",
                    "user_name")
select nextval('seq_user'),
       now(),
       'ACTIVE',
       md5(random() :: text || clock_timestamp() :: text) :: uuid     as guid,
       now(),
       0,
       '$2a$10$3sJ1YDCEybVK/zVcIWCk6O8OLsEh7aRTuom1l7sNtI4szAuu7u5Zm' as password, -- 123456
       'USER',
       'test-user@apm.com';


INSERT INTO "user" ("id", "created_at", "entity_status", "guid", "updated_at", "version", "password", "role",
                    "user_name")
select nextval('seq_user'),
       now(),
       'ACTIVE',
       md5(random() :: text || clock_timestamp() :: text) :: uuid     as guid,
       now(),
       0,
       '$2a$10$3sJ1YDCEybVK/zVcIWCk6O8OLsEh7aRTuom1l7sNtI4szAuu7u5Zm' as password, -- 123456
       'OPERATOR',
       'test-admin@apm.com';
