class Solution {
    fun increasingTriplet(nums: IntArray): Boolean {

        var first = Int.MAX_VALUE
        var second = Int.MAX_VALUE
        for (index in nums.indices) when {
            nums[index] <= first -> first = nums[index]
            nums[index] <= second -> second = nums[index]
            else -> return true
        }

        return false
    }
}
