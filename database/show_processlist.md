## show processlist
Mysql 서버내에서 실행된 스레드 합에서 현재 수행 중인 작업들을 나타낸다.
```sql
## full 키워드가 없을경우 info column 에 대해서 앞에 100자리만 노출한다.
$ show processlist

$ show full processlist
```



## reference
* https://dev.mysql.com/doc/refman/8.0/en/show-processlist.html