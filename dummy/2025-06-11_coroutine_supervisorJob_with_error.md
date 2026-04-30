## 개요
coroutineScope 에서 Job 이 동작하지 않는다.   
왜 그런걸까? launch {} 블럭이 수행되지 않음.

## 현상
아래의 코드가 있다.   
throw exception 이 발생하고 이후에 해당 API 가 재실행되면 요청은 들어가지만 launch {} 가 실행되지 않는다.
```kotlin
@RestController
@RequestMapping("do-something")
class ConvertController {

    private val log = LoggerFactory.getLogger(ConvertController::class.java)
    private val ioCoroutineScope = CoroutineScope(Dispatchers.IO) // <-- 전역 코루틴 스코프

    // POST /do-something 수행 시 suspend 함수를 실행
    @PostMapping 
    fun doPost(
        @RequestParam(value = "number", defaultValue = "0") number: Int,
    ) {
        ioCoroutineScope.launch {
            try {
                doProcess(number)
            } catch (exception: Exception) {
                log.error("message=${exception.message}", exception)
                throw exception // <-- 문제가 되는 지점.
            }
        }
    }

    suspend fun doProcess(number: Int) {
        if (number % 5 == 0) {
            throw RuntimeException("number=$number 에러발생")
        }
        delay(1000)
        log.info("number=$number 수행")
    }
}
```

## 원인
* launch {} 수행 시 throw 를 하면 부모 Job 이 취소처리 된다.
* 이후의 요청들은 코루틴 스코가 취소되었기 때문에 launch {}, async {} 는 수행되지 않는다.
* 전역 코루틴 스코프가 cancelled 상태가 되어서 재사용할 수 없다. 스코프를 다시 만들지 않는 이상 복구가 불가하다.
  * job.isActive=false
  * job.isCancelled=true
```kotlin
fun getCoroutineStatus(): String {
    val job = ioCoroutineScope.coroutineContext[Job] // <-- 전역 코루틴 스코프의 job 상태를 보면 취소되었다. 
    val status = when {
        job == null -> "Unknown"
        job.isActive -> "Active"
        job.isCancelled -> "Cancelled"
        job.isCompleted -> "Completed"
        else -> "Unknown"
    }

    return """
        {
            "status": "$status"
        }
    """.trimIndent()
} 
```

## 해소방법1
SupervisorJob 을 이용한다.   
자식 코루틴간의 실패전파를 차단하는 특수한 잡이다. 전역으로 코루틴 스코프를 작성할 시 아래처럼 작성 가능하다.   
그리고 에러에 대한 핸들링을 하고 싶다면, `invokeOnCompletion {}` 을 붙일 수 있다. 
```kotlin
private val ioCoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

val job = ioCoroutineScope.launch { 
    // 작업내용 수행
}

job.invokeOnCompletion { throwable ->
    throwable?.let {
        // 에러 핸들링
    }
}
```

## 해소방법2
전역 코루틴 스코프가 아닌 메소드 내에서 코루틴 스코프를 매번 만들도록 하면, throw 가 되더라도 처리할 수 있다.
```kotlin
CoroutineScope(Dispatchers.IO).launch {
    // 작업내용 수행
}
```