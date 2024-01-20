create table if not exists course (
    id uuid primary key,
    name varchar(50) unique not null,
    description text not null
);