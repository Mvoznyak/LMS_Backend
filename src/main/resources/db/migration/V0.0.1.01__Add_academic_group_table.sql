create table if not exists academic_group (
    id uuid primary key,
    name varchar(50) unique not null,
    faculty varchar(50) not null,
    study_year smallint not null
);