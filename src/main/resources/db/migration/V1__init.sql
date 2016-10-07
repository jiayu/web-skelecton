create table user (
	id bigint not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null
);

insert into user (id, first_name, last_name) values (1, 'Dave', 'Syer');
