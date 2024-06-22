# 코틀린 코루틴
책 읽고, 실습 및 내용 요약.

## 8장. Job 과 자식 코루틴 기다리기
- launch 코루틴빌더의 결과값으로 Job 인터페이스가 반환됨.
    - async 코루틴빌더의 결과값 Deffered 는 비동기 호출의 결과를 반환. launch 는 결과반환값이 미존재.Job 만 반환.
- Job 인터페이스에는 상태값이 다양함 (New, Active, Completed, ...)
- Job 의 결과를 기다릴 수 있음. 결과를 기다리지 않는다면 다음 로직을 수행함 
    - 단일 잡의 경우 join()
    - 멀티 잡의 경우 joinAll() 

### 특정함수를 수행하고, joinAll() 을 통해서 결과를 기다림
```
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