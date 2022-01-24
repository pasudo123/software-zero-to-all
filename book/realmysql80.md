# real mysql 8.0
real mysql 을 읽으면서 기록.

### 0.0 테스트를 위한 mysql 설치
```yml
version: '3.7'
services:
  db:
    image: library/mysql:8.0
    command: --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
    restart: always
    environment:
      - MYSQL_ROOT_PASSWORD=rootpass
      - MYSQL_ROOT_HOST=%
      - MYSQL_DATABASE=testdb
    container_name: realmysql-container
    ports:
      - "33666:3306"
```

### 1.0 아키텍처
<kbd>
  <img alt="" src="../Image/realmysq8.0_chapter04_1.drawio.png" />
</kbd>

### 2.0 트랜잭션과 잠금
#### 2.1 트랜잭션
* InnoDB 와 MyISAM 의 두 스토리지 엔진간 commit 및 rollback 차이
  * InnoDB 는 트랜잭션 원칙에 입각하여 전체다 커밋되거나 아니면 에러 발생 시 롤백된다.
  * MyISAM 은 같은 트랜잭션 그룹이더라도, 일부만 커밋되는 Partial Update 현상을 보인다.
* 트랜잭션의 작업의 단위를 `최소화` 할 수 있도록 한다.
  * 단순 조회의 경우에 트랜잭션을 사용하여야 하는지 확인한다.
  * 트랜잭션들 사이에 외부 시스템과 연동되는 경우 트랜잭션에 포함시킬지 여부를 판단해야 한다.

#### 2.2 mysql 엔진잠금 (p.160)
* `MySQL 엔진 잠금은 스토리지 엔진 전체에 영향을 끼침`
* `스토리지 엔진 잠금은 스토리지 엔진 간 상호 영향을 끼치지 않음`
* 글로벌락, 테이블락(묵시적, 명시적), 네임드락, 메타데이터 락등이 존재한다.

#### 2.3 InnoDB 스토리지 잠금 (p.166)
* InnoDB 스토리지 잠금에는 네가지가 존재한다.
  * __1) 레코드 락__
    * 레코드 자체만을 잠그는 것을 의미한다.
    * InnoDB 스토리지 엔진에서는 레코드가가 아닌 `인덱스의 레코드를 잠근다.`
  * 2) 갭 락
    * 레코드와 인접한 레코드 사이의 간격만을 잠그는 것을 의미한다.
  * 3) 넥스트 키 락
    * 레코드 락과 갭 락을 합쳐놓은 형태의 잠금을 의미한다.
  * 4) 자동증가 락
    * `AUTO_INCREMENT 락` 이라고 하는 테이블 수준의 잠금을 사용한다.
    * `AUTO_INCREMENT 락` 은 아주 짧은 시간동안 걸렸다가 해제되는 잠금이라서 대부분의 경우 문제가 되지 않는다.
    * mysql5.1 이상부터는 `innodb_autoinc_lock_mode` 라는 시스템변수를 이용해 자동증가 락의 작동방식을 변경할 수 있다. (p.169)
      * innodb_autoinc_lock_mode=0 : 자동증가락 사용
      * innodb_autoinc_lock_mode=1 : 선택적
      * innodb_autoinc_lock_mode=2 : 래치 (뮤텍스), 가볍고 빠름
* InnoDB 의 잠금은 레코드를 잠그는 것이 아니라 `인덱스를 잠그는 방식` 으로 처리된다.
  * 즉 변경하여야할 레코드를 찾기위해 검색한 인덱스의 레코드를 모두 락을 걸어야 한다.
  * 경우에 동시성이 상당히 떨어질 수 있다. (p.171)
* 잠금을 해제하기 위해선 processlist 에서 kill 명령을 통해 해당하는 pid 를 죽여야 한다.

### 20.0 인덱스를 잘못 적용한 케이스
index 값을 조작한 경우
```sql
## salary 는 인덱스 설정이 되어있음

## index full scan
$ SELECT * FROM salaries WHERE salary * 10 > 150000; 

## index range scan
$ SELECT * FROM salaries WHERE salary > 150000; 
$ SELECT * FROM salaries WHERE salary > 15000 * 10; 
```

index 값을 조회 시, 데이터 타입이 잘못된 경우
```sql
CREATE TABLE tb_test(age VARCHAR(10), index ix_age(age));
 
INSERT INTO tb_test VALUES ('1');
INSERT INTO tb_test VALUES ('2');
INSERT INTO tb_test VALUES ('3');
INSERT INTO tb_test VALUES ('4');
INSERT INTO tb_test VALUES ('5');
INSERT INTO tb_test VALUES ('6');

# index full scan 수행
SELECT * FROM tb_test WHERE age = 2;

# index 를 제대로 탔음 : Non_Unique 인덱스를 탔다는 의미
SELECT * FROM tb_test WHERE age = '2';
```
