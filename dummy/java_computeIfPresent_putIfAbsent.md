## computeIfPresent & putIfAbsent 간단.
- 함수형 인터페이스를 인자로 받는다. 결국 해당 메소드 이용 시 람다 표현형태로 작성할 수 있다.

## Code
구성코드
```kotlin
class MapTest {
    @Test
    fun `compute, put 메소드 확인`() {
        val group = mutableMapOf<String, MutableList<Int>>()

        val words = listOf("hello", "hello", "hi", "orange", "banana", "orange")

        words.forEach { word ->
            group.computeIfPresent(word) { _, value ->
                val nextNumber = value.size + 1
                value.add(nextNumber)
                value
            }

            group.putIfAbsent(word, mutableListOf(1))
        }

        println(group)
    }
}
```

결과
```shell
{hello=[1, 2], hi=[1], orange=[1, 2], banana=[1]}
```