## 스프링 트랜잭션 애노테이션은 롤백이 UnCheckedException 에서만 발생한다
선언형 트랜잭션은 언제 롤백이 일어나고 일어나지 않는가?

## Throwable & Exception & Error 의 관계
- 예외는 Throwable 클래스 또는 그 서브클래스의 인스턴스를 의미하고 있다.
- 따라서 Throwable 클래스와 그 서브클래스 전체는 모두 Exception 으로 분류한다.
  - `Exception 클래스`와 `Error 클래스`는 Throwable 클래스의 서브클래스다.
  - Exception 클래스는 모든 Exception (프로그래밍적으로 리커버리 될 수 있는 Exception) 의 슈퍼클래스다.
  - Error 클래스는 모든 Exception (프로그래밍적으로 리커버리 될 수 없는 Exception) 의 슈퍼클래스다.
  - > 결국 Exception 클래스와 Error 클래는 서로 다른 Exception 클래스의 슈퍼클래스

## RuntimeException & CheckedException
- RuntimeException 은 Exception 클래스의 서브클래스다.
- RuntimeException 과 그 아래의 서브클래스는 모두 `run-time exception` 이다.
- `UnChekedException` 은 `run-time exception 클래스` 및 `error 클래스` 이다.
- `ChekedException` 은 UnCheckedException 을 제외한 모든 Exception 클래스다.

## Code
```kotlin
@Service
class Demo01ServiceWithRollback(
  private val coffeeRepository: CoffeeRepository
) {

  private val log = LoggerFactory.getLogger(javaClass)

  /**
   * RuntimeException, Error 은 UnCheckedException 이기 때문에 롤백
   * Exception 은 롤백되지 않음 따라서 별도 rollbackFor=[] 설정이 필요
   */
  @Transactional(rollbackFor = [])
  fun queryWithSize(query: Query, size: Int) {
    val coffees = (1..size).map {
      Coffee.random()
    }

    coffeeRepository.saveAll(coffees)

    if (query == Query.Q_THROW_ERROR) {
      throw Error("$query 발생")
    }

    /**
     * @Transactional(rollbackFor = []) 설정이 필요하다.
     * 없으면 롤백이 되지 않는다.
     */
    if (query == Query.Q_THROW_EXCEPTION) {
      throw Exception("$query 발생")
    }

    if (query == Query.Q_THROW_RUNTIME_EXCEPTION) {
      throw RuntimeException("$query 발생")
    }
  }
}
```
* 위 코드에서 @Transaction 의 롤백은 throw Error() 구문과 throw RuntimeException() 구문에서 발생한다.
* throw Exception() 구문에서도 롤백을 동작하게 하고 싶다면 @Transactional(rollbackFor=[]) 구문을 통해서 롤백을 하려고 하는 클래스를 정의해야하다

## 참고
* https://docs.oracle.com/javase/specs/jls/se7/html/jls-11.html#jls-11.1.1