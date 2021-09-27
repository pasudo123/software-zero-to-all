class Solution {
    fun maximumDifference(nums: IntArray): Int {
        var lastIndex = nums.size - 1

        var max = -1

        while(lastIndex >= 0) {
            for (index in nums.indices) {
                if (lastIndex < index) {
                    break
                }

                if (nums[index] >= nums[lastIndex]) {
                    continue
                }

                max = max.coerceAtLeast(nums[lastIndex] - nums[index])
            }
            lastIndex--
        }

        return max
    }
}
