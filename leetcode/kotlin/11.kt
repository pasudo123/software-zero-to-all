class Solution {
    fun maxArea(height: IntArray): Int {
        var startIndex = 0
        var endIndex = height.size - 1
        var result = -1

        while (startIndex < endIndex) {
            val startHeight = height[startIndex]
            val endHeight = height[endIndex]

            if (startHeight < endHeight) {
                val container = startHeight * abs(startIndex - endIndex)
                result = max(result, container)
                startIndex++
            } else {
                val container = endHeight * abs(startIndex - endIndex)
                result = max(result, container)
                endIndex--
            }
        }

        return result
    }
}
