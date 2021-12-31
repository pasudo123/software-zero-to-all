class Solution {
    fun kthLargestNumber(nums: Array<String>, k: Int): String {
        val newNums = mutableListOf<BigInteger>()
        nums.forEach { num ->
            newNums.add(BigInteger(num))
        }

        val sortedNewNums = newNums.sortedDescending()
        return (sortedNewNums[k - 1]).toString()
    }
}
