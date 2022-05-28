## @ConfigurationProperties 에서 list + object 같이 사용기
- 리스트 내 요소 값을 단순 wrapper 클래스나 String 이 아닌 객체를 넣어서 사용한다.

## (1) 배열의 인덱스를 그대로 표시하기
```yml
app:
  redis-group[0]:
    host: localhost
    port: 16379
    password: ''
    database: 3
  redis-group[1]:
    host: localhost
    port: 16380
    password: ''
    database: 4
  redis-group[2]:
    host: localhost
    port: 16381
    password: ''
    database: 5
```

## (2) 대시(`-`) 를 사용하기
```yml
app:
  redis-group:
    -
      host: localhost
      port: 16379
      password: ''
      database: 0
    -
      host: localhost
      port: 16380
      password: ''
      database: 1
    -
      host: localhost
      port: 16381
      password: ''
      database: 2
```

(1) 과 (2) 는 yml 에서 표현하는 방법만 다를 뿐, 코드레벨에서는 동일하게 표시될 수 있다.

## code
```kotlin
@Component
@ConfigurationProperties(prefix = "app")
class CustomMultipleRedisProps {

    var redisGroup: MutableList<CustomRedisNode> = mutableListOf()

    class CustomRedisNode(
        var host: String? = null,
        var port: Int? = null,
        var database: Int? = null,
        var password: String? = null
    ) {
        fun getRedisPassword(): RedisPassword {
            return if (password.isNullOrBlank()) RedisPassword.none() else RedisPassword.of(password)
        }
    }
}
```

## 결과
* 위의 코드로 실행 시 작동된다.
* private 가 있는 경우 기본적으로 세터가 필요하다. val 가 아닌 var 로 할당가능한 상태로 두어야 한다.