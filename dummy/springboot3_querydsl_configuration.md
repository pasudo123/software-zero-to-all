## springboot3 에서 querydsl 설정하기
* springboot3.x 로 들어오면서 querydsl 설정하는 부분이 변경되었다.
* 그리고 이렇게 변경된 내용을 확인하고 이를 코드에 적용한다.
* `이 글은 2023년 12월 24일 기준으로 작성되었다.`

## 그 이전에, javax.* 패키지에서 jakarta.* 패키지로의 변화가 있다.
* 2018년 Java EE 는 Jakarta EE 로 명칭을 변경하였다.
    * 오라클은 2017년 Java EE 8 릴리즈를 마지막으로 오픈소스SW 를 지원하는 비영리 단체인 이클립스 재단에 Java EE 프로젝트를 이관했다.
* 이클립스 재단에 이관된 Java EE 의 공식명칭은 Jakarta EE
    * 다만 오라클이 Java EE 프로젝트를 이관했지만 상표권은 가지고 있었기 때문에 자바 네임스페이스 사용에 제약이 있었다.
    * 이런 이유로 Jakrata EE 에서는 자바 네임스페이스가 Jakarta 로, javax.* -> jakarta.* 로 API 패키지명이 변경되었다.
* Java EE 는 8 버전을 마지막으로 더이상 릴리즈와 추가적인 기능을 제공하지 않는다.
* [JavaEE 에서 JakartaEE 로의 전환](https://www.samsungsds.com/kr/insights/java_jakarta.html) 내용을 참고

위 내용을 기반해서, 결과적으로 JPA 관련 패키지가 변경되었음을 확인할 수 있었다.
```kotlin
// javax 의 API 패키지명인 persistence 쪽이 jakarta 로 변경
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
```

## Querydsl 설정 (build.gradle.kts)
* 코프링 기준으로 설명한다.
* [스프링 부트 3.0 으로 전환](https://post.dooray.io/we-dooray/tech-insight-ko/back-end/4173/) 내용을 참고
* Jakarta EE 호환버전을 선택할 수 있도록 classifier 를 jakarta 로 설정한다.
* 그 외에 인터넷에 떠돌아다니는 코드가 많은데, 아래 내용정도만 설정해놓으면 `QClass` 까지 정상적으로 만들어지는 것을 확인할 수 있다.

```gradle
kotlin.sourceSets.main {
    // QClass 만들어주는 부분
    setBuildDir("$buildDir")
}

dependencies {
    // querydsl : kapt 를 gradle.build.kts 에 추가
    // springboot 3.x 부터 javax -> jakarta 로 넘어감
    // https://mvnrepository.com/artifact/com.querydsl/querydsl-jpa
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // jakarta.annotation.* 대응, 하지만 querydsl 5.5.0 에선 여전히 javax.annotation 을 사용하고 있음
    // 따라서 없어도 동작은 하지만 그 외 애노테이션 @PostConstruct 등은 jakarta 로 바뀌었음
    kapt("jakarta.annotation:jakarta.annotation-api") 

    // jakarta.persistence.* 대응, JPA 관련 애노테이션
    kapt("jakarta.persistence:jakarta.persistence-api")
    
    //.. 그 외엔 생략.
}
```

