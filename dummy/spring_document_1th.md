## 1회차, 스프링 도큐먼트 공부

### (1) Introduction to the Spring IoC Container and Beans
* org.springframework.beans 와 org.springframework.context 패키지는 스프링 프레임워크의 IoC컨테이너 기반이다.
* BeanFactory 는 Spring IoC컨테이너의 핵심 인터페이스임. 해당 인터페이스는 Bean 객체의 생성/구성/관리를 처리한다. 하지만 기본적인 기능만 제공하고 있기 때문에 더 많은 기능을 사용하고자 하는 경우에는 `ApplicationContext` 를 이용하여야 한다. `ApplicationContext` 는 BeanFactory 의 상위집합 개념이다. 

---

### (2) Container Overview
* ApplicationContext 인터페이스는 IoC컨테이너 그 자체를 표현하고 Bean 을 instantiating/configuring/assembling 한다.
  * instantating: 객체화
  * configuring: 구성/설정
  * assembling: 의존관리/모듈설정
* ApplicationContext 는 configuration metadata 를 읽음으로써, 앞선 행위를 수행할 수 있다. configuration metadata 를 설정하는 방법에는 여러가지가 존재한다. XML, Java annotations, Java code,...

<img src="../Image/20231028_springdoc01.png" width="400" />
비즈니스 오브젝트가 설정 메타데이터와 SpringContainer 를 만나서 Bean 으로 처리됨을 보여주고 있다.

--- 

#### ClassPathXmlApplicationContext : ApplicationContext
* 전통적으로 XML 에 메타데이터 구성요소를 정의함으로써 빈을 생성/관리할 수 있는 IoC컨테이너
* xml 구성을 위해선 별도 xml 파일을 만들고 거기에 `<bean>` 태그를 붙여서 설정하여야 한다.

__sample.xml__
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="coffeeService" class="com.example.springdocumenttraining.chapter01.CoffeeService">
        <property name="coffeeRepository" ref="coffeeRepository"/>
        <!-- <constructor-arg name="coffeeRepository" ref="coffeeRepository"/> -->
    </bean>

    <bean id="coffeeRepository" class="com.example.springdocumenttraining.chapter01.CoffeeRepository"/>
</beans>
```

__CoffeeService.kt__   
xml 파일에 `<property>` 로 설정하였을 때, `lateinit var` 키워드를 이용해서 설정 의존성 주입 가능
```kotlin
class CoffeeService {

    lateinit var coffeeRepository: CoffeeRepository

    fun getCoffee(): Coffee {
        return coffeeRepository.findCoffee()
    }
}

class CoffeeRepository {

    fun findCoffee(): Coffee {
        return Coffee(
            name = "아메리카노",
            price = (Random.nextLong(100000) / 100)
        )
    }
}
```

xml 파일에 `<constructor-arg>`로 설정하였을 때, 생성자 인자로 의존성 주입 가능
```kotlin
class CoffeeService(
    private val coffeeRepository: CoffeeRepository
) {

    fun getCoffee(): Coffee {
        return coffeeRepository.findCoffee()
    }
}
```

__ApplicationRunner.kt__   
스프링부트 애플리케이션이 뜨는 시점에서 xml 파일을 이용해서 빈을 등록하고 조회한다.
```kotlin
@Component
class Chapter01Runner: ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        val context = ClassPathXmlApplicationContext("xmlbeans/sample.xml")

        with(context) {
            log.info("- beanDefinitionCount=${this.beanDefinitionCount}")
            log.info("- beanDefinitionNames=${this.beanDefinitionNames.toList()}")
            val coffeeServiceBean = this.getBean("coffeeService") as CoffeeService
            log.info("- coffServiceBean=${coffeeServiceBean.javaClass}")
            val coffeeRepositoryBean = this.getBean("coffeeRepository") as CoffeeRepository
            log.info("- coffeeRepositoryBean=${coffeeRepositoryBean.javaClass}")
        }
    }
}
```

```console
// 출력되는 값
- beanDefinitionCount=2
- beanDefinitionNames=[coffeeService, coffeeRepository]
- coffServiceBean=class com.example.springdocumenttraining.chapter01.CoffeeService
- coffeeRepositoryBean=class com.example.springdocumenttraining.chapter01.CoffeeRepository
```

--- 

#### GenericGroovyApplicationContext : ApplicationContext
그루비 언어를 통해서도 bean 주입이 가능하다.   

___sample.groovy___
```groovy
beans {
    coffeeRepository(CoffeeRepository)
    coffeeService(CoffeeService, coffeeRepository)
}
```

__ApplicationRunner.kt__   
아래처럼 설정할 수 있다. 나머지는 이전 코드와 동일하다.
```kotlin
@Component
class Chapter01Runner : ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        // groovy 를 쓰기 위해선 'org.codehaus.groovy:groovy-all:3.0.19' 를 추가.
        val context = GenericGroovyApplicationContext("groovybeans/sample.groovy")
    }
}
```

> 하지만 우리가 애플리케이션 코드 내에서 getBean() 메소드를 호출할 일이 없음. 스프링 웹 프레임워크 같은 경우 의존성 주입이 자동적으로 제공되기 때문에 별도 개발자가 액션을 취할일은 없음



---

## 정리
* DI 디자인 패턴을 구현하기 위해서 IoC컨테이너를 이용한다. e.g. ApplicationContext, ClassPathXmlApplicationContext 등
* IoC컨테이너를 이용하지 않더라도 DI 를 구현할 수 있다. (샘플코드 필요)

