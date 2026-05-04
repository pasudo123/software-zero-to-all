# RequestContextHolder 실전 가이드

`RequestContextHolder`는 현재 요청을 thread-bound 상태로 접근하기 위한 Spring 유틸리티다.
핵심은 "요청 스레드 안에서는 편리하지만, 비동기/코루틴 전환 시 그대로 믿으면 깨진다"이다.

## 요약
- Spring MVC에서는 `DispatcherServlet`이 현재 요청 컨텍스트를 기본 노출하므로, 요청 스레드 안에서는 `RequestContextHolder`로 요청 메타를 쉽게 읽을 수 있다.
- `RequestContextHolder`는 `ThreadLocal` 기반이므로 worker thread, coroutine context 전환 구간에서는 값이 비어있을 수 있다.
- 실무 Best Practice는 **입구(Controller)에서 필요한 값을 추출해 불변 DTO로 전달**하는 방식이다.
- 부득이하게 ThreadLocal을 코루틴에서 다뤄야 할 때는 `asContextElement`를 사용해 전파를 명시한다.

## 사용법
### 1) 학습 API 실행
```bash
./gradlew bootRun
```
### 2) 현재 요청 스레드 확인
```bash
http GET :8080/study/request-context/current X-Request-Id:trace-current
```

### 3) 비동기 스레드 확인
```bash
http GET :8080/study/request-context/async X-Request-Id:trace-async
```

### 4) 코루틴 컨텍스트 전환 확인
```bash
http GET :8080/study/request-context/coroutine X-Request-Id:trace-coroutine
```

## 동작 방식
### 1) 요청 스레드 구간
- `RequestContextHolder.currentRequestAttributes()`는 현재 스레드에 바인딩된 `RequestAttributes`를 반환한다.
- 값이 없으면 `IllegalStateException`이 발생한다.

### 2) 비동기/별도 스레드 구간
- `CompletableFuture`나 executor worker thread로 넘어가면 같은 요청이어도 `RequestContextHolder`가 비어있을 수 있다.
- 따라서 worker에서 `RequestContextHolder`를 직접 읽는 대신, 요청 스레드에서 추출한 `RequestMeta`를 명시적으로 전달해야 한다.

### 3) 코루틴 구간
- 코루틴이 다른 스레드로 재개(resume)되면 `ThreadLocal` 값은 자동 보장되지 않는다.
- `RequestContextHolder`를 코루틴 내부에서 직접 의존하는 대신, 필요한 값을 인자로 전달하는 방식이 안정적이다.
- ThreadLocal 전파가 꼭 필요하면 `asContextElement`를 사용해 어떤 값을 전파할지 명시한다.

## 응용 방식
### 1) 감사/추적 로깅
- `X-Request-Id`, `User-Agent`, `clientIp`를 요청 입구에서 추출해 서비스/비동기 작업 로그에 함께 전달한다.

### 2) 멀티테넌시/권한 문맥
- tenant id, actor id를 요청 경계에서 추출 후 DTO로 넘겨 도메인 로직에서 일관되게 사용한다.

### 3) 외부 연동 호출
- downstream 호출 시 trace id를 전달해 end-to-end 추적성을 확보한다.

## 유의사항
### 1) `inheritable=true` 남용 금지
- `RequestContextFilter#setThreadContextInheritable(true)`는 thread pool과 결합되면 다른 요청으로 컨텍스트가 새는 위험이 있다.

### 2) 깊은 계층에서의 직접 호출 지양
- 서비스/도메인 계층에서 `RequestContextHolder`를 직접 호출하면 테스트가 어려워지고 실행 모델 변경(비동기/배치)에 취약해진다.

### 3) API 선택 기준
- `currentRequestAttributes()`: 컨텍스트가 반드시 있어야 하는 경계에서 사용 (없으면 즉시 실패)
- `getRequestAttributes()`: optional한 조회가 필요한 구간에서 null-safe 처리

### 4) 테스트 전략
- 단위/통합 테스트에서 `RequestContextHolder.resetRequestAttributes()`로 격리 보장
- 요청 컨텍스트 없는 케이스(예외 발생)와 비동기 누락 케이스를 함께 검증

## 참고 코드
- 컨트롤러: `GET /study/request-context/{current|async|coroutine}`
- 서비스:
  - 현재 요청 조회
  - async worker에서의 누락 확인
  - coroutine 전환 시 누락 확인 + 명시 전달/`asContextElement` 비교

## 공식 문서
- RequestContextHolder Javadoc: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/request/RequestContextHolder.html
- RequestContextFilter Javadoc: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/RequestContextFilter.html
- RequestContextListener Javadoc: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/request/RequestContextListener.html
- Kotlin Coroutines `asContextElement`: https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/as-context-element.html
