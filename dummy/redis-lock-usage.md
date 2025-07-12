## 레디스를 이용한 분산락.
레디스를 이용한 분산락 기법에 대해 다양한 방볍들을 살펴봅니다.   
더 좋은 것들이 있다면 언제든 수정할 예정입니다. (with chatGPT)  

## setnx + expire + uuid 방식
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
        val currentLockValue = redis.get(userLockKey)
        if (currentLockValue == lockValue) {
            redis.del(userLockKey)
        }
    }
}
```
- uuid 가지게 함으로써, lock 의 소유권을 현재 요청들어온 request 로 특정.
    - uuid 로 비교하지 않는다면 동일유저가 따닥으로 n번 동시요청이 들어올 경우에 race condition 이 발생.
    - (최초 요청 이후에 비교검사가 없기 때문에 lock key 가 del 처리된다.)

## 2. 루아스크립트 방식 (작성중)
```kotlin

```
- 1번 방식에서 redis.get(), redis.del() 은 비원자적인 연산이다. 따라서 원자적인 연산으로 하려면 루아스크립트로 처리를 해주어야 한다.
- 루아스크립트는 선택적 강화장치에 가깝다는 것이 chatGPT 의 의견.