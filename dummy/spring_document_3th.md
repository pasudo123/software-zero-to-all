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

