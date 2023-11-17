## 2회차, 스프링 도큐먼트 공부

## (1) Dependencies
* DI 를 이용하면 테스트 용이/stub or mock 처리 용이 등의 이점이 있음
* DI 에는 두가지 큰 형태가 존재
    * 생성자 기반 DI
    * 세터 기반 DI

> 이후에 스프링 도큐먼트 내용은 생성자 기반 DI/ 세터 기반 DI 를 어떻게 하는지   
> 코드레벨/XML 레벨로 서술하고 있어서 생략

### Constructor based or Setter based DI?
* 스프링팀은 일반적으로 생성자 기반 DI 를 추천함
* 세터 기반 DI 는 null check 를 하고, bean 의 상태가 완전하다는 보장을 하기가 어렵다. 다만 중간에 다른 의존성을 주입하고자 할 때 세터 기반 DI 를 사용하면 된다.
* 그리고 생성자 기반 
* 결국 상황에 맞게 적절하게 DI 패턴을 써야 한다.
* [다양한 의존성 주입 방법과 생성자 주입을 사용해야 하는 이유 - (2/2)](https://mangkyu.tistory.com/125)

### Dependency Resolution Process
* Circular dependencies
    * 생성자 기반 DI 로 처리 시, 서로가 서로를 의존하게 되어 `BeanCurrentlyInCreationException` 이 발생한다. 이를 방지하려면 하나는 생성자 기반, 하나는 세터 기반으로 처리한다. 이는 추천하지 않는 방법이긴 함

해당 코드는 아래와 같은 에러를 유발함
```kotlin
@Service
class CoffeeService(
    private val snackService: SnackService
)

@Service
class SnackService(
    private val coffeeService: CoffeeService
)
```

```console
The dependencies of some of the beans in the application context form a cycle:

┌─────┐
|  coffeeService defined in file [../CoffeeService.class]
↑     ↓
|  snackService defined in file [../SnackService.class]
└─────┘
```

* 빈이 이후에 무탈하게 초기화 되면 라이프사이클 관련 메소드를 호출한다.
    * afterPropertiesSet()
    * @PostConstruct

### depends-on
* @DependsOn 애노테이션을 이용해서 처리가 가능
* 빈이 종료시점도 제어할 수 있음
* 빈의 생성순서는 다양한 요인이 존재함
    * 의존성 순서
    * @DependsOn 애노테이션
    * Ordered, Order 애노테이션
    * Configuration 클래스 내 메소드 호출 순서

#### case1 : fatherBean -> motherBean 순서대로 만들어짐
```kotlin
@Configuration
class Chapter02Configuration {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun fatherBean(): FatherBean {
        return FatherBean(log)
    }

    @Bean
    fun motherBean(): MotherBean {
        return MotherBean(log)
    }

    class MotherBean(
        private val log: Logger
    ) {
        @PostConstruct
        fun init() {
            log.info("mother bean init")
        }
    }

    class FatherBean(
        private val log: Logger
    ) {
        @PostConstruct
        fun init() {
            log.info("father bean init")
        }
    }
}
```

#### case2 : motherBean 의존성이 먼저 있음. 따라서 motherBean -> fatherBean 순으로 만들어짐
```kotlin
@Bean
fun fatherBean(
    motherBean: MotherBean
): FatherBean {
    return FatherBean(log)
}

@Bean
fun motherBean(): MotherBean {
    return MotherBean(log)
}
```

#### case3 : @DependsOn 걸려있음 따라서 motherBean -> fatherBean 순으로 만들어짐
```kotlin
@Bean
@DependsOn(value = ["motherBean"])
fun fatherBean(): FatherBean {
    return FatherBean(log)
}

@Bean
fun motherBean(): MotherBean {
    return MotherBean(log)
}
```

### Lazy Bean
* 빈을 바로 초기화 시키지 않고 요청이 들어오는 시점에 빈을 초기화시킴
* 하지만 초기화 시점에 Lazy 설정된 빈이 다른 빈에 의존성이 먼저 있는 경우에 바로 만들어짐
* @Lazy 의 장점
  * 성능향샹 : 빈 초기화를 하지 않기 때문에 구동시간이 향상될 수 있음
  * 자원절약 : 모든 빈을 불러오지 않기 때문에 CPU/MEMORY 의 낭비를 피할 수 있음
* @Lazy 의 단점
  * 필요한 시점에 초기화가 되므로 해당 빈을 사용할 때 추가적인 비용이 들어갈 수 있음

```kotlin
@Configuration
class Chapter02LazyConfiguration {

    @Bean
    @Lazy // motherLazyBean 을 쓰는 시점에 초기화가 된다. 생성자 주입시 처리하기 까다롭다..
    fun motherLazyBean(): MotherLazyBean {
        println("create motherLazyBean")
        return MotherLazyBean()
    }

    @Bean
    fun motherNowBean(): MotherNowBean {
        println("create motherNowBean")
        return MotherNowBean()
    }

    class MotherLazyBean {

        fun use() {
            println("use, mother lazy bean")
        }
    }

    class MotherNowBean {
        fun use() {
            println("use, mother now bean")
        }
    }
}
```

> @Lazy 애노테이션을 딱히 써본적은 없음. 이점이 있는지 크게 잘 모르겠음.

### autowiring collaborators
* 명시적으로 의존성 설정을 하는 경우, 자동으로 @Autowired 처리는 무시가 된다.

### method injection
싱글톤빈과 프로토타입빈이 서로 협력할 때, 싱글톤빈에서 프로토타입 빈을 획득하는 방법 -> 이를 `method injection` 이라 칭한다.

__singleton bean & prototype bean 의 선언__
```kotlin
@Configuration
class Chapter02MethodInjectionConfiguration {

    @Bean
    @Scope("prototype")
    fun protoTypeSampleBean(): ProtoTypeSampleBean {
        return ProtoTypeSampleBean()
    }

    @Bean
    fun singletonSampleBean(): SingleTonSampleBean {
        return SingleTonSampleBean()
    }

    class ProtoTypeSampleBean

    class SingleTonSampleBean : ApplicationContextAware {

        private lateinit var applicationContext: ApplicationContext

        override fun setApplicationContext(applicationContext: ApplicationContext) {
            this.applicationContext = applicationContext
        }

        fun getProtoType(): ProtoTypeSampleBean {
            return applicationContext.getBean(ProtoTypeSampleBean::class.java)
        }
    }
}
```

__method inejction 사용__
```kotlin
@Component
class Chapter02Runner(
    private val applicationContext: ApplicationContext,
): ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        log.info("args runner")
        val singletonBean = applicationContext.getBean(Chapter02MethodInjectionConfiguration.SingleTonSampleBean::class.java)
        log.info("(1) prototype : ${ObjectUtils.getIdentityHexString(singletonBean.getProtoType())}")
        log.info("(2) prototype : ${ObjectUtils.getIdentityHexString(singletonBean.getProtoType())}")
        log.info("(3) prototype : ${ObjectUtils.getIdentityHexString(singletonBean.getProtoType())}")
    }
}
```

__출력값__
* 빈이 다르기 때문에 반환되는 식별값이 다름
```shell
2023-11-17T16:43:26.392+09:00  INFO 70223 --- [           main] c.e.s.chapter02.Chapter02Runner          : args runner
2023-11-17T16:43:26.393+09:00  INFO 70223 --- [           main] c.e.s.chapter02.Chapter02Runner          : (1) prototype : 5d7f8467
2023-11-17T16:43:26.393+09:00  INFO 70223 --- [           main] c.e.s.chapter02.Chapter02Runner          : (2) prototype : 29bd85db
2023-11-17T16:43:26.393+09:00  INFO 70223 --- [           main] c.e.s.chapter02.Chapter02Runner          : (3) prototype : 7caf1e5
```

__method injection 이 아닌 @Lookup 을 활용__
```kotlin
@Configuration
class Chapter02MethodInjectionConfiguration {

    @Bean
    @Scope("prototype")
    fun protoTypeSampleBean(): ProtoTypeSampleBean {
        return ProtoTypeSampleBean()
    }
}

class ProtoTypeSampleBean

@Component
abstract class SingleTonSampleBean1th {

    @Lookup("protoTypeSampleBean")
    // 미구현이라도 @Lookup 처리로 인하여 CGLIB 프록시가 생성되어 오버라이딩 처리가 된다
    abstract fun createProtoType(): ProtoTypeSampleBean
}

@Component
abstract class SingleTonSampleBean2th {

    @Lookup("protoTypeSampleBean")
    abstract fun createProtoType(): ProtoTypeSampleBean
}

@Component
class Chapter02Runner(
    private val singleTonSampleBean1th: SingleTonSampleBean1th,
    private val singleTonSampleBean2th: SingleTonSampleBean2th
): ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        log.info("args runner")
        log.info("(1) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean1th.createProtoType())}")
        log.info("(2) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean1th.createProtoType())}")
        log.info("(3) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean1th.createProtoType())}")

        log.info("--")
        log.info("(1) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean2th.createProtoType())}")
        log.info("(2) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean2th.createProtoType())}")
        log.info("(3) prototype : ${ObjectUtils.getIdentityHexString(singleTonSampleBean2th.createProtoType())}")
    }
}
```

__출력값__
```shell
2023-11-17T17:19:44.318+09:00  INFO 74288 --- [           main] c.e.s.chapter02.Chapter02Runner          : args runner
2023-11-17T17:19:44.319+09:00  INFO 74288 --- [           main] c.e.s.chapter02.Chapter02Runner          : (1) prototype : 589fb74d
2023-11-17T17:19:44.319+09:00  INFO 74288 --- [           main] c.e.s.chapter02.Chapter02Runner          : (2) prototype : 200d1a3d
2023-11-17T17:19:44.319+09:00  INFO 74288 --- [           main] c.e.s.chapter02.Chapter02Runner          : (3) prototype : 7de147e9
2023-11-17T17:19:44.319+09:00  INFO 74288 --- [           main] c.e.s.chapter02.Chapter02Runner          : --
2023-11-17T17:19:44.319+09:00  INFO 74288 --- [           main] c.e.s.chapter02.Chapter02Runner          : (1) prototype : 12567179
2023-11-17T17:19:44.319+09:00  INFO 74288 --- [           main] c.e.s.chapter02.Chapter02Runner          : (2) prototype : 37d699a1
2023-11-17T17:19:44.319+09:00  INFO 74288 --- [           main] c.e.s.chapter02.Chapter02Runner          : (3) prototype : 7f42b194
```

> @Lookup 을 사용하면 프로토타입 빈에 대해 동적으로 손쉽게 가져올 수 있음   
> CGLIB 프록시로 작동되기 때문에 따로 메소드를 구현하지 않아도 된다.   
> 싱글톤빈과 프로토타입빈의 의존관계를 강결합하게 가져가지 않아도 된다.   
> 따로 사용해본적은 없다..   

## (2) Bean Scopes

### Singleton scope
* GoF 의 싱글톤패턴과 차이가 있음
    * GoF 의 싱글톤 패턴은 ClassLoader 당 하나의 인스턴스만 처리되는 싱글톤 패턴임
    * 멀티스레드 환경에 따라, 결국 경우에 따라 인스턴스가 다르게 생성될 수 있다고 함
    * 하지만 스프링의 싱글톤패턴은 스프링이 관리하는 IoC Container 내에서 관리되는 단일 클래스를 의미.
* 스프링의 기본 빈 스코프

### Prototype scope
* 스프링이 유일하게 라이프사이클 내 관리하지 않는 스코프
    * destruction lifecycle 이 호출되지 않는다.
* 프로토타입 빈 자원을 해제하는 역할은 개발자가 직접 해야함

> 기타 이외의 내용은 깊게 파고들진 않음