# 스프링 부트 Gradle Build 

## Jar 또는 War 빌드 및 실행하기.
- 빌드된 .jar 또는 .war 파일은 build/libs 에 있다.

### ./gradlew build
```gradle
플러그인을 등록하여야 한다.

(1) jar 파일 
apply plugin: 'java'

(2) war
apply plugin: 'war'
```

### ./gradlew bootJar OR ./gradlew bootWar
#### 스프링 부트 2.X 에서는 gradle 4.0 이상의 버전을 사용하여야 한다.
```
bootJar {
    mainClassName = 'com.baeldung.Application'
}

또는 스프링 부트 DSL 을 사용할 수 있다. (아래)

springBoot {
    mainClassName = 'com.baeldung.Application'
}
```

### ./gradlew bootRun
#### 스프링 부트를 빌드하지 않고 실행할 수 있다.
```gradle
bootRun {
    main = 'com.example.demo.Application'
}
```

### Running Application
```
java -jar build/libs/demo.jar
```

### dependecies
- compile 
  - compile 시점에 필요한 디펜던시 라이브러리들을 컴파일로 정의
- runtime
  - 런타임 시에 참조할 라이브러리르 정의, 기본적으로 컴파일 라이브러리를 포함
- compileOnly
  - 컴파일 시점에만 사용하고 런타임에는 필요없는 라이브러리를 정의
- testCompile
  - 프로젝트의 테스트를 위한 디펜던시 라이브러리를 정의. 기본적으로 컴파일된 클래스와 컴파일 라이브러리를 포함
  
  
