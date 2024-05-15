# lazy evaluation vs eager evaluation
- lazy evaluation, 지연연산이라고 한다.
    - 연산의 결과를 즉시 계산하지 않고, 최종연산이 호출되는 시점에 실제 연산을 수행한다.
    - 최종연산은 sum(), toList(), toSet() 등이 있다.
    - kotlin 의 Sequence, java 의 Stream 이 그렇다.

<BR>

# sequence 또는 collection 을 사용해야 하는 경우
## 중간연산이 많은 케이스 (이 외에 상태가 없는 연산도 시퀀스 사용이 권장된다.) 는 시퀀스가 빠르다.
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

<BR>

## 사이즈가 조정되거나 최종연산으로 toSet(), toList() 로 반환하는 케이스는 컬렉션이 빠르다. ?
- 참고자료의 내용을 보고 직접 처리해보았다. 아래처럼 코드를 작성했음에도 컬렉션 속도가 항상 빠른 케이스는 아닌걸로 보인다.
- 벤치마크를 하지 않았지만, 단순 속도측정을 했을때는 속도성능은 시퀀스와 컬렉션이 서로 엇비슷하다.
    - 컬렉션이 조금 느리지만 시퀀스로 변경해서 처리할만큼의 효과는 없어보인다.
- 결국 `(1..10000000).map` 로 처리하는 갯수가 더 많아져도 비슷할걸로 보인다.

```kotlin
class SequenceTesterTest {

    @RepeatedTest(5)
    fun `사이즈가 변경되는 부분에 따른 sequence vs collection 비교`() {
        val coffees = (1..10000000).map { Coffee("old-coffee-$it", it) }

        val elapsedTimeWithCollection = measureTimeMillis {
            val one = coffees
                .filter { it.sequence % 3 == 0 }
                .map { it.copy(name = "new-coffee-${it.sequence}", sequence = it.sequence) }
                .sortedBy { it.sequence }
        }
        println("use collection, elapsedTime= $elapsedTimeWithCollection milliSec")

        val elapsedTimeWithSequence = measureTimeMillis {
            val one = coffees
                .asSequence() // asSequence() 처리.
                .filter { it.sequence % 3 == 0 }
                .map { it.copy(name = "new-coffee-${it.sequence}", sequence = it.sequence) }
                .sortedBy { it.sequence }
                .toList() // 다시 toList() 를 만들어버리기 때문에.
        }
        println("use sequence, elapsedTime= $elapsedTimeWithSequence milliSec")

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
============================================================
use collection, elapsedTime= 273 milliSec
use sequence, elapsedTime= 201 milliSec

============================================================

use collection, elapsedTime= 340 milliSec
use sequence, elapsedTime= 223 milliSec

============================================================

use collection, elapsedTime= 292 milliSec
use sequence, elapsedTime= 209 milliSec

============================================================

use collection, elapsedTime= 318 milliSec
use sequence, elapsedTime= 216 milliSec

============================================================

use collection, elapsedTime= 227 milliSec
use sequence, elapsedTime= 256 milliSec

============================================================
```

<BR>

## 결론
- 자세한 내용은 참고링크에 더 많다.
    - 만일 Sequence -> List -> toList() 형태의 최종연산을 하는 경우에는 시퀀스보단 컬렉션을 쓰는걸 권장한다.

<BR>

## 참고링크
- https://typealias.com/guides/when-to-use-sequences/
- https://typealias.com/guides/kotlin-sequences-illustrated-guide/
- https://typealias.com/guides/inside-kotlin-sequences/