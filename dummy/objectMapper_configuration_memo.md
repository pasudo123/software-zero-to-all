## ObjectMapper 자주쓰는 설정 모음집 (chatGPT 와 함께)
* kotlin + gradle 기반으로 설명

### LocalDateTime, LocalDate, LocalTime 직렬화 설정
> Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling
* ObjectMapper 설정 시 위와 같은 java8 에 추가된 시간/날짜 클래스에 직렬화 에러를 만날 수 있음
* 우선 `com.fasterxml.jackson.datatype:jackson-datatype-jsr310` 의존성을 gradle 에 추가한다.
* 따라서 아래 코드 예시처럼 `.registerModule(JavaTimeModule())` 을 추가한다.
    * 추가해준다고 하더라도, ISO-8601 날짜형식에 맞게 배열로 표시될 수 있다.
* 따라서 timestamp 형식을 disable 하는 `.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)` 을 추가한다.
```kotlin
// class
@Configuration
class CustomMapperConfiguration {
    companion object {
        val mapper: ObjectMapper = ObjectMapper()
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
}

// build.gradle.kts
implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
```

좀 더 확인하면, implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310") 을 추가해주지 않아도 직렬화가 잘되는 것을 확인할 수 있다.
* 다른 의존성에 jsr310 의존성이 있어서 그럴 수 있다.
* 해당 프로젝트에서 `./gradlew dependencyInsight --dependency <dependencyName>` 명령어를 수행하면 아래와 같은 결과를 만난다.
    * 결과적으로 타 의존성에 jsr310 이 포함되어 있음을 볼 수 있다.
    
```shell
com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.5
+--- com.fasterxml.jackson:jackson-bom:2.13.5
|    +--- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.13.5
|    |    +--- org.springframework.boot:spring-boot-starter-json:2.7.9
|    |    |    \--- org.springframework.boot:spring-boot-starter-web:2.7.9
|    |    |         \--- compileClasspath (requested org.springframework.boot:spring-boot-starter-web)
|    |    \--- com.fasterxml.jackson:jackson-bom:2.13.5 (*)
|    +--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.5 (*)
|    +--- com.fasterxml.jackson.module:jackson-module-parameter-names:2.13.5
|    |    +--- org.springframework.boot:spring-boot-starter-json:2.7.9 (*)
|    |    \--- com.fasterxml.jackson:jackson-bom:2.13.5 (*)
|    +--- com.fasterxml.jackson.core:jackson-databind:2.13.5
|    |    +--- org.springframework.boot:spring-boot-starter-json:2.7.9 (*)
|    |    +--- org.springframework.statemachine:spring-statemachine-data-common:3.2.0 (requested com.fasterxml.jackson.core:jackson-databind:2.13.2.1)
|    |    |    \--- org.springframework.statemachine:spring-statemachine-data-jpa:3.2.0
|    |    |         \--- compileClasspath (requested org.springframework.statemachine:spring-statemachine-data-jpa)
|    |    +--- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.13.5 (*)
|    |    +--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.5 (*)
|    |    +--- com.fasterxml.jackson.module:jackson-module-parameter-names:2.13.5 (*)
|    |    +--- com.fasterxml.jackson:jackson-bom:2.13.5 (*)
|    |    \--- com.fasterxml.jackson.module:jackson-module-kotlin:2.13.5
|    |         +--- compileClasspath (requested com.fasterxml.jackson.module:jackson-module-kotlin)
|    |         \--- com.fasterxml.jackson:jackson-bom:2.13.5 (*)
|    +--- com.fasterxml.jackson.core:jackson-annotations:2.13.5
|    |    +--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.5 (*)
|    |    +--- com.fasterxml.jackson.core:jackson-databind:2.13.5 (*)
|    |    +--- io.swagger.core.v3:swagger-models:2.1.2 (requested com.fasterxml.jackson.core:jackson-annotations:2.10.1)
|    |    |    \--- io.springfox:springfox-oas:3.0.0
|    |    |         \--- io.springfox:springfox-boot-starter:3.0.0
|    |    |              \--- compileClasspath
|    |    +--- io.swagger:swagger-models:1.5.20 (requested com.fasterxml.jackson.core:jackson-annotations:2.9.5)
|    |    |    +--- io.springfox:springfox-swagger2:3.0.0
|    |    |    |    \--- io.springfox:springfox-boot-starter:3.0.0 (*)
|    |    |    \--- io.springfox:springfox-swagger-common:3.0.0
|    |    |         +--- io.springfox:springfox-oas:3.0.0 (*)
|    |    |         \--- io.springfox:springfox-swagger2:3.0.0 (*)
|    |    +--- com.fasterxml.jackson:jackson-bom:2.13.5 (*)
|    |    \--- com.fasterxml.jackson.module:jackson-module-kotlin:2.13.5 (*)
|    +--- com.fasterxml.jackson.core:jackson-core:2.13.5
|    |    +--- org.springframework.statemachine:spring-statemachine-data-common:3.2.0 (requested com.fasterxml.jackson.core:jackson-core:2.13.2) (*)
|    |    +--- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.13.5 (*)
|    |    +--- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.5 (*)
|    |    +--- com.fasterxml.jackson.module:jackson-module-parameter-names:2.13.5 (*)
|    |    +--- com.fasterxml.jackson.core:jackson-databind:2.13.5 (*)
|    |    \--- com.fasterxml.jackson:jackson-bom:2.13.5 (*)
|    \--- com.fasterxml.jackson.module:jackson-module-kotlin:2.13.5 (*)
\--- org.springframework.boot:spring-boot-starter-json:2.7.9 (*)
```

### json 을 pretty 하게 보고싶은경우 설정
* 아래와 같이 설정하면 기존에 한줄로 string 이 보이던 형태를 들여쓰기가 되어있는 상태의 json 형태로 노출되도록 해준다.
```kotlin
@Configuration
class CustomMapperConfiguration {

    companion object {
        val mapper: ObjectMapper = ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
    }
}
```