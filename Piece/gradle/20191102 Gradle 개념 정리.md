# Gradle 개념 정리.

## What is Gradle ?
- [What is Gradle](https://docs.gradle.org/current/userguide/what_is_gradle.html#what_is_gradle)
- 소프트웨어의 타입에 상관없이 유연하게 빌드하기 위한 빌드 툴이다. (범용적인 사용이 가능하다.)
- 플러그인(plugins) 를 통해서 사전에 구축된 기능들과 컨벤션 계층들을 추가할 수 있다.

### The Core model is based on tasks
- 그래들 모델은 작업단위들을(```task```) 직접적인 비연결식 그래프(DAG) 로 모델링하고 있으며, 빌드가 일련의 작업을 수행하고 있으며 이것은 결국 종속성에 연결되어 함께 DAG 를 만드는 것이다.

### ...작성중...

### Task dependencies 
```gradle
/** build.gradle **/
task hello {
    doLast {
        println 'Hello world!'
    }
}
task intro {
    dependsOn hello
    doLast {
        println "I'm Gradle"
    }
}

/** gradle command **/
> gradle -q intro
Hello world!
I'm Gradle
```
