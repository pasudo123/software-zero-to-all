# JPA 란
* JdbcTemplate 혹은 마이바티스의 등장으로 코드는 줄었음
* 이후에 JPA 가 등장하고 JPA 를 사용하면 쿼리를 작성할 필요가 없음
* 마치 자바 컬렉션의 객체를 저장하고 조회하는 것처럼 단순하게 JPA 를 이용할 수 있음

# 목차
* [01. JPA 소개](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/01.%20JPA%20%EC%86%8C%EA%B0%9C.md)
* [02. JPA 기본 동작 및 실행](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/02.%20JPA%20기본동작%20및%20실행.md)
* [03. 영속성 컨텍스트](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/03.%20%EC%98%81%EC%86%8D%EC%84%B1%20%EC%BB%A8%ED%85%8D%EC%8A%A4%ED%8A%B8.md)
* [04. 플러시(flush)](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/04.%20%ED%94%8C%EB%9F%AC%EC%8B%9C(flush).md)
* [05. 준영속상태](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/05.%20%EC%A4%80%EC%98%81%EC%86%8D%EC%83%81%ED%83%9C.md)
* [06. 엔티티매핑](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/06.%20%EC%97%94%ED%8B%B0%ED%8B%B0%EB%A7%A4%ED%95%91.md)

# JPA 실무가 어려운 이유
* 예제들은 보통 테이블이 한 두개로 단순하다.
* 실무는 수십 개 이상의 복잡한 객체와 테이블 사용

# 목표 : 객체와 테이블 설계
* __객체와 테이블을 설계하고 매핑__
* __기본 키와 외래 키 매핑__
* __1:N, N:1, N:< 매핑__
* __실무 노하우 + 성능 고려__
* __어떤 복잡한 시스템도 JPA 로 구현가능__

# 목표 : JPA 내부 동작 방식 이해
* __JPA 내부 동작을 그림과 코드로 자세히 설명__
* __JPA 가 어떤 SQL 을 만들어내는지 이해__
* __JPA 가 언제 SQL 을 실행하는지 이해__
