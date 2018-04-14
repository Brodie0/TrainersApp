#drop table users;
create table users (user_id serial primary key,login varchar(255), password varchar(255));
create table history (login varchar(255), partner_login varchar(255), distance int unsigned, total_time time); 
#select * from users;
#select * from history;
