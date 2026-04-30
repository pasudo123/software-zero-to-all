## spring jpa 에서 querydsl 사용하기 with kotlin

## 1. 우선 build.gradle.kts 에서 아래와 같이 작성한다.
* 사전에 querydsl 의 의존성을 다운받을 수 있도록 헤주어야 한다.
```yml
dependencies {
    // querydsl : kapt 를 gradle.build.kts 에 추가
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    kapt("com.querydsl:querydsl-apt:${queryDslVersion}:jpa")
    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}")
}
```

## 2. querydsl QClass 생성을 위한 패스를 지정해준다.
* 코틀린 컴파일을 통해 QClass 를 생성할 수 있다.
```yml
kotlin.sourceSets.main {
    println("kotlin sourceSets buildDir :: $buildDir")
    // querydsl QClass 생성
    setBuildDir("$buildDir")
}
```

## 3. 코드레벨에서 작성해준다.
* 패키지는 본인이 하고싶은 패키지 위치로 선정
```kotlin
@Configuration
class QuerydslConfiguration(
    @PersistenceContext
    private val entityManager: EntityManager
) {

    @Bean
    fun queryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}
```
* JPAQueryFactory 를 만들 때는 Configuration 에서 빈으로 등록해서 사용한다.
* EntityManager 는 스레드 세이프 하지 않기 때문에 queryDsl Repository 에서 사용하려면, 이를 어떻게 주입을 해야하는가? 
    * container-managed entityManager 로 설정한다. : @PersistenceContext 를 붙여서 넣으면 해결이 되는가?
        * `해결이 된다.` : 컨테이너가 해당 엔티티 매니저로 관리하게끔 설정하고, 그것을 기반으로 JPAQueryFactory 를 만들기 때문
        * [참고링크](https://www.baeldung.com/jpa-hibernate-persistence-context)
    * entityManager 에 @PersistenceContext 를 붙이고, entityManager 는 트랜잭션 당 영속성 컨텍스트를 구성할 수 있다.
    * 그리고 해당 객체는 컨테이너 관리 `프록시` 로 설정되어 invoke() 메소드를 호출한다.
   
아래 호출 순서
```shell
EntityManager.persis() 호출 ->
SharedEntityManagerCreator.invoke() 호출 -> 
EntityManagerFactoryUtils.doGetTransactionalEntityManager() 수행, entityManager 만듦
```
* persistenceContext 를 전달하기 위해 애플리케이션은 코드 여기저기에 entityManager 참조를 여기저기 설정할 필요가 없다. [참고](https://docs.oracle.com/javaee/7/tutorial/persistence-intro003.htm)
    * @Autowired 는 스프링 기반 애노테이션
    * @PersistenceContext 는 java.persistence annotation java EE
        * JPA 스펙에서 제공하는 기능, 영속성 컨텍스트를 주입하는 표준 애노테이션 (컨테이너 기반 관리)
* EntityManager 를 관리하는 두 가지 방법
    * Application-managed
        * EntityManager em = EntityManagerFactory.createEntityManager()
    * Container-managed