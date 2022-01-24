## show processlist
Mysql 서버내에서 실행된 스레드 모음에서 현재 수행 중인 작업들을 나타낸다.
```sql
## full 키워드가 없을경우 info column 에 대해서 앞에 100자리만 노출한다.
$ show processlist

$ show full processlist
```

> show processlist 는 `too many connections` 라는 문구를 만났을 때 유용하게 사용할 수 있다.   
> 해당 명령어를 통해서 커넥션에 어떤문제가 있는지 확인할 수 있다.   
> 스레드는 `kill` 명령어를 통해 죽일 수 있다.

show processlist 에 대한 컬럼
```sql
mysql> show processlist;
+----+-----------------+--------------------+--------------------+---------+-------+------------------------+------------------+
| Id | User            | Host               | db                 | Command | Time  | State                  | Info             |
+----+-----------------+--------------------+--------------------+---------+-------+------------------------+------------------+
|  5 | event_scheduler | localhost          | NULL               | Daemon  | 36722 | Waiting on empty queue | NULL             |
| 11 | root            | localhost          | performance_schema | Query   |     0 | init                   | show processlist |
| 12 | root            | 192.168.99.1:62659 | testdb             | Sleep   |     2 |                        | NULL             |
| 13 | root            | 192.168.99.1:62660 | testdb             | Sleep   |     2 |                        | NULL             |
+----+-----------------+--------------------+--------------------+---------+-------+------------------------+------------------+
```
* Id
    * 커넥션 식별자. 
* User
* Host
    * 명령문을 실행하는 클라이언트 {host}:{port} 로 구분한다.
* db
    * 스레드의 기본 데이터베이스, null 이라면 아무것도 선택하지 않은 상태를 의미한다.
* Command
    * 클라이언트를 대신하여 스레드가 실행하는 명령타입.
* Time
* State
    * 스레드가 수행 중인 작업을 나타내는 조치. 이벤트 또는 상태이다.
    * state 에 대한 상세한 내용은 [해당링크](https://dev.mysql.com/doc/refman/8.0/en/general-thread-states.html)를 살핀다.
* Info
    * 스레드가 실행중인 명령문.
    * 스레드가 실행한 명령문이 없는 경우에는 NULL 을 표현한다.

## reference
* https://dev.mysql.com/doc/refman/8.0/en/show-processlist.html