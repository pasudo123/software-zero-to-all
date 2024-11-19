class Solution {
    fun productExceptSelf(nums: IntArray): IntArray {
        val result = IntArray(nums.size) { 1 }
        var leftProduct = 1
        var rightProduct = 1

        for (index in nums.indices) {
            result[index] = leftProduct
            leftProduct *= nums[index]
        }

        for (index in nums.indices.reversed()) {
            result[index] *= rightProduct
            rightProduct *= nums[index]
        }

        return result
    }
}
