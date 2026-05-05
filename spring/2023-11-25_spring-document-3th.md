## 3회차, 스프링 도큐먼트 공부

## (1) Bean Definition Inheritance
- 부모 bean 에 설정된 정보를 자식 bean 이 상속받아서 필드값 오버라이딩을 할 수 있다. (템플릿의 한 형태)
- 이 방식은 코드를 절약할 수 있게 해준다.

__빈 상속 코드 configuration 정의__
```kotlin
@Configuration
class BeanInheritanceConfiguration {

    @Bean
    fun myParentBean(): MyParentBean {
        return MyParentBean().apply {
            this.name = "홍길동"
            this.age = 20
        }
    }

    @Bean
    fun myChildBean(): MyChildBean {
        return MyChildBean().apply {
            this.name = "이순신"
        }
    }
}

open class MyParentBean {
    var name: String? = null
    var age: Int? = null
}

class MyChildBean : MyParentBean()
```

- 추가적으로 MyParentBean 을 abstract 로 처리할 수 있다. 다만 객체화할 수 없기 때문에 빈을 만들수 없다. 대신 빈을 계층을 구조화시킬 수 있다.

__빈 상속 코드 abtract 이용__
```kotlin
@Configuration
class BeanInheritanceConfiguration {

    @Bean
    fun myChildBean(): MyChildBean {
        return MyChildBean().apply {
            this.name = "이순신"
            this.age = 30
        }
    }

    @Bean
    fun myNewChildBean(): MyNewChildBean {
        return MyNewChildBean().apply {
            this.name = "이성계"
            this.age = 40
        }
    }
}

abstract class MyParentBean {
    var name: String? = null
    var age: Int? = null
}

class MyChildBean : MyParentBean()

class MyNewChildBean: MyParentBean()
```

## (2) Container Extension Points

### Customizing Beans by Using a BeanPostProcessor
- BeanPostProcessor 를 이용해서 빈 생성/의존성 등 로직을 설정할 수 있다.
- 여러 개의 BeanPostProcessor 를 등록할 수 있고 Ordered 를 가지고 순서를 정의한다.
- BeanPostProcessor 는 컨테이너 단위로 적용되고 있으며, 특정 컨테이너에 속한 BeanPostProcessor 는 Spring IoC 컨테이너에 속한 빈들에 대해 후처리 작업을 수행한다.
    - `스프링 기반 웹 애플리케이션이나 혹은 배치단 코드 작업 시 IoC 컨테이너를 N 개 만들어본적은 없음`
- BeanDefinitios 을 변경하려면 BeanFactoryPostProcessor 를 이용한다.
    - 자세한 설명은 뒤에서.
- BeanPostProcessor 는 `두 개` 의 콜백메소드를 제공한다.
    - 첫번째 콜백은 Bean 이 초기화 메소드 호출되기 전에 발생한다.
    - 두번째 콜백은 Bean 이 초기화 메소드 호출되고 이후에 발생한다.
- BeanPostProcessor 는 Bean Instance 에 대한 작업을 수행할 수 있다.
    - 콜백 인터페이스 확인
    - 빈을 프록시로 wrapping 하는 작업
- `BeanPostProcessor 조차도 Bean 으로 등록해서 사전에 빈으로 만들어야 한다. 그러면 ApplicationContext 가 사전에 감지해서 Bean 의 PostProcessor 역할이 된다.`
- BeanPostProcessor 를 등록하는 두가지 방법
    - Bean 으로 등록하여 ApplicationContext 로 등록하는 방법 (위에 적힌 방법)
    - ConfigurableBeanFactory 를 이용하여 BeanPostProcessor 를 등록하는 방법 (Ordered 인터페이스를 준수하지 않는다고 함) Bean 으로 등록하는 것보다 더 일찍 후처리기로 등록된다.
- `스프링 AOP 프록시 자체가 BeanPostProcessor 를 구현하고 있기 때문에 BeanPostProcessor 를 구현하는 클래스와 해당 클래스가 참조하는 빈은 AOP 프록시 대상이 아니다.`

__BeanPostProcessor 는 AOP 프록시 대상이 아닌 경우__
```kotlin
@Configuration
class CustomBeanPostProcessorConfiguration {

    // 명시적으로 빈 선언
    @Bean
    fun myCustomBeanPostProcessor(): BeanPostProcessor {
        return MyCustomBeanPostProcessor()
    }
}

class MyCustomBeanPostProcessor: BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        return bean
    }
}
```
- 위처럼 코드작성하면 MyCustomBeanPostProcessor 클래스는 AOP 프록시 대상이 아니다. 실행 시 콘솔창에 아래와 같은 문구가 노출
    - `Bean 'customBeanPostProcessorConfiguration' of type [com.example.springdocumenttraining.chapter03.CustomBeanPostProcessorConfiguration$$SpringCGLIB$$0] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)`
    - 위 클래스는 AOP 프록시 대상이 아님.
    - BeanPostProcessorChecker 라는 클래스에서 검사를 수행하고 있다. (해당 클래스도 BeanPostProcessor 를 구현하는 클래스)

__BeanPostProcessor 를 AOP 프록시 대상으로 만드는 경우__
- BeanPostProcessor 를 구현한 클래스에 @Component 설정을 하면 로그메시지가 미출력
- 이렇게 하면 BeanPostProcessor 가 자기자신을 참조할 수 있다고도 함.
    - 초기화 단계에서 순환참조에 빠질 수도 있음 + 예상치 못한 동작이 발생.?
```kotlin
@Component
class MyCustomBeanPostProcessor: BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        return bean
    }
}
```

__BeanPostProcessor 의 동작방식 플로우__
```kotlin
@Configuration
class CustomBeanPostProcessorConfiguration {

    @Bean
    fun water(): Water {
        return Water()
    }

    @Bean
    fun myCustomBeanPostProcessor(): BeanPostProcessor {
        return MyCustomBeanPostProcessor()
    }
}

class Water {
    @PostConstruct
    fun init() {
        log.info("@@ water postConstruct")
    }
}

class MyCustomBeanPostProcessor: BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is Water) {
            log.info("before init bean, bean.name=${beanName}")
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is Water) {
            log.info("after init bean, bean.name=${beanName}")
        }
        return bean
    }
}

// 위 처럼 작성 시 출력은 아래처럼 된다.
// BeanPostProcessorWithAopSample$Companion : before init bean, bean.name=water
// BeanPostProcessorWithAopSample$Companion : @@ water postConstruct
// BeanPostProcessorWithAopSample$Companion : after init bean, bean.name=water
```

### AutowiredAnnotationBeanPostProcessor 
- 스프링에서 BeanPostProcessor 가 아닌 AutowiredAnnotationBeanPostProcessor 를 통해서 @Autowired 의존주입을 자동으로 처리함.
- 필요시 AutowiredAnnotationBeanPostProcessor 도 커스텀하게 만들 수 있다.
```kotlin
@Configuration
class CustomAutowiredBeanPostProcessorConfiguration {

    @Bean
    fun myAnnotationBeanPostProcessor(): MyAnnotationBeanPostProcessor {
        return MyAnnotationBeanPostProcessor()
    }
}

class MyAnnotationBeanPostProcessor: AutowiredAnnotationBeanPostProcessor() {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        return bean
    }
}
```

## Customizing Configuration Metadata with a BeanFactoryPostProcessor
- BeanFactoryPostProcessor 는 스프링 Bean 의 메타데이터 configuration 에서 동작하는 인터페이스다.
- 빈이 생성되기 이전에 컨테이너의 동작을 제어할 수 있고 필요한 구성을 적용할 수 있다.
- BeanFactoryPostProcessor 인스턴스를 여러 개 구성할 수 있고 order 를 통해 순서도 제어가능한다. (Ordered 인터페이스가 구현되어 있어야 한다.)
- `메타데이터에 의해 기 생성된 Bean 을 변경하려면 BeanPostProcessor 를 건드린다.`
- `BeanPostProcessor or BeanFactoryPostProcessor 둘은 lazy 하게 초기화되면 안된다`
    - 느린 초기화 시에 빈의 메타데이터 구성시점에 동작을 하지 않을 뿐더러 빈이 만들어지고 난 뒤에도 설정을 변경할 수 없기 때문에 자동으로 eager 처리가 된다.



## Customizing Instantiation Logic with a FactoryBean
- FactoryBean 을 가지고도 빈을 생성할 수 있다.
    - 선행조건은 FactoryBean 을 구현하는 클래스가 스프링 빈으로 등록이 가능하다.
- `&` 기호를 쓰면 FactoryBean 그 자체에도 접근이 가능하다.

__Rice 클래스 및 Rice 의 FactoryBean 선언__
```kotlin
class Rice(
    val name: String
)

// FactoryBean 을 구현하는 클래스는 사전에 빈으로 등록되어 있어야 한다.
// 네이밍도 반환하는 Bean 네임으로 한다.
@Component("rice1")
class RiceFactoryBean : FactoryBean<Rice> {
    override fun getObject(): Rice {
        return Rice("흑미밥")
    }

    override fun getObjectType(): Class<*> {
        return Rice::class.java
    }

    override fun isSingleton(): Boolean {
        return true
    }
}
```

__ApplicationRunner 에서 Bean 그리고 FactoryBean 을 획득하는 구문__
```kotlin
@Component
class Chapter03Runner(
    private val applicationContext: ApplicationContext,
): ApplicationRunner {

    override fun run(args: ApplicationArguments?) {

        // bean 획득
        with(applicationContext.getBean("rice1")) {

            // 흑미밥 반환
            println((this as Rice).name)
        }

        // factoryBean 획득
        with(applicationContext.getBean("&rice1")) {

            // true 반환
            println((this as RiceFactoryBean).isSingleton)
        }
    }
}
```

## (3) Annotation-based Container Configuration
### Using @Autowired
- JSR330 의 @Inject 는 스프링의 @Autowired 대체할 수 있다.
- @Autowired 를 의존성 주입을 위한 임의의 이름을 가진 메소드에 붙일 수 있다.
- @Autowired 를 생성자와 필드에 섞어쓸 수 있다
- @Autowired 는 타입을 리스트/셋/맵으로 받을 수 있다.

__임의의 이름을 가진 메소드로 @Autowired 설정 시 호출__
```kotlin
@Component
class MovieRecommender {

    private lateinit var catalog: MovieCatalog
    private lateinit var preference: MoviePreference

    @Autowired
    fun prepare(
        catalog: MovieCatalog,
        preference: MoviePreference
    ) {
        println("autowired prepare")
        this.catalog = catalog
        this.preference = preference
    }
}

@Component
class MovieCatalog

@Component
class MoviePreference
```

__@Autowired 애노테이션을 다양한 타입으로 설정가능__
```kotlin
interface Milk

@Component
class ChocoMilk : Milk

@Component
class OrangeMilk : Milk

@Component
class BasicMilk : Milk

@Component
class MilkStorage(
    @Autowired
    val milkGroup: List<Milk> // Set<Milk> 도 가능

    // Map<String, Milk> 라고 했을 때 key 값은 빈네임이 된다.
)
```

### Using @Primary
- @Primary 를 이용해서 어느 타입을 빈의 우선순위로 할 지 결정한다.
    - `멀티데이터소스 작업처리할 때 이렇게 진행한 적이 있다.`
```kotlin
@Configuration
class CustomPrimaryConfiguration {

    @Bean
    fun mainStudySource(): StudySource {
        return StudySource()
    }

    @Bean
    fun secondStudySource(): StudySource {
        return StudySource()
    }
}

@Service
class StudySourceProvider(
    // 어느 타입을 빈으로 주입할지 IoC 컨테이너는 알 수 없음
    private val studySource: StudySource
)
```

### Fine-tuning Annotation-based Autowiring with Qualifiers
- @Primary 를 사용하면 여러 빈 후보군 중에서 하나를 선택하는데 유용하지만, 더 많은 제어가 필요한 경우는 @Qualifier 를 이용할 수 있다.
    - @Qualifier 를 이용해서 특정한 후보군을 선정해서 처리할 수 있다.
- @Autowired 는 타입을 기반하여 의존성 주입에 중정을 두고 있기 때문에 @Qualifier 를 타입에 대한 매칭을 세부적으로 가져갈 수 있다.
- @Qulifier 는 Collections 에도 적용할 수 있다.

### Custom Qualifiers
- @Qualifier 를 이용해서 커스텀 애노테이션을 만들 수 있음.
- 가독성이 좋긴 하겠지만, 도입한다고 했을 때, 초반에 거부감이 들 수 있을 것 같다.
```kotlin
@Configuration
class MyCustomConfiguration {

    @Bean
    fun actionBookCatalog(): BookCatalog {
        return BookCatalog("action")
    }

    @Bean
    fun comedyBookCatalog(): BookCatalog {
        return BookCatalog("comedy")
    }
}

class BookCatalog(
    val name: String
)

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Qualifier("comedyBookCatalog")
annotation class Comedy

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Qualifier("actionBookCatalog")
annotation class Action

@Component
class BookRecommender(
    @Action
    private val bookCatalog: BookCatalog
) {

    @PostConstruct
    fun init() {
        println("catalog=${bookCatalog.name}")
    }
}
```

### Using @Value
- 외부에 있는 속성값을 넣을 수 있다.
- @PropertySource 는 application.yml 을 로드할 수 없다. 그래서 로드하려면 직접 구현이 필요하다.
    - PropertySourceFactory 를 구현해서 처리가 필요함, [참고](https://www.baeldung.com/spring-yaml-propertysource)
```kotlin
@Component
@PropertySources(
    value = [
        PropertySource("classpath:application.properties"),
    ]
)
class MyConfigStorage(
    // 코틀린은 @Value 사용 시 `백슬래시` 를 이용한다.
    // 디폴트값은 콜론으로 구분해서 처리할 수 있다.
    @Value("\${person.name: park}")
    val personName: String,
    @Value("\${person.age: 22}")
    val personAge: Int,
) {

    init {
        println("name=$personName, age=$personAge")
    }
}
```
__application.properties__
```properties
person.name=hong
person.age=20
```
