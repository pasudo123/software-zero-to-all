class Solution {
    fun findMinArrowShots(points: Array<IntArray>): Int {
        points.sortBy { it[1] }

        var count = 0
        var prevEnd = Long.MIN_VALUE

        for (point in points) {
            val (start, end) = point
            
            // 이전 풍선 구간에 겹치지 않는 곳에 화살을 발사. count++
            // 그리고 구간을 새롭게 갱신한다
            if (start > prevEnd) {
                count++
                prevEnd = end.toLong()
            }
        }

        return count
    }
}
