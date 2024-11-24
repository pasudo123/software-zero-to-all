/**
 * 점화식을 세워서 푼다.
 * 인접한 인덱스를 연속해서 처리하면 안됨.
 */
class Solution {
    fun rob(nums: IntArray): Int {
        if (nums.size == 1) {
            return nums[0]
        }

        if (nums.size == 2) {
            return max(nums[0], nums[1])
        }

        val dp = IntArray(nums.size)
        dp[0] = nums[0]
        dp[1] = max(dp[0], nums[1])
        dp[2] = max(dp[0] + nums[2], dp[1])

        for (index in 2 until nums.size) {
            dp[index] = max(dp[index - 2] + nums[index], dp[index - 1])
        }

        return max(dp[nums.size - 2], dp[nums.size - 1])
    }
}
