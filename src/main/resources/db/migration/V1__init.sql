create table user (
	id int not null auto_increment,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	primary key (id)
);

insert into user (first_name, last_name) values ('Dave', 'Syer');
