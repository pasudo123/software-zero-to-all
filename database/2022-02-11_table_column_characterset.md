## mysql 테이블 및 컬럼에 대한 캐릭터셋 설정
* mysql 의 텍스트형(char, varchar, text) 에는 문자집합과 정렬이라는 것이 존재.
    * character : 자료형(캐릭터셋)
    * collate : 정렬

---
## Mysql 은 컬럼의 캐릭터셋 및 정렬 룰
#### 1) 컬럼에 캐릭터셋과 정령 모두 사용하고 있다면
```sql
CREATE TABLE t1
(
    col1 CHAR(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci
) CHARACTER SET latin1 COLLATE latin1_bin;
```
* 위의 구문에서 col1 컬럼의 character 와 collate 는 각각 `utf8` 와 `utf8_unicode_ci` 를 사용한다.

#### 2) 컬럼에 캐릭서셋만 설정되어 있다면
```sql
CREATE TABLE t1
(
    col1 CHAR(10) CHARACTER SET utf8
) CHARACTER SET latin1 COLLATE latin1_bin;
```
* 위의 구문에서 col1 컬럼의 character 가 utf8 인데, collate 는 컬럼에 설정된 chracter 의 `default collation` 을 따른다.
* `default collation` 은 https://dev.mysql.com/doc/refman/8.0/en/show-character-set.html 해당링크에서 확인할 수 있다.

#### 3) 컬럼에 정렬만 설정되어 있다면
```sql
CREATE TABLE t1
(
    col1 CHAR(10) COLLATE utf8_polish_ci
) CHARACTER SET latin1 COLLATE latin1_bin;
```
* 위의 구문에서 col1 컬럼의 character 는 컬럼에 설정된 `utf8_polish_ci` 와 연관된 `utf8` character 를 사용할 것이다.

#### 4) 컬럼에 캐릭터셋과 정렬 아무것도 설정되어 있지 않다면
```sql
CREATE TABLE t1
(
    col1 CHAR(10)
) CHARACTER SET latin1 COLLATE latin1_bin;
```
* 위의 구문에서 col1 컬럼의 character 는 `latin1` 이고 collate 는 `latin1_bin` 이다.
* 기본 테이블에 설정된 룰을 따른다.

## reference
* https://dev.mysql.com/doc/refman/8.0/en/charset-column.html
* [chracterset 과 collation 의 개념](https://sshkim.tistory.com/128)