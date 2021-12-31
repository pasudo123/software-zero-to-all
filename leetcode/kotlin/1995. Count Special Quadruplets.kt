class Solution {
    fun countQuadruplets(nums: IntArray): Int {

        var resultSum = 0

        for (firstIndex in 0 until nums.size) {
            for (secondIndex in firstIndex + 1 until nums.size) {
                for (thirdIndex in secondIndex + 1 until nums.size) {
                    val currentSum = nums[firstIndex] + nums[secondIndex] + nums[thirdIndex]
                    resultSum += sum(nums, thirdIndex + 1, currentSum)
                }
            }
        }

        return resultSum
    }

    private fun sum(nums: IntArray, lastIndex: Int, currentSum: Int): Int {
        var count = 0
        for(index in lastIndex until nums.size) {
            count = if (nums[index] == currentSum) ++count else count
        }

        return count
    }
}
