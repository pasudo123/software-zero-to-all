# @Transactional 과 전파레벨 & 프록시
- @Transactional 은 편하다. 선언적으로 작성만 해놓고 메소드에 붙여놓으면 트랜잭션 시작과 커밋까지 자동으로 해주니.
- 해당 애노테이션은 Spring AOP 기반의 프록시로 동작한다.

## @Transactional 을 하나의 클래스에서 메소드를 호출해서 쓰는 경우.
아래 코드가 있다. 동작할까? 안할까? `결론부터 말하면 동작하지 않는다.`   
innerCreateIndependently() 메소드의 전파레벨이 신규 트랜잭션을 시작한다고 하더라도, 상위 트랜잭션에서 RuntimeException 이 발생하면서 같은 프록시 레벨의 트랜잭션으로 잡혀 롤백된다.   
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
- 이렇게 되면 proxy -> proxy 로 동작하기 때문에, 트랜잭션이 구분되서 롤백되지 않는다.
- 그러면 다른 방법은 없을까? self-injection 방식이 있다고 한다. 직접 실험했지만 순환참조가 발생하여 스프링부트 구동시에 에러가 발생한다.
- 다른 방법이 있다면,, 그 부분도 같이 기재하고 싶다. 하지만 아직까지 찾지 못했다.