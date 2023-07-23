## springboot @Async 의 ThreadPoolTaskExecutor 설정
* @Aysnc 애노테이션 사용 시, ThreadPoolTaskExecutor 를 설정할 수 있다.
```kotlin
@Configuration
@EnableAsync
class CustomAsyncConfiguration: AsyncConfigurer {

    override fun getAsyncExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            this.setThreadNamePrefix("pasudoAsync-")
            this.corePoolSize = 1
            this.maxPoolSize = 1
            this.setQueueCapacity(1)
            this.initialize()
        }
    }
}
```
* corePoolSize 는 @Async 로 동작할 때 기본적으로 주는 스레드풀 내 갯수
* maxPoolSize 는 작업 Task 가 queueCapacity 를 초과하였을 떄 최대치만큼 생성할 수 있는 스레드풀 내 갯수
    * queueCapacity < taskCount 면 poolSize 를 1개 더 늘린다.
* maxPoolSize 초과 시, RejectedExecutionException 이 발생한다.


## 5초간 스케줄링하는 작업이 6초가 소요되는 Task 예시
* corePoolSize=1, maxPoolSize=1, queueCapacity=1 로 세팅
* 0초 : Task1 coreSize=0 -> coreSize=1 로 처리
* 5초 : queue=[Task2]
* 6초 : Task1 작업종료(coreSize=0), Task2 coreSize=0 -> coreSize=1, queue=[]
* 10초 : queue=[Task3]
* 12초 : Task2 작업종료(coreSize=0), Task3 coreSize=0 -> coreSize=1, queue=[]
* 15초 : queue=[Task4]
* 18초 : Task3 작업종료(coreSize=0), Task4 coreSize=0 -> coreSize=1, queue=[]
* 20초 : queue=[Task5]
* 24초 : Task4 작업종료(coreSize=0), Task5 coreSize=0 -> coreSize=1, queue=[]
* 25초 : queue=[Task6]
* `30초` : Task5 작업종료(coreSize=0), Task6 coreSize=0 -> coreSize=1, queue=[]
* `30초` : queue=[Task7]
* 35초 : queue=[Task7, Task8] 
    * queueCapacity=1 이기 때문에 maxPoolSize 초과 시 RejectedExecutionException 이 발생한다.
    * @Async 애노테이션 내부에서 발생하는데 아닌 외부에서 @Async 애노테이션이 붙은 메소드 호출 시 에러 발생

```shell
Task java.util.concurrent.FutureTask@69a3e3c[Not completed, task = org.springframework.aop.interceptor.AsyncExecutionInterceptor$$Lambda$880/0x00000008006e5c40@61f81d82] rejected from java.util.concurrent.ThreadPoolExecutor@34028e93[Running, pool size = 1, active threads = 1, queued tasks = 1, completed tasks = 0]
```


## 참고
* https://docs.spring.io/spring-framework/reference/integration/scheduling.html#scheduling-task-namespace-executor