/**
 * arr 의 모든 수들은 중복될 수 있다. 그리고 중복된 수의 갯수들은 서로 유니크하다.
 * [1, 1, 3, 2, 2, 2] 로 가정하면 
 * 1은 2회 반복, 2는 3회 반복, 3은 1회 반복. 결과적으로 2회/3회/1회 반복으로 모두 유니크하다.
 * map 을 이용하여 각 숫자별 count 를 처리하고 그 count 를 리스트화해서 중복제거 시, key.size 와 동일한지 여부를 판단한다.
 *
**/
class Solution {
    fun uniqueOccurrences(arr: IntArray): Boolean {
        val uniqueMap = mutableMapOf<Int, Int>()

        arr.forEachIndexed { index, element ->
            uniqueMap.computeIfPresent(element) { _, value -> value + 1 }
            uniqueMap.putIfAbsent(element, 1)
        }   

        val keyCount = uniqueMap.keys.size
        val valueCount = uniqueMap.values.distinct().size

        return (keyCount == valueCount)
    }
}
