# reactive dummies
리액티브 메모한것들

### 시작
https://projectreactor.io/docs/core/release/reference/#intro-reactive
* 리액티브 스트림과 이터레이터 디자인패턴의 가장 큰 차이는 이터레이터는 pull-based, 리액티브 스트림은 push-based 이다.

#### 그래서 왜 비동기 라이브러리가 필요한가?
https://projectreactor.io/docs/core/release/reference/#_blocking_can_be_wasteful
* 블럭킹은 낭비다.
* 대체로 성능 향상 두가지 방법이 존재
    * parallelize : 병렬 스레드를 이용하고 더 높은 하드웨어 리소스 사용
    * seek more efficiency :  현재 가용할 수 있는 리소스를 최대한으로 활용
* 블럭킹은 리소스를 낭비시킨다.
    * 스레드들은 데이터를 기다리는 동안 유휴상태에 빠지게 된다.
    * 병렬화는 해결책이 될 수 없음
        * 스레드를 늘리더라도 병목이 생길 수 밖에 없음

### subscribe 하기 이전까지 아무것도 일어나지 않는다.
https://projectreactor.io/docs/core/release/reference/#reactive.subscribe
* publisher 체인을 쓸 때, 데이터를 기본적으로 펌핑하지 않는다. 대신에 비동기적인 추상적인 명세를 생성할 수 있다.
    * 해당 추상화는 재사용과 플로우 구성에 도움이 된다.

재사용하여 쓸 수 있다.
```java
@Test
public void test1() {
    Flux<String> strings = Flux.just("park", "kim", "yun", "jae")
            .filter(element -> element.startsWith("p"));

    strings.subscribe(element -> {
        System.out.println("name : " + element);
    });

    System.out.println("-");
    strings.subscribe(element -> {
        System.out.println("name : " + element);
    }, (exception) -> new RuntimeException("error ! : " + exception.getMessage()));
}

/** 출력내용
name : park
-
name : park
 */
```

### 데이터 변환
https://projectreactor.io/docs/core/release/reference/#which.values
* map() 을 이용, 타입 변환
* cast() 를 이용 형 변환
* defaultOrEmpty() 를 이용 없는 경우 처리
* reduce() sum 을 내는 경우 유용
* scan() 시퀀셜한 값들을 하나씩 계산함
* startWith() 를 이용 첫번째 값을 세팅

### 데이터 필터링
https://projectreactor.io/docs/core/release/reference/#which.filtering 
* filter() 를 이용
* distinct() 를 이용, 중복된 데이터를 제거
* next() 를 통해 flux 에서 mono 로 첫번째 아이템을 emitted 함
* skipLast(n) 를 통해서 flux 아이템 중 마지막으로부터 n개 아이템은 생략함
* skipWhile(Predicate<? super T> predicate) 를 통해 특정 조건은 생략함
* skipUntil(Predicate<? super T> predicate) 를 통해 특정 조건발생하기 전까지 무시함
* skipWhile 과 skipUntil 등을 같이 섞어 쓸 수 있다.
* ofType() + cast() 를 혼합해서 쓸 수 있다.

### 라이프사이클 : 훅 설정
https://projectreactor.io/docs/core/release/reference/#which.peeking 
* doOnSubscribe() : subscribe 시작하는 시점에
* doFirst() : subscribe 하기 이전에
    * 선언된 내용과, 처리되는 내용은 반대로 될 수 있음 : println() 으로 확인
* doOnNext() : 각각의 이벤트 emit 건에 대한 처리
* doOnComplete() : 완료되고 난 뒤
* doOnCancel() : 취소가 되고 난 뒤
* doAfterTermiate() : 
* doFinallny() : 성공적으로 끝나는거, 취소되는거, 에러뜨는거 등 모든 훅을 받음

### 시퀀스 생성
https://projectreactor.io/docs/core/release/reference/#which.create 
https://projectreactor.io/docs/core/release/reference/#producing 
https://projectreactor.io/docs/core/release/reference/#_simple_ways_to_create_a_flux_or_mono_and_subscribe_to_it 
* just() : T generic 을 emit 한다. 사전에 미리 가지고 있는다
* justOrEmpty() : nullable 한 T 를 emit 한다. + optional 한 객체도 emit 할 수 있다.
* fromCallable() : Callable 객체를 emit 한다.
    * callable 와 runnable 둘 은 쓰임이 비슷하다. callable 은 인자없이 리턴타입이 있는 상태이다.
* fromFuture() : CompletableFuture 도 emit 할 수 있다.
    * CompletableFuture 
* fromRunnable() : Runnable 객체 emit
* empty() : 아무런 아이템 없이 emit 한다.
* never() : 어떤 시그널도 발생시키지 않는다. cancel 또는 complete 든
    * never 은 언제 쓰는걸까? chatGPT 에서는 long polling connection 하고자 할 때, 그 외에 아무것도 트리거하지 않고자 할 때 사용한다고 한다.
* error()
* interval()
* range()

 


