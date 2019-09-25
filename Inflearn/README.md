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
* [07. 연관관계 매핑 기초](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/07.%20%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84%20%EB%A7%A4%ED%95%91%20%EA%B8%B0%EC%B4%88.md)
* [08. 양방향 연관관계 매핑 시 주의점](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/08.%20%EC%96%91%EB%B0%A9%ED%96%A5%20%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84%20%EB%A7%A4%ED%95%91%20%EC%8B%9C%20%EC%A3%BC%EC%9D%98%EC%A0%90.md)
* [09. 다양한 연관관계 매핑](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/09.%20%EB%8B%A4%EC%96%91%ED%95%9C%20%EC%97%B0%EA%B4%80%EA%B4%80%EA%B3%84%20%EB%A7%A4%ED%95%91.md)
* [10. 고급매핑](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/10.%20%EA%B3%A0%EA%B8%89%EB%A7%A4%ED%95%91.md)
* [11. 프록시](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/11.%20%ED%94%84%EB%A1%9D%EC%8B%9C.md)
* [12. 즉시로딩과 지연로딩](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/12.%20%20%EC%A6%89%EC%8B%9C%EB%A1%9C%EB%94%A9%EA%B3%BC%20%EC%A7%80%EC%97%B0%EB%A1%9C%EB%94%A9.md)
* [13. 영속성 전이와 고아객체](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/13.%20%EC%98%81%EC%86%8D%EC%84%B1%20%EC%A0%84%EC%9D%B4%EC%99%80%20%EA%B3%A0%EC%95%84%EA%B0%9D%EC%B2%B4.md)

## 실전예제
* [실전예제 01](https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Inflearn/%EC%8B%A4%EC%A0%84%EC%98%88%EC%A0%9C%2001%20-%20%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD%20%EB%B6%84%EC%84%9D%EA%B3%BC%20%EA%B8%B0%EB%B3%B8%EB%A7%A4%ED%95%91.md)
  * 회원 / 주문 / 상품 / 주문상품

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
