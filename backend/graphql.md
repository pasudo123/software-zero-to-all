# graphql (= gql)
Graph QL 이란 sql 과 마찬가지로 쿼리언어이다.   
`sql` 의 경우에는 데이터베이스 시스템에 저장된 데이터를 효율적으로 가져오는 것이 목적이다.   
* sql 의 문장은 주로 백엔드 시스템에서 작성하고 호출된다.

`gql` 의 경우에는 웹 클라이언트가 데이터를 서버로부터 효율적으로 가져오는 것이 목적이다.   
* gql 의 문장은 주로 클라이언트 시스템에서 작성하고 호출된다.

### sql 쿼리
```shell
SELECT plot_id, species_id, sex, weight, ROUND(weight / 1000.0, 2) FROM surveys;
```

### gql 쿼리
```shell
{
  hero {
    name
    friends {
      name
    }
  }
}
```

## reference
* https://tech.kakao.com/2019/08/01/graphql-basic/
