# lazy evaluation vs eager evaluation
- lazy evaluation, 지연연산이라고 한다.
    - 연산의 결과를 즉시 계산하지 않고, 최종연산이 호출되는 시점에 실제 연산을 수행한다.
    - 최종연산은 sum(), toList(), toSet() 등이 있다.
    - kotlin 의 Sequence, java 의 Stream 이 그렇다.

# 지연연산을 사용해야 하는 경우
## 중간연산이 많은 케이스 (이 외에 상태가 없는 연산도 시퀀스 사용이 권장된다.)
- 아래와 같은 코드를 수행하면 .asSequence() 가 적용된 부분의 속도가 더 빠른 것을 확인할 수 있다.
- 중간단계의 연산은 toSet() 이 호출되는 시점에 filter, map, take 순으로 3번의 연산으로 마무리된다.
- 결국 `(1..10000000).map` 로 처리하는 갯수가 더 많아질수록 시퀀스와 컬렉션간의 메모리 및 속도 성능이 더 벌어진다.

```kotlin
class SequenceTesterTest {

    @RepeatedTest(5)
    fun `operation count 에 따른 sequence vs collection 비교`() {
        val coffees = (1..10000000).map { Coffee("old-coffee-$it", it) }

        val elapsedTimeWithCollection = measureTimeMillis {
            val one = coffees
                .filter { it.sequence % 3 == 0 }
                .map { it.copy("new-coffee-${it.sequence}", it.sequence) }
                .take(1)
                .toSet()

            println(one)
        }
        println("use collection, elapsedTime= ${elapsedTimeWithCollection} milliSec")

        val elapsedTimeWithSequence = measureTimeMillis {
            val one = coffees
                .asSequence() // asSequence() 처리.
                .filter { it.sequence % 3 == 0 }
                .map { it.copy("new-coffee-${it.sequence}", it.sequence) }
                .take(1)
                .toSet()

            println(one)
        }
        println("use sequence, elapsedTime= ${elapsedTimeWithSequence} milliSec")

        println("\n============================================================\n")
    }
}

data class Coffee(
    val name: String,
    val sequence: Int
)
```

결과값
```shell
[Coffee(name=new-coffee-3, sequence=3)]
use collection, elapsedTime= 211 milliSec
[Coffee(name=new-coffee-3, sequence=3)]
use sequence, elapsedTime= 3 milliSec

============================================================

[Coffee(name=new-coffee-3, sequence=3)]
use collection, elapsedTime= 311 milliSec
[Coffee(name=new-coffee-3, sequence=3)]
use sequence, elapsedTime= 0 milliSec

============================================================

[Coffee(name=new-coffee-3, sequence=3)]
use collection, elapsedTime= 263 milliSec
[Coffee(name=new-coffee-3, sequence=3)]
use sequence, elapsedTime= 1 milliSec

============================================================

[Coffee(name=new-coffee-3, sequence=3)]
use collection, elapsedTime= 293 milliSec
[Coffee(name=new-coffee-3, sequence=3)]
use sequence, elapsedTime= 0 milliSec

============================================================

[Coffee(name=new-coffee-3, sequence=3)]
use collection, elapsedTime= 280 milliSec
[Coffee(name=new-coffee-3, sequence=3)]
use sequence, elapsedTime= 0 milliSec

============================================================
```

## 결론
- 자세한 내용은 참고링크에 더 많다.
    - 만일 Sequence -> List -> toList() 형태의 최종연산을 하는 경우에는 시퀀스보단 컬렉션을 쓰는걸 권장한다.

## 참고링크
- https://typealias.com/guides/when-to-use-sequences/
- https://typealias.com/guides/kotlin-sequences-illustrated-guide/
- https://typealias.com/guides/inside-kotlin-sequences/