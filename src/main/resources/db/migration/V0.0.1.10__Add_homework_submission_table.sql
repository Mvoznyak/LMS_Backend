create table if not exists homework_submission (
    homework_id uuid references homework (id) not null,
    student_id uuid references user_account (id) not null,
    send_time timestamp not null,
    submission text not null,
    primary key (homework_id, student_id)
);

create index if not exists homework_id_idx on homework_submission (homework_id);
create index if not exists student_id_idx on homework_submission (student_id);