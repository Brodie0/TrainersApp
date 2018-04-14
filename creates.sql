#drop table users;
create table users (id serial primary key, login varchar(20), password varchar(50), name varchar(20), lastname varchar(40), email varchar(60));
create table history (id serial primary key, user int not null REFERENCES users(id), partner int REFERENCES users(id), distance int unsigned, total_time time); 
#select * from users;
#select * from history;
