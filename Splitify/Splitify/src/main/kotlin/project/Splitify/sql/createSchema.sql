create schema if not exists dbo;

create table if not exists dbo.User(
    id serial primary key,
    password text,
    name text,
    email text,
    phone text,
    token text
);

create table if not exists dbo.Trip(
    id serial primary key,
    location varchar(20)
);

create table if not exists  dbo.Purchase(
    id uuid,
    price numeric,
    description varchar(20),
    user_id int references dbo.User(id),
    trip_id int references dbo.Trip(id)
);

create table dbo.User_Trip(
    user_id int,
    trip_id int
);

create table dbo.User_Purchase_Payed(
    user_id int,
    purchase_id uuid
);


