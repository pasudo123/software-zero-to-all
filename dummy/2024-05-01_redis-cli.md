## redis cli
파편화된 정보들은 모은다.

### raw 데이터 조회
hex 형태로 표시된 데이터를 raw 한 데이터 형태로 조회할 떄 이용한다.
```shell
## 아래와 같이 접속하여 커맨드 실행.
$ redis-cli --raw -h {host} -p {port}
```

### key pattern 을 이용하여 벌크 삭제.
데이터 툴로 특정 키 패턴을 삭제한다. `|` 와 xargs 커맨드를 같이 이용한다.
```shell
$ redis-cli -h ${host} -p ${port} -a ${pwd} -n ${db} --scan --pattern "key:*" | xargs redis-cli -h ${host} -p ${port} -a ${pwd} -n ${db} unlink
```