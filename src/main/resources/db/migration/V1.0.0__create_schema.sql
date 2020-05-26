create sequence hibernate_sequence start 1 increment 1;
create sequence seq_user start 1 increment 1;
create table revinfo (rev int4 not null, revtstmp int8, primary key (rev));
create table "user" (id int8 not null, created_at timestamp, entity_status varchar(255), guid uuid, updated_at timestamp, version int8, password varchar(255), role varchar(255), user_name varchar(255), "created_by_id" int8, "updated_by_id" int8, primary key (id));
create table user_aud (id int8 not null, rev int4 not null, revtype int2, created_at timestamp, created_at_mod boolean, entity_status varchar(255), entity_status_mod boolean, guid uuid, guid_mod boolean, updated_at timestamp, updated_at_mod boolean, password varchar(255), password_mod boolean, role varchar(255), role_mod boolean, user_name varchar(255), user_name_mod boolean, created_by_id int8, created_by_mod boolean, updated_by_id int8, updated_by_mod boolean, primary key (id, rev));
alter table if exists "user" add constraint user_username_uq_idx unique (user_name);
alter table if exists "user" add constraint FKafkm2kr97ap7c0nv9l59okoay foreign key ("created_by_id") references "user";
alter table if exists "user" add constraint FK4fol2gywb2a7tlsugg5vv9r92 foreign key ("updated_by_id") references "user";
alter table if exists user_aud add constraint FK89ntto9kobwahrwxbne2nqcnr foreign key (rev) references revinfo;
