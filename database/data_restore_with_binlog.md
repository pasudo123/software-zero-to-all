# MySQL binlog 활용, 데이터복구
- MySQL 데이터를 날려버릴때 복구가 필요하다.
- 이럴떄는 binlog 를 가지고 복구할 수 있다. (해당 데이터가 있다는 전제하에!!)

<BR>

## MySQL 환경
```
$ SHOW version();

+---------+
|version()|
+---------+
|8.0.34   |
+---------+
```

<BR>

## binary log
- 데이터베이스 변경사항을 기록하는데 사용. 특히 replication 또는 data recovery 작업에 중요한 역할.
- 데이터를 수정하지 않은 SELECT, SHOW 같은 명령어는 바이너리 로그에 기록되지 않음. 모든 명령을 기록하고자 할 때는 일반 쿼리로그를 사용.

<BR>

## binlog 존재여부 확인
```
// SHOW MASTER LOGS 커맨드도 가능함
$ SHOW binary LOGS;

+-------------+---------+---------+
|Log_name     |File_size|Encrypted|
+-------------+---------+---------+
|binlog.000008|10197    |No       |
|binlog.000009|157      |No       |
+-------------+---------+---------+
```
- 서버에 있는 바이너리 로그파일을 나열한다.
- 파일크기도 알 수 있으며, 파일 암호화 여부도 알 수 있다.
    - 만약에 서버에 binlog_enryption=ON 설정이 되어있다면 로그 암호화가 활성화된다.
    - 서버 운영시에는 바이너리 로그 암호화를 활성/비활성 하여도 기존 binlog 는 암호화/복호화 되지 않는다.

<BR>

## binlog 저장위치 확인
```
$ SHOW VARIABLES LIKE '%log_bin%';

// 아래결과값에서 log_bin_basename 를 확인한다.
+-------------------------------+---------------------------+
|Variable_name                  |Value                      |
+-------------------------------+---------------------------+
|log_bin                        |ON                         |
|log_bin_basename               |/var/lib/mysql/binlog      | // binlog 가 저장된 위치.
|log_bin_index                  |/var/lib/mysql/binlog.index| 
|log_bin_trust_function_creators|OFF                        |
|log_bin_use_v1_row_events      |OFF                        |
|sql_log_bin                    |ON                         |
+-------------------------------+---------------------------+
```
- log_bin_basename
    - 바이너리 로그 파일의 기본 이름과 경로를 보유
    - 기본위치는 데이터 디렉토리

<BR>

## Docker MySQL 접속 후 확인
```shell
bash-4.4# pwd
/var/lib/mysql
bash-4.4# ls -al | grep bin
-rw-r----- 1 mysql mysql    10197 May 10 23:21 binlog.000008
-rw-r----- 1 mysql mysql      157 May 10 23:21 binlog.000009
-rw-r----- 1 mysql mysql       32 May 10 23:21 binlog.index
```
- 컨테이너로 MySQL 서버로 접속, binlog 가 있는 위치에서 데이터 조회 시 두 개의 파일이 존재하는 걸 확인.

<BR>

## binlog 를 SQL 로 변환
```shell
root@69e6561e5dd0:/var/lib/mysql# mysqlbinlog binlog.000001 > binlog.000001.sql
root@69e6561e5dd0:/var/lib/mysql# mysqlbinlog binlog.000002 > binlog.000002.sql
```
- 위 명령어를 통해서 binlog 파일을 sql 파일로 변환한다.
    - 도커 컨테이너로 띄어진 MySQL 에 mysqlbinlog 명령어가 동작하지 않는 현상이 있다.
    - 특정 태그버전에서만 동작되도록 구현되어 있음. : 이 글의 주제가 아니기 때문에 생략한다. 
        - [관련내용은 여기서 참고](https://github.com/docker-library/mysql/issues/903#issuecomment-1204381831)
- sql 파일로 변환된 내용을 보면 실제 query 의 내용은 인코딩되어있어서 평문으로 보기 어렵다.

<BR>

## binlog 를 평문으로 조회
```shell
root@69e6561e5dd0:/var/lib/mysql# mysqlbinlog binlog.000002 -v --base64-output=DECODE-ROWS
```
- -v(=--verbose) 옵션을 주어서 평문처리한다.
- --base64-output=DECODE-ROWS 옵션은 bin 인코딩된 내용은 생략해서 보여주지 않도록 한다.
    - -v 옵션을 준채, --base64-output 설정 존재유무를 따지면 쉽게 파악할 수 있음

<BR>

## 데이터 DROP
```shell
$ use testdb;
$ show tables;

+-------------------+
|Tables_in_testdb   |
+-------------------+
|REVINFO            |
|book               |
|book_detail        |
|book_detail_history|
|book_history       |
|food               |
|library            |
|library_history    |
|post               |
|post_detail        |
|post_history       |
+-------------------+

$ SELECT * FROM food;
+--+-------------------+-------------------+-----+-----+--------+
|id|created_at         |modified_at        |name |state|sub_name|
+--+-------------------+-------------------+-----+-----+--------+
|1 |2024-05-12 13:12:03|2024-05-12 13:12:03|food1|GOOD |null    |
|2 |2024-05-12 13:12:05|2024-05-12 13:12:05|food2|GOOD |null    |
|3 |2024-05-12 13:12:09|2024-05-12 13:12:09|food3|GOOD |null    |
+--+-------------------+-------------------+-----+-----+--------+

$ drop table food;
```

<BR>

## 복구를 위해 데이터를 생성하고 진행
### 특정 데이터베이스 기준으로 SQL 생성
```shell
root@69e6561e5dd0:/var/lib/mysql# mysqlbinlog -d testdb binlog.000002 > testdb.sql
WARNING: The option --database has been used. It may filter parts of transactions, but will include the GTIDs in any case. If you want to exclude or include transactions, you should use the options --exclude-gtids or --include-gtids, respectively, instead.
```

### 특정 데이터베이스 및 종료시점을 기준으로 sql 생성 : drop table 명령문이 binlog 에 포함되어 제외시켜주려고 사용한다.
```shell
root@69e6561e5dd0:/var/lib/mysql# mysqlbinlog -d testdb --stop-datetime='2024-05-12 04:20:00' binlog.000002 > testdb.sql
```
- --start-datetime, --stop-datetime 옵션으로 특정 구간을 잘라서 처리할 수 있다.

### 복구시행 : 에러를 만나면 쿼리 수행이 멈춘다. 
```shell
root@69e6561e5dd0:/var/lib/mysql# mysql -u root -p < testdb.sql
Enter password:
ERROR 1050 (42S01) at line 40: Table 'book' already exists
```

### 복구시행 : 추가옵션 force 를 준다. : 에러는 발생하지만 쿼리실행은 다 된다.
```shell
root@69e6561e5dd0:/var/lib/mysql# mysql -u root -p -f < testdb.sql
root@69e6561e5dd0:/var/lib/mysql# mysql -u root -p -f < testdb.sql
Enter password:
ERROR 1050 (42S01) at line 40: Table 'book' already exists
ERROR 1050 (42S01) at line 64: Table 'book_detail' already exists
ERROR 1050 (42S01) at line 85: Table 'book_detail_history' already exists
ERROR 1050 (42S01) at line 106: Table 'book_history' already exists
ERROR 1050 (42S01) at line 152: Table 'library' already exists
ERROR 1050 (42S01) at line 173: Table 'library_history' already exists
ERROR 1050 (42S01) at line 194: Table 'post' already exists
ERROR 1050 (42S01) at line 214: Table 'post_detail' already exists
ERROR 1050 (42S01) at line 234: Table 'post_history' already exists
ERROR 1050 (42S01) at line 254: Table 'REVINFO' already exists
ERROR 1061 (42000) at line 271: Duplicate key name 'isbn_idx'
ERROR 1826 (HY000) at line 301: Duplicate foreign key constraint name 'FKaojxagnfmppd09k35kye5eph5'
ERROR 1826 (HY000) at line 318: Duplicate foreign key constraint name 'FKl1hmgccsvfwcxhem3qw6l7gpm'
ERROR 1826 (HY000) at line 335: Duplicate foreign key constraint name 'FK85i8r6upfm2tynsy2lypep8jv'
ERROR 1826 (HY000) at line 352: Duplicate foreign key constraint name 'FKd92uqgl83i480twt0b2j9jv75'
ERROR 1826 (HY000) at line 369: Duplicate foreign key constraint name 'FKbgjn1umosti65kop12nr2cdqd'
ERROR 1826 (HY000) at line 386: Duplicate foreign key constraint name 'FK46mm0e5earch2ws3ffhl533aa'
ERROR 1826 (HY000) at line 403: Duplicate foreign key constraint name 'FKlk8mybanusf82jocrxo86g22'
```

<BR>

## 복구결과
복구가 잘 되었음을 확인가능하다.
```shell
$ SELECT * FROM food;
+--+-------------------+-------------------+-----+-----+--------+
|id|created_at         |modified_at        |name |state|sub_name|
+--+-------------------+-------------------+-----+-----+--------+
|1 |2024-05-12 13:12:03|2024-05-12 13:12:03|food1|GOOD |null    |
|2 |2024-05-12 13:12:05|2024-05-12 13:12:05|food2|GOOD |null    |
|3 |2024-05-12 13:12:09|2024-05-12 13:12:09|food3|GOOD |null    |
+--+-------------------+-------------------+-----+-----+--------+
```

<BR>

## 참고자료
- [15.7.7.1 SHOW BINARY LOGS Statement](https://dev.mysql.com/doc/refman/8.0/en/show-binary-logs.html)
- [6.6.9 mysqlbinlog — Utility for Processing Binary Log Files](https://dev.mysql.com/doc/refman/8.0/en/mysqlbinlog.html)
- [1.6.4 Binary Logging Options and Variables](https://dev.mysql.com/doc/refman/8.0/en/replication-options-binary-log.html)