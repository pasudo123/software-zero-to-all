## coroutine 의 coroutinScope & supervisorScope 의 에러 핸들링
두 scopeBuilder 는 모두 multiple concurrent operation 을 내부 코드블럭에서 작성할 수 있게 해준다.

### coroutineScope
* 다수 개의 concurrent operation 이 동작할 때, 하나의 child coroutine 이 실패하면 다른 child coroutine 까지 모두 실패한다. 전체 try/catch 로 감싸야 한다

### corotuineScope 코드 (동작 X)
* 한 개의 async block 에서 익셉션이 떨어지면 해당 코루틴 블록 모두 실패처리 된다. 아래으 코드는 동작하지 않는다.
```kotlin
@Service
class FrontServerService(
    private val backServerClient: BackServerClient
) {

    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun getCoffeeById(id: Long): Coffee = coroutineScope {
        val response = backServerClient.getCoffeeById(id).awaitResponse()

        if (response.isSuccessful) {
            return@coroutineScope response.body()!!
        }

        throw FrontServerException("[empty block] 커피 한 개 호출 실패 : coffee[$id] (${response.errorBody()?.toString() ?: "unknown"})")
    }

    suspend fun getCoffeesBySizeWithAsyncError(size: Long): Flow<Coffee> = runBlocking {
        return@runBlocking coroutineScope {

            val deferredChild1th = async(Dispatchers.IO) {
                getCoffeeById(100L)
            }
            val deferredChild2th = async(Dispatchers.IO) {
                getCoffeeById(200L)
                throw FrontServerException("커피획득 에러 발생")
            }

            val coffees = mutableListOf<Coffee>()

            try {
                coffees.add(deferredChild1th.await())
            } catch (exception: Exception) {
                log.error("error child1th : ${exception.message}")
            }

            try {
                coffees.add(deferredChild2th.await())
            } catch (exception: Exception) {
                log.error("error child2th : ${exception.message}")
            }

            return@coroutineScope coffees.asFlow()
        }
    }
}
```

### coroutineScope 코드 (동작 O)
* coroutineScope 는 에러를 핸들링하기 위해선 전체 coroutineScope {} 에 try/catch 로 감싸야한다.

```kotlin
suspend fun getCoffeesBySizeWithAsyncError(size: Long): Flow<Coffee> = runBlocking {
        try {
            return@runBlocking coroutineScope {

                val deferredChild1th = async(Dispatchers.IO) {
                    getCoffeeById(100L)
                }
                val deferredChild2th = async(Dispatchers.IO) {
                    getCoffeeById(200L)
                    throw FrontServerException("커피획득 에러 발생")
                }

                val coffees = mutableListOf<Coffee>()

                try {
                    coffees.add(deferredChild1th.await())
                } catch (exception: Exception) {
                    log.error("error child1th : ${exception.message}")
                }

                try {
                    coffees.add(deferredChild2th.await())
                } catch (exception: Exception) {
                    log.error("error child2th : ${exception.message}")
                }

                return@coroutineScope coffees.asFlow()
            }
        } catch (exception: Exception) {
            log.error("parent 에러 : ${exception.message}")
            return@runBlocking  emptyFlow<Coffee>()
        }
    }
```

### supervisorScope
* 다수 개의 concurrent operation 이 동작할 때, 개별로 child coroutine 결과에 대해 에러핸들링이 가능하다.
* parent coroutine 의 에러에 대해선 child coroutine 의 결과에 상관없이 익셉션이 발생한다.

### supervisorScope 코드
* async block 으로 child coroutine 이 두 개가 있고, 하나가 에러가 떨어져도 개별로 try/catch 블럭을 통해서 처리가능하다.
* 그 상위 코루틴 블럭, supervisorScope 에 대해선 크게 try/catch 블럭으로 감싸주어야 한다.

```kotlin
@Service
class FrontServerService(
    private val backServerClient: BackServerClient
) {

    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun getCoffeeById(id: Long): Coffee = coroutineScope {
        val response = backServerClient.getCoffeeById(id).awaitResponse()

        if (response.isSuccessful) {
            return@coroutineScope response.body()!!
        }

        throw FrontServerException("[empty block] 커피 한 개 호출 실패 : coffee[$id] (${response.errorBody()?.toString() ?: "unknown"})")
    }

    suspend fun getCoffeesBySizeWithAsyncError(size: Long): Flow<Coffee> = runBlocking {
        return@runBlocking supervisorScope {

            val deferredChild1th = async(Dispatchers.IO) {
                getCoffeeById(100L)
            }
            val deferredChild2th = async(Dispatchers.IO) {
                getCoffeeById(200L)
                throw FrontServerException("커피획득 에러 발생")
            }

            val coffees = mutableListOf<Coffee>()

            try {
                coffees.add(deferredChild1th.await())
            } catch (exception: Exception) {
                log.error("error child1th : ${exception.message}")
            }

            try {
                coffees.add(deferredChild2th.await())
            } catch (exception: Exception) {
                log.error("error child2th : ${exception.message}")
            }

            return@supervisorScope coffees.asFlow()
        }
    }
}
```

### 참고사항
* 위 코드에서 backServerClient 는 동일하게 로컬호스터로 띄어놓은 rest api 형태의 단순한 was 이다.