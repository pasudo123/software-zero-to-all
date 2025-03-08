## springboot warmup
springboot warmup 을 하는 방법.

## index
<!-- TOC -->
  * [springboot warmup](#springboot-warmup)
  * [index](#index)
  * [(1) readiness 의 path 를 새롭게 만든다.](#1-readiness-의-path-를-새롭게-만든다)
<!-- TOC -->

## (1) readiness 의 path 를 새롭게 만든다.
k8s 환경에서 readiness probe 가 성공되어야 사용자 트래픽이 들어온다.   
애플리케이션이 최초 뜨는 시점에 웜업을 해줄 필요가 있다.

actuator/health 에 대한 k8s probe path 를 이용할 수 있다.
```yaml
management:
  endpoint:
    health:
      probes:
        ## GET actuator/health/readiness
        ## GET actuator/health/liveness
        enabled: true
```
* k8s deployment 의 readiness probe, liveness probe path 를 위처럼 사용할 수 있다. (스프링 특정버전 이상)
* `하지만 아래처럼 별도로 정의해서 사용하는 방법`

readiness 에 대해서 웜업 수행 이후에 별도 API status UP 상태로 유지한다.
```kotlin
@Component
class ApplicationCustomWarmer : ApplicationListener<ApplicationReadyEvent> {
    private val log = LoggerFactory.getLogger(javaClass)
    private val completed = AtomicBoolean(false)

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        // 로컬호스트 API 호출 (MySQL, 레디스 접근)
        Thread.sleep(5000)

        completed.set(true).also {
            log.info("warm up completed...")
        }
    }

    fun isOk(): Boolean {
        return completed.get()
    }
}

@RestController
@RequestMapping("current-health")
class CustomHealthController(
    private val warmer: ApplicationCustomWarmer
) {
    @GetMapping("readiness-probe")
    fun readinessProbe(): ResponseEntity<ProbeResponse> {
        if (warmer.isOk()) {
            return ResponseEntity
                .ok(ProbeResponse.UP)
        }

        return ResponseEntity
            .internalServerError()
            .body(ProbeResponse.DOWN)
    }
}

class ProbeResponse private constructor (
    val status: String
) {
    companion object {
        val UP = ProbeResponse("UP")
        val DOWN = ProbeResponse("DOWN")
    }
}
```