# Caffeine Local Cache 활용 패턴 정리

이 문서는 `com.vibewithcodex.study.cachetier` 패키지의 실습 코드와 함께 보는 짧은 가이드다. 목표는 Redis/DB 계층 설계까지 확장하기보다, **Caffeine 하나로 어떤 활용 패턴을 만들 수 있는지**를 코드 흐름 중심으로 정리하는 것이다.

---

## 요약

- Caffeine은 단순 Map 대체재가 아니라 TTL, size eviction, loader, refresh, stats를 제공하는 local cache다.
- 처음 학습할 때는 `Cache-aside`, `LoadingCache`, `refreshAfterWrite`, `stats/invalidate` 네 가지로 나누어 보면 충분하다.
- 실무에서는 캐시 전략보다 먼저 “데이터가 조금 stale해도 되는가”, “miss 시 누가 값을 읽어오는가”, “무효화는 누가 책임지는가”를 정해야 한다.

---

## 1) 실습 코드 구조

HTTP 호출 자체보다 중요한 것은 Controller가 어떤 서비스 메서드로 위임하고, 서비스가 Caffeine을 어디까지 직접 제어하는지다.

```kotlin
@RestController
@RequestMapping("/study/cachetier")
class StudyCacheTierController(
    private val studyCacheTierService: StudyCacheTierService,
) {
    @GetMapping("/cache-aside/{key}")
    fun getWithCacheAside(@PathVariable key: String) =
        studyCacheTierService.getWithCacheAside(key)

    @GetMapping("/loading/{key}")
    fun getWithLoadingCache(@PathVariable key: String) =
        studyCacheTierService.getWithLoadingCache(key)

    @GetMapping("/refresh/{key}")
    fun getWithRefreshAfterWrite(@PathVariable key: String) =
        studyCacheTierService.getWithRefreshAfterWrite(key)
}
```

서비스는 origin 역할의 `ConcurrentHashMap`과 Caffeine 캐시 3개를 나란히 둔다. 같은 데이터를 여러 전략으로 관찰하기 위한 학습용 구조다.

```kotlin
class StudyCacheTierService(
    private val properties: StudyCacheTierProperties,
) {
    private val originData = ConcurrentHashMap<String, String>()

    private val cacheAsideCache: Cache<String, String> = Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofSeconds(properties.localTtlSeconds))
        .maximumSize(properties.localMaximumSize)
        .recordStats()
        .build()

    private val loadingCache: LoadingCache<String, String> = Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofSeconds(properties.localTtlSeconds))
        .maximumSize(properties.localMaximumSize)
        .recordStats()
        .build { key -> loadForLoadingCache(key) }

    private val refreshAfterWriteCache: LoadingCache<String, String> = Caffeine.newBuilder()
        .refreshAfterWrite(Duration.ofSeconds(properties.refreshAfterWriteSeconds))
        .expireAfterWrite(Duration.ofSeconds(properties.localTtlSeconds))
        .maximumSize(properties.localMaximumSize)
        .recordStats()
        .build { key -> loadForRefreshCache(key) }
}
```

응답에서 볼 핵심 필드는 다음이다.

| 필드 | 의미 |
| --- | --- |
| `pattern` | 어떤 Caffeine 활용 패턴인지 |
| `source` | `LOCAL_CACHE`, `LOADER`, `MISS` 중 어디에서 값이 왔는지 |
| `message` | 해당 요청에서 관찰할 포인트 |

---

## 2) Cache-aside

Cache-aside는 서비스 코드가 캐시 조회, 원천 조회, 캐시 적재를 직접 제어한다.

```kotlin
fun getWithCacheAside(key: String): CacheLookupResponse {
    cacheAsideCache.getIfPresent(key)?.let { cached ->
        return CacheLookupResponse(
            key = key,
            value = cached,
            pattern = CachePattern.CACHE_ASIDE,
            source = CacheSource.LOCAL_CACHE,
            message = "Cache-aside hit",
        )
    }

    val loaded = originData[key] ?: return CacheLookupResponse(
        key = key,
        value = null,
        pattern = CachePattern.CACHE_ASIDE,
        source = CacheSource.MISS,
        message = "Origin data does not exist",
    )

    cacheAsideCache.put(key, loaded)
    return CacheLookupResponse(
        key = key,
        value = loaded,
        pattern = CachePattern.CACHE_ASIDE,
        source = CacheSource.LOADER,
        message = "Loaded origin data and populated Caffeine",
    )
}
```

이 패턴에서는 “miss일 때 무엇을 할지”가 서비스 코드에 드러난다. 값이 없으면 `MISS`로 응답하고, 값이 있으면 Caffeine에 직접 `put`한다.

| 장점 | 주의사항 |
| --- | --- |
| 흐름이 명시적이고 디버깅이 쉽다 | miss 처리와 중복 로딩 방지를 직접 설계해야 한다 |
| 조건부 적재, 예외 처리, 응답 메시지 제어가 쉽다 | 여러 서비스에 흩어지면 코드가 반복될 수 있다 |

추천 상황은 캐시 miss 시 처리 규칙이 도메인마다 다르거나, 응답에서 hit/miss 원인을 설명해야 하는 경우다.

---

## 3) LoadingCache

`LoadingCache`는 miss 시 Caffeine이 loader를 호출한다. 서비스는 `get(key)`만 호출하고, 값을 가져오는 규칙은 builder의 loader에 둔다.

```kotlin
private val loadingCache: LoadingCache<String, String> = Caffeine.newBuilder()
    .maximumSize(properties.localMaximumSize)
    .expireAfterWrite(Duration.ofSeconds(properties.localTtlSeconds))
    .recordStats()
    .build { key -> loadForLoadingCache(key) }

fun getWithLoadingCache(key: String): CacheLookupResponse {
    val before = loadingCache.getIfPresent(key)
    val value = loadingCache.get(key)

    return CacheLookupResponse(
        key = key,
        value = value,
        pattern = CachePattern.LOADING_CACHE,
        source = if (before == null) CacheSource.LOADER else CacheSource.LOCAL_CACHE,
        message = if (before == null) "Loader called" else "Cached value returned",
    )
}

private fun loadForLoadingCache(key: String): String {
    return originData[key] ?: "generated:$key"
}
```

학습 코드에서는 origin에 값이 없을 때 `generated:$key`를 반환한다. 운영 코드라면 missing value를 예외로 볼지, null object로 볼지, fallback으로 볼지를 별도로 정해야 한다.

| 장점 | 주의사항 |
| --- | --- |
| 서비스 코드가 간결해진다 | loader가 느리거나 실패할 때 영향 범위를 고려해야 한다 |
| 같은 key의 중복 로딩을 줄이기 좋다 | null을 캐싱할 수 없으므로 missing value 정책이 필요하다 |

---

## 4) refreshAfterWrite

`refreshAfterWrite`는 “정해진 시간이 지나면 바로 삭제”가 아니라, refresh 대상이 된 entry를 접근 시점에 다시 로드할 수 있게 한다. 기존 값을 최대한 유지하면서 갱신하려는 성격이라, latency를 낮추고 싶은 read-heavy 데이터에 어울린다.

```kotlin
private val refreshAfterWriteCache: LoadingCache<String, String> = Caffeine.newBuilder()
    .refreshAfterWrite(Duration.ofSeconds(properties.refreshAfterWriteSeconds))
    .expireAfterWrite(Duration.ofSeconds(properties.localTtlSeconds))
    .maximumSize(properties.localMaximumSize)
    .recordStats()
    .build { key -> loadForRefreshCache(key) }

fun refreshNow(key: String): CacheMutationResponse {
    val value = refreshAfterWriteCache.refresh(key).get()
    return CacheMutationResponse(
        key = key,
        value = value,
        message = "RefreshAfterWrite cache refreshed from origin data",
    )
}
```

테스트에서는 origin 값을 바꾼 뒤 캐시를 무효화하지 않아 stale 값을 관찰하고, 이후 refresh를 통해 새 값을 확인한다.

```kotlin
studyCacheTierService.putData("product:3", "old")
studyCacheTierService.getWithRefreshAfterWrite("product:3").value shouldBe "old"

studyCacheTierService.putData("product:3", "new", invalidateCaches = false)
studyCacheTierService.getWithRefreshAfterWrite("product:3").value shouldBe "old"

studyCacheTierService.refreshNow("product:3").value shouldBe "new"
```

| 장점 | 주의사항 |
| --- | --- |
| 오래된 값을 즉시 버리지 않아 tail latency에 유리하다 | stale 값을 잠깐 허용할 수 있어야 한다 |
| 주기적 재조회 데이터에 잘 맞는다 | loader 실패, refresh 지연을 관측해야 한다 |

---

## 5) stats/invalidate

캐시는 넣는 것보다 관측하고 비우는 전략이 더 중요할 때가 많다.

```kotlin
fun invalidate(cacheName: String, key: String): CacheMutationResponse {
    cacheByName(cacheName).invalidate(key)
    return CacheMutationResponse(key = key, value = null, message = "Invalidated")
}

fun getStats(cacheName: String): CacheStatsResponse {
    val cache = cacheByName(cacheName)
    val stats = cache.stats()
    return CacheStatsResponse(
        cacheName = cacheName,
        requestCount = stats.requestCount(),
        hitCount = stats.hitCount(),
        missCount = stats.missCount(),
        hitRate = stats.hitRate(),
        evictionCount = stats.evictionCount(),
        estimatedSize = cache.estimatedSize(),
        loaderCallCount = loaderCount(cacheName),
    )
}
```

| 기능 | 확인할 것 |
| --- | --- |
| `recordStats()` | hit/miss/request/eviction을 볼 수 있다 |
| `estimatedSize()` | 캐시가 얼마나 커졌는지 빠르게 확인한다 |
| `invalidate(key)` | 특정 key의 stale 값을 제거한다 |
| `invalidateAll()` | 배포/테스트/운영 대응 시 전체 초기화를 수행한다 |

---

## 선택 기준

| 상황 | 우선 검토 |
| --- | --- |
| miss 처리 규칙이 도메인마다 다름 | Cache-aside |
| key -> value 로딩 규칙이 단순함 | LoadingCache |
| read-heavy이고 잠깐의 stale 허용 가능 | refreshAfterWrite |
| 운영에서 hit ratio와 eviction 확인 필요 | recordStats |

---

## 유의사항

- TTL은 정합성 정책이다. 무조건 길게 두면 stale 문제가 커진다.
- `maximumSize` 없이 local cache를 운영하면 pod 메모리 사용량이 예측하기 어려워진다.
- 캐시 key는 도메인, 식별자, 조건을 포함해 충돌을 피해야 한다.
- loader는 빠르고 안정적이어야 하며, 실패 시 fallback 정책을 정해야 한다.
- 다중 pod 정합성, Redis L2, 이벤트 기반 invalidation은 별도 주제로 분리해서 다루는 편이 이해하기 쉽다.
