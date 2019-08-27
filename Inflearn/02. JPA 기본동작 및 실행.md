# JPA 기본동작 및 실행
JPA 의 기본동작을 알기 위해선 세가지를 알아야한다.
- EntityManagerFactory
- EntityManager
- EntityTransaction


## EntityManagerFactory
* 하나만 생성되며, 애플리케이션 전체에서 공유된다.
* WAS 가 내려갈 때 커넥션 풀링 등 리소스가 릴리즈된다.

## EntityManager
* 스레드간 공유하지 않으며, 사용되고 이후에 close() 된다.
* 자원을 다 쓰고, 항상 닫아주기


## EntityTransaction
__JPA 안의 모든 데이터들은 트랜잭션 범위 내에서 실행하여야 한다.__ 만약 특정한 엔티티를 조회하고 persist() 또는 save() 하지 않더라도 커밋시 변경사항이 있으면 자동으로 업데이트 해준다.


## JPQL (= 객체 지향 SQL)
- __JPA 는 SQL 을 추상화한 객체 지향 쿼리를 제공__
- JPQL 은 엔티티 객체를 대상으로 쿼리실행 (SQL DB 테이블을 대상으로 쿼리실행)
- JPQL 로 전체회원 조회
```java
List<Member> memberList = entityManager.createQuery("select m from Member as m", Member.class).getResultList();

for(Member member : memberList){
  blahblahblah...
}
```

* 검색을 하는경우, 테이블이 아닌 엔티티 객체를 대상으로 검색
* 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
* 애플리케이션이 필요한 데이터만 DB 에서 조회하기 위해선 결과적으로 검색 조건이 포함된 SQL이 필요
