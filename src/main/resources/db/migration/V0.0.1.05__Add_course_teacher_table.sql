create table if not exists course_teacher (
    course_id uuid not null references course (id) ,
    teacher_id uuid not null references user_account (id),
    primary key (course_id, teacher_id)
);

create index if not exists course_id_idx on course_teacher (course_id);
create index if not exists teacher_id_idx on course_teacher (teacher_id);