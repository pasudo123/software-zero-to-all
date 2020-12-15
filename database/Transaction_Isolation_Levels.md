## Transaction Isolation Levels In DBMS
격리성 수준은 트랜잭션 터리가 병렬 실행될 때 각 트랜잭션의 격리성을 결정하는 것이다. 트랜잭션 특징의 
격리성, Isolation 에 대해서 보면 아래의 그림과 같다.

![alt Isolation Levels](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Image/Isolation_Levels%20Image.png)
```
Transaction1 과 Transaction2 과 나란히 실행되었다는 것을 전제로 설명.
```
1.  __트랜잭션 1__ 이 데이터베이스의 레코드를 갱신했다.
2.  __트랜잭션 1__ 은 커밋하지 않았다. (오류가 발생하면 롤백해서 원래대로 돌아가는 불확정한 상태의 데이터이다.)
3.  __트랜잭션 2__ 가 __트랜잭션 1__ 에서 갱신한 데이터를 읽어오려고 한다. (읽어오려는 데이터는 커밋되지 않은 불확정한 데이터이다.)

__이때 트랜잭션 2는 갱신된 데이터를 읽어와도 되는가 하는 문제가 발생__   
모순되지 않게 처리하려면 트랜잭션 1이 커밋해서 확정된 후 데이터를 읽어와야 한다. 이처럼 트랜잭션 1과 트랜잭션 2가 나란히 실행되었을 때 모순되지 않게 처리하는 속성이 바로 __격리성 (Isolation)__ 이다.

## 병렬 수행되는 트랜잭션에서 데이터가 모순되는 대표적 세가지 상태
### __1.  Dirty Read__<BR />
다른 트랜잭션이 변경했지만, 아직 커밋하지 않은 데이터를 읽어내는 것이다. Dirty 는 흑백이 구분되지 않은 어중간한 데이터라는 느낌을 나타낸다.


### __2.  Unrepeatable Read__<BR />
트랜잭션 내에서 같은 데이터를 여러 번 읽을 때, 다른 트랜잭션이 해당 데이터를 `변경` 하면 이전에 읽은 데이터와 `다른` 데이터를 읽어내는 것이다. 같은 값을 반복해서 읽을 수 없다는 의미에서 __Unrepeatable__ 이라는 표현이 사용되었다.

### __3.  Phantom Read__<BR />
트랜잭션 내에서 같은 데이터를 여러 번 읽을 때, 다른 트랜잭션이 새로 레코드를 `추가` 하면 새로이 추가된 레코드를 읽어내는 것이다. Phantom 은 유령이라는 의미로 없던 내용이 다시 나타남을 생각하면 된다.


## 격리성 (Isolcation) 수준의 종류
|                  독립성 수준                     |           의미            |
|------------------------------------------------|--------------------------|
| __Isolation : Read UnCommitted__ |다른 트랜잭션이 변경하였고, 아직 커밋하지 않은 데이터도 읽어낼 수 `있음`|
| __Isolation : Read Committed__ |다른 트랜잭션이 변경하였지만, 커밋을 하지 않았기 때문에 읽을 수가 `없음`. 대신 커밋 수행 시 읽어들일 수 있음|
| __Isolation : Repeatable Read__ |트랜잭션 내에서 여러 번 데이터를 읽어올 때, <BR /> 다른 트랜잭션이 도중에 데이터를 갱신해도 여전히 같은 값을 읽어낼 수 있음 (이렇게 하는 이유는 스냅샷이 특정시점에 기록되기 때문에) |
| __Isolation : Serializable__ |트랜잭션을 하나씩 순서대로 처리해서 독립시킴|

## 격리성 수준(isolation level) 과 데이터가 모순된 상태 (O : 허용, X : 불허)
|           독립성 수준          |   Dirty Read   |    Unrepeatable Read     |    Phantom Read    |
|------------------------------|------|------|------|
| __Isolation Read UnCommitted__ | O | O | O |
| __Isolation Read Committed__ | X | O | O |
| __Isolation Repeatable Read__ | X | X | O (mysql 에서 X 이기도 함) |
| __Isolation Serializable__ | X | X | X |
   
아래로 내려올수록 모순된 상태를 허용하지 않고 격리성의 수준은 높아진다. 여기서 항상 격리성이 높은 __Isolation Serializable__ 을 생각할 수 있지만, 격리성이 높아지면 그만큼 성능이 나빠진다. 
* 격리성이 높아지다는 것은 트랜잭션이 데이터에 동시에 접근하는 동시성이 떨어진다. 결국 이는 퍼포먼스 하락으로 이어진다. 반면 데이터는 일관된다.
* 추가적으로 다른 블로그의 글을 살펴보니 mysql 의 `REPEATABLE READ` 에서 `phantom read` 또한 발생되지 않는다고 하였다. 왜 그런지에 대한 설명은 실제 mysql document 에 보면 나와있었다.

### `READ COMMITTED` 
> Each consistent read, even within the same transaction, sets and reads its own fresh snapshot.

### `REPEATABLE READ` 
> Consistent reads within the same transaction read the snapshot established by the first read.
* 첫번째 읽어들인 스냅샷을 가지고 쿼리의 결과를 읽어들이기 때문.


## 추가설명
* InnoDB 의 디폴트 isolation level 은 `REPEATABLE READ` 이다.
* 유저는 `SET TRANSACTION` 을 통해서 싱글세션 또는 모든 subsequent connction 에 대해서 isolation level 을 설정할 수 있다. (자세한 내용은 하단 링크 참고)
* `Consistent read` (일관된 읽기)
    * `read operation` 은  쿼리의 결과를 나타내기 위해서 특정시점 기준의 `snapshot` 정보를 사용한다. 다른 트랜잭션이 동시에 살행되는 것과 상관이 없이 특정시점의 `snapshot` 을 이용하는 것이다. 만약에 데이터가 다른 트랜잭션에 의해서 변경되면 오리진 데이터는 `undo log` 에 의해서 재구성된다. 이와 같은 `consistent read 기술` 은 `concurrency` 를 감소시키는 `locking` 이슈를 회피할 수 있다.
    * `Consistent read` 는 InnoDB 가 `READ COMMITED` 와 `REPEATABLE READ` 격리수준에서 SELECT 문을 처리하는 기본모드이다.


```
결국 격리성의 수준과 동시 처리 성능에 대해 적절히 이해하고 트랜잭션을 사용하여야 한다.
```

## reference
* [transaction isolation level](https://dev.mysql.com/doc/refman/8.0/en/innodb-transaction-isolation-levels.html)
* [set transaction statement](https://dev.mysql.com/doc/refman/8.0/en/set-transaction.html#set-transaction-access-mode)
* [누군가의 글 : isolation level](https://jupiny.com/2018/11/30/mysql-transaction-isolation-levels/)