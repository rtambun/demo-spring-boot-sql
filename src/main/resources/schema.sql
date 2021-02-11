create table locationinfo (
    id int AUTO_INCREMENT primary key,
    name varchar(255) not null,
    location varchar(255) not null,
    latitude decimal(8,6) not null,
    longitude decimal(9,6) not null
);