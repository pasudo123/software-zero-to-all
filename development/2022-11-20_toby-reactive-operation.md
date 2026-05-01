## reactive operation
코틀린 기반으로 토비가 알려주는 리액티브를 공부한다.

## pub/sub 의 사용
pub/sub 의 단순사용
```kotlin
fun main() {
    val startPub = startPub()
    startPub.subscribe(endSub())
}

fun startPub(): Publisher<Int> {
    return object: Publisher<Int> {

        val sequence = (1..10).map { it }

        override fun subscribe(s: Subscriber<in Int>?) {
            println("subscribe")

            s?.onSubscribe(object: Subscription {
                override fun request(n: Long) {
                    println("request : $n")
                    try {
                        sequence.forEach { currentSeq -> s.onNext(currentSeq) }
                        s.onComplete()
                    } catch (exception: Exception) {
                        s.onError(exception)
                    }
                }

                override fun cancel() {
                    println("cancel")
                }
            })
        }
    }
}

fun endSub(): Subscriber<Int> {
    return object: Subscriber<Int> {
        override fun onSubscribe(s: Subscription?) {
            println("onSubscribe")
            s?.request(Long.MAX_VALUE)
        }

        override fun onError(t: Throwable?) {
            println("onError")
        }

        override fun onComplete() {
            println("onComplete")
        }

        override fun onNext(t: Int?) {
            println("onNext : $t")
        }
    }
}
```

## pub/sub 사이에 operation 을 하나 추가한다.
```kotlin
fun main() {
    val startPub = startPub()
    val middlePub = middlePub(startPub)
    middlePub.subscribe(endSub())
}

fun middlePub(publisher: Publisher<Int>): Publisher<Int> {

    return Publisher<Int> { subscriber ->
        publisher.subscribe(object: Subscriber<Int> {
            override fun onSubscribe(s: Subscription?) {
                subscriber.onSubscribe(s)
            }

            override fun onError(t: Throwable?) {
                subscriber.onError(t)
            }

            override fun onComplete() {
                subscriber.onComplete()
            }

            override fun onNext(t: Int?) {
                subscriber.onNext(t!! * 10)
            }
        })
    }
}
```