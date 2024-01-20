create table if not exists course_moderator (
    course_id uuid not null references course (id),
    moderator_id uuid not null references user_account (id),
    primary key (course_id, moderator_id)
);

create index if not exists course_id_idx on course_moderator (course_id);
create index if not exists moderator_id_idx on course_moderator (moderator_id);