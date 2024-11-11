/**
 * grid[row][col] 을 하나씩 비교하는 브루트포스로도 가능하다.
 * 나는 마땅한 대안으로 map 을 이용해서 line 을 위아래로 비교처리.
 * 
**/
class Solution {
    fun equalPairs(grid: Array<IntArray>): Int {
        val rows = grid.size
        val cols = grid[0].size
        var count = 0
        val lineSet = mutableMapOf<String, Int>()

        for (row in 0 until rows) {
            var elements = mutableListOf<Int>()
            for (col in 0 until cols) {   
                elements.add(grid[row][col])
            }
            val elementJoin = elements.joinToString("-")

            lineSet.computeIfPresent(elementJoin) { _, value ->
                value + 1
            }
            lineSet.putIfAbsent(elementJoin, 1)
        }

        for (row in 0 until rows) {
            var elements = mutableListOf<Int>()
            for (col in 0 until cols) {   
                elements.add(grid[col][row])
            }
            val elementJoin = elements.joinToString("-")
            
            if (lineSet.contains(elementJoin)) {
                count += lineSet[elementJoin] ?: 0
            }
        }

        return count
    }
}
