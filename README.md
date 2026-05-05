# SoftwareZeroToALL

부족한 소프트웨어 지식을 채워넣기 위한 공간.

문서는 항목별로 묶고, 같은 항목 안에서는 가능하면 작성일 순서로 정리한다. 외부 저장소나 블로그 링크는 하단의 [외부 참고](#외부-참고)에 따로 둔다.

## 목차

- [Computer Science](#computer-science)
- [Backend & Spring](#backend--spring)
- [Infrastructure](#infrastructure)
- [AI](#ai)
- [Command & Git](#command--git)
- [Books & Courses](#books--courses)
- [Personal & Notes](#personal--notes)
- [외부 참고](#외부-참고)

## Computer Science

### Network & HTTP

- 2018-04-15 [한양대 Network MOOC 수강 내용 정리](./network/2018-04-15_network-mooc.md)
- 2019-03-07 [HTTPS](./book/http-완벽가이드/2019-03-07_https.md)
- 2024-05-20 [http](./dummy/2024-05-20_http.md)

### Database

- 2018-07-28 [Difference Between Statement & PreparedStatement In JDBC](./database/2018-07-28_difference-between-statement-and-preparedstatement-in-jdbc.md)
- 2018-09-30 [transaction](./database/2018-09-30_transaction.md)
- 2018-10-28 [join sql](./database/2018-10-28_join-sql.md)
- 2019-01-09 [데이터베이스 정의](./database/2019-01-09_database.md)
- 2019-03-01 [index](./database/2019-03-01_index.md)
- 2020-12-15 [transaction isolation level](./database/2020-12-15_transaction-isolation-levels.md)
- 2020-12-16 [mysql truncate](./database/2020-12-16_truncate.md)
- 2021-04-30 [mysql system variables](./database/2021-04-30_mysql-system-variables.md)
- 2021-05-01 [mysql lock and transaction](./database/2021-05-01_mysql-lock-and-transaction.md)
- 2022-01-23 [mysql query : show processlist](./database/2022-01-23_show-processlist.md)
- 2022-01-24 [mysql clustered index & secondary indexes](./database/2022-01-24_clustered-index-and-secondary-indexes.md)
- 2022-01-29 [mysql query : 단일 인덱스, 다중 인덱스 차이는 뭔가](./database/2022-01-29_single-index-composite-index.md)
- 2022-02-11 [mysql table, column 캐릭터 셋 설정](./database/2022-02-11_table-column-characterset.md)
- 2022-04-20 [mysql log 조회하기](./database/2022-04-20_mysql-show-log.md)
- 2022-05-22 [mysql partition](./database/2022-05-22_table-partition.md)
- 2022-08-28 [mysql table size 확인하기](./database/2022-08-28_mysql-get-table-size.md)
- 2024-05-11 [mysql binlog 파일을 활용 데이터복구 : mysqlbinlog 이용](./database/2024-05-11_data-restore-with-binlog.md)

### OOP & Architecture

- 2018-10-07 [객체지향 언어 배경](./object-oriented-programming/2018-10-07_객체지향-언어의-등장.md)
- 2019-02-19 [객체지향 언어 이해 : 유연성](./object-oriented-programming/2019-02-19_객체지향-언어의-올바른-이해-유연성.md)
- 2019-02-19 [객체지향 언어 이해 : 유연성과 객체지향 4대 특성](./object-oriented-programming/2019-02-19_객체지향-언어의-올바른-이해-유연성-및-추가특징.md)
- 2020-02-28 [왜 정적팩토리 메소드를 쓰는걸까?](./personal/2020-02-28_왜-정적-팩토리-메소드-패턴을-쓰는가.md)
- 2020-03-03 [서비스 레이어 vs 비즈니스 로직](./personal/2020-03-03_서비스레이어와-비즈니스로직과의-관계.md)
- 2020-03-10 [객체지향의 TDA : Tell Don't ASK](./personal/2020-03-10_tell-don-t-ask.md)
- 2022-01-15 [ddd with pivotal : 은행 시스템으로 살펴보는 DDD 약식](./development/2022-01-15_ddd-with-pivotal.md)
- 2024-04-10 [세미나: 잘 키운 모노리스 하나 열 마이크로서비스 안부럽다](./dummy/2024-04-10_wellgrown-monolith.md)
- 2026-04-12 [DDD 계층 구조와 DIP 적용 실습](./development/2026-04-12_ddd-layered-architecture-dip-order-practice.md)

## Backend & Spring

### Spring, JPA, QueryDSL, Spring Boot

- [Spring 문서 인덱스](./spring/README.md)
- 2022-03-09 [spring jpa 에서 hibernate 바인딩된 데이터 조회하기](./spring/2022-03-09_jpa-hibernate-show-binding-value.md)
- 2022-03-26 [spring jpa 에서 @OneToOne, 1:1 관계에 대한 지연로딩이 안된다.](./spring/2022-03-26_jpa-one-to-one-problem.md)
- 2022-03-26 [springboot 2.x 에서 querydsl 설정하기](./spring/2022-03-26_jpa-querydsl-with-kotlin.md)
- 2022-04-17 [spring bean : BeanFactoryPostProcessor & BeanPostProcessor 그리고 동적 Bean 추가](./spring/2022-04-17_spring-bean-with-dynamic.md)
- 2022-05-28 [spring @configurationProperties 사용 : list + object](./spring/2022-05-28_spring-configuration-properties-list-object.md)
- 2023-05-01 [spring @Transactional 롤백은 UnCheckedException 을 throw 할 경우에만 발생한다](./spring/2023-05-01_spring-transaction-rollback-with-uncheckedexception.md)
- 2023-05-28 [spring 에서 objectMapper 자주 쓰는 설정](./spring/2023-05-28_objectmapper-configuration-memo.md)
- 2023-07-08 [springboot 에서 에러를 핸들링하영 응답하기](./spring/2023-07-08_springboot-error-handling.md)
- 2023-07-13 [springboot 에서 sseEmitter 사용기](./spring/2023-07-13_springboot-sseemitter.md)
- 2023-07-23 [springboot 에서 @Async ThreadPoolTaskExecutor 설정](./spring/2023-07-23_springboot-@async.md)
- 2023-08-15 [redis pub/sub : 설정된 정보를 모든 서버에 반영하는 방법](./spring/2023-08-15_springboot-prop-with-redis-pubsub.md)
- 2023-09-26 [프로메테우스와 스프링부트 metrics](./spring/2023-09-26_prometheus-with-springboot.md)
- 2023-10-28 [spring document study](./spring/2023-10-28_spring-document-study.md)
- 2023-12-24 [springboot 3.x 에서 querydsl 설정하기](./spring/2023-12-24_springboot3-querydsl-configuration.md)
- 2024-01-20 [spring 로컬캐시 사용기](./spring/2024-01-20_spring-cache.md)
- 2024-05-06 [hikari CP](./spring/2024-05-06_hikari.md)
- 2024-05-06 [spring @Transactional propagation & proxy](./spring/2024-05-06_spring-transactional-propagation-and-proxy.md)
- 2024-11-20 [springboot RestTemplate logging disabled In TestCode](./spring/2024-11-20_resttemplate-logging-disabled.md)
- 2024-11-25 [spring jpa 에서 strategy = GenerationType.IDENTITY 로 최초 생성 시 에러가 발생하면?](./spring/2024-11-25_jpa-strategy-identity-error.md)
- 2025-02-23 [springboot graceful shutdown](./spring/2025-02-23_springboot-graceful-shutdown.md)
- 2025-03-03 [springboot warmup](./spring/2025-03-03_springboot-warmup.md)
- 2026-03-22 [Caffeine Local Cache 딥다이브](./spring/2026-03-22_caffeine-local-cache-deep-dive.md)
- 2026-03-22 [Caffeine 내부구조 심화](./spring/2026-03-22_caffeine-local-cache-internals-deep-dive.md)
- 2026-03-24 [RequestContextHolder 실전 가이드](./spring/2026-03-24_request-context-holder-practice.md)
- 2026-04-12 [Eventual Consistency 실습](./spring/2026-04-12_eventual-consistency-review-summary-practice.md)
- 2026-04-13 [Caffeine Local Cache 활용 패턴 정리](./spring/2026-04-13_caffeine-local-cache-patterns.md)

### Backend Notes

- 2021-05-14 [graphql](./backend/graphql/2021-05-14_youtube-tutorial-record.md)
- 2024-04-16 [grpc](./dummy/2024-04-16_grpc.md)
- 2024-04-23 [java computeIfPresent, putIfAbsent 사용](./dummy/2024-04-23_java-computeifpresent-putifabsent.md)

### Kotlin, Coroutine, Java

- 2022-01-15 [왜 jvm 인자값은 -D 로 시작하는가?](./personal/2022-01-15_jvm-d-option.md)
- 2023-01-08 [coroutine : coroutineScope vs supervisorScope](./dummy/2023-01-08_coroutine-coroutinescope-supervisorscope.md)
- 2024-03-09 [불공변성 & 공변성 & 반공변성](./dummy/2024-03-09_variance.md)
- 2024-05-05 [jvm : String Constant Pool](./dummy/2024-05-05_jvm-string-constant-pool.md)
- 2024-05-15 [kotlin sequence, lazy evaluation vs eager evaluation](./dummy/2024-05-15_lazy-evaluation-and-eager-evaluation.md)
- 2024-12-15 [kotlin mutex & semaphore](./dummy/2024-12-15_kotlin-mutex-semaphore.md)
- 2025-02-17 [kotlin sort](./dummy/2025-02-17_kotlin-sorted.md)
- 2025-06-11 [coroutine : supervisorJob 을 안쓰면 발생하는 문제. coroutineScope 에서 익셉션 발생 시](./dummy/2025-06-11_coroutine-supervisorjob-with-error.md)

### Reactive, Spark, Elasticsearch

- 2022-05-22 [t academy : spark 개념과 활용](./development/2022-05-22_t-academy-spark01.md)
- 2022-05-22 [t academy : spark의 실시간/배치](./development/2022-05-22_t-academy-spark02.md)
- 2022-11-20 [toby reactive : pub 과 sub 사이에 operation 을 추가해보자](./development/2022-11-20_toby-reactive-operation.md)
- 2023-02-12 [reactive 혼자 메모해놓은 것들 : 더미로 쌓아둔 것](./development/2023-02-12_reactive-dummy.md)
- 2025-03-10 [elasticsearch 실무적용](./dummy/2025-03-10_elasticsearch-practical.md)

### Redis, Kafka, Messaging

- 2024-03-17 [redis pub/sub 동작원리](./dummy/2024-03-17_redis-pub-sub-under-the-hood.md)
- 2024-05-01 [kafka](./dummy/2024-05-01_kafka.md)
- 2024-05-01 [redis-cli](./dummy/2024-05-01_redis-cli.md)
- 2024-06-08 [redis 가 빠른이유](./dummy/2024-06-08_why-redis-fast.md)
- 2025-01-04 [redis cache 전략](./dummy/2025-01-04_redis-cache-strategy.md)
- 2025-07-12 [redis 를 이용한 동시성 제어](./dummy/2025-07-12_redis-lock-usage.md)

## Infrastructure

### AWS, Nginx, Docker

- 2019-08-18 [aws-zero-to-all](./infra/2019-08-18_aws-zero-to-all.md)
- 2020-01-11 [nginx-applying-ssl(https)](./infra/nginx/2020-01-11_nginx-ssl.md)
- 2020-09-10 [docker-zero-to-all](./infra/docker/2020-09-10_docker-zero-to-all.md)
- 2020-10-28 [docker-images](./infra/docker/2020-10-28_docker-images.md)
- 2020-12-29 [nginx-zero-to-all](./infra/nginx/2020-12-29_nginx-zero-to-all.md)
- 2021-01-10 [docker-swarm-zero-to-all](./infra/docker/2021-01-10_docker-swarm-zero-to-all.md)
- 2023-08-14 [CI/CD jenkins 설정 삽질기](./dummy/2023-08-14_jenkins-exercise.md)

### Kubernetes

- 2018-10-07 [k8s ingress nginx timeout 설정](./dummy/2018-10-07_nginx-ingress-setting.md)
- 2021-02-07 [kubernetes, k8s](./infra/k8s/README.md)
- 2021-03-14 [kubectl run vs create vs apply](./infra/k8s/2021-03-14_kubectl-run-vs-create-vs-apply.md)
- 2021-05-05 [kubectl commands](./infra/k8s/2021-05-05_kubectl-commands.md)
- 2022-06-27 [k8s service type](./infra/k8s/2022-06-27_know-service-type.md)
- 2022-07-06 [resources.requests & resources.limits](./infra/k8s/2022-07-06_know-resources-requests-and-resources-limits.md)
- 2024-03-23 [kubernetes, k8s openlens](./infra/k8s/2024-03-23_open-lens.md)
- 2025-08-21 [k8s probe 설정](./dummy/2025-08-21_k8s-probe.md)
- 2026-03-24 [K8s 다중 파드에서 Local Cache 운영 패턴](./infra/k8s/2026-03-24_k8s-local-cache-patterns.md)

## AI

### Overview & Tools

- [AI 문서 인덱스](./ai/README.md)
- 2026-05-04 [Codex CLI](./ai/2026-05-04_codex-cli.md)
- 2026-05-05 [LLM 개요](./ai/2026-05-05_llm-overview.md)

## Command & Git

### Linux, Shell, CLI

- 2020-12-14 [awk](./command/2020-12-14_linux-awk-command.md)
- 2020-12-14 [kill](./command/2020-12-14_linux-kill-command.md)
- 2020-12-14 [ln : hard link & symbolic link](./command/2020-12-14_linux-ln-command.md)
- 2020-12-14 [nohup](./command/2020-12-14_linux-nohup-command.md)
- 2020-12-14 [shell :: variable](./command/2020-12-14_shell-variable-command.md)
- 2020-12-15 [watch](./command/2020-12-15_linux-watch-command.md)
- 2021-01-09 [htop](./command/2021-01-09_linux-htop-command.md)
- 2021-01-10 [grep](./command/2021-01-10_linux-grep-command.md)
- 2021-10-11 [shell :: &> dev/null/](./command/2021-10-11_shell-devnull.md)
- 2022-01-05 [jq](./command/2022-01-05_jq-command.md)
- 2022-01-12 [ll](./command/2022-01-12_ll-command.md)
- 2022-04-12 [rsync](./command/2022-04-12_linux-rsync-cmd.md)
- 2022-04-23 [tcpdump](./command/2022-04-23_tcpdump.md)
- 2022-10-03 [heredoc](./command/2022-10-03_heredoc.md)
- 2022-10-24 [xargs](./command/2022-10-24_linux-xargs.md)
- 2023-01-20 [which](./command/2023-01-20_which.md)
- 2023-03-25 [httpie](./command/2023-03-25_httpie-command.md)
- 2023-09-23 [curl](./command/2023-09-23_curl.md)
- 2024-05-05 [lsof](./command/2024-05-05_lsof.md)

### Git & Gradle

- 2019-10-11 [gradle build](./gradle/2019-10-11_gradle-build.md)
- 2019-11-02 [gradle concept](./gradle/2019-11-02_gradle-concept.md)
- 2019-11-02 [gradle multi module project](./gradle/2019-11-02_gradle-multi-module.md)
- 2021-03-12 [git revert](./git/2021-03-12_revert.md)
- 2021-12-31 [git reset](./git/2021-12-31_reset.md)
- 2022-01-09 [gradle 에서 변수값 지정하는 방법](./gradle/2022-01-09_gradle-properties-variables.md)
- 2023-06-24 [git reset --hard head](./dummy/2023-06-24_git-reset-hard-head.md)
- 2024-05-19 [gradlew 이용 버전확인](./gradle/2024-05-19_gradlew-version.md)

## Books & Courses

### Books

- 2022-01-22 [real mysql 8.0](./book/real-mysql8.0/2022-01-22_real-mysql-8-0.md)
- 2022-02-08 [쿠버네티스 인 액션](./book/2022-02-08_k8s-in-action.md)
- 2023-08-05 [가상면접사례로 배우는 대규모 시스템 설계 기초 1](./book/2023-08-05_system-design-interview.md)
- 2023-11-05 [성공과 실패를 결정하는 1%의 네트워크 원리](./book/2023-11-05_1-percent-network.md)
- 2024-02-27 [가상면접사례로 배우는 대규모 시스템 설계 기초 2](./book/2024-02-27_system-design-interview-2.md)
- 2024-03-09 [대규모 설계시스템 기초2 : base32/base64](./dummy/2024-03-09_base64.md)
- 2024-04-21 [대규모 설계시스템 기초2 : two-phase commit](./dummy/2024-04-21_two-phase-commit.md)
- 2024-04-21 [대규모 설계시스템 기초2 : database transaction schedule](./dummy/2024-04-21_db-transaction-schedule.md)
- 2024-06-22 [코틀린, 코루틴 : kotlin, coroutine](./book/2024-06-22_kotlin-coroutine.md)

### Courses

- 2019-08-04 [Udemy, Spring MVC Validation](./udemy/2019-08-04_spring-mvc-validation.md)
- 2019-08-05 [Udemy, Hibernate Tutorial](./udemy/2019-08-05_hibernate-tutorial.md)
- 2019-08-17 [Udemy, OneToMany & ManyToOne](./udemy/2019-08-17_hibernate-@onetomany-@manytoone.md)
- 2019-08-24 [인프런, 자바 ORM 표준 JPA 프로그래밍 : atoz](./inflearn/README.md)

## Personal & Notes

### 단편지식

- 2019-10-27 [password 저장 방식](./piece/2019-10-27_패스워드-저장.md)
- 2019-11-14 [개인서버 할당 후 기록물 : 코노하 VPS](./piece/2019-11-14_vps.md)
- 2019-11-22 [nginx 정리](./piece/2019-11-22_nginx-정리.md)
- 2019-11-27 [pip 설치](./piece/2019-11-27_pip-설치.md)
- 2020-11-24 [[ java ] classpath 의 json 파일 읽어들이기](./boilerplate/2020-11-24_reading-json-file-of-classpath.md)
- 2021-06-27 [eventloop](./piece/2021-06-27_eventloop.md)
- 2023-02-12 [emit 은 프로그래밍 관점에서 어떤 의미인지?](./personal/2023-02-12_emit.md)

### 실무와 환경

- 2020-09-10 [SPA vs MPA](./personal/2020-09-10_spa-vs-mpa.md)
- 2022-07-22 [실무를 일하면서...](./personal/2022-07-22_keep-in-mind.md)
- 2025-03-30 [MAC 세팅 : alias, iterm 등 단축키](./dummy/2025-03-30_macbook-setting.md)
- 2025-04-21 [혼자하는 개발회고, 2025년 상반기](./dummy/2025-04-21_2025-h1-reflection.md)

## 외부 참고

### Algorithm & Java

- [leetcode](./leetcode)
- [geeksforgeeks](https://github.com/pasudo123/Algorithms/tree/master/GeeksForGeeks)
- [etc repository](https://github.com/pasudo123/Algorithms)
- [abstract keyword](https://github.com/pasudo123/LearnJava/blob/master/JavaBasics/AbstractEx.java)
- [interface keyword](https://github.com/pasudo123/LearnJava/blob/master/JavaBasics/InterfaceEx.java)
- [Difference Between interface & abstract](https://github.com/pasudo123/LearnJava/blob/master/JavaBasics/BetweenInterfaceAbstract.java)
- [Stack Memory & Heap Memory](https://github.com/pasudo123/LearnJava/blob/master/AdvancedTopics/Memory.java)
- [Reflection](https://github.com/pasudo123/LearnJava/blob/master/AdvancedTopics/Reflection.java)
- [Reflection Sample Code](https://github.com/pasudo123/LearnJava/blob/master/AdvancedTopics/ReflectionSimple.java)
- [Difference Between ArrayList & Vector](https://github.com/pasudo123/LearnJava/blob/master/AdvancedTopics/ArrayListVector.java)
- [Difference Between Comparable & Comparator](https://github.com/pasudo123/LearnJava/blob/master/AdvancedTopics/CompareExercise.java)
- [Modern JAVA Stream API](https://github.com/pasudo123/LearnJava/blob/master/AdvancedTopics/StreamAPIExplain.java)
- [Modern JAVA Stream API sample code](https://github.com/pasudo123/LearnJava/blob/master/AdvancedTopics/StreamAPIExample.java)
- [Modern JAVA Interface Default Method](https://github.com/pasudo123/LearnJava/blob/master/JavaBasics/Java8InterfaceChanges01.java)
- [Modern JAVA Interface Static Method](https://github.com/pasudo123/LearnJava/blob/master/JavaBasics/Java8InterfaceChanges02.java)
- [Enum Type Example](https://github.com/pasudo123/LearnJava/blob/master/Enum/ThreadStatesEnum.java)
- [Enum Type Safefy Example via Class Type](https://github.com/pasudo123/LearnJava/blob/master/Enum/ThreadStatesConstant.java)
- [Thread related post](https://github.com/pasudo123/LearnJava/blob/master/README.md)

### Spring & Kotlin Repositories

- [springboot-kafka-basis](https://github.com/coding-buddha/springboot-kafka-basis)
- [springboot-querydsl-basis](https://github.com/coding-buddha/springboot-querydsl-basis)
- [springboot-web-basis](https://github.com/coding-buddha/springboot-web-basis)
- [springboot-test-basis](https://github.com/coding-buddha/springboot-test-basis)
- [springboot-batch-basis](https://github.com/coding-buddha/springboot-batch-basis)
- [springboot-cache-basis](https://github.com/coding-buddha/springboot-cache-basis)
- [springboot-docker-basis](https://github.com/coding-buddha/springboot-docker-basis)
- [springboot-event-basis](https://github.com/coding-buddha/springboot-event-basis)
- [springboot-slackbot-basis](https://github.com/coding-buddha/springboot-slackbot-basis)
- [springboot-cloud-basis](https://github.com/coding-buddha/springboot-cloud-basis)
- [kotlin-zero-to-all](https://github.com/coding-buddha/kotlin-zero-to-all)
- [kotlin-spring-zero-to-all](https://github.com/pasudo123/springboot-kotlin-zerotoall)
- [reactive-with-kopring](https://github.com/pasudo123/reactive-with-kopring)
- [coroutine 정리한 README](https://github.com/pasudo123/springboot-kotlin-zerotoall/blob/main/pure-kotlin/coroutine-readme.md)
- [reactive study, springboot with with](https://github.com/pasudo123/reactive-with-kopring)
- [oom, OutOfMemory 관련 gradle, jattatch 기록해놓은 레포](https://github.com/pasudo123/oom-zero)
- [querydsl projection 5가지 방법 작성글](https://pasudo123.tistory.com/431)

### Design Pattern & Books

- [초보개발자에게 권장하는 객체지향 모델링 공부방법](https://okky.kr/article/358197)
- [oop-basis : decorator-pattern](https://github.com/coding-buddha/oop-basis/blob/master/design-pattern-decorator)
- [oop-basis : adapter-pattern](https://github.com/coding-buddha/oop-basis/blob/master/design-pattern-adapter)
- [오브젝트](https://github.com/coding-buddha/object-by-kotlin/blob/main/README-object.md)
- [클린코드](https://github.com/pasudo123/mango-banana-clean-code)
- [클린아키텍처](https://github.com/pasudo123/newyear-clean-architecture)
- [ddd : 도메인 주도 설계 철저 입문](https://github.com/pasudo123/ddd)
- [객체지향 사고 프로세스](https://pasudo123.tistory.com/446)

### Books, Blogs, Tools

- [씨아이보드 메뉴얼 스키마 설계 샘플](http://www.ciboard.co.kr/manual/tables)
- [innodb locking read](https://pasudo123.tistory.com/505)
- [c4 modeling](https://pasudo123.tistory.com/469)
- [virtualization(가상화) : blog](https://pasudo123.tistory.com/455)
- [hypervisors(하이퍼바이저) : blog](https://pasudo123.tistory.com/454)
- [스프링 가이드 : cheese10yun](https://github.com/cheese10yun/spring-guide)
- [스프링 JPA 가이드 : cheese10yun](https://github.com/cheese10yun/spring-jpa-best-practices)
- [롬복 애노테이션 사용 전략 : cheese10yun](https://www.popit.kr/실무에서-lombok-사용법/)
- [ngrok : 로컬개발환경을 인터넷으로 공유해주는 툴](https://www.lesstif.com/software-architect/ngrok-39126236.html)
- [crontab 표현식](https://crontab.guru/)
- [caffeinate](https://pasudo123.tistory.com/497)
- [[외부링크] vscode 로 json 정렬하기](https://gigi.nullneuron.net/gigilabs/formatting-json-in-visual-studio-code/)
