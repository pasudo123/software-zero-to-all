# 코틀린 코루틴
책 읽고, 실습 및 내용 요약. + chatGPT 이용.

## 6장. 코루틴 빌더
- suspend function() 을 사용하기 위해선 코루틴 빌더 또는 코루틴 스코프 함수가 필요하다.
  - (1) runBlocking, launch, async (코루틴 빌더)
  - (2) coroutineScope (코루틴 스코프 함수)
- 일반함수가 suspend function 을 호출할 수 없다. suspend function 을 호출할 수 있는 지점이 코루틴 빌더다. 

```kotlin
fun main() {
    runBlocking {
        coroutineScopeFunc()
        runBlockingFunc()
    }
}

// 코루틴 빌더를 사용하기 위해서, coroutineScope 로 wrapping
suspend fun coroutineScopeFunc() = coroutineScope {
    launch { println("hello") }
}

// 코루틴 빌더를 사용하기 위해서, runBlocking 로 wrapping
suspend fun runBlockingFunc() = runBlocking {
    launch { println("world") }
}

/**
 * 코루틴 빌더를 사용하기 위해선, 코루틴 스코프 혹은 runBlocking 이 필요하다.
 * 없다면 컴파일 에러가 발생한다. (아래 코드는 컴파일 에러가 발생하는 코드이다.)
 */
//suspend fun basicFunc() {
//    launch {  } -> 코루틴 빌더(launch or await) 를 사용할 수 없다. 컴파일 에러 발생.
//}
```

## 8장. Job 과 자식 코루틴 기다리기
- launch 코루틴빌더의 결과값으로 Job 인터페이스가 반환됨.
    - async 코루틴빌더의 결과값 Deffered 는 비동기 호출의 결과를 반환. launch 는 결과반환값이 미존재.Job 만 반환.
- Job 인터페이스에는 상태값이 다양함 (New, Active, Completed, ...)
- Job 의 결과를 기다릴 수 있음. 결과를 기다리지 않는다면 다음 로직을 수행함 
    - 단일 잡의 경우 join()
    - 멀티 잡의 경우 joinAll() 

### 특정함수를 수행하고, joinAll() 을 통해서 결과를 기다림
```kotlin
fun main() = runBlocking(Dispatchers.IO) {
    val files = listOf("file1", "file2", "file3", "file4", "file5")

    val jobs = files.map { file ->
        launch { processFile(file) }
    }

    jobs.forEach { job ->
        // 출력내용. StandaloneCoroutine{Active}@6e51066e
        println(job)
    }

    // joinAll() 존재여부에 따라서, processFile() 메소드 기다릴지 여부가 결정된다.
    jobs.joinAll()

    jobs.forEach { job ->
        // 출력내용. StandaloneCoroutine{Completed}@24711d71
        println(job)
    }

    // joinAll() 을 통해서 가장 마지막에 출력된다.
    println("done")
}

suspend fun processFile(file: String) {
    println("process file=$file ...")
    delay(2000)

    println("process file=$file done")
}
```

## 9장. 취소
- launch {} 를 이용시 취소, cancel() 을 지원한다.
- cancel() 만 쓰면 race condition 이 발생할 수 있다. cancel() 을 사용한다고 바로 취소되는 것은 아니다. 취소 가능한 시점에서 CancellationException 을 발생시키면서 종료된다.
  - 코루틴은 취소될 때까지 시간이 소요된다.
  - join() 을 사용하여 현재 스레드가 종료될때까지 스레드를 일시중단 한다.
  - cancel() + join() 을 합친 cancelAndJoin() 을 사용한다.
- invokeOnCompletion {} 을 통해서 Completed, Cancelled 같은 마지막 상태 도달 시 호출 핸들러를 지정할 수 있다.

```kotlin
fun main() = runBlocking(Dispatchers.IO) {
  val job = launch { processFileWithRepeat("sample.txt") }
  job.invokeOnCompletion { throwable ->
    if (throwable != null) {
      println("## 코루틴 수행 실패. throwable=${throwable.message}")
    } else {
      println("## 코루틴 수행 성공.")
    }
  }
  delay(1000)
//    job.cancel()
//    job.cancelAndJoin()
  job.join()

  println("done")
}

suspend fun processFileWithRepeat(file: String) {
  try {
    repeat(15) {
      println("$it process file=$file ...")
      delay(100)
      Thread.sleep(100)
      println("$it process file=$file done")
    }
  } catch (exception: CancellationException) {
    println("## cancellationException message=${exception.message}")
  } catch (exception: Exception) {
    println("## exception message=${exception.message}")
  } finally {
    println("## finally")
  }
}
```

## 11장. 코루틴 스코프 함수
- SupervisorJob 을 이용하여 신규 CoroutineScope 를 만들 수 있다.
  - SupervisorJob 내부에서 에러가 발생하더라도 부모 코루틴 스코프로 에러가 전파되지 않는다.
  - 복잡한 코루틴 구조를 관리할 수 있으며 독립적으로 실행 시에 유리하다.
  - 하나의 코루틴이 실패해도 다른 코루틴이 계속 실행되도록 보장한다.
- SupervisorJob + Dispatchers.IO 와 결합하여 사용도 가능하다.

```kotlin
/**
 * 에러가 부모 코루틴까지 전파되지 않는다.
 */
fun printHelloWorldWithSupervisorScope() = runBlocking {
    supervisorScope {
        launch {
            // supervisorScope 로 감싼 해당 {} 만 에러가 발생하고, 나머지는 영향을 끼치지 않음
            throw RuntimeException("Hello Error")
            delay(500)
            println("Hello")
        }
        launch {
            delay(100)
            println("World")
        }
    }

    launch {
        println("Done")
    }
}

val personalCoroutineScope = CoroutineScope(context = SupervisorJob())
val personalCoroutineScopeIO = CoroutineScope(context = SupervisorJob() + Dispatchers.IO)

suspend fun printHelloWorldWithSupervisorScope2() = coroutineScope {

    delay(500)
    println("Do something...")

    // personalCoroutineScope.launch { notifyToUser() }
    // personalCoroutineScopeIO.launch { notifyToUser() }
    CoroutineScope(Dispatchers.IO).launch {
        // 에러가 부모코루틴으로 전파된다.
        notifyToUser()
    }

    println("Done")
}

// 별도 작업으로 처리할 수 있다.
suspend fun notifyToUser() {
    delay(3000)
    println("Hello World")
}

fun main() = runBlocking {
    printHelloWorldWithSupervisorScope2()
}
```

## 12장. 디스패처
> - 코틀린 코루틴 라이브러리가 제공하는 중요한 기능은 코루틴이 실행되어야할 (시작 or 재개) 스레드 또는 스레드풀을 결정할 있다는 것. <BR>
> - Dispatchers.Default 는 1core 에 1개 스레드까지만 허용. (`CPU 직얍적인 연산을 수행하기 위함`) <BR>
> - Dispatchers.IO 는 1core 에 최대 64개 스레드까지 확장가능. (`I/O 작업이 많음, 대기시간이 있음`) 8core 면 512개 스레드까지 확장가능 (8 * 64) <BR>

### Executor 와 asCoroutineDispatchers() 를 이용
- 직접 스레드풀을 제한할 수 있다. (스레드 풀의 크기를 제한한다.)
- `close()` 는 반드시 하여야 한다. 스레드 누수가 발생하고, 아래코드에서 미존재시 main() 함수가 종료되지 않는다.
- try {} finally {} 말고도 executorDispatchersIO.use {} 블럭을 통해 심플하게 사용할 수 있다.
```kotlin
fun main() = runBlocking {
    
    // 스레드 3개인 스레드풀 생성. 거기에 따른 launch {} 처리
    val executorDispatchersIO = Executors.newFixedThreadPool(3).asCoroutineDispatcher()

    try {
      val jobs = (1..50).map { i ->
        launch(executorDispatchersIO) {
          println("thread=${Thread.currentThread().name}, current=$i, time=${System.currentTimeMillis()}")
          Thread.sleep(100)
        }
      }
      jobs.joinAll()
    } finally {
      executorDispatchersIO.close()
    }
    println("end...")
}
```

출력 결과값.
- pool-1-thread-[1::3] 으로 출력되는 것을 확인할 수 있다.
```shell
thread=pool-1-thread-1, current=1, time=1728805953246
thread=pool-1-thread-3, current=3, time=1728805953247
thread=pool-1-thread-2, current=2, time=1728805953247
// 생략...
thread=pool-1-thread-1, current=47, time=1728805954805
thread=pool-1-thread-3, current=48, time=1728805954808
thread=pool-1-thread-2, current=49, time=1728805954905
thread=pool-1-thread-1, current=50, time=1728805954906
end...
```

### 커스텀 스레드풀을 사용
- kotlin 1.6 부터 도입된 limitedParallelism(). 아직까지 실험적 단계의 API 이기 때문에 사용 시에 `@OptIn(ExperimentalCoroutinesApi::class)` 가 붙는다.
- 독립적인 스레드풀을 가진 새로운 디스패처를 만들어낸다. (-> 기존 스레드풀과 격리된 환경에서의 병렬성을 제공)
```kotlin
@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
    val customDispatchersIO = Dispatchers.IO.limitedParallelism(3)
    val jobs = (1..50).map { i ->
        launch(customDispatchersIO) {
            println("thread=${Thread.currentThread().name}, current=$i, time=${System.currentTimeMillis()}")
            Thread.sleep(100)
        }
    }
    jobs.joinAll()
    println("end...")
}
```

출력 결과값.
- DefaultDispatcher-worker-[1::3] 으로 출력되는 것을 확인할 수 있다.
```shell
thread=DefaultDispatcher-worker-3, current=2, time=1728806292730
thread=DefaultDispatcher-worker-2, current=3, time=1728806292730
thread=DefaultDispatcher-worker-1, current=1, time=1728806292728
// 생략...
thread=DefaultDispatcher-worker-1, current=48, time=1728806294307
thread=DefaultDispatcher-worker-2, current=49, time=1728806294396
thread=DefaultDispatcher-worker-1, current=50, time=1728806294410
end...
```

