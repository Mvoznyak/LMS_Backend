create table if not exists course_material (
    id uuid primary key,
    course_id uuid not null references course (id),
    name varchar(50) not null,
    material text not null,
    created_on timestamp not null
);

create index if not exists created_on_idx on course_material (created_on);