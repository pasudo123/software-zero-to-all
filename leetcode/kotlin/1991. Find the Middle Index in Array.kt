class Solution {
    fun findMiddleIndex(nums: IntArray): Int {

        if (nums.size == 1) {
            return 0
        }

        for (middleIndex in nums.indices) {
            val leftSum = sumOfSide(nums, middleIndex - 1, -1)
            val rightSum = sumOfSide(nums, middleIndex + 1, +1)

            if (leftSum == rightSum) {
                return middleIndex
            }
        }

        return -1
    }

    private fun sumOfSide(nums: IntArray, currentIndex: Int, direction: Int): Long {

        if (currentIndex < 0 || currentIndex >= nums.size) {
            return 0
        }

        return nums[currentIndex] + sumOfSide(nums, (currentIndex + direction), direction)
    }
}
