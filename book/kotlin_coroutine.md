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