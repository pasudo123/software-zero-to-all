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
