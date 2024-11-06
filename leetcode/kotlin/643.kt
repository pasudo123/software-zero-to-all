class Solution {
    fun findMaxAverage(nums: IntArray, k: Int): Double {
        var sum = 0
        var maxVal = -10001.0
        var firstIndexValue = 0

        for (index in 0 until k) {
            sum +=nums[index]
        }

        println("maxVal=${maxVal}, sum=${sum}")
        firstIndexValue = nums[0]
        maxVal = max(maxVal, sum.toDouble() / k)

        for (index in 0 until nums.size) {
            val nextIndex = index + k

            if (nextIndex >= nums.size) break

            sum += nums[nextIndex]
            sum -= firstIndexValue

            maxVal = max(maxVal, sum.toDouble() / k)
            firstIndexValue = nums[index + 1]
        }

        return maxVal
    }
}
