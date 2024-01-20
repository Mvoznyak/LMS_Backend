create table if not exists student (
    id uuid primary key references user_account (id),
    group_id uuid references academic_group (id) not null,
    admission_year smallint not null,
    degree varchar(50) not null,
    form varchar(50) not null,
    basis varchar(50) not null
);