alter table student
add constraint chk_degree check (degree in ('бакалавр', 'магистр', 'специалист'));

alter table student
add constraint chk_form check (form in ('очная', 'заочная', 'вечерняя'));

alter table student
add constraint chk_basis check (basis in ('контрактная', 'бюджетная'));

/* it was possible to add tables for these columns but in this case constraints seem good too*/