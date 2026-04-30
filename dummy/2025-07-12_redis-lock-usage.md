## 레디스를 이용한 분산락.
레디스를 이용한 분산락 기법에 대해 다양한 방법들을 살펴봅니다.
더 좋은 것들이 있다면 언제든 수정할 예정입니다. Redis 단일 인스턴스 기준의 간단한 락과 Redlock 알고리즘은 보장 범위가 다르므로 운영 환경의 장애 허용 수준에 맞춰 선택해야 합니다.

## SET NX PX + 랜덤 토큰 방식
```kotlin
const val LOCK_KEY = "lock:resource:user"

fun doSomething(userId: Long) {
    val lockValue = UUID.randomUUID().toString()
    val ttl = Duration.ofMinutes(1)
    val userLockKey = "$LOCK_KEY:$userId"
    
    // setIfAbsent 
    // 키 값 세팅 O -> true 반환
    // 키 값 세팅 X -> false 반환
    val acquired = redis.setIfAbsent(userLockKey, lockValue, ttl)
    
    if (acquired.not()) {
        // 락 점유 상태. throw or return
        return
    }

    try {
        // 작업실행
        process()
    } finally {
        releaseLock(userLockKey, lockValue)
    }
}
```
- `SET key value NX PX ttl` 형태로 락 획득과 TTL 설정을 하나의 명령으로 처리한다.
- uuid 를 가지게 함으로써, lock 의 소유권을 현재 요청들어온 request 로 특정.
    - 토큰 비교 없이 `DEL` 만 수행하면 TTL 만료 후 다른 요청이 새로 획득한 락을 이전 요청이 삭제할 수 있다.
    - UUID 도 가능하지만, Redis 문서에서는 모든 클라이언트와 락 요청 사이에서 충분히 유니크한 값을 사용하라고 설명한다.

## 2. Lua script 로 안전하게 해제
```kotlin
val releaseScript = """
    if redis.call("get", KEYS[1]) == ARGV[1] then
        return redis.call("del", KEYS[1])
    else
        return 0
    end
""".trimIndent()

fun releaseLock(userLockKey: String, lockValue: String) {
    redis.eval(releaseScript, keys = listOf(userLockKey), args = listOf(lockValue))
}
```
- 1번 방식에서 `redis.get()` 으로 값을 확인한 뒤 `redis.del()` 을 별도 호출하는 방식은 원자적이지 않다.
- 따라서 해제는 Lua script 로 값 비교와 삭제를 한 번에 수행해야 다른 클라이언트가 획득한 락을 지우지 않는다.
- Redis 8.4 이상에서는 `DELEX key IFEQ value` 로 같은 의도를 표현할 수 있지만, 이전 버전에서는 Lua script 를 사용한다.

## reference
- [Redis docs - Distributed Locks with Redis](https://redis.io/docs/latest/develop/clients/patterns/distributed-locks/)
- [Redis docs - SET command](https://redis.io/docs/latest/commands/set/)
