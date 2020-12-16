# truncate
mysql truncate 알아보기

## truncate table statement
* 테이블을 완벽하게 지운다.
* `drop` 권한이 우선적으로 필요한데, 논리적으로 모든 데이터 로우를 `delete` 하거나 또는 `drop table` 과 `create table` 순차적으로 수행하는 구문이다.
* 높은 퍼포먼스를 수행하기 위해서 `truncate` 를 데이터 삭제 `dml method` 를 우회한다. 그러므로 `on delete` trigger 가 발생하지 않는다.
* `fk` 가 관계가 있는 테이블에 대해서는 수행할 수 없다.
    * 수행 시, `cannot truncate a table referenced in foreign key contraints ~ ` 라고 작성되며 수행되지 않는다.
* `rollback` 을 수행할 수 없다.
* 만약 테이블이 `atomic ddl-supported storage engine` 에 대해서 `truncate` 를 한다고 했을 때, 서버가 작동 중에 멈춰버리면, 완전히 커밋되거나 완전히 롤백이 된다.
* `truncate` 는 `delete` 와 유사하지만 DML 보다는 `DDL` 로 분류된다.
    * truncate operation 은 drop and re-create table 을 수행하는데, 테이블이 큰 경우 데이터를 row one by one 으로 삭제하는 것보다 빠르다.
    * truncate operation 은 `implicit commit` 을 발생시키고 `rollback` 을 수행하지 않는다.
    * truncate operation 은 `active table lock` 에 대해서 수행할 수 없다.
    * truncate operation 은 데이터 로우 삭제에 대한 의미있는 값을 반환하지 않는다. 일반적인 결과로 `0 rows affected` 로 해석되고 나타난다.
    * `auto_increment` 는 시작값으로 재설정된다. `MyISAM` 또는 `InnoDB` 에서도 마찬가지이고 시퀀스 값을 재사용하지 않는다.
* `file-per-table tablespace` 에 있는 InnoDB 테이블을 truncate 하면, 현재 존재하는 `tablespace`를 `drop` 하고 새로운 것을 `create` 한다.

# reference
* https://dev.mysql.com/doc/refman/8.0/en/truncate-table.html
* [on delete trigger example](https://www.mysqltutorial.org/mysql-triggers/mysql-after-delete-trigger/)