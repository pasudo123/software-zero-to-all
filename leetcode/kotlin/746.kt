/**
 * 동적 프로그래밍 처리. (chatGPT 도움.)
 * dp 에 계속 최적화된 값을 쌓아둔다
 */
class Solution {
    fun minCostClimbingStairs(cost: IntArray): Int {
        val dp = IntArray(cost.size + 1)
        dp[0] = 0
        dp[1] = 0

        for (index in 2..cost.size) {
            dp[index] = min(dp[index - 1] + cost[index - 1], dp[index - 2] + cost[index - 2])
        }

        return dp[cost.size]
    }
}
