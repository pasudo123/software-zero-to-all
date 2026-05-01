# @Transactional 과 전파레벨 & 프록시
- @Transactional 은 편하다. 선언적으로 작성만 해놓고 메소드에 붙여놓으면 트랜잭션 시작과 커밋까지 자동으로 해주니.
- 해당 애노테이션은 Spring AOP 기반의 프록시로 동작한다.

## @Transactional 을 하나의 클래스에서 메소드를 호출해서 쓰는 경우.
아래 코드가 있다. 동작할까? 안할까? `결론부터 말하면 동작하지 않는다.`   
Spring 의 기본 proxy mode 에서는 프록시를 통과한 외부 호출만 트랜잭션 인터셉터가 적용된다. 같은 클래스 내부에서 `innerCreateIndependently()` 를 직접 호출하면 `REQUIRES_NEW` 설정 자체가 적용되지 않는다. 결국 외부에서 호출된 `create()` 의 트랜잭션 안에서 함께 실행되고, `create()` 에서 RuntimeException 이 발생하면 함께 롤백된다.
```kotlin
@Service
class PostService(
    private val postInsertService: PostInsertService,
    private val postRepository: PostRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun create(dto: PostCreateDto) {
        val initPost = innerCreateIndependently(dto)

        try {
            log.info("#### try catch")
            initPost.update("update-${dto.contents}")
            postRepository.saveAndFlush(initPost)
            throw RuntimeException("강제에러 발생")
        } catch (exception: Exception) {
            log.error("message=${exception.message}")
            throw exception
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun innerCreateIndependently(dto: PostCreateDto): Post {
        log.info("#### 같은 클래스, @Transactional 호출")
        return postRepository.saveAndFlush(Post.from(dto))
    }
}
```

## 어떻게 해결하는게 좋은가?
- `innerCreateIndependently() 메소드를 다른 클래스에 넣어서, 호출되도록 한다.`
- 이렇게 되면 외부 빈의 프록시를 거쳐 호출되므로 `REQUIRES_NEW` 가 적용되고, 기존 트랜잭션을 잠시 중단한 뒤 새 트랜잭션을 시작한다.
- 그러면 다른 방법은 없을까? self-injection 으로 자기 자신의 프록시를 주입받는 방법도 있다고 한다. 직접 실험했을 때는 순환참조가 발생하여 스프링부트 구동시에 에러가 발생했다.
- 같은 클래스 내부 호출까지 트랜잭션을 적용해야 한다면 Spring 문서 기준으로 AspectJ mode 를 고려할 수 있다. 다만 아직 이 방식은 직접 확인하지 못했다.

## reference
- [Spring Framework docs - Using @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html)
