# kotlin mutex & semaphore

## mutex
뮤텍스. 멀티스레딩 or 멀티프로세스 환경에서 공유자원에 접근할 때, 데이터 일관성을 유지하고 안전한 동시성 처리를 가능하게 고안된 기법 중 하나.
- Mutual Exclusion (상호배제)
  - 상호배제, 동시에 여러 스레드가 같은 공유자원에 접근하지 못하도록 하는 것을 의미
  - 하나의 스레드만 임계영역(Critical Section) 에 들어가서 그 자원을 안전하게 접근하고 해당 스레드가 자원을 사용하면 그 다음 스레드가 자원에 접근한다.
- Critical Section (임계영역)
  - 여러 스레드가 공통적으로 접근/수정하는 코드영역을 의미
  - 뮤텍스는 이런 임계영역의 진입을 관리하는 도구다.
- 소유권 개념이 존재
  - 하나의 프로세스 또는 스레드만 접근.


## semaphore
세마포어. 뮤텍스와 비슷하나 Critical Section 에 접근할 수 있는 프로세스 or 스레드의 수를 제한하여 자원경합을 안전하게 처리. 


## 공유자원에 동기화가 되지 않는 코틀린 코드. 
runBlocking 을 통해서 동시에 incr() 메소드를 수행하는 메인함수.   
println() 을 통해 결과값을 확인하고 동일한 행위를 repeat(N) 만큼 반복합니다.
```kotlin
var counter = 0

private suspend fun incr() {
    val milli = Random.nextLong(2)
    delay(milli)
    counter++
}

fun main() = runBlocking {
    repeat(5) {

        val jobs = (1..1000).map {
            launch(Dispatchers.IO) { incr() }
        }

        jobs.joinAll()
        println("[$it] counter=$counter")
    }
}
```
   
결과에서는 5번 반복했고, 1000까지 counter 값이 처리되지 않습니다.
race condition 이 발생되었음을 확인할 수 있습니다.
```shell
[0] counter=979
[1] counter=977
[2] counter=971
[3] counter=961
[4] counter=970
```

## mutex 적용 코틀린 코드
mutex 를 만들어서 withLock{} 람다를 사용했습니다.   
이렇게 하면 lock, unlock 처리가 내부적으로 구현됩니다.
```kotlin
var counter = 0
val mutex = Mutex()

private suspend fun incrWithLock() {
    val milli = Random.nextLong(2)
    delay(milli)
    mutex.withLock { counter++ }
}

fun main() = runBlocking {
    repeat(5) {

        val jobs = (1..1000).map {
            launch(Dispatchers.IO) { incrWithLock() }
        }

        jobs.joinAll()
        println("[$it] counter=$counter")
    }
}
```
   
결과는 임계영역에 접근 시 공유자원 동기화가 되어서 1000으로 출력됩니다.
```shell
[0] counter=1000
[1] counter=1000
[2] counter=1000
[3] counter=1000
[4] counter=1000
```

## CAS(Compare And Set) 를 이용. 코틀린 코드
AtomicInteger 를 사용해서 락 없이도 동시성 제어를 가능하게 합니다.   
CAS 연산은 CPU 가 제공하는 기계어 명령어 (원자적 CPU 인스트럭션) 를 통해 구현됩니다.   
명령어는 메모리에 있는 값을 읽고 조건을 비교합니다. 조건이 충족되면 값을 갱신하는 과정이고 이는 Atomic 한 연산을 뜻합니다. (출저, chatGPT) 
```kotlin
val counterAtomic = AtomicInteger(0)

private suspend fun incrWithAtomic() {
    val milli = Random.nextLong(2)
    delay(milli)
    counterAtomic.incrementAndGet()
}

fun main() = runBlocking {
    repeat(5) {

        val jobs = (1..1000).map {
            launch(Dispatchers.IO) { incrWithAtomic() }
        }

        jobs.joinAll()
        println("[$it] counter=${counterAtomic.get()}")
    }
}
```
   
결과는 mutex 사용과 마찬가지로 1000으로 출력됩니다.
```shell
[0] counter=1000
[1] counter=1000
[2] counter=1000
[3] counter=1000
[4] counter=1000
```

## semaphore 사용. 코틀린 코드 (1)
Semaphore 를 생성할 시에 몇개의 스레드를 허용할지 갯수를 설정할 수 있습니다.   
아래 코드에선 2개의 스레드를 허용하고 있으며, job_1, job_2 만 임계영역에 들어올 수 있습니다.   
나머지 스레드들은 대기하는 상태로, 먼저 들어간 작업이 끝나면 이후에 작업을 수행합니다.   
```kotlin
val semaphore = Semaphore(2)

private suspend fun useSemaphore(name: String) {

    semaphore.acquire()
    try {
        println("enter-$name")
        delay(3000)
        println("exit-$name")
    } finally {
        semaphore.release()
    }
}

fun main() = runBlocking {
    val jobs = (1..10).map {
        launch { useSemaphore("job_$it") }
    }

    jobs.joinAll()
}
```
   
결과는 enter 과 exit 과 2개씩 끊어져서 처리됩니다.   
```shell
enter-job_1
enter-job_2
exit-job_1
exit-job_2
enter-job_3
enter-job_4
exit-job_3
exit-job_4
enter-job_5
enter-job_6
exit-job_5
exit-job_6
enter-job_7
enter-job_8
exit-job_7
exit-job_8
enter-job_9
enter-job_10
exit-job_9
exit-job_10
```

## semaphore 사용. 코틀린 코드 (2)
withPermit{} 은 Semaphore의 permit을 안전하게 acquire/release 할 수 있도록 도와주는 확장함수입니다.
내부적으로 acquire() 와 release() 를 포함하고 있어, 블록 안에서 실행되는 코드가 항상 permit 범위 내에서 실행되도록 보장합니다.
아래 예제는 동시에 최대 2개의 apiCall() 만 실행되도록 제어합니다. 결과는 acquire() & release() 사용코드와 동일합니다.

```kotlin
val semaphore = Semaphore(2)

private suspend fun apiCall(name: String) {
    println("enter-$name")
    delay(3000)
    println("exit-$name")
}

fun main() = runBlocking {
    val jobs = (1..10).map {
        launch { semaphore.withPermit { apiCall("job_$it") } }
    }

    jobs.joinAll()
}
```