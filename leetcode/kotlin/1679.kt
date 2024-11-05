class Solution {
    fun maxOperations(nums: IntArray, k: Int): Int {
        nums.sort()

        var startIndex = 0
        var endIndex = nums.size - 1
        var result = 0

        while (startIndex < endIndex) {
            val sum = nums[startIndex] + nums[endIndex]

            if (sum < k) {
                startIndex++
            } else if (sum > k) {
                endIndex--
            } else {
                startIndex++
                endIndex--
                result++
            }
        }

        return result
    }
}
