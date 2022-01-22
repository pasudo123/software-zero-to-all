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
