## redis cache 전략
레디스를 이용한 캐시전략. 애플리케이션 성능을 높인다.

## Lazy Loading
- 데이터 요청이 있을 때만 캐싱. 즉 최소 1번은 데이터를 조회해야만 캐싱이
- 최초 조회 시 DB 에 직접 요청하므로 느리다는 단점이 존재. (Cache Miss 시에 약간의 지연이 발생할 수 있음)
- 데이터가 갱신되었을 때 기존에 캐싱된 데이터만 처리하고, 불필요한 데이터를 캐시하지 않음.
```kotlin
fun getData(key: String): String {
		return redisTemplate.opsForValue().get(key) ?: run {
        val value = database.findByKey(key)
        redisTemplate.opsForValue().set(key, value)
        value
    }
}
```

## Write Through
- 애플리케이션은 데이터베이스에 쓰는 로직 + 캐시에 저장하는 로직
- 캐시와 데이터베이스 간의 데이터 일관성이 보장. 
- 데이터 베이스 쓰기 작업 시에 약간의 성능이 발생하는 단점. -> 이 부분은 별도 비동기로가도 상관없을걸로 판단
```kotlin
fun writeData(key: String, value: String) {
    database.save(key, value)
    redisTemplate.opsForValue().set(key, value)
}
```

## Pre Caching
- 로컬캐시를 사용하는 경우에 유용
- 리모트캐시의 경우에는 필요치 않을 수 있음
- 로컬캐시는 각 애플리케이션 인스턴스가 자체 메모리를 들고있으면서 데이터를 관리
- 중요한 데이터의 경우에는 인스턴스별로 자체 데이터를 가진 상태로 로드되는 것이 성능에 이점이 있기 때문이다.

## 개인생각
- Lazy Loading 과 Write Through 를 섞어서 처리하는 방향.
- 일반적인 요청은 Lazy Loading 방식을 이용하고 이후에 어드민에서 데이터 조작 후 수정/변경 작업 발생 시 Write Through 작업으로 처리. 추가로 TTL 반영을 함으로써 캐시와 디비간의 데이터 이격을 좁힘.