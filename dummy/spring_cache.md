## spring cache 사용기
사용해본 내용을 기록한다.

## 버전
* springboot 3.1.6
* kotlin 1.8.22

### 캐시설정
* cacheName 을 설정하고, 이 네이밍을 캐시네임으로 설정한다.
* 스프링 캐시는 name > key 로 구조가 잡혀있음
```kotlin
@Configuration
@EnableCaching
class CustomCacheConfiguration {

    val cacheNames = listOf(DEMO)

    @Bean
    fun cacheManager(): CacheManager {
        return ConcurrentMapCacheManager(*cacheNames.toTypedArray()).apply {
            // 널 허용여부를 결정한다.
            this.isAllowNullValues = false
        }
    }

    @Bean
    fun companiesPerMarketKeyGenerator(): KeyGenerator {
        return CompaniesPerMarketKeyGenerator()
    }
}
```

### 캐시사용
```kotlin
@RestController
@RequestMapping("default")
class DefaultController {

    private val logger = LoggerFactory.getLogger(javaClass)

    // 최초 한번만 호출되고, 이후에는 프록시 처리가 되서 getUUID() 메소드는 호출하지 않음
    @Cacheable(cacheNames = [DEMO])
    @GetMapping("uuid")
    fun getUUID(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid
    }

    // 캐시를 만료한다. 그리고 getUUID() 를 재호출 시, 캐시가 비었기 때문에 호출이 됨
    // void 타입이어도 상관없음. 속성값으로 allEntries=true 가 있는데 설정 시 해당 cacheNames 내부의 모든 키값도 만료시킨다.
    // beforeInvocation 속성도 존재. 에러여부에 상관없이 evict 하려면 true 로 설정한다.
    @CacheEvict(cacheNames = [DEMO])
    @DeleteMapping("uuid")
    fun deleteUUID() {}

    // 항상 실행된다. 캐시값을 갱신한다.
    @CachePut(cacheNames = [DEMO])
    @PatchMapping("uuid")
    fun updateUUID(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid
    }
}
```