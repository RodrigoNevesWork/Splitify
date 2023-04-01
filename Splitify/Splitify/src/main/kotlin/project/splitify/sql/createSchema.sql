create schema if not exists dbo;

create table if not exists dbo.User(
    id serial primary key,
    name text,
    email text unique,
    phone text unique,
    password text,
    token text unique
);

create table if not exists dbo.Trip(
    id serial primary key,
    location varchar(20)
);

create table if not exists  dbo.Purchase(
    id text unique,
    price numeric,
    description varchar(20),
    purchase_date date,
    user_id int references dbo.User(id),
    trip_id int references dbo.Trip(id)
);

create table if not exists dbo.User_Trip(
    user_id int not null,
    trip_id int not null,
    constraint unique_pair_constraint_user_trip UNIQUE (user_id, trip_id)
);

create table if not exists dbo.User_Purchase_Payed(
    user_id int not null,
    purchase_id text references dbo.Purchase(id)
);

create table if not exists dbo.friend_request(
    id text not null unique,
    user_id int references dbo.User(id),
    user_requesting int references dbo.User(id),
    constraint unique_pair_constraint_friend_request UNIQUE (user_id,user_requesting),
    constraint different_request check (user_id != user_requesting)
);

create table if not exists dbo.friend_list(
    user_id_1 int references dbo.User(id),
    user_id_2 int references dbo.User(id),
    primary key (user_id_1, user_id_2),
    constraint unique_pair_constraint_friend_list UNIQUE (user_id_1,user_id_2),
    constraint different_friends check (user_id_1 != user_id_2)
);





