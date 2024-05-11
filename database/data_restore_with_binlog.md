# MySQL binlog 로 데이터 복구하기
- MySQL 데이터를 날려버릴때 복구가 필요하다.
- 이럴떄는 binlog 를 가지고 복구할 수 있다. (해당 데이터가 있다는 전제하에!!)

## MySQL 환경
```
$ SHOW version();

+---------+
|version()|
+---------+
|8.0.34   |
+---------+
```

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
- 작성중...

## Docker MySQL 접속 후 확인.
작성중...

## 참고자료
- [15.7.7.1 SHOW BINARY LOGS Statement](https://dev.mysql.com/doc/refman/8.0/en/show-binary-logs.html)
- [6.6.9 mysqlbinlog — Utility for Processing Binary Log Files](https://dev.mysql.com/doc/refman/8.0/en/mysqlbinlog.html)
- [https://dev.mysql.com/doc/refman/8.0/en/replication-options-binary-log.html](https://dev.mysql.com/doc/refman/8.0/en/replication-options-binary-log.html)