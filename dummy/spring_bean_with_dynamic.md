## BeanFactoryPostProcessor & BeanPostProcessor 그리고 동적 Bean 추가
동적으로 빈을 추가해야하는 일이 생겼다.

## BeanFactoryPostProcessor
* applicatoin context 에서 빈에 프로퍼티 값을 재정의하고 조작하기 위해 시스템 관리자가 유용하게 사용할 수 있다.
    * xml 설정이나 컴포넌트 스캔에 의해서 설정된 빈들에 대해서 접근할 수 있다.
* BeanDefinitions 이 모두 loaded 가 되고 난 후에 호출된다.
    * BeanDefinition 을 조작하는 것이다.
    * BeanDefinition 은 Bean Instance 가 아니다.
    * Bean Instance 를 조작하려면, `BeanPostProcessor` 를 구현해야 한다.
* `BeanFactoryPostProcessor` 에서 `beanFactory.getBean("~~~")` 으로 접근하지 말기.
    * 기존 bean 의 라이프 사이클에 맞지 않을 뿐더러, 예상치 못한 오류가 발생할 수 있다. ([참고](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-extension-factory-postprocessors))

### BeanFactoryPostProcessor 구현
```kotlin
@Component
class CustomBeanFactoryPostProcessor : BeanFactoryPostProcessor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val lines = StringBuilder().apply {
            this.appendLine()
            this.appendLine("===========================================")
            this.appendLine("called post processor bean factory...")
            this.appendLine("===========================================")
        }

        for (beanName in beanFactory.beanDefinitionNames) {
            log.info("beanName : $beanName")
            val beanDefinition = beanFactory.getBeanDefinition(beanName!!)
            // 여기에서 빈에 대한 조작을 수행할 수 있다.
        }
        
        log.info(lines.toString())
    }
}
```


## BeanPostProcessor
* Spring IoC Container 에 의해 bean 이 인스턴스화 되고 난 후, BeanPostProcessor 가 작업을 수행한다.
* bean 을 커스터마이징 하기 위한 용도로 사용할 수 있다.

### BeanPostProcessor 구현
```kotlin
@Component
class CustomBeanPostProcessor : BeanPostProcessor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (checkBeanName(beanName)) {
            log.info("===> before init : $bean")
        }
        return super.postProcessBeforeInitialization(bean, beanName)
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (checkBeanName(beanName)) {
            log.info("===> after init : $bean")
        }
        return super.postProcessAfterInitialization(bean, beanName)
    }

    private fun checkBeanName(beanName: String): Boolean {
        return (beanName == "springbootBasisApplication")
    }
}
```

## 호출순서는?
<img style="border: 1px solid black;" src="https://i.stack.imgur.com/jg555.png" />

그림을 기준으로 아래와 같이 생각한다.
* Bean detection -> `BeanFactoryPostProcessor` -> Bean Init & DI -> `BeanPostProcessor`
* Bean 의 정의를 수정하고자 하는 경우 -> `BeanFactoryPostProcessor`
* Bean 인스턴스를 수정하고자 하는 경우 -> `BeanPostProcessor`

## 동적으로 빈을 추가한다면?
* 동적으로 빈을 추가한다면 빈을 정의가 모두 로드된, BeanFactoryPostProcessor 에서 하는게 좋을듯 하다.   
* 하지만 별도의 BeanFactoryPostProcessor SPI (servie provider interface) 로 제공하는 인터페이스가 있어 그걸 이용하려고 한다.
* `BeanDefinitionRegistryPostProcessor` 을 아래의 코드처럼 이용할 수 있다.
    * EnvironmentAware 를 사용하면, program arguments 로 보내준 `--{property}={values}` 값을 읽어들일 수 있다. (대시는 무조건 2개가 있어야 한다.)
    * 아래에선 --services=apple,banana 를 읽어들이고 있다.

```kotlin
@Component
class CustomBeanDefRegistryPostProcessor : BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private lateinit var env: Environment

    private val log = LoggerFactory.getLogger(javaClass)

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        this.logging("[beanDef] postProcessBeanFactory ...")
    }

    override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {

        val services = env.getProperty(Constant.SERVICES)?.split(Constant.COMMA) ?: emptyList()
        this.logging(services.joinToString(", "))

        services.forEach { serviceName ->
            val propertyValues = MutablePropertyValues().apply {
                this.add("name", serviceName)
            }

            val beanDef = RootBeanDefinition(MyService::class.java).apply {
                this.targetType = MyService::class.java
                this.role = BeanDefinition.ROLE_APPLICATION // 사용자 정의 빈으로 간주
                this.scope = BeanDefinition.SCOPE_PROTOTYPE // 싱글톤이 아닌 프로토타입
                this.propertyValues = propertyValues
            }

            registry.registerBeanDefinition("${serviceName}KService", beanDef)
        }



        this.logging("[beanDef] postProcessBeanDefinitionRegistry ...")
    }

    override fun setEnvironment(environment: Environment) {
        this.env = environment
    }
}
```

## 참고
* https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html
* https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanFactoryPostProcessor.html
* https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/support/BeanDefinitionRegistryPostProcessor.html
* https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-extension
* https://blog.jdriven.com/2015/04/spicy-spring-dynamically-create-your-own-beandefinition/
* https://t.motd.kr/posts/1618/java-spi-service-provider-interface/
* https://stackoverflow.com/questions/30455536/beanfactorypostprocessor-and-beanpostprocessor-in-lifecycle-events 