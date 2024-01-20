# LMS

**LMS** is a learning management system,
мы будем считать, что сценарии использования системы администратором подразумевают прямое подключение к базе данных 
и редактирование имеющихся таблиц. Путем создания учебной группы, создания учебного курса, предварительной регистрации,
И пуьличного апи аутентификация, регистрация, редактирование профиля, просмотр курсов, управление материалами курса.

### Running

To run **LMS** installed [sbt] is required.
While sbt downloading create db via docker-compose file or programms like dbBeaver or Pgadmin with settings just like in
src/main/resources/application.conf. Applied dump from db.sql
Once [sbt] is installed, run
```console
user@host:~/LMS$ sbt run
```
in the root directory of **LMS**.

[sbt]: https://www.scala-sbt.org/



### Static Code Analysis

Static code analysis may be done with [Scalastyle] in the root directory of **LMS**:

```console
user@host:~/LMS$ sbt scalastyleGenerateConfig scalastyle
```

[Scalastyle]: http://www.scalastyle.org/
