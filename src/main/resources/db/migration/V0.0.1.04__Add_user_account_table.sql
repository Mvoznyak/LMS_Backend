create table if not exists user_account (
    id uuid primary key,
    verification_code varchar(50) default MD5(random()::text) not null,
    email varchar(350) unique null,
    password_hash varchar(350) null,
    first_name varchar(50) null,
    middle_name varchar(50) null,
    last_name varchar(50) null,
    phone_number varchar(50) null,
    city varchar(50) null,
    info text null,
    vk_link varchar(350) null,
    facebook_link varchar(350) null,
    linkedin_link varchar(350) null,
    instagram_link varchar(350) null
);

create index if not exists email_idx on user_account (email);