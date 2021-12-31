class Solution {
    fun numOfPairs(nums: Array<String>, target: String): Int {
        var count = 0
        for (firstIndex in nums.indices) {
            val firstElement = nums[firstIndex]
            for (secondIndex in nums.indices) {
                if (firstIndex == secondIndex) {
                    continue
                }

                val secondElement = nums[secondIndex]
                if (target == "${firstElement}${secondElement}") {
                    count++
                }
            }
        }

        return count
    }
}
