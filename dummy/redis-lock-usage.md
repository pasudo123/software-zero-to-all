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
