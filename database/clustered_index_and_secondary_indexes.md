## clustered index & secondary indexes

mysql8.0 기준, 인덱스를 만들지 않은 테이블에 한해 클러스터드 인덱스 조회하는 방법
```sql
mysql> SELECT VERSION();
+-----------+
| VERSION() |
+-----------+
| 8.0.27    |
+-----------+
1 row in set (0.00 sec)

## item 테이블을 인덱스 없이 만듦
mysql> CREATE TABLE item(name VARCHAR(200));

## employee db 에 item 테이블은 GEN_CLUST_INDEX 라를 명칭을 가진 기본 인덱스가 만들어짐 (PK 가 없어서 만들어졌다.)
mysql> SELECT t.name as table_name, i.name as index_name
    -> from information_schema.INNODB_TABLES t
    -> join information_schema.INNODB_INDEXES i using (table_id)
    -> where i.name = 'GEN_CLUST_INDEX';

+-------------------+-----------------+
| table_name        | index_name      |
+-------------------+-----------------+
| employees/item    | GEN_CLUST_INDEX |
+-------------------+-----------------+
3 rows in set (0.00 sec)
```
* 만약 테이블이 PK 나 적당한 UNIQUE INDEX 가 없다면, InnoDB 는 행 ID 값을 포함하는 `GEN_CLUST_INDEX` 라는 히든 클러스터드 인덱스를 생성한다.
* 행은 행 ID 로 정렬된다.


## reference
* https://dev.mysql.com/doc/refman/8.0/en/innodb-index-types.html
* https://stackoverflow.com/questions/54446161/innodb-find-clustered-index-in-existing-table