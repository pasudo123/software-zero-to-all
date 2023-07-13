## springboot sseEmitter 사용기
* 서버에서 프론트로 단방향 통신이 가능하다.
* 2019년에 블로그에 포스팅 했던 내용이 있음(https://pasudo123.tistory.com/334)
* [HTML Standard](https://html.spec.whatwg.org/multipage/server-sent-events.html#server-sent-events) 에서 Server-Sent-Events 를 정의한다.
  * 서버 푸시 프로토콜을 통해서 서버에서 웹페이지로 데이터를 푸시할 수 있음
  * 해당 명세는 EventSource 인터페이스에서 소개함
* 웹소켓과 차이가 있다면 웹소켓은 클라-서버 간으 양방향 통신을 지원하지만 SSE 는 서버 -> 클라로 단방향 통신만을 지원함

### springboot 코드 예제
Controller
* SseEmitter 의 타임아웃은 디폴트 `30초` 이다.
* 클라로 응답주는 MediaType 이 SSE 는 `text/event-stream` 이다.
```kotlin
@RestController
@RequestMapping("sample-stream")
class SampleStreamingController {

    private val executor = Executors.newCachedThreadPool()

    @GetMapping("sse")
    fun useSse(): SseEmitter {
        val emitter = RyeSseEmitter()
        emitter.sendEvent()
        return emitter
    }

    private fun SseEmitter.sendEvent() {
        executor.execute {
            try {
                (1..Long.MAX_VALUE).forEach {
                    val person = Person("홍길동", it)
                    val event = SseEmitter.event()
                        // .data(person.toJson().replace(Regex("\\s"), ""))
                        .data(person, MediaType.APPLICATION_JSON)
                        .name(EmitterEvent.USER_NAME_EVENT.name)
                        .id(UUID.randomUUID().toString())
                    this.send(event)
                    Thread.sleep(2000)
                }
            } catch (exception: Exception) {
                this.completeWithError(exception)
            }
        }
    }

    data class Person(
        val name: String,
        val age: Long
    )

    enum class EmitterEvent {
        USER_NAME_EVENT
    }
}
```

RyeSseEmitter
* SseEmitter 에서 필요한 부분만 구현한다.
* 기본 인코딩 타입이 ISO-8859-1 이기 때문에 UTF-8 로 변경하여 한글이 깨지는 것을 막는다
```kotlin
class RyeSseEmitter(timeoutMilli: Long = 60000) : SseEmitter(timeoutMilli) {

    private val log = LoggerFactory.getLogger(javaClass)

    init {
        this.onCompletion()
        this.onError()
        this.onTimeout()
    }

    private fun onCompletion() {
        super.onCompletion {
            log.info("### complete")
        }
    }

    private fun onError() {
        super.onError {
            log.error("### error : ${it.message}")
        }
    }

    private fun onTimeout() {
        super.onTimeout {
            log.error("### timeout")
        }
    }

    /**
     * sse response 에서 한글이 깨지기 때문에 인코딩타입을 맞춰준다.
     * 디폴트는 ISO-8859-1 로 잡혀있어서 한글이 깨진다.
     */
    override fun extendResponse(outputMessage: ServerHttpResponse) {
        when (outputMessage) {
            is ServletServerHttpResponse -> outputMessage.servletResponse.characterEncoding = "UTF-8"
        }
        super.extendResponse(outputMessage)
    }
}
```

결과
* 화면이 매번 갱신되는 것이 아닌 화면에 계속 내용이 쌓인다.
* 프론트에서 별도로 구성하면 다르게 처리는 가능할 걸로 보인다.
```text
data:{
data:  "name" : "홍길동",
data:  "age" : 1
data:}
event:USER_NAME_EVENT
id:299343b7-988e-4d5e-948b-813ffe313c6e

data:{
data:  "name" : "홍길동",
data:  "age" : 2
data:}
event:USER_NAME_EVENT
id:dedee870-7bad-40d8-9b36-8c5827ada2b7
```


## 참고
* https://www.baeldung.com/spring-server-sent-events
