# mysql log 조회하기

## [1] general_log 의 ON/OFF 여부를 확인한다.
```mysql
mysql> show variables like '%general%';
+------------------+---------------------------------+
| Variable_name    | Value                           |
+------------------+---------------------------------+
| general_log      | OFF                             |
| general_log_file | /var/lib/mysql/fe39edd43af2.log |
+------------------+---------------------------------+
```

## [2] general_log 의 상태를 ON 으로 변경한다.
```mysql
mysql> SET global general_log = on;
Query OK, 0 rows affected (0.00 sec)

mysql> show variables like '%general%';
+------------------+---------------------------------+
| Variable_name    | Value                           |
+------------------+---------------------------------+
| general_log      | ON                             |
| general_log_file | /var/lib/mysql/fe39edd43af2.log |
+------------------+---------------------------------+
```

## [3] general_log_file 에 있는 위치에 로깅을 tail -f 로 확인
아래처럼 확인되는 것을 볼 수 있다.
```mysql
root@fe39edd43af2:/var/lib/mysql# tail -f fe39edd43af2.log

2022-04-20T14:01:04.013392Z	   53 Quit
2022-04-20T14:01:04.013729Z	   54 Quit
2022-04-20T14:02:15.707342Z	   34 Query	show variables like '%general%'
2022-04-20T14:02:15.715335Z	   33 Query	SELECT st.* FROM performance_schema.events_statements_current st JOIN performance_schema.threads thr ON thr.thread_id = st.thread_id WHERE thr.processlist_id = 34
2022-04-20T14:02:15.718782Z	   33 Query	SELECT st.* FROM performance_schema.events_stages_history_long st WHERE st.nesting_event_id = 43
2022-04-20T14:02:15.721573Z	   33 Query	SELECT st.* FROM performance_schema.events_waits_history_long st WHERE st.nesting_event_id = 43
2022-04-20T14:02:46.393927Z	   55 Connect	root@localhost on  using Socket
2022-04-20T14:02:46.394901Z	   55 Query	select @@version_comment limit 1
2022-04-20T14:02:51.502483Z	   55 Query	show variables like '%general%'
2022-04-20T14:04:42.704127Z	   55 Query	SET global general_log = on
```
