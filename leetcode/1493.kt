class Solution {
    fun longestSubarray(nums: IntArray): Int {
        if (nums.size == 0) {
            return 0
        }

        val zeroIndexArray = mutableListOf<Int>()
        val minIndexed = 0
        val maxIndexed = nums.size - 1

        nums.forEachIndexed { index, element ->
            if (element == 0) zeroIndexArray.add(index)
        }

        if (zeroIndexArray.size == 0) {
            return nums.size - 1
        }

        val size = zeroIndexArray.size
        var maxValue = -1
        for (index in 0 until size) {

            val middleIndex = zeroIndexArray[index]
            val leftIndex = if (index - 1 >= 0) { 
                zeroIndexArray[index - 1] + 1
            } else minIndexed
            val rightIndex = if (index + 1 < size) {
                zeroIndexArray[index + 1] - 1
            } else maxIndexed

            val leftSum = (middleIndex - leftIndex)
            val rightSum = (rightIndex - middleIndex)
            maxValue = max(leftSum + rightSum, maxValue)
        }

        return maxValue
    }
}
