create table if not exists homework (
    id uuid primary key,
    course_id uuid references course (id),
    name varchar(50) not null,
    description text not null,
    start_time timestamp not null,
    end_time timestamp not null
);

create index if not exists course_id_idx on homework (course_id);
create index if not exists start_time_idx on homework (start_time);
create index if not exists end_time_idx on homework (end_time);