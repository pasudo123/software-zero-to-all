class Solution {
    /**
     * 간격의 [start, end] 에서 end 를 오름차순 정렬한다.
     * 그리고 end 를 기준으로 현재 순회하고 있는 start 값과 비교한다.
     * start 값이 더 큰 경우는 오버래핑이 되는 구간이기 때문에 remove 해야하는 구간이다. 
     */
    fun eraseOverlapIntervals(intervals: Array<IntArray>): Int {
        intervals.sortBy{ it[1] }
        var prevEnd = Int.MIN_VALUE
        var removeCount = 0

        for (interval in intervals) {
            val (start, end) = interval
            
            if (start < prevEnd) {
                removeCount++
                continue
            }

            prevEnd = end
        }

        return removeCount 
    }
}
