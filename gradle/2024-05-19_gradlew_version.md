# gradlew 이용. 버전확인
- 버전을 확인하는 방법은 인텔리제이에서 gradle 탭을 눌러서 확인하는 방법이 있다.
- 혹은 ./gradlew 명령어를 통해서 확인하는 방법도 있다.

## dependencyInsight 를 이용하여 버전확인
- ./gradlew dependencyInsight 명령어는 Gradle 프로젝트에서 특정 의존성에 대한 정보를 제공하는 데 사용한다. 
- 이 명령어를 사용하면 특정 의존성이 어떤 경로를 통해 프로젝트에 포함되었는지, 그리고 그 버전이 무엇인지 확인이 가능하다.

### spring-boot-starter-undertow 의존성의 `runtimeClasspath` 구성에서 버전을 확인한다.
```
// ./gradlew dependencyInsight --dependency ${dependencyName} --configuration ${compileClasspath | runtimeClasspath | ...}
❯ ./gradlew dependencyInsight --dependency spring-boot-starter-undertow --configuration runtimeClasspath
```

### 결과내용
```shell
> Task :dependencyInsight
org.springframework.boot:spring-boot-starter-undertow:2.7.6 (selected by rule)
  Variant runtimeElements:
    | Attribute Name                     | Provided     | Requested    |
    |------------------------------------|--------------|--------------|
    | org.gradle.status                  | release      |              |
    | org.gradle.category                | library      | library      |
    | org.gradle.dependency.bundling     | external     | external     |
    | org.gradle.jvm.version             | 8            | 11           |
    | org.gradle.libraryelements         | jar          | jar          |
    | org.gradle.usage                   | java-runtime | java-runtime |
    | org.gradle.jvm.environment         |              | standard-jvm |
    | org.jetbrains.kotlin.platform.type |              | jvm          |

org.springframework.boot:spring-boot-starter-undertow -> 2.7.6
\--- runtimeClasspath

A web-based, searchable dependency report is available by adding the --scan option.

BUILD SUCCESSFUL in 397ms
1 actionable task: 1 executed
```
- spring-boot-starter-undertow 는 2.7.6 버전으로 확인됨
- `selected by rule` 은 --configuration 으로 인해 규칙에 의해 버전이 선택되었음을 의미.
- Variant runtimeElements 의미
    - org.gradle.status : 의존성 상태
    - org.gradle.category : 의존성 카테고리
    - org.gradle.dependency.bundling : 의존성 번들링 방식
    - org.gradle.jvm.version : 제공 JVM 버전 및 요청 JVM 버전
    - org.gradle.libraryelements : 의존성이 제공되는 형식, jar 로 제공
    - org.gradle.usage : 사용용도
    - org.gradle.jvm.environment : JVM 환경
    - org.jetbrains.kotlin.platform.type : 플랫폼 타입