create table if not exists group_course (
    group_id uuid references academic_group (id),
    course_id uuid references course (id),
    primary key (group_id, course_id)
);

create index if not exists group_id_idx on group_course (group_id);