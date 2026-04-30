# 스프링부트 테스트코드 단에서 RestTemplate logging disabled 처리
- 테스트코드에서 RestTemplate 코드 수행시 로깅이 DEBUG 로 남는다.
- 해당 로그를 끄고 싶을 때 아래와 같은 구문을 입력한다. 

# Code
```kotlin
init {
    (LoggerFactory.getILoggerFactory() as LoggerContext).getLogger("org.apache.http").apply {
        this.level = Level.ERROR
    }

    (LoggerFactory.getILoggerFactory() as LoggerContext).getLogger("org.springframework.web.client.RestTemplate").apply {
        this.level = Level.ERROR
    }
}
```
* slf4j 로깅 라이브러리 및 Logback 을 사용하여 특정 로거의 레벨을 설정
* org.apache.http 이름의 로거를 가져오고, 로그레벨은 ERROR 로 변경
* org.springframework.web.client.RestTemplate 이름의 로거를 가져오고, 로그레벨 ERROR 설정