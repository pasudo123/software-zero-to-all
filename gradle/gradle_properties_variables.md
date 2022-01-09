# gradle.properties 에서 variable 작성하고 이용해보기
* version 을 build.gradle.kts 에서 하드코딩하지 않고, gradle.properties 파일에서 설정해서 읽어들이는 방식을 해보자.
* 단일 프로젝트 기준이다.

### gradle.properties   
아래와 같이 작성하고 해당 시스템 속성값을 gradle dsl 에서 이용하는 방법을 익힌다.
```properties
systemProp.version.kotlin=1.5.32
systemProp.version.springboot=2.5.8
systemProp.version.springbootManagement=1.0.11.RELEASE
```

### build.gradle.kts
plugins block 에서 아래와 같이 이용한다.
```
plugins {
    val kotlinVersion = System.getProperty("version.kotlin")
    val springBootVersion = System.getProperty("version.springboot")
    val springBootManagementVersion = System.getProperty("version.springbootManagement")

    val versions = StringBuilder()
    versions.appendLine("=======================")
    versions.appendLine("kotlin = $kotlinVersion")
    versions.appendLine("springboot = $springBootVersion")
    versions.appendLine("springboot-management = $springBootManagementVersion")
    versions.appendLine("=======================")
    println(versions.toString())

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springBootManagementVersion
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}
```

결과 
```
> Configure project :
=======================
kotlin = 1.5.32
springboot = 2.5.8
springboot-management = 1.0.11.RELEASE
=======================
```

# reference
* https://docs.gradle.org/current/userguide/build_environment.html