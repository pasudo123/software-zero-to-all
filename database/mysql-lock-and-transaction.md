# mysql 의 잠금과 트랜잭션
* 잠금은 동시성을 제어하기 위한 기능
  * 여러 커넥션이 동시에 하나의 자원을 요청하는 경우, 순서에 따라서 한 시점에 하나의 커넥션만 변경시킬 수 있도록 한다.
* 트랜잭션은 데이터 정합성을 보장하기 위한 기능
  * 하나의 트랜잭션 내에서 또는 여러 트랜잭션 간의 작업내용을 어떻게 공유하고 차단할 것인지 결정하는 레벨이다.

## 트랜잭션 주의사항
코드레벨에서 트랜잭션을 적용할 때, 최소한의 단위로만 트랜잭션을 적용할 수 있도록 한다. 왜나하면 트랜잭션을 물고 있는 시간이 길어질수록 사용가능한 여유 커넥션이 줄어들기 때문이다.   
그렇게 되면 각 단위의 프로그램에서 사용할 커넥의 개수가 줄어들 수 있다. (다른 부분에서 커넥션을 길게 물고있기 때문이다.)

## mysql engine 락

## reference
* real mysql
