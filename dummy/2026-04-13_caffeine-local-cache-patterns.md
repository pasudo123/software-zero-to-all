# Caffeine Local Cache 활용 패턴 정리

이 문서는 `com.vibewithcodex.study.cachetier` 패키지의 실습 코드와 함께 보는 짧은 가이드다. 목표는 Redis/DB 계층 설계까지 확장하기보다, **Caffeine 하나로 어떤 활용 패턴을 만들 수 있는지**를 선명하게 정리하는 것이다.

---

## 요약

- Caffeine은 단순 Map 대체재가 아니라 TTL, size eviction, loader, refresh, stats를 제공하는 local cache다.
- 처음 학습할 때는 `Cache-aside`, `LoadingCache`, `refreshAfterWrite`, `stats/invalidate` 네 가지로 나누어 보면 충분하다.
- 실무에서는 캐시 전략보다 먼저 “데이터가 조금 stale해도 되는가”, “miss 시 누가 값을 읽어오는가”, “무효화는 누가 책임지는가”를 정해야 한다.

---

## 1) 실습 API

```http
POST /study/cachetier/data/product:100
Content-Type: application/json

{
  "value": "apple",
  "invalidateCaches": true
}
```

```http
GET /study/cachetier/cache-aside/product:100
GET /study/cachetier/loading/product:100
GET /study/cachetier/refresh/product:100
POST /study/cachetier/cache-aside/product:100/invalidate
GET /study/cachetier/cache-aside/stats
```

응답에서 볼 핵심 필드는 다음이다.

| 필드 | 의미 |
| --- | --- |
| `pattern` | 어떤 Caffeine 활용 패턴인지 |
| `source` | `LOCAL_CACHE`, `LOADER`, `MISS` 중 어디에서 값이 왔는지 |
| `message` | 해당 요청에서 관찰할 포인트 |

---

## 2) Cache-aside

```kotlin
cache.getIfPresent(key)?.let { return it }
val loaded = origin[key] ?: return null
cache.put(key, loaded)
return loaded
```

서비스 코드가 캐시 조회, 원천 조회, 캐시 적재를 직접 제어한다.

| 장점 | 주의사항 |
| --- | --- |
| 흐름이 명시적이고 디버깅이 쉽다 | miss 처리와 중복 로딩 방지를 직접 설계해야 한다 |
| 조건부 적재, 예외 처리, 응답 메시지 제어가 쉽다 | 여러 서비스에 흩어지면 코드가 반복될 수 있다 |

추천 상황은 캐시 miss 시 처리 규칙이 도메인마다 다르거나, 응답에서 hit/miss 원인을 설명해야 하는 경우다.

---

## 3) LoadingCache

```kotlin
val cache = Caffeine.newBuilder()
    .maximumSize(10_000)
    .expireAfterWrite(Duration.ofSeconds(10))
    .build<String, String> { key -> loadFromOrigin(key) }

val value = cache.get(key)
```

Caffeine이 miss 시 loader를 자동 호출한다.

| 장점 | 주의사항 |
| --- | --- |
| 서비스 코드가 간결해진다 | loader가 느리거나 실패할 때 영향 범위를 고려해야 한다 |
| 같은 key의 중복 로딩을 줄이기 좋다 | null을 캐싱할 수 없으므로 missing value 정책이 필요하다 |

추천 상황은 key를 넣으면 값을 읽는 규칙이 단순하고 일관적인 경우다.

---

## 4) refreshAfterWrite

```kotlin
val cache = Caffeine.newBuilder()
    .refreshAfterWrite(Duration.ofSeconds(3))
    .expireAfterWrite(Duration.ofSeconds(10))
    .build<String, String> { key -> loadFromOrigin(key) }
```

`refreshAfterWrite`는 “정해진 시간이 지나면 바로 삭제”가 아니라, refresh 대상이 된 entry를 접근 시점에 다시 로드할 수 있게 한다. 기존 값을 최대한 유지하면서 갱신하려는 성격이라, latency를 낮추고 싶은 read-heavy 데이터에 어울린다.

| 장점 | 주의사항 |
| --- | --- |
| 오래된 값을 즉시 버리지 않아 tail latency에 유리하다 | stale 값을 잠깐 허용할 수 있어야 한다 |
| 주기적 재조회 데이터에 잘 맞는다 | loader 실패, refresh 지연을 관측해야 한다 |

---

## 5) stats/invalidate

```kotlin
cache.stats()
cache.invalidate(key)
```

캐시는 넣는 것보다 관측하고 비우는 전략이 더 중요할 때가 많다.

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
