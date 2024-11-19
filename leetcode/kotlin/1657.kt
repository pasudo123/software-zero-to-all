/**
 * word1, word2 가 operation1, operation2 에 의해 동일하게 표시할 수 있는지.
 * 맵을 활용헀다. 길이가 우선 다르면 동일하게 표시할 수 없음.
 * 문자 카운트가 다르더라도, 카운트 다른 값들 자체가 동일해야함. 32, 33 번째줄.
 *
**/
class Solution {
    fun closeStrings(word1: String, word2: String): Boolean {
        if (word1.count() != word2.count()) return false

        val word1Map = mutableMapOf<Char, Int>()
        val word2Map = mutableMapOf<Char, Int>()

        word1.forEach { word ->
            word1Map.computeIfPresent(word) { _, value -> value + 1 }
            word1Map.putIfAbsent(word, 1)
        }

        word2.forEach { word ->
            word2Map.computeIfPresent(word) { _, value -> value + 1 }
            word2Map.putIfAbsent(word, 1)
        }
        
        word1Map.entries.forEach { entry ->
            if (word2Map.contains(entry.key).not()) return false
        }

        word2Map.entries.forEach { entry ->
            if (word1Map.contains(entry.key).not()) return false
        }

        val sorted1 = word1Map.values.sorted()
        val sorted2 = word2Map.values.sorted()

        sorted1.forEachIndexed { index, _ ->
            if (sorted1[index] != sorted2[index]) return false
        }

        return true
    }
}
