alter table "user" add column raw_password varchar(255);
alter table user_aud add column raw_password varchar(255);
alter table user_aud add column raw_password_mod boolean;
