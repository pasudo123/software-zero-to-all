class Solution {
    fun sumOfBeauties(nums: IntArray): Int {

        val leftArray: Array<Boolean> = Array(nums.size) { false }
        val rightArray: Array<Boolean> = Array(nums.size) { false }

        // left
        var leftValue = nums[0]
        leftArray[0] = true
        for (index in 1 until nums.size) {
            if (nums[index] > leftValue) {
                leftArray[index] = true
                leftValue = nums[index]
            }
        }

        // right
        var rightValue = nums[nums.size - 1]
        rightArray[nums.size - 1] = true
        for (index in nums.size - 2 downTo 0 ) {
            if (nums[index] < rightValue) {
                rightArray[index] = true
                rightValue = nums[index]
            }
        }

        var result = 0
        for (index in 1 until nums.size - 1) {
            if (leftArray[index] && rightArray[index]) {
                result += 2
                continue
            }

            if (nums[index-1] < nums[index] && nums[index] < nums[index + 1])
                result += 1
        }

        return result
    }
}
