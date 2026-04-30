## Elasticsearch 실무적용
ES 실무 적용 시, 고려했던 것들 기록.

## index
<!-- TOC -->
  * [ES 실무적용](#es-실무적용)
  * [index](#index)
  * [Tip](#tip)
    * [Restful API 적극 활용](#restful-api-적극-활용)
    * [색인의 종류도 여러개로 구분](#색인의-종류도-여러개로-구분)
    * [refresh_interval 을 활용](#refresh_interval-을-활용)
<!-- TOC -->

## Tip
### Restful API 적극 활용
- 키바나 Dev Tool 통해서 Restful API 요청, 응답을 처리할 수 있다.

### 색인의 종류도 여러개로 구분
- 색인은 도메인 전략에 따른 여러가지 방식으로 처리할 수 있다.
  - 전체색인
  - 실시간색인
  - 조건부색인
- 색인시 데이터가 많다면 `bulk API` 로 처리하는 것이 퍼포먼스 측면에서 효과적이다.
  - 애플리케이션 레벨에서 색인을 처리하고자 한다면 멀티 스레드 또는 코루틴으로 처리할 수 있다. (+ chunk 도 이용)

### refresh_interval 을 활용
- `refresh_interval`은 ES 색인 후 도큐먼트가 검색 가능해지기까지 걸리는 시간을 결정한다.
- ES 는 새로운 도큐먼트가 추가되면 먼저 translog에 기록한 후, 메모리에 임시 저장한다. 이후, 일정 주기마다 데이터를 세그먼트(Segment)로 변환하고 디스크에 기록하는데, 이 과정이 'refresh'다.
- refresh interval 이 짧다면?
  - 색인된 문서가 빠르게 검색된다.
  - 짧은 주기로 처리되기 때문에 잦게 발생한다면 시스템 리소스를 자주 사용한다. (검색성능 저하)
  - 시스템의 오버헤드가 커진다. (ES 자체의 context switching 비용 증가)
- refresh interval 이 길다면?
  - 인덱싱 성능을 높일 수 있다.
  - 하지만 주기가 길어지기 때문에 실시간 검색이 중요한 서비스에선 적합하지 않다.
- refresh interval 을 -1 설정하면?
  - refresh interval 이 `비활성화` 된다.
  - 인덱싱 성능을 크게 향상시킬 수 있지만, 수동으로 refresh를 호출하지 않으면 데이터가 검색되지 않는다.

**실무 적용 예시**

| **시나리오** | **추천 `refresh_interval` 값** |
|-------------|--------------------|
| 실시간 검색 서비스 (예: 뉴스) | `1s` (기본값) |
| 대량 데이터 배치 처리 (Bulk Insert) | `-1` 후 수동 `refresh` 호출 |
| 로그 저장소 (ELK Stack) | `5~30s` (검색 성능보다 리소스 절약이 중요) |

```shell
# refresh_interval 값을 30초로 설정
PUT /my-index/_settings
{
  "index" : {
    "refresh_interval" : "30s"
  }
}
```
