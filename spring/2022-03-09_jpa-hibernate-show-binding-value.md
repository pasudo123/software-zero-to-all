## spring jpa 에서 hibernate 바인딩된 데이터 조회하기

아래와 같이 작성 (필요 값만)
```yml
spring:
  jpa:
    show-sql: true
    properties.hibernate.format_sql: true

logging:
  level:
    org.hibernate.type.descriptor.sql: TRACE
```

결과는 아래와 같이 노출된다.
```console
Hibernate: 
    insert 
    into
        member
        (id, created_at, modified_at, age, name) 
    values
        (null, ?, ?, ?, ?)
2022-03-09 13:28:56.263 TRACE 20085 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [TIMESTAMP] - [2022-03-09T13:28:56.220703]
2022-03-09 13:28:56.271 TRACE 20085 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [TIMESTAMP] - [2022-03-09T13:28:56.220703]
2022-03-09 13:28:56.272 TRACE 20085 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [3] as [INTEGER] - [55]
2022-03-09 13:28:56.273 TRACE 20085 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [4] as [VARCHAR] - [세종대왕]
```